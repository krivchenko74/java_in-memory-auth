<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.io.File, java.text.SimpleDateFormat" %>

<html lang="ru">
<head>
    <title>File Browser</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 6px;
        }
        th {
            background-color: #eee;
        }
        .head {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: fit-content;
            gap: 40px;
        }
        .btn_logout {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 8px;
            border-radius: 8px;
            outline: 2px gray solid;
            text-decoration: none;
            background: transparent;
            transition: 0.2;
            color: black;
        }
        .btn_logout:hover {
            background-color: #eee;
        }
    </style>
</head>
<body>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
%>

<span>Время генерации: <%= sdf.format((java.util.Date) request.getAttribute("now")) %></span>
<div class="head">
    <h1>Текущая директория: ${currentPath}</h1>
    <a class="btn_logout" href="/logout">Выйти</a>
</div>

<%
    String parentPath = (String) request.getAttribute("parentPath");
    if (parentPath != null) {
%>
    <a href="?path=<%= parentPath %>">⬆ Вверх</a><br><br>
<%
    }

    File[] files = (File[]) request.getAttribute("files");
%>

<table>
    <tr>
        <th>Тип</th>
        <th>Имя</th>
        <th>Размер (байт)</th>
        <th>Дата изменения</th>
    </tr>

<%
    if (files != null) {
        for (File f : files) {
%>
    <tr>
        <td>
            <%= f.isDirectory() ? "📁" : "📄" %>
        </td>

        <td>
            <a href="?path=<%= f.getAbsolutePath() %>">
                <%= f.getName() %>
            </a>
        </td>

        <td>
            <%= f.isDirectory() ? "-" : f.length() %>
        </td>

        <td>
            <%= sdf.format(f.lastModified()) %>
        </td>
    </tr>
<%
        }
    }
%>

</table>

</body>
</html>