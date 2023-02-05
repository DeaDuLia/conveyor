package com.application.repository;

import com.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    @Query(
            value = "select * from application",
            nativeQuery = true
    )
    List<Application> getAll ();

}
