package com.jlkj.rabbit.feign.financeprovider;

import com.jlkj.common.dto.finance.gp.*;
import com.jlkj.common.dto.finance.ip.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang'mai
 */
@Component
@FeignClient(value = "finance-service-provider")
public interface FinanceperQueService {

    /**
     * 厂商基本资料树新增板块资料
     * @param manufacturerTreeDTO 厂商树DTO
     * @param token
     * @return Object addTree(@RequestBody ManufacturerTreeDTO manufacturerTreeDTO)
     */
    @PostMapping("/financepr/board/addTree")
  Object addTree(@RequestBody ManufacturerTreeDTO manufacturerTreeDTO, @RequestHeader("token") String token);

    /**
     * 厂商基本资料新增板块资料
     * @param manufacturerBaseDTO  厂商DTO
     * @param token
     * @return Object add1(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO);
     */
    @PostMapping("/financepr/board/addBoardData1")
     Object add1(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO, @RequestHeader("token") String token);
    /**
     * 厂商基本资料查询板块资料
     * @param manufacturerBaseDTO 厂商DTO
     *  @param token
     * @return Object queryId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO);
     */
    @PostMapping("/financepr/board/queryId")
     Object queryId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO, @RequestHeader("token") String token);
    /**
     * 厂商基本资料删除板块资料
     * @param manufacturerBaseDTO 厂商DTO
     *  @param token
     * @return  Object deleteId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO);
     */
    @PostMapping("/financepr/board/deleteId")
     Object deleteId(@RequestBody ManufacturerBaseDTO manufacturerBaseDTO, @RequestHeader("token") String token);
    /**
     * 关系新增板块资料
     * @param manufacturerRelationDTO 厂商关系DTO
     *   @param token
     * @return Object add(@RequestBody ManufacturerRelationDTO manufacturerRelationDTO);
     */
    @PostMapping("/financepr/board/addBoardData")
     Object add(@RequestBody ManufacturerRelationDTO manufacturerRelationDTO, @RequestHeader("token") String token);
    /**
     * 客户关系新增板块资料
     * @param manufacturerCustomerDTO 厂商客户关系DTO
     *   @param token
     * @return Object add2(@RequestBody ManufacturerCustomerDTO manufacturerCustomerDTO);
     */
    @PostMapping("/financepr/board/addBoardData2")
     Object add2(@RequestBody ManufacturerCustomerDTO manufacturerCustomerDTO, @RequestHeader("token") String token);
    /**
     * 采购关系新增板块资料
     * @param manufacturerPurchaseDTO 厂商采购关系DTO
     *   @param token
     * @return Object add3(@RequestBody ManufacturerPurchaseDTO manufacturerPurchaseDTO);
     */
    @PostMapping("/financepr/board/addBoardData3")
     Object add3(@RequestBody ManufacturerPurchaseDTO manufacturerPurchaseDTO, @RequestHeader("token") String token);
    /**
     * 新增板块资料
     * @param manufacturerFinanceDTO 厂商报支关系DTO
     *   @param token
     * @return Object add4(@RequestBody  ManufacturerFinanceDTO manufacturerFinanceDTO);
     */
    @PostMapping("/financepr/board/addBoardData4")
     Object add4(@RequestBody ManufacturerFinanceDTO manufacturerFinanceDTO, @RequestHeader("token") String token);
    /**
     * 新增板块资料
     * @param manufacturerTransportDTO 厂商承运关系DTO
     *   @param token
     * @return Object add5(@RequestBody  ManufacturerTransportDTO manufacturerTransportDTO);
     */
    @PostMapping("/financepr/board/addBoardData5")
     Object add5(@RequestBody ManufacturerTransportDTO manufacturerTransportDTO, @RequestHeader("token") String token);
    /**
     * 修改板块资料
     * @param financeperDTO 厂商树DTO
     *   @param token
     * @return Object updateBoardData(@RequestBody FinanceperDTO financeperDTO);
     */
    @PutMapping("/financepr/board/updateBoardData")
     Object updateBoardData(@RequestBody FinanceperDTO financeperDTO, @RequestHeader("token") String token);

    /**
     * 删除板块资料
     * @param financeperDTO 厂商树DTO
     *   @param token
     * @return Object deleteBoardData(@RequestBody FinanceperDTO financeperDTO);
     */
    @DeleteMapping("/financepr/board/deleteBoardData")
     Object deleteBoardData(@RequestBody FinanceperDTO financeperDTO, @RequestHeader("token") String token);

    /**
     * 成本中心新增板块资料
     * @param financeperCostDTO 成本中心DTO
     *   @param token
     * @return Object add(@RequestBody FinanceperCostDTO financeperCostDTO);
     */
    @PostMapping("/financepr/cost/addBoardData")
     Object add(@RequestBody FinanceperCostDTO financeperCostDTO, @RequestHeader("token") String token);
    /**
     * 成本中心修改板块资料
     * @param financeperCostDTO 成本中心DTO
     *   @param token
     * @return Object updateBoardData(@RequestBody FinanceperCostDTO financeperCostDTO);
     */
    @PostMapping("/financepr/cost/updateBoardData")
     Object updateBoardData(@RequestBody FinanceperCostDTO financeperCostDTO, @RequestHeader("token") String token);
    /**
     * 成本中心删除板块资料
     * @param financeperCostDTO 成本中心DTO
     *   @param token
     * @return Object deleteBoardData(@RequestBody FinanceperCostDTO financeperCostDTO);
     */
    @PostMapping("/financepr/cost/deleteBoardData")
     Object deleteBoardData(@RequestBody FinanceperCostDTO financeperCostDTO, @RequestHeader("token") String token);

   /**
    * 成本科目新增板块资料
    * @param financeperCodeDTO 成本科目DTO
    *   @param token
    * @return Object addCode(@RequestBody FinanceperCodeDTO financeperCodeDTO);
    */
   @PostMapping("/financepr/code/addCode")
    Object addCode(@RequestBody FinanceperCodeDTO financeperCodeDTO, @RequestHeader("token") String token);
   /**
    * 成本科目修改板块资料
    * @param financeperCodeDTO 成本科目DTO
    *   @param token
    * @return Object updateBoardCode(@RequestBody FinanceperCodeDTO financeperCodeDTO);
    */
   @PostMapping("/financepr/code/updateBoardCode")
    Object updateBoardCode(@RequestBody FinanceperCodeDTO financeperCodeDTO, @RequestHeader("token") String token);
   /**
    * 成本科目删除板块资料
    * @param financeperCodeDTO 成本科目DTO
    *   @param token
    * @return Object deleteBoardCode(@RequestBody FinanceperCodeDTO financeperCodeDTO);
    */
   @PostMapping("/financepr/code/deleteBoardCode")
    Object deleteBoardCode(@RequestBody FinanceperCodeDTO financeperCodeDTO, @RequestHeader("token") String token);

   /**
    * 成本科目树新增板块资料
    * @param financeperMenuDTO 成本科目DTO
    *   @param token
    * @return Object addMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO);
    */
   @PostMapping("/financepr/menu/addMenu")
    Object addMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO, @RequestHeader("token") String token);
   /**
    * 成本科目树修改板块资料
    * @param financeperMenuDTO 成本科目DTO
    *   @param token
    * @return Object updateBoardMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO);
    */
   @PostMapping("/financepr/menu/updateBoardMenu")
    Object updateBoardMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO, @RequestHeader("token") String token);
   /**
    * 成本科目树删除板块资料
    * @param financeperMenuDTO 成本科目DTO
    *   @param token
    * @return Object deleteBoardMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO);
    */
   @PostMapping("/financepr/menu/deleteBoardMenu")
    Object deleteBoardMenu(@RequestBody FinanceperMenuDTO financeperMenuDTO, @RequestHeader("token") String token);

    /**
     * 产线新增板块资料
     * @param financeProductMillDTO 产线DTO
     *   @param token
     * @return Object addCode(@RequestBody FinanceProductMillDTO financeProductMillDTO);
     */
    @PostMapping("/finance/product/mill/addMillCode")
    Object addMillCode(@RequestBody FinanceProductMillDTO financeProductMillDTO, @RequestHeader("token") String token);
    /**
     * 产线科目修改板块资料
     * @param financeProductMillDTO 产线DTO
     *   @param token
     * @return Object updateBoardCode(@RequestBody FinanceProductMillDTO financeProductMillDTO);
     */
    @PostMapping("/finance/product/mill/updateMillCode")
    Object updateMillCode(@RequestBody FinanceProductMillDTO financeProductMillDTO, @RequestHeader("token") String token);
    /**
     * 产线科目删除板块资料
     * @param financeProductMillDTO 产线DTO
     *   @param token
     * @return Object deleteBoardCode(@RequestBody FinanceProductMillDTO financeProductMillDTO);
     */
    @PostMapping("/finance/product/mill/deleteMillCode")
    Object deleteMillCode(@RequestBody FinanceProductMillDTO financeProductMillDTO, @RequestHeader("token") String token);
    /**
     * 会计科目新增板块资料
     * @param financeAccountCodeDTO 会计科目DTO
     *   @param token
     * @return Object addAccountCode(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO);
     */
    @PostMapping("/financepr/accountCode/addAccountCode")
     Object addAccountCode(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO, @RequestHeader("token") String token);
    /**
     * 会计科目修改板块资料
     * @param financeAccountCodeDTO 会计科目DTO
     *   @param token
     * @return Object updateBoardAccountCode(@RequestBody  FinanceAccountCodeDTO financeAccountCodeDTO);
     */
    @PostMapping("/financepr/accountCode/updateBoardAccountCode")
     Object updateBoardAccountCode(@RequestBody  FinanceAccountCodeDTO financeAccountCodeDTO, @RequestHeader("token") String token);
    /**
     * 会计科目删除板块资料
     * @param financeAccountCodeDTO 会计科目DTO
     *   @param token
     * @return Object deleteBoardAccountCode(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO);
     */
    @PostMapping("/financepr/accountCode/deleteBoardAccountCode")
     Object deleteBoardAccountCode(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO, @RequestHeader("token") String token);


    /**
     * 会计科目树新增板块资料
     * @param financeAccountCodeDTO 会计科目树DTO
     *   @param token
     * @return Object addAccountCodeTree(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO);
     */
    @PostMapping("/financepr/accountCode/addAccountCodeTree")
     Object addAccountCodeTree(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO, @RequestHeader("token") String token);
    /**
     * 会计科目树修改板块资料
     * @param financeAccountCodeDTO 会计科目树DTO
     *   @param token
     * @return      Object updateBoardAccountCodeTree(@RequestBody  FinanceAccountCodeDTO financeAccountCodeDTO);
     */
    @PostMapping("/financepr/accountCode/updateBoardAccountCodeTree")
     Object updateBoardAccountCodeTree(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO, @RequestHeader("token") String token);
    /**
     * 会计科目树删除板块资料
     * @param financeAccountCodeDTO 会计科目树DTO
     *   @param token
     * @return Object deleteBoardAccountCodeTree(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO);
     */
    @PostMapping("/financepr/accountCode/deleteBoardAccountCodeTree")
     Object deleteBoardAccountCodeTree(@RequestBody FinanceAccountCodeDTO financeAccountCodeDTO, @RequestHeader("token") String token);
}
