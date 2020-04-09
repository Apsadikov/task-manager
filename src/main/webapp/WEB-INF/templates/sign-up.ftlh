<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/base.css">
    <link rel="stylesheet" href="/static/css/sign-up.css">
    <link rel="stylesheet" href="/static/fontawesome-free-5.13.0-web/css/all.min.css">
</head>
<body>
<div class="header-wrapper">
    <div class="header">
        <div class="app-name">Task Manager</div>
        <div><a href="/sign-in" class="link"><i class="fas fa-sign-in-alt"></i> Sign In</a></div>
    </div>
</div>
<div class="wrapper">
    <div class="sign-in shadow">
        <div class="sign-up-header">Sign Up</div>
        <form action="/sign-up" class="sign-in-form" method="post">
            <div class="hint">Name</div>
            <input type="text" placeholder="Name" name="name" class="input">
            <div class="error hidden">Enter name</div>
            <div class="hint">Email</div>
            <input type="email" name="email" placeholder="Email" class="input">
            <div class="error hidden">Email invalid</div>
            <div class="hint">Password</div>
            <input type="password" name="password" placeholder="Password" class="input">
            <div class="error hidden">Password must be greater 8 characters</div>
            <input type="submit" class="send" value="Sign Up">
            <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
        </form>
    </div>
</div>
</body>
</html>