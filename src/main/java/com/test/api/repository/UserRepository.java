package com.test.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.test.api.model.User;
import com.test.api.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User as u inner join UserRole as ur  on u.id =:userId")
    List<User> existsByName(@Param("userId") Long id);

    @Query("FROM User u left join UserRole as ur ON u.id = ur.userId WHERE ur.unitId = :unit_id AND :date  >= ur.validFrom AND (ur.validTo <= :date  OR ur.validTo IS NULL)")
    List<User> request2(@Param("unit_id") int unit_id, @Param("date") LocalDateTime date);

    @Query("FROM UserRole ur where ur.id.userId = :userId")
    List<UserRole> checkuserIduserRole(@Param("userId") int id);
}