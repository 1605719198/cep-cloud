import request from '@/utils/request'

// 查询岗位信息数据维护列表
export function listPostMaintenance(query) {
  return request({
    url: '/human/postMaintenance/list',
    method: 'get',
    params: query
  })
}

// 查询岗位信息数据维护列表历史
export function listPostVersion(query) {
  return request({
    url: '/human/postMaintenance/getHistory',
    method: 'get',
    params: query
  })
}

// 查询岗位信息数据维护详细
export function getPostMaintenance(postId) {
  return request({
    url: '/human/postMaintenance/' + postId,
    method: 'get'
  })
}

// 新增岗位信息数据维护
export function addPostMaintenance(data) {
  return request({
    url: '/human/postMaintenance',
    method: 'post',
    data: data
  })
}

// 修改岗位信息数据维护
export function updatePostMaintenance(data) {
  return request({
    url: '/human/postMaintenance',
    method: 'put',
    data: data
  })
}

// 删除岗位信息数据维护
export function delPostMaintenance(postId) {
  return request({
    url: '/human/postMaintenance/' + postId,
    method: 'delete'
  })
}
