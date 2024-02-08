package net.serg.springfoundation.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Component
@Endpoint(id = "appinfo")
@RequiredArgsConstructor
public class AppInfoEndpoint {

    private final Environment environment;
    private final DataSource dataSource;

    @ReadOperation
    public Map<String, String> getAppInfo() throws SQLException {
        String activeProfiles = String.join(",", environment.getActiveProfiles());
        String dbUrl = dataSource.getConnection().getMetaData().getURL();
        return Map.of("activeProfiles", activeProfiles, "dbUrl", dbUrl);
    }

}