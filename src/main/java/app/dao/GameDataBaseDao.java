package app.dao;

import app.entities.Game;
import app.entities.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class GameDataBaseDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public GameDataBaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //write in games table
    public Game createInGameTable(Game game) {
        System.out.println("Creating game In DB Game");
        final String sql = "INSERT INTO games(secretNumbers,isFinished) " +
                "VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getSecretNumbers());
            statement.setBoolean(2, game.isFinished());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    // write in rounds Table
    public Round createInRoundTable(Round round) {
        System.out.println("Creating data in Rounds Table");
        final String sql = "INSERT INTO rounds (gameId, userNumbers, EP) VALUES (?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGameId());
            statement.setString(2, round.getPlayerNumbers());
            statement.setString(3, round.getEP());
            return statement;

        }, keyHolder);

        round.setId(keyHolder.getKey().intValue());

        return round;
    }

    // Display info from games table
    public List<Game> getAllGames() {
        System.out.println("Getting all games");
        final String sql = "SELECT * FROM games;";
        return jdbcTemplate.query(sql, new GameMapper());
    }


// Find game by Id query
    public Game findByIdGamesInDao(int id) {
        System.out.println("Finding game by id...");
        final String sql = "SELECT * "
                + "FROM games WHERE gameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

 public List <Round> findByIdRoundsInDao(int id) {
        System.out.println("Finding Round by game id...");
        final String sql = "SELECT * "
                + "FROM rounds WHERE gameId = ?;";
        return jdbcTemplate.query(sql, new RoundMapper(),id);
    }

    // Display info from rounds query
    public List<Round> findAllRounds() {
        final String sql = "SELECT * "
                + "FROM rounds ";
        return jdbcTemplate.query(sql, new RoundMapper());
    }


    public boolean updateGameFinishStatus(Game game) {
        System.out.println("Updating...");
        final String UPDATE_GAME_FINISH_STATUS = "UPDATE games SET "
                + "secretNumbers = ?, "
                + "isFinished = ? "
                + "WHERE gameId = ?;";

        return jdbcTemplate.update(UPDATE_GAME_FINISH_STATUS,
                game.getSecretNumbers(),
                game.isFinished(),
                game.getGameId()) > 0;
    }

    // Game mapper class
    private static final class GameMapper implements RowMapper<Game> {


        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            // Integer []resInt = new Integer[4];
            game.setGameId(rs.getInt("gameId"));
            game.setGame_time(rs.getTimestamp("game_time"));
            game.setSecretNumbers(rs.getString("secretNumbers"));
            game.setFinished(rs.getBoolean("isFinished"));

           /* for (int i = 0; i< res.length; i++)
            {
                resInt[i] = Integer.parseInt(res[i]);
            }*/
            //game.setCombination(resInt);
            //game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }

    // Round table mapper class
        private static final class RoundMapper implements RowMapper<Round> {

            @Override
            public Round mapRow(ResultSet rs, int index) throws SQLException {
                Round round = new Round();
                round.setId(rs.getInt("roundId"));
                round.setGameId(rs.getInt("gameId"));
                round.setPlayerNumbers(rs.getString("userNumbers"));
                round.setEP(rs.getString("EP"));

                return round;
            }
        }
    }


