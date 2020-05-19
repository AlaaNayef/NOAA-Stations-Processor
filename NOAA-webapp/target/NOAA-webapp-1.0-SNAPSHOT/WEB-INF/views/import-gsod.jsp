
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Import GSOD</title>
</head>
<body>
<form method="post" action="/noaa/importGSODResult" enctype="multipart/form-data">
    <table>
        <tr>
            <td>GSOD File</td>
            <td>
                <input name="gsodFile" type="file">
            </td>
        </tr>
        <tr>
            <td colspan="3"></td>
            <td>
                <button>Import</button>
            </td>
        </tr>

    </table>
</form>
</body>
</html>
