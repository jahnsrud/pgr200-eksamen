package no.kristiania.pgr200.server;

import no.kristiania.pgr200.core.PropertyReader;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.IOException;


public class DatabaseManager {

    private PropertyReader properties;

    public DatabaseManager() throws IOException {
        this.properties = new PropertyReader();
    }

    public DataSource createDataSource() {

        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUser(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        // Flyway.configure().dataSource(dataSource).baselineOnMigrate(true);
        // Flyway.configure().dataSource(dataSource).load().migrate();

        return dataSource;
    }

}
