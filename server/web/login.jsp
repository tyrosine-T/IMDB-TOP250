<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>登录</title>

  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f2f2f2;
      background-image: url('img/login4.jpg');
      background-size: cover;
      background-repeat: no-repeat;
      background-position: center top;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .login-container {
      width: 400px;
      padding: 20px;
      background-color: rgba(128, 128, 128, 0.5);
      border-radius: 0;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    h1 {
      margin: 0;
      text-align: center;
      margin-bottom: 20px;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
      color: #fff;
    }

    .form-group input[type="text"],
    .form-group input[type="password"] {
      width: 100%;
      height: 30px;
      padding: 5px;
      border-radius: 4px;
      border: 1px solid #aaaaaa;
    }

    .form-group button {
      display: block;
      width: 100%;
      padding: 8px 16px;
      background-color: #444444;
      color: #aaaaaa;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .form-group button:hover {
      background-color: #333333;
    }
  </style>
</head>

<body>
<div class="login-container">
  <h1>CANCAN电影</h1>
  <form action="index.jsp" method="post">
    <div class="form-group">
      <label for="username">用户名：</label>
      <input type="text" name="username" id="username" required>
    </div>
    <div class="form-group">
      <label for="password">密码：</label>
      <input type="password" name="password" id="password" required>
    </div>
    <div class="form-group">
      <button type="submit">登录</button>
    </div>
  </form>
</div>
</body>
</html>