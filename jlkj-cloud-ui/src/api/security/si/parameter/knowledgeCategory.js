import request from '@/utils/request'
//知识类别-查询
export const knowledgecategoryList = (page, query) => {
  return request({
    url: '/safety/safe/knowledgecategory/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const knowledgecategoryInsert = (params) => {
  return request({
    url: '/safety/safe/knowledgecategory/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const knowledgecategoryUpdate = (params) => {
  return request({
    url: '/safety/safe/knowledgecategory/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const knowledgecategoryDelete = (ids) => {
  return request({
    url: '/safety/safe/knowledgecategory/delete',
    method: 'post',
    data: ids
  })
};
