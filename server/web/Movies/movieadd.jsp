<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/4
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加电影</title>
    <link rel="stylesheet" type="text/css" href="../css/add.css">
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
<h1>电影添加页面</h1>
<form action="movieadd" method="post">
  <table class="add-table">
    <tr>
      <td>电影ID</td>
      <td><input type="text" name="movie_id" id="movie_id"></td>
    </tr>
    <tr>
      <td>片名</td>
      <td><input type="text" name="title" id="title"></td>
    </tr>
    <tr>
      <td>导演</td>
      <td><input type="text" name="director_name" id="director_name"></td>
    </tr>
    <tr>
      <td>主演</td>
      <td><input type="text" name="actor_name" id="actor_name"></td>
    </tr>
    <tr>
      <td>评分</td>
      <td><input type="number" name="rating" id="rating"></td>
    </tr>
    <tr>
      <td>类别</td>
      <td><input type="text" name="genre" id="genre"></td>
    </tr>
    <tr>
      <td>简介</td>
      <td><input type="text" name="description" id="description"></td>
    </tr>
    <tr>
      <td>网址</td>
      <td><input type="text" name="image_url" id="image_url"></td>
    </tr>
    <tr>
      <td>内容评级</td>
      <td><input type="text" name="content_rating" id="content_rating"></td>
    </tr>
    <tr>
      <td>片长</td>
      <td><input type="text" name="duration" id="duration"></td>
    </tr>
    <tr>
      <td>发行年份</td>
      <td><input type="number" name="release_year" id="release_year"></td>
    </tr>
    <tr>
      <td>评分人数</td>
      <td><input type="number" name="rating_count" id="rating_count"></td>
    </tr>
    <tr>
      <%--      colspan 跨列 --%>
      <td align="center" colspan="2">
        <input type="submit" value="提交">
        <input type="reset" value="重置">
      </td>
    </tr>
  </table>
</form>

<div class="bottom-section">
  <a href="movies">返回主列表</a>
</div>

<script>
  function onSubmitForm() {
    // 提交成功后自动返回原版的 department.jsp 文件
    alert("提交成功！将返回电影列表页面。");
    setTimeout(function() {
      window.location.href = "movies.jsp";
    }, 2000); // 2秒后跳转，可根据需要调整延迟时间
    return false; // 阻止表单默认提交行为
  }
</script>

</body>
</html>
