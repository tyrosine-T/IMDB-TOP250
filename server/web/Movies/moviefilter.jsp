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
  <!--
  <script>
    function downloadCSV() {
      event.preventDefault(); // 阻止链接的默认行为
      // 获取表格元素
      var table = document.querySelector('table');

      // 创建一个CSV字符串
      var csv = [];
      var rows = table.querySelectorAll('tr');

      for (var i = 0; i < rows.length; i++) {
        var row = [], cols = rows[i].querySelectorAll('td, th');

        for (var j = 0; j < cols.length; j++)
          row.push(cols[j].innerText);

        csv.push(row.join(','));
      }

      // 创建并下载CSV文件
      var csvContent = "data:text/csv;charset=utf-8," + csv.join('\n');
      var encodedUri = encodeURI(csvContent);
      var link = document.createElement("a");
      link.setAttribute("href", encodedUri);
      link.setAttribute("download", "data.csv");
      document.body.appendChild(link); // 需要将链接添加到文档中，然后才能触发点击事件
      link.click();
    }
  </script>
  -->

  <script>
    function downloadCSV() {
      event.preventDefault(); // 阻止链接的默认行为
    // 获取表格元素
      var table = document.querySelector('table');
    //创建一个CSV字符串
      var csv = [];
      var rows = table.querySelectorAll('tr');
      for (var i = 0; i < rows.length; i++) {
        var row = [], cols = rows[i].querySelectorAll('td, th');
        for (var j = 0; j < cols.length; j++) {
          // 检查当前单元格是否有多个值
          var cellValue = cols[j].innerText;
          if (cellValue.includes(',')) { // 假设多个值以逗号分隔
            var multipleValues = cellValue.split(',');
            // 将多个值用双引号包围并用逗号分隔
            row.push('"' + multipleValues.join(',') + '"');
          } else {
            row.push(cellValue);
          }
        } csv.push(row.join(','));
      } // 创建并下载CSV文件
      var csvContent = "\uFEFF" + csv.join('\n'); // 使用BOM来确保UTF-8编码
      var encodedUri = encodeURI("data:text/csv;charset=utf-8," + csvContent);
      var link = document.createElement("a");
      link.setAttribute("href", encodedUri);
      link.setAttribute("download", "data.csv");
      document.body.appendChild(link); // 需要将链接添加到文档中，然后才能触发点击事件
      link.click(); }
  </script>
<head>
<body>
<h1>电影列表</h1>

<form action="moviefilter" method="get" class="search-form">
  <p>继续搜索</p>
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

<div class="table-container">
  <table align="center">
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
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${FilteredMovies}" var="movies">
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
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<div class="bottom-section">
  <a href="#" onclick="downloadCSV()">下载CSV</a>
  <a href="movies">返回主列表</a>
</div>
</body>
</html>
