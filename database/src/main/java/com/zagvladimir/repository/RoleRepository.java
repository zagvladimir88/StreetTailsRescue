package com.zagvladimir.repository;

import com.zagvladimir.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("select r from Role r inner join r.users u where u.id = :userId")
    List<Role> findRolesByUserid(int userId);
}
