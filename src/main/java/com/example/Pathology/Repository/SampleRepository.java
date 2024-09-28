package com.example.Pathology.Repository;

import com.example.Pathology.Entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<Sample,Long> {

}
