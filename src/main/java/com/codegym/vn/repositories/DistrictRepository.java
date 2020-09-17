package com.codegym.vn.repositories;

import com.codegym.vn.models.house.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District , Long> {
    List<District> findAllByProvinceProvinceName(String provinceName);
}