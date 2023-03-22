package peaksoft.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String massage;

    public ExceptionResponse(HttpStatus httpStatus, String exceptionClassName, String massage) {
        this.httpStatus = httpStatus;
        this.exceptionClassName = exceptionClassName;
        this.massage = massage;
    }
}
