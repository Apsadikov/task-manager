<#macro extends css link linkTitle icon>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/base.css">
    <link rel="stylesheet" href="/static/css/${css}.css">
    <link rel="stylesheet" href="/static/fontawesome-free-5.13.0-web/css/all.min.css">
</head>
<body>
<div class="header-wrapper">
    <div class="header">
        <div class="app-name"><a href="/" class="white">Task Manager</a></div>
        <div class="link"><a href="${link}" class="white"><i class="fas ${icon}"></i> ${linkTitle}</a></div>
    </div>
</div>
<#nested>
</body>
</html>
</#macro>