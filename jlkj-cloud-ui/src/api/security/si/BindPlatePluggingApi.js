/**
 * 安环通用接口
 */
import request from '@/utils/request';

//获取模板列表
export const approvalTemplateList = () => {
  return request({
    url:'/safety/safe/job/blindplateplugging/approval/template/list',
    method:'get'
  });
}

//获取附件列表
export const blindPlatePluggingFileList = (id) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/file/list?id=' + id,
    method:'get'
  });
}

//获取证确认
export  const blindPlatePluggingConfirmApprovalTime = (id) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/confirm/approval/time',
    method:'post',
    data:{...id}
  });
}

//修改作业票
export const blindPlatePluggingUpdate = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/update',
    method:'post',
    data:{...params}
  });
}

//详情
export const blindPlatePluggingInfo = (id) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/info?id=' + id,
    method:'get'
  });
}

//获取作业人员列表
export const blindPlatePluggingPersonList = (id) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/person/list?id=' + id,
    method:'get'
  });
}

//设置审批人
export const blindPlatePluggingUpdateApproval = (params) => {
  return request({
    url:'/safety/safe/job/blindplateplugging/update/approval',
    method:'post',
    data:{...params}
  });
}
