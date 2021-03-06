import AppStart.DataBaseConfiguration;

import Providers.PlayerRepository;
import ServerModels.Player;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerDaoTests {

    private PlayerRepository playerDao;
    private Injector injector = TestEnvironment.CreateInjector();

    @BeforeEach
    public void SetUp() {
        DataBaseConfiguration.Configure(injector.getInstance(DataSource.class));
        playerDao = injector.getInstance(PlayerRepository.class);
    }

    private static Stream<Arguments> providePlayersForInsertGetTest() {
        return Stream.of(
                Arguments.of(new ArrayList<Player>(Arrays.asList(new Player(1, "1", "1")))),
                Arguments.of(new ArrayList<Player>(Arrays.asList(new Player(2, "2", "2"), new Player(3, "3", "3"), new Player(4, "4", "4")))),
                Arguments.of(new ArrayList<Player>(Arrays.asList(new Player(101, "101", "101"), new Player(102, "102", "102"), new Player(103, "", ""), new Player(104, "", ""))))
        );
    }

    @ParameterizedTest
    @MethodSource("providePlayersForInsertGetTest")
    public void PlayerDaoShouldReturnObjectAlreadyInserted(ArrayList<Player> players) {
        for (Player player : players)
            playerDao.Insert(player);

        for (Player player : players) {
            Optional<Player> playerOptional = playerDao.Get(player.getId());
            assertTrue(playerOptional.isPresent());
            assertEquals(playerOptional.get().getId(), player.getId());
        }
    }
}