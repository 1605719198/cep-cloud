package com.jlkj.finance.aa.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceCalSysRule;
import com.jlkj.finance.aa.domain.FinanceCaltype;
import com.jlkj.finance.aa.mapper.FinanceCalSysRuleMapper;
import com.jlkj.finance.aa.mapper.FinanceCaltypeMapper;
import com.jlkj.finance.aa.service.IFinanceCaltypeService;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 核算项目-类别维护Service业务层处理
 *
 * @author 116514
 * @date 2023-04-06
 */
@Service
public class FinanceCaltypeServiceImpl implements IFinanceCaltypeService
{
    @Autowired
    private FinanceCaltypeMapper financeCaltypeMapper;
    @Autowired
    private FinanceCalSysRuleMapper financeCalSysRuleMapper;

    /**
     * 查询核算项目-类别维护
     *
     * @param id 核算项目-类别维护主键
     * @return 核算项目-类别维护
     */
    @Override
    public FinanceCaltype selectFinanceCaltypeById(String id)
    {
        return financeCaltypeMapper.selectFinanceCaltypeById(id);
    }

    /**
     * 查询核算项目-类别维护列表
     *
     * @param financeCaltype 核算项目-类别维护
     * @return 核算项目-类别维护
     */
    @Override
    public List<FinanceCaltype> selectFinanceCaltypeList(FinanceCaltype financeCaltype)
    {
        return financeCaltypeMapper.selectFinanceCaltypeList(financeCaltype);
    }

    /**
     * 新增核算项目-类别维护
     *
     * @param financeCaltype 核算项目-类别维护
     * @return 结果
     */
    @Override
    public int insertFinanceCaltype(FinanceCaltype financeCaltype)
    {
        financeCaltype.setId(UUID.randomUUID().toString());
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCaltype.setCreateBy(user.getUserName());
        financeCaltype.setCreateName(user.getNickName());
        financeCaltype.setCreateTime(DateUtils.getNowDate());
        return financeCaltypeMapper.insertFinanceCaltype(financeCaltype);
    }

    /**
     * 修改核算项目-类别维护
     *
     * @param financeCaltype 核算项目-类别维护
     * @return 结果
     */
    @Override
    public int updateFinanceCaltype(FinanceCaltype financeCaltype)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        financeCaltype.setUpdateBy(user.getUserName());
        financeCaltype.setUpdateName(user.getNickName());
        financeCaltype.setUpdateTime(DateUtils.getNowDate());
        return financeCaltypeMapper.updateFinanceCaltype(financeCaltype);
    }

    /**
     * 批量删除核算项目-类别维护
     *
     * @param ids 需要删除的核算项目-类别维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCaltypeByIds(String[] ids)
    {
        return financeCaltypeMapper.deleteFinanceCaltypeByIds(ids);
    }

    /**
     * 删除核算项目-类别维护信息
     *
     * @param id 核算项目-类别维护主键
     * @return 结果
     */
    @Override
    public int deleteFinanceCaltypeById(String id)
    {
        return financeCaltypeMapper.deleteFinanceCaltypeById(id);
    }

    /**
     * 查询核算项目类别(下拉选单用)
     * @return
     */
    @Override
    public List<Map<String,String>> selectCalTypeList() { return financeCaltypeMapper.selectCalTypeList(); }
    /**
     * 查询核算项目类别(下拉选单用)
     * @param financeCaltype 核算项目-类别维护
     * @return
     */
    @Override
    public List<Map<String,String>> selectCalTypeSystemList(FinanceCaltype financeCaltype) {
        List<Map<String, String>> maps = new ArrayList<>();
        Map map = new HashMap();
        FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
        if (financeCaltype1!=null){
            String calRule3 = "03";
            if (calRule3.equals(financeCaltype1.getCalRule())){
                FinanceCalSysRule financeCalSysRule= new FinanceCalSysRule();
                financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                maps = financeCalSysRuleMapper.selectFinanceCalSysRuleMapList(financeCalSysRule);
            }else {
                map.put("calRule", "04");
                maps.add(map);
            }
        }
        return maps;
    }

    @Override
    public List<JSONObject> selectFinanceAaBaseListTab(FinanceCaltype financeCaltype) {
        FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
        List<JSONObject> financeAaBasesString = new ArrayList<>();
        if (financeCaltype1!=null){
            String calRule3 = "03";
            if (calRule3.equals(financeCaltype1.getCalRule())){
                FinanceCalSysRule financeCalSysRule= new FinanceCalSysRule();
                financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                financeCalSysRule.setSys(financeCaltype.getSys());
                FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                String str1;
                String[] split =financeCalSysRule1.getSqlString().split(",");
                for (int i = 0;i<split.length;i++){
                    if (i == 0){
                        str1= split[0].substring( split[0].indexOf("SELECT") + 7,  split[0].lastIndexOf("AS"));
                        split[0] =  split[0].replaceAll("\\s*","").replaceAll("[^(\\u4e00-\\u9fa5)]","");
                        JSONObject rtJson = new JSONObject();
                        rtJson.put("name", split[0]);
                        rtJson.put("nameEn",str1.replace(" ",""));
                        rtJson.put("value","");
                        financeAaBasesString.add(rtJson);
                        }else {
                         str1 = split[i].substring(0,  split[i].indexOf("AS"));
                        split[i] =  split[i].replaceAll("\\s*","").replaceAll("[^(\\u4e00-\\u9fa5)]","");
                        JSONObject rtJson = new JSONObject();
                        rtJson.put("name", split[i]);
                        rtJson.put("nameEn",str1.replace(" ",""));
                        rtJson.put("value","");
                        financeAaBasesString.add(rtJson);
                    }
                }
            }else {
                financeAaBasesString = new ArrayList<>();
            }
        }
        return financeAaBasesString;
    }

    @Override
    public    List<Map<String, String>> selectFinanceAaBaseListTable(FinanceCaltype financeCaltype) {
        FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
        List<Map<String, String>> maps= new ArrayList<>();
        if (financeCaltype1!=null) {
            String calRule3 = "03";
            if (calRule3.equals(financeCaltype1.getCalRule())) {
                FinanceCalSysRule financeCalSysRule = new FinanceCalSysRule();
                financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                financeCalSysRule.setSys(financeCaltype.getSys());
                FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                maps = financeCalSysRuleMapper.selectFinanceSqlMapList(financeCalSysRule1.getSqlStringDb());
            }
        }
                return maps;
    }

  @Override
    public List<Map<String, String>> selectFinanceAaBaseListLikeTable1(JSONObject financeCaltypeLike) {
        String financeCaltypes = financeCaltypeLike.get("key").toString().replaceAll("([A-Z])", "_$1").toLowerCase();
        FinanceCaltype financeCaltype = new FinanceCaltype();
        financeCaltype.setCompanyId(financeCaltypeLike.get("companyId").toString());
        financeCaltype.setCalTypeCode(financeCaltypeLike.get("calTypeCode").toString());
        List<Map<String, String>> list= new ArrayList<>();
        FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
        StringBuffer sql = new StringBuffer();
        if (financeCaltype1!=null) {
            String calRule3 = "03";
            StringBuffer s = new StringBuffer();
            String substring;
            String substring1;
            if (calRule3.equals(financeCaltype1.getCalRule())) {
                FinanceCalSysRule financeCalSysRule = new FinanceCalSysRule();
                financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                financeCalSysRule.setSys(financeCaltypeLike.get("sys").toString());
                FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                String[] split =financeCaltypes.split(",");
                for (int i1 = 0;i1<split.length;i1++){
                    if (i1 == 0){
                         substring = split[i1].split("=")[0].substring(split[i1].split("=")[0].indexOf('{') + 1);
                    }else {
                        substring = split[i1].split("=")[0];
                    }
                    if (i1 == split.length-1){
                        substring1 = split[i1].split("=")[1].substring(0,split[i1].split("=")[1].indexOf('}'));
                    }else {
                        substring1 = split[i1].split("=")[1];
                    }
                    if ("null".equals(substring1)){
                        substring1="";
                    }
                    if (i1 == 0){
                        s =  s.append(" WHERE " + substring + " LIKE " + "'%" +substring1 + "%'" );
                    }else {
                        s =  s.append(" and " + substring + " LIKE " + "'%" +substring1 + "%'");
                    }
                }
                sql.append(s);
                list = financeCalSysRuleMapper.selectFinanceSqlMapList(financeCalSysRule1.getSqlStringDb()+sql);
            }
        }
        return list;
    }
    @Override
    public List<Map<String, String>> selectFinanceAaBaseListLikeTable(JSONObject financeCaltypeLike) {

        FinanceCaltype financeCaltype = new FinanceCaltype();
        financeCaltype.setCompanyId(financeCaltypeLike.get("companyId").toString());
        financeCaltype.setCalTypeCode(financeCaltypeLike.get("calTypeCode").toString());
        List<Map<String, String>> list= new ArrayList<>();
        String nullId="null";
        FinanceCaltype financeCaltype1 = financeCaltypeMapper.selectCalTypeSystemList(financeCaltype);
        StringBuffer sql = new StringBuffer();
        if (financeCaltype1!=null) {
            String calRule3 = "03";
            String s ;
            String s1 = "";
            String s2 = "";
            if (calRule3.equals(financeCaltype1.getCalRule())) {
                FinanceCalSysRule financeCalSysRule = new FinanceCalSysRule();
                financeCalSysRule.setCompanyId(financeCaltype1.getCompanyId());
                financeCalSysRule.setCalTypeCode(financeCaltype1.getCalTypeCode());
                financeCalSysRule.setSys(financeCaltypeLike.get("sys").toString());
                FinanceCalSysRule financeCalSysRule1 = financeCalSysRuleMapper.selectFinanceCalSysRule(financeCalSysRule);
                String[] sqlStringDb =financeCalSysRule1.getSqlStringDb().split(",");
                for (int i = 0;i<sqlStringDb.length;i++){
                    if (i == 0){
                        s= sqlStringDb[0].substring( sqlStringDb[0].indexOf("SELECT") + 7,  sqlStringDb[0].lastIndexOf("AS"));

                    }else if (i == 1){
                        s = sqlStringDb[i].substring(0,  sqlStringDb[i].indexOf("AS"));
                        if (nullId.equals(financeCaltypeLike.get("calNo").toString())){
                            s1 =  " WHERE " + s + " LIKE " + "'%" + ""+ "%'";
                        }else {
                            s1 =  " WHERE " + s + " LIKE " + "'%" +financeCaltypeLike.get("calNo") + "%'";
                        }

                    }else {
                        s = sqlStringDb[i].substring(0,  sqlStringDb[i].indexOf("AS"));
                        if (nullId.equals(financeCaltypeLike.get("calName").toString())){
                            s2 = " and " + s  + " LIKE " + "'%" +"" + "%'";
                        }else {
                            s2 = " and " + s  + " LIKE " + "'%" +financeCaltypeLike.get("calName") + "%'";
                        }
                    }
                }
                sql.append(s1+s2);
                list = financeCalSysRuleMapper.selectFinanceSqlMapList(financeCalSysRule1.getSqlStringDb()+sql);
            }
        }
        return list;
    }
}
