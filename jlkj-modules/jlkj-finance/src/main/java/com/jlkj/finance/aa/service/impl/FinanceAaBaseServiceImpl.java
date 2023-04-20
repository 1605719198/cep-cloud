package com.jlkj.finance.aa.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.finance.aa.mapper.FinanceAaBaseMapper;
import com.jlkj.finance.aa.domain.FinanceAaBase;
import com.jlkj.finance.aa.service.IFinanceAaBaseService;

/**
 * 系统选单-基本设定Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-19
 */
@Service
public class FinanceAaBaseServiceImpl implements IFinanceAaBaseService
{
    @Autowired
    private FinanceAaBaseMapper financeAaBaseMapper;

    /**
     * 查询系统选单-基本设定
     *
     * @param id 系统选单-基本设定主键
     * @return 系统选单-基本设定
     */
    @Override
    public FinanceAaBase selectFinanceAaBaseById(String id)
    {
        return financeAaBaseMapper.selectFinanceAaBaseById(id);
    }

    /**
     * 查询系统选单-基本设定列表
     *
     * @param financeAaBase 系统选单-基本设定
     * @return 系统选单-基本设定
     */
    @Override
    public List<FinanceAaBase> selectFinanceAaBaseList(FinanceAaBase financeAaBase)
    {
        List<FinanceAaBase> financeAaBases = financeAaBaseMapper.selectFinanceAaBaseList(financeAaBase);
        for (FinanceAaBase financeAaBase1:financeAaBases){

        }
        return financeAaBaseMapper.selectFinanceAaBaseList(financeAaBase);
    }
    /**
     * 查询系统选单-基本设定列表
     *
     * @param financeAaBase 系统选单-基本设定
     * @return 系统选单-基本设定
     */
    @Override
    public   List<JSONObject> selectFinanceAaBaseListTab(FinanceAaBase financeAaBase)
    {
        List<FinanceAaBase> financeAaBases = financeAaBaseMapper.selectFinanceAaBaseListTab(financeAaBase);
        List<JSONObject> financeAaBasesString = new ArrayList<>();

        for (FinanceAaBase financeAaBase1:financeAaBases){
            if (financeAaBase1.getDataa()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDataa());
                rtJson.put("nameEn","dataa");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);

            } if (financeAaBase1.getDatab()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDatab());
                rtJson.put("nameEn","datab");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);

            } if (financeAaBase1.getDatac()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDatac());
                rtJson.put("nameEn","datac");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            } if (financeAaBase1.getDatad()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDatad());
                rtJson.put("nameEn","datad");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            }  if (financeAaBase1.getDatae()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDatae());
                rtJson.put("nameEn","datae");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            } if (financeAaBase1.getDataf()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDataf());
                rtJson.put("nameEn","dataf");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            } if (financeAaBase1.getDatag()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getDatag());
                rtJson.put("nameEn","datag");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            } if (financeAaBase1.getItema()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getItema());
                rtJson.put("nameEn","itema");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            } if (financeAaBase1.getItemb()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getItemb());
                rtJson.put("nameEn","itemb");
                rtJson.put("value"," ");
                financeAaBasesString.add(rtJson);
            }
             if (financeAaBase1.getItemc()!=null){
                 JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getItemc());
                rtJson.put("nameEn","itemc");
                 rtJson.put("value","");
                 financeAaBasesString.add(rtJson);
            } if (financeAaBase1.getItemd()!=null){
                JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getItemd());
                rtJson.put("nameEn","itemd");
                rtJson.put("value","");
                financeAaBasesString.add(rtJson);
            }
             if (financeAaBase1.getIteme()!=null){
                 JSONObject rtJson = new JSONObject();
                rtJson.put("name",financeAaBase1.getIteme());
                rtJson.put("nameEn","iteme");
                 rtJson.put("value","");
                 financeAaBasesString.add(rtJson);
            }


        }
        return  financeAaBasesString;
    }


    /**
     * 新增系统选单-基本设定
     *
     * @param financeAaBase 系统选单-基本设定
     * @return 结果
     */
    @Override
    public int insertFinanceAaBase(FinanceAaBase financeAaBase)
    {
        financeAaBase.setCreateTime(DateUtils.getNowDate());
        financeAaBase.setId(UUID.fastUUID().toString());
        return financeAaBaseMapper.insertFinanceAaBase(financeAaBase);
    }

    /**
     * 修改系统选单-基本设定
     *
     * @param financeAaBase 系统选单-基本设定
     * @return 结果
     */
    @Override
    public int updateFinanceAaBase(FinanceAaBase financeAaBase)
    {
        financeAaBase.setUpdateTime(DateUtils.getNowDate());
        return financeAaBaseMapper.updateFinanceAaBase(financeAaBase);
    }

    /**
     * 批量删除系统选单-基本设定
     *
     * @param leafId 需要删除的系统选单-基本设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaBaseByIds(String[] leafId)
    {
        return financeAaBaseMapper.deleteFinanceAaBaseByIds(leafId);
    }

    /**
     * 删除系统选单-基本设定信息
     *
     * @param id 系统选单-基本设定主键
     * @return 结果
     */
    @Override
    public int deleteFinanceAaBaseById(String id)
    {
        return financeAaBaseMapper.deleteFinanceAaBaseById(id);
    }
}
