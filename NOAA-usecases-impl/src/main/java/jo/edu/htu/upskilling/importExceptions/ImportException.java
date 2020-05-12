package jo.edu.htu.upskilling.importExceptions;

import java.io.IOException;

public class ImportException extends RuntimeException {
    public ImportException() {
    }

    public ImportException(Exception exception) {
        super(exception);
    }


}

