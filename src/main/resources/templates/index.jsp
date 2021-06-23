<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<input type="button" onclick="tema()" value="Сменить тему"/>
<div class="banner">
    <div class="container">
        <div class="top-header">
        </div>
        <div class="banner-info">
            <div id="top" class="callbacks_container">
                <ul class="rslides" id="slider3">
                    <li>
                        <div class="banner-text">
                            <h3>Welcom to Arent Car</h3>
                            <p></p>
                            <a href="${pageContext.request.contextPath}comeIn" class="hvr-rectangle-out button">Come
                                In</a>
                            <a href="${pageContext.request.contextPath}registration"
                               class="hvr-rectangle-in button red">Registration</a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

</body>
</html>
<style type="text/css">
    body{
        background-color: white;
    }
    body.dark-mode{
        background-color: black;
    }

</style>
<script type="application/javascript">
    function tema()
    {
        document.body.classList.toggle('dark-mode');
    }

</script>