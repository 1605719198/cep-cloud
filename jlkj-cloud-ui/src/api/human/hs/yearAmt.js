import request from '@/utils/request'

// 查询年收入维护列表
export function listYearAmt(query) {
  return request({
    url: '/human/yearAmt/list',
    method: 'get',
    params: query
  })
}

// 保存年收入维护
export function saveYearAmt(data) {
  return request({
    url: '/human/yearAmt/save',
    method: 'post',
    data: data
  })
}



// 新增年收入维护
export function addYearAmt(data) {
  return request({
    url: '/human/yearAmt',
    method: 'post',
    data: data
  })
}


// 修改年收入维护
export function updateYearAmt(data) {
  return request({
    url: '/human/yearAmt',
    method: 'put',
    data: data
  })
}

// 删除年收入维护
export function delYearAmt(id) {
  return request({
    url: '/human/yearAmt/' + id,
    method: 'delete'
  })
}
