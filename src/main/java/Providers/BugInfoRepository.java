package Providers;

import Repository.DataBaseConstants;
import ServerModels.BugInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;

public class BugInfoRepository implements InsertDataAccessMethod<BugInfo> {

    @Inject
    private DataSource dataSource;

    private static final String insertQuery = "INSERT INTO " + DataBaseConstants.TableNames.BugReportTable
            + "(" + DataBaseConstants.FieldNames.UserId + ", " + DataBaseConstants.FieldNames.Message + ") VALUES ( ?, ?)";

    @Override
    public void Insert(BugInfo object) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(insertQuery, object.getUserId(), object.getMessage());
    }
}
