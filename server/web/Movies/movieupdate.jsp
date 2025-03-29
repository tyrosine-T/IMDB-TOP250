<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/4
  Time: 21:08
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
<h1>更新电影信息</h1>
<form action="movieupdate" method="post" class="update-form">
  <input type="hidden" name="movie_id" value="${user_attribute.movie_id}">

  <div>
    <label for="title">片名:</label>
    <input input type="text" name="title" id="title" value="${user_attribute.title}">
  </div>
  <div>
    <label for="description">简介:</label>
    <input type="text" name="description" id="description" value="${user_attribute.description}">
  </div>
  <div>
    <label for="image_url">网址:</label>
    <input type="text" name="image_url" id="image_url" value="${user_attribute.image_url}">
  </div>
  <div>
    <label for="content_rating">内容评级:</label>
    <input type="text" name="content_rating" id="content_rating" value="${user_attribute.content_rating}">
  </div>
  <div>
    <label for="duration">片长:</label>
    <input type="text" name="duration" id="duration" value="${user_attribute.duration}">
  </div>
  <div>
    <label for="release_year">发行年份:</label>
    <input type="text" name="release_year" id="release_year" value="${user_attribute.release_year}">
  </div>

  <div>
    <label for="director_name">导演:</label>
    <input input type="text" name="director_name" id="director_name" value="${user_attribute.director_name}">
  </div>
  <div>
    <label for="actor">主演:</label>
    <input type="text" name="actor_name" id="actor" value="${user_attribute.actor_name}">
  </div>
  <div>
    <label for="rating">评分:</label>
    <input type="number" name="rating" id="rating" value="${user_attribute.rating}">
  </div>
  <div>
    <label for="genre">类别:</label>
    <input type="text" name="genre" id="genre" value="${user_attribute.genre}">
  </div>


  <div align="center">
    <input type="submit" value="更新">
    <input type="reset"  value="重置">
  </div>
</form>

<div class="bottom-section">
  <a href="movies">返回主列表</a>
</div>
</body>
</html>
