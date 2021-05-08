package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Attachments;
import com.server.fmb.entity.Fonts;

public interface IFontQueryManager extends JpaRepository<Fonts, UUID>{

	
	@Query(value = "SELECT u.* FROM fonts u ORDER BY u.name DESC OFFSET ?1 LIMIT ?2", nativeQuery = true)
	public List<Fonts> getFonts(@Param("start") int start, @Param("end") int end);
	
	
	@Query(value = "SELECT u.* FROM fonts u WHERE u.category = ?1  ORDER BY u.name DESC OFFSET ?2 LIMIT ?3", nativeQuery = true)
	public List<Fonts> getFontsByCategory(@Param("category") String category,@Param("start") int start, @Param("end") int end);
}
