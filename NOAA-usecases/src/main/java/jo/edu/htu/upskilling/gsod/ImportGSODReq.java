package jo.edu.htu.upskilling.gsod;

import java.nio.file.Path;

public class ImportGSODReq {

    private Path path;

    public ImportGSODReq(Path path) {

        this.path = path;
    }

    public  Path getPath() {
        return path;
    }
}
