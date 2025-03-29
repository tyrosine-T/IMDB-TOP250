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
    <title>导演列表</title>
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
    <h1>导演列表</h1>

    <div class="top-section">
        <div class="search-form">
            <form action="directorfilter" method="get">
                <select name="searchKey">
                    <option value="director_movie_id">导演ID</option>
                    <option value="movie_id">电影ID</option>
                    <option value="director">姓名</option>
                </select>
                <input type="text" name="searchValue" placeholder="输入搜索值">
                <button type="submit">搜索</button>
            </form>
        </div>
        <div class="add-button">
            <a href="directoradd.jsp">添加导演</a>
        </div>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>导演ID</th>
                <th>电影ID</th>
                <th>姓名</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${DirectorList}" var="director">
                <tr>
                    <td>${director.director_movie_id}</td>
                    <td>${director.movie_id}</td>
                    <td>${director.name}</td>
                    <td>
                        <a href="directorselectById?director_movie_id=${director.director_movie_id}">修改导演信息</a>
                        <a href="directordelete?director_movie_id=${director.director_movie_id}">删除</a>
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
