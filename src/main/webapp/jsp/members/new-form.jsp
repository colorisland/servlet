<!-- JSP문서라는 뜻이다. JSP 문서는 이렇게 시작해야 한다-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
 username: <input type="text" name="username" />
 age: <input type="text" name="age" />
 <button type="submit">전송</button>
</form>
</body>
</html>