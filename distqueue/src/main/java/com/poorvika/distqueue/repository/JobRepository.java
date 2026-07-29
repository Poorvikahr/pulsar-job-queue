package com.poorvika.distqueue.repository;

import com.poorvika.distqueue.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
}
