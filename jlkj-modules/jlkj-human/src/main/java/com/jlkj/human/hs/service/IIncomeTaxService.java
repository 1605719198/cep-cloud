package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.IncomeTax;

import java.util.List;
import java.util.Map;

/**
 * 各公司所得税起征点设定Service接口
 * 
 * @author 116519
 * @date 2023-05-16
 */
public interface IIncomeTaxService 
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
     * 批量删除各公司所得税起征点设定
     * 
     * @param uuids 需要删除的各公司所得税起征点设定主键集合
     * @return 结果
     */
    public int deleteIncomeTaxByUuids(String[] uuids);

    /**
     * 删除各公司所得税起征点设定信息
     * 
     * @param uuid 各公司所得税起征点设定主键
     * @return 结果
     */
    public int deleteIncomeTaxByUuid(String uuid);

    /**
     * 版本号列表
     * @return
     */
    List<Map<String,Long>> getVersionList(String compId);
}
