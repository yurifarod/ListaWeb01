import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "ListaTurmas", urlPatterns = {"/ListaTurmas"})


public class ListaTurmas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/pweb2";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
            Connection conn;
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Universidade Federal do CSS > Todas as Turmas</title>");
            out.println("<style type=\"text/css\">" +
                "body  {background-image:url(\"Imagem/background.jpg\");"+
                "font-family: verdana; color:red; font-size: 25; text-decoration:none;}"+
                "</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div align = \"center\">");
    	    out.println("<img src = \"Imagem/ufc-logo-6.png\" height=\"100\" weidth=\"100\">");
    	    out.println("<br><br>");
        Autenticador aut = new Autenticador();
	       try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DATABASE_URL, aut.getUser() , aut.getPass() );
	            Statement st = conn.createStatement();
	            ResultSet rec = st.executeQuery("SELECT * FROM turmas");
                    out.println("<table border=1><tr>");
                    out.println("<td><b>NOME DA MATERIA</b></td><td><b>SIGLA</b></td>" +
                            "<td><b>CODIGO</b></td>" + "<td><b>HORARIO 1</b></td>" +
                            "<td><b>HORARIO 2</b></td>" + "<td><b>HORARIO 3</b></td>" +"</tr>");
                    out.println("<form action=\"ListaTurmas\"> ");
	            while(rec.next()) {
                        out.println("<tr><td>"+rec.getString(1)+"</td>"
                                +"<td>"+rec.getString(2)+"</td>"
                                +"<td>"+rec.getString(3)+"</td>"
                                +"<td>"+rec.getString(4)+"</td>"
                                +"<td>"+rec.getString(5)+"</td>"
                                +"<td>"+rec.getString(6)+"</td></tr>");
	            }
                    out.println("</table>");
                    out.println("</form>");
                    out.println("<br><a href=\"index.html\"><input type=\"button\" name=\"button1\" value=\"VOLTAR\"></a>");
	            st.close();
	        } catch (SQLException s) {
	            out.println("SQL BUGOU AQUI: " + s.toString() + " "+ s.getErrorCode() + " " + s.getSQLState());
	        } catch (Exception e) {
	            out.println("JABU GRANDE AQUI: " + e.toString()+ e.getMessage());
	        }
	    out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}