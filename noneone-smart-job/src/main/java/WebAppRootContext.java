
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebAppRootContext implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        servletContext.addListener(WebAppRootListener.class);
        servletContext.setInitParameter("quartWebUser","lifeifei:lifeifei");
        servletContext.setInitParameter("resourcePath","/html");
        servletContext.setAttribute("quartzweb","/quartzweb/*");
    } 
}