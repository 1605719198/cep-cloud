package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.HumanHsAmt;
import com.jlkj.human.hs.domain.HumanHsAmtDetail;
import com.jlkj.human.hs.mapper.HumanHsAmtMapper;
import com.jlkj.human.hs.service.IHumanHsAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 薪资应付作业Service业务层处理
 *
 * @author jlkj
 * @date 2023-05-24
 */
@Service
public class HumanHsAmtServiceImpl implements IHumanHsAmtService
{
    @Autowired
    private HumanHsAmtMapper humanHsAmtMapper;

    /**
     * 查询薪资应付作业
     *
     * @param id 薪资应付作业主键
     * @return 薪资应付作业
     */
    @Override
    public HumanHsAmt selectHumanHsAmtById(String id)
    {
        HumanHsAmt humanHsAmt = humanHsAmtMapper.selectHumanHsAmtById(id);
        if(humanHsAmt.getFileUrl().length()>0){
            humanHsAmt.setFileList(humanHsAmtMapper.selectFileList(humanHsAmt.getFileUrl().split(",")));
        }
        return humanHsAmt;
    }

    /**
     * 查询薪资应付作业列表
     *
     * @param humanHsAmt 薪资应付作业
     * @return 薪资应付作业
     */
    @Override
    public List<HumanHsAmt> selectHumanHsAmtList(HumanHsAmt humanHsAmt)
    {
        return humanHsAmtMapper.selectHumanHsAmtList(humanHsAmt);
    }

    /**
     * 新增薪资应付作业
     *
     * @param humanHsAmt 薪资应付作业
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertHumanHsAmt(HumanHsAmt humanHsAmt)
    {
        int count = humanHsAmtMapper.updateCheckUnique(humanHsAmt.getCompId(), humanHsAmt.getYyMonth(), humanHsAmt.getOperTime());
        if(count>0){
            throw new ServiceException("已存在相同年月与作业时点数据，不可重复新增！");
        }
        humanHsAmt.setId(IdUtils.simpleUUID());
        humanHsAmt.setSendDate(DateUtils.getNowDate());
        humanHsAmt.setSendEmpNo(SecurityUtils.getUsername());
        humanHsAmt.setStatus("1");
        humanHsAmt.setCreateBy(SecurityUtils.getUsername());
        humanHsAmt.setCreateName(SecurityUtils.getNickName());
        humanHsAmt.setCreateTime(DateUtils.getNowDate());
        int rows = humanHsAmtMapper.insertHumanHsAmt(humanHsAmt);
        insertHumanHsAmtDetail(humanHsAmt);
        return rows;
    }

    /**
     * 修改薪资应付作业
     *
     * @param humanHsAmt 薪资应付作业
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateHumanHsAmt(HumanHsAmt humanHsAmt)
    {
        // 修改月别与作业时点时，捡核数据是否重复
        HumanHsAmt dbHumanHsAmt = humanHsAmtMapper.selectHumanHsAmtById(humanHsAmt.getId());
        String dbKey = dbHumanHsAmt.getYyMonth() + dbHumanHsAmt.getOperTime();
        String currKey = humanHsAmt.getYyMonth() + humanHsAmt.getOperTime();
        if(!dbKey.equals(currKey)){
            int count = humanHsAmtMapper.updateCheckUnique(humanHsAmt.getCompId(), humanHsAmt.getYyMonth(), humanHsAmt.getOperTime());
            if(count>0){
                throw new ServiceException("操作失败！已存在相同年月与作业时点数据");
            }
        }
        humanHsAmtMapper.deleteHumanHsAmtDetailByAmtId(humanHsAmt.getId());
        insertHumanHsAmtDetail(humanHsAmt);
        return humanHsAmtMapper.updateHumanHsAmt(humanHsAmt);
    }

    /**
     * 批量删除薪资应付作业
     *
     * @param ids 需要删除的薪资应付作业主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteHumanHsAmtByIds(String[] ids)
    {
        humanHsAmtMapper.deleteHumanHsAmtDetailByAmtIds(ids);
        return humanHsAmtMapper.deleteHumanHsAmtByIds(ids);
    }

    /**
     * 删除薪资应付作业信息
     *
     * @param id 薪资应付作业主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteHumanHsAmtById(String id)
    {
        humanHsAmtMapper.deleteHumanHsAmtDetailByAmtId(id);
        return humanHsAmtMapper.deleteHumanHsAmtById(id);
    }

    /**
     * 新增薪资应付明细信息
     *
     * @param humanHsAmt 薪资应付作业对象
     */
    public void insertHumanHsAmtDetail(HumanHsAmt humanHsAmt)
    {
        List<HumanHsAmtDetail> humanHsAmtDetailList = humanHsAmt.getHumanHsAmtDetailList();
        String id = humanHsAmt.getId();
        if (StringUtils.isNotNull(humanHsAmtDetailList))
        {
            List<HumanHsAmtDetail> list = new ArrayList<HumanHsAmtDetail>();
            for (HumanHsAmtDetail humanHsAmtDetail : humanHsAmtDetailList)
            {
                humanHsAmtDetail.setAmtId(id);
                humanHsAmtDetail.setId(IdUtils.simpleUUID());
                humanHsAmtDetail.setCreateBy(SecurityUtils.getUsername());
                humanHsAmtDetail.setCreateName(SecurityUtils.getNickName());
                humanHsAmtDetail.setCreateTime(DateUtils.getNowDate());
                list.add(humanHsAmtDetail);
            }
            if (list.size() > 0)
            {
                humanHsAmtMapper.batchHumanHsAmtDetail(list);
            }
        }
    }


    /**
     * 查询薪资应付作业列表
     *
     * @param humanHsAmt 薪资应付作业
     * @return 薪资应付作业
     */
    @Override
    public List<HumanHsAmtDetail> getDetailAmtList(HumanHsAmt humanHsAmt)
    {
        String ymonth  =humanHsAmt.getYyMonth();
        String year =  ymonth.substring(0,4);
        String month = ymonth.substring(5,7);
        if(month.startsWith("0")) {
            month = month.replaceAll("0", "");
        }
        List<HumanHsAmtDetail>  detailList =  new ArrayList<HumanHsAmtDetail>();
        //查询薪资明细list  人、金额
        if(humanHsAmt.getOperTime().equals("01")) {
            detailList = humanHsAmtMapper.selectDetailAmtList01(year,month);
        }else{
            detailList = humanHsAmtMapper.selectDetailAmtList02(year,month);
        }
        return  detailList;
    }
}
