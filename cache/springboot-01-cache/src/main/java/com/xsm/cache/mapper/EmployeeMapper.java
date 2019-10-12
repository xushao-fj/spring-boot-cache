package com.xsm.cache.mapper;

import com.xsm.cache.po.Employee;
import org.apache.ibatis.annotations.*;

/**
 * @author xsm
 * @Date 2019/10/11 22:02
 */
@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee getEmById(Long id);

    @Update("update employee set lastName=#{lastName}, email={email}, gender=#{gender}, d_id=#{dId} where id=#{id}")
    public void updateEm(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public void deleteEmById(Long id);

    @Insert("insert into employee(lastName, email, gender, d_id) values(#{lastName}, #{email},#{gender},#{d_id})")
    public void insertEm(Employee employee);

    @Select("select * from employee where lastName = #{lastName}")
    Employee getEmpByLastName(String lastName);
}
