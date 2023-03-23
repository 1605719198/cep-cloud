/**
 * 安环通用接口
 */
import request from '@/utils/request'

/**
 * 获取安全区域Api
 */
export const getSafetyArea = () => {
  return request({
    url: '/safety/common/combo/safe/area',
    method: 'get',
  });
}

/**
 * 获取施工单位树
 */
export const getConstructionUnit = ()=>{
  return request({
    url:'/safety/common/tree/safe/constructionunit',
    method:'get'
  });
}

/**
 * 获取施工人员树
 */
export const getConstructorTree = () => {
  return request({
    url: '/safety/common/tree/safe/constructionunit/person',
    method: 'get'
  });
}

/**
 * 获取施工人员列表，获取施工人得单位、作业证、等信息
 * id 施工人得id
 */
export const getConstructorInfo = (id) => {
  return request({
    url: '/safety/common/list/safe/constructionunit/person/list?id_list=' + id,
    method: 'get',
  });
}

/**
 * 吊装作业票Api
 */

//获取吊装作业票列表
export const getHoistingList = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/list',
    method: 'get',
    params
  });
}

//吊装作业票--- 获取作业票详情
export const hoistingInfo = (id) => {
  return request({
    url:'/safety/safe/job/hoisting/info?id=' + id ,
    method:'get'
  });
}


//获取吊装作业票编号
export const getHoistingWorkTicketCode = () => {
  console.log("调用了api")
  return request({
    url: '/safety/safe/job/hoisting/code',
    method: 'get'
  });
}

//新增吊装作业证
export const insertHoistingWorkTicket = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/insert',
    method: 'post',
    data: { ...params }
  });
}

//作业证审批
export const hoistingConfirmApproval = (params) => {
  return request({
    url: "/safety/safe/job/hoisting/confirm/approval",
    method: "post",
    data:{...params}
  });
}

//获取吊装的安全措施列表
export const hoistingSafetymeasuresList = (id) => {
  return request({
    url: "/safety/safe/job/hoisting/safetymeasures/list?id=" + id,
    method: "get"
  });
}
//吊装作业票安全措施---确认
export const hoistingConfirmSafetymeasures = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/confirm/safetymeasures',
    method: 'post',
    data: { ...params }
  });
}

//吊装作业票安全措施---取消确认
export const hoistingCancelSafetymeasures = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/cancel/safetymeasures',
    method: 'post',
    data: { ...params }
  });
}

//吊装作业票---确认审批时间
export const hoistingConfirmApprovalTime = (params) => {
  return request({
    url:'/safety/safe/job/hoisting/confirm/approval/time',
    method:'post',
    data:{...params}
  });
}

//施工单位树
export const getconstructionunitTree = (params) => {
  return request({
    url: '/safety/common/tree/safe/constructionunit',
    method: 'get',
    params
  });
}
//工种下拉
export const getWorktype = (params) => {
  return request({
    url: '/safety/common/combo/safe/worktype',
    method: 'get',
    params
  });
}

//知识类别树
export const getknowledgecategoryTree = (params) => {
  return request({
    url: '/safety/common/tree/safe/knowledgecategory',
    method: 'get',
    params
  });
}

//获取审批人列表
export const hoistingApprovalList = (id) => {
  return request({
    url: "/safety/safe/job/hoisting/approval/list?id=" + id,
    method: "get"
  });
}

//获取作业人列表
export const hoistingPersonList = (id) => {
  return request({
    url: "/safety/safe/job/hoisting/person/list?id=" + id,
    method: "get"
  });
}

//获取附件列表
export const hoistingFileList = (id) => {
  return request({
    url: "/safety/safe/job/hoisting/file/list?id=" + id,
    method: "get"
  });
}

//修改吊装审批人
export const updateHoistingApproval = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/update/approval',
    method: 'post',
    data: { ...params }
  });
}

//删除作业票
export const hoistingDelete = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/delete',
    method: 'post',
    data: { ...params }
  });
}

//吊装作业票---修改
export const updateHoistingWorkTicket = (params) => {
  return request({
    url: '/safety/safe/job/hoisting/update',
    method: 'post',
    data: { ...params }
  });
}

//吊装作业票---获取审批人模板
export const hoistingApprovalTemplateList = (level) => {
  return request({
    url:'/safety/safe/job/hoisting/approval/template/list?job_level=' + level,
    method:'get'
  });
}

//吊装--设置审批人
export const hoistingUpdateApproval = (params) => {
  return request({
    url:'/safety/safe/job/hoisting/update/approval',
    method:'post',
    data:{...params}
  });
}

//工段下拉列表
export const workshopsection = (params) => {
  return request({
    url: '/safety/common/combo/safe/workshopsection',
    method: 'get',
    params
  });
}


/*******************************************start抽堵盲板作业票start************************************************************************/
//抽堵盲板作业票---获取作业票列表
export const blindplatepluggingList = (params)=>{
  return request({
    url:'/safety/safe/job/blindplateplugging/list',
    method:'get',
    params
  });
}

//抽堵盲板---获取作业证票号
export const blindPlatePluggingCode = () => {
  return request({
    url:'/safety/safe/job/blindplateplugging/code',
    method:'get'
  });
}

//抽堵盲板---保存作业票
export const insertBlindPlatePluggingTicket = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/insert',
    method:'post',
    data: { ...params }
  });
}

//抽堵盲板---获取安全措施列表
export const getBlindplatepluggingSafetymeasuresList = (id) =>{
  return request({
    url:'/safety/safe/job/blindplateplugging/safetymeasures/list?id='+ id,
    method:'get'
  });
}

//抽堵盲板---安全措施确认
export const blindplatepluggingConfirmSafetyMeasures = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/confirm/safetymeasures',
    method:'post',
    data:{...params}
  });
}

//抽堵盲板---安全措施取消
export const blindplatepluggingCancelSafetyMeasures = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/cancel/safetymeasures',
    method:'post',
    data:{...params}
  });
}

//抽堵盲板---获取审批人列表
export const blindingPlatePluggingApprovalList = (id)=>{
  return request({
    url:'/safety/safe/job/blindplateplugging/approval/list?id=' + id ,
    method:'get',
  });
}

//抽堵盲板---作业票审批
export const blindingPlatePluggingConfirmApproval = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/confirm/approval',
    method:'post',
    data:{...params}
  });
}

//抽堵盲板---更新审批人
export const blindingPlatePluggingUpdateApproval = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/update/approval',
    method:'post',
    data:{...params}
  });
}

//抽堵盲板---删除作业票
export const deleteBlindingPlatePlugging = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/delete',
    method:'post',
    data:{...params}
  });
}






/********************************************end抽堵盲板作业票end************************************************************************/

//安全隐患等级
export const dangertype = (params) => {
  return request({
    url: '/safety/common/combo/safe/dangertype',
    method: 'get',
    params
  });
}

//安全巡检类型
export const checktype = (params) => {
  return request({
    url: '/safety/common/combo/safe/checktype',
    method: 'get',
    params
  });
}


//动火作业票--- 获取作业票详情
export const hotWorkInfoApi = (id) => {
  return request({
    url:'/safety/safe/job/fire/info?id=' + id ,
    method:'get'
  });
}

//断路作业票--- 获取作业票详情
export const roadBreakingInfoApi = (id) => {
  return request({
    url:'/safety/safe/job/cutroad/info?id=' + id ,
    method:'get'
  });
}

//高处作业票--- 获取作业票详情
export const heightInfoApi = (id) => {
  return request({
    url:'/safety/safe/job/high/info?id=' + id ,
    method:'get'
  });
}
//临时用电作业票--- 获取作业票详情
export const tempElectricityInfoApi = (id) => {
  return request({
    url:'/safety/safe/job/temporaryelectricity/info?id=' + id ,
    method:'get'
  });
}

