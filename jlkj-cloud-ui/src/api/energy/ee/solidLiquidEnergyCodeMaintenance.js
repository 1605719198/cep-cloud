import request from "@/utils/request";

// 新增
export function addInfo(data) {
  return request({
    url: '/energy/energy/code/addSolid',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/energy/energy/code/updateSolid',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/energy/energy/code/deleteSolid/` + ids,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/energy/energy/code/querySolid',
  method: 'get',
  params: data
});

// 查询固液体能源代码选单
export function queryEngyIds(data) {
  return request({
    url: '/energy/energy/code/querySolidLiquidDropDownMenu',
    method: 'get',
    data: data
  })
}
