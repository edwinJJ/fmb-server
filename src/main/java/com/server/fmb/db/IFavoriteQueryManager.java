package com.server.fmb.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Favorites;

public interface IFavoriteQueryManager extends JpaRepository<Favorites, UUID> {

	@Modifying
	@Query(value = "DELETE FROM favorites t WHERE t.routing = ?1", nativeQuery = true)
	public void deleteByRoutingId(@Param("boardId") String boardId);
	
}
