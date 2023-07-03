import request from "@/utils/request";

// 新增
export function addInfo(data) {
  return request({
    url: '/energy/energy/code/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/energy/energy/code/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/energy/energy/code/delete/` + ids,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/energy/energy/code/query',
  method: 'get',
  params: data
});


// 修改时查询数据
export function getInfo(id) {
  return request({
    url: `/energy/energy/code/queryById/` + id,
    method: 'get'
  })
}

// 查询能源代码选单
export function queryEngyIds(data) {
  return request({
    url: '/energy/energy/code/queryDropDownMenuZh',
    method: 'get',
    data: data
  })
}
