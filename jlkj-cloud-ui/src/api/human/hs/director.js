import request from '@/utils/request'

// 查询各公司人事业务负责人列表
export function listDirector(query) {
  return request({
    url: '/human/director/list',
    method: 'get',
    params: query
  })
}

// 新增各公司人事业务负责人
export function addDirector(data) {
  return request({
    url: '/human/director',
    method: 'post',
    data: data
  })
}

// 删除各公司人事业务负责人
export function delDirector(uuid) {
  return request({
    url: '/human/director/' + uuid,
    method: 'delete'
  })
}
