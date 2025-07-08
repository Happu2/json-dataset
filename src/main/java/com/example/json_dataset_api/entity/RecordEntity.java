package com.example.json_dataset_api.entity;

import com.example.json_dataset_api.converter.JsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "records")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataset;

    @ColumnTransformer(write = "?::jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode data;
}
