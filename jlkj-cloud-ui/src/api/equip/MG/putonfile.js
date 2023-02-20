import request from '@/utils/request'
// 查询停机立案
// export const queryInfo = (params) => request({
//   url: '/api/neplatform/epm/equipment/mgms1/query',
//   method: 'get',
//   params:{
//     ...params,
//     datetimeRange:params.datetimeRange?params.datetimeRange.join(','):'',
//   }
// });
export function queryInfo(params) {
  return request({
    url: '/equip/equipment/mgms1/query',
    method: 'get',
    params: params
  })
}
//手动立案
export function addInfo(data) {
  return request({
    url: '/equip/equipment/mgms1/insert',
    method: 'post',
    data: data
  })
}
//编辑手动立案
export function updateInfo(data) {
  return request({
    url: '/equip/equipment/mgms1/update',
    method: 'put',
    data: data
  })
}
//查询下拉表单
export function initSelect() {
  return request({
    url: '/equip/equipment/mgms1/init',
    method: 'get',
  })
}
// 查询编辑手动立案
export function queryEditForm(id) {
  return request({
    url: `/equip/equipment/mgms1/queryEditForm/${id}`,
    method: 'get',
  })
}
// 根据停机主类型编码查询 细类型select‘
export function getChildrenMgmc1Name(optionValue) {
  return request({
    url: `/equip/equipment/mgms1/getChildrenMgmc1Name/${optionValue}`,
    method: 'get',
  })
}

// 根据停机细类型编码查询 停机原因select‘
export function getChildrenReasonNo(optionValue) {
  return request({
    url: `/equip/equipment/mgms1/getChildrenReasonNo/${optionValue}`,
    method: 'get',
  })
}
// 保存重启立案资料
export function updateEditForm(data) {
  return request({
    url: `/equip/equipment/mgms1/updateEditForm`,
    method: 'put',
    data: data
  })
}

