import request from '@/utils/request'

// 查询各公司最低工资标准及生活保障标准设定列表
export function listMiniStandard(query) {
  return request({
    url: '/human/miniStandard/list',
    method: 'get',
    params: query
  })
}

// 查询各公司最低工资标准及生活保障标准设定详细
export function getMiniStandard(uuid) {
  return request({
    url: '/human/miniStandard/' + uuid,
    method: 'get'
  })
}

// 新增各公司最低工资标准及生活保障标准设定
export function addMiniStandard(data) {
  return request({
    url: '/human/miniStandard/save',
    method: 'post',
    data: data
  })
}

// 修改各公司最低工资标准及生活保障标准设定
export function updateMiniStandard(data) {
  return request({
    url: '/human/miniStandard',
    method: 'put',
    data: data
  })
}

// 删除各公司最低工资标准及生活保障标准设定
export function delMiniStandard(uuid) {
  return request({
    url: '/human/miniStandard/' + uuid,
    method: 'delete'
  })
}

//获取公司列表
export function selectVersion(compId) {
  return request({
    url: '/human/miniStandard/selectVersion/'+compId,
    method: 'get',
  })
}
