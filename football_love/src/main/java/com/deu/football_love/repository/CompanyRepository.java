package com.deu.football_love.repository;

import com.deu.football_love.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    List<Company> findAllByName(@Param("Name") String companyName);
}
