package jo.edu.htu.upskilling.servlets.gsod;

import jo.edu.htu.upskilling.gsod.ImportGSOD;

import javax.servlet.http.HttpServlet;

public class ImportGSODServlet extends HttpServlet {
    ImportGSOD importGSOD;

    public ImportGSODServlet(ImportGSOD importGSOD) {
        this.importGSOD = importGSOD;
    }
}
