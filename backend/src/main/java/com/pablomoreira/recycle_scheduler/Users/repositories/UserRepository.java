package com.pablomoreira.recycle_scheduler.Users.repositories;

import com.pablomoreira.recycle_scheduler.Users.models.UserModel;
import com.pablomoreira.recycle_scheduler.Users.models.UserRoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserDetails findByName(String name);
    UserModel findByEmail(String email);

    @Query("SELECT u FROM UserModel u WHERE " +
            "(LOWER(REPLACE(u.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:searchTerm, ' ', ''), '%')) " +
            "OR LOWER(REPLACE(u.email, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:searchTerm, ' ', ''), '%'))) ")
    List<UserModel> findAllByName(@Param("searchTerm") String searchTerm, Sort sort);

    @Query("SELECT u FROM UserModel u WHERE " +
            "(LOWER(REPLACE(u.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:searchTerm, ' ', ''), '%')) " +
            "OR LOWER(REPLACE(u.email, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:searchTerm, ' ', ''), '%'))) ")
    Page<UserModel> findAllByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT u FROM UserModel u WHERE " +
            "(:search IS NULL OR LOWER(REPLACE(u.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:search, ' ', ''), '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(CASE  WHEN u.role = 'ADMIN' THEN 'ADMINISTRADOR' ELSE 'LOCATARIO' END) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<UserModel> findAllBySearch(@Param("search") String search, Sort sort);



    @Query("SELECT u FROM UserModel u WHERE " +
            "(:search IS NULL OR LOWER(REPLACE(u.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:search, ' ', ''), '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(CASE  WHEN u.role = 'ADMIN' THEN 'ADMINISTRADOR' ELSE 'LOCATARIO' END) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<UserModel> findAllBySearch(
            @Param("search") String search,
            Pageable pageable );
}

