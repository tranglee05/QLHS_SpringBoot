package com.qlhs.server.repository;

import com.qlhs.server.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, String> {

    @Query(value = "SELECT * FROM MonHoc WHERE MaMH LIKE %:keyword% OR TenMH LIKE %:keyword%", nativeQuery = true)
    List<MonHoc> searchMonHoc(@Param("keyword") String keyword);
}
