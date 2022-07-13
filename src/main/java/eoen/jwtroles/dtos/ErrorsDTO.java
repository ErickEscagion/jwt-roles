package eoen.jwtroles.dtos;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDTO {

    @Schema(example = "Message base")
    private String message;

    @Schema(example = "Error(s)")
    private List<String> errors;

    @Schema(example = "Status code")
    private HttpStatus status;

    public ErrorsDTO(String message, String error, HttpStatus status) {
        this.message = message;
        this.errors = Arrays.asList(error);
        this.status = status;
    }

}
