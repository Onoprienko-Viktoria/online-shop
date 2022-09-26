<#import "parts/common.ftl" as c>
<@c.page>
    <h1>Login</h1>
    <form action="/login" method="post">
        <div class="mb-3">
            <label class="form-label"><strong>Email</strong></label>
            <input name="email" type="text" class="form-control" required="required">
        </div>
        <div class="mb-3">
            <label class="form-label"><strong>Password</strong></label>
            <input name="password" type="password" class="form-control" required="required">
        </div>
        <button type="submit" class="btn btn-dark">Submit</button>
    </form>
    <div style="padding-top: 30px">
        <h3>Not registered yet?</h3>
        <form style="display: inline-block;" action="/registration" target="_self">
            <button class="btn btn-dark">Registration</button>
        </form>
        <form style="display: inline-block;" action="/products" target="_self">
            <button class="btn btn-dark">Return to products</button>
        </form>
    </div>
</@c.page>