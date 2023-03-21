package com.jlkj.rabbit.feign.salesprovider;

import com.jlkj.common.core.domain.R;
import com.jlkj.common.dto.sales.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 智能研发室 xk
 * @Description 销售模块远程调用
 * @date 2022/5/11 - 9:01
 */
@Component
@FeignClient(value = "sales-service-provider")
public interface SalesFeignService {

    /**
     * 新增合同条款主档
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesArticleMasterDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/article/master/insert")
    R addSalesArticleMaster(@Valid @RequestBody SalesArticleMasterDTO salesArticleMasterDTO, @RequestHeader("token") String token);
    /**
     * 删除合同条款主档
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/article/master/delete/{ids}")
    R deleteSalesArticleMaster(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增合同条款明细档
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesArticleSecondDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/article/second/insert")
    R addSalesArticleSecond(@Valid @RequestBody SalesArticleSecondDTO salesArticleSecondDTO, @RequestHeader("token") String token);
    /**
     * 修改合同条款明细档
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesArticleSecondDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/article/second/update")
    R updateSalesArticleSecond(@Valid @RequestBody SalesArticleSecondDTO salesArticleSecondDTO, @RequestHeader("token") String token);
    /**
     * 删除合同条款明细档
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/article/second/delete/erp/{ids}")
    R deleteSalesArticleSecond(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增终到地区
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportEndAreaDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/transport/end/area/add")
    R addSalesTransportEndArea(@Valid @RequestBody SalesTransportEndAreaDTO salesTransportEndAreaDTO, @RequestHeader("token") String token);
    /**
     * 修改终到地区
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportEndAreaDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/transport/end/area/update")
    R updateSalesTransportEndArea(@Valid @RequestBody SalesTransportEndAreaDTO salesTransportEndAreaDTO, @RequestHeader("token") String token);
    /**
     * 删除终到地区
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/transport/end/area/delete/{ids}")
    R deleteSalesTransportEndArea(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增汽运交运地
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportSiteDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/transport/site/motore/add")
    R addSalesTransportSite(@Valid @RequestBody SalesTransportSiteDTO salesTransportSiteDTO, @RequestHeader("token") String token);
    /**
     * 修改汽运交运地
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportSiteDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/transport/site/motore/update")
    R updateSalesTransportSite(@Valid @RequestBody SalesTransportSiteDTO salesTransportSiteDTO, @RequestHeader("token") String token);
    /**
     * 删除汽运交运地
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/transport/site/motore/delete/{ids}")
    R deleteSalesTransportSite(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增铁路到局
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportSiteDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/transport/site/railway/add")
    R addSalesTransportSiteRailWay(@Valid @RequestBody SalesTransportSiteDTO salesTransportSiteDTO, @RequestHeader("token") String token);
    /**
     * 修改铁路到局
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportSiteDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/transport/site/railway/update")
    R updateSalesTransportSiteRailWay(@Valid @RequestBody SalesTransportSiteDTO salesTransportSiteDTO, @RequestHeader("token") String token);
    /**
     * 删除铁路到局
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/transport/site/railway/delete/{ids}")
    R deleteSalesTransportSiteRailWay(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增铁路到站
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportStationDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/transport/station/railway/add")
    R addSalesTransportStationRailWay(@Valid @RequestBody SalesTransportStationDTO salesTransportStationDTO,@RequestHeader("token") String token);


    /**
     * 修改铁路到站
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportStationDTO
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/transport/station/railway/update")
    R updateSalesTransportStationRailWay(@Valid @RequestBody SalesTransportStationDTO salesTransportStationDTO);
    /**
     * 删除铁路到站
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/transport/station/railway/delete/{ids}")
    R deleteSalesTransportStationRailWay(@PathVariable String[] ids);

    /**
     * 新增专用线
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportSpecialLineDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/transport/special/line/add")
    R addSalesTransportSpecialLine(@Valid @RequestBody SalesTransportSpecialLineDTO salesTransportSpecialLineDTO, @RequestHeader("token") String token);
    /**
     * 修改铁专用线
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesTransportSpecialLineDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/transport/special/line/update")
    R updateSalesTransportSpecialLine(@Valid @RequestBody SalesTransportSpecialLineDTO salesTransportSpecialLineDTO, @RequestHeader("token") String token);
    /**
     * 删除铁专用线
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/transport/special/line/delete/{ids}")
    R deleteSalesTransportSpecialLine(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增收货人信息
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesDeliveryReceiverDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/delivery/receiver/insert")
    R addSalesDeliveryReceiver(@Valid @RequestBody SalesDeliveryReceiverDTO salesDeliveryReceiverDTO, @RequestHeader("token") String token);
    /**
     * 修改收货人信息
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesDeliveryReceiverDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/delivery/receiver/update")
    R updateSalesDeliveryReceiver(@Valid @RequestBody SalesDeliveryReceiverDTO salesDeliveryReceiverDTO, @RequestHeader("token") String token);
    /**
     * 删除收货人信息
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/delivery/receiver/delete/erp/{ids}")
    R deleteSalesDeliveryReceiver(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增指送地点
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesDeliveryPlaceDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/delivery/place/add")
    R addSalesDeliveryPlace(@Valid @RequestBody SalesDeliveryPlaceDTO salesDeliveryPlaceDTO, @RequestHeader("token") String token);
    /**
     * 修改指送地点
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesDeliveryPlaceDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/delivery/place/update")
    R updateSalesDeliveryPlace(@Valid @RequestBody SalesDeliveryPlaceDTO salesDeliveryPlaceDTO, @RequestHeader("token") String token);
    /**
     * 删除指送地点
     * @author xk
     * @date 2022/6/8 8:49
     * @param ids
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @DeleteMapping("/sales/delivery/place/delete/erp/{ids}")
    R deleteSalesDeliveryPlace(@PathVariable String[] ids, @RequestHeader("token") String token);

    /**
     * 新增合同
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesContractDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/contract/insert")
    R addSalesContract(@Valid @RequestBody SalesContractDTO salesContractDTO, @RequestHeader("token") String token);

    /**
     * 修改合同
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesContractDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/contract/update")
    R updateSalesContract(@Valid @RequestBody SalesContractDTO salesContractDTO, @RequestHeader("token") String token);

    /**
     * 新增订单
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesOrderDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PostMapping("/sales/order/insert")
    R addSalesOrder(@Valid @RequestBody SalesOrderDTO salesOrderDTO, @RequestHeader("token") String token);

    /**
     * 修改订单
     * @author xk
     * @date 2022/6/8 8:08
     * @param salesOrderDTO
     * @param token
     * @return com.jljl.common.resp.Result
     */
    @PutMapping("/sales/order/update")
    R updateSalesOrder(@Valid @RequestBody SalesOrderDTO salesOrderDTO, @RequestHeader("token") String token);
}
