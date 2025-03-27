package com.es.API_REST_Ez_Learning.repository;

import com.es.API_REST_Ez_Learning.model.Test;
import com.es.API_REST_Ez_Learning.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
