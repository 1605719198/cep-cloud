import request from '@/utils/request'
//施工人员-查询
export const constructionunitpersonsList = (page, query) => {
  return request({
    url: '/safety/safe/constructionunitpersons/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const constructionunitpersonsInsert = (params) => {
  return request({
    url: '/safety/safe/constructionunitpersons/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const constructionunitpersonsUpdate = (params) => {
  return request({
    url: '/safety/safe/constructionunitpersons/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const constructionunitpersonsDelete = (ids) => {
  return request({
    url: '/safety/safe/constructionunitpersons/delete',
    method: 'post',
    data: ids
  })
};
