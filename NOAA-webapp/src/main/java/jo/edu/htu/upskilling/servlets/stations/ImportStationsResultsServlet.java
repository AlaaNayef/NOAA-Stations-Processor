package jo.edu.htu.upskilling.servlets.stations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImportStationsResultsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String totalRows = req.getParameter("totalRowsCount");
        String insertedRows = req.getParameter("insertedRowsCount");
        String updatedRows = req.getParameter("updatedRowsCount");



    }
}
