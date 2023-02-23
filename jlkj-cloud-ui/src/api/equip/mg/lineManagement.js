import request from '@/utils/request'

// 查询产线
export const queryInfo = (params) => request({
  url: '/equip/mgma1/baseLine/query',
  method: 'get',
  params:{
    pageNum:params.page,
    pageSize:params.limit,
    mgma1no:params.mgma1no,
    mgma1name:params.mgma1name,

  }
});
// 新增产线
export function addInfo(data) {
  return request({
    url: '/equip/mgma1/baseLine/insert',
    method: 'post',
    data: data
  })
}

// 修改产线
export function updateInfo(data) {
  return request({
    url: '/equip/mgma1/baseLine/updata',
    method: 'post',
    data: data
  })
}

// 删除产线、批量删除
export function delInfo(ids) {
  return request({
    url: `/equip/mgma1/baseLine/delete?ids=${ids}`,
    method: 'delete',
  })
}
// 检验产线编号是否存在
export function checkno(mgma1no,id) {
  return request({
    url: `/equip/mgma1/baseLine/checkno?mgma1no=${mgma1no}&id=${id}` ,
    method: 'get',

  })
}
// 校验产线名称是否存在
export function checkname(name,id) {
  return request({
    url: `/equip/mgma1/baseLine/checkname?mgma1name=${name}&id=${id}` ,
    method: 'get',
  })
}
// 查询停机群码
export function querygropcode(name) {
  return request({
    url: '/equip/mgma1/baseLine/querygroupcod' ,
    method: 'get',
    params:{
      mgma1name:name,
      }
  })
}
// 查询立案资料产线编号是否使用
export function queryLineEdit(mgma1no) {
  return request({
    url: `/equip/equipment/mgms1/getLineEditForm`,
    method: 'get',
    params: {
      mgma1no
    }
  })
}



