package com.deu.football_love.repository;

import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.dto.company.QueryCompanyDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

  List<Company> findAllByName(@Param("Name") String companyName);

  @Query("select new com.deu.football_love.dto.company.QueryCompanyDto(c)" +
      " from Company c" +
      " join c.owner" +
      " where c.owner.number = :memberNumber")
  Optional<QueryCompanyDto> findQueryCompanyDto(@Param("memberNumber") Long memberNumber);

  @Query("SELECT c FROM Company c JOIN c.owner m WHERE m.number=:memberNumber")
  List<Company> findWithPagingByMemberNumber(@Param("memberNumber") Long memberNumber, Pageable pageable);

  default boolean existsByMemberNumber(Long memberNumber) {
    return findWithPagingByMemberNumber(memberNumber, PageRequest.of(0, 1)).size() == 1 ? true : false;
  }


}
