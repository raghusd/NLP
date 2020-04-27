/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@WebServlet(urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
                 ArrayList al = new ArrayList();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String s = request.getParameter("sno");
		String l = request.getParameter("String");
                 if ((s == null) || (s.equals(""))) {
            al.add("PROVIDE S NUMBER...");
        } 
        if ((l == null) || (l.equals(""))) {
            al.add("PROVIDE LINK...");
        }
              if (al.size() != 0) {
            out.println(al);
        } else {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

			PreparedStatement ps = con.prepareStatement("insert into list values(?,?)");

			ps.setInt(1, Integer.parseInt(s));
			ps.setString(2, l);
			
			int i = ps.executeUpdate();
			if (i > 0)
				out.print("You are successfully registered...");
                        
                                                                                    
		} catch (Exception e2) {
			System.out.println(e2);
		}

		out.close();
	}

}
}


    