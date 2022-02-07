package com.deu.football_love.repository;

import com.deu.football_love.domain.Company;
import com.deu.football_love.dto.company.QueryCompanyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    List<Company> findAllByName(@Param("Name") String companyName);

    @Query( "select new com.deu.football_love.dto.company.QueryCompanyDto(c)" +
            " from Company c" +
            " join c.owner" +
            " where c.owner.number = :memberNumber")
    Optional<QueryCompanyDto> findQueryCompanyDto(@Param("memberNumber") Long memberNumber);
}
