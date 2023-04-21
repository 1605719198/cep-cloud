package com.jlkj.human.hd.mapper;

import java.util.List;
import com.jlkj.human.hd.domain.OrgClassMaster;

/**
 * 出勤身份Mapper接口
 * 
 * @author 266861
 * @date 2023-04-12
 */
public interface OrgClassMasterMapper 
{
    /**
     * 查询出勤身份
     * 
     * @param id 出勤身份主键
     * @return 出勤身份
     */
    public OrgClassMaster selectOrgClassMasterById(String id);

    /**
     * 查询出勤身份列表
     * 
     * @param orgClassMaster 出勤身份
     * @return 出勤身份集合
     */
    public List<OrgClassMaster> selectOrgClassMasterList(OrgClassMaster orgClassMaster);

    /**
     * 新增出勤身份
     * 
     * @param orgClassMaster 出勤身份
     * @return 结果
     */
    public int insertOrgClassMaster(OrgClassMaster orgClassMaster);

    /**
     * 修改出勤身份
     * 
     * @param orgClassMaster 出勤身份
     * @return 结果
     */
    public int updateOrgClassMaster(OrgClassMaster orgClassMaster);

    /**
     * 删除出勤身份
     * 
     * @param id 出勤身份主键
     * @return 结果
     */
    public int deleteOrgClassMasterById(String id);

    /**
     * 批量删除出勤身份
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrgClassMasterByIds(String[] ids);
}
