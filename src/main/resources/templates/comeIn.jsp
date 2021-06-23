<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Come in page</title>
</head>
<body>
<%--@elvariable id="authenticationRequestDto" type="FunFictionUserProject.funFictionUser.dto.AuthenticationRequestDto"--%>
<form:form class="register-form" action="${pageContext.request.contextPath}login"
           modelAttribute="authenticationRequestDto" method="post">
    <div class="form">
        <form:input path="login" type="text" placeholder="login"/>
        <form:errors path="login" cssStyle="color: red"/>

        <form:input path="password" type="text" placeholder="password"/>
        <form:errors path="password" cssStyle="color: red"/>
        <div class="send-button">
            <input type="submit" value="Come In">
        </div>
    </div>
</form:form>
</body>
</html>