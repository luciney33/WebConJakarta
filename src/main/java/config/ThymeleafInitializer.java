package config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(servletContext);

        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        templateResolver.setPrefix(UrlConstantes.TEMPLATE_PREFIX);
        templateResolver.setSuffix(UrlConstantes.TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(ThymeleafConstantes.TEMPLATE_MODE);
        templateResolver.setCharacterEncoding(ThymeleafConstantes.CHARACTER_ENCODING);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        servletContext.setAttribute(ThymeleafConstantes.TEMPLATE_ENGINE_ATTR, templateEngine);
    }
}
