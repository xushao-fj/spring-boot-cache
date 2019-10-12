package com.xsm.cache.mapper;

import com.xsm.cache.po.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author xsm
 * @Date 2019/10/11 22:02
 */
@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id = #{id}")
    Department getDeptById(Long id);
}
