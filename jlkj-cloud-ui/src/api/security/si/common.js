import request from '@/utils/request'
//生产
//--------------------通用API接口--------------------//
//接口查询
export const req = (method, url, data) => {
  let req = {url: '/safety/safe/job/' + url, method: method};
  if (method === 'get')
    req.params = {...data};
  else
    req.data = {...data};
  return request(req);
};

