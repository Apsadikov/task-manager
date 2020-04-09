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
    <link rel="stylesheet" href="/static/css/home.css">
    <link rel="stylesheet" href="/static/fontawesome-free-5.13.0-web/css/all.min.css">
</head>
<body>
<div class="header-wrapper">
    <div class="header">
        <div class="app-name">Task Manager</div>
        <div><a href="/logout" class="link"><i class="fas fa-sign-out-alt"></i> Logout</a></div>
    </div>
</div>
<div class="boards">
    <div class="board">
        <form action="/boards" class="new-board" method="post">
            <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
            <input type="text" placeholder="Title" name="title" class="input">
            <button><i class="fas fa-check" id="save"></i></button>
        </form>
    </div>
    <#list boards as board>
    <div class="board">
        <div><a href="/boards/${board.id}" class="title">${board.title}</a></div>
    </div>
    </#list>
</div>
</body>
</html>