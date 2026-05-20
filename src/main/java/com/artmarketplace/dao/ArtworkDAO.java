package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.Artwork;
import com.artmarketplace.utilities.DbConfig;

public class ArtworkDAO {

    // =========================================
    // ADD ARTWORK
    // =========================================

    public boolean addArtwork(Artwork artwork) {

        String sql =
                "INSERT INTO artworks " +
                "(category_id, title, description, price, image_path) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            ps.setInt(1, artwork.getCategoryId());

            ps.setString(2, artwork.getTitle());

            ps.setString(3, artwork.getDescription());

            ps.setDouble(4, artwork.getPrice());

            ps.setString(5, artwork.getImagePath());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================================
    // GET CATEGORY NAME BY ID
    // =========================================

    public String getCategoryNameById(int categoryId) {

        String categoryName = "Others";

        String sql =
                "SELECT category_name " +
                "FROM categories " +
                "WHERE category_id = ?";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                categoryName =
                        rs.getString("category_name");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        // Replace spaces with underscore
        categoryName =
                categoryName.replaceAll("\\s+", "_");

        return categoryName;
    }


    // =========================================
    // GET ALL ARTWORKS
    // =========================================

    public List<Artwork> getAllArtworks() {

        List<Artwork> list =
                new ArrayList<>();

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


    // =========================================
    // GET ARTWORK BY ID
    // =========================================

    public Artwork getArtworkById(int artworkId) {

        String sql =
                "SELECT * FROM artworks " +
                "WHERE artwork_id = ?";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            ps.setInt(1, artworkId);

            ResultSet rs = ps.executeQuery();

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


    // =========================================
    // UPDATE ARTWORK
    // =========================================

    public boolean updateArtwork(Artwork artwork) {

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

            ps.setInt(1, artwork.getCategoryId());

            ps.setString(2, artwork.getTitle());

            ps.setString(3, artwork.getDescription());

            ps.setDouble(4, artwork.getPrice());

            ps.setString(5, artwork.getImagePath());

            ps.setInt(6, artwork.getArtworkId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // =========================================
    // DELETE ARTWORK
    // =========================================

    public boolean deleteArtwork(int artworkId) {

        String sql =
                "DELETE FROM artworks " +
                "WHERE artwork_id = ?";

        try (

            Connection conn = DbConfig.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)

        ) {

            ps.setInt(1, artworkId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}