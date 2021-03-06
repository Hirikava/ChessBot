package Providers;

import Repository.DataBaseConstants;
import ServerModels.Match;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class MatchesRepository implements GetSequenceAccessMethod<Match, Integer>, InsertDataAccessMethod<Match> {

    @Inject
    private DataSource dataSource;

    private static final String searchByIdSqlQuery = "SELECT * FROM " + DataBaseConstants.TableNames.MatchesTableName + " WHERE " +
            DataBaseConstants.FieldNames.PlayerId1 + " =  ? or " + DataBaseConstants.FieldNames.PlayerId2 + " = ?";
    private static final String insertQuery = "INSERT INTO " + DataBaseConstants.TableNames.MatchesTableName
            + "(" + DataBaseConstants.FieldNames.PlayerId1 + ", " + DataBaseConstants.FieldNames.PlayerId2 + ", "
            + DataBaseConstants.FieldNames.WinnerId + ") VALUES (?, ?, ?)";

    @Override
    public ArrayList<Match> Get(Integer searchObject) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(searchByIdSqlQuery, new Object[]{searchObject, searchObject}, (rs) -> {
            ArrayList<Match> matches = new ArrayList<Match>();
            while (rs.next())
                matches.add(new Match(rs.getInt("PlayerId1"), rs.getInt("PlayerId2"), rs.getInt("WinnerId")));
            return matches;
        });
    }

    @Override
    public void Insert(Match object) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(insertQuery, object.getPlayerId1(), object.getPlayerId2(), object.getWinnerId());
    }
}
