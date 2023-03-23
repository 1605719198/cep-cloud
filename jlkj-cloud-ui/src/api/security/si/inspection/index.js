import request from '@/utils/request'
//安全巡检-查询列表
export const safeCheckList = (page, query) => {
  return request({
    url: '/safety/safe/check/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const safeCheckInsert = (params) => {
  return request({
    url: '/safety/safe/check/insert',
    method: 'post',
    data: { ...params }
  })
};
//处理
export const safeCheckDispose = (params) => {
  return request({
    url: '/safety/safe/check/dispose',
    method: 'post',
    data: { ...params }
  })
};

//详情
export const safeCheckInfo = (params) => {
  return request({
    url: '/safety/safe/check/info',
    method: 'get',
    params
  })
};

//删除
export const safeCheckDelete = (ids) => {
  return request({
    url: '/safety/safe/check/delete',
    method: 'post',
    data: ids
  })
};
