package hu.davidhalma.minecraft.exception;

public class UserAlreadyExists extends Exception {

  public UserAlreadyExists() {
  }

  public UserAlreadyExists(String message) {
    super(message);
  }

  public UserAlreadyExists(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyExists(Throwable cause) {
    super(cause);
  }

  public UserAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
