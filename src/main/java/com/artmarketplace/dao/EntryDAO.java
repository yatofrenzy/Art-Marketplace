
package com.ingcollegeapt.week7twebapp.dao;

import com.ingcollegeapt.week7twebapp.dao.interfaces.EntryDAOInterface;
import com.ingcollegeapt.week7twebapp.model.Entry;
import com.ingcollegeapt.week7twebapp.utilities.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EntryDAO implements EntryDAOInterface {

    private Connection connection;
    private boolean isConnectionError = false;

    //getter for connection error status
    public boolean isIsConnectionError() {
        return isConnectionError;
    }

    // Constructor to pass the database connection
    public EntryDAO() {
        try {
            this.connection = DBConfig.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            isConnectionError = true;
            System.out.println(ex.getLocalizedMessage());
        }
    }

    // Fetch all entries for a specific topic
    @Override
    public ArrayList<Entry> fetchAllEntries(int topicId) {
        ArrayList<Entry> entries = new ArrayList<>();
        String sql = "SELECT * FROM entries WHERE topic_id = ?";
        
        //try-with autocloses the connection on completion
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, topicId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Entry entry = new Entry();
                entry.setId(rs.getInt("id"));
                entry.setTopicId(rs.getInt("topic_id"));
                entry.setTitle(rs.getString("title"));
                entry.setDescription(rs.getString("description"));
                entry.setLink(rs.getString("link"));
                entry.setImage(rs.getString("image"));
                entry.setCreatedAt(rs.getObject("created_at",LocalDateTime.class));
                entry.setUpdatedAt(rs.getObject("updated_at",LocalDateTime.class));
                entries.add(entry);
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return entries;
    }

    // Insert a new entry linked to a specific topic
    @Override
    public boolean insertEntry(Entry entry, int topicId) {
        String sql = "INSERT INTO entries (topic_id, title, description, link, image) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, topicId);
            ps.setString(2, entry.getTitle());
            ps.setString(3, entry.getDescription());
            ps.setString(4, entry.getLink());
            ps.setString(5, entry.getImage());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    // Update an existing entry by its unique ID
    @Override
    public boolean updateEntry(Entry newEntry, int entryId) {
        String sql = "UPDATE entries SET title = ?, description = ?, link = ?, image = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newEntry.getTitle());
            ps.setString(2, newEntry.getDescription());
            ps.setString(3, newEntry.getLink());
            ps.setString(4, newEntry.getImage());
            ps.setInt(5, entryId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    // Delete a specific entry by its unique ID
    @Override
    public boolean deleteEntry(int entryId) {
        String sql = "DELETE FROM entries WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }
}
