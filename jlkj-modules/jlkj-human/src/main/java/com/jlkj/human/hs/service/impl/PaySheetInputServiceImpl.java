package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PaySheetInput;
import com.jlkj.human.hs.mapper.PaySheetInputMapper;
import com.jlkj.human.hs.service.IPaySheetInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 各公司薪给输入Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-05-21
 */
@Service
public class PaySheetInputServiceImpl implements IPaySheetInputService
{
    @Autowired
    private PaySheetInputMapper paySheetInputMapper;

    /**
     * 查询各公司薪给输入
     *
     * @param uuid 各公司薪给输入主键
     * @return 各公司薪给输入
     */
    @Override
    public PaySheetInput selectPaySheetInputByUuid(String uuid)
    {
        return paySheetInputMapper.selectPaySheetInputByUuid(uuid);
    }

    /**
     * 查询各公司薪给输入列表
     *
     * @param paySheetInput 各公司薪给输入
     * @return 各公司薪给输入
     */
    @Override
    public List<PaySheetInput> selectPaySheetInputList(PaySheetInput paySheetInput)
    {
        return paySheetInputMapper.selectPaySheetInputList(paySheetInput);
    }

    /**
     * 新增各公司薪给输入
     *
     * @param paySheetInputList 各公司薪给输入
     * @return 结果
     */
    @Override
    public int insertPaySheetInput(List<PaySheetInput> paySheetInputList) {
        for (PaySheetInput paySheetInput : paySheetInputList) {
            if (paySheetInput.getUuid() != null) {
                paySheetInput.setCreatorId(SecurityUtils.getUserId().toString());
                paySheetInput.setCreator(SecurityUtils.getNickName());
                paySheetInput.setCreateDate(new Date());
                paySheetInputMapper.updatePaySheetInput(paySheetInput);
            } else {
                paySheetInput.setUuid(UUID.randomUUID().toString().substring(0, 32));
                paySheetInputMapper.insertPaySheetInput(paySheetInput);
            }
        }
        return 1;
    }

    /**
     * 修改各公司薪给输入
     *
     * @param paySheetInput 各公司薪给输入
     * @return 结果
     */
    @Override
    public int updatePaySheetInput(PaySheetInput paySheetInput)
    {
        return paySheetInputMapper.updatePaySheetInput(paySheetInput);
    }

    /**
     * 批量删除各公司薪给输入
     *
     * @param uuids 需要删除的各公司薪给输入主键
     * @return 结果
     */
    @Override
    public int deletePaySheetInputByUuids(String[] uuids)
    {
        return paySheetInputMapper.deletePaySheetInputByUuids(uuids);
    }

    /**
     * 删除各公司薪给输入信息
     *
     * @param uuid 各公司薪给输入主键
     * @return 结果
     */
    @Override
    public int deletePaySheetInputByUuid(String uuid)
    {
        return paySheetInputMapper.deletePaySheetInputByUuid(uuid);
    }
}
