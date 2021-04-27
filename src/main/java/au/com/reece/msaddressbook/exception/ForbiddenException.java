package au.com.reece.msaddressbook.exception;

import lombok.Builder;

@Builder
public class ForbiddenException extends RuntimeException {

  private final String message;
}
