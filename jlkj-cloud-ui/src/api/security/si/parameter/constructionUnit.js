import request from '@/utils/request'
//施工单位-查询
export const constructionunitList = (page, query) => {
  return request({
    url: '/safety/safe/constructionunit/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const constructionunitInsert = (params) => {
  return request({
    url: '/safety/safe/constructionunit/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const constructionunitUpdate = (params) => {
  return request({
    url: '/safety/safe/constructionunit/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const constructionunitDelete = (ids) => {
  return request({
    url: '/safety/safe/constructionunit/delete',
    method: 'post',
    data: ids
  })
};
