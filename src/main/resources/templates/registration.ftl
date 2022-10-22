<#import "parts/common.ftl" as c>
<@c.page>
    <h1>Registration</h1>
    <form action="/registration" method="post">
        <div class="mb-3">
            <label class="form-label"><strong>Name</strong></label>
            <input name="name" type="name" class="form-control" required="required" id="name">
        </div>
        <div class="mb-3">
            <label class="form-label"><strong>Email address</strong></label>
            <input name="email" type="email" class="form-control" required="required" id="InputEmail">
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input name="password" type="password" class="form-control" required="required" id="InputPassword">
        </div>
        <button type="submit" class="btn btn-dark">Submit</button>
    </form>
    <h3>Already registered?</h3>
    <form style="display: inline-block;" action="/login" target="_self">
        <button class="btn btn-dark">Login</button>
    </form>
    <form style="display: inline-block;" action="/products" target="_self">
        <button class="btn btn-dark">Return to products</button>
    </form>
</@c.page>