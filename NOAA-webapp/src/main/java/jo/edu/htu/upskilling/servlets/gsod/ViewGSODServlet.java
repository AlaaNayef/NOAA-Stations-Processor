package jo.edu.htu.upskilling.servlets.gsod;

import jo.edu.htu.upskilling.gsod.ListGSOD;

import javax.servlet.http.HttpServlet;

public class ViewGSODServlet extends HttpServlet {
    ListGSOD listGSOD;

    public ViewGSODServlet(ListGSOD listGSOD) {
        this.listGSOD = listGSOD;
    }
}
