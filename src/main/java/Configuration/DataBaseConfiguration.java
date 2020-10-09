package Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import java.sql.*;

public class DataBaseConfiguration {

    public static void Configure()
    {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(org.h2.Driver.load());
        dataSource.setUrl("jdbc:h2:./ChessBotDB");

        try {
            Connection con = dataSource.getConnection();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT 1+1");
            if (rs.next()) {

                System.out.println(rs.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
