<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/5
  Time: 13:38
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
<h1>排行榜</h1>

<form action="ratingfilter" method="get" class="search-form">
    <p>继续搜索</p>
    <select name="searchKey">
        <option value="rating_id">排名</option>
        <option value="movie_id">电影ID</option>
        <option value="rating">评分</option>
        <option value="rating_count">评价人数</option>
    </select>
    <input type="text" name="searchValue" placeholder="输入搜索值">
    <button type="submit">搜索</button>
</form>

<div class="table-container">
    <table align="center">
        <thead>
        <tr>
            <th>排名</th>
            <th>电影ID</th>
            <th>评分</th>
            <th>评价人数</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${FilteredRatings}" var="rating">
            <tr>
                <td>${rating.rating_id}</td>
                <td>${rating.movie_id}</td>
                <td>${rating.rating}</td>
                <td>${rating.rating_count}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="bottom-section">
    <a href="ratings">返回主列表</a>
</div>
</body>
</html>
