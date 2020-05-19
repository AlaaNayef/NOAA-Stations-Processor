package jo.edu.htu.upskilling.servlets.stations;

import jo.edu.htu.upskilling.stations.ListStations;
import jo.edu.htu.upskilling.stations.ListStationsReq;
import jo.edu.htu.upskilling.stations.ListStationsRes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewStationsServlet extends HttpServlet {
    ListStations listStations;

    public ViewStationsServlet(ListStations listStations) {
        this.listStations = listStations;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToView(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String stationId = req.getParameter("wsaf");
        String wbanNumber = req.getParameter("wban");
        Double longitude;
        Double latitude;
        if (req.getParameter("long").isEmpty()) {
            longitude = null;
        } else {
            longitude = Double.parseDouble(req.getParameter("long"));
        }
        if (req.getParameter("lat").isEmpty()) {
            latitude = null;
        } else {
            latitude = Double.parseDouble(req.getParameter("lat"));
        }
        ListStationsRes listStationsRes = listStations.listStations(new ListStationsReq(stationId, wbanNumber, latitude, longitude));
        req.setAttribute("listStationsRes", listStationsRes.getStations());
        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/view-stations.jsp");
        requestDispatcher.forward(req, resp);
    }
}
