package com.example.json_dataset_api.controller;

import com.example.json_dataset_api.dto.RecordRequest;
import com.example.json_dataset_api.dto.RecordResponse;
import com.example.json_dataset_api.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
public class DatasetController {

    private final DatasetService service;

    @PostMapping("/record")
    public ResponseEntity<RecordResponse> insertRecord(@RequestBody RecordRequest request) {
        RecordResponse response = service.insertRecord(request.getDataset(), request.getData());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{datasetName}/query")
    public ResponseEntity<?> queryDataset(
            @PathVariable String datasetName,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        if (groupBy != null) {
            return ResponseEntity.ok(Map.of("groupedRecords", service.groupBy(datasetName, groupBy)));
        }
        if (sortBy != null) {
            boolean asc = order.equalsIgnoreCase("asc");
            return ResponseEntity.ok(Map.of("sortedRecords", service.sortBy(datasetName, sortBy, asc)));
        }
        return ResponseEntity.badRequest().body("Either groupBy or sortBy must be provided");
    }
}
