package com.BackEndLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")  // this will make this java file as servlet
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Getting KeyWord from FrontEnd
        String keyword = request.getParameter("keyword");
        //Setting up a connection to database
        Connection connection = DatabaseConnection.getConnection();
        try {
            //Store the query of user
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "https://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();

            //Getting results after running the ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select  pageTitle, pageLink, (length(lower(pageText))-length(replace(lower(pageText), '" + keyword.toLowerCase() + "', '')))/length('" + keyword.toLowerCase() + "') as CountOccurences from pages order by CountOccurences desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            //Transferring  values  from resultSet to results arrayList
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            //Displaying  results arrayList in console
            for(SearchResult result:results) {
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }

            request.setAttribute("results", results);//results will be called results arraylist;
            request.getRequestDispatcher("search.jsp").forward(request, response);
            response.setContentType("text/html");   
            PrintWriter out = response.getWriter();
        }
        catch(SQLException | ServletException sqlException) {
            sqlException.printStackTrace();
        }

    }
}

