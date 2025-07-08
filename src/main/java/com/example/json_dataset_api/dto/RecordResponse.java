package com.example.json_dataset_api.dto;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordResponse {
    private Long id;
    private String dataset;
    private JsonNode data;
}
