package com.thanple.thinking.springboot.repository;

import com.thanple.thinking.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Thanple on 2017/2/20.
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
}
