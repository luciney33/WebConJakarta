import config.UrlConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Random;

@WebServlet("/juego")
public class JuegoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(UrlConstants.PAGE_LOGIN_HTML);
            return;
        }

        if (session.getAttribute("numeroSecreto") == null) {
            int numeroSecreto = new Random().nextInt(100) + 1;
            session.setAttribute("numeroSecreto", numeroSecreto);
            session.setAttribute("intentos", 0);
        }

        TemplateEngine engine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        JakartaServletWebApplication application =
                JakartaServletWebApplication.buildApplication(getServletContext());
        IWebExchange exchange = application.buildExchange(request, response);
        WebContext context = new WebContext(exchange);

        context.setVariable("usuario", session.getAttribute("usuario"));
        context.setVariable("intentos", session.getAttribute("intentos"));
        context.setVariable("mensaje", session.getAttribute("mensaje"));

        session.removeAttribute("mensaje");

        engine.process(UrlConstants.TEMPLATE_JUEGO, context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(UrlConstants.PAGE_LOGIN_HTML);
            return;
        }

        int numeroSecreto = (int) session.getAttribute("numeroSecreto");
        int intentos = (int) session.getAttribute("intentos");
        int intento = Integer.parseInt(request.getParameter("numero"));

        intentos++;
        session.setAttribute("intentos", intentos);

        if (intento == numeroSecreto) {
            // comentado porque el método no existe aún
            // guardarEstadistica(session, intentos);

            session.setAttribute("intentosFinales", intentos);
            session.removeAttribute("numeroSecreto");
            session.removeAttribute("intentos");

            response.sendRedirect(UrlConstants.URL_FINISH);
        } else {
            String mensaje = intento < numeroSecreto ? "Demasiado bajo" : "Demasiado alto";
            session.setAttribute("mensaje", mensaje);
            response.sendRedirect(UrlConstants.URL_JUEGO);
        }
    }
}
