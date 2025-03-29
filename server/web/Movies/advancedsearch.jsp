<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/5
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>高级搜索</title>
  <link rel="stylesheet" type="text/css" href="../css/adv_search.css">
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
<h1>高级搜索</h1>
<div class="container">
  <form action="advancedsearch" method="get">
    <div class="input-group">
      <label for="title">片名:</label>
      <input type="text" id="title" name="title">
    </div>
    <div class="input-group">
      <label for="director_id">导演:</label>
      <input type="text" name="director_id" id="director_id">
    </div>
    <div class="input-group">
      <label for="actor_id">演员:</label>
      <input type="text" name="actor_id" id="actor_id">
    </div>
    <div class="input-group">
      <label for="genre">类型:</label>
      <select id="genre" name="genre">
        <option value="">不限</option>
        <option value="Drama">戏剧</option>
        <option value="Crime">犯罪</option>
        <option value="Action">动作</option>
        <option value="Adventure">冒险</option>
        <option value="Biography">传记</option>
        <option value="History">历史</option>
        <option value="Western">西部</option>
        <option value="Romance">情感</option>
        <option value="Sci-Fi">科幻</option>
        <option value="War">战争</option>
        <option value="Thriller">惊悚</option>
        <option value="Music">音乐剧</option>
        <option value="Comedy">喜剧</option>
        <option value="Horror">恐怖</option>
      </select>
    </div>

    <div class="input-group">
      <label for="content_rating">内容评级:</label>
      <select id="content_rating" name="content_rating">
        <option value="">不限</option>
        <option value="G">G级</option>
        <option value="Approved" style="display: none;">G级</option>
        <option value="Passed" style="display: none;">G级</option>
        <option value="PG">PG级</option>
        <option value="PG-13">PG-13级</option>
        <option value="R">R级</option>
        <option value="NC-17">NC-17级</option>
        <option value="Not Rated">未评级</option>
      </select>
    </div>


    <div class="input-group">
      <label for="duration">片长:</label>
      <input type="text" id="duration" name="duration">
    </div>
    <div class="input-group">
      <label for="release_year">发行时间:</label>
      <input type="text" name="release_year" id="release_year">
    </div>

    <div class="input-group">
      <button type="submit">搜索</button>
    </div>
  </form>

  <div class="bottom-section">
    <a href="movies">返回主列表</a>
  </div>
</div>
</body>
</html>