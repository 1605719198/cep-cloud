package com.jlkj.finance.aa.service.impl;

import com.jlkj.finance.aa.dto.FinanceAaLedgerAcctDTO;
import com.jlkj.finance.aa.mapper.FinanceAaAcctcodeCorpMapper;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerAcctMapper;
import com.jlkj.finance.aa.mapper.FinanceAaLedgerlCalMapper;
import com.jlkj.finance.aa.mapper.FinanceAaVoucherDetailMapper;
import com.jlkj.finance.aa.service.IFinanceAaDetailSteelService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 明细报表Service业务层处理
 *
 * @author 265799
 * @date 2023-06-02
 */
@Service
public class FinanceAaDetailSteelServiceImpl  implements IFinanceAaDetailSteelService {
    @Autowired
    private FinanceAaLedgerAcctMapper financeAaLedgerAcctMapper;
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;
    @Autowired
    private FinanceAaVoucherDetailMapper financeAaVoucherDetailMapper;
    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;
    @Override
    public List<Map<String, Object>> selectListDetailSteel(FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        List<Map<String, Object>> maps;
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 = new FinanceAaLedgerAcctDTO();
        BeanUtils.copyProperties(financeAaLedgerAcctDTO, financeAaLedgerAcctDTO1);
        //获取明细表余额表去重的会计科目
        List<FinanceAaLedgerAcctDTO> acctCode;
        //会计科目数组
        String[] acctCode1;
        //当只印有交易资料为Y时，获取全部的会计科目
        if (ConstantsUtil.CODE_Y.equals(financeAaLedgerAcctDTO.getTransactionalData())) {
            //获取明细表余额表去重的会计科目满足筛选科目级别
            acctCode1 = financeAaLedgerAcctMapper.selectAcctCode(financeAaLedgerAcctDTO);
        }
        else {
            //获取明细表的会计科目集合满足筛选科目级别
            acctCode1 = financeAaVoucherDetailMapper.seleAcctCode(financeAaLedgerAcctDTO);
        }
        //批量查询会计科目编码对应的名称
        acctCode= financeAaAcctcodeCorpMapper.selectForeach(acctCode1,financeAaLedgerAcctDTO.getCompanyId());
      //生成满足会计科目编码的map
        maps = maps(acctCode, financeAaLedgerAcctDTO1);
        return maps;
    }
    public   List<Map<String, Object>> maps(List<FinanceAaLedgerAcctDTO> acctCode,FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO) {
        List<Map<String, Object>> maps = new ArrayList<>();
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 = new FinanceAaLedgerAcctDTO();
        BeanUtils.copyProperties(financeAaLedgerAcctDTO, financeAaLedgerAcctDTO1);
        //获取核算项目一的期初余额、数量/外币
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 ;
        //获取会计科目的期初余额、数量/外币
        FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO3;
        for (FinanceAaLedgerAcctDTO acctCode1 : acctCode) {
            //获取当前会计科目的核算项目1的集合
            List<FinanceAaLedgerAcctDTO> calNamebAdd = new ArrayList<>();
            financeAaLedgerAcctDTO.setAcctCode(acctCode1.getAcctCode());
            List<FinanceAaLedgerAcctDTO> calNameAdd = new ArrayList<>();
            //获取会计科目的期初余额、数量/外币
            financeAaLedgerAcctDTO3 = financeAaLedgerlCalMapper.selectBgnAmtAndBgnQty(financeAaLedgerAcctDTO);
            if (financeAaLedgerAcctDTO3 ==null){
                financeAaLedgerAcctDTO3 = new FinanceAaLedgerAcctDTO();
                financeAaLedgerAcctDTO3.setBgnQty(BigDecimal.ZERO);
                financeAaLedgerAcctDTO3.setBgnAmt(BigDecimal.ZERO);
            }
            List<FinanceAaLedgerAcctDTO> calName = financeAaLedgerlCalMapper.selectDetailSteelCalName(financeAaLedgerAcctDTO);
            List<FinanceAaLedgerAcctDTO> calDetailName = financeAaVoucherDetailMapper.seleClName(financeAaLedgerAcctDTO);
            calNameAdd.addAll(calDetailName);
            calNameAdd.addAll(calName);
            List<FinanceAaLedgerAcctDTO> list = calNameAdd.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> p.getCalNamea()))), ArrayList::new));
            for (int i = 0; i<list.size() ;i++){
                String calNamea = list.get(i).getCalNamea();
                financeAaLedgerAcctDTO.setCalNameaStart(calNamea);
                financeAaLedgerAcctDTO.setCalName(calNamea);
                //获取核算项目一的期初余额、数量/外币
                financeAaLedgerAcctDTO2 = financeAaLedgerAcctMapper.selectBgnAmtAndBgnQty(financeAaLedgerAcctDTO);
                //核算项目1的第一行的拼接
                    list.get(i).setVoucherDate("#核算项目一期初#");
                    if (financeAaLedgerAcctDTO2.getBgnAmt().compareTo(BigDecimal.ZERO) > 0){
                        list.get(i).setDrcr(ConstantsUtil.DRCRC);
                    }else {
                        list.get(i).setDrcr(ConstantsUtil.DRCRD);
                    }
                    if (financeAaLedgerAcctDTO2!=null){
                        list.get(i).setBalance(financeAaLedgerAcctDTO2.getBgnAmt());
                        list.get(i).setQtyFrnamtBalance(financeAaLedgerAcctDTO2.getBgnQty());
                    }else {
                        list.get(i).setBalance(BigDecimal.ZERO);
                        list.get(i).setQtyFrnamtBalance(BigDecimal.ZERO);
                    }
                    calNamebAdd.add( list.get(i));


                    //当期会计科目核算项目1之下的2-4核算项目
                    List<FinanceAaLedgerAcctDTO> calNameb = financeAaVoucherDetailMapper.selectcalNameList(financeAaLedgerAcctDTO);
                   if(calNameb.size()>0){
                    for (int b = 0;b<calNameb.size();b++){
                        //核算项目1之下的2-4核算项目的余额、额数量/外币金额
                        if (b==0){
                            BigDecimal bgnAmt = ((null == list.get(i).getBalance()? BigDecimal.ZERO : list.get(i).getBalance()).add(calNameb.get(b).getNtamtD())).subtract(calNameb.get(b).getNtamtC());
                            calNameb.get(b).setBalance( bgnAmt);
                            BigDecimal qtyFrnamtBalance = ((null ==list.get(i).getQtyFrnamtBalance()? BigDecimal.ZERO : list.get(i).getQtyFrnamtBalance()).add(calNameb.get(b).getQtyFrnamtD())).subtract(calNameb.get(b).getQtyFrnamtC());
                            calNameb.get(b).setQtyFrnamtBalance(qtyFrnamtBalance);
                        }else {
                            BigDecimal bgnAmt = ((null == calNameb.get(b-1).getBalance()? BigDecimal.ZERO : calNameb.get(b-1).getBalance()).add(calNameb.get(b).getNtamtD())).subtract(calNameb.get(b).getNtamtC());
                            calNameb.get(b).setBalance( bgnAmt);
                            BigDecimal qtyFrnamtBalance = ((null ==calNameb.get(b-1).getQtyFrnamtBalance()? BigDecimal.ZERO : calNameb.get(b-1).getQtyFrnamtBalance()).add(calNameb.get(b).getQtyFrnamtD())).subtract(calNameb.get(b).getQtyFrnamtC());
                            calNameb.get(b).setQtyFrnamtBalance(qtyFrnamtBalance);
                        }
                        calNamebAdd.add(calNameb.get(b));
                    }
                }
                //是否含未过账凭证
                if (ConstantsUtil.DISABLEDCODE.equals(financeAaLedgerAcctDTO.getUnpostedVoucher())) {
                    calNamebAdd = selectAccountLevel(calNamebAdd, financeAaLedgerAcctDTO);
                }
                //如果是借方
                if (ConstantsUtil.DRCRC.equals(financeAaLedgerAcctDTO.getDebitOrCredit())){
                    List<FinanceAaLedgerAcctDTO> finalCalNamebAdd = calNamebAdd;
                    calNamebAdd.stream().findFirst().map(vo -> {
                        if (ConstantsUtil.DRCRD.equals(vo.getDrcr())&& !vo.getVoucherDate().equals(ConstantsUtil.NAMECAL)){
                            if (vo.getDrAmt()==null){
                                finalCalNamebAdd.remove(vo);
                            } else if ( vo.getDrAmt().compareTo(BigDecimal.ZERO) == 0){
                                finalCalNamebAdd.remove(vo);
                            }
                        }
                        return vo;
                    });
                }
                //如果是贷方
                if (ConstantsUtil.DRCRD.equals(financeAaLedgerAcctDTO.getDebitOrCredit()) ){
                    List<FinanceAaLedgerAcctDTO> finalCalNamebAdd = calNamebAdd;
                    calNamebAdd.stream().findFirst().map(vo -> {
                        if (ConstantsUtil.DRCRC.equals(vo.getDrcr())&& !vo.getVoucherDate().equals(ConstantsUtil.NAMECAL)){
                            if (vo.getCrAmt()==null){
                                finalCalNamebAdd.remove(vo);
                            }else if ( vo.getCrAmt().compareTo(BigDecimal.ZERO) == 0  ){
                                finalCalNamebAdd.remove(vo);
                            }
                        }
                        return vo;
                    });
                }
                //期初、本年合计
                calNamebAdd= OpeningAdd(calNamebAdd,financeAaLedgerAcctDTO);
            }

            Map mapTemp = new HashMap(16);
            mapTemp.put("acctCode1", acctCode1.getAcctCode() + acctCode1.getAcctName());
            mapTemp.put("bgnAmt",financeAaLedgerAcctDTO3.getBgnAmt());
            mapTemp.put("bgnQty", financeAaLedgerAcctDTO3.getBgnQty());
            mapTemp.put("list", calNamebAdd);
            maps.add(mapTemp);
        }
        return maps;
    }

    public List<FinanceAaLedgerAcctDTO> OpeningAdd(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO,FinanceAaLedgerAcctDTO acctCode1) {
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTOS2 = new ArrayList<>();
        Map<String, String> dateMap = new HashMap<>(16);
        BigDecimal drQty;
        BigDecimal drAmt;
        BigDecimal crQty;
        BigDecimal crAmt;
        BigDecimal drQtyAdd = BigDecimal.ZERO;
        BigDecimal drAmtAdd = BigDecimal.ZERO;
        BigDecimal crQtyAdd = BigDecimal.ZERO;
        BigDecimal crAmtAdd = BigDecimal.ZERO;
        BigDecimal endOfPeriodQtyAdd = BigDecimal.ZERO;
        BigDecimal endOfPeriodAmtAdd = BigDecimal.ZERO;
        for (int i = 0; i < financeAaLedgerAcctDTO.size(); i++) {
            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
            //list大于1时的本期合计逻辑，根据Map数组放初次放key之后进行本期合计计算所以要求大于1
            if (financeAaLedgerAcctDTO.size() > 1) {
                if (!dateMap.containsKey(financeAaLedgerAcctDTO.get(i).getVoucherDate())&& financeAaLedgerAcctDTO.get(i).getVoucherDate()!=null ) {
                    if (!financeAaLedgerAcctDTO.get(i).getVoucherDate().equals(ConstantsUtil.NAMECAL)) {
                        if (dateMap.size() > 0) {
                            financeAaLedgerAcctDTO2.setCrcyUnit("本期合计");
                            financeAaLedgerAcctDTO2.setQtyFrnamtD(drQtyAdd);
                            financeAaLedgerAcctDTO2.setNtamtD(drAmtAdd);
                            financeAaLedgerAcctDTO2.setQtyFrnamtC(crQtyAdd);
                            financeAaLedgerAcctDTO2.setNtamtC(crAmtAdd);
                            financeAaLedgerAcctDTO2.setQtyFrnamtBalance(endOfPeriodQtyAdd);
                            financeAaLedgerAcctDTO2.setBalance(endOfPeriodAmtAdd);
                            if (financeAaLedgerAcctDTO2.getNtamtD().compareTo(financeAaLedgerAcctDTO2.getNtamtC()) == -1) {
                                financeAaLedgerAcctDTO2.setDrcr(ConstantsUtil.DRCRC);
                            } else {
                                financeAaLedgerAcctDTO2.setDrcr(ConstantsUtil.DRCRD);
                            }
                            financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO2);
                            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO3;
                            //根据公司别、会计周期、会计科目、核算项目1查询年度累计
                            financeAaLedgerAcctDTO3= financeAaLedgerlCalMapper.selectBnlj(acctCode1);
                            BigDecimal yearDrQty = financeAaLedgerAcctDTO3.getYearDrQty();
                            BigDecimal yearCrQty = financeAaLedgerAcctDTO3.getYearCrQty();
                            BigDecimal yearCrAmt = financeAaLedgerAcctDTO3.getYearCrAmt();
                            BigDecimal yearDrAmt = financeAaLedgerAcctDTO3.getYearDrAmt();
                            financeAaLedgerAcctDTO3.setQtyFrnamtD(yearDrQty.add(financeAaLedgerAcctDTO2.getQtyFrnamtD()));
                            financeAaLedgerAcctDTO3.setQtyFrnamtC(yearCrQty.add(financeAaLedgerAcctDTO2.getQtyFrnamtC()));
                            financeAaLedgerAcctDTO3.setNtamtC(yearCrAmt.add(financeAaLedgerAcctDTO2.getNtamtC()));
                            financeAaLedgerAcctDTO3.setNtamtD(yearDrAmt.add(financeAaLedgerAcctDTO2.getNtamtD()));
                            financeAaLedgerAcctDTO3.setCrcyUnit("本年累计");
                            financeAaLedgerAcctDTO3.setDrcr(financeAaLedgerAcctDTO2.getDrcr());
                            financeAaLedgerAcctDTO3.setQtyFrnamtBalance(financeAaLedgerAcctDTO2.getQtyFrnamtBalance());
                            financeAaLedgerAcctDTO3.setBalance(financeAaLedgerAcctDTO2.getBalance());
                            financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO3);
                            dateMap.clear();
                            endOfPeriodQtyAdd = BigDecimal.ZERO;
                            endOfPeriodAmtAdd = BigDecimal.ZERO;
                            drQtyAdd = BigDecimal.ZERO;
                            drAmtAdd = BigDecimal.ZERO;
                            crQtyAdd = BigDecimal.ZERO;
                            crAmtAdd = BigDecimal.ZERO;
                        }
                        dateMap.put(financeAaLedgerAcctDTO.get(i).getVoucherDate(), financeAaLedgerAcctDTO.get(i).getVoucherDate());
                        financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
                    }
                }
            }

            drQty = null == financeAaLedgerAcctDTO.get(i).getQtyFrnamtD() ? BigDecimal.ZERO : financeAaLedgerAcctDTO.get(i).getQtyFrnamtD();
            drAmt = null == financeAaLedgerAcctDTO.get(i).getNtamtD() ? BigDecimal.ZERO : financeAaLedgerAcctDTO.get(i).getNtamtD();
            crQty = null == financeAaLedgerAcctDTO.get(i).getQtyFrnamtC() ? BigDecimal.ZERO : financeAaLedgerAcctDTO.get(i).getQtyFrnamtC();
            crAmt = null == financeAaLedgerAcctDTO.get(i).getNtamtC() ? BigDecimal.ZERO : financeAaLedgerAcctDTO.get(i).getNtamtC();
            BigDecimal balance =null ==  financeAaLedgerAcctDTO.get(i).getBalance()? BigDecimal.ZERO : financeAaLedgerAcctDTO.get(i).getBalance();
            BigDecimal qtyFrnamtBalance =null == financeAaLedgerAcctDTO.get(i).getQtyFrnamtBalance()? BigDecimal.ZERO : financeAaLedgerAcctDTO.get(i).getQtyFrnamtBalance();
            financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO.get(i));
            drQtyAdd = drQtyAdd.add(drQty);
            drAmtAdd = drAmtAdd.add(drAmt);
            crQtyAdd = crQtyAdd.add(crQty);
            crAmtAdd = crAmtAdd.add(crAmt);
            endOfPeriodQtyAdd = endOfPeriodQtyAdd.add(balance);
            endOfPeriodAmtAdd = endOfPeriodAmtAdd.add(qtyFrnamtBalance);
        }
        if (financeAaLedgerAcctDTO.size()!=1 &&!financeAaLedgerAcctDTO.get(financeAaLedgerAcctDTO.size()-1).getVoucherDate().equals(ConstantsUtil.NAMECAL)) {
            //list=1时或最后一笔时的本期合计
            dateMap.clear();
            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2 = new FinanceAaLedgerAcctDTO();
            financeAaLedgerAcctDTO2.setCrcyUnit("本期合计");
            financeAaLedgerAcctDTO2.setQtyFrnamtD(drQtyAdd);
            financeAaLedgerAcctDTO2.setNtamtD(drAmtAdd);
            financeAaLedgerAcctDTO2.setQtyFrnamtC(crQtyAdd);
            financeAaLedgerAcctDTO2.setNtamtC(crAmtAdd);
            financeAaLedgerAcctDTO2.setQtyFrnamtBalance(endOfPeriodAmtAdd);
            financeAaLedgerAcctDTO2.setBalance(endOfPeriodQtyAdd);
            if (financeAaLedgerAcctDTO2.getNtamtD().compareTo(financeAaLedgerAcctDTO2.getNtamtC()) == -1) {
                financeAaLedgerAcctDTO2.setDrcr(ConstantsUtil.DRCRC);
            } else {
                financeAaLedgerAcctDTO2.setDrcr(ConstantsUtil.DRCRD);
            }
            financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO2);

            FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO3 ;

            financeAaLedgerAcctDTO3= financeAaLedgerlCalMapper.selectBnlj(acctCode1);
            BigDecimal yearDrQty = financeAaLedgerAcctDTO3.getYearDrQty();
            BigDecimal yearCrQty = financeAaLedgerAcctDTO3.getYearCrQty();
            BigDecimal yearCrAmt = financeAaLedgerAcctDTO3.getYearCrAmt();
            BigDecimal yearDrAmt = financeAaLedgerAcctDTO3.getYearDrAmt();
            financeAaLedgerAcctDTO3.setQtyFrnamtD(yearDrQty.add(financeAaLedgerAcctDTO2.getQtyFrnamtD()));
            financeAaLedgerAcctDTO3.setQtyFrnamtC(yearCrQty.add(financeAaLedgerAcctDTO2.getQtyFrnamtC()));
            financeAaLedgerAcctDTO3.setNtamtC(yearCrAmt.add(financeAaLedgerAcctDTO2.getNtamtC()));
            financeAaLedgerAcctDTO3.setNtamtD(yearDrAmt.add(financeAaLedgerAcctDTO2.getNtamtD()));
            financeAaLedgerAcctDTO3.setDrcr(financeAaLedgerAcctDTO2.getDrcr());
            financeAaLedgerAcctDTO3.setQtyFrnamtBalance(financeAaLedgerAcctDTO2.getQtyFrnamtBalance());
            financeAaLedgerAcctDTO3.setBalance(financeAaLedgerAcctDTO2.getBalance());
            financeAaLedgerAcctDTO3.setCrcyUnit("本年累计");
            financeAaLedgerAcctDTOS2.add(financeAaLedgerAcctDTO3);
        }
        //本年累计


        return financeAaLedgerAcctDTOS2;
    }
    public List<FinanceAaLedgerAcctDTO> selectAccountLevel(List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctDTO, FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO2) {
        List<FinanceAaLedgerAcctDTO> financeAaLedgerAcctList = new ArrayList<>();

        for (FinanceAaLedgerAcctDTO financeAaLedgerAcctDTO1 : financeAaLedgerAcctDTO) {
            financeAaLedgerAcctDTO1.setCompanyId(financeAaLedgerAcctDTO2.getCompanyId());
            financeAaLedgerAcctDTO1.setStartDate(financeAaLedgerAcctDTO2.getStartDate());
            financeAaLedgerAcctDTO1.setEndDate(financeAaLedgerAcctDTO2.getEndDate());
            FinanceAaLedgerAcctDTO  financeAaLedgerAcctDTO3 = financeAaVoucherDetailMapper.selectFinanceAaLedgerAcctDTOList(financeAaLedgerAcctDTO1);
            if (financeAaLedgerAcctDTO3!=null){
                financeAaLedgerAcctDTO1.setDrAmt((null ==financeAaLedgerAcctDTO1.getDrAmt()? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getDrAmt()).add(null ==financeAaLedgerAcctDTO3.getDrAmt()? BigDecimal.ZERO : financeAaLedgerAcctDTO3.getDrAmt() ));
                financeAaLedgerAcctDTO1.setDrQty((null ==financeAaLedgerAcctDTO1.getDrQty()? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getDrQty()).add(null ==financeAaLedgerAcctDTO3.getDrQty()? BigDecimal.ZERO : financeAaLedgerAcctDTO3.getDrQty() ));
                financeAaLedgerAcctDTO1.setCrAmt((null ==financeAaLedgerAcctDTO1.getCrAmt()? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getCrAmt()).add(null ==financeAaLedgerAcctDTO3.getCrAmt()? BigDecimal.ZERO :financeAaLedgerAcctDTO3.getCrAmt()));
                financeAaLedgerAcctDTO1.setCrQty((null ==financeAaLedgerAcctDTO1.getCrQty()? BigDecimal.ZERO : financeAaLedgerAcctDTO1.getCrQty()).add(null ==financeAaLedgerAcctDTO3.getCrQty()? BigDecimal.ZERO :financeAaLedgerAcctDTO3.getCrQty()));
            }

            financeAaLedgerAcctList.add(financeAaLedgerAcctDTO1);
        }


        return financeAaLedgerAcctList;
    }


}
