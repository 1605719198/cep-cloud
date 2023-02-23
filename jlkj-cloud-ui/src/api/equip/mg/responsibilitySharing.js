import request from '@/utils/request'

// 查询停机立案详情
export const querydetails = (params) => request({
  url: '/equip/equipment/mgmsa/queryquerydetails',
  method: 'get',
  params:{
    ...params
  }
});
//查询停机细类型详情
export const querymgmc1no = (params) => request({
  url: `/equip/equipment/mgmsa/querymgmc1no?mgmc1No=${params}`,
  method: 'get',

});
//查询停机原因编号
export const querymgmc2no = (params) => request({
  url: `/equip/equipment/mgmsa/querymgmc2no?mgmc2No=${params}`,
  method: 'get',

});
// 查询分摊记录
export const queryInfo = (params) => request({
  url: '/equip/equipment/mgmsa/query',
  method: 'get',
  params:{
    ...params
  }
});
// 新增
export function addInfo(data) {
  return request({
    url: '/equip/equipment/mgmsa/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/equip/equipment/mgmsa/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/equip/equipment/mgmsa/delete?ids=${ids}`,
    method: 'delete'
  })
}
// 查询分摊单条记录
export const queryone = (id) => request({
  url: `/equip/equipment/mgmsa/queryone?id=${id}`,
  method: 'get',
});
// 重复数据验证
export function repeatVal(params) {
  return request({
    url: `/equip/equipment/mgmsa/repeatVal`,
    method: 'get',
    params: {...params}
  })
}
