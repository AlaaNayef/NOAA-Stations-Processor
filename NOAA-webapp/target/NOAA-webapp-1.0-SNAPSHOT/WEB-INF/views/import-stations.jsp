<%@ page import="jo.edu.htu.upskilling.stations.ImportStationRes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import Stations</title>
</head>

<body>

<form method="post" action="/noaa/importStations" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Stations File</td>
            <td><input name="stationFile" type="file"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <button>Import</button>
            </td>
        </tr>

    </table>
</form>

</body>
</html>
