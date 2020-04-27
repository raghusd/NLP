/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import javax.swing.*; 

/**
 *
 * @author Ruchitha
 */
@WebServlet(urlPatterns = {"/RetrieveServlet"})
public class RetrieveServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
                 ArrayList al = new ArrayList();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String s = request.getParameter("sno");
                if ((s == null) || (s.equals(""))) {
                    al.add("PROVIDE S NUMBER...");
                }
        if (al.size() != 0) {
            out.println(al);
        } else {
            try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

			PreparedStatement ps = con.prepareStatement("select * from list where sno=?");

			ps.setInt(1, Integer.parseInt(s));
			ps.setString(1, s);
                        String retrievedString;
                        String[] positiveWords = {"good", "beautiful", "better", "nice", "super"};
                        String[] negativeWords = {"bad", "worse", "worst", "ugly"};
                        String very = "very";
                        int positiveCount = 0;
                        int negativeCount = 0;
                        int veryCount = 0;
                        boolean positiveFound;
			
			out.print("<table width=25% border=1>");

                        out.print("<center><h1>Result:</h1></center>");
                        ResultSet rs=ps.executeQuery();
                        ResultSetMetaData rsmd=rs.getMetaData();
                        while(rs.next()) {
                            out.print("<b><center>"+rsmd.getColumnName(2)+"</center></b><br>");
                            out.print("<b><center>"+rs.getString(2)+"</center></b><br>");
                            String[] words = rs.getString(2).split(" ");
                            for(String w:words) {
                                out.print("<b><center>"+ "Words ::" +w+"</b></center></br>");
                                for(String g:positiveWords){
                                    if(g.equals(w)){
                                        positiveCount++;
                                    }
                                }
                                for(String n:negativeWords) {
                                    if(n.equals(w)){
                                        negativeCount++;
                                    }
                                }
                                if (very.equals(w)) {
                                    veryCount++;
                                }
                            }
                            if (veryCount > 0  && negativeCount > 0) {
                                out.println("<b><center>"+ "This is a very Negative sentence"+"</b></center></br>");
                            } else if(veryCount > 0 && positiveCount > 0) {
                                out.println("<b><center>"+ "This is a very Positive sentence"+"</b></center></br>");
                            } else if(positiveCount > 0) {
                                out.println("<b><center>"+ "This is a Positive sentence"+"</b></center></br>");
                            } else if (negativeCount > 0) {
                                out.println("<b><center>"+ "This is a Negative sentence"+"</b></center></br>");
                            } else {
                               out.println("<b><center>"+ "This is a Neutral sentence"+"</b></center></br>");     
                            }
                            out.print("<form method='post' action='barchart.html'><center><input type='submit' value='Barchart'/></center></form></br>");
                        }
            }catch (Exception e2) {
			System.out.println(e2);
		}

		out.close();
            }
}
}


    