package au.com.reece.msaddressbook.exception;

import au.com.reece.msaddressbook.vo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static au.com.reece.msaddressbook.entity.ReeceApiStatus.API_404;
import static au.com.reece.msaddressbook.entity.ReeceApiStatus.API_500;

@RestControllerAdvice
public class ApiExceptionalHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    return ErrorResponse.builder().errorCode("Resource Not found").errorId(API_404.name()).build();
  }

  @ExceptionHandler(ServiceForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponse handleForbiddenException(ServiceForbiddenException ex, WebRequest request) {
    return ErrorResponse.builder()
        .errorCode("Not Allowed")
        .errorMessage(ex.getMessage())
        .errorId("API-403")
        .build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleServerException(Exception ex, WebRequest request) {
    return ErrorResponse.builder()
            .errorCode("Internal Server Error")
            .errorMessage(ex.getMessage())
            .errorId(API_500.name())
            .build();
  }
}
