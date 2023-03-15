package com.jlkj.human.hm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.human.hm.domain.Organization;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 机构数据 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-05-05
 */
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 查询所有机构资料
     * @return
     */
    @Select("SELECT id, organization_name, parent_id, parent_name FROM t_humanresource_organization WHERE 1=1")
    List<Organization> listAll();
}
