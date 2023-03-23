import request from '@/utils/request'
//气防站应急救援物资-查询列表
export const emergencyreliefmaterialsList = (page, query) => {
  return request({
    url: '/safety/safe/emergencyreliefmaterials/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const emergencyreliefmaterialsInsert = (params) => {
  return request({
    url: '/safety/safe/emergencyreliefmaterials/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const emergencyreliefmaterialsUpdate = (params) => {
  return request({
    url: '/safety/safe/emergencyreliefmaterials/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const emergencyreliefmaterialsDelete = (ids) => {
  return request({
    url: '/safety/safe/emergencyreliefmaterials/delete',
    method: 'post',
    data: ids
  })
};
