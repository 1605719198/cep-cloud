package com.jlkj.finance.aa.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.*;
import com.jlkj.finance.aa.mapper.*;
import com.jlkj.finance.utils.AssertUtil;
import com.jlkj.finance.utils.ConstantsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.service.IFinanceAaReportInfoService;

/**
 * 印项目计算记录Service业务层处理
 *
 * @author jlkj
 * @date 2023-06-28
 */
@Service
@Slf4j
public class FinanceAaReportInfoServiceImpl implements IFinanceAaReportInfoService
{
    @Autowired
    private FinanceAaReportInfoMapper financeAaReportInfoMapper;

    @Autowired
    private FinanceAaProjectContentMapper financeAaProjectContentMapper;
    @Autowired
    private FinanceAaProjectResultMapper financeAaProjectResultMapper;
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;
    @Autowired
    private FinanceAaProjectFormulaMapper financeAaProjectFormulaMapper;
    /**
     * 查询印项目计算记录
     *
     * @param id 印项目计算记录主键
     * @return 印项目计算记录
     */
    @Override
    public FinanceAaReportInfo selectFinanceAaReportInfoById(String id)
    {
        return financeAaReportInfoMapper.selectFinanceAaReportInfoById(id);
    }

    /**
     * 查询印项目计算记录列表
     *
     * @param financeAaReportInfo 印项目计算记录
     * @return 印项目计算记录
     */
    @Override
    public List<FinanceAaReportInfo> selectFinanceAaReportInfoList(FinanceAaReportInfo financeAaReportInfo)
    {
        return financeAaReportInfoMapper.selectFinanceAaReportInfo(financeAaReportInfo);
    }


    /** 计算
    * @Description
    * @Param
    * @return
    * @Author 114288
    * @Date 2023/7/4 8:48
    **/
    private Map<String,FinanceAaProjectResult> financeAaProjectResultMap ;
    private int count;
    @Override
    public int insertFinanceAaReportInfo(FinanceAaReportInfo financeAaReportInfo)
    {


        financeAaProjectResultMapper.deleteFinanceAaProjectResultByPeriod(financeAaReportInfo.getCompanyId(),
                financeAaReportInfo.getAcctPeriod(),financeAaReportInfo.getReportNo());

        FinanceAaProjectFormula financeAaProjectFormula = new FinanceAaProjectFormula();
        financeAaProjectFormula.setCompanyId(financeAaReportInfo.getCompanyId());
        financeAaProjectFormula.setReportNo(financeAaReportInfo.getReportNo());
        List<FinanceAaProjectFormula> financeAaProjectFormulas =   financeAaProjectFormulaMapper.selectFinanceAaProjectFormulaList(financeAaProjectFormula);
        if(financeAaProjectFormulas==null || financeAaProjectFormulas.size()==0){
            throw new ServiceException("计算失败，报表项目为空！");
        }

       financeAaProjectResultMap = new HashMap(financeAaProjectFormulas.size());
       for (int i=0;i<financeAaProjectFormulas.size();i++){
           FinanceAaProjectFormula financeProjectFormula =  financeAaProjectFormulas.get(i);
           count = 0;
           compute71(financeAaReportInfo,financeProjectFormula);



       }

        for (String key: financeAaProjectResultMap.keySet()){
            financeAaProjectResultMapper.insertFinanceAaProjectResult(financeAaProjectResultMap.get(key));

    }

        financeAaReportInfo.setStatus("Y");
        financeAaReportInfo.setId(IdUtils.simpleUUID());
        financeAaReportInfo.setCreateBy(SecurityUtils.getUsername());
        financeAaReportInfo.setCreateName(SecurityUtils.getNickName());
        financeAaReportInfo.setCreateTime(DateUtils.getNowDate());
        return financeAaReportInfoMapper.insertFinanceAaReportInfo(financeAaReportInfo);
    }

    private boolean compute71(FinanceAaReportInfo financeAaReportInfo, FinanceAaProjectFormula financeProjectFormula) {
        boolean flag = false;
        String codeKey = financeAaReportInfo.getCompanyId()+"/"+financeAaReportInfo.getAcctPeriod()+"/"+financeProjectFormula.getReportId()+"/"+financeProjectFormula.getItemCode();
        FinanceAaProjectResult financeAaProjectResultCode = financeAaProjectResultMap.get(codeKey);
        if(financeAaProjectResultCode!=null) {
            return true;
        }


        FinanceAaProjectContent financeAaProjectContent = new FinanceAaProjectContent();
        financeAaProjectContent.setCompanyId(financeAaReportInfo.getCompanyId());
        financeAaProjectContent.setReportNo(financeAaReportInfo.getReportNo());
        financeAaProjectContent.setItemCode(financeProjectFormula.getItemCode());


        List<FinanceAaProjectContent> financeAaProjectContentList = financeAaProjectContentMapper.selectFinanceProjectContentList(financeAaProjectContent);
        for (int i=0;i<financeAaProjectContentList.size();i++){
            FinanceAaProjectContent financeProjectContent =  financeAaProjectContentList.get(i);
            if(ConstantsUtil.CARRYA.equals(financeProjectContent.getKind())){
                flag = sumKindA(financeAaReportInfo,financeProjectContent);
            }
            if(ConstantsUtil.CARRYB.equals(financeProjectContent.getKind())){
                flag = sumKindB(financeAaReportInfo,financeProjectContent);
            }
        }


        return  flag;
    }

    /**
     * 计算类别是项目的
    * @Description
    * @Param financeAaReportInfo
    * @Param financeProjectContent
    * @return
    * @Author 114288
    * @Date 2023/7/5 9:00
    **/
    private boolean sumKindB(FinanceAaReportInfo financeAaReportInfo, FinanceAaProjectContent financeProjectContent) {
        boolean flag = false;
        try{
            String codeKey = financeAaReportInfo.getCompanyId()+"/"+financeAaReportInfo.getAcctPeriod()+"/"+financeProjectContent.getReportId()+"/"+financeProjectContent.getCode();
            String itemCodeKey = financeAaReportInfo.getCompanyId()+"/"+financeAaReportInfo.getAcctPeriod()+"/"+financeProjectContent.getReportId()+"/"+financeProjectContent.getItemCode();

            FinanceAaProjectResult financeAaProjectResultCode = financeAaProjectResultMap.get(codeKey);
            FinanceAaProjectResult financeAaProjectResultItemCode = financeAaProjectResultMap.get(itemCodeKey);
            if(financeAaProjectResultItemCode==null){
                financeAaProjectResultItemCode = new FinanceAaProjectResult();
                financeAaProjectResultItemCode.setId(IdUtils.simpleUUID());
                financeAaProjectResultItemCode.setCompanyId(financeAaReportInfo.getCompanyId());
                financeAaProjectResultItemCode.setAcctPeriod(financeAaReportInfo.getAcctPeriod());
                financeAaProjectResultItemCode.setReportNo(financeProjectContent.getReportNo());
                financeAaProjectResultItemCode.setReportId(financeProjectContent.getReportId());
                financeAaProjectResultItemCode.setItemCode(financeProjectContent.getItemCode());
                financeAaProjectResultMap.put(itemCodeKey,financeAaProjectResultItemCode);
            }
            if (financeAaProjectResultCode!=null ){
                if (ConstantsUtil.LINE.equals(financeProjectContent.getOperator().trim())){
                    financeAaProjectResultItemCode.setAmt(financeAaProjectResultCode.getAmt().negate());
                    financeAaProjectResultItemCode.setQty(financeAaProjectResultCode.getQty().negate());
                }
                //判断该AmtQty为A,Q,T
                if ((ConstantsUtil.STR_A).equals(financeProjectContent.getAmtqty().trim().toUpperCase())){
                    financeAaProjectResultItemCode.setAmt(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getAmt())
                            .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getAmt())));
                    financeAaProjectResultItemCode.setQty(new BigDecimal("0"));
                }
                if ((ConstantsUtil.STR_B).equals(financeProjectContent.getAmtqty().trim().toUpperCase())){
                    financeAaProjectResultItemCode.setAmt(new BigDecimal("0"));
                    financeAaProjectResultItemCode.setQty(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getQty())
                            .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getQty())));
                }
                if ((ConstantsUtil.STR_C).equals(financeProjectContent.getAmtqty().trim().toUpperCase())){
                    financeAaProjectResultItemCode.setAmt(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getAmt())
                            .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getAmt())));
                    financeAaProjectResultItemCode.setQty(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getQty())
                            .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getQty())));
                }
                if ((ConstantsUtil.STR_H).equals(financeProjectContent.getDrcrkind().trim())){
                    if (financeAaProjectResultItemCode.getAmt().compareTo(BigDecimal.ZERO)<0){
                        financeAaProjectResultItemCode.setAmt(BigDecimal.ZERO);
                    }

                    if (financeAaProjectResultItemCode.getQty().compareTo(BigDecimal.ZERO)<0){
                        financeAaProjectResultItemCode.setQty(BigDecimal.ZERO);
                    }

                }
                if ((ConstantsUtil.STR_I).equals(financeProjectContent.getDrcrkind().trim())){
                    if (financeAaProjectResultItemCode.getAmt().compareTo(BigDecimal.ZERO)>0){
                        financeAaProjectResultItemCode.setAmt(BigDecimal.ZERO);
                    }

                    if (financeAaProjectResultItemCode.getQty().compareTo(BigDecimal.ZERO)>0){
                        financeAaProjectResultItemCode.setQty(BigDecimal.ZERO);
                    }

                }

            }else{
                count++;
                if (count > ConstantsUtil.INT_20) {
                    throw new ServiceException("超过recursive超过次数,可能有错误,暂停执行" );
                 }

                FinanceAaProjectFormula financeAaProjectFormula = new FinanceAaProjectFormula();
                financeAaProjectFormula.setCompanyId(financeAaReportInfo.getCompanyId());
                financeAaProjectFormula.setReportNo(financeAaReportInfo.getReportNo());
                financeAaProjectFormula.setItemCode(financeProjectContent.getCode());
                List<FinanceAaProjectFormula> financeAaProjectFormulas =   financeAaProjectFormulaMapper.selectFinanceAaProjectFormulaList(financeAaProjectFormula);


                for (int i=0;i<financeAaProjectFormulas.size();i++){
                    FinanceAaProjectFormula financeProjectFormula =  financeAaProjectFormulas.get(i);
                    if (compute71(financeAaReportInfo,financeProjectFormula)){

                        codeKey = financeAaReportInfo.getCompanyId()+"/"+financeAaReportInfo.getAcctPeriod()+"/"+financeProjectContent.getReportId()+"/"+financeProjectContent.getCode();
                        itemCodeKey = financeAaReportInfo.getCompanyId()+"/"+financeAaReportInfo.getAcctPeriod()+"/"+financeProjectContent.getReportId()+"/"+financeProjectContent.getItemCode();

                        financeAaProjectResultCode = financeAaProjectResultMap.get(codeKey);
                        financeAaProjectResultItemCode = financeAaProjectResultMap.get(itemCodeKey);
                        if(financeAaProjectResultItemCode==null){
                            financeAaProjectResultItemCode = new FinanceAaProjectResult();
                            financeAaProjectResultItemCode.setId(IdUtils.simpleUUID());
                            financeAaProjectResultItemCode.setCompanyId(financeAaReportInfo.getCompanyId());
                            financeAaProjectResultItemCode.setAcctPeriod(financeAaReportInfo.getAcctPeriod());
                            financeAaProjectResultItemCode.setReportNo(financeProjectContent.getReportNo());
                            financeAaProjectResultItemCode.setReportId(financeProjectContent.getReportId());
                            financeAaProjectResultItemCode.setItemCode(financeProjectContent.getItemCode());
                            financeAaProjectResultMap.put(itemCodeKey,financeAaProjectResultItemCode);
                        }
                        if ((ConstantsUtil.LINE).equals(financeProjectContent.getOperator().trim())) {
                            financeAaProjectResultItemCode.setAmt(financeAaProjectResultItemCode.getAmt().negate());
                            financeAaProjectResultItemCode.setQty(financeAaProjectResultItemCode.getQty().negate());
                        }
                        //判断该AmtQty为A,Q,T
                        if ((ConstantsUtil.STR_A).equals(financeProjectContent.getAmtqty().trim().toUpperCase())) {
                            financeAaProjectResultItemCode.setAmt(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getAmt())
                                    .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getAmt())));
                            financeAaProjectResultItemCode.setQty(new BigDecimal(ConstantsUtil.STR_0));
                        }
                        else if ((ConstantsUtil.STR_B).equals(financeProjectContent.getAmtqty().trim().toUpperCase())) {
                            financeAaProjectResultItemCode.setQty(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getQty())
                                    .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getQty())));
                            financeAaProjectResultItemCode.setAmt(new BigDecimal(ConstantsUtil.STR_0));
                        }
                        else if ((ConstantsUtil.STR_C).equals(financeProjectContent.getAmtqty().trim().toUpperCase())) {
                            financeAaProjectResultItemCode.setAmt(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getAmt())
                                    .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getAmt())));
                            financeAaProjectResultItemCode.setQty(AssertUtil.bigDecimalValue(financeAaProjectResultItemCode.getQty())
                                    .add(AssertUtil.bigDecimalValue(financeAaProjectResultCode.getQty())));
                        }
                        if ((ConstantsUtil.STR_H).equals(financeProjectContent.getDrcrkind().trim())){
                            if (financeAaProjectResultItemCode.getAmt().compareTo(new BigDecimal(ConstantsUtil.STR_0))<0){
                                financeAaProjectResultItemCode.setAmt(new BigDecimal(ConstantsUtil.STR_0));
                            }

                            if (financeAaProjectResultItemCode.getQty().compareTo(new BigDecimal(ConstantsUtil.STR_0))<0){
                                financeAaProjectResultItemCode.setQty(new BigDecimal(ConstantsUtil.STR_0));
                            }

                        }
                        if ((ConstantsUtil.STR_I).equals(financeProjectContent.getDrcrkind().trim())){
                            if (financeAaProjectResultItemCode.getAmt().compareTo(new BigDecimal(ConstantsUtil.STR_0))>0){
                                financeAaProjectResultItemCode.setAmt(new BigDecimal(ConstantsUtil.STR_0));
                            }

                            if (financeAaProjectResultItemCode.getQty().compareTo(new BigDecimal(ConstantsUtil.STR_0))>0){
                                financeAaProjectResultItemCode.setQty(new BigDecimal(ConstantsUtil.STR_0));
                            }

                        }
                        count--;
                    }


                }


            }
        }
        catch(Exception e){
            flag = false;
            log.error("",e);
            throw new ServiceException("计算失败，" + e.getMessage());

        }
        return flag;


    }

    /**
     * 计算代码类别为会计科目的
    * @Description
    * @Param
    * @return
    * @Author 114288
    * @Date 2023/7/4 10:10
    **/
    private boolean sumKindA(FinanceAaReportInfo financeAaReportInfo, FinanceAaProjectContent financeProjectContent) {
        String codeCheck = "";
        StringBuilder code = new StringBuilder("");
        String equalsLike = "";
        boolean flag = false;
        try {
            BigDecimal qty = BigDecimal.ZERO;
            BigDecimal amt = BigDecimal.ZERO;
            for (int i = 0; i < financeProjectContent.getCode().length(); i++) {
                if (financeProjectContent.getCode().trim().charAt(i) == '*') {
                    code.append("%") ;
                    codeCheck = "yes";
                    break;
                } else if (financeProjectContent.getCode().trim().charAt(i) == '@') {
                    code.append("_") ;
                    codeCheck = "no";
                } else {
                    code.append( financeProjectContent.getCode().trim().charAt(i));
                }
                financeProjectContent.setCode(code.toString());
            }
            if (ConstantsUtil.STR_NO.equals(codeCheck.trim())) {
                code.append("%") ;
            }
            if (("").equals(codeCheck.trim())) {
                equalsLike = "=";
            } else {
                equalsLike = "like";
            }

            log.info(financeProjectContent.getItemCode() +"==========="+code);
            String key = financeAaReportInfo.getCompanyId()+"/"+financeAaReportInfo.getAcctPeriod()+"/"+financeProjectContent.getReportId()+"/"+financeProjectContent.getItemCode();
            FinanceAaProjectResult financeAaProjectResult = financeAaProjectResultMap.get(key);
            if(financeAaProjectResult==null){
                financeAaProjectResult = new FinanceAaProjectResult();
                financeAaProjectResult.setId(IdUtils.simpleUUID());
                financeAaProjectResult.setCompanyId(financeAaReportInfo.getCompanyId());
                financeAaProjectResult.setAcctPeriod(financeAaReportInfo.getAcctPeriod());
                financeAaProjectResult.setReportNo(financeProjectContent.getReportNo());
                financeAaProjectResult.setReportId(financeProjectContent.getReportId());
                financeAaProjectResult.setItemCode(financeProjectContent.getItemCode());
                financeAaProjectResultMap.put(key,financeAaProjectResult);
            }
            //户号借余、贷余
            if (ConstantsUtil.STR_J.equals(financeProjectContent.getDrcrkind().trim() )
                    || ConstantsUtil.STR_K.equals(financeProjectContent.getDrcrkind().trim())) {
                amt = this.getKindaByIdCode(financeAaReportInfo.getCompanyId(), financeAaReportInfo.getAcctPeriod(), code.toString(), equalsLike,financeProjectContent.getDrcrkind().trim());
            }//结转额
            else if (ConstantsUtil.STR_N.equals(financeProjectContent.getDrcrkind().trim())) {
                amt = this.getJz(financeAaReportInfo.getCompanyId(), financeAaReportInfo.getAcctPeriod(), code.toString(), financeProjectContent.getDrcrkind().trim());
            } else {
                //定义金额取数方式
                getSumSql(financeProjectContent.getAmtqty(), financeProjectContent.getDrcrkind().trim());
                //=====组合会计科目的SQL
                Map[] aa02Amt =  financeAaLedgerlCalMapper.selectByManageReport(financeAaReportInfo.getCompanyId(),financeAaReportInfo.getAcctPeriod(),sumstr,sumstr2,sumstr3,equalsLike, String.valueOf(code));

                if (aa02Amt!=null && aa02Amt.length > 0 && aa02Amt[0]!=null) {
                     qty =  AssertUtil.bigDecimalValue(aa02Amt[0].get("qty"));
                     amt = AssertUtil.bigDecimalValue(aa02Amt[0].get("amt"));
                }
            }
            log.info(financeProjectContent.getItemCode() +"=====qty======"+qty);
            log.info(financeProjectContent.getItemCode() +"=====amt======"+amt);
            //加总前判断正负
            if (ConstantsUtil.LINE.equals(financeProjectContent.getOperator().trim().toUpperCase())) {
                qty = qty.negate();
                amt = amt.negate();
            }
            if (ConstantsUtil.STR_A.equals(financeProjectContent.getAmtqty().trim().toUpperCase())) {
                //最后放入db.tbaa73表里的金额
                financeAaProjectResult.setAmt( AssertUtil.bigDecimalValue(financeAaProjectResult.getAmt()).add(amt));
            } else {
                if (ConstantsUtil.STR_B.equals(financeProjectContent.getAmtqty().trim().toUpperCase())) {
                    financeAaProjectResult.setQty( AssertUtil.bigDecimalValue(financeAaProjectResult.getQty()).add(qty));
                } else {
                    //--金额与数量同时计算----
                    financeAaProjectResult.setAmt( AssertUtil.bigDecimalValue(financeAaProjectResult.getAmt()).add(amt));
                    financeAaProjectResult.setQty( AssertUtil.bigDecimalValue(financeAaProjectResult.getQty()).add(qty));
                }
            }
            flag = true;
        }catch(Exception e){
            flag = false;
            log.error("",e);
            throw new ServiceException("计算失败，" + e.getMessage());
       }
        return flag;

    }

    /**
     * 查询SQL DB.TBAA02
     * @param compId 公司别
     * @param equalslike = 或者 like
     * @param code 会计科目/印表项目代码(%)
     * @param drCrKind 金额取数方式
     * @param amtQty 表达方式 A.只取金额；Q.只取数量；T.同时取数量和金额
     * */




    String sumstr = "";
    String sumstr2 = "";
    String sumstr3 = "";
    private void getSumSql(String amtType, String drCrType) {


            if (ConstantsUtil.STR_A.equals(drCrType.trim())) {
                sumstr = "IFNULL(IFNULL(dr_qty,0),0)-IFNULL(IFNULL(cr_qty,0),0)";
                sumstr2 = "IFNULL(dr_amt,0)-IFNULL(cr_amt,0)";
            }
            if (ConstantsUtil.STR_B.equals(drCrType.trim())) {
                sumstr = "IFNULL(dr_qty,0)";
                sumstr2 = "IFNULL(dr_amt,0)";
            }
            if (ConstantsUtil.STR_C.equals(drCrType.trim())) {
                sumstr = "IFNULL(cr_qty,0)";
                sumstr2 = "IFNULL(cr_amt,0)";
            }
            if (ConstantsUtil.STR_D.equals(drCrType.trim())) {
                sumstr = "IFNULL(bgn_qty,0)+IFNULL(dr_qty,0)-IFNULL(cr_qty,0)";
                sumstr2 = "IFNULL(bgn_amt,0)+IFNULL(dr_amt,0)-IFNULL(cr_amt,0)";
            }
            if (ConstantsUtil.STR_E.equals(drCrType.trim())) {
                sumstr = "IFNULL(bgn_qty,0)";
                sumstr2 = "IFNULL(bgn_amt,0)";
            }
            if (ConstantsUtil.STR_F.equals(drCrType.trim())) {
                sumstr = "IFNULL(bgn_qty,0)+IFNULL(dr_qty,0)-IFNULL(cr_qty,0)";
                sumstr2 = "IFNULL(bgn_amt,0)+IFNULL(dr_amt,0)-IFNULL(cr_amt,0)";
                sumstr3 = "(IFNULL(bgn_qty,0)+IFNULL(dr_qty,0)-IFNULL(cr_qty,0))>0 and (IFNULL(bgn_amt,0)+IFNULL(dr_amt,0)-IFNULL(cr_amt,0))>0";
            }
            if (ConstantsUtil.STR_G.equals(drCrType.trim())) {
                sumstr = "IFNULL(bgn_qty,0)+IFNULL(dr_qty,0)-IFNULL(cr_qty,0)";
                sumstr2 = "IFNULL(bgn_amt,0)+IFNULL(dr_amt,0)-IFNULL(cr_amt,0)";
                sumstr3 = "(IFNULL(bgn_qty,0)+IFNULL(dr_qty,0)-IFNULL(cr_qty,0))<0 and (IFNULL(bgn_amt,0)+IFNULL(dr_amt,0)-IFNULL(cr_amt,0))<0";
            }
            if (ConstantsUtil.STR_H.equals(drCrType.trim())) {
                sumstr = "IFNULL(dr_qty,0)-IFNULL(cr_qty,0)";
                sumstr2 = "IFNULL(dr_amt,0)-IFNULL(cr_amt,0)";
                sumstr3 = "(IFNULL(dr_qty,0)-IFNULL(cr_qty,0))>0 and (IFNULL(dr_amt,0)-IFNULL(cr_amt,0))>0";
            }
            if (ConstantsUtil.STR_I.equals(drCrType.trim())) {
                sumstr = "IFNULL(dr_qty,0)-IFNULL(cr_qty,0)";
                sumstr2 = "IFNULL(dr_amt,0)-IFNULL(cr_amt,0)";
                sumstr3 = "(IFNULL(dr_qty,0)-IFNULL(cr_qty,0))<0 and (IFNULL(dr_amt,0)-IFNULL(cr_amt,0))<0";
            }

    }

    /**
     * 取得结转的额度
    * @Description
    * @Param
    * @return
    * @Author 114288
    * @Date 2023/7/4 12:22
    **/
    private BigDecimal getJz(String companyId, String acctPeriod, String code, String drCrKind) {

        BigDecimal detailAmt = new BigDecimal(0);
        try{

            List<FinanceAaVoucherDetail> financeAaVoucherDetailList = financeAaVoucherDetailMapper.selectDetailByPgrmId(companyId,acctPeriod,code ,"voucherAA" );
            for(int i = 0;financeAaVoucherDetailList!=null && i<financeAaVoucherDetailList.size();i++){
                FinanceAaVoucherDetail financeAaVoucherDetail = (FinanceAaVoucherDetail)financeAaVoucherDetailList.get(i);
                if(("D").equals(financeAaVoucherDetail.getDrcr())){
                    detailAmt = detailAmt.add(financeAaVoucherDetail.getNtamt());
                }else if(("C").equals(financeAaVoucherDetail.getDrcr())){
                    detailAmt = detailAmt.subtract(financeAaVoucherDetail.getNtamt());
                }
            }
        }catch(Exception e){
            log.error("getKindAGroupByIdCode",e);
            throw new ServiceException("计算失败,"+e.getMessage());
        }
        return detailAmt;


    }
/**
* @description  计算A类型的值
* @param companyId 公司别
 * @param acctPeriod 会计周期
 * @param code 会计科目
 * @param drCrKind 类别
* @return java.math.BigDecimal
* @Author 114288
* @Date 2023/7/7 10:57
**/
    private BigDecimal getKindaGroupByIdaCode(String companyId, String acctPeriod, String code, String drCrKind) {

        BigDecimal detailAmt = new BigDecimal(0);
        try {

            int j = 0;

            String acct = "";
            if(AssertUtil.isVal(code)){
                throw new Exception("印表项目代码为空");
            }else{
                acct = code.substring(0, 6);
            }
            //票据-暂估-质保金
            Map[] maps =  financeAaLedgerlCalMapper.getKindaByIdCode( companyId,  acctPeriod,  code,  drCrKind) ;
            //先求出户号的余额，在把户号做累加
            for(int i =0;maps!=null && i<maps.length;i++){
                Map obj = maps[i];

                detailAmt = detailAmt.add( AssertUtil.bigDecimalValue(obj.get("AMT")));

            }

        }catch(Exception e1){
            log.error("getKindAGroupByIdCode",e1);
            throw new ServiceException("计算失败,"+e1.getMessage());
        }
        return detailAmt;


    }

    /**
     * 户号借余、贷余
     * 针对特殊往来科目<1122-应收帐款/2202-应付帐款/1221-其他应收/2241-其他应付>
     * <<应付帐款按客商为准,若该客商的应付帐款当期期末为正(drCrType.trim().equals("F"))就累计在资产负债表的预付帐款项目上>>
     * <<应付帐款按客商为准,若该客商的应付帐款当期期末为负(drCrType.trim().equals("G"))就累计在资产负债表的应付帐款项目上>>
     *@param compId
     *@param acctPeriod
     *@param acctCode
     *@param drCrType
     *@Author 114288
     *@Date 2023/7/4 10:39
     */
    private BigDecimal getKindaByIdCode(String compId, String acctPeriod, String acctCode,String equalslike, String drCrType) {
        BigDecimal detailAmt = new BigDecimal(0);

        try {
            StringBuffer acct =  new StringBuffer("");
            if(AssertUtil.isVal(acctCode)){
                throw new Exception("会计科目/印表项目代码为空");
            }else{
                String[] strArr = acctCode.split(",");
                if(strArr.length>0){
                    for(int i =0;i<strArr.length;i++){
                        if(i == 0){
                            acct.append(" ( ACCTCODE ").append(equalslike).append(" '" + strArr[i] + "' ");
                        }else{
                            acct.append(" or ACCTCODE ").append(equalslike).append(" '" + strArr[i] + "' ");
                        }
                    }
                    if(strArr.length>0){
                        acct.append(" ) ");
                    }
                }

            }
            //资产负债表特殊取数
            Map[] maps = financeAaLedgerlCalMapper.getKindaByIdCode(compId, acctPeriod, acct.toString(), drCrType);
            //先求出户号的余额，在把户号做累加
            for(int i =0;maps!=null && i<maps.length;i++){
                Map obj = maps[i];

                detailAmt = detailAmt.add( AssertUtil.bigDecimalValue(obj.get("AMT")));

            }


        }catch(Exception e1){
            return new BigDecimal(0);
        }
        return detailAmt;
    }
    /**
     * 修改印项目计算记录
     *
     * @param financeAaReportInfo 印项目计算记录
     * @return 结果
     */
    @Override


    public int updateFinanceAaReportInfo(FinanceAaReportInfo financeAaReportInfo)
    {
        return financeAaReportInfoMapper.updateFinanceAaReportInfo(financeAaReportInfo);
    }

    /**
     * 批量删除印项目计算记录
     *
     * @param ids 需要删除的印项目计算记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaReportInfoByIds(String[] ids)
    {
        return financeAaReportInfoMapper.deleteFinanceAaReportInfoByIds(ids);
    }

    /**
     * 删除印项目计算记录信息
     *
     * @param id 印项目计算记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaReportInfoById(String id)
    {
        return financeAaReportInfoMapper.deleteFinanceAaReportInfoById(id);
    }
}
