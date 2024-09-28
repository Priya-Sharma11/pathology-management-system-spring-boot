package com.example.Pathology.Controller;

import com.example.Pathology.Entity.Sample;
import com.example.Pathology.Service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/samples")
@CrossOrigin(origins = "http://localhost:5173")
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping
    public ResponseEntity<List<Sample>> getAllSamples() {
        List<Sample> samples = sampleService.getAllSamples();
        return ResponseEntity.ok(samples);
    }

    @PostMapping
    public ResponseEntity<Sample> createSample(@RequestBody Sample sample) {
        Sample createdSample = sampleService.createSample(sample);
        return ResponseEntity.ok(createdSample);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sample> updateSample(@PathVariable Long id, @RequestBody Sample sample) {
        Sample updatedSample = sampleService.updateSample(id, sample);
        return ResponseEntity.ok(updatedSample);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        sampleService.deleteSample(id);
        return ResponseEntity.noContent().build();
    }
}
