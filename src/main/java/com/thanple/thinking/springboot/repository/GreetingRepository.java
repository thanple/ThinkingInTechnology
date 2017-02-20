package com.thanple.thinking.springboot.repository;

import com.thanple.thinking.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Thanple on 2017/2/20.
 */

@Transactional
public interface GreetingRepository extends JpaRepository<User,Long>{
}
