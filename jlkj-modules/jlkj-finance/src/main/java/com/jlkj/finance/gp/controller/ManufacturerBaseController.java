package com.jlkj.finance.gp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.dto.finance.gp.ManufacturerBaseInterfaceDTO;
import com.jlkj.finance.aa.domain.FinanceAaDictData;

import com.jlkj.finance.aa.service.IFinanceAaDictDataService;

import com.jlkj.finance.gp.domain.*;
import com.jlkj.finance.gp.dto.ManufacturerBaseDTO;

import com.jlkj.finance.gp.mapper.ManufacturerTreeMapper;
import com.jlkj.finance.gp.service.*;
import com.jlkj.finance.gp.service.impl.FinanceGpCorrelationServiceImpl;
import com.jlkj.finance.gp.service.impl.FinanceGpPurchaseLinkServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wm
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/finance/manufacturer")
public class ManufacturerBaseController {
    @Autowired
    private ManufacturerBaseService manufacturerBaseService;
    @Autowired
    private ManufacturerCustomerService manufacturerCustomerService;
    @Autowired
    private ManufacturerFinanceService manufacturerFinanceService;
    @Autowired
    private ManufacturerPurchaseService manufacturerPurchaseService;
    @Autowired
    private ManufacturerRelationService manufacturerRelationService;
    @Autowired
    private ManufacturerTransportService manufacturerTransportService;
    @Autowired
    private IFinanceAaDictDataService financeAaDictDataService;
    @Autowired
    private   ManufacturerBaseTreeService manufacturerBaseTreeService;
    @Autowired
    private FinanceGpCorrelationServiceImpl financeGpCorrelationService;
    @Autowired
    private FinanceGpPurchaseLinkServiceImpl financeGpPurchaseLinkService;
    @Autowired
    private ManufacturerTreeMapper manufacturerTreeMapper;
    /**
     * 根据厂商编码、厂商中文名称、税号
     */
    @Log(title = "根据厂商编码、厂商中文名称、税号查询", businessType = BusinessType.OTHER)
    @Operation(summary = "根据厂商编码、厂商中文名称、税号")
    @GetMapping("/queryAll")
    public Object queryAll(ManufacturerBaseDTO manufacturerBaseDTO) {
        try {
            String manufacturerChineseName = manufacturerBaseDTO.getManufacturerChineseName();
            String manufacturerId = manufacturerBaseDTO.getManufacturerId();
            String taxNo = manufacturerBaseDTO.getTaxNo();
            Long pageNum = manufacturerBaseDTO.getPageNum();
            Long pageSize = manufacturerBaseDTO.getPageSize();
            LambdaQueryWrapper<ManufacturerBase> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(manufacturerChineseName), ManufacturerBase::getManufacturerChineseName, manufacturerChineseName)
                    .like(StringUtils.isNotBlank(manufacturerId), ManufacturerBase::getManufacturerId, manufacturerId)
                    .like(StringUtils.isNotBlank(taxNo), ManufacturerBase::getTaxNo, taxNo);
            Page<ManufacturerBase> page = manufacturerBaseService.page(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<ManufacturerBase> records = page.getRecords();
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("total", total);
            dataMap.put("list", records);
            if (page.getRecords().isEmpty()) {
                return AjaxResult.error("查无资料");
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch(Exception e)
        {
            return AjaxResult.error();
        }

    }
    /**
     * 根据公司别、厂商编码
     */
    @Log(title = "根据公司别、厂商编码查询", businessType = BusinessType.OTHER)
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "查看详情")
    @GetMapping("/queryOne")
    public Object queryOne(@RequestParam String manufacturerId) {

        QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
        String mainCompIdValue = "finance_compid";
        List<FinanceAaDictData> compIdValue = financeAaDictDataService.getMainCompIdValue(mainCompIdValue);
        String compId=null;
        for (FinanceAaDictData financeAaDictData:compIdValue){
            if ("J00".equals(financeAaDictData.getDictLabel())){
               /* compId=financeAaDictData.getDictLabel();*/

            }
        }
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId);
        }
        ManufacturerBase one = manufacturerBaseService.getOne(wrapper);

        ManufacturerBaseDTO manufacturerBaseDTO = new ManufacturerBaseDTO();
        BeanUtils.copyProperties(one, manufacturerBaseDTO);

        QueryWrapper<ManufacturerRelation> wrapperRelation = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapperRelation.eq("manufacturer_id", manufacturerId);
        }
        List<ManufacturerRelation> list = manufacturerRelationService.list(wrapperRelation);

        String[] relation1 = new String[list.size()];
        for (int i = 0;i<list.size();i++){
             relation1[i] = list.get(i).getRelation();
        }

        manufacturerBaseDTO.setRelation(relation1);
        return AjaxResult.success(manufacturerBaseDTO);
    }
    /**
     * 根据公司别、厂商编码
     */
    @Log(title = "根据公司别、厂商编码查询", businessType = BusinessType.OTHER)
    @Operation(summary = "查询接口")
    @GetMapping("/queryInterface")
    public ManufacturerBaseInterfaceDTO queryInterface(@RequestParam String manufacturerId, String compId) {
        ManufacturerBaseInterfaceDTO manufacturerBaseInterfaceDTO =new  ManufacturerBaseInterfaceDTO();
        QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.like("manufacturer_id", manufacturerId)
                    ;
        }
        ManufacturerBase manufacturerBase = manufacturerBaseService.getOne(wrapper);
        BeanUtils.copyProperties(manufacturerBase, manufacturerBaseInterfaceDTO);
        return manufacturerBaseInterfaceDTO;
    }

    /**
     * 根据公司别、厂商编码查询厂商客户关系资料表
     */
    @Log(title = "查询厂商客户关系资料表", businessType = BusinessType.OTHER)
    @Operation(summary = "查看详情")
    @GetMapping("/querySo")
    public Object querySo(@RequestParam String manufacturerId) {
        QueryWrapper<ManufacturerCustomer> wrapper = new QueryWrapper<>();
        String mainCompIdValue = "finance_compid";
        List<FinanceAaDictData> compIdValue = financeAaDictDataService.getMainCompIdValue(mainCompIdValue);
        String compId="";
        for (FinanceAaDictData financeAaDictData:compIdValue){
            if ("J00".equals(financeAaDictData.getDictLabel())){
           /*     compId=financeAaDictData.getDictLabel();*/
            }
        }

        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId);
        }
        ManufacturerCustomer queryCus = manufacturerCustomerService.getOne(wrapper);
        return AjaxResult.success(queryCus);
    }

    /**
     * 根据公司别、厂商编码查询厂商报支关系资料表
     */
    @Log(title = "查询厂商报支关系资料表", businessType = BusinessType.OTHER)
    @SuppressWarnings("SuspiciousMethodCalls")
    @Operation(summary = "查看报支关系")
    @GetMapping("/queryFc")
    public Object queryFc(@RequestParam String manufacturerId) {
        QueryWrapper<ManufacturerFinance> wrapper = new QueryWrapper<>();
        String mainCompIdValue = "finance_compid";
        List<FinanceAaDictData> compIdValue = financeAaDictDataService.getMainCompIdValue(mainCompIdValue);
        String compId="jln";
        for (FinanceAaDictData financeAaDictData:compIdValue){
            if ("J00".equals(financeAaDictData.getDictLabel())){
                /*compId=financeAaDictData.getDictLabel();*/
            }
        }
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                   ;
        }
        ManufacturerFinance queryFin = manufacturerFinanceService.getOne(wrapper);

        return AjaxResult.success(queryFin);
    }

    /**
     * 根据公司别、厂商编码查询厂商采购关系资料表
     */
    @Log(title = "查询厂商采购关系资料表", businessType = BusinessType.OTHER)
    @Operation(summary = "查看详情")
    @GetMapping("/queryMp")
    public Object queryMp(@RequestParam String manufacturerId) {
        String mainCompIdValue = "finance_compid";
        List<FinanceAaDictData> compIdValue = financeAaDictDataService.getMainCompIdValue(mainCompIdValue);
        String compId="jln";
        for (FinanceAaDictData financeAaDictData:compIdValue){
            if ("J00".equals(financeAaDictData.getDictLabel())){
          /*      compId=financeAaDictData.getDictLabel();*/
            }
        }
        QueryWrapper<ManufacturerPurchase> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId);
        }
        ManufacturerPurchase queryPur = manufacturerPurchaseService.getOne(wrapper);
        FinanceGpPurchaseLink financeGpPurchaseLink = new FinanceGpPurchaseLink();
        financeGpPurchaseLink.setManufacturerId(manufacturerId);
        List<FinanceGpPurchaseLink> financeGpPurchaseLinks = financeGpPurchaseLinkService.selectFinanceGpPurchaseLinkList(financeGpPurchaseLink);
        if(financeGpPurchaseLinks.size()>0){
            queryPur.setFinanceGpPurchaseLinkList(financeGpPurchaseLinks);
        }
        return AjaxResult.success(queryPur);
    }
    /**
     * 根据公司别、厂商编码查看关系
     */
    @Log(title = "查看编码关系", businessType = BusinessType.OTHER)
    @Operation(summary = "查看详情查询对应关系")
    @GetMapping("/queryRelations")
    public Object queryRelations(@RequestParam String manufacturerId) {
        String mainCompIdValue = "finance_compid";
        List<FinanceAaDictData> compIdValue = financeAaDictDataService.getMainCompIdValue(mainCompIdValue);
        String compId="jln";
        for (FinanceAaDictData financeAaDictData:compIdValue){
            if ("J00".equals(financeAaDictData.getDictLabel())){
              /*  compId=financeAaDictData.getDictLabel();*/
            }
        }
        QueryWrapper<ManufacturerRelation> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId)
                   ;
        }
        List<ManufacturerRelation> list = manufacturerRelationService.list(wrapper);
        return AjaxResult.success(list);
    }

    /**
     * 根据公司别、厂商编码查询厂商承运关系资料表
     */
    @Log(title = "询厂商承运关系资料表", businessType = BusinessType.OTHER)
    @Operation(summary = "查看承运关系")
    @GetMapping("/querySt")
    public Object querySt(@RequestParam String manufacturerId) {
        String mainCompIdValue = "finance_compid";
        List<FinanceAaDictData> compIdValue = financeAaDictDataService.getMainCompIdValue(mainCompIdValue);
        String compId="jln";
        for (FinanceAaDictData financeAaDictData:compIdValue){
            if ("J00".equals(financeAaDictData.getDictLabel())){
             /*   compId=financeAaDictData.getDictLabel();*/
            }
        }
        QueryWrapper<ManufacturerTransport> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(manufacturerId)) {
            wrapper.eq("manufacturer_id", manufacturerId);
        }
        ManufacturerTransport queryTran = manufacturerTransportService.getOne(wrapper);
        return AjaxResult.success(queryTran);
    }
    /**
     * 新增厂商编码基本资料
     */
    @Log(title = "新增厂商编码基本资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增厂商编码基本资料")
    @PostMapping("/doAdd")
    public Object doAdd( @RequestBody ManufacturerBaseDTO manufacturerBaseDTO){
        ManufacturerBase manufacturerBase = new ManufacturerBase();
        QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
        String[] relation = manufacturerBaseDTO.getRelation();
        String detailAreaId = manufacturerBaseDTO.getDetailAreaId();
        String mainAreaId = manufacturerBaseDTO.getMainAreaId();
        String manufacturerIdList =null;
        //自动更新厂商编号
        QueryWrapper<ManufacturerBase> wrapperLike = new QueryWrapper<>();
        wrapperLike.like("manufacturer_id", mainAreaId+detailAreaId);
        List<ManufacturerBase> list = manufacturerBaseService.list(wrapperLike);
        if(list.size()>0){
            for (int i = 0 ;i<list.size();i++){
                manufacturerIdList = list.get(i).getManufacturerId();
            }
        }else {
            manufacturerIdList =  manufacturerBase.getMainAreaId()+manufacturerBase.getDetailAreaId()+0000;
        }
        String manufacturerId = String.valueOf(Integer.parseInt(manufacturerIdList.substring(3, 7)) + 1);
        manufacturerBase.setManufacturerId(manufacturerIdList.substring(0, 6)+manufacturerId);
        manufacturerBaseDTO.setManufacturerId(manufacturerIdList.substring(0, 6)+manufacturerId);
        wrapper.eq("tax_no",manufacturerBase.getTaxNo());
        List<ManufacturerBase> listSave = manufacturerBaseService.list(wrapper);
        if (listSave.size() >= 1) {
            return AjaxResult.error("您输入的厂商基本资料系统中已存在，请重新输入！");
        }
        ManufacturerRelation manufacturerRelation = new ManufacturerRelation();
        for (int i = 0;i<relation.length;i++) {
            manufacturerRelation.setManufacturerId(manufacturerBaseDTO.getManufacturerId());
            manufacturerRelation.setRelation(relation[i]);
            manufacturerRelation.setId("");

            QueryWrapper<ManufacturerRelation> wrapperRelation = new QueryWrapper<>();
            wrapperRelation.eq("manufacturer_id",manufacturerRelation.getManufacturerId())
            .eq("relation",manufacturerRelation.getRelation());
            ManufacturerRelation manufacturerRelationOne = manufacturerRelationService.getOne(wrapperRelation);
            if (manufacturerRelationOne==null){
                manufacturerRelationService.save(manufacturerRelation);
            }
        }
        ManufacturerBaseDTO treeData = manufacturerBaseTreeService.createTreeData(manufacturerBaseDTO);

        BeanUtils.copyProperties(treeData,manufacturerBase);
        FinanceGpCorrelation financeGpCorrelation = new FinanceGpCorrelation();
        financeGpCorrelation.setCompId(manufacturerBaseDTO.getCompId());
        financeGpCorrelation.setCorrelationDate( manufacturerBaseDTO.getCorrelationDate());
        financeGpCorrelation.setManufacturerId(manufacturerBaseDTO.getManufacturerId());
        financeGpCorrelation.setPreStatus("3");
        financeGpCorrelation.setCreateTime(DateUtils.getNowDate());
        financeGpCorrelation.setCurrentStatus(manufacturerBaseDTO.getRelatedParty());
        financeGpCorrelationService.insertFinanceGpCorrelation(financeGpCorrelation);
        manufacturerBase.setCreateTime(DateUtils.getNowDate());
        boolean save = manufacturerBaseService.save(manufacturerBase);
        return AjaxResult.success(save);
    }
    /**
     * 修改厂商编码基本资料
     */
    @Log(title = "修改厂商编码基本资料", businessType = BusinessType.INSERT)
    @Operation(summary = "修改厂商编码基本资料")
    @PostMapping("/doEdit")
    public Object doEdit( @RequestBody ManufacturerBaseDTO manufacturerBaseDTO){
        ManufacturerBase manufacturerBase = new ManufacturerBase();
        String[] relation = manufacturerBaseDTO.getRelation();
        List<String> listB = new ArrayList<>(Arrays.asList(relation));
        List<String> listC = new ArrayList<>();
        ManufacturerRelation manufacturerRelation = new ManufacturerRelation();
        QueryWrapper<ManufacturerRelation> wrapperList = new QueryWrapper<>();
        wrapperList.eq("manufacturer_id", manufacturerBaseDTO.getManufacturerId());
        List<ManufacturerRelation> list = manufacturerRelationService.list(wrapperList);
        for (ManufacturerRelation manufacturerRelation1:list){
            if (!listB.contains(manufacturerRelation1.getRelation())){
                listC.add(manufacturerRelation1.getRelation());
            }
        }
        if (listC.size()>0){
            for (String relationUpdate:listC){
                manufacturerRelation.setManufacturerId(manufacturerBaseDTO.getManufacturerId());
                manufacturerRelation.setRelation(relationUpdate);
                manufacturerRelation.setId("");

                QueryWrapper<ManufacturerRelation> wrapperRelation = new QueryWrapper<>();
                wrapperRelation.eq("manufacturer_id", manufacturerRelation.getManufacturerId())
                        .eq("relation", manufacturerRelation.getRelation());
                manufacturerRelationService.remove(wrapperRelation);
                if ("SO".equals(relationUpdate)){
                    QueryWrapper<ManufacturerCustomer> wrapper = new QueryWrapper<>();
                    wrapper.eq("manufacturer_id", manufacturerBaseDTO.getManufacturerId());
                    manufacturerCustomerService.remove(wrapper);
                }
                if ("FC".equals(relationUpdate)){
                    QueryWrapper<ManufacturerFinance> wrapper = new QueryWrapper<>();
                    wrapper.eq("manufacturer_id", manufacturerBaseDTO.getManufacturerId());
                    manufacturerFinanceService.remove(wrapper);
                }
                if ("MP".equals(relationUpdate)){
                    QueryWrapper<ManufacturerPurchase> wrapper = new QueryWrapper<>();
                    wrapper.eq("manufacturer_id", manufacturerBaseDTO.getManufacturerId());
                    manufacturerPurchaseService.remove(wrapper);
                }
                if ("ST".equals(relationUpdate)){
                    QueryWrapper<ManufacturerTransport> wrapper = new QueryWrapper<>();
                    wrapper.eq("manufacturer_id", manufacturerBaseDTO.getManufacturerId());
                    manufacturerTransportService.remove(wrapper);
                }

            }
        }
        for (int i = 0;i<relation.length;i++) {
            manufacturerRelation.setManufacturerId(manufacturerBaseDTO.getManufacturerId());
            manufacturerRelation.setRelation(relation[i]);
            manufacturerRelation.setId("");

            QueryWrapper<ManufacturerRelation> wrapperRelation = new QueryWrapper<>();
            wrapperRelation.eq("manufacturer_id",manufacturerRelation.getManufacturerId())
                    .eq("relation",manufacturerRelation.getRelation());
            ManufacturerRelation manufacturerRelationOne = manufacturerRelationService.getOne(wrapperRelation);
            if (manufacturerRelationOne==null){
                manufacturerRelationService.save(manufacturerRelation);
            }
        }
        BeanUtils.copyProperties(manufacturerBaseDTO,manufacturerBase);
        manufacturerBase.setUpdateTime(DateUtils.getNowDate());
        boolean result = manufacturerBaseService.updateById(manufacturerBase);
        return AjaxResult.success(result);
    }
    /**
     * 修改报支关系基本资料
     */
    @Log(title = "修改报支关系基本资料", businessType = BusinessType.INSERT)
    @Operation(summary = "修改报支关系基本资料")
    @PostMapping("/doEditFc")
    public Object doEditFc( @RequestBody ManufacturerFinance manufacturerFinance){
        QueryWrapper<ManufacturerFinance> wrapper = new QueryWrapper<>();
            wrapper.eq("manufacturer_id", manufacturerFinance.getManufacturerId());
        ManufacturerFinance one = manufacturerFinanceService.getOne(wrapper);
        boolean result;
        if (one==null){
             result = manufacturerFinanceService.save(manufacturerFinance);
        }else {
             result = manufacturerFinanceService.updateById(manufacturerFinance);
        }

        return AjaxResult.success(result);
    }
    /**
     * 修改采购关系基本资料
     */
    @Log(title = "修改采购关系基本资料", businessType = BusinessType.INSERT)
    @Operation(summary = "修改采购关系基本资料")
    @PostMapping("/doEditMp")
    public Object doEditMp( @RequestBody ManufacturerPurchase manufacturerPurchase){
        QueryWrapper<ManufacturerPurchase> wrapper = new QueryWrapper<>();
        wrapper.eq("manufacturer_id", manufacturerPurchase.getManufacturerId());
        ManufacturerPurchase one = manufacturerPurchaseService.getOne(wrapper);
        boolean result;
        List<FinanceGpPurchaseLink> financeGpPurchaseLinkList = manufacturerPurchase.getFinanceGpPurchaseLinkList();
        if (financeGpPurchaseLinkList != null) {
            financeGpPurchaseLinkService.insertCapitalDetail(financeGpPurchaseLinkList,manufacturerPurchase.getManufacturerId());
        }

        if (one==null){
            result = manufacturerPurchaseService.save(manufacturerPurchase);

        }else {
            result = manufacturerPurchaseService.updateById(manufacturerPurchase);
        }
        return AjaxResult.success(result);
    }
    /**
     * 修改客户关系基本资料
     */
    @Log(title = "修改客户关系基本资料", businessType = BusinessType.INSERT)
    @Operation(summary = "修改客户关系基本资料")
    @PostMapping("/doEditSo")
    public Object doEditSo( @RequestBody ManufacturerCustomer manufacturerCustomer){
        QueryWrapper<ManufacturerCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("manufacturer_id", manufacturerCustomer.getManufacturerId());
        ManufacturerCustomer queryCus = manufacturerCustomerService.getOne(wrapper);
        boolean result;
        if (queryCus==null){
            result = manufacturerCustomerService.save(manufacturerCustomer);
        }else {
            result = manufacturerCustomerService.updateById(manufacturerCustomer);
        }
        return AjaxResult.success(result);
    }
    /**
     * 修改承运关系基本资料
     */
    @Log(title = "修改承运关系基本资料", businessType = BusinessType.INSERT)
    @Operation(summary = "修改承运关系基本资料")
    @PostMapping("/doEditSt")
    public Object doEditSt( @RequestBody ManufacturerTransport manufacturerTransport){
        QueryWrapper<ManufacturerTransport> wrapper = new QueryWrapper<>();
        wrapper.eq("manufacturer_id", manufacturerTransport.getManufacturerId());
        ManufacturerTransport queryTran = manufacturerTransportService.getOne(wrapper);
        boolean result;
        if (queryTran==null){
            result = manufacturerTransportService.save(manufacturerTransport);
        }else {
            result = manufacturerTransportService.updateById(manufacturerTransport);
        }
        return AjaxResult.success(result);
    }
    /**
     * 基本厂商资料删除
     */
    @Operation(summary = "基本厂商资料删除")
    @DeleteMapping("/delete")
    @Log(title = "基本厂商资料删除", businessType = BusinessType.DELETE)
    public Object deleteEnergyCode(@RequestParam String id) {
        try {
            QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
            wrapper.eq("id",id);
            ManufacturerBase one = manufacturerBaseService.getOne(wrapper);
            boolean delete = manufacturerBaseService.remove(wrapper);
            QueryWrapper<ManufacturerRelation> wrapperRelationList = new QueryWrapper<>();
            wrapperRelationList.eq("manufacturer_id", one.getManufacturerId());
            List<ManufacturerRelation> list = manufacturerRelationService.list(wrapperRelationList);
            if (list.size()>0){
                for (int i = 0;i<list.size();i++) {
                    QueryWrapper<ManufacturerRelation> wrapperRelation = new QueryWrapper<>();
                    wrapperRelation.eq("manufacturer_id", list.get(i).getManufacturerId())
                            .eq("relation", list.get(i).getRelation());
                    manufacturerRelationService.remove(wrapperRelation);
                }
            }
            QueryWrapper<ManufacturerTree> wrapperTree = new QueryWrapper<>();
            wrapperTree.eq("manufacturer_id",one.getManufacturerId())
                    .eq("parentId",one.getParentId())
            .eq("node_name",one.getManufacturerChineseName());
            manufacturerTreeMapper.delete(wrapperTree);
            return AjaxResult.success(delete);
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }
}
