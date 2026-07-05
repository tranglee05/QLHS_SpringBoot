package com.qlhs.server.service;

import com.qlhs.server.entity.TKB;
import com.qlhs.server.repository.MonHocRepository;
import com.qlhs.server.repository.TKBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TKBService {
    @Autowired
    private TKBRepository tkbRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    private void fillTenMH(List<TKB> list) {
        list.forEach(t -> monHocRepository.findById(t.getMaMH())
                .ifPresent(m -> t.setTenMH(m.getTenMH())));
    }

    public List<TKB> getAllTKB() {
        List<TKB> list = tkbRepository.findAll();
        fillTenMH(list);
        return list;
    }

    public List<TKB> filter(String maLop, String maMH, int thu) {
        List<TKB> list = tkbRepository.filterTKB(maLop, maMH, thu);
        fillTenMH(list);
        return list;
    }

    public List<String> getDistinctMaLop() {
        return tkbRepository.getDistinctMaLop();
    }

    public Optional<TKB> getByIdTKB(Integer maTKB) { return tkbRepository.findById(maTKB); }

    public TKB save(TKB tkb) { return tkbRepository.save(tkb); }

    public void delete(Integer maTKB) { tkbRepository.deleteById(maTKB); }

    public boolean existsByIdTKB(Integer maTKB) { return tkbRepository.existsById(maTKB); }

    public boolean isTrungTiet(TKB tkb) {
        return tkbRepository.findAll().stream().anyMatch(t ->
                t.getThu().equals(tkb.getThu()) && (
                        t.getMaLop().equals(tkb.getMaLop()) ||
                                t.getMaGV().equals(tkb.getMaGV()) ||
                                t.getMaPhong().equals(tkb.getMaPhong())
                ) &&
                        !(tkb.getTietBatDau() > t.getTietKetThuc() || tkb.getTietKetThuc() < t.getTietBatDau())
        );
    }
}