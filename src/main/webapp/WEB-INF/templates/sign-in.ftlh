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
    <link rel="stylesheet" href="/static/css/sign-in.css">
    <link rel="stylesheet" href="/static/fontawesome-free-5.13.0-web/css/all.min.css">
</head>
<body>
<div class="header-wrapper">
    <div class="header">
        <div class="app-name">Task Manager</div>
        <div><a href="/sign-up" class="link"><i class="fas fa-user-plus"></i> Sign Up</a></div>
    </div>
</div>
<div class="wrapper">
    <div class="sign-in shadow">
        <div class="sign-in-header">Sign in</div>
        <form action="/sign-in" class="sign-in-form" method="post">
            <div class="hint">Email</div>
            <input type="email" name="email" placeholder="Email" class="input">
            <div class="error hidden">Email invalid</div>
            <div class="hint">Password</div>
            <input type="password" name="password" placeholder="Password" class="input">
            <div class="error hidden">Email invalid</div>
            <input type="submit" class="send" value="Sign In">
            <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
        </form>
    </div>
</div>
</body>
</html>