package Infrastructer;

import java.sql.Driver;

public class DataBaseConnectionInfo {

    private final String ConnectionUri;
    private final Driver ConnectionDriver;

    public DataBaseConnectionInfo(String connectionString,
                                  Driver connectionDriver) {
        ConnectionUri = connectionString;
        ConnectionDriver = connectionDriver;
    }

    public String getConnectionUri() {
        return ConnectionUri;
    }

    public Driver getConnectionDriver() {
        return ConnectionDriver;
    }
}
