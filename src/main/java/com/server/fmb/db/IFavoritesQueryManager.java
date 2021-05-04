package com.server.fmb.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.fmb.entity.Favorites;

public interface IFavoritesQueryManager extends JpaRepository<Favorites, UUID> {

}
