package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.api.RemoteAaApiService;
import com.jlkj.finance.api.bean.FinanceVoucherBean;
import com.jlkj.finance.api.bean.FinanceVoucherDetailBean;
import com.jlkj.human.hs.domain.HumanHsAmt;
import com.jlkj.human.hs.domain.HumanHsAmtDetail;
import com.jlkj.human.hs.domain.PayamtRules;
import com.jlkj.human.hs.mapper.HumanHsAmtMapper;
import com.jlkj.human.hs.mapper.PayamtRulesMapper;
import com.jlkj.human.hs.service.IHumanHsAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RemoteAaApiService  remoteAaApiService;
    @Autowired
    private PayamtRulesMapper payamtRulesMapper;



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
                humanHsAmtDetail.setCreateName(SecurityUtils.getNickName());
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
        String year = null,month = null;
        List<HumanHsAmtDetail>  detailList =  new ArrayList<HumanHsAmtDetail>();
       if(!StringUtils.isEmpty(humanHsAmt.getYyMonth())){
           year =  humanHsAmt.getYyMonth().substring(0,4);
           month = humanHsAmt.getYyMonth().substring(5,7).replaceFirst("^0*", "");
       }

        //查询薪资明细list  人、金额
        if(Constants.STR_ZERO_ONE.equals(humanHsAmt.getOperTime())) {
            detailList = humanHsAmtMapper.selectDetailAmtList01(year,month);
        }else{
            detailList = humanHsAmtMapper.selectDetailAmtList02(year,month);
        }
        return  detailList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int sendAA(String[] ids)
    {
        int count =0 ;
        for (String  id : ids)
        {
            HumanHsAmt  humanHsAmt  = this.selectHumanHsAmtById(id);
            //抛帐主档
            FinanceVoucherBean financeVoucherBean = new FinanceVoucherBean();
            financeVoucherBean.setCompanyId(humanHsAmt.getCompId());
            //凭证日期？？？？？
            financeVoucherBean.setVoucherDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD,DateUtils.getDate()));
            financeVoucherBean.setApid("HS");
            financeVoucherBean.setPgrmid("hs001");
            if(humanHsAmt.getOperTime().equals("01")){
                financeVoucherBean.setVoucherDesc("应付职工薪酬——工资抛账");
            }else{
            financeVoucherBean.setVoucherDesc("应付职工薪酬——奖金抛账");
            }
            financeVoucherBean.setBillNo("20230501");
            financeVoucherBean.setBillUrl("");
            financeVoucherBean.setVoucherType("T");
            financeVoucherBean.setStatus("N");

            financeVoucherBean.setSrlnoCount(0L);
            //业务单据新增人？？？？还是核准人？？？？？
            financeVoucherBean.setPastuserId(humanHsAmt.getCreateBy());
            financeVoucherBean.setPastuserName(humanHsAmt.getCreateBy());
            //出纳工号 ？？？？？
            financeVoucherBean.setMoneyuserId("");
            financeVoucherBean.setMoneyuserName("");
            //业务单据新增人？？？？还是核准人？？？？？
            financeVoucherBean.setCreateBy(humanHsAmt.getCreateBy());
            financeVoucherBean.setCreateName(humanHsAmt.getCreateBy());
            //
            financeVoucherBean.setCreateDate(DateUtils.dateTime());
            financeVoucherBean.setUpdateBy("");
            financeVoucherBean.setCreateTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getTime()));
            //薪资付款明细档
          // List<HumanHsAmtDetail>  humanHsAmtDetail = humanHsAmt.getHumanHsAmtDetailList();
           //计算总金额   不同银行需分开抛送？？？？ 分开则 跑for循环
            BigDecimal  amt  = new BigDecimal(0);
            //如果统一抛送 则写方法取得总金额
           Map  sumamt  = humanHsAmtMapper.selectSumAmt(id);
           if(!sumamt.isEmpty()){
               amt= new BigDecimal(sumamt.get("amt").toString());
           }
            //抛帐明细档
            List<FinanceVoucherDetailBean> list = new ArrayList<FinanceVoucherDetailBean>();
            FinanceVoucherDetailBean financeVoucherDetailBean = new  FinanceVoucherDetailBean();
            //借方抛帐资料  取得借方会计科目  核算科目一 、核算科目二
            PayamtRules payamtRules = new PayamtRules();
            List<PayamtRules> payamtRulesList = payamtRulesMapper.selectPayamtRulesByCode(humanHsAmt.getOperTime());
            if(payamtRulesList.size()>0){
                payamtRules=  payamtRulesList.get(0);
            }else{
                throw new ServiceException("根据薪资付款作业时点,找不到匹配的抛帐规则，请核对抛帐资料是否正确！！");
            }
            financeVoucherDetailBean.setCompanyId(humanHsAmt.getCompId());
            financeVoucherDetailBean.setVoucherDate(DateUtils.getDate());
            financeVoucherDetailBean.setVoucherNo("");
            financeVoucherDetailBean.setSrlno(1L);
            financeVoucherDetailBean.setDrcr("D");
            financeVoucherDetailBean.setAcctId("");
            financeVoucherDetailBean.setAcctCode(payamtRules.getAcctcoded());
           // financeVoucherDetailBean.setAcctName("库存现金-人民币");
            financeVoucherDetailBean.setCalIda("");
            financeVoucherDetailBean.setCalCodea(payamtRules.getIdcoded());//类别  核算项目一
            financeVoucherDetailBean.setCalNamea("");
            financeVoucherDetailBean.setCalIdb("");
            financeVoucherDetailBean.setCalCodeb(payamtRules.getEfnod());//类别 核算项目二
            financeVoucherDetailBean.setCalNameb("");
            financeVoucherDetailBean.setCalIdc("");
            financeVoucherDetailBean.setCalCodec("");
            financeVoucherDetailBean.setCalNamec("");
            financeVoucherDetailBean.setCalIdd("");
            financeVoucherDetailBean.setCalCoded("");
            financeVoucherDetailBean.setCalNamed("");
            financeVoucherDetailBean.setCrcyUnit("元");
            financeVoucherDetailBean.setQtyFrnamt(amt);
            financeVoucherDetailBean.setNtamt(amt);
            financeVoucherDetailBean.setSrlDesc("薪资付款借方资料");

            list.add(financeVoucherDetailBean);

            financeVoucherDetailBean = new  FinanceVoucherDetailBean();

            financeVoucherDetailBean.setCompanyId(humanHsAmt.getCompId());
            financeVoucherDetailBean.setVoucherDate(DateUtils.getDate());
            financeVoucherDetailBean.setVoucherNo("");
            financeVoucherDetailBean.setSrlno(2L);
            financeVoucherDetailBean.setDrcr("C");
            financeVoucherDetailBean.setAcctId("725954b1be764d3390e2ae78e5bfdc61");
            financeVoucherDetailBean.setAcctCode(payamtRules.getIdcodec());
            financeVoucherDetailBean.setCalIda("");
            financeVoucherDetailBean.setCalCodea(payamtRules.getIdcodec());
            financeVoucherDetailBean.setCalNamea("");
            financeVoucherDetailBean.setCalIdb("");
            financeVoucherDetailBean.setCalCodeb(payamtRules.getRefnoc());
            financeVoucherDetailBean.setCalNameb("");
            financeVoucherDetailBean.setCalIdc("");
            financeVoucherDetailBean.setCalCodec("");
            financeVoucherDetailBean.setCalNamec("");
            financeVoucherDetailBean.setCalIdd("");
            financeVoucherDetailBean.setCalCoded("");
            financeVoucherDetailBean.setCalNamed("");
            financeVoucherDetailBean.setCrcyUnit("元");
            financeVoucherDetailBean.setQtyFrnamt(amt);
            financeVoucherDetailBean.setNtamt(amt);
            financeVoucherDetailBean.setSrlDesc("薪资付款贷方资料");

            list.add(financeVoucherDetailBean);
            financeVoucherBean.setDetailList(list);

            Map<String,Object> returnMap=
                    remoteAaApiService.doVoucher(financeVoucherBean, SecurityConstants.INNER);

            //根据返回Map 更新薪资付款主档资料
            if(returnMap!=null){
                if(returnMap.get("isSuccess").toString().equals("Y")){
                    humanHsAmt.setVchrno(returnMap.get("voucherNo").toString());
                  count =  humanHsAmtMapper.updateHumanHsAmt(humanHsAmt);
                }else{
                    String  message  = returnMap.get("message").toString();
                    throw new ServiceException("薪资付款调用抛帐接口失败!!!!"+message);
                }
            }else{
                throw new ServiceException("薪资付款调用抛帐接口失败!!!!");
            }

        } //组抛帐主档
        return count;
    }
}
