import no.kristiania.pgr200.core.Talk;
import no.kristiania.pgr200.server.TalkDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {

    private DataSource dataSource = createDataSource();

    /**
     * Inserts a talk, gets its ID, and then uses getTalkById() to find the right talk
     */

    @Test
    public void shouldRetrieveTalkById() throws SQLException {

        Talk talk = createTalk();

        System.out.println("Creating:");
        System.out.println(talk);

        TalkDao dao = new TalkDao(dataSource);
        dao.create(talk);

        System.out.println("Found:");
        Talk checkTalk = dao.getTalkById(1);
        System.out.println(checkTalk);

        assertThat(talk.getId()).isEqualTo(checkTalk.getId());


    }

    /**
     * Inserts 3 talks – and expects to get 3 back from the DB
     */

    @Test
    public void canInsertTalks() throws SQLException {

        Talk talk1 = createTalk();
        Talk talk2 = createTalk();
        Talk talk3 = createTalk();

        TalkDao dao = new TalkDao(dataSource);

        dao.dropTable();

        dao.create(talk1);
        dao.create(talk2);
        dao.create(talk3);

        assertThat(dao.listAll().size()).isEqualTo(3);

    }

    private Talk createTalk() {

        Talk talk = new Talk(generateRandomName(), "Description", "Topic");
        return talk;

    }

    private String generateRandomName() {

        String prefix[] = {
                "Intro to",
                "Advanced",
                "Formal",
                "Higher order",
                "Expert's guide to"
        };

        String title[] = {
                "Maven",
                "Java 11",
                "PGR200",
                "Flyway",
                "JDBC",
                "Postgresql"
        };


        int rand1 = new Random().nextInt(prefix.length);
        int rand2 = new Random().nextInt(title.length);

        return prefix[rand1] + " " + title[rand2];
    }
    
   

    private DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        // Flyway.configure().dataSource(dataSource).load().migrate();


        return dataSource;

    }

}
