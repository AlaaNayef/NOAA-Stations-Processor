package jo.edu.htu.upskilling.stations;

import javax.sql.DataSource;

public class RecordNotFoundException extends DBAccessException{

    public RecordNotFoundException() {
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
