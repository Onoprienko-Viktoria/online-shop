<#import "parts/common.ftl" as c>
<@c.page>
        <div align="right">
            <form style="display: inline-block;" action="/products" target="_self">
                <button class="btn btn-dark">Return to products</button>
            </form>
            <form style="display: inline-block;" action="/product/add" target="_self">
                <button class="btn btn-dark">Add new product</button>
            </form>
            <form style="display: inline-block;" action="/logout" target="_self">
                <button class="btn btn-dark">Logout</button>
            </form>
        </div>
        <h1 align="center">Products in cart</h1>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Price</th>
                <th scope="col">Creation date</th>
            </tr>
            </thead>
            <tr>
                <#list products as product>
            <tr>
                <th scope="row">${product.id}</th>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.creationDate}</td>
                <td>
                    <form method="post" action="/cart/remove" target="_self">
                        <button class="btn btn-dark" value=${product.id} name="id"> Remove</button>
                    </form>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
</@c.page>