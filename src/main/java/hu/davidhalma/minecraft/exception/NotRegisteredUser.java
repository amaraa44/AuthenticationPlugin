package hu.davidhalma.minecraft.exception;

public class NotRegisteredUser extends Exception {

  public NotRegisteredUser() {
  }

  public NotRegisteredUser(String message) {
    super(message);
  }

  public NotRegisteredUser(String message, Throwable cause) {
    super(message, cause);
  }

  public NotRegisteredUser(Throwable cause) {
    super(cause);
  }

  public NotRegisteredUser(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
