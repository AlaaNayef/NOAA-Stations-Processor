<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<html>
<head>
    <title>Welcome to my project</title>
</head>
<body>
<%--scriptlet code --%>
<%!
    private String getDate() {
        return LocalDate.of(2020, 9, 18).toString();
    }
%>

<% String sysDate = getDate();%>

<%-- scriptlet expression --%>

Time on server <%= sysDate%>
</body>
</html>
