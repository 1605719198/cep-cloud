import request from '@/utils/request'

// 查询职位名称数据维护列表
export function listJobTitle(query) {
  return request({
    url: '/human/jobTitle/list',
    method: 'get',
    params: query
  })
}


// 查询职位名称数据idname
export function listJobTitleidname(compId) {
  return request({
    url: '/human/jobTitle/getIdname',
    method: 'get',
    params: compId
  })
}

// 查询职位名称数据维护详细
export function getJobTitle(id) {
  return request({
    url: '/human/jobTitle/' + id,
    method: 'get'
  })
}

// 新增职位名称数据维护
export function addJobTitle(data) {
  return request({
    url: '/human/jobTitle',
    method: 'post',
    data: data
  })
}

// 修改职位名称数据维护
export function updateJobTitle(data) {
  return request({
    url: '/human/jobTitle',
    method: 'put',
    data: data
  })
}

// 删除职位名称数据维护
export function delJobTitle(id) {
  return request({
    url: '/human/jobTitle/' + id,
    method: 'delete'
  })
}
