package jo.edu.htu.upskilling.initializers;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import jo.edu.htu.upskilling.gsod.*;

import jo.edu.htu.upskilling.servlets.users.AddUserServlet;
import jo.edu.htu.upskilling.servlets.welcome.HomePageServlet;
import jo.edu.htu.upskilling.stations.*;
import jo.edu.htu.upskilling.users.*;
import jo.edu.htu.upskilling.servlets.users.*;
import jo.edu.htu.upskilling.servlets.stations.*;
import jo.edu.htu.upskilling.servlets.gsod.*;

import javax.servlet.*;
import java.util.Set;

public class NOAAStationsInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("hello");
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/NOAA_processor?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        GSODRepository gsodRepository = new DBGSODRepository(dataSource);
        StationsRepository stationsRepository = new DBStationsRepository(dataSource);
        UserRepository userRepository = new DBUSerRepository(dataSource);

        //*** stations use cases ***
        ImportStations importStations = new DefaultImportStations(stationsRepository);
        ListStations listStations = new DefaultListStations(stationsRepository);

        //*** GSOD use cases ***
        ImportGSOD importGSOD = new DefaultImportGSOD(gsodRepository);
        ListGSOD listGSOD = new DefaultListGSOD(gsodRepository);

        //*** users use cases ***
        UsersHandler usersHandler=new DefaultUsersHandler(userRepository);

        prepareUsersServlets(servletContext);
        prepareStationsServlets(servletContext, importStations, listStations);
        prepareGSODServlets(servletContext, importGSOD, listGSOD);
        prepareHomePageServlet(servletContext);


    }

    private void prepareHomePageServlet(ServletContext servletContext) {
        HomePageServlet homePageServlet = new HomePageServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("HomePageServlet", homePageServlet);
        servletRegistration.addMapping("/homePage");
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    private void prepareUsersServlets(ServletContext servletContext) {
        prepareAddUserServlet(servletContext);
        prepareChangePasswordServlet(servletContext);
        prepareUsersManagementsServlet(servletContext);
        prepareLoginScreenServlet(servletContext);

    }

    private void prepareLoginScreenServlet(ServletContext servletContext) {
        LoginScreenServlet loginScreenServlet = new LoginScreenServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("LoginScreenServlet", loginScreenServlet);
        servletRegistration.addMapping("/loginScreen");
    }

    private void prepareChangePasswordServlet(ServletContext servletContext) {
        ChangePasswordServlet changePasswordServlet = new ChangePasswordServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ChangePasswordServlet", changePasswordServlet);
        servletRegistration.addMapping("/changePassword");
    }

    private void prepareAddUserServlet(ServletContext servletContext) {
        AddUserServlet addUserServlet = new AddUserServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("AddUserServlet", addUserServlet);
        servletRegistration.addMapping("/addUser");
    }

    private void prepareUsersManagementsServlet(ServletContext servletContext) {
        UsersManagementsServlet usersManagementsServlet = new UsersManagementsServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("UsersManagementsServlet", usersManagementsServlet);
        servletRegistration.addMapping("/usersManagements");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    private void prepareStationsServlets(ServletContext servletContext, ImportStations importStations, ListStations listStations) {
        prepareImportStationsServlet(servletContext, importStations);
        prepareImportStationsResultsServlet(servletContext);
        prepareViewStationsServlet(servletContext, listStations);
    }

    private void prepareViewStationsServlet(ServletContext servletContext, ListStations listStations) {
        ViewStationsServlet viewStationsServlet = new ViewStationsServlet(listStations);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ViewStationsServlet", viewStationsServlet);
        servletRegistration.addMapping("/viewStations");
    }

    private void prepareImportStationsResultsServlet(ServletContext servletContext) {
        ImportStationsResultsServlet importStationsResultsServlet = new ImportStationsResultsServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ImportStationsResultsServlet", importStationsResultsServlet);
        servletRegistration.addMapping("/importStationsResults");
    }

    private void prepareImportStationsServlet(ServletContext servletContext, ImportStations importStations) {
        ImportStationsServlet importStationsServlet = new ImportStationsServlet(importStations);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ImportStationsServlet", importStationsServlet);
        servletRegistration.addMapping("/importStations");
        servletRegistration.setMultipartConfig(new MultipartConfigElement((String)null));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void prepareGSODServlets(ServletContext servletContext, ImportGSOD importGSOD, ListGSOD listGSOD) {
        prepareImportGSODServlet(servletContext, importGSOD);
        prepareImportGSODResultsServlet(servletContext);
        prepareViewGSODServlet(servletContext, listGSOD);

    }

    private void prepareViewGSODServlet(ServletContext servletContext, ListGSOD listGSOD) {
        ViewGSODServlet viewGSODServlet = new ViewGSODServlet(listGSOD);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ViewGSODServlet", viewGSODServlet);
        servletRegistration.addMapping("/viewGSOD");
    }

    private void prepareImportGSODResultsServlet(ServletContext servletContext) {
        ImportGSODResultsServlet importGSODResultsServlet = new ImportGSODResultsServlet();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ImportGSODResultsServlet", importGSODResultsServlet);
        servletRegistration.addMapping("/importGSODResult");
    }

    private void prepareImportGSODServlet(ServletContext servletContext, ImportGSOD importGSOD) {
        ImportGSODServlet importGSODServlet = new ImportGSODServlet(importGSOD);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("ImportGSODServlet", importGSODServlet);
        servletRegistration.addMapping("/importGSOD");
    }


}
