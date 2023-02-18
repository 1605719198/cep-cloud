import request from '@/utils/request'

// 新增停机原因数据
export function addMgmc2Data(data) {
  return request({
    url: '/equip/equipment/mgmc2/addMgmc2Data',
    method: 'post',
    data: data
  })
}
// 修改停机原因数据
export function updateMgmc2Data(data) {
  return request({
    url: '/equip/equipment/mgmc2/updateMgmc2Data',
    method: 'put',
    data: data
  })
}
// 删除停机原因数据
export function deleteMgmc2Data(ids) {
  return request({
    url: '/equip/equipment/mgmc2/deleteMgmc2Data/'+ids,
    method: 'delete',
  })
}
// 分页查询停机原因
export function queryDataByParams(params) {
  return request({
    url: '/equip/equipment/mgmc2/queryDataByParams',
    method: 'get',
    params: params
  })
}
//根据ID查询数据
export function getData(typeId) {
  return request({
    url: '/equip/equipment/mgmc2/getByDataId/' + typeId,
    method: 'get'
  })
}
