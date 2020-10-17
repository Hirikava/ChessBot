import AppStart.DataBaseConfiguration;

import Providers.PlayerDao;
import ServerModels.Player;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerDaoTests {

    private PlayerDao playerDao;
    private Injector injector = TestEnvironment.CreateInjector();

    @Before
    public void SetUp() {
        DataBaseConfiguration.Configure(injector.getInstance(DataSource.class));
        playerDao = injector.getInstance(PlayerDao.class);
    }

    @Test
    public void PlayerDaoShouldReturnObjectAlreadyInserted() {
        playerDao.Insert(new Player(1));
        Optional<Player> playerOptional = playerDao.Get(1);
        assertTrue(playerOptional.isPresent());
        assertEquals(playerOptional.get().getId(), 1);
    }
}
