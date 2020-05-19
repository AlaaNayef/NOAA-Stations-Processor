<%@ page import="jo.edu.htu.upskilling.stations.Station" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Stations</title>
</head>
<%
    List<Station> listStationsRes = (List<Station>) request.getAttribute("listStationsRes");
%>
<body>
<form method="post" action="/noaa/viewStations">
    <table>
        <tr>
            <td align="center">WSAF</td>
            <td align="center">WBAN</td>
            <td align="right" aria-colspan="1">LOCATION</td>
        </tr>
        <tr>
            <td><input type="text" name="wsaf"></td>
            <td><input type="text" name="wban"></td>
            <td><input type="text" name="lat"></td>
            <td><input type="text" name="long"></td>
        </tr>
        <tr>
            <td colspan="3"></td>
            <td><input type="reset" name="clear" value="Clear" size="20px" style="margin: 5px">
                <input type="submit" name="search" value="Search" size="20px"></td>
        </tr>
    </table>
    <%
        if (!(listStationsRes == null)) {%>
    <table>
        <tr>
            <td align="center"> WSAF-WBAN</td>
            <td align="center">Name</td>
            <td align="center">Country-State</td>
            <td align="center">Location Lat,Long</td>
        </tr>
        <%
            for (Station station : listStationsRes) {
        %>
        <tr>
            <td>
                <%=station.getStationId()%> <%=station.getWbanNumber()%>
            </td>
            <td>
                <%=station.getStationName()%>
            </td>
            <td>
                <%=station.getCountryId()%> <%=station.getStateOfUS()%>
            </td>
            <td>
                <%=station.getLatitude()%> <%=station.getLongitude()%>
            </td>
        </tr>

        <%
            }%>
    </table>
    <%
        }
    %>

</form>
</body>
</html>