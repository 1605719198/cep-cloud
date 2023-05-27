package com.jlkj.human.hs.service;

import com.jlkj.human.hs.domain.PaySheetInput;

import java.util.List;

/**
 * 各公司薪给输入Service接口
 * 
 * @author jiangbingchen
 * @date 2023-05-21
 */
public interface IPaySheetInputService 
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
     * @param paySheetInputList 各公司薪给输入
     * @return 结果
     */
    public int insertPaySheetInput(List<PaySheetInput> paySheetInputList);

    /**
     * 修改各公司薪给输入
     * 
     * @param paySheetInput 各公司薪给输入
     * @return 结果
     */
    public int updatePaySheetInput(PaySheetInput paySheetInput);

    /**
     * 批量删除各公司薪给输入
     * 
     * @param uuids 需要删除的各公司薪给输入主键集合
     * @return 结果
     */
    public int deletePaySheetInputByUuids(String[] uuids);

    /**
     * 删除各公司薪给输入信息
     * 
     * @param uuid 各公司薪给输入主键
     * @return 结果
     */
    public int deletePaySheetInputByUuid(String uuid);
}
