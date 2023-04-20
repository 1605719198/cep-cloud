package com.jlkj.human.hm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 人员数据 Mapper 接口
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@Repository
public interface PersonnelMapper extends BaseMapper<Personnel> {
    /**
     * 查找状态为2的有效人员状态
     * @return
     */
    @Select(" SELECT id, job_number, full_name, department_name, alternate_field14 FROM t_humanresource_personnel WHERE status = 2 ")
    List<Personnel> listAll();

    /**
     * 根据条件分页查询用户列表
     *
     * @param deptId 用户信息
     * @return 用户信息集合信息
     */
    List<HumanresourcePersonnelInfoDTO> selectOrgPerson(String deptId);

}
