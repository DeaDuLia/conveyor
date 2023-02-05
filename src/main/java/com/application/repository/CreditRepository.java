package com.application.repository;


import com.application.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {

    @Query (
            value = "SELECT * FROM credit",
            nativeQuery = true
    )
    List<Credit> getAll();

}
