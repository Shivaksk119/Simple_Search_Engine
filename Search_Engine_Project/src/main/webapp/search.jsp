<%@page import = "java.util.ArrayList"%>
<%@page import = "com.BackEndLogic.SearchResult"%>
<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "styling.css">
</head>
<body>
<h1 class = "h1_styling">My Dumb Search EnginE</h1>
<form action = "Search">
    <input type = "text" name = "keyword"></input>
    <button type = "submit">Search</button>
</form>
<form action = "History">
    <button type = "submit">History</button>
</form>
    <table border = 2 class = "resultTable table_styling">
        <tr>
            <th class = "table_styling tile_link">Title</th>
            <th class = "table_styling tile_link">Link</th>
        </tr>
        <%
            ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
            for(SearchResult result:results) {
        %>
        <tr>
            <td class = "table_styling"><%out.println(result.getTitle());%></td>
            <td class = "table_styling"><a href = "<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
