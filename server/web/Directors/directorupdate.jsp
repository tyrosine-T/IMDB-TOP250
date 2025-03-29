<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/5
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新现有信息</title>
    <link rel="stylesheet" type="text/css" href="../css/update.css">
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
<h1>更新导演信息</h1>
<form action="directorupdate" method="post" class="update-form">
    <input type="hidden" name="director_movie_id" value="${user_attribute.director_movie_id}">

    <div>
        <label for="movie_id">电影ID:</label>
        <input type="text" name="movie_id" id="movie_id" value="${user_attribute.movie_id}">
    </div>
    <div>
        <label for="name">评分:</label>
        <input type="text" name="name" id="name" value="${user_attribute.name}">
    </div>
    <div align="center">
        <input type="submit" value="更新">
        <input type="reset"  value="重置">
    </div>
</form>

<div class="bottom-section">
    <a href="directors">返回主列表</a>
</div>
</body>
</html>