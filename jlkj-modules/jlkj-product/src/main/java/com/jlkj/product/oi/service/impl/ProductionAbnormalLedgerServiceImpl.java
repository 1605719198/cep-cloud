package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionAbnormalLedger;
import com.jlkj.product.oi.dto.productionabnormalledger.AddProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.DelProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.GetProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.UpdateProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.mapper.ProductionAbnormalLedgerMapper;
import com.jlkj.product.oi.service.ProductionAbnormalLedgerService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.Map;

import static com.jlkj.product.oi.constants.RedissonUtil.getLock;

/**
* @author zyf
* @description 针对表【product_oi_abnormal_ledger(生产异常台账)】的数据库操作Service实现
* @createDate 2022-05-12 08:30:53
*/
@Service
public class ProductionAbnormalLedgerServiceImpl extends ServiceImpl<ProductionAbnormalLedgerMapper, ProductionAbnormalLedger>
    implements ProductionAbnormalLedgerService {

    @Autowired
    RedissonClient redissonClient;

    public IPage<Map<String, Object>> get(GetProductionAbnormalLedgerDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getPlanStartTime()) ? "1790-01-01" : dto.getPlanStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getPlanEndTime()) ? "1790-01-01" : dto.getPlanEndTime() + " 23:59:59");
        QueryWrapper<ProductionAbnormalLedger> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getPlanStartTime()), ProductionAbnormalLedger::getHappenTime, start)
                .le(!StrUtil.isEmpty(dto.getPlanEndTime()), ProductionAbnormalLedger::getHappenTime, end)
                .like(!StrUtil.isEmpty(dto.getDepartmentName()), ProductionAbnormalLedger::getDepartmentName, dto.getDepartmentName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return pageMaps(page, queryWrapper);
    }

    public Object save(AddProductionAbnormalLedgerDTO dto) {
        Date now = DateUtil.date();
        ProductionAbnormalLedger abnormalLedger = new ProductionAbnormalLedger();
        abnormalLedger.setId(IdUtil.randomUUID());
        abnormalLedger.setDepartmentId(dto.getDepartmentId());
        abnormalLedger.setDepartmentName(dto.getDepartmentName());
        abnormalLedger.setHappenTime(DateUtil.parse(dto.getHappenTime()));
        abnormalLedger.setAccidentName(dto.getAccidentName());
        abnormalLedger.setPreventiveMeasures(dto.getPreventiveMeasures());
        abnormalLedger.setMeasuresCompletionTime(DateUtil.parse(dto.getMeasuresCompletionTime()));
        abnormalLedger.setMeasuresImplementation(dto.getMeasuresImplementation());
        abnormalLedger.setProfessionalResponsiblePerson(dto.getProfessionalResponsible());
        abnormalLedger.setTrackResponsiblePerson(dto.getTrackResponsiblePerson());
        abnormalLedger.setAccidentReportFile(dto.getAccidentReportFile());
        abnormalLedger.setCreateUserId(dto.getCreateUserId());
        abnormalLedger.setCreateUserName(dto.getCreateUserNname());
        abnormalLedger.setCreateTime(now);
        save(abnormalLedger);
        return AjaxResult.success();
    }

    public Object update(UpdateProductionAbnormalLedgerDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("updateProductionAbnormalLedger", dto.getId()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            ProductionAbnormalLedger abnormalLedger = getById(dto.getId());
            if (abnormalLedger == null) {
                return AjaxResult.error( "当前记录不存在或已删除");
            }
            abnormalLedger.setDepartmentId(dto.getDepartmentId());
            abnormalLedger.setDepartmentName(dto.getDepartmentName());
            abnormalLedger.setHappenTime(DateUtil.parse(dto.getHappenTime()));
            abnormalLedger.setAccidentName(dto.getAccidentName());
            abnormalLedger.setPreventiveMeasures(dto.getPreventiveMeasures());
            abnormalLedger.setMeasuresCompletionTime(DateUtil.parse(dto.getMeasuresCompletionTime()));
            abnormalLedger.setMeasuresImplementation(dto.getMeasuresImplementation());
            abnormalLedger.setProfessionalResponsiblePerson(dto.getProfessionalResponsible());
            abnormalLedger.setTrackResponsiblePerson(dto.getTrackResponsiblePerson());
            abnormalLedger.setAccidentReportFile(dto.getAccidentReportFile());
            abnormalLedger.setModifyUserId(dto.getModifyUserId());
            abnormalLedger.setModifyUserName(dto.getModifyUserName());
            abnormalLedger.setCreateTime(now);
            updateById(abnormalLedger);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    public Object delete(DelProductionAbnormalLedgerDTO dto) {
        ProductionAbnormalLedger abnormalLedger = getById(dto.getId());
        if (abnormalLedger == null) {
            return AjaxResult.error( "当前记录不存在或已删除");
        }
        removeById(abnormalLedger);
        return AjaxResult.success();
    }
}




