<%@ page import="whs.jo20046.beans.Data" %>
<html>
<head>
    <title>Eingabe</title>
</head>
<body>
<%
    Data data = (Data) session.getAttribute("Data");
    if (data == null) data = new Data();
%>
<form method="post" action="${pageContext.request.contextPath}/check">
    <label>1. URL:
        <input type="text" name="url1" value="<%=data.getUrl(0)%>"> <%=data.getNotFoundText(0)%><br><br>
    </label>
    <label>2. URL:
        <input type="text" name="url2" value="<%=data.getUrl(1)%>"> <%=data.getNotFoundText(1)%><br><br>
    </label>
    <label>3. URL:
        <input type="text" name="url3" value="<%=data.getUrl(2)%>"> <%=data.getNotFoundText(2)%><br><br>
    </label>
    <input type="submit" value="Best&auml;tigen">
</form>
</body>
</html>
