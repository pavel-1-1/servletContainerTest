<%--
  Created by IntelliJ IDEA.
  User: Laku
  Date: 20.03.2023
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My super project!</title>
</head>
<body>
<div>
    <h1>GRUD app!</h1>
</div>
<div>
    <div>
        <form method="post" action="/create">
            <label>Id: Create
                <input type="text" name="id">
            </label>
            <label>Content:
                <input type="text" name="content"><br>
            </label>
            <button type="submit">List create</button>
        </form>

        <form method="get" action="/read">
            <div>
                Id: All read<br>
            </div>
            <button type="submit">List read</button>
        </form>

        <form method="post" action="/update">
            <label>Id: Update!
                <input type="text" name="id">
            </label>
            <label>Content:
                <input type="text" name="content"><br>
            </label>
            <button type="submit">List update</button>
        </form>

        <form method="post" action="/delete">
            <label>Id: Delete!
                <input type="text" name="id"><br>
            </label>
            <button type="submit">List delete</button>
        </form>
    </div>
</div>
</body>
</html>