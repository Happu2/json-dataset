package com.example.json_dataset_api.repository;

import com.example.json_dataset_api.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    List<RecordEntity> findByDataset(String dataset);
}
