package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.PaySheetInput;

import java.util.List;

/**
 * 各公司薪给输入Mapper接口
 * 
 * @author jiangbingchen
 * @date 2023-05-21
 */
public interface PaySheetInputMapper 
{
    /**
     * 查询各公司薪给输入
     * 
     * @param uuid 各公司薪给输入主键
     * @return 各公司薪给输入
     */
    public PaySheetInput selectPaySheetInputByUuid(String uuid);

    /**
     * 查询各公司薪给输入列表
     * 
     * @param paySheetInput 各公司薪给输入
     * @return 各公司薪给输入集合
     */
    public List<PaySheetInput> selectPaySheetInputList(PaySheetInput paySheetInput);

    /**
     * 新增各公司薪给输入
     * 
     * @param paySheetInput 各公司薪给输入
     * @return 结果
     */
    public int insertPaySheetInput(PaySheetInput paySheetInput);

    /**
     * 修改各公司薪给输入
     * 
     * @param paySheetInput 各公司薪给输入
     * @return 结果
     */
    public int updatePaySheetInput(PaySheetInput paySheetInput);

    /**
     * 删除各公司薪给输入
     * 
     * @param uuid 各公司薪给输入主键
     * @return 结果
     */
    public int deletePaySheetInputByUuid(String uuid);

    /**
     * 批量删除各公司薪给输入
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePaySheetInputByUuids(String[] uuids);
}
