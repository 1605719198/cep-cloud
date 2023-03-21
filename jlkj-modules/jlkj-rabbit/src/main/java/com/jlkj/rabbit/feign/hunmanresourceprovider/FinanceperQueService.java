package com.jlkj.rabbit.feign.hunmanresourceprovider;/*
package com.jljl.messagecenter.feign.hunmanresourceprovider;

import com.jljl.common.dto.financeperdto.*;
import com.jljl.common.dto.hunmanresourcedto.HumanresourceBoardDTO;
import com.jljl.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(value = "finance-service-provider")
public interface FinanceperQueService {

    */
/**
     * 厂商基本资料新增板块资料
     *//*

    @PostMapping("/financepr/board/addBoardData1")
    public Result add1(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO);
    */
/**
     * 厂商基本资料查询板块资料
     *//*

    @PostMapping("/financepr/board/queryID")
    public Result queryID(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO);
    */
/**
     * 厂商基本资料删除板块资料
     *//*

    @PostMapping("/financepr/board/deleteId")
    public Result deleteId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO);
    */
/**
     * 关系新增板块资料
     *//*

    @PostMapping("/financepr/board/addBoardData")
    public Result add(@RequestBody ManufacturerRelationDTO manufacturerRelationDTO);
    */
/**
     * 客户关系新增板块资料
     *//*

    @PostMapping("/financepr/board/addBoardData2")
    public Result add2(@RequestBody ManufacturerCustomerDTO manufacturerCustomerDTO);
    */
/**
     * 采购关系新增板块资料
     *//*

    @PostMapping("/financepr/board/addBoardData3")
    public Result add3(@RequestBody ManufacturerPurchaseDTO manufacturerPurchaseDTO);
    */
/**
     * 新增板块资料
     *//*

    @PostMapping("/financepr/board/addBoardData4")
    public Result add4(@RequestBody  ManufacturerFinanceDTO manufacturerFinanceDTO);
    */
/**
     * 新增板块资料
     *//*

    @PostMapping("/financepr/board/addBoardData5")
    public Result add5(@RequestBody  ManufacturerTransportDTO manufacturerTransportDTO);
    */
/**
     * 修改板块资料
     *//*

    @PutMapping("/financepr/board/updateBoardData")
    public Result updateBoardData(@RequestBody FinanceperDTO financeperDTO);

    */
/**
     * 删除板块资料
     *//*

    @DeleteMapping("/financepr/board/deleteBoardData")
    public Result deleteBoardData(@RequestBody FinanceperDTO financeperDTO);
}
*/
