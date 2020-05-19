package jo.edu.htu.upskilling.servlets.stations;

import jo.edu.htu.upskilling.stations.ImportStationRes;
import jo.edu.htu.upskilling.stations.ImportStations;
import jo.edu.htu.upskilling.stations.ImportStationsReq;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportStationsServlet extends HttpServlet {
    private ImportStations importStations;
    private Path tempDirectory;

    public ImportStationsServlet(ImportStations importStations) {
        this.importStations = importStations;
    }

    @Override
    public void init() throws ServletException {

        String userHome = System.getProperty("user.home");
        System.out.println(userHome + "user home path is ");

        tempDirectory = Paths.get(userHome).resolve("stations-temp");
        try {
            Files.createDirectories(tempDirectory);
        } catch (IOException exception) {
            throw new ServletException(exception);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Part part = req.getPart("stationFile");
        System.out.println(part.getSubmittedFileName() + "file name is ");
        Path fileToImport = Files.createTempFile("myStations", ".temp");

        part.write(fileToImport.toString());

        ImportStationRes importStationRes = importStations.importStations(new ImportStationsReq(fileToImport));

        req.setAttribute("totalRowsCount", importStationRes.getTotalNumOfRecords());
        req.setAttribute("insertedRowsCount", importStationRes.getNewRecordsCount());
        req.setAttribute("updatedRowsCount", importStationRes.getUpdatedRecordsCount());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/stations-results.jsp");
        requestDispatcher.forward(req, resp);
//        String totalRows = request.getParameter("totalRowsCount");
//        String insertedRows = request.getParameter("insertedRowsCount");
//        String updatedRows = request.getParameter("updatedRowsCount");
        //forwardToView(req, resp);

    }


    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/import-stations.jsp");
        requestDispatcher.forward(req, resp);
    }
}
