package com.example.json_dataset_api.service;

import com.example.json_dataset_api.dto.RecordResponse;
import com.example.json_dataset_api.entity.RecordEntity;
import com.example.json_dataset_api.repository.RecordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final RecordRepository repository;
    private final ObjectMapper mapper = new ObjectMapper(); // ✅ ObjectMapper instance

    public RecordResponse insertRecord(String dataset, JsonNode json) {
        RecordEntity saved = repository.save(
                RecordEntity.builder()
                        .dataset(dataset)
                        .data(json)
                        .build()
        );
        return new RecordResponse(saved.getId(), saved.getDataset(), saved.getData());
    }



    public Map<String, List<JsonNode>> groupBy(String dataset, String groupByKey) {
        return repository.findByDataset(dataset).stream()
                .map(RecordEntity::getData) // 👈 already JsonNode
                .filter(json -> json.has(groupByKey))
                .collect(Collectors.groupingBy(json -> json.get(groupByKey).asText()));
    }

    public List<JsonNode> sortBy(String dataset, String sortByKey, boolean asc) {
        Comparator<JsonNode> comparator = Comparator.comparing(json -> json.get(sortByKey).asText());
        if (!asc) comparator = comparator.reversed();

        return repository.findByDataset(dataset).stream()
                .map(RecordEntity::getData)
                .filter(json -> json.has(sortByKey))
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    // ✅ Convert raw JSON string to JsonNode safely
    private JsonNode convertToJsonNode(String jsonString) {
        try {
            return mapper.readTree(jsonString); // 👈 This is where you use it
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
