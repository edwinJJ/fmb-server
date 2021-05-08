package com.server.fmb.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.fmb.entity.Scenarios;

public interface IScenarioQueryManager  extends JpaRepository<Scenarios , UUID> {

}
