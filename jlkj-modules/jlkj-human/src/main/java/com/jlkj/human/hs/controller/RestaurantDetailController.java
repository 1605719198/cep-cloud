package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.RestaurantDetail;
import com.jlkj.human.hs.service.IRestaurantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 餐饮消费明细Controller
 *
 * @author 266861
 * @date 2023-06-19
 */
@RestController
@RequestMapping("/restaurantDetail")
public class RestaurantDetailController extends BaseController
{
    @Autowired
    private IRestaurantDetailService restaurantDetailService;


    /**
    * @Description 查询餐饮消费明细列表
    * @param restaurantDetail 餐饮消费明细列表查询参数
    * @return 查询结果
    * @author 266861
    * @date 2023/6/21 15:05
    **/
    @RequiresPermissions("human:restaurantDetail:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated RestaurantDetail restaurantDetail)
    {
        startPage();
        List<RestaurantDetail> list = restaurantDetailService.selectRestaurantDetailList(restaurantDetail);
        return success(getDataTable(list));
    }


}
