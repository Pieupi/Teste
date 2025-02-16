import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Define a codificação
        request.setCharacterEncoding("UTF-8");

        // Recupera os dados enviados pelo formulário
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Configuração do servidor SMTP
        String host = "smtp.gmail.com"; // Servidor SMTP do Gmail
        String username = "upietroguedes@gmail.com"; // Substitua pelo seu e-mail
        String password = "pie11#-@"; // Substitua pela sua senha ou App Password
        String recipient = "upietroguedes@gmail.com"; // E-mail para o qual as respostas serão enviadas

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Cria a sessão com autenticação
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Cria a mensagem de e-mail
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            msg.setSubject("Nova resposta do formulário AmarggieFlop");
            msg.setText("Nome: " + name + "\nEmail: " + email + "\nMensagem: " + message);

            // Envia o e-mail
            Transport.send(msg);

            // Prepara a resposta para o usuário
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Resposta do Formulário</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Obrigado, " + name + "!</h1>");
            out.println("<p>Sua mensagem foi enviada com sucesso.</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
