package com.example.Pathology.Controller;

import com.example.Pathology.Entity.Test;
import com.example.Pathology.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin("http://localhost:5173/")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Test>> getAllTests() {
        System.out.println("Fetching all tests");
        List<Test> tests = testService.getAllTest();
        System.out.println("Tests fetched: " + tests.size());
        return ResponseEntity.ok(tests);
    }

    //get by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Optional<Test>> getTestById(@PathVariable Long id){
        Optional<Test> test = testService.getTestById(id);
        return ResponseEntity.ok(test);
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Test> createTest(@RequestBody Test test){
        Test createdTest = testService.createTest(test);
        return ResponseEntity.ok(createdTest);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Test> updateTest(@PathVariable Long id, @RequestBody Test test) {
        Test updatedTest = testService.updateTest(id, test);
        return ResponseEntity.ok(updatedTest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }


}
