<%@ page import="whs.jo20046.beans.Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ausgabe</title>
</head>
<body>
<%
    Data data = (Data) session.getAttribute("Data");
%>
url1: <%= data.getUrl(0) %><br>
url2: <%= data.getUrl(1) %><br>
url3: <%= data.getUrl(2) %><br>

</body>
</html>
