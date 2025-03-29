<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/5
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="refresh" content="20">
    <title>搜索列表</title>
    <link rel="stylesheet" type="text/css" href="../css/filter.css">
    <style>
        body {
            background-image: url('../img/background.jpg');
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center top;
            height: 100vh;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body>
<h1>演员列表</h1>

<form action="directorfilter" method="get" class="search-form">
    <p>继续搜索</p>
    <select name="searchKey">
        <option value="director_movie_id">导演ID</option>
        <option value="movie_id">电影ID</option>
        <option value="direct">姓名</option>
    </select>
    <input type="text" name="searchValue" placeholder="输入搜索值">
    <button type="submit">搜索</button>
</form>

<div class="table-container">
    <table align="center">
        <thead>
        <tr>
            <th>导演ID</th>
            <th>电影ID</th>
            <th>姓名</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${FilteredDirectors}" var="director">
            <tr>
                <td>${director.director_movie_id}</td>
                <td>${director.movie_id}</td>
                <td>${director.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="bottom-section">
    <a href="directors">返回主列表</a>
</div>
</body>
</html>
