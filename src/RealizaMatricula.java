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
@WebServlet(name = "RealizaMatricula", urlPatterns = {"/RealizaMatricula"})


public class RealizaMatricula extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/pweb2";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
            Connection conn;
            String materia = request.getParameter("materia");
            String turma = request.getParameter("turma");
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
	            ResultSet rec= st.executeQuery("SELECT * FROM alunos WHERE matricula = "+mat+";");
	            rec.next();
	            	out.println("<b> Bem vindo: <b>"+rec.getString(2)+"<br>");
	            	out.println("<b> Matricula: <b>"+rec.getString(1));
	            	out.println("<b>  Código Do Curso: "+rec.getString(3)+"</b><br>");
	            boolean existe = true;
	            
	            rec = st.executeQuery("SELECT * FROM turmas WHERE cod = '"+materia+"';");
	            rec.next();
	            int numTurma = Integer.parseInt(turma.substring(2,3));
	            if(rec.getString(numTurma+3).equals("-")){
	            	out.println("<b>A turma não existe</b><br>");
	            	existe = false;
	            }
	            if(existe){
			        rec= st.executeQuery("SELECT * FROM materiaAluno WHERE martricula = "+mat+";");
			        rec.next();
			        int i = 2;
			        boolean flag = true;
			        boolean pass = true;
			        while(i < 7){
			        	if(!rec.getString(i).equals(materia+turma)){
				        	if(rec.getString(i).equals("-") && flag){
				        		st.executeUpdate("UPDATE materiaAluno SET materia"+(i-1)+" = '"+materia+turma+"' "
				        				+ "WHERE martricula = "+mat+";");
				        		out.println("<b>O aluno foi matriculado com sucesso</b><br>");
				        		i = 7;
				        		pass = false;
				        	}
			        	}
			        	else{
			        		out.println("<b>O aluno já possui matricula nesta disciplina</b><br>");
			        		flag = false;
			        	}
			        	i++;
			        }
			        if(!flag && !pass){
			        	out.println("<b>O aluno já está matriculado em 5 disciplinas!</b><br>");
			        }
	        }
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