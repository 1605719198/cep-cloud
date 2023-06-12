package com.jlkj.finance.aa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.finance.aa.domain.*;
import com.jlkj.finance.aa.dto.FinanceAaVoucherDTO;
import com.jlkj.finance.aa.mapper.*;
import com.jlkj.finance.aa.service.FinanceAccountYearService;
import com.jlkj.finance.aa.service.IFinanceAaVoucherService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 凭证维护-主Service业务层处理
 *
 * @author 265799
 * @date 2023-04-24
 */
@Service
public class FinanceAaVoucherServiceImpl implements IFinanceAaVoucherService {
    @Autowired
    private FinanceAaVoucherMapper financeAaVoucherMapper;
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;
    @Autowired
    private FinanceAccountYearService financeAccountYearService;
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;
    @Autowired
    private FinanceAaVoucherBackupServiceImpl financeAaVoucherBackupService;
    @Autowired
    private FinanceAaLedgerAcctServiceImpl financeAaLedgerAcctService;
    @Autowired
    private FinanceAaLedgerlCalServiceImpl financeAaLedgerlCalService;
    @Autowired
    private FinanceCaltypeMapper financeCaltypeMapper;
    @Autowired
    private FinanceCalSysRuleMapper financeCalSysRuleMapper;
    @Autowired
    private FinanceCalcodeMapper financeCalcodeMapper;

    /**
     * 查询凭证维护-主
     *
     * @param id 凭证维护-主主键
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucherById(String id) {
        return financeAaVoucherMapper.selectFinanceAaVoucherById(id);
    }

    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherListHeadVoucher(FinanceAaVoucher financeAaVoucher) {
        return financeAaVoucherMapper.selectFinanceAaVoucherListHeadVoucher(financeAaVoucher);
    }

    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher) {
        return financeAaVoucherMapper.selectFinanceAaVoucherList(financeAaVoucher);
    }

    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherDetailList(FinanceAaVoucher financeAaVoucher) {
        return financeAaVoucherMapper.selectFinanceAaVoucherDetailList(financeAaVoucher);
    }

    /**
     * 查询凭证维护-主列表(不去重)
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public List<FinanceAaVoucher> selectFinanceAaVoucherLinkList(FinanceAaVoucher financeAaVoucher) {
        return financeAaVoucherMapper.selectFinanceAaVoucherLinkList(financeAaVoucher);
    }

    /**
     * 查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucher(FinanceAaVoucher financeAaVoucher) {
        FinanceAaVoucher financeAaVoucher1;
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            financeAaVoucher1 = new FinanceAaVoucher();
        } else {
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
    public FinanceAaVoucher selectFinanceAaVoucherFront(FinanceAaVoucher financeAaVoucher) {
        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            financeAaVoucher1 = new FinanceAaVoucher();
        } else {
            List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherFront(financeAaVoucher);
            if (financeAaVouchers != null) {
                for (int i = 0; i < financeAaVouchers.size(); i++) {
                    financeAaVoucher1 = financeAaVouchers.get(i);
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
    public FinanceAaVoucher selectFinanceAaVoucherHead(FinanceAaVoucher financeAaVoucher) {

        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            financeAaVoucher1 = new FinanceAaVoucher();
        } else {

            List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherHead(financeAaVoucher);
            if (financeAaVouchers != null) {
                for (int i = 0; i < financeAaVouchers.size(); i++) {
                    financeAaVoucher1 = financeAaVouchers.get(0);
                }
            }

        }

        return financeAaVoucher1;
    }
    /**
     * 次笔笔查询凭证维护-主列表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 凭证维护-主
     */
    @Override
    public FinanceAaVoucher selectFinanceAaVoucherOrder(FinanceAaVoucher financeAaVoucher) {

        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            financeAaVoucher1 = new FinanceAaVoucher();
        } else {
            List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherOrder(financeAaVoucher);
            if (financeAaVouchers != null) {
                for (int i = 0; i < financeAaVouchers.size(); i++) {
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
    public FinanceAaVoucher selectFinanceAaVoucherLast(FinanceAaVoucher financeAaVoucher) {
        FinanceAaVoucher financeAaVoucher1 = new FinanceAaVoucher();
        List<FinanceAaVoucher> financeAaVouchers = financeAaVoucherMapper.selectFinanceAaVoucherLast(financeAaVoucher);
        if (financeAaVouchers != null) {
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
    public String insertFinanceAaVoucher(FinanceAaVoucher financeAaVoucher) {
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            String voucherNo = insertFinanceAaVoucherVoucherNo(financeAaVoucher);
            financeAaVoucher.setVoucherNo(voucherNo);
        }
        if (StringUtils.isEmpty(financeAaVoucher.getStatus())) {
            financeAaVoucher.setStatus("N");
        }
        financeAaVoucher.setApid("AA");
        if (StringUtils.isEmpty(financeAaVoucher.getPgrmid())) {
            financeAaVoucher.setPgrmid("voucherAA");
        }
        financeAaVoucher.setVoucherDesc(financeAaVoucher.getDetailList().get(0).getSrlDesc());
        BigDecimal ntamtD = BigDecimal.ZERO;
        BigDecimal ntamtC = BigDecimal.ZERO;
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        if (detailList.size() > 0) {
            for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList) {
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                if ("D".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt());
                }
                if ("C".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtC = ntamtC.add(financeAaVoucherDetail.getNtamt());
                }
            }
        }
        financeAaVoucher.setDrAmt(ntamtD);
        financeAaVoucher.setCrAmt(ntamtC);
        if (financeAaVoucher.getPastuserName() == null) {
            financeAaVoucher.setPastuserName(getUsername());
        }
        String inspectionCollection = inspectionCollection(financeAaVoucher);
        if (StringUtils.isEmpty(inspectionCollection)) {
            insertFinanceAaVoucherDetail(financeAaVoucher);
            financeAaVoucherMapper.insertFinanceAaVoucher(financeAaVoucher);
        }

        return inspectionCollection;

    }

    /**
     * 新增凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public List<Map<String, String>> insertFinanceAaVoucherSubmitForm(FinanceAaVoucher financeAaVoucher) {
        List<Map<String, String>> maps = new ArrayList<>();
        Map map = new HashMap(16);
        String voucherNo = insertFinanceAaVoucherVoucherNo(financeAaVoucher);
        financeAaVoucher.setVoucherNo(voucherNo);
        financeAaVoucher.setStatus("N");
        financeAaVoucher.setApid("AA");
        if (StringUtils.isEmpty(financeAaVoucher.getPgrmid())) {
            financeAaVoucher.setPgrmid("voucherAA");
        }
        financeAaVoucher.setVoucherDesc(financeAaVoucher.getDetailList().get(0).getSrlDesc());
        BigDecimal ntamtD = BigDecimal.ZERO;
        BigDecimal ntamtC = BigDecimal.ZERO;
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        if (detailList.size() > 0) {
            for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList) {
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                if ("D".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt());
                }
                if ("C".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtC = ntamtC.add(financeAaVoucherDetail.getNtamt());
                }
            }
        }
        financeAaVoucher.setDrAmt(ntamtD);
        financeAaVoucher.setCrAmt(ntamtC);
        if (financeAaVoucher.getPastuserName() == null) {
            financeAaVoucher.setPastuserName(getUsername());
        }
        String inspectionCollection = inspectionCollection(financeAaVoucher);
        if (StringUtils.isEmpty(inspectionCollection)) {
            insertFinanceAaVoucherDetail(financeAaVoucher);
            financeAaVoucherMapper.insertFinanceAaVoucher(financeAaVoucher);
        }
        map.put("msg", inspectionCollection);
        map.put("voucherNo", financeAaVoucher.getVoucherNo());
        maps.add(map);
        return maps;

    }

    /**
     * 红冲凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public List<Map<String,String>> insertFinanceAaVoucherHongChong(FinanceAaVoucher financeAaVoucher) {
        List<Map<String, String>> maps = new ArrayList<>();
        Map map = new HashMap(16);
        String voucherNo1 = financeAaVoucher.getVoucherNo();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            String voucherNo = insertFinanceAaVoucherVoucherNo(financeAaVoucher);
            financeAaVoucher.setVoucherNo(voucherNo);
        }
        if (StringUtils.isEmpty(financeAaVoucher.getStatus())) {
            financeAaVoucher.setStatus("N");
        }
        financeAaVoucher.setApid("AA");
        if (StringUtils.isEmpty(financeAaVoucher.getPgrmid())) {
            financeAaVoucher.setPgrmid("voucherAA");
        }
        financeAaVoucher.setId(UUID.fastUUID().toString());
        if (StringUtils.isEmpty(financeAaVoucher.getPgrmid())) {
            financeAaVoucher.setPgrmid("voucherAA");
        }
        financeAaVoucher.setVoucherDesc(financeAaVoucher.getDetailList().get(0).getSrlDesc());
        BigDecimal ntamtD = BigDecimal.ZERO;
        BigDecimal ntamtC = BigDecimal.ZERO;
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        if (detailList.size() > 0) {
            for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList) {
                financeAaVoucherDetail.setQtyFrnamt((null == financeAaVoucherDetail.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getQtyFrnamt()).negate());
                financeAaVoucherDetail.setSrlDesc("红冲凭证" + voucherNo1);
                financeAaVoucherDetail.setNtamt((null == financeAaVoucherDetail.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getNtamt()).negate());
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                if ("D".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt().negate());
                }
                if ("C".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtC = ntamtC.add(financeAaVoucherDetail.getNtamt().negate());
                }
            }
        }
        financeAaVoucher.setDrAmt(ntamtD);
        financeAaVoucher.setCrAmt(ntamtC);
        if (financeAaVoucher.getPastuserName() == null) {
            financeAaVoucher.setPastuserName(getUsername());
        }
        String inspectionCollection = inspectionCollection(financeAaVoucher);
        if (StringUtils.isEmpty(inspectionCollection)) {
            insertFinanceAaVoucherDetail(financeAaVoucher);
            financeAaVoucherMapper.insertFinanceAaVoucher(financeAaVoucher);
        }
        map.put("msg", inspectionCollection);
        map.put("voucherNo", financeAaVoucher.getVoucherNo());
        maps.add(map);
        return maps;

    }

    /**
     * 红冲凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public String insertHongChong(FinanceAaVoucher financeAaVoucher) {
        String voucherNo1 = financeAaVoucher.getVoucherNo();
        if (StringUtils.isEmpty(financeAaVoucher.getVoucherNo())) {
            String voucherNo = insertFinanceAaVoucherVoucherNo(financeAaVoucher);
            financeAaVoucher.setVoucherNo(voucherNo);
        }
        if (StringUtils.isEmpty(financeAaVoucher.getStatus())) {
            financeAaVoucher.setStatus("N");
        }
        financeAaVoucher.setApid("AA");
        if (StringUtils.isEmpty(financeAaVoucher.getPgrmid())) {
            financeAaVoucher.setPgrmid("voucherAA");
        }
        financeAaVoucher.setId(UUID.fastUUID().toString());
        if (StringUtils.isEmpty(financeAaVoucher.getPgrmid())) {
            financeAaVoucher.setPgrmid("voucherAA");
        }
        BigDecimal ntamtD = BigDecimal.ZERO;
        BigDecimal ntamtC = BigDecimal.ZERO;
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        if (detailList.size() > 0) {
            for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList) {
                financeAaVoucherDetail.setQtyFrnamt((null == financeAaVoucherDetail.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getQtyFrnamt()).negate());
                financeAaVoucherDetail.setSrlDesc("红冲凭证" + voucherNo1);
                financeAaVoucherDetail.setNtamt((null == financeAaVoucherDetail.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail.getNtamt()).negate());
                financeAaVoucherDetail.setVoucherId(financeAaVoucher.getId());
                if ("D".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtD = ntamtD.add(financeAaVoucherDetail.getNtamt().negate());
                }
                if ("C".equals(financeAaVoucherDetail.getDrcr())) {
                    ntamtC = ntamtC.add(financeAaVoucherDetail.getNtamt().negate());
                }
            }
        }
        financeAaVoucher.setDrAmt(ntamtD);
        financeAaVoucher.setCrAmt(ntamtC);
        if (financeAaVoucher.getPastuserName() == null) {
            financeAaVoucher.setPastuserName(getUsername());
        }
        String inspectionCollection = inspectionCollection(financeAaVoucher);
        if (StringUtils.isEmpty(inspectionCollection)) {
            insertFinanceAaVoucherDetail(financeAaVoucher);
            financeAaVoucherMapper.insertFinanceAaVoucher(financeAaVoucher);
        }

        return financeAaVoucher.getVoucherNo();

    }

    /**
     * 获取凭证号
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public String insertFinanceAaVoucherVoucherNo(FinanceAaVoucher financeAaVoucher) {
        financeAaVoucher.setCreateTime(DateUtils.getNowDate());
        financeAaVoucher.setId(UUID.fastUUID().toString());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
        String voucherNo = "";
        QueryWrapper<FinanceAccountYear> wrapper = new QueryWrapper<>();
        wrapper.likeRight("account_period", sdf1.format(financeAaVoucher.getVoucherDate()))
                .eq("comp_id", financeAaVoucher.getCompanyId());
        FinanceAccountYear financeAccountYear = financeAccountYearService.getOne(wrapper);
        String M = "M";
        String R = "R";
        String P = "P";
        String T = "T";
        if (M.equals(financeAaVoucher.getVoucherType())) {
            Long manVoucherhrSrl = financeAccountYear.getManVoucherhrSrl();
            DecimalFormat decimalFormat = new DecimalFormat("00000");
            int i = Integer.parseInt(manVoucherhrSrl.toString()) + 1;
            voucherNo = M + financeAccountYear.getAccountPeriod() + decimalFormat.format(i);
            financeAccountYear.setManVoucherhrSrl(Long.valueOf(i));
            financeAccountYearService.updateById(financeAccountYear);
        }
        if (R.equals(financeAaVoucher.getVoucherType())) {
            Long receiveVoucherCurrentSrl = financeAccountYear.getReceiveVoucherCurrentSrl();
            DecimalFormat decimalFormat = new DecimalFormat("00000");
            int i = Integer.parseInt(receiveVoucherCurrentSrl.toString()) + 1;
            voucherNo = R + financeAccountYear.getAccountPeriod() + decimalFormat.format(i);

            financeAccountYear.setReceiveVoucherCurrentSrl(Long.valueOf(i));
            financeAccountYearService.updateById(financeAccountYear);
        }
        if (P.equals(financeAaVoucher.getVoucherType())) {
            Long payVoucherCurrentSrl = financeAccountYear.getPayVoucherCurrentSrl();
            DecimalFormat decimalFormat = new DecimalFormat("00000");
            int i = Integer.parseInt(payVoucherCurrentSrl.toString()) + 1;
            voucherNo = P + financeAccountYear.getAccountPeriod() + decimalFormat.format(i);

            financeAccountYear.setPayVoucherCurrentSrl(Long.valueOf(i));
            financeAccountYearService.updateById(financeAccountYear);
        }
        if (T.equals(financeAaVoucher.getVoucherType())) {
            Long transVoucherCurrentSrl = financeAccountYear.getTransVoucherCurrentSrl();
            DecimalFormat decimalFormat = new DecimalFormat("00000");
            int i = Integer.parseInt(transVoucherCurrentSrl.toString()) + 1;
            voucherNo = T + financeAccountYear.getAccountPeriod() + decimalFormat.format(i);

            financeAccountYear.setTransVoucherCurrentSrl(Long.valueOf(i));
            financeAccountYearService.updateById(financeAccountYear);
        }
        financeAaVoucher.setVoucherNo(voucherNo);
        return financeAaVoucher.getVoucherNo();
    }

    /**
     * 新增凭证维护-明细信息
     *
     * @param financeAaVoucher 凭证维护-主对象
     */
    public void insertFinanceAaVoucherDetail(FinanceAaVoucher financeAaVoucher) {
        List<FinanceAaVoucherDetail> financeAaVoucherDetailList = financeAaVoucher.getDetailList();
        String id = financeAaVoucher.getVoucherNo();
        if (StringUtils.isNotNull(financeAaVoucherDetailList)) {
            List<FinanceAaVoucherDetail> list = new ArrayList<FinanceAaVoucherDetail>();
            for (int i = 0; i < financeAaVoucherDetailList.size(); i++) {
                financeAaVoucherDetailList.get(i).setVoucherNo(id);
                financeAaVoucherDetailList.get(i).setId(UUID.fastUUID().toString());
                financeAaVoucherDetailList.get(i).setSrlno(Long.valueOf(i));
                financeAaVoucherDetailList.get(i).setVoucherDate(DateUtils.dateTime(financeAaVoucher.getVoucherDate()));
                list.add(financeAaVoucherDetailList.get(i));
            }
            if (list.size() > 0) {
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
    public List<Map<String,String>> updateFinanceAaVoucher(FinanceAaVoucher financeAaVoucher) {
        List<Map<String, String>> maps = new ArrayList<>();
        Map map = new HashMap(16);
        financeAaVoucher.setUpdateTime(DateUtils.getNowDate());
        String status = "N";
        if (!status.equals(financeAaVoucher.getStatus())) {
            throw new ServiceException("当前状态不允许修改！");
        }
        if (financeAaVoucher.getPostTime() != null) {
            financeAaVoucher.setPastuserName(getUsername());
        }
        String inspectionCollection = inspectionCollection(financeAaVoucher);
        if (StringUtils.isEmpty(inspectionCollection)) {
            financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(financeAaVoucher.getVoucherNo());
            insertFinanceAaVoucherDetail(financeAaVoucher);
            financeAaVoucherMapper.updateFinanceAaVoucher(financeAaVoucher);
        }
        map.put("msg", inspectionCollection);
        map.put("voucherNo", financeAaVoucher.getVoucherNo());
        maps.add(map);
        return maps;

    }

    /**
     * 修改状态凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherStatus(FinanceAaVoucher financeAaVoucher) {
        financeAaVoucher.setUpdateTime(DateUtils.getNowDate());

        if (!StringUtils.isEmpty(financeAaVoucher.getPotstuserName())) {
            financeAaVoucher.setPotstuserName(getUsername());
        }
        if (ConstantsUtil.DISABLEDCODE.equals(financeAaVoucher.getIsInspect())) {
            String inspectionCollection = inspectionCollection(financeAaVoucher);
            if (StringUtils.isEmpty(inspectionCollection)) {
                financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(financeAaVoucher.getVoucherNo());
                insertFinanceAaVoucherDetail(financeAaVoucher);
            }
        }

        return financeAaVoucherMapper.updateFinanceAaVoucher(financeAaVoucher);
    }

    /**
     * 过账凭证维护-主
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    @Override
    public int updateFinanceAaVoucherCross(FinanceAaVoucher financeAaVoucher) {
        financeAaVoucher.setUpdateTime(DateUtils.getNowDate());
        if (!StringUtils.isEmpty(financeAaVoucher.getPotstuserName())) {
            financeAaVoucher.setPotstuserName(getUsername());
        }
        financeAaVoucher.setPostTime(DateUtils.getNowDate());
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(financeAaVoucher.getVoucherNo());
        insertFinanceAaVoucherDetail(financeAaVoucher);
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        List<FinanceAaVoucherDetail> list = detailList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> p.getAcctCode()))), ArrayList::new));
        BigDecimal drAmt;
        BigDecimal drQty;
        BigDecimal drAmtC;
        BigDecimal drQtyC;
        BigDecimal drQtyCal;
        BigDecimal drAmtCal;
        BigDecimal drQtyCalC;
        BigDecimal drAmtCalC;
        if (list.size() > 0) {
            for (FinanceAaVoucherDetail financeAaVoucherDetail : list) {
                FinanceAaLedgerAcct financeAaLedgerAcct = new FinanceAaLedgerAcct();
                financeAaLedgerAcct.setCompanyId(financeAaVoucherDetail.getCompanyId());
                financeAaLedgerAcct.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                financeAaLedgerAcct.setAcctId(financeAaVoucherDetail.getAcctId());
                FinanceAaLedgerAcct financeAaLedgerAcct1 = financeAaLedgerAcctService.selectFinanceAaLedgerAcct(financeAaLedgerAcct);
                FinanceAaVoucherDetail financeAaVoucherDetail1 = financeAaVoucherDetailMapper.selectFinanceAdd(financeAaVoucherDetail);
                BeanUtils.copyProperties(financeAaVoucherDetail, financeAaLedgerAcct);
                if (ConstantsUtil.CODE_N.equals(financeAaVoucher.getStatus())) {
                    if (financeAaLedgerAcct1 != null) {
                        String id = financeAaLedgerAcct1.getId();
                        BeanUtils.copyProperties(financeAaVoucherDetail, financeAaLedgerAcct1);
                        financeAaLedgerAcct1.setId(id);
                        drAmt = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt()).add(null == financeAaLedgerAcct1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getDrAmt().negate());
                        drQty = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt()).add(null == financeAaLedgerAcct1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getDrQty().negate());
                        drAmtC = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt()).add(null == financeAaLedgerAcct1.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getCrAmt().negate());
                        drQtyC = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt()).add(null == financeAaLedgerAcct1.getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getCrQty().negate());
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerAcct1.setDrAmt(drAmt);
                            financeAaLedgerAcct1.setDrQty(drQty);
                        } else {
                            financeAaLedgerAcct1.setCrAmt(drAmtC);
                            financeAaLedgerAcct1.setCrQty(drQtyC);
                        }
                        financeAaLedgerAcct1.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerAcctService.updateFinanceAaLedgerAcct(financeAaLedgerAcct1);
                    } else {
                        drAmt = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt()).negate();
                        drQty = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt()).negate();
                        drAmtC = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt()).negate();
                        drQtyC = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt()).negate();
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerAcct.setDrAmt(drAmt);
                            financeAaLedgerAcct.setDrQty(drQty);
                        } else {
                            financeAaLedgerAcct.setCrAmt(drAmtC);
                            financeAaLedgerAcct.setCrQty(drQtyC);
                        }
                        financeAaLedgerAcct.setId(UUID.fastUUID().toString());
                        financeAaLedgerAcct.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerAcctService.insertFinanceAaLedgerAcct(financeAaLedgerAcct);
                    }
                } else if (ConstantsUtil.STATUS_P.equals(financeAaVoucher.getStatus())) {
                    if (financeAaLedgerAcct1 != null) {
                        String id = financeAaLedgerAcct1.getId();
                        BeanUtils.copyProperties(financeAaVoucherDetail, financeAaLedgerAcct1);
                        financeAaLedgerAcct1.setId(id);

                        drAmt = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt()).add(null == financeAaLedgerAcct1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getDrAmt());
                        drQty = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt()).add(null == financeAaLedgerAcct1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getDrQty());
                        drAmtC = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt()).add(null == financeAaLedgerAcct1.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerAcct1.getCrAmt());
                        drQtyC = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt()).add(null == financeAaLedgerAcct1.getCrQty() ? BigDecimal.ZERO : financeAaLedgerAcct1.getCrQty());
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerAcct1.setDrAmt(drAmt);
                            financeAaLedgerAcct1.setDrQty(drQty);
                        } else {
                            financeAaLedgerAcct1.setCrAmt(drAmtC);
                            financeAaLedgerAcct1.setCrQty(drQtyC);
                        }
                        financeAaLedgerAcct1.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerAcctService.updateFinanceAaLedgerAcct(financeAaLedgerAcct1);
                    } else {
                        drAmt = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt());
                        drQty = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt());
                        drAmtC = (null == financeAaVoucherDetail1.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getNtamt());
                        drQtyC = (null == financeAaVoucherDetail1.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail1.getQtyFrnamt());
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerAcct.setDrAmt(drAmt);
                            financeAaLedgerAcct.setDrQty(drQty);
                        } else {
                            financeAaLedgerAcct.setCrAmt(drAmtC);
                            financeAaLedgerAcct.setCrQty(drQtyC);
                        }

                        financeAaLedgerAcct.setId(UUID.fastUUID().toString());
                        financeAaLedgerAcct.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerAcctService.insertFinanceAaLedgerAcct(financeAaLedgerAcct);
                    }
                }
            }
        }
        if (detailList.size() > 0) {
            for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList) {
                FinanceAaLedgerlCal financeAaLedgerlCal = new FinanceAaLedgerlCal();
                financeAaLedgerlCal.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                BeanUtils.copyProperties(financeAaVoucherDetail, financeAaLedgerlCal);
                FinanceAaLedgerlCal financeAaLedgerlCal1 = financeAaLedgerlCalService.selectFinanceAaLedgerlCal(financeAaLedgerlCal);
                FinanceAaVoucherDetail financeAaVoucherDetail2 = financeAaVoucherDetailMapper.selectFinancecalCodeAdd(financeAaVoucherDetail);
                if (ConstantsUtil.CODE_N.equals(financeAaVoucher.getStatus())) {
                    if (financeAaLedgerlCal1 != null) {
                        String id = financeAaLedgerlCal1.getId();
                        BeanUtils.copyProperties(financeAaVoucherDetail, financeAaLedgerlCal1);
                        financeAaLedgerlCal1.setId(id);
                        drQtyCal = (null == financeAaLedgerlCal1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getDrQty()).subtract((null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt()));
                        drAmtCal = (null == financeAaLedgerlCal1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getDrAmt()).subtract(null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt());
                        drQtyCalC = (null == financeAaLedgerlCal1.getCrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getCrQty()).subtract(null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt());
                        drAmtCalC = (null == financeAaLedgerlCal1.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getCrAmt()).subtract(null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt());
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerlCal1.setDrAmt(drAmtCal);
                            financeAaLedgerlCal1.setDrQty(drQtyCal);
                        } else {
                            financeAaLedgerlCal1.setCrAmt(drQtyCalC);
                            financeAaLedgerlCal1.setCrQty(drAmtCalC);
                        }
                        financeAaLedgerlCal1.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerlCalService.updateFinanceAaLedgerlCal(financeAaLedgerlCal1);
                    } else {
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerlCal.setDrAmt(null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt().negate());
                            financeAaLedgerlCal.setDrQty(null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt().negate());
                        } else {
                            financeAaLedgerlCal.setCrAmt(null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt().negate());
                            financeAaLedgerlCal.setCrQty(null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt().negate());
                        }
                        financeAaLedgerlCal.setId(UUID.fastUUID().toString());
                        financeAaLedgerlCal.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerlCalService.insertFinanceAaLedgerlCal(financeAaLedgerlCal);
                    }
                } else if (ConstantsUtil.STATUS_P.equals(financeAaVoucher.getStatus())) {
                    if (financeAaLedgerlCal1 != null) {
                        String id = financeAaLedgerlCal1.getId();
                        BeanUtils.copyProperties(financeAaVoucherDetail, financeAaLedgerlCal1);
                        financeAaLedgerlCal1.setId(id);
                        drQtyCal = (null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt()).add(null == financeAaLedgerlCal1.getDrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getDrQty());
                        drAmtCal = (null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt()).add(null == financeAaLedgerlCal1.getDrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getDrAmt());
                        drQtyCalC = (null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt()).add(null == financeAaLedgerlCal1.getCrQty() ? BigDecimal.ZERO : financeAaLedgerlCal1.getCrQty());
                        drAmtCalC = (null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt()).add(null == financeAaLedgerlCal1.getCrAmt() ? BigDecimal.ZERO : financeAaLedgerlCal1.getCrAmt());
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerlCal1.setDrAmt(drAmtCal);
                            financeAaLedgerlCal1.setDrQty(drQtyCal);
                        } else {
                            financeAaLedgerlCal1.setCrAmt(drQtyCalC);
                            financeAaLedgerlCal1.setCrQty(drAmtCalC);
                        }
                        financeAaLedgerlCal1.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerlCalService.updateFinanceAaLedgerlCal(financeAaLedgerlCal1);
                    } else {
                        if (ConstantsUtil.DRCRD.equals(financeAaVoucherDetail.getDrcr())) {
                            financeAaLedgerlCal.setDrAmt(null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt());
                            financeAaLedgerlCal.setDrQty(null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt());

                        } else {
                            financeAaLedgerlCal.setCrAmt(null == financeAaVoucherDetail2.getNtamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getNtamt());
                            financeAaLedgerlCal.setCrQty(null == financeAaVoucherDetail2.getQtyFrnamt() ? BigDecimal.ZERO : financeAaVoucherDetail2.getQtyFrnamt());

                        }
                        financeAaLedgerlCal.setId(UUID.fastUUID().toString());
                        financeAaLedgerlCal.setAcctPeriod(financeAaVoucherDetail.getVoucherDate().substring(0, 7));
                        financeAaLedgerlCalService.insertFinanceAaLedgerlCal(financeAaLedgerlCal);
                    }
                }
            }
        }

        return financeAaVoucherMapper.updateFinanceAaVoucher(financeAaVoucher);
    }

    /**
     * 1-5集合
     *
     * @return 结果
     */
    public String inspectionCollection(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        successMsg.append(inspectFinanceAaVoucher(financeAaVoucher));
        successMsg.append(inspectFinanceAaVoucherList(financeAaVoucher));
        successMsg.append(inspectFinanceAccountingList(financeAaVoucher));
        successMsg.append(inspectFinanceCalTypeList(financeAaVoucher));
        successMsg.append(inspectFinanceCrcyUnitList(financeAaVoucher));
        return successMsg.toString();
    }

    /**
     * 导入1-5集合
     *
     * @return 结果
     */
    public String inspectionImportCollection(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        successMsg.append(inspectFinanceAaVoucher(financeAaVoucher));
        successMsg.append(inspectFinanceAccountingList(financeAaVoucher));
        successMsg.append(inspectFinanceCalTypeList(financeAaVoucher));
        successMsg.append(inspectFinanceCrcyUnitList(financeAaVoucher));
        return successMsg.toString();
    }

    /**
     * 检查资料数据完整-主表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */


    public String inspectFinanceAaVoucher(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
        if (financeAaVoucher.getCompanyId() == null && financeAaVoucher.getVoucherDate() == null &&
                financeAaVoucher.getApid() == null && financeAaVoucher.getPgrmid() == null && financeAaVoucher.getVoucherDesc() == null) {
            successMsg.append("公司别、凭证类别、凭证日期、抛帐系统代号、抛帐程序名称、凭证摘要说明，不能为空！");

        } else {
            QueryWrapper<FinanceAccountYear> wrapper = new QueryWrapper<>();
            wrapper.likeRight("account_period", sdf1.format(financeAaVoucher.getVoucherDate()))
                    .eq("comp_id", financeAaVoucher.getCompanyId());
            List<FinanceAccountYear> list = financeAccountYearService.list(wrapper);
            for (FinanceAccountYear financeAccountYear : list) {
                if ("N".equals(financeAccountYear.getIsClosed())) {
                } else {
                    successMsg.append("资料不是未关账状态");

                }
            }
        }
        return successMsg.toString();
    }

    /**
     * 检查资料数据完整--检查资料明细表
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public String inspectFinanceAaVoucherList(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        int i = 2;
        if (ConstantsUtil.DISABLEDCODE.equals(financeAaVoucher.getIsInspect())) {
            if (detailList.size() < i) {
                successMsg.append("明细表至少有两笔数据!");
            }
        }
        BigDecimal ntamtC = BigDecimal.ZERO;
        BigDecimal ntamtD = BigDecimal.ZERO;
        for (int i1 = 0; i1 < detailList.size(); i1++) {
            if (StringUtils.isEmpty(detailList.get(i1).getAcctCode()) ||
                    StringUtils.isEmpty(detailList.get(i1).getDrcr()) || StringUtils.isEmpty(detailList.get(i1).getSrlDesc())) {
                successMsg.append("第" + (i1 + 1) + "会计科目、借贷别、凭证分录摘要不能为空！");
            }
            if (detailList.get(i1).getNtamt().equals(BigDecimal.ZERO)) {
                successMsg.append("第" + (i1 + 1) + "借贷金额不能为空！");
            }
            if (detailList.get(i1).getQtyFrnamt() != null && detailList.get(i1).getNtamt() != null) {
                if (detailList.get(i1).getQtyFrnamt().equals(BigDecimal.ZERO) && detailList.get(i1).getNtamt().equals(BigDecimal.ZERO)) {
                    successMsg.append("第" + (i1 + 1) + "数量和金额不能同时为零！");
                }
                String qtyFrnamt = detailList.get(i1).getQtyFrnamt().toString();
                String ntamt = detailList.get(i1).getNtamt().toString();
                String zhengShu = "^[0-9]+\\\\.{0,1}[0-9]{0,2}$";
                Pattern pattern = Pattern.compile(zhengShu);
                Matcher matcher = pattern.matcher(ntamt);
                if (matcher.matches()) {
                    successMsg.append("第" + (i1 + 1) + "金额小数点必须小于等于2位！");
                }
                String zhengShuqtyFrnamt = " ^[0-9]+\\\\.{0,1}[0-9]{0,3}$";
                Pattern patternqtyFrnamt = Pattern.compile(zhengShuqtyFrnamt);
                Matcher matcherqtyFrnamt = patternqtyFrnamt.matcher(qtyFrnamt);
                if (matcherqtyFrnamt.matches()) {
                    successMsg.append("第" + (i1 + 1) + "数量小数点必须小于等于3位！");
                }
            }

            if ("D".equals(detailList.get(i1).getDrcr())) {
                ntamtD = ntamtD.add(detailList.get(i1).getNtamt());
            }
            if ("C".equals(detailList.get(i1).getDrcr())) {
                ntamtC = ntamtC.add(detailList.get(i1).getNtamt());
            }
        }
        if ((ntamtD.subtract(ntamtC).compareTo(BigDecimal.ZERO) != 0)) {
            successMsg.append("金额借贷金额必须平衡！");
        }
        return successMsg.toString();
    }


    /**
     * 检查资料数据有效性-会计科目
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public String inspectFinanceAccountingList(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList) {
            String acctCode = financeAaVoucherDetail.getAcctCode();
            String companyId = financeAaVoucherDetail.getCompanyId();
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp = financeAaAcctcodeCorpMapper.selectAcctId(acctCode, companyId);
            if (financeAaAcctcodeCorp != null) {
                if (!financeAaAcctcodeCorp.getDisabledCode().equals(ConstantsUtil.DISABLEDCODE) && !financeAaAcctcodeCorp.getIsVoucher().equals(ConstantsUtil.DISABLEDCODE)) {
                    successMsg.append("该会计科目" + financeAaVoucher.getAcctCode() + "状态为作废或传票性科目为否！");
                }
            } else {
                successMsg.append("该会计科目" + financeAaVoucher.getAcctCode() + "在会计科目——公司级不存在！");
            }
        }
        return successMsg.toString();
    }

    /**
     * 检查资料数据有效性-核算项目
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public String inspectFinanceCalTypeList(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        FinanceCaltype financeCaltype = new FinanceCaltype();
        FinanceCalSysRule financeCalSysRule = new FinanceCalSysRule();
        FinanceCalcode financeCalcode = new FinanceCalcode();
        for (int i = 0; i < detailList.size(); i++) {
            String acctCode = detailList.get(i).getAcctCode();
            String companyId = detailList.get(i).getCompanyId();
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp = financeAaAcctcodeCorpMapper.selectAcctId(acctCode, companyId);
            if (financeAaAcctcodeCorp != null) {
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodea())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCodea());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (StringUtils.isEmpty(detailList.get(i).getCalCodea())){
                        successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目一内容不能为空!");
                    }else  if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysa());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        String s = selectFinanceAaBaseSql(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCodea(), detailList.get(i).getCalNamea(), "第" + (i + 1) + "笔" , "一");
                        successMsg.append(s);
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule()) ) {

                            financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                            financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                            financeCalcode.setCalCode(detailList.get(i).getCalCodea());
                            FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                            if (!financeCalcode1.getCalName().equals(detailList.get(i).getCalNamea())) {
                                successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目一内容不正确!");
                            }


                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodeb())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCodeb());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (StringUtils.isEmpty(detailList.get(i).getCalCodeb())){
                        successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目二内容不能为空!");
                    }else if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysb());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        String s = selectFinanceAaBaseSql(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCodeb(), detailList.get(i).getCalNameb(), "第" + (i + 1) + "笔" , "二");
                        successMsg.append(s);
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule()) ) {

                            financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                            financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                            financeCalcode.setCalCode(detailList.get(i).getCalCodeb());
                            FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                            if (!financeCalcode1.getCalName().equals(detailList.get(i).getCalNameb())) {
                                successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目二内容不正确!");
                            }

                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodec())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCodec());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (StringUtils.isEmpty(detailList.get(i).getCalNamec())){
                        successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目三内容不能为空!");
                    }else if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysc());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        String s = selectFinanceAaBaseSql(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCodec(), detailList.get(i).getCalNamec(), "第" + (i + 1) + "笔" , "三");
                        successMsg.append(s);
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule()) ) {

                            financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                            financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                            financeCalcode.setCalCode(detailList.get(i).getCalCodec());
                            FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                            if (!financeCalcode1.getCalName().equals(detailList.get(i).getCalNamec())) {
                                successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目三内容不正确!");
                            }


                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCoded())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCoded());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (StringUtils.isEmpty(detailList.get(i).getCalCoded())){
                        successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目四内容不能为空");
                    }else if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysd());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        String s = selectFinanceAaBaseSql(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCoded(), detailList.get(i).getCalNamed(), "第" + (i + 1) + "笔" , "四");
                        successMsg.append(s);
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule())) {
                            financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                            financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                            financeCalcode.setCalCode(detailList.get(i).getCalCoded());
                            FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                            if (!financeCalcode1.getCalName().equals(detailList.get(i).getCalNamed())) {
                                successMsg.append("第" + (i + 1) + "笔" + "所选择的核算项目四内容不正确");
                            }

                    }
                }
            }
        }
        return successMsg.toString();
    }

    /**
     * 导入获取会计id、项目1-4的id
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */
    public FinanceAaVoucher inspectImportFinanceCalTypeList(FinanceAaVoucher financeAaVoucher) {
        List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
        FinanceCaltype financeCaltype = new FinanceCaltype();
        FinanceCalSysRule financeCalSysRule = new FinanceCalSysRule();
        FinanceCalcode financeCalcode = new FinanceCalcode();
        for (int i = 0; i < detailList.size(); i++) {
            String acctCode = detailList.get(i).getAcctCode();
            String companyId = detailList.get(i).getCompanyId();
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp = financeAaAcctcodeCorpMapper.selectAcctId(acctCode, companyId);
            if (financeAaAcctcodeCorp != null) {
                detailList.get(i).setAcctId(financeAaAcctcodeCorp.getAcctId());
                detailList.get(i).setGroupAcctId(financeAaAcctcodeCorp.getGroupAcctId());
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodea())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCodea());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysa());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        List<Map<String, String>> list = selectFinanceAaBaseSqlList(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCoded(), detailList.get(i).getCalNamed());
                        detailList.get(i).setCalIda(list.get(0).get("Id"));
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule())) {

                        financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        financeCalcode.setCalCode(detailList.get(i).getCalCodea());
                        FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                        detailList.get(i).setCalIda(financeCalcode1.getId());
                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodeb())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCodeb());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysb());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        List<Map<String, String>> list = selectFinanceAaBaseSqlList(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCoded(), detailList.get(i).getCalNamed());
                        detailList.get(i).setCalIdb(list.get(0).get("Id"));
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule())) {
                        financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        financeCalcode.setCalCode(detailList.get(i).getCalCodeb());
                        FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                        detailList.get(i).setCalIdb(financeCalcode1.getId());
                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCodec())) {
                    financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                    financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCodec());
                    FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                    if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                        financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalSysRule.setSys(financeAaVoucher.getSysc());
                        financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                        List<Map<String, String>> list = selectFinanceAaBaseSqlList(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCoded(), detailList.get(i).getCalNamed());
                        detailList.get(i).setCalIdc(list.get(0).get("Id"));
                    } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule())) {
                        financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                        financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                        financeCalcode.setCalCode(detailList.get(i).getCalCodec());
                        FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                        detailList.get(i).setCalIdc(financeCalcode1.getId());
                    }
                }
            }
            if (!StringUtils.isEmpty(financeAaAcctcodeCorp.getCalTypeCoded())) {
                financeCaltype.setCompanyId(financeAaAcctcodeCorp.getCompanyId());
                financeCaltype.setCalTypeCode(financeAaAcctcodeCorp.getCalTypeCoded());
                FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
                if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
                    financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                    financeCalSysRule.setSys(financeAaVoucher.getSysd());
                    financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                    FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                    List<Map<String, String>> list = selectFinanceAaBaseSqlList(financeCalSysRule1.getSqlStringDb(), detailList.get(i).getCalCoded(), detailList.get(i).getCalNamed());
                    detailList.get(i).setCalIdd(list.get(0).get("Id"));

                } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule())) {
                    financeCalcode.setCompanyId(financeCaltype1.getCompanyId());
                    financeCalcode.setCalTypeCode(financeCaltype1.getCalTypeCode());
                    financeCalcode.setCalCode(detailList.get(i).getCalCoded());
                    FinanceCalcode financeCalcode1 = financeCalcodeMapper.selectFinanceCalcode(financeCalcode);
                    detailList.get(i).setCalIdd(financeCalcode1.getId());
                }
            }
        }

        return financeAaVoucher;
    }

    public String selectFinanceAaBaseSql(String sqlQuery, String calCode, String calName, String number,String name) {
        StringBuilder successMsg = new StringBuilder();
        StringBuffer sql = new StringBuffer();
        String s;
        String s1 = "";
        String s2 = "";
        sqlQuery.replaceAll("as", "AS");
        String[] sqlStringDb = sqlQuery.split(",");
        for (int i = 0; i < sqlStringDb.length; i++) {
            if (i == 0) {
                s = sqlStringDb[0].substring(sqlStringDb[0].indexOf("SELECT") + 7, sqlStringDb[0].lastIndexOf("AS"));
            } else if (i == 1) {
                s = sqlStringDb[i].substring(0, sqlStringDb[i].indexOf("AS"));
                s1 = " and " + s + " LIKE " + "'%" + calCode + "%'";
            } else if (i == 2) {
                s = sqlStringDb[i].substring(0, sqlStringDb[i].indexOf("AS"));
                s2 = " and " + s + " LIKE " + "'%" + calName + "%'";
            }
        }
        sql.append(s1 + s2);
        List<Map<String, String>> list = financeCalSysRuleMapper.selectFinanceSqlMapList(sqlQuery + sql);
        if (list.size() != 1) {
            successMsg.append("所选择的" + number + "核算项目"+name+"内容不正确！");
        }
        return successMsg.toString();
    }

    public List<Map<String, String>> selectFinanceAaBaseSqlList(String sqlQuery, String calCode, String calName) {
        StringBuffer sql = new StringBuffer();
        String s;
        String s1 = "";
        String s2 = "";
        sqlQuery.replaceAll("as", "AS");
        String[] sqlStringDb = sqlQuery.split(",");
        for (int i = 0; i < sqlStringDb.length; i++) {
            if (i == 0) {
                s = sqlStringDb[0].substring(sqlStringDb[0].indexOf("SELECT") + 7, sqlStringDb[0].lastIndexOf("AS"));
            } else if (i == 1) {
                s = sqlStringDb[i].substring(0, sqlStringDb[i].indexOf("AS"));
                s1 = " and " + s + " LIKE " + "'%" + calCode + "%'";
            } else if (i == 2) {
                s = sqlStringDb[i].substring(0, sqlStringDb[i].indexOf("AS"));
                s2 = " and " + s + " LIKE " + "'%" + calName + "%'";
            }
        }
        sql.append(s1 + s2);
        List<Map<String, String>> list = financeCalSysRuleMapper.selectFinanceSqlMapList(sqlQuery + sql);

        return list;
    }

    public List<Map<String, String>> selectInterfaceList(String sqlQuery, String calCode, String calName) {
        StringBuffer sql = new StringBuffer();
        String s;
        String s1 = "";
        String s2 = "";
        sqlQuery.replaceAll("as", "AS");
        String[] sqlStringDb = sqlQuery.split(",");
        for (int i = 0; i < sqlStringDb.length; i++) {
            if (i == 0) {
                s = sqlStringDb[0].substring(sqlStringDb[0].indexOf("SELECT") + 7, sqlStringDb[0].lastIndexOf("AS"));
            } else if (i == 2) {
                s = sqlStringDb[i].substring(0, sqlStringDb[i].indexOf("AS"));
                s2 = " and " + s + "=" + "'" + calName + "'";
            }
        }
        sql.append(s1 + s2);
        List<Map<String, String>> list = financeCalSysRuleMapper.selectFinanceSqlMapList(sqlQuery + sql);

        return list;
    }

    /**
     * 检核会计分录的币别/数量/金额规则
     *
     * @param financeAaVoucher 凭证维护-主
     * @return 结果
     */

    public String inspectFinanceCrcyUnitList(FinanceAaVoucher financeAaVoucher) {
        StringBuilder successMsg = new StringBuilder();
        String isFrnCrcy = "Y";
        if (isFrnCrcy.equals(financeAaVoucher.getIsFrnCrcy())) {
            List<FinanceAaVoucherDetail> detailList = financeAaVoucher.getDetailList();
            for (int i = 0; i < detailList.size(); i++) {
                if (StringUtils.isEmpty(detailList.get(i).getCrcyUnit())) {
                    successMsg.append("第" + (i + 1) + "笔" + "数量单位/币别不能为空！");
                }
                if (detailList.get(i).getQtyFrnamt() == null) {
                    successMsg.append("第" + (i + 1) + "笔" + "数量/外币金额不能为空！");
                }
            }
        }
        return successMsg.toString();
    }

    /**
     * 批量删除凭证维护-主
     *
     * @param financeAaVoucher 需要删除的凭证维护-主主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaVoucherByIds(FinanceAaVoucher financeAaVoucher) {
        String[] ids = {financeAaVoucher.getVoucherNo()};
        FinanceAaVoucherBackup financeAaVoucherBackup = new FinanceAaVoucherBackup();
        BeanUtils.copyProperties(financeAaVoucher, financeAaVoucherBackup);
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
    public int deleteFinanceAaVoucherById(String id) {
        financeAaVoucherMapper.deleteFinanceAaVoucherDetailByVoucherNo(id);
        return financeAaVoucherMapper.deleteFinanceAaVoucherById(id);
    }

    /**
     * 导入凭证
     *
     * @param financeAaVoucher 凭证数据列表
     * @param isUpdateSupport  是否更新支持，如果已存在，则进行更新数据
     * @param operName         操作用户
     * @return 结果
     */
    @Override
    public List<FinanceAaVoucherDTO> importFinanceAaVoucher(List<FinanceAaVoucherDTO> financeAaVoucher, Boolean isUpdateSupport, String operName, String companyId) {
        List<FinanceAaVoucherDTO> financeAaVoucherDTO = new ArrayList<>();
        List<FinanceAaVoucherDTO> financeAaVoucherVoucherNo = new ArrayList<>();
        if (StringUtils.isNull(financeAaVoucher) || financeAaVoucher.size() == 0) {
            throw new ServiceException("导入凭证数据不能为空！");
        }
        List<FinanceAaVoucherDetail> detailList1 = new ArrayList<>();
        FinanceAaVoucher financeAaVoucher4 = new FinanceAaVoucher();
        for (int i = 0; i < financeAaVoucher.size(); i++) {
            FinanceAaVoucher financeAaVoucher3 = new FinanceAaVoucher();
            financeAaVoucher.get(i).setCompanyId(companyId);
            financeAaVoucher.get(i).setId(UUID.fastUUID().toString());
            BeanUtils.copyProperties(financeAaVoucher.get(i), financeAaVoucher3);
            FinanceAaVoucherDetail financeAaVoucherDetail = new FinanceAaVoucherDetail();
            BeanUtils.copyProperties(financeAaVoucher.get(i), financeAaVoucherDetail);
            List<FinanceAaVoucherDetail> detailList3 = new ArrayList<>();
            detailList3.add(financeAaVoucherDetail);
            financeAaVoucher3.setDetailList(detailList3);
            FinanceAaVoucher financeAaVoucher1 = inspectImportFinanceCalTypeList(financeAaVoucher3);
            financeAaVoucher1.getDetailList().get(0).setVoucherDate(DateUtils.dateTime(financeAaVoucher.get(i).getVoucherDate()));
            List<FinanceAaVoucherDetail> detailList = new ArrayList<>();
            detailList.add(financeAaVoucher1.getDetailList().get(0));
            detailList1.add(financeAaVoucher1.getDetailList().get(0));
            financeAaVoucher3.setDetailList(detailList);
            if (!StringUtils.isEmpty(inspectionImportCollection(financeAaVoucher3))) {
                financeAaVoucher.get(i).setErrorReason(inspectionImportCollection(financeAaVoucher3));
                financeAaVoucherDTO.add(financeAaVoucher.get(i));
            }
            financeAaVoucherVoucherNo.add(financeAaVoucher.get(i));
            if ((i + 1) == financeAaVoucher.size()) {
                financeAaVoucher4.setDetailList(detailList1);
                financeAaVoucher4.setIsInspect("N");
                inspectFinanceAaVoucherList(financeAaVoucher4);
                if (!StringUtils.isEmpty(inspectFinanceAaVoucherList(financeAaVoucher4))) {
                    financeAaVoucher.get(i).setErrorReason(inspectFinanceAaVoucherList(financeAaVoucher4));
                    financeAaVoucherDTO.add(financeAaVoucher.get(i));
                }
            }
        }
        if (financeAaVoucherDTO.size() == 0) {
            FinanceAaVoucher financeAaVoucher2 = new FinanceAaVoucher();
            FinanceAaVoucherDTO financeAaVoucherDTO1 = financeAaVoucher.get(0);
            BeanUtils.copyProperties(financeAaVoucherDTO1, financeAaVoucher2);
            financeAaVoucher2.setVoucherDesc(financeAaVoucher.get(0).getSrlDesc());
            financeAaVoucher2.setVoucherNo(insertFinanceAaVoucherVoucherNo(financeAaVoucher2));
            financeAaVoucher2.setPgrmid(financeAaVoucher.get(0).getSrlDesc());
            financeAaVoucherMapper.insertFinanceAaVoucher(financeAaVoucher2);
            for (FinanceAaVoucherDetail financeAaVoucherDetail : detailList1) {
                financeAaVoucherDetail.setVoucherId(financeAaVoucher2.getId());
                financeAaVoucherDetail.setVoucherNo(financeAaVoucher2.getVoucherNo());
            }
            financeAaVoucherMapper.batchFinanceAaVoucherDetail(detailList1);
        }
        return financeAaVoucherDTO;
    }
}
