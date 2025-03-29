
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>新增演员</title>
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
<h1>演员添加页面</h1>
<form action="actoradd" method="post">
  <table class="add-table">
    <tr>
      <td>电影ID</td>
      <td><input type="text" name="movie_id" id="movie_id"></td>
    </tr>
    <tr>
      <td>姓名</td>
      <td><input type="text" name="name" id="name"></td>
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
  <a href="actors">返回主列表</a>
</div>

<script>
  function onSubmitForm() {
    // 提交成功后自动返回原版的 actors.jsp 文件
    alert("提交成功！将返回系列表页面。");
    setTimeout(function() {
      window.location.href = "actors.jsp";
    }, 2000); // 2秒后跳转，可根据需要调整延迟时间
    return false; // 阻止表单默认提交行为
  }
</script>

</body>
</html>
