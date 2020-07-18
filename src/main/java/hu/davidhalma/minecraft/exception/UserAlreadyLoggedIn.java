package hu.davidhalma.minecraft.exception;

public class UserAlreadyLoggedIn extends Exception {

  public UserAlreadyLoggedIn() {
  }

  public UserAlreadyLoggedIn(String message) {
    super(message);
  }

  public UserAlreadyLoggedIn(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyLoggedIn(Throwable cause) {
    super(cause);
  }

  public UserAlreadyLoggedIn(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
