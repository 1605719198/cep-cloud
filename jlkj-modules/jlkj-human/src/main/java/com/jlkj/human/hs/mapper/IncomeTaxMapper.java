package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.IncomeTax;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 各公司所得税起征点设定Mapper接口
 * 
 * @author 116519
 * @date 2023-05-16
 */
public interface IncomeTaxMapper 
{
    /**
     * 查询各公司所得税起征点设定
     * 
     * @param uuid 各公司所得税起征点设定主键
     * @return 各公司所得税起征点设定
     */
    public IncomeTax selectIncomeTaxByUuid(String uuid);

    /**
     * 查询各公司所得税起征点设定列表
     * 
     * @param incomeTax 各公司所得税起征点设定
     * @return 各公司所得税起征点设定集合
     */
    public List<IncomeTax> selectIncomeTaxList(IncomeTax incomeTax);

    /**
     * 新增各公司所得税起征点设定
     * 
     * @param incomeTax 各公司所得税起征点设定
     * @return 结果
     */
    public int insertIncomeTax(IncomeTax incomeTax);

    /**
     * 修改各公司所得税起征点设定
     * 
     * @param incomeTax 各公司所得税起征点设定
     * @return 结果
     */
    public int updateIncomeTax(IncomeTax incomeTax);

    /**
     * 删除各公司所得税起征点设定
     * 
     * @param uuid 各公司所得税起征点设定主键
     * @return 结果
     */
    public int deleteIncomeTaxByUuid(String uuid);

    /**
     * 批量删除各公司所得税起征点设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIncomeTaxByUuids(String[] uuids);

    List<IncomeTax> getVersionList(String compId);


    Map<String,Object> selectMaxVersion(String compId);
    public int deleteIncomeTaxByVersion(@Param("compId") String compId,@Param("version") Long version);

}
