package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.human.hs.domain.UnfixedSalary;
import com.jlkj.human.hs.dto.UnfixedSalaryDTO;
import com.jlkj.human.hs.mapper.UnfixedSalaryMapper;
import com.jlkj.human.hs.service.IUnfixedSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 未定薪人员挑选作业Service业务层处理
 *
 * @author 266861
 * @date 2023-06-15
 */
@Service
public class UnfixedSalaryServiceImpl implements IUnfixedSalaryService
{
    @Autowired
    private UnfixedSalaryMapper unfixedSalaryMapper;

    /**
     * 查询未定薪人员挑选作业
     *
     * @param id 未定薪人员挑选作业主键
     * @return 未定薪人员挑选作业
     */
    @Override
    public UnfixedSalary selectUnfixedSalaryById(String id)
    {
        return unfixedSalaryMapper.selectUnfixedSalaryById(id);
    }

    /**
     * 查询未定薪人员挑选作业列表
     *
     * @param dto 未定薪人员挑选作业
     * @return 未定薪人员挑选作业
     */
    @Override
    public List<UnfixedSalary> selectUnfixedSalaryList(UnfixedSalaryDTO dto)
    {
        return unfixedSalaryMapper.selectUnfixedSalaryList(dto);
    }

    /**
     * 查询未定薪人员挑选作业列表(连表)
     *
     * @param dto 未定薪人员挑选作业
     * @return 未定薪集合
     */
    @Override
    public List<UnfixedSalaryDTO> selectUncheckedList(UnfixedSalaryDTO dto){
        return unfixedSalaryMapper.selectUncheckedList(dto);
    }

    /**
     * 保存未定薪人员挑选作业
     *
     * @param unfixedSalaryDTO 未定薪人员挑选作业
     * @return 结果
     */
    @Override
    public int saveUnfixedSalary(UnfixedSalaryDTO unfixedSalaryDTO){
        int result = 0;
        unfixedSalaryMapper.deletePreDataByParam(unfixedSalaryDTO);
        List<UnfixedSalary> list = unfixedSalaryDTO.getList();
        for(UnfixedSalary item :list){
            item.setId(IdUtils.simpleUUID());
            unfixedSalaryMapper.insertUnfixedSalary(item);
            result++;
        }
        return result;
    }

    /**
     * 新增未定薪人员挑选作业
     *
     * @param unfixedSalary 未定薪人员挑选作业
     * @return 结果
     */
    @Override
    public int insertUnfixedSalary(UnfixedSalary unfixedSalary)
    {
        return unfixedSalaryMapper.insertUnfixedSalary(unfixedSalary);
    }

    /**
     * 修改未定薪人员挑选作业
     *
     * @param unfixedSalary 未定薪人员挑选作业
     * @return 结果
     */
    @Override
    public int updateUnfixedSalary(UnfixedSalary unfixedSalary)
    {
        return unfixedSalaryMapper.updateUnfixedSalary(unfixedSalary);
    }

    /**
     * 批量删除未定薪人员挑选作业
     *
     * @param ids 需要删除的未定薪人员挑选作业主键
     * @return 结果
     */
    @Override
    public int deleteUnfixedSalaryByIds(String[] ids)
    {
        return unfixedSalaryMapper.deleteUnfixedSalaryByIds(ids);
    }

    /**
     * 删除未定薪人员挑选作业信息
     *
     * @param id 未定薪人员挑选作业主键
     * @return 结果
     */
    @Override
    public int deleteUnfixedSalaryById(String id)
    {
        return unfixedSalaryMapper.deleteUnfixedSalaryById(id);
    }
}
