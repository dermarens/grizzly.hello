package eu.marens.grizzly.hello;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;


/**
 * {@link Server}
 *
 * @author <a href="mailto:marc.arens@open-xchange.com">Marc Arens</a>
 * @since 7.x.y
 */
public class Server {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
            HttpServer httpServer = new HttpServer();
            httpServer.getServerConfiguration().setJmxEnabled(true);
            NetworkListener listener = new NetworkListener("grizzly", NetworkListener.DEFAULT_NETWORK_HOST, 8009);
            httpServer.addListener(listener);

            WebappContext ctx = new WebappContext("hello", "/hello");

            ServletRegistration reg = ctx.addServlet("helloServlet", new HttpServlet() {

                @Override
                protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
                    resp.getOutputStream().write("hello\n".getBytes());
                }
            });

            reg.addMapping("/helloServlet/*");

            ctx.deploy(httpServer);
            httpServer.start();
            System.in.read();

        }
    }
