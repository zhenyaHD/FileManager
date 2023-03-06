<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="org.example.FileInformation"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FileManager</title>
</head>
<body>
<p>${date}</p>
<h1>${name}</h1>
<hr/>
<img src="https://cdn-icons-png.flaticon.com/512/7778/7778637.png" width="16" height="16"><a href="?path=${back}">Назад</a>
<table>
    <tr>
        <th></th>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <%
    List<FileInformation> files = (List<FileInformation>) request.getAttribute("files");
    if(files == null) {
        out.print("Files is null");
        return;
    }
    for(int i = 0; i < files.size(); i++) {
        FileInformation file = files.get(i);
    %>
        <tr>
            <% if(file.isDirectory()) { %>
                    <td>
                        <img src="https://cdn-icons-png.flaticon.com/512/7525/7525173.png" width="16" height="16" alt="directory">
                    </td>
                    <td>
                        <a href="?path=<%=file.getLink()%>"><%=file.getFileName()%></a>
                    </td>
                    <td></td>
                <% } else { %>
                    <td>
                        <img src="https://cdn-icons-png.flaticon.com/512/7525/7525161.png" width="16" height="16" alt="file">
                    </td>
                    <td>
                        <a href="download?path=<%=file.getLink()%>"><%=file.getFileName()%></a>
                    </td>
                    <td><%=file.getFileSize()%> B</td>
               <% } %>
            <td><%=file.getDate()%></td>
        </tr>
    <% } %>
</table>
</body>
</html>