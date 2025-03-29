<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/5
  Time: 13:42
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
<h1>更新评分信息</h1>
<form action="ratingupdate" method="post" class="update-form">
  <input type="hidden" name="rating_id" value="${user_attribute.rating_id}">

  <div>
    <label for="movie_id">电影ID:</label>
    <input type="text" name="movie_id" id="movie_id" value="${user_attribute.movie_id}">
  </div>
  <div>
    <label for="rating">评分:</label>
    <input type="text" name="rating" id="rating" value="${user_attribute.rating}">
  </div>
  <div>
    <label for="rating_count">评分人数:</label>
    <input type="text" name="rating_count" id="rating_count" value="${user_attribute.rating_count}">
  </div>

  <div align="center">
    <input type="submit" value="更新">
    <input type="reset"  value="重置">
  </div>
</form>

<div class="bottom-section">
  <a href="ratings">返回主列表</a>
</div>
</body>
</html>
