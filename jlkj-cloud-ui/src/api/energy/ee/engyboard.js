import request from "@/utils/request";

//焦炭产量记录查询
export const getEngyTopAnalys = (params) => {
  return request({
    url: '/energy/dashboard/getEngyTopAnalys',
    method: 'get',
    params
  })
};
