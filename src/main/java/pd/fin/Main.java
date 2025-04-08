package pd.fin;

import pd.fin.app.AppServer;
import pd.fin.source.ConfigDataSource;
import pd.fin.source.DatabaseType;

public class Main {
    public static void main(String[] args) throws Exception {
        // Primero toca manejar el config datasource
        var configDataSource = new ConfigDataSource(DatabaseType.POSTGRESQL);
        var appServer = new AppServer(configDataSource);
        appServer.start();
    }
}