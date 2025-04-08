package pd.fin.source;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ConfigDataSource {

    private final DataSource dataSource;

    public ConfigDataSource(DatabaseType databaseType) {
        this.dataSource =
                switch (databaseType) {
                    case MYSQL -> createMysqlDataSource();
                    case POSTGRESQL -> createPostgreSQLDataSource();
                    case SQLITE -> createSQLiteDataSource();

                };
    }

    public DataSource createMysqlDataSource() {
        // aqui simplemente ponemos los datos
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        config.setUsername("user");
        config.setPassword("password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }

    public DataSource createPostgreSQLDataSource() {
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://ep-damp-glade-a87tu69i-pooler.eastus2.azure.neon.tech/test?user=test_owner&password=npg_P4vVgE8ULHCB&sslmode=require");
        config.setUsername("test_owner");
        config.setPassword("npg_P4vVgE8ULHCB");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }

    public DataSource createSQLiteDataSource() {
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:mydb.db");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }

    // getters:

    public DataSource getDataSource() {
        return dataSource;
    }


}
