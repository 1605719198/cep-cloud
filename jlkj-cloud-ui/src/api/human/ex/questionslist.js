import request from '@/utils/request'

// 查询题目属性列表
export function listQuestions(query) {
  return request({
    url: '/human/questionslist/list',
    method: 'get',
    params: query
  })
}

export function chiceListQuestions(examCode) {
  return request({
    url: '/human/questionslist/selectlist/' + examCode,
    method: 'get'
  })
}

