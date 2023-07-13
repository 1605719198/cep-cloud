import request from '@/utils/request'

// 查询题目属性列表
export function listQuestionsproperty(query) {
  return request({
    url: '/human/questionsproperty/list',
    method: 'get',
    params: query
  })
}

// 查询题目属性详细
export function getQuestionsproperty(questionsCode) {
  return request({
    url: '/human/questionsproperty/' + questionsCode,
    method: 'get'
  })
}

// 新增题目属性
export function addQuestionsproperty(data) {
  return request({
    url: '/human/questionsproperty/add',
    method: 'post',
    data: data
  })
}

// 修改题目属性
export function updateQuestionsproperty(data) {
  return request({
    url: '/human/questionsproperty/update',
    method: 'post',
    data: data
  })
}

// 删除题目属性
export function delQuestionsproperty(questionsCode) {
  return request({
    url: '/human/questionsproperty/delete/' + questionsCode,
    method: 'get'
  })
}

// 导出题目属性
export function exportQuestionsproperty(query) {
  return request({
    url: '/human/questionsproperty/export',
    method: 'get',
    params: query
  })
}
