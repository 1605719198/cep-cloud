import request from '@/utils/request';
// 获取全部树节点
export function getTreeAccountNode() {
  return request({
    url: '/finance/accountCode/generateTree',
    method: 'get',
  })
}
// 获取全部树节点
export function getTreeAccountNodeAll() {
  return request({
    url: '/finance/accountCode/generateAllTree',
    method: 'get',
  })
}
