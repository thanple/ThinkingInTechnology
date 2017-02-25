package com.thanple.thinking.springboot.mybatis;

import com.thanple.thinking.springboot.entity.Email;
import com.thanple.thinking.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Thanple on 2017/2/25.
 */

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> find();

    @Insert("insert into user(name,email) "+
            "values(#{name,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR})")
    int insert(@Param("name")String name, @Param("email")String email);
}
