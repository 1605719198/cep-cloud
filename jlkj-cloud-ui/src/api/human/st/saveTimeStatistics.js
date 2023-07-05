import request from '@/utils/request'

// 查询存班统计列表
export function listSaveTimeStatistics(query) {
  return request({
    url: '/human/saveTimeStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询存班统计详细
export function getSaveTimeStatistics(id) {
  return request({
    url: '/human/saveTimeStatistics/' + id,
    method: 'get'
  })
}

// 新增存班统计
export function addSaveTimeStatistics(data) {
  return request({
    url: '/human/saveTimeStatistics',
    method: 'post',
    data: data
  })
}

// 修改存班统计
export function updateSaveTimeStatistics(data) {
  return request({
    url: '/human/saveTimeStatistics',
    method: 'put',
    data: data
  })
}

// 删除存班统计
export function delSaveTimeStatistics(id) {
  return request({
    url: '/human/saveTimeStatistics/' + id,
    method: 'delete'
  })
}
