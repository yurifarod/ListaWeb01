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
@WebServlet(name = "ListaTurmasAluno", urlPatterns = {"/ListaTurmasAluno"})


public class ListaTurmasAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/pweb2";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
            Connection conn;
            String mat = request.getParameter("matricula");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Universidade Federal do CSS > Turmas do Aluno</title>");
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
	            ResultSet rec = st.executeQuery("SELECT * FROM alunos WHERE matricula = "+mat+";");
	            rec.next();
	            	out.println("<b> Bem vindo: <b>"+rec.getString(2)+"<br>");
	            	out.println("<b> Matricula: <b>"+rec.getString(1));
	            	out.println("<b>  Código Do Curso: "+rec.getString(3)+"</b><br>");
                    out.println("<table border=1><tr>");
                    out.println("<td><b>CODIGO</b></td>" + "<td><b>TURMA</b></td>"+"</tr>");
                    out.println("<form action=\"ListaTurmasAluno\"> ");
                rec = st.executeQuery("SELECT * FROM materiaAluno WHERE martricula = "+mat+";");
                rec.next();
                    for(int i = 2; i < 6; i++){
                    	String materia, turma;
                    	if(!rec.getString(i).equals("-")){
                    		materia = rec.getString(i).substring(0, 6);
                    		turma = rec.getString(i).substring(6, 9);
                    		out.println("<td>"+materia+"</td>"+"<td>"+turma+"</td>"+"</tr>");
                    	}
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