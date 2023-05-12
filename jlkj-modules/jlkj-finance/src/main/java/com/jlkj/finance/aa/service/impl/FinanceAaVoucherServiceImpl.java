package com.jlkj.finance.aa.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.finance.aa.domain.*;
import com.jlkj.finance.aa.dto.FinanceAaVoucherDTO;
import com.jlkj.finance.aa.mapper.FinanceAaAcctcodeCorpMapper;
import com.jlkj.finance.aa.service.FinanceAccountYearService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherMapper;
import com.jlkj.finance.aa.service.IFinanceAaVoucherService;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 凭证维护-主Service业务层处理
 *
 * @author 265799
 * @date 2023-04-24
 */
@Service
public class FinanceAaVoucherServiceImpl implements IFinanceAaVoucherService
{
    @Autowired
    private FinanceAaVoucherMapper financeAaVoucherMapper;
    @Autowired
    private FinanceAccountYearService financeAccountYearService;
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;
    @Autowired
    private FinanceAaVoucherBackupServiceImpl financeAaVoucherBackupService;
    @Autowired
    private  FinanceAaLedgerAcctServiceImpl financeAaLedgerAcctService;
    @Autowired
    private FinanceAaLedgerlCalServiceImpl financeAaLedgerlCalService;
    /**
     * 查询凭证维护-主
     *
     * @param id 凭证维护-主主键
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucherById(String id)
    {
        return financeAaVoucherMapper.selectFinanceAaVoucherById(id);
    }
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherListHeadVoucher(FinanceAaVoucher financeAaVoucher)
    {
        return financeAaVoucherMapper.selectFinanceAaVoucherListHeadVoucher(financeAaVoucher);
    }
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher)
    {
        return financeAaVoucherMapper.selectFinanceAaVoucherList(financeAaVoucher);
    }
    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherDetailList(FinanceAaVoucher financeAaVoucher)
    {
        return financeAaVoucherMapper.selectFinanceAaVoucherDetailList(financeAaVoucher);
    }

    /**
     * 查询凭证维护-主列表(不去重)
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherLinkList(FinanceAaVoucher financeAaVoucher)
    {
        return financeAaVoucherMapper.selectFinanceAaVoucherLinkList(financeAaVoucher);
    }

    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucher(FinanceAaVoucher financeAaVoucher)
    {
        FinanceAaVoucher financeAaVoucher1 ;
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())){
             financeAaVoucher1 = new FinanceAaVoucher();
        }else {
            financeAaVoucher1 = financeAaVoucherMapper.selectFinanceAaVoucher(financeAaVoucher);
        }
        return financeAaVoucher1;
    }
    /**
     * 前笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucherFront(FinanceAaVoucher financeAaVoucher)
    {
        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())){
            financeAaVoucher1 = new FinanceAaVoucher();
        }else {
            List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherFront(financeAaVoucher);
            if (financeAaVouchers!=null){
                for (int i = 0; i<financeAaVouchers.size();i++){
                    financeAaVoucher1 = financeAaVouchers.get(0);
                }
            }

        }
        return financeAaVoucher1;
    }
    /**
     * 首笔笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucherOrder(FinanceAaVoucher financeAaVoucher)
    {

        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())){
            financeAaVoucher1 = new FinanceAaVoucher();
        }else {
            List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherOrder(financeAaVoucher);
            if (financeAaVouchers!=null){
                for (int i = 0; i<financeAaVouchers.size();i++){
                    financeAaVoucher1 = financeAaVouchers.get(0);
                }
            }

        }

        return financeAaVoucher1;
    }
    /**
     * 末笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucherLast(FinanceAaVoucher financeAaVoucher)
    {
        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherLast(financeAaVoucher);
        if (financeAaVouchers!=null) {
            for (int i = 0; i < financeAaVouchers.size(); i++) {
                financeAaVoucher1 = financeAaVouchers.get(i);
            }
        }
        return financeAaVoucher1;
    }
    /**
     * 新增凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int insertFinanceAaVoucher(FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setCreateTime(DateUtils.getNowDate());
        financeAaVoucher.setId(UUID.fastUUID().toString());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String voucherNo = financeAaVoucher.getVoucherType() + sdf1.format(financeAaVoucher.getVoucherDate());
        List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherList(financeAaVoucher);
        String voucherNo1="";
        String voucherNoSubstring;
        if (financeAaVouchers.size()>0){
            for (FinanceAaVoucher financeAaVoucher1:financeAaVouchers){
                 voucherNo1 = financeAaVoucher1.getVoucherNo();
            }
            if (!StringUtils.isEmpty(voucherNo1)){
                DecimalFormat decimalFormat=new DecimalFormat("00000");
                int i=Integer.parseInt(voucherNo1.substring(9,14))+1;
                voucherNoSubstring=decimalFormat.format(i);
                voucherNo= voucherNo+voucherNoSubstring;
            }

        }else {
            voucherNo = voucherNo+"00000";
        }
        financeAaVoucher.setVoucherNo(voucherNo);
        financeAaVoucher.setStatus("N");
        financeAaVoucher.setApid("AA");
        BigDecimal ntamtD=BigDecimal.ZERO;
        BigDecimal ntamtC=BigDecimal.ZERO;
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        if(detailList.size()>0){

            for (FinanceAaVoucherDetail financeAaVoucherDetail :detailList){

                if ("D".equals(financeAaVoucherDetail.getDrcr())){
                    ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt());
                }
                if ("C".equals(financeAaVoucherDetail.getDrcr())){
                    ntamtC=ntamtC.add(financeAaVoucherDetail.getNtamt());
                }
            }
    }

        financeAaVoucher.setDrAmt(ntamtD);
        financeAaVoucher.setCrAmt(ntamtC);
        insertFinanceAaVoucherDetail(financeAaVoucher);
        return financeAaVoucherMapper.insertFinanceAaVoucher(financeAaVoucher);

    }
    /**
     * 新增凭证维护-明细信息
     *
     * @param financeAaVoucher 凭证维护-主对象
     */
    public void insertFinanceAaVoucherDetail(FinanceAaVoucher financeAaVoucher)
    {
        List<FinanceAaVoucherDetail> financeAaVoucherDetailList = financeAaVoucher.getDetailList();
        String id = financeAaVoucher.getVoucherNo();
        if (StringUtils.isNotNull(financeAaVoucherDetailList))
        {
            List<FinanceAaVoucherDetail> list = new ArrayList<FinanceAaVoucherDetail>();
            for (FinanceAaVoucherDetail financeAaVoucherDetail : financeAaVoucherDetailList)
            {
                financeAaVoucherDetail.setVoucherNo(id);
                financeAaVoucherDetail.setId(UUID.fastUUID().toString());
                financeAaVoucherDetail.setVoucherDate(DateUtils.dateTime(financeAaVoucher.getVoucherDate()));
                list.add(financeAaVoucherDetail);
            }
            if (list.size() > 0)
            {
                financeAaVoucherMapper.batchFinanceAaVoucherDetail(list);
            }
        }
    }
    /**
     * 修改凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucher(FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateTime(DateUtils.getNowDate());
      String  status = "N";
        if(!status.equals(financeAaVoucher.getStatus())){
            throw new ServiceException("当前状态不允许修改！");
        }
        if (financeAaVoucher.getPostTime()!=null){
            financeAaVoucher.setPastuserName(getUsername());
        }
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(financeAaVoucher.getVoucherNo());
        insertFinanceAaVoucherDetail(financeAaVoucher);
        return financeAaVoucherMapper.updateFinanceAaVoucher(financeAaVoucher);
    }
    /**
     * 修改凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherStatus(FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateTime(DateUtils.getNowDate());

        if (!StringUtils.isEmpty(financeAaVoucher.getPotstuserName())){
            financeAaVoucher.setPotstuserName(getUsername());
        }
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(financeAaVoucher.getVoucherNo());
        insertFinanceAaVoucherDetail(financeAaVoucher);


        return financeAaVoucherMapper.updateFinanceAaVoucher(financeAaVoucher);
    }
    /**
     * 过账凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherCross(FinanceAaVoucher financeAaVoucher)
    {
        financeAaVoucher.setUpdateTime(DateUtils.getNowDate());
        if (!StringUtils.isEmpty(financeAaVoucher.getPotstuserName())){
            financeAaVoucher.setPotstuserName(getUsername());
        }
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(financeAaVoucher.getVoucherNo());
        insertFinanceAaVoucherDetail(financeAaVoucher);
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        BigDecimal ntamtD=BigDecimal.ZERO;
        BigDecimal ntamtC=BigDecimal.ZERO;
        BigDecimal qtyFrnamtD=BigDecimal.ZERO;
        BigDecimal qtyFrnamtC=BigDecimal.ZERO;
        BigDecimal drAmt=BigDecimal.ZERO;
        BigDecimal drQty=BigDecimal.ZERO;
        BigDecimal drAmtC=BigDecimal.ZERO;
        BigDecimal drQtyC=BigDecimal.ZERO;
        BigDecimal drQtyCal=BigDecimal.ZERO;
        BigDecimal drAmtCal=BigDecimal.ZERO;
        BigDecimal drQtyCalC=BigDecimal.ZERO;
        BigDecimal drAmtCalC=BigDecimal.ZERO;
        FinanceAaLedgerAcct financeAaLedgerAcct = new FinanceAaLedgerAcct();
        BeanUtils.copyProperties(financeAaVoucher,financeAaLedgerAcct);
        if(detailList.size()>0){
            for(FinanceAaVoucherDetail financeAaVoucherDetail :detailList){
                BeanUtils.copyProperties(financeAaVoucherDetail,financeAaLedgerAcct);
                    if ("D".equals(financeAaVoucherDetail.getDrcr())){
                        if ("N".equals(financeAaVoucher.getStatus())){
                            ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt().negate());
                            qtyFrnamtD = qtyFrnamtD.add(financeAaVoucherDetail.getQtyFrnamt()).negate();
                        }else if ("P".equals(financeAaVoucher.getStatus())) {
                            ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt());
                            qtyFrnamtD = qtyFrnamtD.add(financeAaVoucherDetail.getQtyFrnamt());
                        }
                    }
                    if ("C".equals(financeAaVoucherDetail.getDrcr())){
                        if ("N".equals(financeAaVoucher.getStatus())){
                            ntamtC=ntamtC.add(financeAaVoucherDetail.getNtamt().negate());
                            qtyFrnamtC = qtyFrnamtC.add(financeAaVoucherDetail.getQtyFrnamt().negate());
                        }else if ("P".equals(financeAaVoucher.getStatus())) {
                            ntamtC=ntamtC.add(financeAaVoucherDetail.getNtamt());
                            qtyFrnamtC = qtyFrnamtC.add(financeAaVoucherDetail.getQtyFrnamt());
                        }
                    }
                FinanceAaLedgerAcct financeAaLedgerAcct1 = financeAaLedgerAcctService.selectFinanceAaLedgerAcct(financeAaLedgerAcct);
                if (financeAaLedgerAcct1!=null){
                    drAmt = drAmt.add(financeAaLedgerAcct1.getDrAmt());
                    drQty =drQty.add(financeAaLedgerAcct1.getDrQty()) ;
                    drAmtC = drAmt.add(financeAaLedgerAcct1.getCrAmt());
                    drQtyC =drQty.add(financeAaLedgerAcct1.getCrQty()) ;
                    BeanUtils.copyProperties(financeAaVoucherDetail,financeAaLedgerAcct1);
                    financeAaLedgerAcct1.setDrAmt(ntamtD.add(drAmt));
                    financeAaLedgerAcct1.setDrQty(qtyFrnamtD.add(drQty));
                    financeAaLedgerAcct1.setCrAmt(ntamtC.add(drAmtC));
                    financeAaLedgerAcct1.setCrQty(qtyFrnamtC.add(drQtyC));
                    financeAaLedgerAcctService.updateFinanceAaLedgerAcct(financeAaLedgerAcct1);
                }else {
                    financeAaLedgerAcct.setDrAmt(ntamtD.add(drAmt));
                    financeAaLedgerAcct.setCrAmt(ntamtC.add(drAmtC));
                    financeAaLedgerAcct.setDrQty(qtyFrnamtD.add(drQty));
                    financeAaLedgerAcct.setCrQty(qtyFrnamtC.add(drQtyC));
                    financeAaLedgerAcct.setId(UUID.fastUUID().toString());
                    financeAaLedgerAcctService.insertFinanceAaLedgerAcct(financeAaLedgerAcct);
                }
                FinanceAaLedgerlCal financeAaLedgerlCal = new FinanceAaLedgerlCal();
                BeanUtils.copyProperties(financeAaVoucherDetail,financeAaLedgerlCal);
                FinanceAaLedgerlCal financeAaLedgerlCal1 = financeAaLedgerlCalService.selectFinanceAaLedgerlCal(financeAaLedgerlCal);
                if (financeAaLedgerlCal1!=null){
                    drQtyCal = drQtyCal.add(financeAaLedgerlCal1.getDrQty());
                    drAmtCal = drAmtCal.add(financeAaLedgerlCal1.getDrAmt());
                    drQtyCalC = drQtyCal.add(financeAaLedgerlCal1.getCrQty());
                    drAmtCalC = drAmtCal.add(financeAaLedgerlCal1.getCrAmt());
                    BeanUtils.copyProperties(financeAaVoucherDetail,financeAaLedgerlCal1);
                    financeAaLedgerlCal1.setDrAmt(ntamtD.add(drAmt));
                    financeAaLedgerlCal1.setDrQty(qtyFrnamtD.add(drQty));
                    financeAaLedgerlCal1.setCrAmt(ntamtC.add(drQtyCalC));
                    financeAaLedgerlCal1.setCrQty(qtyFrnamtC.add(drAmtCalC));
                    financeAaLedgerlCalService.updateFinanceAaLedgerlCal(financeAaLedgerlCal1);
                }else {
                    financeAaLedgerlCal.setDrAmt(ntamtD.add(drAmt));
                    financeAaLedgerlCal.setDrQty(qtyFrnamtD.add(drQty));
                    financeAaLedgerlCal.setCrAmt(ntamtC.add(drQtyCalC));
                    financeAaLedgerlCal.setCrQty(qtyFrnamtC.add(drAmtCalC));
                    financeAaLedgerlCal.setId(UUID.fastUUID().toString());
                    financeAaLedgerlCalService.insertFinanceAaLedgerlCal(financeAaLedgerlCal);
                }
            }
        }

        financeAaVoucher.setDrAmt(ntamtD);
        financeAaVoucher.setCrAmt(ntamtC);

        return financeAaVoucherMapper.updateFinanceAaVoucher(financeAaVoucher);
    }
    /**
     * 检查资料数据完整-主表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public void inspectFinanceAaVoucher(FinanceAaVoucher financeAaVoucher)
    {
        if (financeAaVoucher.getCompanyId()==null||financeAaVoucher.getVoucherDate()==null||
                financeAaVoucher.getApid()==null||financeAaVoucher.getPgrmid()==null||financeAaVoucher.getVoucherDesc()==null){

            throw new ServiceException("公司别、凭证类别、凭证日期、抛帐系统代号、抛帐程序名称、凭证摘要说明，不能为空！");
        }else {
            QueryWrapper<FinanceAccountYear> wrapper = new QueryWrapper<>();
            wrapper.likeRight("account_period", financeAaVoucher.getVoucherDate())
                    .eq("comp_id", financeAaVoucher.getCompanyId());
            List<FinanceAccountYear> list = financeAccountYearService.list(wrapper);
            for (FinanceAccountYear financeAccountYear :list){
                if ("N".equals(financeAccountYear.getIsClosed())){
                }else {
                    throw new ServiceException("资料不是未关账状态！");
                }
            }
        }

    }
    /**
     * 检查资料数据完整--检查资料明细表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public void inspectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher)
    {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        int i=2;
        if (detailList.size()<i){
            throw new ServiceException("明细表至少有两笔数据！");
        }
        BigDecimal ntamtC=BigDecimal.ZERO;
        BigDecimal ntamtD=BigDecimal.ZERO;
        for (FinanceAaVoucherDetail financeAaVoucherDetail:detailList){
            if (StringUtils.isEmpty(financeAaVoucherDetail.getAcctCode())||
                    StringUtils.isEmpty(financeAaVoucherDetail.getDrcr())||StringUtils.isEmpty(financeAaVoucherDetail.getSrlDesc())){
                throw new ServiceException("会计科目、借贷别、凭证分录摘要不能为空！");
            }
            if (financeAaVoucherDetail.getNtamt().equals( BigDecimal.ZERO)){
                throw new ServiceException("借贷金额不能为空！");
            }
            if (financeAaVoucherDetail.getQtyFrnamt().equals(BigDecimal.ZERO)&& financeAaVoucherDetail.getNtamt().equals(BigDecimal.ZERO)){
                throw new ServiceException("数量和金额不能同时为零！");
            }
            String qtyFrnamt = financeAaVoucherDetail.getQtyFrnamt().toString();
            String ntamt = financeAaVoucherDetail.getQtyFrnamt().toString();

            String zhengShu="/(^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:.\\d{1,2})?$)/";
            Pattern pattern = Pattern.compile(zhengShu);
            Matcher matcher = pattern.matcher(ntamt);
            if (!matcher.matches()){
                throw new ServiceException("金额小数点必须小于等于2位！");
            }
            String zhengShuqtyFrnamt="/(^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:.\\d{1,3})?$)/";
            Pattern patternqtyFrnamt = Pattern.compile(zhengShuqtyFrnamt);
            Matcher matcherqtyFrnamt = patternqtyFrnamt.matcher(qtyFrnamt);
            if (!matcherqtyFrnamt.matches()){
                throw new ServiceException("数量小数点必须小于等于3位！");
            }
            if ("D".equals(financeAaVoucherDetail.getDrcr())){
                 ntamtD =ntamtD.add(financeAaVoucherDetail.getNtamt());
            }
            if ("C".equals(financeAaVoucherDetail.getDrcr())){
                ntamtC =ntamtC.add(financeAaVoucherDetail.getNtamt());
            }
            if (!ntamtD.subtract(ntamtC).equals(BigDecimal.ZERO)){
                throw new ServiceException("金额借贷金额必须平衡！");
            }
        }

    }
    /**
     * 检查资料数据有效性-会计科目
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public void inspectFinanceAccountingList(FinanceAaVoucher financeAaVoucher)
    {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        for (FinanceAaVoucherDetail financeAaVoucherDetail :detailList){
            String acctId = financeAaVoucherDetail.getAcctId();
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp = financeAaAcctcodeCorpMapper.selectAcctId(acctId);
            if(financeAaAcctcodeCorp!=null){
                if (!financeAaAcctcodeCorp.getDisabledCode().equals("Y")&&!financeAaAcctcodeCorp.getIsVoucher().equals("Y")){
                    throw new ServiceException("状态为作废或传票性科目为否!");
                }
            }else {
                throw new ServiceException("该会计科目在会计科目——公司级不存在");
            }
        }

    }
    /**
     * 检查资料数据有效性-核算项目
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public void inspectFinanceCalTypeList(FinanceAaVoucher financeAaVoucher)
    {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        for (FinanceAaVoucherDetail financeAaVoucherDetail :detailList){
            String acctId = financeAaVoucherDetail.getAcctId();
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp = financeAaAcctcodeCorpMapper.selectAcctId(acctId);
            if(financeAaAcctcodeCorp!=null){
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodea())){

                }
            }
        }
    }
    /**
     * 批量删除凭证维护-主
     *
     * @param financeAaVoucher 需要删除的凭证维护-主主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherByIds(FinanceAaVoucher financeAaVoucher)
    {
        String[] ids={financeAaVoucher.getVoucherNo()};
        FinanceAaVoucherBackup financeAaVoucherBackup = new FinanceAaVoucherBackup();
        BeanUtils.copyProperties(financeAaVoucher,financeAaVoucherBackup);
        financeAaVoucherBackup.setDetailList(financeAaVoucher.getDetailList());
        financeAaVoucherBackupService.insertFinanceAaVoucherBackup(financeAaVoucherBackup);
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNos(ids);
        return financeAaVoucherMapper.deleteFinanceAaVoucherByIds(ids);

    }

    /**
     * 删除凭证维护-主信息
     *
     * @param id 凭证维护-主主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherById(String id)
    {
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(id);
        return financeAaVoucherMapper.deleteFinanceAaVoucherById(id);
    }
    /**
     * 导入凭证
     *
     * @param financeAaVoucher 凭证数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importFinanceAaVoucher(List<FinanceAaVoucherDTO> financeAaVoucher, Boolean isUpdateSupport, String operName)
    {
        StringBuilder successMsg = new StringBuilder();
        if (StringUtils.isNull(financeAaVoucher) || financeAaVoucher.size() == 0)
        {
            successMsg.insert(0, "导入探亲假数据不能为空！");
            throw new ServiceException("导入探亲假数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;

        StringBuilder failureMsg = new StringBuilder();
        for (FinanceAaVoucherDTO financeAaVoucher1 : financeAaVoucher)
        {
            try
            {

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
