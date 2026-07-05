package com.qlhs.server.repository;

import com.qlhs.server.entity.TKB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TKBRepository extends JpaRepository<TKB, Integer> {

    @Query(value = "SELECT * FROM ThoiKhoaBieu WHERE " +
            "(:maLop = '' OR :maLop = 'Tất cả' OR MaLop = :maLop) AND " +
            "(:maMH = '' OR MaMH LIKE %:maMH%) AND " +
            "(:thu = 0 OR Thu = :thu)",
            nativeQuery = true)
    List<TKB> filterTKB(@Param("maLop") String maLop,
                        @Param("maMH") String maMH,
                        @Param("thu") int thu);

    @Query(value = "SELECT DISTINCT MaLop FROM ThoiKhoaBieu", nativeQuery = true)
    List<String> getDistinctMaLop();

    List<TKB> findByMaLop(String maLop);
    List<TKB> findByMaMH(String maMH);
    List<TKB> findByMaPhong(String maPhong);
    List<TKB> findByMaGV(String maGV);
    List<TKB> findByThu(int thu);
}