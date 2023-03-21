package com.jlkj.rabbit.feign.qualityprovider;

import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.quality.*;
import com.jlkj.rabbit.dto.quality.TsSampleBasicDatabDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 智能研发室 黄兵
 * @Description 质量模块远程调用
 * @date 2022/5/11 - 9:01
 */
@Component
@FeignClient(value = "quality-service-provider")
public interface QualityFeignService {

    /**
     * 添加样品基本资料主表数据同步
     * @param tsSampleBasicDataaDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/sampleBasicData/addMaster")
    R addTsSampleBasicMasterData(TsSampleBasicDataaDTO tsSampleBasicDataaDTO, @RequestHeader("token") String token);

    /**
     * 修改样品基本资料主表数据同步
     * @param tsSampleBasicDataaDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/sampleBasicData/updateMaster")
    R updateTsSampleBasicMasterData(TsSampleBasicDataaDTO tsSampleBasicDataaDTO, @RequestHeader("token") String token);

    /**
     * 删除样品基本资料主表数据同步
     * @param sampleClass
     * @param token
     * @return
     */
    @DeleteMapping("/quality/chemicalTest/sampleBasicData/deleteMaster")
    R deleteTsSampleBasicMasterData(@RequestParam String sampleClass, @RequestHeader("token") String token);

    /**
     * 添加样品基本资料从表数据同步
     * @param tsSampleBasicDatabDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/sampleBasicData/addSlave")
    R addTsSampleBasicSlaveData(TsSampleBasicDatabDTO tsSampleBasicDatabDTO, @RequestHeader("token") String token);

    /**
     * 修改样品基本资料从表数据同步
     * @param tsSampleBasicDatabDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/sampleBasicData/updateSlave")
    R updateTsSampleBasicSlaveData(TsSampleBasicDatabDTO tsSampleBasicDatabDTO, @RequestHeader("token") String token);

    /**
     * 删除样品基本资料从表数据同步
     * @param sampleClass
     * @param token
     * @return
     */
    @DeleteMapping("/quality/chemicalTest/sampleBasicData/deleteSlave")
    R deleteTsSampleBasicSlaveData(@RequestParam String sampleClass, @RequestHeader("token") String token);

    /**
     * 添加试验仪器基本资料
     * @param tsEquipBasicInformationDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/instrumentBasicData/add")
    AjaxResult addTsEquipBasicInformation(TsEquipBasicInformationDTO tsEquipBasicInformationDTO, @RequestHeader("token") String token);

    /**
     * 编辑试验仪器基本资料
     * @param tsEquipBasicInformationDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/instrumentBasicData/update")
    AjaxResult updateTsEquipBasicInformation(TsEquipBasicInformationDTO tsEquipBasicInformationDTO, @RequestHeader("token") String token);

    /**
     * 删除试验仪器基本资料
     * @param equipNo
     * @param token
     * @return
     */
    @DeleteMapping("/quality/chemicalTest/instrumentBasicData/delete")
    AjaxResult deleteTsEquipBasicInformation(@RequestParam String equipNo, @RequestHeader("token") String token);

    /**
     * 添加试验元素代号
     * @param tsTestElementCodeDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/elementId/add")
    R addTsTestElementCode(TsTestElementCodeDTO tsTestElementCodeDTO, @RequestHeader("token") String token);

    /**
     * 编辑试验元素代号
     * @param tsTestElementCodeDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/elementId/update")
    R updateTsTestElementCode(TsTestElementCodeDTO tsTestElementCodeDTO, @RequestHeader("token") String token);

    /**
     * 删除试验元素代号
     * @param elementIndex
     * @param token
     * @return
     */
    @DeleteMapping("/quality/chemicalTest/elementId/delete")
    R deleteTsTestElementCode(@RequestParam String elementIndex, @RequestHeader("token") String token);

    /**
     * 添加试验元素表格
     * @param tsElementTableDataDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/elementTable/addErp")
    R addTsElementTableData(TsElementTableDataDTO tsElementTableDataDTO, @RequestHeader("token") String token);

    /**
     * 编辑试验元素表格
     * @param tsElementTableDataDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/elementTable/updateErp")
    R updateTsElementTableData(TsElementTableDataDTO tsElementTableDataDTO, @RequestHeader("token") String token);

    /**
     * 删除试验元素表格
     * @param formatId
     * @param elementIndex
     * @param token
     * @return
     */
    @DeleteMapping("/quality/chemicalTest/elementTable/deleteA")
    R deleteTsElementTableData(@RequestParam String formatId,@RequestParam String elementIndex, @RequestHeader("token") String token);

    /**
     * 添加样品试验高低标准
     * @param tsElementHighLowStandardDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/highAndLowStandards/addErp")
    R addTsElementHighLowStandard(TsElementHighLowStandardDTO tsElementHighLowStandardDTO, @RequestHeader("token") String token);

    /**
     * 编辑样品试验高低标准
     * @param tsElementHighLowStandardDTO
     * @param token
     * @return
     */
    @PostMapping("/quality/chemicalTest/highAndLowStandards/updateErp")
    R updateTsElementHighLowStandard(TsElementHighLowStandardDTO tsElementHighLowStandardDTO, @RequestHeader("token") String token);

    /**
     * 删除样品试验高低标准
     * @param sampleClass
     * @param elementIndex
     * @param token
     * @return
     */
    @DeleteMapping("/quality/chemicalTest/highAndLowStandards/deleteA")
    R deleteTsElementHighLowStandard(@RequestParam String sampleClass, @RequestParam String elementIndex, @RequestHeader("token") String token);

    /**
     * 新增焦化煤试验值
     * @param tsExperimentRecordDTO
     * @return
     */
    @PostMapping("/quality/chemicalTest/experimentRecord/add")
    Object addTsExperimentRecord(TsExperimentRecordDTO tsExperimentRecordDTO);
}
