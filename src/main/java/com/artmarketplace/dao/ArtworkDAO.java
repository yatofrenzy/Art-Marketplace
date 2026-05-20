package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.Artwork;
import com.artmarketplace.utilities.DbConfig;

/**
 * Data Access Object (DAO) representing operations on Artworks.
 * Part of the DAO layer in the MVC architecture.
 * Implements CRUD actions on the artworks database table.
 */
public class ArtworkDAO {

    /**
     * Inserts a new artwork record into the database.
     * 
     * @param artwork The {@link Artwork} object containing details of the artwork to be added.
     * @return {@code true} if inserted successfully; {@code false} otherwise.
     */
    public boolean addArtwork(Artwork artwork) {

        // SQL query to insert artwork records
        String sql =
                "INSERT INTO artworks " +
                "(category_id, title, description, price, image_path) " +
                "VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources auto-closes the connection and prepared statement objects
        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            // Bind values to placeholders
            ps.setInt(1, artwork.getCategoryId());

            ps.setString(2, artwork.getTitle());

            ps.setString(3, artwork.getDescription());

            ps.setDouble(4, artwork.getPrice());

            ps.setString(5, artwork.getImagePath());

            // Execute insert query and check if rows affected > 0
            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    /**
     * Resolves the text name of a category based on its unique integer ID.
     * 
     * @param categoryId The unique identifier of the category.
     * @return The resolved category name string, with spaces replaced by underscores, defaulting to "Others".
     */
    public String getCategoryNameById(int categoryId) {

        String categoryName = "Others";

        // SQL query to fetch category name filtered by category ID
        String sql =
                "SELECT category_name " +
                "FROM categories " +
                "WHERE category_id = ?";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            // Set category ID parameter
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            // Read matched category name
            if(rs.next()) {

                categoryName =
                        rs.getString("category_name");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        // Replace spaces with underscore to make formatting consistent for directory creation or path checks
        categoryName =
                categoryName.replaceAll("\\s+", "_");

        return categoryName;
    }


    /**
     * Retrieves all artwork items stored in the database in descending order of ID.
     * 
     * @return A {@link List} containing all {@link Artwork} objects.
     */
    public List<Artwork> getAllArtworks() {

        List<Artwork> list =
                new ArrayList<>();

        // SQL query to retrieve all artwork records
        String sql =
                "SELECT * FROM artworks " +
                "ORDER BY artwork_id DESC";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()

        ) {

            // Loop through the query results and map each record to an Artwork model
            while (rs.next()) {

                Artwork artwork =
                        new Artwork();

                artwork.setArtworkId(
                        rs.getInt("artwork_id"));

                artwork.setCategoryId(
                        rs.getInt("category_id"));

                artwork.setTitle(
                        rs.getString("title"));

                artwork.setDescription(
                        rs.getString("description"));

                artwork.setPrice(
                        rs.getDouble("price"));

                artwork.setImagePath(
                        rs.getString("image_path"));

                list.add(artwork);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }


    /**
     * Retrieves a single artwork item by its unique ID.
     * 
     * @param artworkId The unique ID of the artwork.
     * @return The matching {@link Artwork} object if found; {@code null} otherwise.
     */
    public Artwork getArtworkById(int artworkId) {

        // SQL query to retrieve a single artwork record matching the ID
        String sql =
                "SELECT * FROM artworks " +
                "WHERE artwork_id = ?";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            // Bind the artwork ID parameter
            ps.setInt(1, artworkId);

            ResultSet rs = ps.executeQuery();

            // Populate the Artwork model from the matched record
            if (rs.next()) {

                Artwork artwork =
                        new Artwork();

                artwork.setArtworkId(
                        rs.getInt("artwork_id"));

                artwork.setCategoryId(
                        rs.getInt("category_id"));

                artwork.setTitle(
                        rs.getString("title"));

                artwork.setDescription(
                        rs.getString("description"));

                artwork.setPrice(
                        rs.getDouble("price"));

                artwork.setImagePath(
                        rs.getString("image_path"));

                return artwork;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    /**
     * Updates an existing artwork record details in the database.
     * 
     * @param artwork The {@link Artwork} object containing updated details.
     * @return {@code true} if update is successful; {@code false} otherwise.
     */
    public boolean updateArtwork(Artwork artwork) {

        // SQL query to modify an artwork record details
        String sql =
                "UPDATE artworks SET " +
                "category_id=?, title=?, description=?, " +
                "price=?, image_path=? " +
                "WHERE artwork_id=?";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            // Bind update parameters
            ps.setInt(1, artwork.getCategoryId());

            ps.setString(2, artwork.getTitle());

            ps.setString(3, artwork.getDescription());

            ps.setDouble(4, artwork.getPrice());

            ps.setString(5, artwork.getImagePath());

            ps.setInt(6, artwork.getArtworkId());

            // Execute the update query and return result status
            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    /**
     * Deletes an artwork record from the database.
     * First deletes matching records in cart_items and order_items tables to prevent foreign key violations.
     * 
     * @param artworkId The unique identifier of the artwork.
     * @return {@code true} if deleted successfully; {@code false} otherwise.
     */
    public boolean deleteArtwork(int artworkId) {

        // SQL statement to clean up associated shopping cart items
        String deleteCartItems =
            "DELETE FROM cart_items WHERE artwork_id = ?";

        // SQL statement to clean up associated order line items
        String deleteOrderItems =
            "DELETE FROM order_items WHERE artwork_id = ?";

        // SQL statement to delete the actual artwork record
        String deleteArtwork =
            "DELETE FROM artworks WHERE artwork_id = ?";

        try (Connection conn = DbConfig.getConnection()) {

            // Execute cart_items deletion
            PreparedStatement ps1 = conn.prepareStatement(deleteCartItems);
            ps1.setInt(1, artworkId);
            ps1.executeUpdate();

            // Execute order_items deletion
            PreparedStatement ps2 = conn.prepareStatement(deleteOrderItems);
            ps2.setInt(1, artworkId);
            ps2.executeUpdate();

            // Execute artwork deletion
            PreparedStatement ps3 = conn.prepareStatement(deleteArtwork);
            ps3.setInt(1, artworkId);

            return ps3.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}