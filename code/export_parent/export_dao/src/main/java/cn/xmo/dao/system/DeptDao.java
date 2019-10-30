package cn.xmo.dao.system;

import cn.xmo.domain.system.Dept;
import cn.xmo.domain.system.DeptExample;

import java.util.List;

public interface DeptDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    int insert(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    int insertSelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    List<Dept> selectByExample(DeptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    Dept selectByPrimaryKey(String deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Dept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pe_dept
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Dept record);
}