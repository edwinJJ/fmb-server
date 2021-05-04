package com.server.fmb.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.fmb.entity.Boards;


public interface IBoardQueryManager extends JpaRepository<Boards, UUID> {

	@Query(value = "SELECT u.* FROM boards u ORDER BY u.name DESC OFFSET ?1 LIMIT ?2", nativeQuery = true)
	public List<Boards> getBoards(@Param("start") int start, @Param("end") int end);
	
//	일부 데이터만 가져오는 로직 (UUID 이슈로 보류)
//	@Query(value = "SELECT t.id, t.name, t.thumbnail, t.description FROM boards t WHERE t.id IN (?1)", nativeQuery = true)
//	public List<Map<String, Object>> getBoardsByRoutingIds(@Param("routingIds") List<UUID> routingIds);
	@Query(value = "SELECT t.* FROM boards t WHERE t.id IN (?1)", nativeQuery = true)
	public List<Boards> getBoardsByRoutingIds(@Param("routingIds") List<UUID> routingIds);
	
	@Query(value = "SELECT t.* FROM boards t WHERE t.group_id = ?1", nativeQuery = true)
	public List<Boards> getBoardsByGroupId(@Param("groupId") UUID groupId);
	
}
