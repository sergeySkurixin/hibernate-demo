package rs.skurikhin.demo.hibernate;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresTest {
    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Test
    void name() throws SQLException {
        PostgreSQLContainer pg = new PostgreSQLContainer("postgres:9.6.12")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");

        pg.start();

        String jdbcUrl = pg.getJdbcUrl();
        log.info("PG: {}", pg);
        log.info("host: {}", pg.getHost());
        log.info("url: {}", jdbcUrl);
        log.info("user: {}", pg.getUsername());
        log.info("password: {}", pg.getPassword());

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername("sa");
        config.setPassword("sa");
        HikariDataSource ds = new HikariDataSource(config);
        PreparedStatement ps = ds.getConnection().prepareStatement("SELECT 1");
        ResultSet rs = ps.executeQuery();
        rs.next();
        String res = rs.getString(1);
        System.out.println("Res: " + res);
    }
}
