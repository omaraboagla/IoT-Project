package com.iotproject.iotproject.Repo;

import com.iotproject.iotproject.Entity.Setting;
import com.iotproject.iotproject.Enum.SettingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {
    List<Setting> findByType(SettingType type);
    Setting findTopByOrderByCreatedAtDesc();

}

