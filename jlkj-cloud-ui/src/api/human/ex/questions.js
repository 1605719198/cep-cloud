import request from '@/utils/request'

// 查询试题列表
export function listQuestions(query) {
  return request({
    url: '/human/questions/list',
    method: 'get',
    params: query
  })
}

// 查询试题详细
export function getQuestions(id) {
  return request({
    url: '/human/questions/' + id,
    method: 'get'
  })
}

// 新增试题
export function addQuestions(data) {
  return request({
    url: '/human/questions',
    method: 'post',
    data: data
  })
}

// 修改试题
export function updateQuestions(data) {
  return request({
    url: '/human/questions',
    method: 'put',
    data: data
  })
}

// 删除试题
export function delQuestions(id) {
  return request({
    url: '/human/questions/' + id,
    method: 'delete'
  })
}
