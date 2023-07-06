import request from '@/utils/request'

// 查询排班记录列表
export function listArrangeClassMaster(query) {
  return request({
    url: '/human/arrangeClassMaster/list',
    method: 'get',
    params: query
  })
}

// 查询排班记录详细
export function getArrangeClassMaster(id) {
  return request({
    url: '/human/arrangeClassMaster/' + id,
    method: 'get'
  })
}

// 新增排班记录
export function addArrangeClassMaster(data) {
  return request({
    url: '/human/arrangeClassMaster',
    method: 'post',
    data: data
  })
}


// 删除排班记录
export function delArrangeClassMaster(id) {
  return request({
    url: '/human/arrangeClassMaster/' + id,
    method: 'delete'
  })
}
