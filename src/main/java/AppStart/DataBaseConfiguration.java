package AppStart;

import Repository.DataBaseConstants;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataBaseConfiguration {

    public static void Configure(DataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        if (!IsTableExists(DataBaseConstants.TableNames.PlayersTableName, template))
            CreatePlayerTable(template);
    }

    private static final String checkTableExistsSqlQuery = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";

    private static boolean IsTableExists(String TableName, JdbcTemplate template) {
        int flag = template.queryForObject(checkTableExistsSqlQuery, new Object[]{TableName}, Integer.class);
        return flag != 0;
    }

    private static void CreatePlayerTable(JdbcTemplate template) {
        String createPlayerTableSqlQuery = "CREATE TABLE " + DataBaseConstants.TableNames.PlayersTableName + "(" + DataBaseConstants.FieldNames.IdFieldName + " INT PRIMARY KEY)";
        template.update(createPlayerTableSqlQuery);
    }

}
