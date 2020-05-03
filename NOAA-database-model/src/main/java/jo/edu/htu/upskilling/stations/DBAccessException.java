package jo.edu.htu.upskilling.stations;

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
