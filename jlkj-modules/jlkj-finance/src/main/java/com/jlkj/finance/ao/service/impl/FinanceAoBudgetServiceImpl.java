package com.jlkj.finance.ao.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.ao.dto.FinanceAoBudgetDto;
import com.jlkj.finance.ao.mapper.FinanceAoBudgetMapper;
import com.jlkj.finance.ao.service.IFinanceAoBudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

/**
 * 预算设置Service业务层处理
 *
 * @author jlkj
 * @date 2023-06-16
 */
@Service
public class FinanceAoBudgetServiceImpl implements IFinanceAoBudgetService {
    private static final Logger log = LoggerFactory.getLogger(FinanceAoBudgetServiceImpl.class);
    @Autowired
    private FinanceAoBudgetMapper financeAoBudgetMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询预算设置
     *
     * @param id 预算设置主键
     * @return 预算设置
     */
    @Override
    public FinanceAoBudgetDto selectFinanceAoBudgetById(String id) {
        FinanceAoBudgetDto financeAoBudgetDto = financeAoBudgetMapper.selectFinanceAoBudgetDtoById(id);

        financeAoBudgetDto = financeAoBudgetMapper.selectFinanceAoBudgetDtoMonth(financeAoBudgetDto);
        return financeAoBudgetDto;
    }

    public FinanceAoBudgetDto financeAoBudgetDtoS(List<FinanceAoBudgetDto> financeAoBudgetDtos) {
        FinanceAoBudgetDto financeAoBudgetDto = new FinanceAoBudgetDto();
        for (FinanceAoBudgetDto financeAoBudgetDto1 : financeAoBudgetDtos) {
            if (financeAoBudgetDto1.getJanuaryJanBudget().compareTo(BigDecimal.ZERO) != 0) {
                financeAoBudgetDto.setJanuaryJanBudget(financeAoBudgetDto1.getJanuaryJanBudget());
            }
        }
        return financeAoBudgetDto;
    }

    /**
     * 查询预算设置列表
     *
     * @param financeAoBudgetDto 预算设置
     * @return 预算设置
     */
    @Override
    public List<FinanceAoBudgetDto> selectFinanceAoBudgetList(FinanceAoBudgetDto financeAoBudgetDto) {
        List<FinanceAoBudgetDto> financeAoBudgetDtos = financeAoBudgetMapper.selectFinanceAoBudgetList(financeAoBudgetDto);
        for (FinanceAoBudgetDto financeAoBudgetDto1 : financeAoBudgetDtos) {
            String yarMonth = financeAoBudgetDto1.getYarMonth();
            FinanceAoBudgetDto financeAoBudgetDto2;
            String LastMonth = DateUtils.addMonth(financeAoBudgetDto1.getYarMonth() + "-01", -1);
            financeAoBudgetDto1.setYarMonth(LastMonth.substring(0, 7));
            //上月预算值
            financeAoBudgetDto2 = financeAoBudgetMapper.selectMonth(financeAoBudgetDto1);
            if (financeAoBudgetDto2!=null){
                financeAoBudgetDto1.setJanBudgetLastMonth(null == financeAoBudgetDto2.getJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto2.getJanBudget());
            }else {
                financeAoBudgetDto1.setJanBudgetLastMonth(BigDecimal.ZERO);
            }

            String NextMonth = DateUtils.addMonth(financeAoBudgetDto1.getYarMonth() + "-01", 2);
            financeAoBudgetDto1.setYarMonth(NextMonth.substring(0, 7));

            //下月预算值
            financeAoBudgetDto2 = financeAoBudgetMapper.selectMonth(financeAoBudgetDto1);
            if (financeAoBudgetDto2!=null){
                financeAoBudgetDto1.setJanBudgetNextMonth(null == financeAoBudgetDto2.getJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto2.getJanBudget());
            }else {
                financeAoBudgetDto1.setJanBudgetNextMonth(BigDecimal.ZERO);
            }

            financeAoBudgetDto1.setYarMonth(yarMonth);
        }
        return financeAoBudgetDtos;
    }

    /**
     * 新增预算设置
     *
     * @param financeAoBudgetDto 预算设置
     * @return 结果
     */
    @Override
    public int insertFinanceAoBudget(FinanceAoBudgetDto financeAoBudgetDto) {
        financeAoBudgetDto.setCreateTime(DateUtils.getNowDate());
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        financeAoBudgetDto.setCreateBy(SecurityUtils.getUsername());
        financeAoBudgetDto.setCreateName(SecurityUtils.getNickName());
        int i;
        List<FinanceAoBudgetDto> financeAoBudgetDtoS = financeAoBudgetMapper.selectDuplicateQuery(financeAoBudgetDto);
        if (financeAoBudgetDtoS.size() > 0) {
            throw new ServiceException("预算设置部门编码：" + financeAoBudgetDto.getDeptNo() + "已经存在，不可重复新增！");
        }
        //新增1月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getJanuaryJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-01");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增2月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getFebruaryJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-02");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增3月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getMarchJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-03");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增4月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getAprilJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-04");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增5月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getMarchJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-05");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增6月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getJuneJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-06");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增7月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getJulyJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-07");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增8月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getAugustJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-08");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增9月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getSeptemberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-09");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增10月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getOctoberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-10");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增11月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getNovemberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-11");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);
        //新增12月金额
        financeAoBudgetDto.setJanBudget(financeAoBudgetDto.getDecemberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-12");
        financeAoBudgetDto.setId(IdUtil.randomUUID());
        i = financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto);


        return i;
    }

    /**
     * 修改预算设置
     *
     * @param financeAoBudgetDto 预算设置
     * @return 结果
     */
    @Override
    public int updateFinanceAoBudget(FinanceAoBudgetDto financeAoBudgetDto) {
        financeAoBudgetDto.setUpdateTime(DateUtils.getNowDate());
        financeAoBudgetDto.setUpdateBy(SecurityUtils.getUsername());
        financeAoBudgetDto.setUpdateName(SecurityUtils.getNickName());

        int i;

        //修改1月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getJanuaryJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getJanuaryJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-01");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改2月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getFebruaryJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getFebruaryJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-02");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改3月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getMarchJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getMarchJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-03");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改4月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getAprilJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getAprilJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-04");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改5月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getMarchJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getMarchJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-05");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改6月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getJuneJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getJuneJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-06");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改7月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getJulyJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getJulyJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-07");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改8月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getAugustJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getAugustJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-08");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改9月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getSeptemberJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getSeptemberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-09");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改10月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getOctoberJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getOctoberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-10");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改11月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getNovemberJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getNovemberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-11");
        i = financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        //修改12月金额
        financeAoBudgetDto.setJanBudget(null == financeAoBudgetDto.getDecemberJanBudget() ? BigDecimal.ZERO : financeAoBudgetDto.getDecemberJanBudget());
        financeAoBudgetDto.setYarMonth(financeAoBudgetDto.getYear() + "-12");
        financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDto);
        return i;
    }

    /**
     * 批量删除预算设置
     *
     * @param financeAoBudgetDtos 需要删除的预算设置集合
     * @return 结果
     */
    @Override
    public int deleteFinanceAoBudgetByIds(List<FinanceAoBudgetDto> financeAoBudgetDtos) {
        int i = 0;
        for (FinanceAoBudgetDto financeAoBudgetDto : financeAoBudgetDtos) {
            List<FinanceAoBudgetDto> list = financeAoBudgetMapper.selectDuplicateQuery(financeAoBudgetDto);
            i = financeAoBudgetMapper.deleteFinanceAoBudgetList(list);
        }
        return i;
    }

    /**
     * 删除预算设置信息
     *
     * @param id 预算设置主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAoBudgetById(String id) {
        return financeAoBudgetMapper.deleteFinanceAoBudgetById(id);
    }

    /**
     * 导入预算设置信息
     *
     * @param financeAoBudgetDtos 预算设置信息列表
     * @param isUpdateSupport     是否更新支持，如果已存在，则进行更新数据
     * @param operName            操作用户
     * @return 结果
     */
    @Override
    public String importFinanceAaVoucher(List<FinanceAoBudgetDto> financeAoBudgetDtos, Boolean isUpdateSupport, String operName) {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        if (StringUtils.isNull(financeAoBudgetDtos) || financeAoBudgetDtos.size() == 0) {
            throw new ServiceException("预算设置信息不能为空！");
        }
        for (FinanceAoBudgetDto financeAoBudgetDto1 : financeAoBudgetDtos) {
            String tipMsg=financeAoBudgetDto1.getYear()+","+ financeAoBudgetDto1.getYarMonth()+","+financeAoBudgetDto1.getDeptNo()+","+financeAoBudgetDto1.getBudgetType();
            try {
                if (StringUtils.isEmpty(financeAoBudgetDto1.getCompanyId()) || StringUtils.isEmpty(financeAoBudgetDto1.getYear())
                        || StringUtils.isEmpty(financeAoBudgetDto1.getYarMonth()) || StringUtils.isEmpty(financeAoBudgetDto1.getDeptNo())
                        || StringUtils.isEmpty(financeAoBudgetDto1.getBudgetType())) {
                    failureNum++;
                    String msg = "<br/>" + "共" + failureNum  + tipMsg + "公司或年度或年月或部门编码或预算类别栏位存在空值！";
                    failureMsg.append(msg);
                }
                FinanceAoBudgetDto financeAoBudgetDtoS = financeAoBudgetMapper.selectMonth(financeAoBudgetDto1);
                if (financeAoBudgetDtoS == null) {
                    financeAoBudgetDto1.setCreateBy(operName);
                    financeAoBudgetDto1.setCreateTime(DateUtils.getNowDate());
                    financeAoBudgetDto1.setId(IdUtil.randomUUID());
                    financeAoBudgetDto1.setCreateBy(SecurityUtils.getUsername());
                    financeAoBudgetDto1.setCreateName(SecurityUtils.getNickName());
                    financeAoBudgetMapper.insertFinanceAoBudget(financeAoBudgetDto1);
                    successNum++;
                    successMsg.append("<br/>" + "共" + successNum  + tipMsg + " 导入成功");

                } else if (isUpdateSupport) {
                    BeanUtils.copyProperties(financeAoBudgetDto1, financeAoBudgetDtoS);
                    financeAoBudgetDtoS.setUpdateBy(operName);
                    financeAoBudgetMapper.updateFinanceAoBudget(financeAoBudgetDtoS);
                    successNum++;
                    successMsg.append("<br/>" + "共" + successNum  + tipMsg + " 更新成功");
                }
               else  {
                    failureNum++;
                    failureMsg.append("<br/>" + tipMsg + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + "共" + failureNum  + tipMsg + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.append("<br/>" + "导入成功！共 " + successNum + " 条数据" );
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
