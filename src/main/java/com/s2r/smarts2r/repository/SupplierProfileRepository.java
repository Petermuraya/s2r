package com.s2r.smarts2r.repository;

import com.s2r.smarts2r.model.SupplierProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SupplierProfileRepository extends JpaRepository<SupplierProfile, Long> {
	@Query(value = "SELECT * FROM supplier_profiles sp WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(sp.latitude)) * cos(radians(sp.longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(sp.latitude)))) <= :radiusKm ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(sp.latitude)) * cos(radians(sp.longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(sp.latitude))))", nativeQuery = true)
	List<SupplierProfile> findNearby(@Param("lat") double lat, @Param("lon") double lon, @Param("radiusKm") double radiusKm);
}
