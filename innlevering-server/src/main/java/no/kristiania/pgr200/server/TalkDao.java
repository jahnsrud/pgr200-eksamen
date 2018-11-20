package no.kristiania.pgr200.server;

import no.kristiania.pgr200.core.Talk;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TalkDao {

    private DataSource dataSource;

    public TalkDao(DataSource dataSource) {

        this.dataSource = dataSource;

    }

    /**
     * Creates our table if it does not already exist
     */

    public void createTableIfNotExists() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().executeUpdate("create table if not exists TALKS (id serial primary key, TITLE varchar, DESCRIPTION text, TOPIC text)");
        }
    }

    public void create(Talk talk) throws SQLException {

        createTableIfNotExists();

        try (Connection conn = dataSource.getConnection()) {

            // Preventing SQL injections
            String sql = "insert into TALKS (TITLE, DESCRIPTION, TOPIC) values (?, ?, ?)";

            String generatedColumns[] = { "id" };
            try (PreparedStatement statement = conn.prepareStatement(sql, generatedColumns)) {
                statement.setString(1, talk.getTitle());
                statement.setString(2, talk.getDescription());
                statement.setString(3, talk.getTopic());

                statement.executeUpdate();


                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        talk.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating talk failed, no ID obtained.");
                    }
                }

            }

        }

    }

    /*
    Method for dropping our table – if it exists
    */
    public void dropTable() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "drop table if exists TALKS";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.executeUpdate();
            }


        }
    }
    /**
     * Coming soon:
     *
     */
    // private Talk mapTalk()



    public List<Talk> listAll() throws SQLException {

        List<Talk> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
    		String sql = "select * from TALKS";
    		try (PreparedStatement statement = conn.prepareStatement(sql)) {
    			try (ResultSet rs = statement.executeQuery()) {
    				while (rs.next()) {
    					Talk talk = new Talk();
    					talk.setTitle(rs.getString("title"));
    					talk.setDescription(rs.getString("description"));
    					talk.setTopic(rs.getString("topic"));
    					talk.setId(rs.getInt("id"));

    					result.add(talk);

    				}
    			}
    		}
    	}

    	return result;

    }

    public List<Talk> listAllTalksByTopic(String topic) throws SQLException {

        List<Talk> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sql = "select * from TALKS where topic=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, topic);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Talk talk = new Talk();
                        talk.setTitle(rs.getString("title"));
                        talk.setDescription(rs.getString("description"));
                        talk.setTopic(rs.getString("topic"));
                        talk.setId(rs.getInt("id"));

                        result.add(talk);

                    }
                }
            }
        }

        return result;

    }


    /**
     *  Gets talk from conference_talk where ID = ?
     */
    public Talk getTalkById(int id) throws SQLException{

    	try (Connection connection = dataSource.getConnection()) {
    		String sql = "select * from TALKS where id = ?";

    		try (PreparedStatement statement = connection.prepareStatement(sql)) {
    		    statement.setString(1, "" + id);
    			try (ResultSet resultSet = statement.executeQuery()) {
    				while (resultSet.next()) {

    					Talk talk = new Talk();
    					talk.setId(resultSet.getInt("id"));
    					talk.setTitle(resultSet.getString("title"));
    					talk.setDescription(resultSet.getString("description"));
    					talk.setTopic(resultSet.getString("topic"));

    					return talk;
    				}
    			}
    		}
    	}
    	
    	return null;
    }



}
