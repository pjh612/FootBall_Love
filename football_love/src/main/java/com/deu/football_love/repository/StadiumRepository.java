package com.deu.football_love.repository;

import com.deu.football_love.domain.Stadium;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {

    @Query("SELECT s FROM Stadium s WHERE s.company.id = :companyId")
    List<Stadium> selectAllStadiumByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT s.id as id FROM Stadium s JOIN s.company c WHERE c.id=:companyId")
    List<StadiumId> findAllIdsByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT s FROM Stadium s JOIN s.company c WHERE c.id=:companyId")
    List<Stadium> findAllByCompanyId(@Param("companyId") Long companyId);

    interface StadiumId{
        Long getId();
    }

}
