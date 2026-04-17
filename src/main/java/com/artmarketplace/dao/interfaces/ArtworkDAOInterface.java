package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.Artwork;
import java.util.List;

public interface ArtworkDAOInterface {
    boolean addArtwork(Artwork artwork);
    List<Artwork> getAllArtworks();
}