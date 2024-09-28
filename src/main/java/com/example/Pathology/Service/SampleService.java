package com.example.Pathology.Service;

import com.example.Pathology.Entity.Sample;
import com.example.Pathology.Repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    public List<Sample> getAllSamples() {
        return sampleRepository.findAll();
    }

    public Sample createSample(Sample sample) {
        return sampleRepository.save(sample);
    }

    public Sample updateSample(Long id, Sample sample) {
        sample.setId(id);
        return sampleRepository.save(sample);
    }

    public void deleteSample(Long id) {
        sampleRepository.deleteById(id);
    }
}
