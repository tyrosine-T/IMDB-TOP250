
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

<form action="actorfilter" method="get" class="search-form">
  <p>继续搜索</p>
  <select name="searchKey">
    <option value="actor_id">演员ID</option>
    <option value="movie_id">电影ID</option>
    <option value="name">姓名</option>
  </select>
  <input type="text" name="searchValue" placeholder="输入搜索值">
  <button type="submit">搜索</button>
</form>

<div class="table-container">
  <table align="center">
    <thead>
    <tr>
      <th>演员ID</th>
      <th>电影ID</th>
      <th>姓名</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${FilteredActors}" var="actor">
      <tr>
        <td>${actor.actor_id}</td>
        <td>${actor.movie_id}</td>
        <td>${actor.name}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<div class="bottom-section">
  <a href="actors">返回主列表</a>
</div>
</body>
</html>
