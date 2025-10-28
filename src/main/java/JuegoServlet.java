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
            session.setAttribute("inicioPartida", System.currentTimeMillis());
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
        int numIntento = Integer.parseInt(request.getParameter("numero"));

        intentos++;
        session.setAttribute("intentos", intentos);

        if (numIntento == numeroSecreto) {
            crearPartida(session, intentos);
            session.removeAttribute("numeroSecreto");
            session.removeAttribute("intentos");
            response.sendRedirect(UrlConstants.URL_FINISH);
        } else {
            String mensaje = numIntento < numeroSecreto ? "Demasiado bajo" : "Demasiado alto";
            session.setAttribute("mensaje", mensaje);
            response.sendRedirect(UrlConstants.URL_JUEGO);
        }
    }

    private void crearPartida(HttpSession session, int intentos) {
        String usuario = (String) session.getAttribute("usuario");
        long inicioPartida = (long) session.getAttribute("inicioPartida");
        long tiempoJuego = System.currentTimeMillis() - inicioPartida;

        Estadistica estadistica = Estadistica.obtenerOCrearEstadistica(usuario);

        int puntuacion = 100 - (intentos * 5);
        Partida partida = new Partida(puntuacion, intentos, tiempoJuego);
        estadistica.agregarPartida(partida);

        session.setAttribute("intentosFinales", intentos);
    }
}
