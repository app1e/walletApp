package com.ivlev.alexey.repository;

import com.ivlev.alexey.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by aivlev on 1/29/16.
 */

public interface UserRepository extends PagingAndSortingRepository<User, Long>{

    @Query("SELECT CASE WHEN (COUNT (us) > 0)  THEN TRUE ELSE false END FROM User us WHERE us.email = :email")
    boolean existByEmail(@Param("email") String email);
}
