package com.example.json_dataset_api.dto;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class RecordRequest {
    private String dataset;
    private JsonNode data; // ✅ Not String
}
