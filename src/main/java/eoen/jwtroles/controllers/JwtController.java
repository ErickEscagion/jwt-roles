package eoen.jwtroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eoen.jwtroles.dtos.JwtRequestDTO;
import eoen.jwtroles.dtos.JwtResponseDTO;
import eoen.jwtroles.services.JwtService;

@RestController
@CrossOrigin
@RequestMapping("v1")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public ResponseEntity<JwtResponseDTO> createJwtToken(@RequestBody JwtRequestDTO jwtRequest) throws Exception {
       
        JwtResponseDTO jwtResponse = jwtService.createJwtToken(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
