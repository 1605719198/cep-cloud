package com.jlkj.finance.gp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.dto.dto.financeperdto.*;
import com.jlkj.finance.gp.domain.*;
import com.jlkj.finance.gp.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * @author wang'mai
 */
@RestController
@RequestMapping("/financepr/board")
public class FinanceprBoardMqController {
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
    private ManufacturerTreeService manufacturerTreeService;
    /**
     * 查询厂商基本资料新增板块资料
     */
    @Log(title = "查询厂商基本资料", businessType = BusinessType.OTHER)
    @Operation(summary = "查询板块资料")
    @PostMapping("/queryId")
    public Object queryId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO){
        ManufacturerBase manufacturerBase = new ManufacturerBase();
        BeanUtils.copyProperties(manufacturerBaseDTO,manufacturerBase);
        String manufacturerId = manufacturerBaseDTO.getManufacturerId();
        LambdaQueryWrapper<ManufacturerBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(manufacturerId),ManufacturerBase::getManufacturerId,manufacturerId);
        List<ManufacturerBase>list = manufacturerBaseService.list(queryWrapper);
        return AjaxResult.success(list);
    }
    /**
     * 删除厂商基本资料新增板块资料
     */
    @Log(title = "删除厂商基本资料新增板块资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除板块资料")
    @PostMapping("/deleteId")
    public Object deleteId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO){
        ManufacturerBase manufacturerBase = new ManufacturerBase();
        BeanUtils.copyProperties(manufacturerBaseDTO,manufacturerBase);
        String manufacturerId = manufacturerBaseDTO.getManufacturerId();
        boolean delete = manufacturerBaseService.removeById(manufacturerId);
        return AjaxResult.success(delete);
    }
    /**
     * 厂商基本资料新增板块资料
     */
    @Log(title = "厂商基本资料新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData1")
    public Object add1(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO, @RequestHeader("token") String token) throws ParseException {
        ManufacturerBase manufacturerBase = new ManufacturerBase();
        BeanUtils.copyProperties(manufacturerBaseDTO,manufacturerBase);
        QueryWrapper<ManufacturerBase> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",manufacturerBase.getCompId())
               .eq("manufacturer_id",manufacturerBase.getManufacturerId());
        manufacturerBaseService.remove(wrapper);
        ManufacturerBaseDTO manufacturerBaseTree =  manufacturerBaseService.createTreeData(manufacturerBaseDTO);
        BeanUtils.copyProperties(manufacturerBaseTree,manufacturerBase);
        boolean save = manufacturerBaseService.save(manufacturerBase);
        return AjaxResult.success(save);
    }
    /**
     * 关系新增板块资料
     */
    @Log(title = "关系新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData")
    public Object add(@RequestBody ManufacturerRelationDTO manufacturerRelationDTO, @RequestHeader("token") String token){
        ManufacturerRelation manufacturerRelation = new ManufacturerRelation();
        BeanUtils.copyProperties(manufacturerRelationDTO,manufacturerRelation);
        QueryWrapper<ManufacturerRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",manufacturerRelation.getCompId())
                .eq("manufacturer_id",manufacturerRelation.getManufacturerId())
                .eq("relation",manufacturerRelation.getRelation());
        manufacturerRelationService.remove(wrapper);
        boolean save = manufacturerRelationService.save(manufacturerRelation);
        //TODO 获取登录用户名
        return AjaxResult.success(save);
    }
    /**
     * 厂商客户关系新增板块资料
     */
    @Log(title = "厂商客户关系新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData2")
    public Object add2(@RequestBody ManufacturerCustomerDTO manufacturerCustomerDTO, @RequestHeader("token") String token){
        ManufacturerCustomer manufacturerCustomer = new ManufacturerCustomer();
        BeanUtils.copyProperties(manufacturerCustomerDTO,manufacturerCustomer);
        QueryWrapper<ManufacturerCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",manufacturerCustomer.getCompId())
                .eq("manufacturer_id",manufacturerCustomer.getManufacturerId());
        manufacturerCustomerService.remove(wrapper);
        boolean save = manufacturerCustomerService.save(manufacturerCustomer);

        //TODO 获取登录用户名

        return AjaxResult.success(save);
    }
    /**
     * 采购新增板块资料
     */
    @Log(title = "采购新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData3")
    public Object add3(@RequestBody ManufacturerPurchaseDTO manufacturerPurchaseDTO, @RequestHeader("token") String token){
        ManufacturerPurchase manufacturerPurchase = new ManufacturerPurchase();
        BeanUtils.copyProperties(manufacturerPurchaseDTO,manufacturerPurchase);
        QueryWrapper<ManufacturerPurchase> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",manufacturerPurchase.getCompId())
                .eq("manufacturer_id",manufacturerPurchase.getManufacturerId());
        manufacturerPurchaseService.remove(wrapper);
        boolean save = manufacturerPurchaseService.save(manufacturerPurchase);
        //TODO 获取登录用户名

        return AjaxResult.success(save);
    }
    /**
     *厂商报支关系资料表新增板块资料
     */
    @Log(title = "厂商报支关系资料表新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData4")
    public Object add4(@RequestBody ManufacturerFinanceDTO manufacturerFinanceDTO, @RequestHeader("token") String token){
        ManufacturerFinance manufacturerFinance = new ManufacturerFinance();
        BeanUtils.copyProperties(manufacturerFinanceDTO,manufacturerFinance);
        QueryWrapper<ManufacturerFinance> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",manufacturerFinance.getCompId())
                .eq("manufacturer_id",manufacturerFinance.getManufacturerId());
        manufacturerFinanceService.remove(wrapper);
        boolean save = manufacturerFinanceService.save(manufacturerFinance);
        //TODO 获取登录用户名

        return AjaxResult.success(save);
    }
    /**
     * 厂商承运关系资料表新增板块资料
     */
    @Log(title = "厂商承运关系资料表新增板块资料", businessType = BusinessType.INSERT)
    @Operation(summary = "新增板块资料")
    @PostMapping("/addBoardData5")
    public Object add5(@RequestBody ManufacturerTransportDTO manufacturerTransportDTO, @RequestHeader("token") String token){
        ManufacturerTransport manufacturerTransport = new ManufacturerTransport();
        BeanUtils.copyProperties(manufacturerTransportDTO,manufacturerTransport);
        QueryWrapper<ManufacturerTransport> wrapper = new QueryWrapper<>();
        wrapper.eq("comp_id",manufacturerTransport.getCompId())
                .eq("manufacturer_id",manufacturerTransport.getManufacturerId());
        manufacturerTransportService.remove(wrapper);
        boolean save = manufacturerTransportService.save(manufacturerTransport);
        //TODO 获取登录用户名

        return AjaxResult.success(save);
    }


    /**
     * 修改板块资料
     */
    @Log(title = "修改板块资料", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改板块资料")
    @PutMapping("/updateBoardData")
    public Object updateBoardData(@RequestBody FinanceperDTO financeperDTO, @RequestHeader("token") String token){
        ManufacturerTree manufacturerTree = new ManufacturerTree();
        BeanUtils.copyProperties(financeperDTO,manufacturerTree);
        ManufacturerTree board = manufacturerTreeService.getById(manufacturerTree.getId());
        if (StringUtils.isNotNull(board)) {
            boolean update = manufacturerTreeService.updateById(manufacturerTree);
            return AjaxResult.success(update);
        } else {
            boolean save = manufacturerTreeService.save(manufacturerTree);
            return AjaxResult.success(save);
        }
        //TODO 获取登录用户名
    }

    /**
     * 删除板块资料
     */
    @Log(title = "删除板块资料", businessType = BusinessType.DELETE)
    @Operation(summary = "删除板块资料")
    @DeleteMapping("/deleteBoardData")
    public Object deleteBoardData(@RequestBody FinanceperDTO financeperDTO, @RequestHeader("token") String token){
        ManufacturerTree manufacturerTree = new ManufacturerTree();
        BeanUtils.copyProperties(financeperDTO,manufacturerTree);
        boolean delete = manufacturerTreeService.removeById(manufacturerTree);
        //TODO 获取登录用户名
        return AjaxResult.success(delete);
    }
}


