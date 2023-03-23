import request from '@/utils/request';
const API_PREFIX = '/safety';

function makeUrl(url){
  return API_PREFIX + url ;
}

//Post请求
function makePostRequest(url, params){
  return {
    url:makeUrl(url),
    method:'post',
    data:{...params}
  }
}

//Get请求 单个参数
function makeGetRequest(url, pathParamKey, pathParamValue){
  let mUrl = '' ;
  if (pathParamKey !== undefined && pathParamValue !== undefined){
    mUrl = url + "?" + pathParamKey + "=" + pathParamValue
  }else {
    mUrl = url ;
  }
  return {
    url:makeUrl(mUrl),
    method:'get'
  }
}

//Get请求 多个参数
function makeGetRequestMultiple(url, params){
  return {
    url:makeUrl(url),
    method:'get',
    params
  }
}

//获取分析列表
export const spaceAnalysisList = (id) => {
  return request({
    url:makeUrl('/safe/job/space/analysis/list?id=' + id),
    method:'get'
  });
}

//获取审批列表
export const spaceApprovalList = (id) => {
  return request({
    url:makeUrl('/safe/job/space/approval/list?id=' + id),
    method:'get'
  });
}

//获取审批模板列表
export const spaceApprovalTemplateList = () => {
  return request({
    url:makeUrl('/safe/job/space/approval/template/list'),
    method:'get'
  });
}

//安全措施取消
export const spaceCancelSsfetymeasures = (params) => {
  return request({
    url:makeUrl('/safe/job/space/cancel/safetymeasures'),
    method:'post',
    data:{...params}
  });
}

//安全措施确认
export const spaceConfirmSafetymeasures = (params) => {
  return request(makePostRequest('/safe/job/space/confirm/safetymeasures', params));
}

//获取作业票编号
export const spaceCode = () => {
  return request({
    url:makeUrl('/safe/job/space/code'),
    method:'get'
  });
}

//作业票审批
export const spaceConfirmApproval = (params) => {
  return request(makePostRequest('/safe/job/space/confirm/approval', params));
}

//作业票审批确认
export const spaceConfirmApprovalTime = (params) => {
  return request(makePostRequest('/safe/job/space/confirm/approval/time' , params));
}

//作业证删除
export const spaceDelete = (params) => {
 return request(makePostRequest('/safe/job/space/delete',params));
}

//删除分析
export const spaceDeleteAnalysis = (params) => {
  return request(makePostRequest('/safe/job/space/delete/analysis', params));
}

//附件列表
export const spaceFileList = (id) => {
  return request(makeGetRequest('/safe/job/space/file/list', 'id', id));
}

//获取作业票详情
export const spaceInfo = (id) => {
  return request(makeGetRequest('/safe/job/space/info', 'id', id));
}

//作业票新增
export const spaceInsert = (params) => {
  return request(makePostRequest('/safe/job/space/insert', params));
}

//新增作业分析
export const spaceInsertAnalysis = (params) => {
  return request(makePostRequest('/safe/job/space/insert/analysis', params));
}

//查询列表
export const spaceList = (params) => {
  return request(makeGetRequestMultiple('/safe/job/space/list', params));
}

//安全措施列表
export const spaceSafetymeasuresList = (id) => {
  return request(makeGetRequest('/safe/job/space/safetymeasures/list', 'id', id));
}

//作业票修改
export const spaceUpdate = (params) => {
  return request(makePostRequest('/safe/job/space/update', params));
}

//设置审批人
export const spaceUpdateApproval = (params) => {
  return request(makePostRequest('/safe/job/space/update/approval', params));
}

//获取作业人列表
export const spacePersonList = (id) => {
  return request(makeGetRequest('/safe/job/space/person/list', 'id' ,id));
}

