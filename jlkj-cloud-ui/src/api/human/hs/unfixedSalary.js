import request from '@/utils/request'

// 查询未定薪人员挑选作业列表
export function listUnfixedSalary(data) {
  return request({
    url: '/human/unfixedSalary/list',
    method: 'post',
    data: data
  })
}


// 新增未定薪人员挑选作业
export function saveUnfixedSalary(data) {
  return request({
    url: '/human/unfixedSalary',
    method: 'post',
    data: data
  })
}

