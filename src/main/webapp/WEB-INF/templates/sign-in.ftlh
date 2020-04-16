<#import "base.ftlh" as base>
<@base.extends css="${css}" link="${link}" linkTitle="${linkTitle}" icon="fa-user-plus">
<div class="wrapper">
    <div class="sign-in shadow">
        <div class="sign-in-header">Sign in</div>
        <form action="/sign-in" class="sign-in-form" method="post">
            <div class="hint">Email</div>
            <input type="email" name="email" placeholder="Email" class="input">
            <div class="hint">Password</div>
            <input type="password" name="password" placeholder="Password" class="input">
            <input type="submit" class="send" value="Sign In">
            <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
        </form>
    </div>
</div>
</@base.extends>