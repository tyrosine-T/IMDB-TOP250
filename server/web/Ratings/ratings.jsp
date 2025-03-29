<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/4
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>排行榜</title>
    <link rel="stylesheet" type="text/css" href="../css/list.css">
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
<div class="container">
    <h1>排行榜</h1>

    <div class="top-section">
        <div class="search-form">
            <form action="ratingfilter" method="get">
                <select name="searchKey">
                    <option value="rating_id">排名</option>
                    <option value="movie_id">电影ID</option>
                    <option value="rating">评分</option>
                    <option value="rating_count">评价人数</option>
                </select>
                <input type="text" name="searchValue" placeholder="输入搜索值">
                <button type="submit">搜索</button>
            </form>
        </div>
        <div class="add-button">
            <a href="ratingadd.jsp">添加评分</a>
        </div>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>排名</th>
                <th>电影ID</th>
                <th>评分</th>
                <th>评价人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${RatingList}" var="rating">
                <tr>
                    <td>${rating.rating_id}</td>
                    <td>${rating.movie_id}</td>
                    <td>${rating.rating}</td>
                    <td>${rating.rating_count}</td>
                    <td>
                        <a href="ratingselectById?rating_id=${rating.rating_id}">修改用户信息</a>
                        <a href="ratingdelete?rating_id=${rating.rating_id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="bottom-section">
        <a href="../index.jsp">返回主列表</a>
    </div>
</div>
</body>
</html>
