package com.server.fmb.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.fmb.entity.Connections;

public interface IConnectionQueryManager extends JpaRepository<Connections, UUID> {

}
