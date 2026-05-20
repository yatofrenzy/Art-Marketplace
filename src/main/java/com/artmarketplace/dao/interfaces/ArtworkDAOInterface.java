package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.Artwork;
import java.util.List;

/**
 * Interface defining Data Access Object (DAO) operations for Artwork entity management.
 * Part of the DAO (Data Access Object) layer contract.
 * Describes methods for adding and retrieving artworks to/from the database.
 */
public interface ArtworkDAOInterface {

    /**
     * Inserts a new artwork record into the database.
     * 
     * @param artwork The {@link Artwork} object containing details of the artwork.
     * @return {@code true} if successfully added; {@code false} otherwise.
     */
    boolean addArtwork(Artwork artwork);

    /**
     * Retrieves all available artworks stored in the database.
     * 
     * @return A {@link List} of all {@link Artwork} models.
     */
    List<Artwork> getAllArtworks();
}