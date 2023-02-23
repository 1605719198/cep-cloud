import request from "@/utils/request";

// 新增
export function addInfo(data) {
  return request({
    url: '/energy/energy/code/material/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/energy/energy/code/material/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/energy/energy/code/material/delete?id=${ids}`,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/energy/energy/code/material/query',
  method: 'get',
  params:data
});


// 修改时查询数据
export function getInfo(id) {
  return request({
    url: `/energy/energy/code/material/queryById?id=${id}`,
    method: 'get'
  })
}

// 查询固液体能源代码选单
export function querySolidEngyIds(data) {
  return request({
    url: '/energy/energy/code/querySolidLiquidDropDownMenu',
    method: 'get',
    data: data
  })
}
