<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/4
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="20">
    <title>电影列表</title>
    <link rel="stylesheet" type="text/css" href="../css/movielist.css">
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
    <h1>电影列表</h1>

    <div class="top-section">
        <div class="search-form">
            <form action="moviefilter" method="get">
                <select name="searchKey">
                    <option value="movie_id">电影ID</option>
                    <option value="title">片名</option>
                    <option value="genre">类别</option>
                    <option value="content_rating">内容评级</option>
                    <option value="duration">片长</option>
                    <option value="release_year">发行年份</option>
                </select>
                <input type="text" name="searchValue" placeholder="输入搜索值">
                <button type="submit">搜索</button>
            </form>
        </div>
        <div class="advanced-search-button">
            <a href="advancedsearch.jsp">高级搜索</a>
        </div>
        <div class="add-button">
            <a href="movieadd.jsp">添加电影</a>
        </div>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>电影ID</th>
                <th>片名</th>
                <th>简介</th>
                <th>内容评级</th>
                <th>片长</th>
                <th>发行年份</th>
                <th>导演</th>
                <th>主演</th>
                <th>评分</th>
                <th>类别</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${MovieList}" var="movies">
                <tr>
                    <td>${movies.movie_id}</td>
                    <td>${movies.title}</td>
                    <td>${movies.description}</td>
                    <td>${movies.content_rating}</td>
                    <td>${movies.duration}</td>
                    <td>${movies.release_year}</td>
                    <td>${movies.director_name}</td>
                    <td>${movies.actor_name}</td>
                    <td>${movies.rating}</td>
                    <td>${movies.genre}</td>
                    <td>
                        <a href="movieselectById?movie_id=${movies.movie_id}">修改电影信息</a>
                        <a href="moviedelete?movie_id=${movies.movie_id}">删除</a>
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