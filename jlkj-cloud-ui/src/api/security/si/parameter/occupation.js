import request from '@/utils/request'
//工种-查询
export const worktypeList = (page, query) => {
  return request({
    url: '/safety/safe/worktype/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const worktypeInsert = (params) => {
  return request({
    url: '/safety/safe/worktype/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const worktypeUpdate = (params) => {
  return request({
    url: '/safety/safe/worktype/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const worktypeDelete = (ids) => {
  return request({
    url: '/safety/safe/worktype/delete',
    method: 'post',
    data: ids
  })
};
