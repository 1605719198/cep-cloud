import request from '@/utils/request';

// 查询设备缺陷
export const queryInfo = (params) => request({
  url: '/equip/equipment/defect/query',
  method: 'get',
  params:{
    ...params
  }
});
// 新增
export function addInfo(data) {
  return request({
    url: '/equip/equipment/defect/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/equip/equipment/defect/updata',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/equip/equipment/defect/delete?ids=${ids}`,
    method: 'delete'
  })
}
// 根据id查询数据修改
export function queryInfoById(id) {
  return request({
    url: `/equip/equipment/defect/queryInfoById`,
    method: 'get',
    params: {
      id: id
    }
  })
}

