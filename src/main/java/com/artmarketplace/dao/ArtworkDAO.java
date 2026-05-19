package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.Artwork;
import com.artmarketplace.utilities.DbConfig;

/**
 * Data Access Object (DAO) implementation for the "artwork" database table.
 * <p>
 * This class encapsulates all direct interaction with the persistent database storage 
 * for {@link com.artmarketplace.model.Artwork} entities. It handles low-level 
 * JDBC connections, query execution, mapping tabular SQL row data into Java objects, 
 * and structural exception fallback handling.
 * </p>
 * 
 * @author ACER
 * @version 1.0.0
 * @see com.artmarketplace.model.Artwork
 * @see com.artmarketplace.utilities.DbConfig
 */
public class ArtworkDAO {

    /**
     * Persists a new artwork entry into the persistent database storage.
     * <p>
     * Establishes a connection, configures a pre-compiled SQL insert statement 
     * using positional parameters to secure data integrity against SQL injection vectors, 
     * and maps attributes from the entity object to the database fields.
     * </p>
     * 
     * @param artwork the {@link Artwork} instance containing data fields to be added; must not be null.
     * @return {@code true} if the database insertion successfully altered 1 or more rows; 
     *         {@code false} if the execution failed or zero rows were affected.
     */
    public boolean addArtwork(Artwork artwork) {
    	// Defines the parameterizable SQL statement for the target columns
        String sql = "INSERT INTO artworks (category_id, title, description, price, image_path) VALUES (?, ?, ?, ?, ?)";


        // Try-with-resources resource management pattern:
        // Automatically manages and releases heavy database connection resources,
        // ensuring connections do not leak even if runtime exceptions occur.
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	// Step 1: Bind Java domain model properties to the index-based SQL parameters
            ps.setInt(1, artwork.getCategoryId());
            ps.setString(2, artwork.getTitle());
            ps.setString(3, artwork.getDescription());
            ps.setDouble(4, artwork.getPrice());
            ps.setString(5, artwork.getImagePath());

            // Step 2: Execute the DML command statement inside the database engine.
            // executeUpdate() yields an integer detailing total records written.
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
        	// Intercepts structural database faults, connectivity limits, or schema mismatches
            e.printStackTrace();
        }

     // Returns fallback safe failure state if exception intercepted
        return false;
    }
    
    
    /**
     * Scans the persistent data layer to compile an active catalog of all recorded artwork.
     * <p>
     * Generates a structural query ordered dynamically by the primary key identifier 
     * in descending sequence to highlight newly listed artwork first.
     * </p>
     * 
     * @return a {@link java.util.List} containing fully built {@link Artwork} domain instances; 
     *         returns an initialized empty list if zero records exist in the database table.
     */
    public List<Artwork> getAllArtworks() {
    	// Instantiate the holding array structure to preserve ordered records
        List<Artwork> list = new ArrayList<>();
        String sql = "SELECT * FROM artworks ORDER BY artwork_id DESC";

     // Open transaction pipeline context across connection, statement, and pointer cursor
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

        	// Sequential pointer loop processing table entries systematically line-by-line
            while (rs.next()) {
            	// Instantiates a clean Java context entity container per record line
                Artwork artwork = new Artwork();
                
             // Extract structural data from individual column labels and map to properties
                artwork.setArtworkId(rs.getInt("artwork_id"));
                artwork.setCategoryId(rs.getInt("category_id"));
                artwork.setTitle(rs.getString("title"));
                artwork.setDescription(rs.getString("description"));
                artwork.setPrice(rs.getDouble("price"));
                artwork.setImagePath(rs.getString("image_path"));

             // Queue the populated Java instance back to the operational array list
                list.add(artwork);
            }

        } catch (Exception e) {
        	// Prints the failure trace details to standard error streams for diagnostics
            e.printStackTrace();
        }

     // Returns populated list array matrix object reference back to caller context
        return list;
    }

    /**
     * Looks up an individual artwork reference matching an exact primary key criterion.
     * <p>
     * Safely isolates query tasks using database parameters to dynamically search for 
     * a single table row with the specified identifier.
     * </p>
     * 
     * @param artworkId the exact database integer unique identifier assigned to the required entry.
     * @return a populated {@link Artwork} entity matching the record key; 
     *         returns {@code null} if no corresponding record exists in the system database.
     */
    public Artwork getArtworkById(int artworkId) {
        String sql = "SELECT * FROM artworks WHERE artwork_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	// Map the method argument variable onto the targeted structural filter constraint
            ps.setInt(1, artworkId);
            ResultSet rs = ps.executeQuery();

         // Evaluate if data cursor finds a matching row in the result set
            if (rs.next()) {
                Artwork artwork = new Artwork();
                
             // Pull field metrics from current active cursor reference index 
                artwork.setArtworkId(rs.getInt("artwork_id"));
                artwork.setCategoryId(rs.getInt("category_id"));
                artwork.setTitle(rs.getString("title"));
                artwork.setDescription(rs.getString("description"));
                artwork.setPrice(rs.getDouble("price"));
                artwork.setImagePath(rs.getString("image_path"));

             // Return domain instance to avoid unnecessary memory lookups
                return artwork;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

     // Returns safe default indicating search query matched zero records
        return null;
    }

    /**
     * Modifies historical database attributes on an existing persistent artwork entry.
     * <p>
     * Matches the core record via its underlying database identity key and uses an 
     * UPDATE query to replace the old text, numbers, and image paths with the object's current state.
     * </p>
     * 
     * @param artwork the {@link Artwork} domain object representing updated values; 
     *                must contain a valid, pre-existing structural primary key ID field.
     * @return {@code true} if an existing entry matched the identity constraint criteria 
     *         and successfully modified properties; {@code false} if update processing missed targets.
     */
    public boolean updateArtwork(Artwork artwork) {
    	// Construct standard parameterized update directive target structure
        String sql = "UPDATE artworks SET category_id=?, title=?, description=?, price=?, image_path=? WHERE artwork_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	// Step 1: Assign plod variables to dynamic modification attributes
            ps.setInt(1, artwork.getCategoryId());
            ps.setString(2, artwork.getTitle());
            ps.setString(3, artwork.getDescription());
            ps.setDouble(4, artwork.getPrice());
            ps.setString(5, artwork.getImagePath());
         // Step 2: Establish lookup targeting constraint parameters to locate the exact record
            ps.setInt(6, artwork.getArtworkId());

         // Step 3: Run the write task and track row alterations
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Removes an artwork entry from the persistent database.
     * <p>
     * **Warning**: This action executes a permanent delete operation on the database table row. 
     * Ensure cascading rules or application confirmations are handled safely before calling this logic.
     * </p>
     * 
     * @param artworkId the unique identifier of the artwork record targeted for deletion.
     * @return {@code true} if a record matching the unique ID was found and deleted; 
     *         {@code false} if no row was removed from the database.
     */
    public boolean deleteArtwork(int artworkId) {
        String sql = "DELETE FROM artworks WHERE artwork_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	// Map target dynamic value safely to key parameters position wrapper
            ps.setInt(1, artworkId);
         // Execute the system state deletion mutation engine task
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<Artwork> getAllArtworks() {

        List<Artwork> artworks = new ArrayList<>();

        String sql = "SELECT * FROM artworks";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Artwork art = new Artwork();

                art.setArtworkId(rs.getInt("artwork_id"));
                art.setTitle(rs.getString("title"));
                art.setDescription(rs.getString("description"));
                art.setPrice(rs.getDouble("price"));
                art.setImagePath(rs.getString("image_path"));
                art.setCategoryId(rs.getInt("category_id"));

                artworks.add(art);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return artworks;
    }
}