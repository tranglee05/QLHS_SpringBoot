package com.qlhs.server.repository;

import com.qlhs.server.entity.PhongHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhongHocRepository extends JpaRepository<PhongHoc, String> {

    @Query(value = "SELECT * FROM PhongHoc WHERE " +
            "(:ma = '' OR MaPhong LIKE %:ma%) AND " +
            "(:loai = '' OR :loai = 'Tất cả' OR LoaiPhong = :loai) AND " +
            "(:tinhTrang = '' OR :tinhTrang = 'Tất cả' OR TinhTrang = :tinhTrang)",
            nativeQuery = true)
    List<PhongHoc> searchPhongHoc(@Param("ma") String ma,
                                  @Param("loai") String loai,
                                  @Param("tinhTrang") String tinhTrang);
}