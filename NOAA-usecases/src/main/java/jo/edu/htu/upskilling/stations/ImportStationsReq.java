package jo.edu.htu.upskilling.stations;

import java.nio.file.Path;

public class ImportStationsReq {
   private Path path;

    public ImportStationsReq(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
