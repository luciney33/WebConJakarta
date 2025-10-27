import config.GameConstants;
import config.MessageConstants;
import config.ThymeleafConstants;
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

@WebServlet(UrlConstants.URL_FINISH)
public class FinishServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(UrlConstants.PAGE_LOGIN_HTML);
            return;
        }

        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        context.setVariable("usuario", session.getAttribute("usuario"));
        Integer intentos = (Integer) session.getAttribute(GameConstants.VAR_INTENTOS);
        context.setVariable(GameConstants.VAR_INTENTOS, intentos);

        Integer numeroSecreto = (Integer) session.getAttribute(GameConstants.NUMERO_SECRETO);
        context.setVariable(MessageConstants.VAR_MENSAJE,
                MessageConstants.MSG_GANASTE + numeroSecreto);

        String mensajeMotivacional;
        if (intentos <= 5) {
            mensajeMotivacional = "oleeeee!! maquina";
        } else if (intentos <= GameConstants.MAX_INTENTOS) {
            mensajeMotivacional = "muyy bieen";
        } else {
            mensajeMotivacional = "Sigue practicando!!!";
        }
        context.setVariable("mensajeMotivacional", mensajeMotivacional);

        response.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine) getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(UrlConstants.TEMPLATE_FINISH, context, response.getWriter());
    }
}