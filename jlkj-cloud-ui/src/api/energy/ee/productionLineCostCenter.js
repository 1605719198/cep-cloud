import request from "@/utils/request";

// 新增
export function addInfo(data) {
  return request({
    url: '/energy/energy/mill/cost/add',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/energy/energy/mill/cost/delete?id=${ids}`,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/energy/energy/mill/cost/query',
  method: 'get',
  params: data
});
// 查询选单 绑定数据
export const querySelectInfo = () => request({
  url: '/energy/energy/mill/cost/selectsZh',
  method: 'get'
});
// 根据条件查询信息
export function listApplyLike (data) {
  return request({
    url: '/api/neplatform/fin/finance/cost/costCenter/queryLike',
    method: 'get',
    params: data
  })
}
