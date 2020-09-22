package com.test.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.test.api.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("FROM UserRole as u Where u.userId = :user_id and u.unitId = :unit_id")
    List<UserRole> finUserUserUnitId(@Param("user_id") int user_id, @Param("unit_id") int unit_id);

    @Query("FROM UserRole as u Where u.userId = :user_id AND u.unitId = :unit_id and u.validFrom <= :date And (u.validTo >= :date OR u.validTo is null)")
    List<UserRole> finUserUnitIdDate(@Param("user_id") int user_id, @Param("unit_id") int unit_id, @Param("date") LocalDateTime date);

    @Query("FROM UserRole as ur join User as u on ur.unitId = :unit_id")
    List<UserRole> finUserUnitId(@Param("unit_id") int unit_id);
}