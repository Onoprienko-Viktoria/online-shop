<#import "parts/common.ftl" as c>
<@c.page>
    <h1>Edit your Product</h1>
    <strong>
        <form action="/product/edit" method="POST">
            <p>New name: <input type="text" name="name"/></p>
            <p>New price: <input type="number" name="price"/></p>
            <input type="hidden" name="id" value="${id}"/>
            <p><input class="btn btn-dark" type="submit" value="Edit product"></p>
        </form>
        <form action="/products" target="_self">
            <button class="btn btn-dark"> Return to products</button>
        </form>
    </strong>
</@c.page>