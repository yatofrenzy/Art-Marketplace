package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.ArtworkDAOInterface;
import com.artmarketplace.model.Artwork;
import com.artmarketplace.utilities.DBConnection;

import java.sql.*;
import java.util.*;

public class ArtworkDAO implements ArtworkDAOInterface {

    public boolean addArtwork(Artwork artwork) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO artwork(title, price, category_id, artist_id, image_path) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, artwork.getTitle());
            ps.setDouble(2, artwork.getPrice());
            ps.setInt(3, artwork.getCategoryId());
            ps.setInt(4, artwork.getArtistId());
            ps.setString(5, artwork.getImagePath());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Artwork> getAllArtworks() {
        List<Artwork> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM artwork";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Artwork a = new Artwork();
                a.setArtworkId(rs.getInt("artwork_id"));
                a.setTitle(rs.getString("title"));
                a.setPrice(rs.getDouble("price"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}