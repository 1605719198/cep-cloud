import request from '@/utils/request'
//安全巡检类型-查询
export const checktypelist = (page, query) => {
  return request({
    url: '/safety/safe/checktype/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const checktypeInsert = (params) => {
  return request({
    url: '/safety/safe/checktype/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const checktypeUpdate = (params) => {
  return request({
    url: '/safety/safe/checktype/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const checktypeDelete = (ids) => {
  return request({
    url: '/safety/safe/checktype/delete',
    method: 'post',
    data: ids
  })
};
