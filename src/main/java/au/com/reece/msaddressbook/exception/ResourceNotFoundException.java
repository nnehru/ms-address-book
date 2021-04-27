package au.com.reece.msaddressbook.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {
  private final String message;
  private final String errorCode;
}
