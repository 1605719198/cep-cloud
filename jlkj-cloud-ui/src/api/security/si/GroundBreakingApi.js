/**
 * 动土作业票接口
 */
import request from '@/utils/request';

const API_PREFIX = '/safety';
// 审批列表
export const soilApprovalList = (id) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/approval/list?id=' + id,
    method:'get'
  });
}

//审批模板列表
export const soilApprovalTemplateList = () => {
  return request({
    url:API_PREFIX + '/safe/job/soil/approval/template/list',
    method:'get'
  });
}

//安全措施确认
export const soilConfirmSafetymeasures = (params) => {
  return request({
    url:API_PREFIX+'/safe/job/soil/confirm/safetymeasures',
    method:'post',
    data:{...params}
  });
}

//安全措施取消
export const soilCancelSafetymeasures = (param) => {
  return request({
    url:API_PREFIX+'/safe/job/soil/cancel/safetymeasures',
    method:'post',
    data:{...param}
  });
}
//作业票编号
export const soilCode = () => {
  return request({
    url:API_PREFIX+'/safe/job/soil/code',
    method:'get'
  });
}
//作业票审批
export const soilConfirmApproval = (params) => {
  return request({
    url:API_PREFIX+'/safe/job/soil/confirm/approval',
    method:'post',
    data:{...params}
  });
}

//作业票审批确认
export const soilConfirmApprovalTime = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/confirm/approval/time',
    method:'post',
    data:{...params}
  });
}

//作业票删除
export const soilDelete = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/delete',
    method:'post',
    data:{...params}
  });
}

//删除作业人员
export const soilDeletePerson = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/delete/person',
    method:'post',
    data:{...params}
  });
}

//作业证详情
export const soilInfo = (id) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/info?id=' + id,
    method:'get'
  });
}

//作业证新增
export const soilInsert = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/insert',
    method:'post',
    data:{...params}
  });
}

//新增作业人员
export const soilInsertWorkPerson = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/insert/person',
    method:'post',
    data:{...params}
  });
}

//查询作业票列表
export const soilList = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/list',
    method:'get',
    params
  });
}

//作业人员列表
export const soilPersonList = (id) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/person/list?id='+ id,
    method:'get',
  });
}

//安全措施列表
export const soilSafetymeasuresList = (id) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/safetymeasures/list?id=' + id,
    method:'get'
  });
}

//作业票修改
export const soilUpdate = (params) => {
  return request({
    url:API_PREFIX + '/safe/job/soil/update',
    method:'post',
    data:{...params}
  });
}

//设置审批人
export const soilUpdateApproval = (params) => {
  return request({
    url:API_PREFIX+'/safe/job/soil/update/approval',
    method:'post',
    data:{...params}
  });
}

//附件列表
export const soilFileList = (id) => {
  return request({
    url:API_PREFIX+'/safe/job/soil/file/list?id=' + id ,
    method:'get'
  });
}
