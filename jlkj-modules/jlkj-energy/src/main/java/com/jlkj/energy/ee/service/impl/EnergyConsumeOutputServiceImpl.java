package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.BeanCopyUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.dto.energy.ee.EnergyConsumeOutputDTO;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.domain.EnergyConsumeOutput;
import com.jlkj.energy.ee.domain.EnergyCostOutput;
import com.jlkj.energy.ee.domain.EnergyTransData;
import com.jlkj.energy.ee.mapper.EnergyCodeMapper;
import com.jlkj.energy.ee.mapper.EnergyConsumeOutputMapper;
import com.jlkj.energy.ee.service.EnergyConsumeOutputService;
import com.jlkj.energy.ee.service.EnergyCostOutputService;
import com.jlkj.energy.ee.service.EnergyTransDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 265675
 * 针对表【t_energy_consume_output(能源项目基本资料)】的数据库操作Service实现
 * @Date: 2022-05-05 08:33:10
 */
@Service
@Slf4j
public class EnergyConsumeOutputServiceImpl extends ServiceImpl<EnergyConsumeOutputMapper, EnergyConsumeOutput>
        implements EnergyConsumeOutputService {

    @Autowired
    private EnergyCodeMapper energyCodeMapper;
    @Autowired
    private EnergyTransDataService energyTransDataService;
    @Autowired
    private EnergyCostOutputService energyCostOutputService;
    @Autowired
    private EnergyConsumeOutputMapper energyConsumeOutputMapper;

    /**
     * 成本中心日能耗资料查询
     *
     * @Author: 265800
     * @Date: 2023/7/7 9:54
     * @Param: [energyConsumeOutputDTO] 查询条件
     * @Return: com.jlkj.common.core.web.domain.AjaxResult  查询结果
     */
    @Override
    public List<EnergyConsumeOutput> queryEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        // 构造条件
        LambdaQueryWrapper<EnergyConsumeOutput> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(energyConsumeOutputDTO.getECountDateStart())
                && StringUtils.isNotBlank(energyConsumeOutputDTO.getECountDateEnd())) {
            queryWrapper.between(EnergyConsumeOutput::getECountDate, energyConsumeOutputDTO.getECountDateStart(),
                    energyConsumeOutputDTO.getECountDateEnd());
        }
        if (StringUtils.isNotBlank(energyConsumeOutputDTO.getCostCenter1())
                && StringUtils.isNotBlank(energyConsumeOutputDTO.getCostCenter2())) {
            queryWrapper.between(EnergyConsumeOutput::getCostCenter, energyConsumeOutputDTO.getCostCenter1(),
                    energyConsumeOutputDTO.getCostCenter2());
        } else if (StringUtils.isNotBlank(energyConsumeOutputDTO.getCostCenter1())) {
            queryWrapper.eq(EnergyConsumeOutput::getCostCenter, energyConsumeOutputDTO.getCostCenter1());
        } else if (StringUtils.isNotBlank(energyConsumeOutputDTO.getCostCenter2())) {
            queryWrapper.eq(EnergyConsumeOutput::getCostCenter, energyConsumeOutputDTO.getCostCenter2());
        }
        if (StringUtils.isNotBlank(energyConsumeOutputDTO.getEngyIdStart())
                && StringUtils.isNotBlank(energyConsumeOutputDTO.getEngyIdEnd())) {
            queryWrapper.between(EnergyConsumeOutput::getEngyId, energyConsumeOutputDTO.getEngyIdStart(),
                    energyConsumeOutputDTO.getEngyIdEnd());
        } else if (StringUtils.isNotBlank(energyConsumeOutputDTO.getEngyIdStart())) {
            queryWrapper.eq(EnergyConsumeOutput::getEngyId, energyConsumeOutputDTO.getEngyIdStart());
        } else if (StringUtils.isNotBlank(energyConsumeOutputDTO.getEngyIdEnd())) {
            queryWrapper.eq(EnergyConsumeOutput::getEngyId, energyConsumeOutputDTO.getEngyIdEnd());
        }
        return energyConsumeOutputMapper.selectList(queryWrapper);
    }

    /**
     * 查询成本中心日能耗资料能源代码下拉选单
     *
     * @Author: 265800
     * @Date: 2023/7/7 10:09
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> queryDropDownMenu() {
        List<EnergyConsumeOutput> list = getBaseMapper().selectList(null);
        List<String> list1 = new ArrayList<>();
        for (EnergyConsumeOutput energyConsumeOutput : list) {
            list1.add(energyConsumeOutput.getEngyId());
        }
        return list1;
    }

    /**
     * 接收报文执行每日能源产耗资料新增
     *
     * @Author: 265800
     * @Date: 2023/7/7 11:36
     * @Param: [energyConsumeOutputDTO]
     * @Return: com.jlkj.common.core.web.domain.AjaxResult
     */
    @Override
    public int addEnergyConsumeOutput(EnergyConsumeOutput energyConsumeOutput) {
        int result = 0;
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(energyConsumeOutput.getEngyId()), EnergyCode::getEngyId,
                energyConsumeOutput.getEngyId());
        Map<String, Object> map = energyCodeMapper.selectMaps(queryWrapper).get(0);
        if(!StringUtils.isEmpty(map)){
            energyConsumeOutput.setEngyType(map.get("engy_type").toString() == null ? "" : map.get("engy_type").toString() );
            energyConsumeOutput.setAcctSys(map.get("acct_sys").toString() == null ? "" : map.get("acct_sys").toString());
            energyConsumeOutput.setEngyUnit(map.get("engy_unit").toString() == null ? "" : map.get("engy_unit").toString());
        }else {
            new ServiceException("未查询到" + energyConsumeOutput.getEngyId() + "的能源代码配置信息。");
        }
        if (energyConsumeOutput.getCostCenter().startsWith(Constants.ENERGY_W5)) {
            energyConsumeOutput.setIoTypeId(Constants.ENERGY_S);
        }
        energyConsumeOutput.setCalUnit(Constants.ENERGY_CAL_UNIT);
        // 报文类型为N（新增）
        if (Constants.ENERGY_N.equals(energyConsumeOutput.getExecJobFunc())) {
            LambdaQueryWrapper<EnergyConsumeOutput> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(EnergyConsumeOutput::getCostCenter, energyConsumeOutput.getCostCenter())
                    .eq(EnergyConsumeOutput::getECountDate, energyConsumeOutput.getECountDate())
                    .eq(EnergyConsumeOutput::getEngyId, energyConsumeOutput.getEngyId())
                    .eq(EnergyConsumeOutput::getIoTypeId, energyConsumeOutput.getIoTypeId());
            List<EnergyConsumeOutput> list1 = energyConsumeOutputMapper.selectList(queryWrapper1);
            if (!list1.isEmpty()) {
                log.info(Constants.ENERGY_ERROR_MSG, energyConsumeOutput);
                new ServiceException(Constants.ENERGY_ERROR_MSG);
                this.sendMessage(energyConsumeOutput, Constants.ENERGY_ERROR_MSG);
            }
            result = energyConsumeOutputMapper.insert(energyConsumeOutput);
        } else {
            if (Constants.ENERGY_R.equals(energyConsumeOutput.getExecJobFunc())) {
                updateEnergyConsumeOutput(energyConsumeOutput);
                energyConsumeOutput.setExecJobFunc(Constants.ENERGY_N);
                // 非重复资料才新增t_energy_consume_output，同时抛IP
                result = energyConsumeOutputMapper.insert(energyConsumeOutput);
            }
            // 若为冲销资料，在IP/AM已月关帐时，不可冲销IP/AM
            if (!"88".equals(energyConsumeOutput.getECountDate().substring(6, 8))) {
                if (Constants.ENERGY_D.equals(energyConsumeOutput.getExecJobFunc()) &&
                        Constants.ENERGY_IP.equals(energyConsumeOutput.getAcctSys()) ||
                        Constants.ENERGY_AMIP.equals(energyConsumeOutput.getAcctSys())) {
                    // 在IP/AM已月关帐时，写到异常档
                    this.sendMessage(energyConsumeOutput, Constants.ENERGY_ERROR_MSG_CLOSE);
                    new ServiceException(Constants.ENERGY_ERROR_MSG_CLOSE);
                } else {
                    if (energyConsumeOutput.getProConQty().compareTo(BigDecimal.ZERO) != 0) {
                        transToIP(energyConsumeOutput);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 异常资讯公共方法
     */
    private void sendMessage(EnergyConsumeOutput energyConsumeOutput, String msg) {
        EnergyTransData energyTransData = new EnergyTransData();
        energyTransData.setCompId("J00");
        energyTransData.setTransDate(String.valueOf(new Date()).substring(0, 8));
        energyTransData.setTransTime(String.valueOf(new Date()).substring(0, 6));
        energyTransData.setTransData(energyConsumeOutput.getMessageInfo());
        energyTransData.setErrMsg(msg);
        energyTransDataService.save(energyTransData);
    }

    /**
     * 将资料抛送到IP，同时新增到t_energy_cost_output
     * (回收量及能源抛帐系统为IP，才抛)
     *
     * @param energyConsumeOutput
     * @since 2023/02/13
     */
    public void transToIP(EnergyConsumeOutput energyConsumeOutput) {
        String dataTypeIdxP = "", dataTypeIdxC = "", activityIdR = "", activityIdP = "", activityIdC = "";
        //   能源抛帐系统为IP才抛(耗用量不抛IP)
        if ((Constants.ENERGY_IP.equals(energyConsumeOutput.getAcctSys())
                || Constants.ENERGY_AMIP.equals(energyConsumeOutput.getAcctSys()))
                && (Constants.ENERGY_R.equals(energyConsumeOutput.getIoTypeId())
                || Constants.ENERGY_B.equals(energyConsumeOutput.getIoTypeId())
                || Constants.ENERGY_X.equals(energyConsumeOutput.getIoTypeId()))) {
            // 回收量
            if (Constants.ENERGY_R.equals(energyConsumeOutput.getIoTypeId())) {
                //	ip回收资料的产出代码
                activityIdR = "RECYCLE";
                //	ip回收耗用资料的耗用代码
                activityIdC = "RECYCLE_CONSUMPTION";
                //	ip产出资料的产出代码
                activityIdP = "RECYCLE_PRODUCE";
                // 依 execJobFunc决定 dataTypeIdx
                if (Constants.ENERGY_N.equals(energyConsumeOutput.getExecJobFunc()) || Constants.ENERGY_R.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "N";
                    dataTypeIdxC = "O";
                } else if (Constants.ENERGY_D.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "O";
                    dataTypeIdxC = "N";
                }
                //一律改成抛成本中心
                energyConsumeOutput.setChargeFactoryId(energyConsumeOutput.getChargeCostCenter());
                energyConsumeOutput.setFactoryId(energyConsumeOutput.getCostCenter());
                //  抛送IP正项资料--需抛卖方产线
                createTBEE10(energyConsumeOutput, dataTypeIdxP, activityIdR);
                //  抛送IP负项资料 --需抛买方产线
                createTBEE10(energyConsumeOutput, dataTypeIdxC, activityIdC);
                if (!("DZS201").equals(energyConsumeOutput.getEngyId())) {
                    //  抛送IP正项资料--需抛卖方产线
                    createTBEE10(energyConsumeOutput, dataTypeIdxP, activityIdP);
                }
                // 外购量
            } else if (Constants.ENERGY_B.equals(energyConsumeOutput.getIoTypeId())) {
                //	ip外购资料的外购代码
                activityIdR = "BUY";
                //	ip外购领用资料的领用代码
                activityIdC = "BUY_CONSUMPTION";
                // 依 execJobFunc决定 dataTypeIdx
                if (Constants.ENERGY_N.equals(energyConsumeOutput.getExecJobFunc()) || Constants.ENERGY_R.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "N";
                    dataTypeIdxC = "O";
                } else if (Constants.ENERGY_D.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "O";
                    dataTypeIdxC = "N";
                }
                //一律改成抛成本中心
                energyConsumeOutput.setChargeFactoryId(energyConsumeOutput.getChargeCostCenter());
                energyConsumeOutput.setFactoryId(energyConsumeOutput.getCostCenter());
                //  抛送IP正项资料--需抛卖方产线
                createTBEE10(energyConsumeOutput, dataTypeIdxP, activityIdR);
                //  抛送IP负项资料 --需抛买方产线
                createTBEE10(energyConsumeOutput, dataTypeIdxC, activityIdC);
                // 结转产量
            } else if (Constants.ENERGY_X.equals(energyConsumeOutput.getIoTypeId())) {
                //	ip结转产量代码
                activityIdR = "IP_PRODUCE";
                // 依 execJobFunc决定 dataTypeIdx
                if (Constants.ENERGY_N.equals(energyConsumeOutput.getExecJobFunc()) || Constants.ENERGY_R.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "N";
                } else if (Constants.ENERGY_D.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "O";
                }
                //一律改成抛成本中心
                energyConsumeOutput.setChargeFactoryId(energyConsumeOutput.getChargeCostCenter());
                energyConsumeOutput.setFactoryId(energyConsumeOutput.getCostCenter());
                //  抛送IP正项资料--需抛卖方产线
                createTBEE10(energyConsumeOutput, dataTypeIdxP, activityIdR);
            }
        }
    }

    /**
     * 新增t_energy_cost_output
     *
     * @param energyConsumeOutput
     * @since 2023/02/13
     */
    private void createTBEE10(EnergyConsumeOutput energyConsumeOutput, String dataTypeIdx, String activityId) {
        //将资料新增入t_energy_cost_output表
        EnergyCostOutput energyCostOutput = new EnergyCostOutput();
        BeanCopyUtils.copy(energyConsumeOutput,energyCostOutput);
        //  财务用能源单位
        energyCostOutput.setEngyUnit(energyConsumeOutput.getEngyUnitF());
        //  交易资料指示码(N/O)
        energyCostOutput.setDataTypeIdx(dataTypeIdx);
        // 生产活动(C/P)
        energyCostOutput.setActivityId(activityId);
        //  当前产线别
        energyCostOutput.setCrntMill(energyConsumeOutput.getFactoryId());
        energyCostOutput.setAcctDate(energyConsumeOutput.getECountDate());
        // 当前产线别
        energyCostOutput.setFactoryId(energyConsumeOutput.getFactoryId());
        if (Constants.ENERGY_D.equals(energyConsumeOutput.getExecJobFunc())) {
            BigDecimal proConQty = BigDecimal.ZERO.subtract(energyConsumeOutput.getProConQty());
            //  能量值
            energyCostOutput.setProConQty(proConQty);
        } else {
            //  能量值
            energyCostOutput.setProConQty(energyConsumeOutput.getProConQty());
        }
        energyCostOutputService.save(energyCostOutput);
    }

    /**
     * 当执行作业代码为"R"时（冲销），自行产生的一笔"D"的资料做冲销
     *
     * @param energyConsumeOutput
     * @Author: 111191
     * @Date: 2023年7月8日, 0008 上午 08:33:42
     */
    @Log(title = "当执行作业代码为\"R\"时（冲销），自行产生的一笔\"D\"的资料做冲销", businessType = BusinessType.INSERT)
    public void updateEnergyConsumeOutput(EnergyConsumeOutput energyConsumeOutput) {
        String chargeCostCenter = energyConsumeOutput.getChargeCostCenter();
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(energyConsumeOutput.getEngyId()),
                EnergyCode::getEngyId, energyConsumeOutput.getEngyId());
        Map<String, Object> map = energyCodeMapper.selectMaps(queryWrapper).size() == 0 ? null :
                energyCodeMapper.selectMaps(queryWrapper).get(0);
        energyConsumeOutput.setEngyType(map.get("engy_type").toString());
        energyConsumeOutput.setAcctSys(map.get("acct_sys").toString());
        energyConsumeOutput.setEngyUnit(map.get("engy_unit").toString());
        QueryWrapper<EnergyConsumeOutput> queryWrapperA = new QueryWrapper<>();
        queryWrapperA.select("IFNULL(sum(pro_con_qty),0) as proConQty", "IFNULL(sum(cal_value),0) as calValue",
                        "IFNULL(sum(engy_value),0) as engyValue")
                .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(energyConsumeOutput.getEngyId()),
                        "engy_id", energyConsumeOutput.getEngyId())
                .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(energyConsumeOutput.getECountDate())
                        , "e_count_date",
                        energyConsumeOutput.getECountDate())
                .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(energyConsumeOutput.getCostCenter())
                        , "cost_center",
                        energyConsumeOutput.getCostCenter())
                .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(energyConsumeOutput.getIoTypeId()),
                        "io_type_id", energyConsumeOutput.getIoTypeId())
                .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(chargeCostCenter),
                        "charge_cost_center", chargeCostCenter);
        Map<String, Object> mapA = getBaseMapper().selectMaps(queryWrapperA).size() == 0 ? null :
                getBaseMapper().selectMaps(queryWrapperA).get(0);
        BigDecimal sumProConQty = new BigDecimal(mapA.get("proConQty") == null ? "0" :
                mapA.get("proConQty").toString()).multiply(new BigDecimal(-1));
        BigDecimal sumCalValue = new BigDecimal(mapA.get("calValue") == null ? "0" :
                mapA.get("calValue").toString());
        BigDecimal sumEngyValue = new BigDecimal(mapA.get("engyValue") == null ? "0" :
                mapA.get("engyValue").toString()).multiply(new BigDecimal(-1));
        energyConsumeOutput.setProConQty(sumProConQty);
        energyConsumeOutput.setCalValue(sumCalValue);
        energyConsumeOutput.setEngyValue(sumEngyValue);
        if (energyConsumeOutput.getCostCenter().startsWith(Constants.ENERGY_W5)) {
            energyConsumeOutput.setIoTypeId(Constants.ENERGY_S);
        }
        energyConsumeOutput.setCalUnit(Constants.ENERGY_CAL_UNIT);
        energyConsumeOutput.setExecJobFunc(Constants.ENERGY_D);
        energyConsumeOutputMapper.insert(energyConsumeOutput);
    }
}




