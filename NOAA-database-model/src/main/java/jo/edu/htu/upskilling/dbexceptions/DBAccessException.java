package jo.edu.htu.upskilling.dbexceptions;

public class DBAccessException extends RuntimeException{
    public DBAccessException() {
    }

    public DBAccessException(String message) {
        super(message);
    }

    public DBAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBAccessException(Throwable cause) {
        super(cause);
    }
}
