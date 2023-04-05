package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionCokeItemYieldEntity;
import com.jlkj.product.oi.domain.ProductionMaterialsCokeStock;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldInsertDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldQueryDTO;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldUpdateDTO;
import com.jlkj.product.oi.mapper.ProductionCokeItemYieldMapper;
import com.jlkj.product.oi.service.ProductionCokeItemYieldService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zzh
 * @datetime 2022/9/9 13:52
 * @description 煤焦分项产量服务的实现类
 */

@Service
public class ProductionCokeItemYieldServiceImpl extends ServiceImpl<ProductionCokeItemYieldMapper, ProductionCokeItemYieldEntity> implements ProductionCokeItemYieldService {


    public IPage<Map<String, Object>> getCokeItemYieldList(CokeItemYieldQueryDTO dto) {
        Date startDate = DateUtil.parseDate(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime()).setField(Calendar.HOUR_OF_DAY, 0).setField(Calendar.MINUTE, 0).setField(Calendar.SECOND, 0);
        Date endDate = DateUtil.parseDate(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime()).setField(Calendar.HOUR_OF_DAY, 23).setField(Calendar.MINUTE, 59).setField(Calendar.SECOND, 59);
        QueryWrapper<ProductionCokeItemYieldEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionCokeItemYieldEntity::getCreateTime, startDate)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionCokeItemYieldEntity::getCreateTime, endDate)
                .eq(!StrUtil.isEmpty(dto.getMaterialsCode()), ProductionCokeItemYieldEntity::getMaterialsCode, dto.getMaterialsCode());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return pageMaps(page, queryWrapper);
    }
    public IPage<Map<String, Object>> getCokeItemYieldList2(CokeItemYieldQueryDTO dto) {
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getCokeItemYieldList2(page, dto);
    }


    public Object insertCokeItemYield(CokeItemYieldInsertDTO dto) {
        /**
         * 1. 按照时间在product_oi_coke_records表中查找所在时间段都产了什么焦炭
         * 2. 如果是只有一种焦炭,直接在t_materials_coke_stock表中减掉焦粉焦粒的重量,
         *    如果是多种焦炭,在t_materials_coke_stock表中分别找出每个焦炭对应的重量, 计算出每个焦炭的重量比例, 分别减掉 比例×焦粉焦粒的重量
         */

        List<Object> productionCokeRecordsName = getBaseMapper().getProductionCokeMaterialsNameByTime(dto.getTime());
        if (productionCokeRecordsName == null || productionCokeRecordsName.size() == 0) {
            return AjaxResult.error("当前时间段没有焦炭记录");
        }

        if (productionCokeRecordsName.size() == 1) {
            //如果查找到的焦炭产量记录只有一种焦炭的话, 直接在焦炭库存表中找到该焦炭的总库存, 减去焦丁焦末总量
            ProductionMaterialsCokeStock cokeStock = getBaseMapper().getMaterialsCokeStockOneByMaterialName(productionCokeRecordsName.get(0).toString());
            if (cokeStock == null) {
                return AjaxResult.error("在库存中没找名为" + productionCokeRecordsName.get(0).toString() + "的焦炭");
            }
            //用原库存 - 焦丁焦末的重量
            BigDecimal inventory = cokeStock.getInventory();
            BigDecimal rInventory = inventory.subtract(dto.getYield());
            cokeStock.setInventory(rInventory);
            //对焦炭库存中的对应ID的数据进行修改
            boolean isOk = getBaseMapper().updateMaterialsCokeStockById(cokeStock);

            //用原库存的焦丁焦末重量 + 传入的产量
            ProductionMaterialsCokeStock cokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getMaterialsName());
            BigDecimal inventoryDm = cokeStockDm.getInventory().add(dto.getYield());
            cokeStockDm.setInventory(inventoryDm);
            //对焦炭库存中的对应ID的数据进行修改
            boolean isOkDm = getBaseMapper().updateMaterialsCokeStockById(cokeStockDm);

            if (isOk && isOkDm) {
                //分项产量中插入一条记录
                ProductionCokeItemYieldEntity entity = new ProductionCokeItemYieldEntity();
                entity.setId(IdUtil.randomUUID());
                entity.setCreateTime(dto.getTime());
                entity.setMaterialsCode(dto.getMaterialsCode());
                entity.setMaterialsName(dto.getMaterialsName());
                entity.setYield(dto.getYield());
                save(entity);
            }
        } else {
            List<ProductionMaterialsCokeStock> cokeStockList = getBaseMapper().getMaterialsCokeStockMultipleByMaterialName(productionCokeRecordsName);
            if (cokeStockList != null && cokeStockList.size() > 0) {
                BigDecimal totalInventory = BigDecimal.valueOf(0);
                for (ProductionMaterialsCokeStock i : cokeStockList) {
                    totalInventory = totalInventory.add(i.getInventory());
                }

                for (ProductionMaterialsCokeStock stock : cokeStockList) {
                    BigDecimal rate = stock.getInventory().divide(totalInventory, 3, RoundingMode.HALF_UP);
                    BigDecimal itemInventory = stock.getInventory().subtract(dto.getYield().multiply(rate));
                    stock.setInventory(itemInventory);
                    getBaseMapper().updateMaterialsCokeStockById(stock);
                }

                //用原库存的焦丁焦末重量 + 传入的产量
                ProductionMaterialsCokeStock cokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getMaterialsName());
                BigDecimal inventoryDm = cokeStockDm.getInventory().add(dto.getYield());
                cokeStockDm.setInventory(inventoryDm);
                //对焦炭库存中的对应ID的数据进行修改
                boolean isOkDm = getBaseMapper().updateMaterialsCokeStockById(cokeStockDm);

                if (isOkDm) {
                    //分项产量中插入一条记录
                    ProductionCokeItemYieldEntity entity = new ProductionCokeItemYieldEntity();
                    entity.setId(IdUtil.randomUUID());
                    entity.setCreateTime(dto.getTime());
                    entity.setMaterialsCode(dto.getMaterialsCode());
                    entity.setMaterialsName(dto.getMaterialsName());
                    entity.setYield(dto.getYield());
                    save(entity);
                }
            }
        }
        return AjaxResult.success("保存成功");
    }

    public Object updateCokeItemYield(CokeItemYieldUpdateDTO dto) {
        /**
         *
         * 0. 判断
         *      修改的只是物料名称的话, 就不需要对库存中的焦炭产量进行还原重计算
         *          1. 接收到原物料名称后, 在t_materials_coke_stock表中减掉原物料的对应重量, 在新物料中把重量加上
         *          2. 修改分项产量记录表中, 对数据进行修改
         *
         *      修改的只要包含时间或者产量的话, 就需要对库存中的焦炭重量进行还原重计算
         *          1. 按时间在product_oi_coke_records表中查找所在时间段都产了什么焦炭
         *          2. 如果是一种焦炭, 直接加上之前被减掉的产量, 也就是参数中的产量
         *             如果是两种或者两种以上焦炭, 按照比例分别把之前减掉的重量加回去, 也就是参数*比例
         *          3. 在对应的焦丁焦末的产量上减去修改之前的产量
         *          4. 如果是一种焦炭, 直接减掉修改前的焦丁焦末产量
         *             如果是两种及以上的焦炭, 按照比例分别减掉焦丁焦末的重量
         *          5. 在对应焦丁焦末的产量上加上修改后的焦丁焦末产量
         *
         */

        //如果只是修改物料名称
        if (dto.getOldTime().equals(dto.getTime()) && dto.getOldYield().equals(dto.getYield()) && !dto.getOldMaterialsName().equals(dto.getMaterialsName())) {
            BigDecimal jDYield = dto.getYield();

            //找到旧的焦丁焦末, 把产量减回去
            ProductionMaterialsCokeStock oldCokeStock = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getOldMaterialsName());
            if (oldCokeStock == null) {
                return AjaxResult.error("库存中为找到" + dto.getOldMaterialsName());
            }
            oldCokeStock.setInventory(oldCokeStock.getInventory().subtract(jDYield));
            getBaseMapper().updateMaterialsCokeStockById(oldCokeStock);

            //找到新的焦丁焦末, 把产量加上
            ProductionMaterialsCokeStock newCokeStock = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getMaterialsName());
            if (newCokeStock == null) {
                return AjaxResult.error("库存中为找到" + dto.getOldMaterialsName());
            }
            newCokeStock.setInventory(newCokeStock.getInventory().add(jDYield));
            getBaseMapper().updateMaterialsCokeStockById(newCokeStock);
        } else {
            List<Object> oldProductionCokeRecordsName = getBaseMapper().getProductionCokeMaterialsNameByTime(dto.getOldTime());
            if (oldProductionCokeRecordsName == null || oldProductionCokeRecordsName.size() == 0) {
                return AjaxResult.error("当前时间段没有焦炭记录");
            }
            if (oldProductionCokeRecordsName.size() == 1) {
                //如果查找到的焦炭产量记录只有一种焦炭的话, 直接在焦炭库存表中找到该焦炭的总库存, 减去焦丁焦末总量
                ProductionMaterialsCokeStock oldCokeStock = getBaseMapper().getMaterialsCokeStockOneByMaterialName(oldProductionCokeRecordsName.get(0).toString());
                if (oldCokeStock == null) {
                    return AjaxResult.error("在库存中没找名为" + oldProductionCokeRecordsName.get(0).toString() + "的焦炭");
                }
                //用原库存 + 修改之前的值
                BigDecimal inventory = oldCokeStock.getInventory();
                BigDecimal rInventory = inventory.add(dto.getOldYield());
                oldCokeStock.setInventory(rInventory);
                //对焦炭库存中的对应ID的数据进行修改
                getBaseMapper().updateMaterialsCokeStockById(oldCokeStock);

                //用原库存的焦丁焦末重量 - 修改之前的产量
                ProductionMaterialsCokeStock oldCokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getOldMaterialsName());
                BigDecimal inventoryDm = oldCokeStockDm.getInventory().subtract(dto.getOldYield());
                oldCokeStockDm.setInventory(inventoryDm);
                //对焦炭库存中的对应ID的数据进行修改
                getBaseMapper().updateMaterialsCokeStockById(oldCokeStockDm);
            } else {
                List<ProductionMaterialsCokeStock> oldCokeStockList = getBaseMapper().getMaterialsCokeStockMultipleByMaterialName(oldProductionCokeRecordsName);
                if (oldCokeStockList != null && oldCokeStockList.size() > 0) {
                    BigDecimal totalInventory = BigDecimal.valueOf(0);
                    for (ProductionMaterialsCokeStock i : oldCokeStockList) {
                        totalInventory = totalInventory.add(i.getInventory());
                    }

                    for (ProductionMaterialsCokeStock stock : oldCokeStockList) {
                        BigDecimal rate = stock.getInventory().divide(totalInventory, 3, RoundingMode.HALF_UP);
                        BigDecimal itemInventory = stock.getInventory().add(dto.getOldYield().multiply(rate));
                        stock.setInventory(itemInventory);
                        getBaseMapper().updateMaterialsCokeStockById(stock);
                    }

                    //用原库存的焦丁焦末重量 + 传入的产量
                    ProductionMaterialsCokeStock oldCokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getOldMaterialsName());
                    BigDecimal inventoryDm = oldCokeStockDm.getInventory().subtract(dto.getOldYield());
                    oldCokeStockDm.setInventory(inventoryDm);
                    //对焦炭库存中的对应ID的数据进行修改
                    getBaseMapper().updateMaterialsCokeStockById(oldCokeStockDm);
                }
            }
            //用新的值从新对库存进行修改
            List<Object> newProductionCokeRecordsName = getBaseMapper().getProductionCokeMaterialsNameByTime(dto.getTime());
            if (newProductionCokeRecordsName == null || newProductionCokeRecordsName.size() == 0) {
                return AjaxResult.error("当前时间段没有焦炭记录");
            }
            if (newProductionCokeRecordsName.size() == 1) {
                //如果查找到的焦炭产量记录只有一种焦炭的话, 直接在焦炭库存表中找到该焦炭的总库存, 减去焦丁焦末总量
                ProductionMaterialsCokeStock newCokeStock = getBaseMapper().getMaterialsCokeStockOneByMaterialName(newProductionCokeRecordsName.get(0).toString());
                if (newCokeStock == null) {
                    return AjaxResult.error("在库存中没找名为" + newProductionCokeRecordsName.get(0).toString() + "的焦炭");
                }
                //用原库存 - 焦丁焦末的重量
                BigDecimal inventory = newCokeStock.getInventory();
                BigDecimal rInventory = inventory.subtract(dto.getYield());
                newCokeStock.setInventory(rInventory);
                //对焦炭库存中的对应ID的数据进行修改
                boolean isOk = getBaseMapper().updateMaterialsCokeStockById(newCokeStock);

                //用原库存的焦丁焦末重量 + 传入的产量
                ProductionMaterialsCokeStock cokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getMaterialsName());
                BigDecimal newInventoryDm = cokeStockDm.getInventory().add(dto.getYield());
                cokeStockDm.setInventory(newInventoryDm);
                //对焦炭库存中的对应ID的数据进行修改
                boolean isOkDm = getBaseMapper().updateMaterialsCokeStockById(cokeStockDm);
            } else {
                List<ProductionMaterialsCokeStock> cokeStockList = getBaseMapper().getMaterialsCokeStockMultipleByMaterialName(newProductionCokeRecordsName);
                if (cokeStockList != null && cokeStockList.size() > 0) {
                    BigDecimal newTotalInventory = BigDecimal.valueOf(0);
                    for (ProductionMaterialsCokeStock i : cokeStockList) {
                        newTotalInventory = newTotalInventory.add(i.getInventory());
                    }

                    for (ProductionMaterialsCokeStock stock : cokeStockList) {
                        BigDecimal rate = stock.getInventory().divide(newTotalInventory, 3, RoundingMode.HALF_UP);
                        BigDecimal itemInventory = stock.getInventory().subtract(dto.getYield().multiply(rate));
                        stock.setInventory(itemInventory);
                        getBaseMapper().updateMaterialsCokeStockById(stock);
                    }

                    //用原库存的焦丁焦末重量 + 传入的产量
                    ProductionMaterialsCokeStock cokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(dto.getMaterialsName());
                    BigDecimal newInventoryDm = cokeStockDm.getInventory().add(dto.getYield());
                    cokeStockDm.setInventory(newInventoryDm);
                    //对焦炭库存中的对应ID的数据进行修改
                    boolean isOkDm = getBaseMapper().updateMaterialsCokeStockById(cokeStockDm);
                }
            }
        }
        ProductionCokeItemYieldEntity entity = getById(dto.getId());
        if (entity != null) {
            entity.setCreateTime(dto.getTime());
            entity.setMaterialsName(dto.getMaterialsName());
            entity.setMaterialsCode(dto.getMaterialsCode());
            entity.setYield(dto.getYield());
            updateById(entity);
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("数据不存在");
        }
    }

    public Object deleteCokeItemYield(String id) {
        ProductionCokeItemYieldEntity entity = getById(id);
        if (entity != null) {
            List<Object> oldProductionCokeRecordsName = getBaseMapper().getProductionCokeMaterialsNameByTime(entity.getCreateTime());
            if (oldProductionCokeRecordsName.size() == 1) {
                //如果查找到的焦炭产量记录只有一种焦炭的话, 直接在焦炭库存表中找到该焦炭的总库存, 减去焦丁焦末总量
                ProductionMaterialsCokeStock oldCokeStock = getBaseMapper().getMaterialsCokeStockOneByMaterialName(oldProductionCokeRecordsName.get(0).toString());
                if (oldCokeStock == null) {
                    return AjaxResult.error("在库存中没找名为" + oldProductionCokeRecordsName.get(0).toString() + "的焦炭");
                }
                //用原库存 + 修改之前的值
                BigDecimal inventory = oldCokeStock.getInventory();
                BigDecimal rInventory = inventory.add(entity.getYield());
                oldCokeStock.setInventory(rInventory);
                //对焦炭库存中的对应ID的数据进行修改
                getBaseMapper().updateMaterialsCokeStockById(oldCokeStock);

                //用原库存的焦丁焦末重量 - 修改之前的产量
                ProductionMaterialsCokeStock oldCokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(entity.getMaterialsName());
                BigDecimal inventoryDm = oldCokeStockDm.getInventory().subtract(entity.getYield());
                oldCokeStockDm.setInventory(inventoryDm);
                //对焦炭库存中的对应ID的数据进行修改
                getBaseMapper().updateMaterialsCokeStockById(oldCokeStockDm);
            } else {
                List<ProductionMaterialsCokeStock> oldCokeStockList = getBaseMapper().getMaterialsCokeStockMultipleByMaterialName(oldProductionCokeRecordsName);
                if (oldCokeStockList != null && oldCokeStockList.size() > 0) {
                    BigDecimal totalInventory = BigDecimal.valueOf(0);
                    for (ProductionMaterialsCokeStock i : oldCokeStockList) {
                        totalInventory = totalInventory.add(i.getInventory());
                    }

                    for (ProductionMaterialsCokeStock stock : oldCokeStockList) {
                        BigDecimal rate = stock.getInventory().divide(totalInventory, 3, RoundingMode.HALF_UP);
                        BigDecimal itemInventory = stock.getInventory().add(entity.getYield().multiply(rate));
                        stock.setInventory(itemInventory);
                        getBaseMapper().updateMaterialsCokeStockById(stock);
                    }

                    //用原库存的焦丁焦末重量 + 传入的产量
                    ProductionMaterialsCokeStock oldCokeStockDm = getBaseMapper().getMaterialsCokeStockOneByMaterialName(entity.getMaterialsName());
                    BigDecimal inventoryDm = oldCokeStockDm.getInventory().subtract(entity.getYield());
                    oldCokeStockDm.setInventory(inventoryDm);
                    //对焦炭库存中的对应ID的数据进行修改
                    getBaseMapper().updateMaterialsCokeStockById(oldCokeStockDm);
                }
            }
            removeById(entity);
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("数据不存在");
        }

    }
}
