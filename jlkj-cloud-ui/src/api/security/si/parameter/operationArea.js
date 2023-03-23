import request from '@/utils/request'
//安全作业区域-查询
export const arealist = (page, query) => {
  return request({
    url: '/safety/safe/area/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const safeInsert = (params) => {
  return request({
    url: '/safety/safe/area/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const areaUpdate = (params) => {
  return request({
    url: '/safety/safe/area/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const areaDelete = (ids) => {
  return request({
    url: '/safety/safe/area/delete',
    method: 'post',
    data: ids
  })
};
