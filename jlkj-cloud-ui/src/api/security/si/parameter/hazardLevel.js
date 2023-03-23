import request from '@/utils/request'
//隐患等级-查询
export const dangertypeList = (page, query) => {
  return request({
    url: '/safety/safe/dangertype/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const dangertypeInsert = (params) => {
  return request({
    url: '/safety/safe/dangertype/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const dangertypeUpdate = (params) => {
  return request({
    url: '/safety/safe/dangertype/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const dangertypeDelete = (ids) => {
  return request({
    url: '/safety/safe/dangertype/delete',
    method: 'post',
    data: ids
  })
};
