package com.s2r.smarts2r.repository;

import com.s2r.smarts2r.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.supplier.id = :supplierId")
    Double findAverageScoreBySupplierId(@Param("supplierId") Long supplierId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.supplier.id = :supplierId")
    Long countBySupplierId(@Param("supplierId") Long supplierId);
}
