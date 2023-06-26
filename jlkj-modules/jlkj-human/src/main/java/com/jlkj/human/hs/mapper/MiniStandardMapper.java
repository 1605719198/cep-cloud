package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.MiniStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 各公司最低工资标准及生活保障标准设定Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-05-24
 */
public interface MiniStandardMapper 
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
     * @param miniStandard 各公司最低工资标准及生活保障标准设定
     * @return 结果
     */
    public int insertMiniStandard(MiniStandard miniStandard);

    /**
     * 修改各公司最低工资标准及生活保障标准设定
     * 
     * @param miniStandard 各公司最低工资标准及生活保障标准设定
     * @return 结果
     */
    public int updateMiniStandard(MiniStandard miniStandard);

    /**
     * 删除各公司最低工资标准及生活保障标准设定
     * 
     * @param uuid 各公司最低工资标准及生活保障标准设定主键
     * @return 结果
     */
    public int deleteMiniStandardByUuid(String uuid);

    /**
     * 批量删除各公司最低工资标准及生活保障标准设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMiniStandardByUuids(String[] uuids);

    /**
     * 获取版本号
     *
     * @param compId 公司别
     * @return 结果
     */
    List<MiniStandard> getVersionList(String compId);

    /**
     * 获取最大版本号
     *
     * @param compId 公司别
     * @return 结果
     */
    Map<String,Object> selectMaxVersion(String compId);


    /**
     * 删除工资标准数据
     * @Description 删除工资标准数据
     * @param compId 公司别
     * @param version 版本号
     * @return 最低工资标准删除结果
     * @author 267383
     * @Date 2023/6/26 15:35
     **/
    public int deleteMiniStandardByVersion(@Param("compId") String compId, @Param("version") Long version);
}
