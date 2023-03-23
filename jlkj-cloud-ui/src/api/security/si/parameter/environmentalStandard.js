import request from '@/utils/request'
//(page ,query) == (params)

//列表
export const EList = (page, query) => {
  return request({
    url: '/safety/safe/safeIndex/list',
    method: 'get',
    params: {
      ...page,
      ...query,//查
    }
  })
};

//获取列表的params写法
// export const EList = (params) => {
//   return request({
//     url: '/safety/safe/safeIndex/list',
//     method: 'get',
//     params: { ...params }
//   })
// };

//新增
export const EInsert = (params) => {
  return request({
    url: '/safety/safe/safeIndex/save',
    method: 'post',
    data: { ...params } //json格式
  })
};
//修改
export const EUpdate = (params) => {
  return request({
    url: '/safety/safe/safeIndex/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const EDelete = (ids) => {
  return request({
    url: '/safety/safe/safeIndex/delete',
    method: 'post',
    data: ids
  })
};
