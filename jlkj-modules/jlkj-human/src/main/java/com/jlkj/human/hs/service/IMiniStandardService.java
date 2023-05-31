package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.MiniStandard;

import java.util.List;
import java.util.Map;

/**
 * 各公司最低工资标准及生活保障标准设定Service接口
 * 
 * @author jiangbingchen
 * @date 2023-05-24
 */
public interface IMiniStandardService 
{
    /**
     * 查询各公司最低工资标准及生活保障标准设定
     * 
     * @param uuid 各公司最低工资标准及生活保障标准设定主键
     * @return 各公司最低工资标准及生活保障标准设定
     */
    public MiniStandard selectMiniStandardByUuid(String uuid);

    /**
     * 查询各公司最低工资标准及生活保障标准设定列表
     * 
     * @param miniStandard 各公司最低工资标准及生活保障标准设定
     * @return 各公司最低工资标准及生活保障标准设定集合
     */
    public List<MiniStandard> selectMiniStandardList(MiniStandard miniStandard);

    /**
     * 新增各公司最低工资标准及生活保障标准设定
     * 
     * @param miniStandardList 各公司最低工资标准及生活保障标准设定
     * @return 结果
     */
    public int insertMiniStandard(List<MiniStandard> miniStandardList);

    /**
     * 修改各公司最低工资标准及生活保障标准设定
     * 
     * @param miniStandard 各公司最低工资标准及生活保障标准设定
     * @return 结果
     */
    public int updateMiniStandard(MiniStandard miniStandard);

    /**
     * 批量删除各公司最低工资标准及生活保障标准设定
     * 
     * @param uuids 需要删除的各公司最低工资标准及生活保障标准设定主键集合
     * @return 结果
     */
    public int deleteMiniStandardByUuids(String[] uuids);

    /**
     * 删除各公司最低工资标准及生活保障标准设定信息
     * 
     * @param uuid 各公司最低工资标准及生活保障标准设定主键
     * @return 结果
     */
    public int deleteMiniStandardByUuid(String uuid);

    /**
     * 版本号列表
     * @return
     */
    List<Map<String,Long>> getVersionList(String compId);
}
