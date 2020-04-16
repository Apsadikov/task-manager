<#import "base.ftlh" as base>
<@base.extends css="${css}" link="${link}" linkTitle="${linkTitle}" icon="fa-sign-in-alt">
<div class="wrapper">
    <div class="sign-in shadow">
        <div class="sign-up-header">Sign Up</div>
        <form action="/sign-up" class="sign-in-form" method="post">
            <div class="hint">Name</div>
            <input type="text" placeholder="Name" name="name" class="input">
            <div class="hint">Email</div>
            <input type="email" name="email" placeholder="Email" class="input">
            <div class="hint">Password</div>
            <input type="password" name="password" placeholder="Password" class="input">
            <input type="submit" class="send" value="Sign Up">
            <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
        </form>
    </div>
</div>
</@base.extends>