package com.example.premieres.domain.repository;

import com.example.premieres.domain.entity.Premiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PremiereRepository extends JpaRepository<Premiere,Long> {
    @Query(value = "CALL sp_get_premieres()", nativeQuery = true)
    List<Premiere> getPremieres();
    boolean existsByImageUrl(String imageUrl);
}

