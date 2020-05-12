package jo.edu.htu.upskilling.gsod;

import java.util.Collections;
import java.util.List;

public class ListGSODRes {
    private List<GSOD> GSODs;

    public ListGSODRes(List<GSOD> GSODs) {
        this.GSODs = Collections.unmodifiableList(GSODs);
    }

    public List<GSOD> getGSODs() {
        return GSODs;
    }
}
