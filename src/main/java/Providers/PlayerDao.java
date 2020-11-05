package Providers;

import Repository.DataBaseConstants;
import ServerModels.Player;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Optional;

public class PlayerDao implements InsertDataAccessMethod<Player>, GetDataAccessMethod<Player, Integer> {

    private static final String searchByIdSqlQuery = "SELECT * FROM " + DataBaseConstants.TableNames.PlayersTableName + " WHERE ID = ?";
    private static final String insertNewPlayerSqlQuery = "INSERT INTO " + DataBaseConstants.TableNames.PlayersTableName
            + "(" + DataBaseConstants.FieldNames.IdFieldName + ") VALUES (?)";

    @Inject
    DataSource dataSource;

    @Override
    public Optional<Player> Get(Integer id) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(searchByIdSqlQuery, new Object[]{id}, (rs) -> {
            if (rs.next())
                return Optional.ofNullable(new Player(rs.getInt(DataBaseConstants.FieldNames.IdFieldName)));
            else
                return Optional.empty();
        });
    }

    @Override
    public void Insert(Player object) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(insertNewPlayerSqlQuery, object.getId());
    }
}
