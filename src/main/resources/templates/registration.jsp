<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<%--@elvariable id="registerRequestDto" type="FunFictionUserProject.funFictionUser.dto.RegisterRequestDto"--%>
<form:form class="register-form" action="${pageContext.request.contextPath}save"
           modelAttribute="registerRequestDto" method="post">
    <div class="form">
        <form:input path="username" type="text" placeholder="login"/>
        <form:errors path="username" cssStyle="color: red"/>

        <form:input path="firstName" type="text" placeholder="name"/>
        <form:errors path="firstName" cssStyle="color: red"/>

        <form:input path="lastName" type="text" placeholder="surname"/>
        <form:errors path="lastName" cssStyle="color: red"/>

        <form:input path="email" type="text" placeholder="email"/>
        <form:errors path="email" cssStyle="color: red"/>

        <form:input path="password" type="text" placeholder="password"/>
        <form:errors path="password" cssStyle="color: red"/>

        <form:input path="secondPassword" type="text" placeholder="secondPassword"/>
        <form:errors path="secondPassword" cssStyle="color: red"/>
        <div class="send-button">
            <input type="submit" value="Registration">
        </div>
    </div>
</form:form>
</body>
</html>