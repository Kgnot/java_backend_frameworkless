package pd.fin.app;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.VirtualThreadPool;
import pd.fin.api.UsersApi;
import pd.fin.core.User;
import pd.fin.core.UserService;
import pd.fin.dao.user.UserDAO;
import pd.fin.json.UserSerializer;
import pd.fin.mapper.MapperI;
import pd.fin.source.ConfigDataSource;

import java.util.List;
import java.util.logging.Logger;

public class AppServer {

    private static final Logger log;
    static {
        log = Logger.getLogger(AppServer.class.getName());
        log.info("Starting server...");
    }

    private final Server server;
    private final ConfigDataSource dataSource;

    public AppServer(ConfigDataSource dataSource) {
        this(Integer.parseInt(System.getProperty("API_PORT", "8080")),dataSource);
    }

    public AppServer(int port, ConfigDataSource dataSource) {
        this.dataSource = dataSource;
        server = createServer(port);
    }

    private Server createServer(int port) {
        var server = new Server(new VirtualThreadPool());
        server.setStopAtShutdown(true);
        server.addConnector(createConnector(port, server));
        try {
            server.setHandler(createHandler(createUsersAPI()));
        } catch (Exception exception) {
            System.out.println("xd");
        }
        return server;
    }


    private ServerConnector createConnector(int port, Server server) {
        var connector = new ServerConnector(server);
        connector.setName("main");
        connector.setPort(port);
        return connector;
    }

    private ServletContextHandler createHandler(UsersApi usersApi) {
        var contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.addServlet(new ServletHolder(usersApi), "/");
        contextHandler.setVirtualHosts(List.of("@main"));
        return contextHandler;
    }

    private UsersApi createUsersAPI() {
        System.out.println("datasource: "+this.dataSource);
        var usersDao = new UserDAO(this.dataSource);
        var usersService =
                new UserService(
                        () -> MapperI.entitiesToCores(usersDao.findAll()),
                        (User.UserId userId) ->
                                usersDao.findById(userId.value())
                                        .map(MapperI::entityToCore)
                                        .orElseThrow());
        return new UsersApi(
                UserSerializer::serializerUser,
                UserSerializer::serializerUserList,
                () -> MapperI.coreToDtos(usersService.getAllUsers()),
                (Integer id) ->
                        usersService
                                .getUserById(User.UserId.of(id))
                                .map(MapperI::coreToDto)
                                .orElseThrow());
    }
    // creamos metodos importantes, realmente no los entiendo super, pero voy a intentarlo
    // los metodos mas importantes
    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public int getPort() {
        return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
    }
}
