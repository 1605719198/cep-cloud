import request from '@/utils/request'


// 新增加班汇总（薪酬计算用）
export function addOverTime(data) {
  return request({
    url: '/human/overTime',
    method: 'post',
    data: data
  })
}

// 修改加班汇总（薪酬计算用）
export function updateOverTime(data) {
  return request({
    url: '/human/overTime',
    method: 'put',
    data: data
  })
}

// 删除加班汇总（薪酬计算用）
export function delOverTime(id) {
  return request({
    url: '/human/overTime/' + id,
    method: 'delete'
  })
}
