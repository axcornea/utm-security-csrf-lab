package md.utm.security.security;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class TomcatSameSiteCookieProcessorCustomizer
        implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory server) {
        server.getTomcatContextCustomizers().add(context -> {
            Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor() {

                @Override
                public String generateHeader(Cookie cookie, HttpServletRequest request) {
                    String serializedCookie = super.generateHeader(cookie, request);

                    // Allow setting SameSite cookie policy of 'None'
                    if (!serializedCookie.contains("Secure")) {
                        serializedCookie = serializedCookie + "; Secure";
                    }

                    return serializedCookie;
                }
            };
            cookieProcessor.setSameSiteCookies("None");
            context.setCookieProcessor(cookieProcessor);
        });
    }
}