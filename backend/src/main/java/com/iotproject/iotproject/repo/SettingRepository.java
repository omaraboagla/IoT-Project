package com.iotproject.iotproject.repo;

import com.iotproject.iotproject.entity.Setting;
import com.iotproject.iotproject.enums.SettingType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {
    List<Setting> findByType(SettingType type);
    Setting findTopByOrderByCreatedAtDesc();

}

