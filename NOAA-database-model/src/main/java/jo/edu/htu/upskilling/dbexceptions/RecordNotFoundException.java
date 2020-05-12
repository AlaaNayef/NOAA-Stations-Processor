package jo.edu.htu.upskilling.dbexceptions;

import jo.edu.htu.upskilling.dbexceptions.DBAccessException;

public class RecordNotFoundException extends DBAccessException {

    public RecordNotFoundException() {
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
