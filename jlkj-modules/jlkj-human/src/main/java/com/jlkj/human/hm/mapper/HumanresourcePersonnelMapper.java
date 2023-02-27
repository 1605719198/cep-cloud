package com.jlkj.human.hm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hm.domain.HumanresourcePersonnel;
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
public interface HumanresourcePersonnelMapper extends BaseMapper<HumanresourcePersonnel> {
    /**
     * 查找状态为2的有效人员状态
     * @return
     */
    @Select(" SELECT id, job_number, full_name, department_name, alternate_field14 FROM t_humanresource_personnel WHERE status = 2 ")
    List<HumanresourcePersonnel> listAll();

}
