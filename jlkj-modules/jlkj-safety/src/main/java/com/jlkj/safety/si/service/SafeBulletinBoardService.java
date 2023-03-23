package com.jlkj.safety.si.service;

import com.jlkj.safety.si.entity.SafeBulletinBoard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 安全承诺公告牌 服务类
 * </p>
 *
 * @author su
 * @since 2021-05-13
 */
public interface SafeBulletinBoardService extends IService<SafeBulletinBoard> {

    /**
     * 安全承诺公告牌-查询列表
     * @param params 查询条件
     * @return 结果列表
     */
    public Object getSafeBulletinBoardPageList(Map<String, Object> params);

    /**
     * 安全承诺公告牌-新增
     * @param params 请求参数
     * @return 结果
     */
    public Object insertSafeBulletinBoard(Map<String, Object> params);

    /**
     * 安全承诺公告牌-修改
     * @param params 请求参数
     * @return 结果
     */
    public Object updateSafeBulletinBoard(Map<String, Object> params);

    /**
     * 安全承诺公告牌-删除
     * @param params 请求参数
     * @return 结果
     */
    public Object deleteSafeBulletinBoard(Map<String, Object> params);

    /**
     * 安全承诺公告牌-模板
     * @param params 查询条件
     * @return 结果
     */
    public Object getSafeBulletinBoardTemplateInfo(Map<String, Object> params);

}
