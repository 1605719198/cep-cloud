package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author 265675
* @description 针对表【t_energy_consume_output(能源项目基本资料)】的数据库操作Service实现
* @createDate 2022-05-05 08:33:10
*/
@Service
@Slf4j
public class EnergyConsumeOutputServiceImpl extends ServiceImpl<EnergyConsumeOutputMapper, EnergyConsumeOutput>
    implements EnergyConsumeOutputService{

    @Autowired
    private EnergyCodeMapper energyCodeMapper;
    @Autowired
    private EnergyTransDataService energyTransDataService;
    @Autowired
    private EnergyCostOutputService energyCostOutputService;

    /**
     * @description 成本中心日能耗资料查询
     * @author: 265800
     * @date: 2023/7/7 9:54
     * @param: [energyConsumeOutputDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult queryEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        try {
            String eCountDateStart = energyConsumeOutputDTO.getECountDateStart();
            String eCountDateEnd = energyConsumeOutputDTO.getECountDateEnd();
            String costCenter1 = energyConsumeOutputDTO.getCostCenter1();
            String costCenter2 = energyConsumeOutputDTO.getCostCenter2();
            String engyIdStart = energyConsumeOutputDTO.getEngyIdStart();
            String engyIdEnd = energyConsumeOutputDTO.getEngyIdEnd();
            Long pageNum = energyConsumeOutputDTO.getPageNum();
            Long pageSize = energyConsumeOutputDTO.getPageSize();
            // 构造条件
            LambdaQueryWrapper<EnergyConsumeOutput> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(eCountDateStart) && StringUtils.isNotBlank(eCountDateEnd)) {
                queryWrapper.between(EnergyConsumeOutput::getECountDate, eCountDateStart, eCountDateEnd);
            }
            if (StringUtils.isNotBlank(costCenter1) && StringUtils.isNotBlank(costCenter2)) {
                queryWrapper.between(EnergyConsumeOutput::getCostCenter, costCenter1, costCenter2);
            } else if (StringUtils.isNotBlank(costCenter1)) {
                queryWrapper.eq(EnergyConsumeOutput::getCostCenter, costCenter1);
            } else if (StringUtils.isNotBlank(costCenter2)) {
                queryWrapper.eq(EnergyConsumeOutput::getCostCenter, costCenter2);
            }
            if (StringUtils.isNotBlank(engyIdStart) && StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.between(EnergyConsumeOutput::getEngyId, engyIdStart, engyIdEnd);
            } else if (StringUtils.isNotBlank(engyIdStart)) {
                queryWrapper.eq(EnergyConsumeOutput::getEngyId, engyIdStart);
            } else if (StringUtils.isNotBlank(engyIdEnd)){
                queryWrapper.eq(EnergyConsumeOutput::getEngyId, engyIdEnd);
            }
            //实现分页查询
            Page<EnergyConsumeOutput> page = getBaseMapper().selectPage(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergyConsumeOutput> records = page.getRecords();
            BigDecimal sum = BigDecimal.ZERO;
            Map<String,Object> dataMap = new HashMap<>(16);
            for (EnergyConsumeOutput item : records) {
                BeanUtils.copyProperties(item, energyConsumeOutputDTO);
                //计算能源产耗总量
                sum = sum.add(energyConsumeOutputDTO.getProConQty());
            }
            dataMap.put("total",total);
            dataMap.put("list",records);
            dataMap.put("sum", sum);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * @description 查询成本中心日能耗资料能源代码下拉选单
     * @author: 265800
     * @date: 2023/7/7 10:09
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @throws:
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
     * @description 接收报文执行每日能源产耗资料新增
     * @author: 265800
     * @date: 2023/7/7 11:36
     * @param: [energyConsumeOutputDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult addEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        try {
            EnergyConsumeOutput energyConsumeOutput = new EnergyConsumeOutput();
            BeanUtils.copyProperties(energyConsumeOutputDTO, energyConsumeOutput);
            String costCenter = energyConsumeOutput.getCostCenter();
            String eCountDate = energyConsumeOutput.getECountDate();
            String engyId = energyConsumeOutput.getEngyId();
            String ioTypeId = energyConsumeOutput.getIoTypeId();
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            energyConsumeOutput.setTimeSeqNo(format);
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtils.isNotBlank(engyId), EnergyCode::getEngyId, engyId);
            Map<String, Object> map = energyCodeMapper.selectMaps(queryWrapper).size()==0 ? null : energyCodeMapper.selectMaps(queryWrapper).get(0);
            String engyType = String.valueOf(map.get("engy_type"));
            String acctSys = String.valueOf(map.get("acct_sys"));
            String engyUnit = String.valueOf(map.get("engy_unit"));
            energyConsumeOutput.setEngyType(engyType);
            energyConsumeOutput.setAcctSys(acctSys);
            energyConsumeOutput.setEngyUnit(engyUnit);
            String w5 = "W5";
            if (costCenter.startsWith(w5)) {
                energyConsumeOutput.setIoTypeId("S");
            }
            energyConsumeOutput.setCalUnit("KJ/unit");
            //作业代码N
            String n = "N";
            //作业代码R
            String r = "R";
            //作业代码D
            String d = "D";
            //IP
            String ip = "IP";
            //AMIP
            String amIp = "AMIP";
            String closeAccount = "88";
            if (n.equals(energyConsumeOutput.getExecJobFunc())) {
                LambdaQueryWrapper<EnergyConsumeOutput> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(EnergyConsumeOutput::getCostCenter, costCenter)
                        .eq(EnergyConsumeOutput::getECountDate, eCountDate)
                        .eq(EnergyConsumeOutput::getEngyId, engyId)
                        .eq(EnergyConsumeOutput::getIoTypeId, ioTypeId);
                List<EnergyConsumeOutput> list1 = getBaseMapper().selectList(queryWrapper1);
                if (!list1.isEmpty()) {
                    String msg = "资料重复，新增失败！";
                    log.info(msg, energyConsumeOutput);
                    if (energyConsumeOutputDTO.getMessageInfo().length() > 2000) {
                        energyConsumeOutputDTO.getMessageInfo().substring(0,2000);
                    }
                    sendMessage(energyConsumeOutputDTO, msg);
                    return AjaxResult.error(msg);
                }
                int result = getBaseMapper().insert(energyConsumeOutput);
                if(result>0){
                    return AjaxResult.success("新增成功");
                }else {
                    return AjaxResult.error("新增失败");
                }
            } else {
                if (r.equals(energyConsumeOutput.getExecJobFunc())){
                    updateEnergyConsumeOutput(energyConsumeOutputDTO);
                    energyConsumeOutput.setExecJobFunc("N");
                    // 非重复资料才新增t_energy_consume_output，同时抛IP
                    int result = getBaseMapper().insert(energyConsumeOutput);
                    if(result>0){
                        return AjaxResult.success("新增成功");
                    }else {
                        return AjaxResult.error("新增失败");
                    }
                }
                // 若为冲销资料，在IP/AM已月关帐时，不可冲销IP/AM
                if (!closeAccount.equals(energyConsumeOutput.getECountDate().substring(6,8))){
                    if(d.equals(energyConsumeOutput.getExecJobFunc()) && ip.equals(energyConsumeOutput.getAcctSys()) || amIp.equals(energyConsumeOutput.getAcctSys())){
                        // 在IP/AM已月关帐时，写到异常档
                        if (energyConsumeOutputDTO.getMessageInfo().length() > 2000) {
                            energyConsumeOutputDTO.getMessageInfo().substring(0,2000);
                        }
                        sendMessage(energyConsumeOutputDTO, "本月已关账");
                    } else {
                        if (energyConsumeOutput.getProConQty().compareTo(new BigDecimal("0")) != 0){
                            transToIP(energyConsumeOutput);
                        }
                    }
                }
                return null;
            }
        } catch (Exception e) {
            String eMsg = "EE系统处理异常" + e.getMessage();
            if (energyConsumeOutputDTO.getMessageInfo().length() > 2000) {
                energyConsumeOutputDTO.getMessageInfo().substring(0,2000);
            }
            sendMessage(energyConsumeOutputDTO, eMsg);
            return AjaxResult.error();
        }
    }

    /**
     * 异常资讯公共方法
     */
    private void sendMessage(EnergyConsumeOutputDTO energyConsumeOutputDTO, String msg) {
        EnergyTransData energyTransData = new EnergyTransData();
        energyTransData.setCompId("J00");
        energyTransData.setTransDate(String.valueOf(new Date()).substring(0,8));
        energyTransData.setTransTime(String.valueOf(new Date()).substring(0,6));
        energyTransData.setTransData(energyConsumeOutputDTO.getMessageInfo());
        energyTransData.setErrMsg(msg);
        energyTransDataService.save(energyTransData);
    }

    /**
     * 将资料抛送到IP，同时新增到t_energy_cost_output
     * (回收量及能源抛帐系统为IP，才抛)
     * @param energyConsumeOutput
     * @since 2023/02/13
     */
    public void transToIP(EnergyConsumeOutput energyConsumeOutput) throws Exception {
        String dataTypeIdxP = "", dataTypeIdxC = "", activityIdR = "", activityIdP = "", activityIdC = "";
        String ip = "IP";
        String amip = "AMIP";
        String n = "N";
        String r = "R";
        String d = "D";
        String b = "B";
        String x = "X";
        //   能源抛帐系统为IP才抛(耗用量不抛IP)
        if ((ip.equals(energyConsumeOutput.getAcctSys()) || amip.equals(energyConsumeOutput.getAcctSys())) && (r.equals(energyConsumeOutput.getIoTypeId()) || b.equals(energyConsumeOutput.getIoTypeId()) || x.equals(energyConsumeOutput.getIoTypeId()))) {
            // 回收量
            if (r.equals(energyConsumeOutput.getIoTypeId())) {
                //	ip回收资料的产出代码
                activityIdR = "RECYCLE";
                //	ip回收耗用资料的耗用代码
                activityIdC = "RECYCLE_CONSUMPTION";
                //	ip产出资料的产出代码
                activityIdP = "RECYCLE_PRODUCE";
                // 依 execJobFunc决定 dataTypeIdx
                if (n.equals(energyConsumeOutput.getExecJobFunc()) || r.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "N";
                    dataTypeIdxC = "O";
                } else if (d.equals(energyConsumeOutput.getExecJobFunc())) {
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
            } else if (b.equals(energyConsumeOutput.getIoTypeId())) {
                //	ip外购资料的外购代码
                activityIdR = "BUY";
                //	ip外购领用资料的领用代码
                activityIdC = "BUY_CONSUMPTION";
                // 依 execJobFunc决定 dataTypeIdx
                if (n.equals(energyConsumeOutput.getExecJobFunc()) || r.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "N";
                    dataTypeIdxC = "O";
                } else if (d.equals(energyConsumeOutput.getExecJobFunc())) {
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
            } else if (x.equals(energyConsumeOutput.getIoTypeId())) {
                //	ip结转产量代码
                activityIdR = "IP_PRODUCE";
                // 依 execJobFunc决定 dataTypeIdx
                if (n.equals(energyConsumeOutput.getExecJobFunc()) || r.equals(energyConsumeOutput.getExecJobFunc())) {
                    dataTypeIdxP = "N";
                } else if (d.equals(energyConsumeOutput.getExecJobFunc())) {
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
     * @param energyConsumeOutput
     * @since 2023/02/13
     */
    private void createTBEE10(EnergyConsumeOutput energyConsumeOutput, String dataTypeIdx, String activityId) throws Exception {
        String d = "D";
        //将资料新增入t_energy_cost_output表
        EnergyCostOutput energyCostOutput  = new EnergyCostOutput();
        energyCostOutput.setCompId(energyConsumeOutput.getCompId());
        // 成本中心
        energyCostOutput.setCostCenter(energyConsumeOutput.getCostCenter());
        // 卖方成本中心
        energyCostOutput.setChargeCostCenter(energyConsumeOutput.getChargeCostCenter());
        // 活动日期
        energyCostOutput.setActivityDate(energyConsumeOutput.getECountDate());
        // 能源代码
        energyCostOutput.setEngyId(energyConsumeOutput.getEngyId());
        //  财务用能源单位
        energyCostOutput.setEngyUnit(energyConsumeOutput.getEngyUnitF());
        //  交易资料指示码(N/O)
        energyCostOutput.setDataTypeIdx(dataTypeIdx);
        // 生产活动(C/P)
        energyCostOutput.setActivityId(activityId);
        //   执行作业(N/D)
        energyCostOutput.setExecJobFunc(energyConsumeOutput.getExecJobFunc());
        //  当前产线别
        energyCostOutput.setCrntMill(energyConsumeOutput.getFactoryId());
        energyCostOutput.setAcctDate(energyConsumeOutput.getECountDate());
        // 当前产线别
        energyCostOutput.setFactoryId(energyConsumeOutput.getFactoryId());
        if(d.equals(energyConsumeOutput.getExecJobFunc())){
            BigDecimal proConQty = new BigDecimal("0").subtract(energyConsumeOutput.getProConQty());
            //  能量值
            energyCostOutput.setProConQty(proConQty);
        }else{
            //  能量值
            energyCostOutput.setProConQty(energyConsumeOutput.getProConQty());
        }
        energyCostOutputService.save(energyCostOutput);
    }

    /**
     * 当执行作业代码为"R"时（冲销），自行产生的一笔"D"的资料做冲销
     */
    @Log(title = "当执行作业代码为\"R\"时（冲销），自行产生的一笔\"D\"的资料做冲销", businessType = BusinessType.INSERT)
    public void updateEnergyConsumeOutput(EnergyConsumeOutputDTO energyConsumeOutputDTO) {
        try {
            EnergyConsumeOutput energyConsumeOutput = new EnergyConsumeOutput();
            org.springframework.beans.BeanUtils.copyProperties(energyConsumeOutputDTO, energyConsumeOutput);
            String engyId = energyConsumeOutput.getEngyId();
            String eCountDate = energyConsumeOutput.getECountDate();
            String costCenter = energyConsumeOutput.getCostCenter();
            String ioTypeId = energyConsumeOutput.getIoTypeId();
            String chargeCostCenter = energyConsumeOutput.getChargeCostCenter();
            LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(engyId), EnergyCode::getEngyId, engyId);
            Map<String, Object> map = energyCodeMapper.selectMaps(queryWrapper).size()==0 ? null : energyCodeMapper.selectMaps(queryWrapper).get(0);
            String engyType = String.valueOf(map.get("engy_type"));
            String acctSys = String.valueOf(map.get("acct_sys"));
            String engyUnit = String.valueOf(map.get("engy_unit"));
            energyConsumeOutput.setEngyType(engyType);
            energyConsumeOutput.setAcctSys(acctSys);
            energyConsumeOutput.setEngyUnit(engyUnit);
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            energyConsumeOutput.setTimeSeqNo(format);
            QueryWrapper<EnergyConsumeOutput> queryWrapperA = new QueryWrapper<>();
            queryWrapperA.select("IFNULL(sum(pro_con_qty),0) as proConQty", "IFNULL(sum(cal_value),0) as calValue", "IFNULL(sum(engy_value),0) as engyValue")
                    .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(engyId), "engy_id", engyId)
                    .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(eCountDate), "e_count_date", eCountDate)
                    .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(costCenter), "cost_center", costCenter)
                    .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(ioTypeId), "io_type_id", ioTypeId)
                    .eq(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(chargeCostCenter), "charge_cost_center", chargeCostCenter);
            Map<String, Object> mapA = getBaseMapper().selectMaps(queryWrapperA).size()==0 ? null : getBaseMapper().selectMaps(queryWrapperA).get(0);
            BigDecimal sumProConQty = new BigDecimal(mapA.get("proConQty")==null?"0":mapA.get("proConQty").toString()).multiply(new BigDecimal(-1));
            BigDecimal sumCalValue = new BigDecimal(mapA.get("calValue")==null?"0":mapA.get("calValue").toString());
            BigDecimal sumEngyValue = new BigDecimal(mapA.get("engyValue")==null?"0":mapA.get("engyValue").toString()).multiply(new BigDecimal(-1));
            energyConsumeOutput.setProConQty(sumProConQty);
            energyConsumeOutput.setCalValue(sumCalValue);
            energyConsumeOutput.setEngyValue(sumEngyValue);
            String w5 = "W5";
            if (costCenter.startsWith(w5)) {
                energyConsumeOutput.setIoTypeId("S");
            }
            energyConsumeOutput.setCalUnit("KJ/unit");
            energyConsumeOutput.setExecJobFunc("D");
            getBaseMapper().insert(energyConsumeOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




