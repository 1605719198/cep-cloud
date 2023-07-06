import request from '@/utils/request'

// 查询折旧方法维护列表
export function listDeprMethod(query) {
  return request({
    url: '/finance/deprMethod/list',
    method: 'get',
    params: query
  })
}

// 查询折旧方法维护详细
export function getDeprMethod(id) {
  return request({
    url: '/finance/deprMethod/' + id,
    method: 'get'
  })
}

// 新增折旧方法维护
export function addDeprMethod(data) {
  return request({
    url: '/finance/deprMethod',
    method: 'post',
    data: data
  })
}

// 修改折旧方法维护
export function updateDeprMethod(data) {
  return request({
    url: '/finance/deprMethod',
    method: 'put',
    data: data
  })
}

// 删除折旧方法维护
export function delDeprMethod(id) {
  return request({
    url: '/finance/deprMethod/' + id,
    method: 'delete'
  })
}

// 查询折旧方法(下拉选单用)
export function selectDeprMethodList() {
  return request({
    url: '/finance/deprMethod/deprMethodList',
    method: 'get',
  })
}




