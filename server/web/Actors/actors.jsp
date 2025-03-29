<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/4
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>演员列表</title>
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
    <h1>演员列表</h1>

    <div class="top-section">
        <div class="search-form">
            <form action="actorfilter" method="get">
                <select name="searchKey">
                    <option value="actor_id">演员ID</option>
                    <option value="movie_id">电影ID</option>
                    <option value="name">姓名</option>
                </select>
                <input type="text" name="searchValue" placeholder="输入搜索值">
                <button type="submit">搜索</button>
            </form>
        </div>
        <div class="add-button">
            <a href="actoradd.jsp">添加演员</a>
        </div>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>演员ID</th>
                <th>电影ID</th>
                <th>姓名</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ActorList}" var="actor">
                <tr>
                    <td>${actor.actor_id}</td>
                    <td>${actor.movie_id}</td>
                    <td>${actor.name}</td>
                    <td>
                        <a href="actorselectById?actor_id=${actor.actor_id}">修改演员信息</a>
                        <a href="actordelete?actor_id=${actor.actor_id}">删除</a>
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
