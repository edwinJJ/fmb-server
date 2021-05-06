package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Attachments;


public interface IAttachmentQueryManager extends JpaRepository<Attachments, UUID> {

	@Query(value = "SELECT u.* FROM attachments u ORDER BY u.name DESC OFFSET ?1 LIMIT ?2", nativeQuery = true)
	public List<Attachments> getAttachments(@Param("start") int start, @Param("end") int end);
	
	@Query(value = "SELECT u.* FROM attachments u WHERE u.category = ?1  ORDER BY u.name DESC OFFSET ?2 LIMIT ?3", nativeQuery = true)
	public List<Attachments> getAttachmentsByCategory(@Param("category") String category,@Param("start") int start, @Param("end") int end);

}
