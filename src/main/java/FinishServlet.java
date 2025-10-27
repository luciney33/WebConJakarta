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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String usuario = (String) session.getAttribute("usuario");
        int intentos = (int) session.getAttribute("intentosFinales");
        long inicioPartida = (long) session.getAttribute("inicioPartida");
        long tiempoJuego = System.currentTimeMillis() - inicioPartida;

        // Obtener o crear estadística
        LoginServlet loginServlet = (LoginServlet) getServletContext().getAttribute("loginServlet");
        Estadistica estadistica = loginServlet.obtenerEstadistica(usuario);

        if (estadistica == null) {
            estadistica = new Estadistica();
            loginServlet.crearEstadistica(usuario, estadistica);
        }

        // Guardar partida
        int puntuacion = 100 - (intentos * 5);
        estadistica.agregarPartida(new Partida(puntuacion, intentos, tiempoJuego));

        // Limpiar sesión
        session.removeAttribute("inicioPartida");
        session.removeAttribute("intentosFinales");

        // Mostrar pantalla de fin
        TemplateEngine engine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(getServletContext());
        IWebExchange exchange = application.buildExchange(request, response);
        WebContext context = new WebContext(exchange);

        context.setVariable("intentos", intentos);
        context.setVariable("usuario", usuario);
        context.setVariable("puntuacion", puntuacion);

        engine.process(UrlConstants.TEMPLATE_FINISH, context, response.getWriter());
    }
}