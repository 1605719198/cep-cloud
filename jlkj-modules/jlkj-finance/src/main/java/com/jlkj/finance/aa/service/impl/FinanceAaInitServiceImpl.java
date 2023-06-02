package com.jlkj.finance.aa.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.finance.aa.domain.*;
import com.jlkj.finance.aa.mapper.*;
import com.jlkj.finance.aa.service.IFinanceAaInitService;
import com.jlkj.finance.utils.ConstantsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化记录Service业务层处理
 *
 * @author 265799
 * @date 2023-05-26
 */
@Service
public class FinanceAaInitServiceImpl implements IFinanceAaInitService {
    @Autowired
    private FinanceAaInitMapper financeAaInitMapper;
    @Autowired
    private FinanceAaLedgerAcctMapper financeAaLedgerAcctMapper;
    @Autowired
    private FinanceAaLedgerlCalMapper financeAaLedgerlCalMapper;

    @Autowired
    private FinanceAaAcctcodeCorpMapper financeAaAcctcodeCorpMapper;

    @Autowired
    private FinanceCaltypeMapper financeCaltypeMapper;
    @Autowired
    private FinanceCalSysRuleMapper financeCalSysRuleMapper;
    @Autowired
    private  FinanceAaVoucherServiceImpl financeAaVoucherService;

    @Autowired
    private FinanceCalcodeMapper financeCalcodeMapper;
    /**
     * 查询初始化记录
     *
     * @param id 初始化记录主键
     * @return 初始化记录
     */
    @Override
    public FinanceAaInit selectFinanceAaInitById(String id) {
        return financeAaInitMapper.selectFinanceAaInitById(id);
    }

    /**
     * 查询初始化记录列表
     *
     * @param financeAaInit 初始化记录
     * @return 初始化记录
     */
    @Override
    public List<FinanceAaInit> selectFinanceAaInitList(FinanceAaInit financeAaInit) {
        return financeAaInitMapper.selectFinanceAaInitList(financeAaInit);
    }

    /**
     * 新增初始化记录
     *
     * @param financeAaInit 初始化记录
     * @return 结果
     */
    @Override
    public int insertFinanceAaInit(FinanceAaInit financeAaInit) {
        financeAaInit.setCreateTime(DateUtils.getNowDate());
        return financeAaInitMapper.insertFinanceAaInit(financeAaInit);
    }

    /**
     * 修改初始化记录
     *
     * @param financeAaInit 初始化记录
     * @return 结果
     */
    @Override
    public int updateFinanceAaInit(FinanceAaInit financeAaInit) {
        return financeAaInitMapper.updateFinanceAaInit(financeAaInit);
    }

    /**
     * 批量删除初始化记录
     *
     * @param ids 需要删除的初始化记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaInitByIds(String[] ids) {
        return financeAaInitMapper.deleteFinanceAaInitByIds(ids);
    }

    /**
     * 删除初始化记录信息
     *
     * @param id 初始化记录主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaInitById(String id) {
        return financeAaInitMapper.deleteFinanceAaInitById(id);
    }

    @Override
    public List<FinanceAaInit> importFinanceAaInits(List<FinanceAaInit> financeAaInits,
                                                    Boolean isUpdateSupport, String operName, String acctPeriod, String companyId) {
        if (StringUtils.isNull(financeAaInits) || financeAaInits.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        List<FinanceAaInit> financeAaInitList = new ArrayList<>();


        for (FinanceAaInit financeAaInit : financeAaInits) {
            String msg = "";
            FinanceAaInit financeAaInit1 = new FinanceAaInit();
            BeanUtils.copyProperties(financeAaInit,financeAaInit1);
            FinanceAaAcctcodeCorp financeAaAcctcodeCorp1 = financeAaAcctcodeCorpMapper.selectAcctId(financeAaInit.getAcctCode(), companyId);
            financeAaInit1.setAcctId(financeAaAcctcodeCorp1.getAcctId());
            financeAaInit1.setAcctName(financeAaAcctcodeCorp1.getAcctName());
            financeAaInit1.setId(IdUtils.fastSimpleUUID());
            financeAaInit1.setCompanyId(companyId);
            financeAaInit1.setAcctPeriod(acctPeriod.substring(0,7));
            if (financeAaAcctcodeCorp1 != null) {
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp1.getCalTypeCodea())) {
                    Map<String, Object>  map = interfaceImport(financeAaAcctcodeCorp1.getCalTypeCodea(), companyId, financeAaInit.getCalNamea(), ConstantsUtil.CALRULE1,financeAaInit.getAcctCode(),"A");
                    if (!StringUtils.isEmpty(map.get("Msg").toString())){
                          msg =msg + map.get("Msg").toString();
                        financeAaInit1.setErrorMessage(msg);
                    }
                    if (StringUtils.isEmpty(map.get("Id").toString())){
                        financeAaInit1.setCalNamea("");

                    }else {
                        financeAaInit1.setCalCodea(map.get("Code").toString());
                        financeAaInit1.setCalIda(map.get("Id").toString());
                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp1.getCalTypeCodeb())) {
                    Map map = interfaceImport(financeAaAcctcodeCorp1.getCalTypeCodeb(), companyId, financeAaInit.getCalNameb(), ConstantsUtil.CALRULE2,financeAaInit.getAcctCode(),"B");
                    if (!StringUtils.isEmpty(map.get("Msg").toString())){
                        msg =msg + map.get("Msg").toString();
                        financeAaInit1.setErrorMessage(msg);

                    }
                    if (StringUtils.isEmpty(map.get("Id").toString())){
                        financeAaInit1.setCalNameb("");

                    }else {
                        financeAaInit1.setCalCodeb(map.get("Code").toString());
                        financeAaInit1.setCalIdb(map.get("Id").toString());
                    }
                }

                if (!StringUtils.isEmpty(financeAaAcctcodeCorp1.getCalTypeCodec())) {
                    Map map = interfaceImport(financeAaAcctcodeCorp1.getCalTypeCodec(), companyId, financeAaInit.getCalNamec(), ConstantsUtil.CALRULE3,financeAaInit.getAcctCode(),"C");
                    if (!StringUtils.isEmpty(map.get("Msg").toString())){
                        msg =msg + map.get("Msg").toString();
                        financeAaInit1.setErrorMessage(msg);

                    }
                    if (StringUtils.isEmpty(map.get("Id").toString())){
                        financeAaInit1.setCalNamec("");

                    }else {
                        financeAaInit1.setCalCodec(map.get("Code").toString());
                        financeAaInit1.setCalIdc(map.get("Id").toString());
                    }
                }
                if (!StringUtils.isEmpty(financeAaAcctcodeCorp1.getCalTypeCoded())) {
                    Map map = interfaceImport(financeAaAcctcodeCorp1.getCalTypeCoded(), companyId, financeAaInit.getCalNamed(), ConstantsUtil.CALRULE4,financeAaInit.getAcctCode(),"D");
                    if (!StringUtils.isEmpty(map.get("Msg").toString())){
                        msg =msg + map.get("Msg").toString();
                        financeAaInit1.setErrorMessage(msg);

                    }
                    if (StringUtils.isEmpty(map.get("Id").toString())){
                        financeAaInit1.setCalNamed("");

                    }else {
                        financeAaInit1.setCalCoded(map.get("Code").toString());
                        financeAaInit1.setCalIdd(map.get("Id").toString());
                    }
                }
                financeAaInitList.add(financeAaInit1);
            } else {
                financeAaInit1.setErrorMessage("当前会计科目编号在会计科目中无对应数据");
                financeAaInitList.add(financeAaInit1);
            }

        }

        for (FinanceAaInit financeAaInit2:financeAaInitList){
            if (!StringUtils.isEmpty(financeAaInit2.getErrorMessage())){
                return financeAaInitList;
            }
        }
        for (FinanceAaInit financeAaInit2:financeAaInitList){
            FinanceAaLedgerlCal financeAaLedgerlCal =new FinanceAaLedgerlCal();
            BeanUtils.copyProperties(financeAaInit2,financeAaLedgerlCal);
            financeAaLedgerlCal.setAcctPeriod(financeAaInit2.getAcctPeriod().substring(0,7));
            FinanceAaLedgerlCal financeAaLedgerlCal1 = financeAaLedgerlCalMapper.selectFinanceAaLedgerlCal(financeAaLedgerlCal);
            financeAaLedgerlCal.setId(IdUtils.fastSimpleUUID());
            financeAaLedgerlCal.setBgnAmt(financeAaInit2.getBgnAmt());
            financeAaLedgerlCal.setBgnQty(financeAaInit2.getBgnQty());
            if (financeAaLedgerlCal1!=null){
                financeAaLedgerlCal1.setBgnAmt(financeAaInit2.getBgnAmt());
                financeAaLedgerlCal1.setBgnQty(financeAaInit2.getBgnQty());
                financeAaLedgerlCalMapper.updateFinanceAaLedgerlCal(financeAaLedgerlCal1);
            }else {
                financeAaLedgerlCalMapper.insertFinanceAaLedgerlCal(financeAaLedgerlCal);
            }
            FinanceAaLedgerAcct financeAaLedgerAcct = new FinanceAaLedgerAcct();
            BeanUtils.copyProperties(financeAaInit2,financeAaLedgerAcct);
            financeAaLedgerAcct.setCompanyId(companyId);
            financeAaLedgerAcct.setAcctPeriod(acctPeriod.substring(0, 7));
            financeAaLedgerAcct.setAcctCode(financeAaInit2.getAcctCode());
            FinanceAaLedgerAcct financeAaLedgerAcct1 = financeAaLedgerAcctMapper.selectFinanceAaLedgerAcct(financeAaLedgerAcct);
            financeAaLedgerAcct.setId(IdUtils.fastSimpleUUID());
            financeAaLedgerAcct.setBgnAmt(financeAaInit2.getBgnAmt());
            financeAaLedgerAcct.setBgnQty(financeAaInit2.getBgnQty());
            if (financeAaLedgerAcct1 != null) {
                financeAaLedgerAcct1.setBgnAmt(financeAaInit2.getBgnAmt());
                financeAaLedgerAcct1.setBgnQty(financeAaInit2.getBgnQty());
                financeAaLedgerAcctMapper.updateFinanceAaLedgerAcct(financeAaLedgerAcct1);
            }else {
                financeAaLedgerAcctMapper.insertFinanceAaLedgerAcct(financeAaLedgerAcct);
            }

        }
        financeAaInitMapper.batchFinanceAaInit(financeAaInitList);
        List<FinanceAaInit> financeAaInitList1= new ArrayList<>();
        return financeAaInitList1;
    }

    public Map interfaceImport(String calTypeCode, String companyId,String CalName,String number,String acctCode,String cal) {
        Map<String, String> map = new HashMap<>(2);
        FinanceCalSysRule financeCalSysRule = new FinanceCalSysRule();
        FinanceCalcode financeCalcode = new FinanceCalcode();
        FinanceCaltype financeCaltype = new FinanceCaltype();
        financeCaltype.setCompanyId(companyId);
        financeCaltype.setCalTypeCode(calTypeCode);
        FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
        if (ConstantsUtil.CALRULE3.equals(financeCaltype1.getCalRule())) {
            if (ConstantsUtil.ISEMPTY.equals(CalName)){
                map.put("Id",number);
                map.put("Code","");
                map.put("Msg", "会计科目"+acctCode+"的核算项目"+cal+"类型为"+calTypeCode+"，不可以为空！");
            }else{
                financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);

                List<Map<String, String>> list1 = financeAaVoucherService.selectInterfaceList(financeCalSysRule1.getSqlStringDb(), "", CalName);
                if (list1.size()==0){
                    map.put("Id",number);
                    map.put("Code","");
                    map.put("Msg", "核算项目"+number+"不存在");
                }else {
                    map.put("Id", list1.get(0).get("Id"));
                    map.put("Code", list1.get(0).get("calNo"));
                    map.put("Msg", "");
                }
            }




        } else if (ConstantsUtil.CALRULE4.equals(financeCaltype1.getCalRule())) {
            financeCalcode.setCompanyId(companyId);
            financeCalcode.setCalTypeCode(calTypeCode);
            financeCalcode.setCalName(CalName);
            List<FinanceCalcode> financeCalcodes = financeCalcodeMapper.selectFinanceCalcodeList(financeCalcode);
            if (financeCalcodes.size()==0) {
                map.put("Id",number);
                map.put("Code","");
                map.put("Msg", "核算项目"+number+"不存在");
            }else {
                map.put("Id", financeCalcodes.get(0).getId());
                map.put("Code", financeCalcodes.get(0).getCalCode());
                map.put("Msg", "");
            }
        } else {
            map.put("Id","");
            map.put("Code","");
            map.put("Msg", "");
        }
        return map;
    }
}
