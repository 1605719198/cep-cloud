import request from '@/utils/request'

// 查询招聘需求列表
export function listRecruitingNeeds(query) {
  return request({
    url: '/human/recruitingNeeds/list',
    method: 'get',
    params: query
  })
}

// 查询招聘需求详细
export function getRecruitingNeeds(uuid) {
  return request({
    url: '/human/recruitingNeeds/' + uuid,
    method: 'get'
  })
}

// 新增招聘需求
export function addRecruitingNeeds(data) {
  return request({
    url: '/human/recruitingNeeds',
    method: 'post',
    data: data
  })
}

// 修改招聘需求
export function updateRecruitingNeeds(data) {
  return request({
    url: '/human/recruitingNeeds',
    method: 'put',
    data: data
  })
}

// 删除招聘需求
export function delRecruitingNeeds(uuid) {
  return request({
    url: '/human/recruitingNeeds/' + uuid,
    method: 'delete'
  })
}
