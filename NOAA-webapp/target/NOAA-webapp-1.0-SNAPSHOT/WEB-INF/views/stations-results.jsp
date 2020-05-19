<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stations Results</title>
</head>
<%
    Integer totalRows =(Integer) request.getAttribute("totalRowsCount");
    Integer insertedRows = (Integer)request.getAttribute("insertedRowsCount");
    Integer updatedRows =(Integer) request.getAttribute("updatedRowsCount");%>
<body>

<table>
    <tr>
        <td>New records : <%=insertedRows%>
        </td>

    </tr>
    <tr>
        <td>Updated records : <%=updatedRows%>
        </td>
    </tr>
    <tr>
        <td>Total stations in database : <%=totalRows%>
        </td>
    </tr>
</table>

</body>
</html>
