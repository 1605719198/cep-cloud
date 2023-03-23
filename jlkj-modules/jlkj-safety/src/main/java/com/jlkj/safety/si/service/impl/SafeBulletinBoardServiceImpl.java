package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeBulletinBoard;
import com.jlkj.safety.si.mapper.SafeBulletinBoardMapper;
import com.jlkj.safety.si.service.SafeBulletinBoardService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.ID;

/**
 * <p>
 * 安全承诺公告牌 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-05-13
 */
@Service
public class SafeBulletinBoardServiceImpl extends ServiceImpl<SafeBulletinBoardMapper, SafeBulletinBoard> implements SafeBulletinBoardService {

    @Resource
    private SafeBulletinBoardMapper safeBulletinBoardMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeBulletinBoardPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeBulletinBoardMapper.getSafeBulletinBoardPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeBulletinBoard(Map<String, Object> params) {
        SafeBulletinBoard safeBulletinBoard = new SafeBulletinBoard();
        safeBulletinBoard.setId(IdUtil.randomUUID());
        safeBulletinBoard.setTitle(params.get("title").toString());
        safeBulletinBoard.setTeamRiskAssessment(params.get("team_risk_assessment").toString());
        safeBulletinBoard.setTeamCommitment(params.get("team_commitment").toString());
        safeBulletinBoard.setPrincipalResponsiblePerson(params.get("principal_responsible_person").toString());
        safeBulletinBoard.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        safeBulletinBoard.setCreateUserName(params.get("create_user_name").toString());
        if (save(safeBulletinBoard)) {
            return ResponseUtil.toResult(params, "安全承诺公告牌保存成功");
        } else {
            return ResponseUtil.toError(params, "安全承诺公告牌保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeBulletinBoard(Map<String, Object> params) {
        SafeBulletinBoard safeBulletinBoard = getById(params.get("id").toString());
        if (null != safeBulletinBoard) {
            safeBulletinBoard.setTitle(params.get("title").toString());
            safeBulletinBoard.setTeamRiskAssessment(params.get("team_risk_assessment").toString());
            safeBulletinBoard.setTeamCommitment(params.get("team_commitment").toString());
            safeBulletinBoard.setPrincipalResponsiblePerson(params.get("principal_responsible_person").toString());
            if (updateById(safeBulletinBoard)) {
                return ResponseUtil.toResult(params, "安全承诺公告牌保存成功");
            } else {
                return ResponseUtil.toError(params, "安全承诺公告牌保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "安全承诺公告牌记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeBulletinBoard(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            return ResponseUtil.toResult(params, "安全承诺公告牌删除成功");
        }
        else {
            return ResponseUtil.toError(params, "安全承诺公告牌删除失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeBulletinBoardTemplateInfo(Map<String, Object> params) {
        Map<String, Object> data = safeBulletinBoardMapper.getSafeBulletinBoardTemplateInfo(params);
        if (null == data) {
            return ResponseUtil.toError(params, "模板数据不存在");
        }
        else {
            return ResponseUtil.toResult(params, data);
        }
    }
}
