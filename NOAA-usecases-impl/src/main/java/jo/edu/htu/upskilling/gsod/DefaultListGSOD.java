package jo.edu.htu.upskilling.gsod;


import java.util.ArrayList;
import java.util.List;

public class DefaultListGSOD implements ListGSOD {
   DBGSODRepository repository;

    public DefaultListGSOD(DBGSODRepository repository) {
        this.repository = repository;
    }

    @Override
    public ListGSODRes listGSOD(ListGSODReq request) {
        List<GSOD> GSODs = new ArrayList<>();
        if (!(request.getStationId()==null)) {
            GSODs.add(repository.findGSODByStationId(request.getStationId()));
        }
        if (!(request.getWbanNum()==null)) {
            GSODs.add(repository.findGSODByWbanNumber(request.getWbanNum()));
        }
        if (!((request.getFromDate()==null) && (request.getToDate()==null))) {
            GSODs.addAll(repository.findGSODBYDate(request.getFromDate(), request.getToDate()));
        }

        return new ListGSODRes(GSODs);
    }
}
