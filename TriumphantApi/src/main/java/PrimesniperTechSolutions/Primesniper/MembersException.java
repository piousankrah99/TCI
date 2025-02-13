package PrimesniperTechSolutions.Primesniper;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class MembersException extends Exception {
    private final String message;
    private final HttpStatus status;

    public MembersException(String message,HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
