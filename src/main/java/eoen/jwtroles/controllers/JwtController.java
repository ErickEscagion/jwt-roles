package eoen.jwtroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eoen.jwtroles.dtos.ErrorsDTO;
import eoen.jwtroles.dtos.JwtRequestDTO;
import eoen.jwtroles.dtos.JwtResponseDTO;
import eoen.jwtroles.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@CrossOrigin
@RequestMapping("v1")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Generate Token", description = "Route to generate token", tags = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated!"),
            @ApiResponse(responseCode = "401", description = "Password does not match!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
            @ApiResponse(responseCode = "404", description = "User Not Found!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
    })
    @PostMapping({ "/authenticate" })
    public ResponseEntity<JwtResponseDTO> createJwtToken(@RequestBody JwtRequestDTO jwtRequest) throws Exception {

        JwtResponseDTO jwtResponse = jwtService.createJwtToken(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
