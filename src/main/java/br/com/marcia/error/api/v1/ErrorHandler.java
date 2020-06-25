package br.com.marcia.error.api.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    private final String BAD_REQUEST_MESSAGE = "Invalid request.";

    @ExceptionHandler(BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException e) {
        return handleBadRequest(e);
    }

    private ErrorResponse handleBadRequest(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult
                .getFieldErrors()
                .stream()
                .map(e -> new FieldError(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResponseWithFields(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, errors);
    }

    @Getter
    class ErrorResponse {
        private Integer status;
        private String error;
        private String message;
        private Long timestamp;

        public ErrorResponse(HttpStatus status, String message) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
    }

    @Getter
    class ErrorResponseWithFields extends ErrorResponse {
        private List<FieldError> fieldErrors;

        public ErrorResponseWithFields(HttpStatus status, String message, List<FieldError> fieldErrors) {
            super(status, message);
            this.fieldErrors = fieldErrors;
        }
    }

    @AllArgsConstructor
    @Getter
    class FieldError {
        private String field;
        private String message;
    }


}
