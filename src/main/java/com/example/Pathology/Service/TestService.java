package com.example.Pathology.Service;

import com.example.Pathology.Entity.Test;
import com.example.Pathology.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public Test createTest(Test test){
        return testRepository.save(test);
    }

    public List<Test> getAllTest(){
        return testRepository.findAll();
    }

    public Test updateTest(Long id, Test test) {
        test.setId(id);
        return testRepository.save(test);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }
}
