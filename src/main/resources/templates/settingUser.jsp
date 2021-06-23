<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Setting page</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>name fun</th>
        <th>short description</th>
        <th>rating</th>
        <th>likes</th>
        <th>created</th>
        <th>type Genre</th>
        <th>read fun fiction</th>
        <th>delete fun fiction</th>
        <th>updated fun fiction</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${funFictions}" var="funFiction">
        <tr>
            <td>${funFiction.getNameFun()}</td>
            <td>${funFiction.getShortDescription()}</td>
            <td>${funFiction.getRating()}</td>
            <td>${funFiction.getLike()}</td>
            <td>${funFiction.getCreated()}</td>
            <td>${funFiction.genre.getTypeGenre()}</td>
            <td>
                <form action="<c:url value="readFunFick/${funFiction.id}"/>" method="get">
                    <button class="button block"><i class="fa fa-lock">read fun fiction</i>
                    </button>
                </form>
            </td>
            <td>
                <form action="<c:url value="updatedFunFictionGet/${funFiction.id}"/>" method="get">
                    <button class="button block"><i class="fa fa-lock">update fun fiction</i>
                    </button>
                </form>
            </td>
            <td>
                <form action="<c:url value="deleteFunFiction/${funFiction.id}"/>" method="get">
                    <button class="button block"><i class="fa fa-lock">delete fun fiction</i>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>