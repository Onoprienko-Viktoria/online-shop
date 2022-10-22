<#import "parts/common.ftl" as c>
<@c.page>
    <div align="right">
        <form style="display: inline-block;" action="/cart" target="_self">
            <button class="btn btn-dark">Products cart</button>
        </form>
        <form style="display: inline-block;" action="/product/add" target="_self">
            <button class="btn btn-dark">Add new product</button>
        </form>
        <form method="post" style="display: inline-block;" action="/logout" target="_self">
            <button class="btn btn-dark">Logout</button>
        </form>
        <form style="display: inline-block;" action="/login" target="_self">
            <button class="btn btn-dark">Login</button>
        </form>
        <form style="display: inline-block;" action="/registration" target="_self">
            <button class="btn btn-dark">Registration</button>
        </form>
    </div>
    <h1 align="center">List of products</h1>
    <h3>Search:</h3>
    <div class="input-group mb-3">
        <input type="text" id="word" class="form-control" placeholder="type word..." name="word"
               aria-label="Word to find"
               aria-describedby="button-addon2">
        <button class="btn btn-outline-secondary" onclick="" type="button" id="button-find">Find</button>
        <script type="text/javascript">
            document.getElementById("button-find").onclick = function () {
                location.href = "/products?word=" + word.value;
            };
        </script>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col">Creation Date</th>
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
                <form action="/product/edit" target="_self">
                    <button class="btn btn-dark" value=${product.id} name="id"> Edit</button>
                </form>
            </td>
            <td>
                <form method="post" action="/product/remove" target="_self">
                    <button class="btn btn-dark" value=${product.id} name="id"> Remove</button>
                </form>
            </td>
            <td>
                <form method="post" action="/cart/add" target="_self">
                    <button class="btn btn-dark" value=${product.id} name="id"> Add to cart</button>
                </form>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</@c.page>