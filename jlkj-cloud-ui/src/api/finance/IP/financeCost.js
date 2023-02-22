import request from '@/utils/request';
// 获取全部树节点
export function getTreeCostNode() {
  return request({
    url: '/finance/finance/cost/menu/generateTree',
    method: 'get',
  })
}
// 获取全部树节点
export function getTreeCostNodeAll() {
  return request({
    url: '/finance/finance/cost/menu/getTreeCostNodeAll',
    method: 'get',
  })

}
// 查询户号参号
export function queryDataByParams(accountCode) {
  return request({
    url: `/finance/finance/cost/menu/getListAccountTree/` + accountCode,
    method: 'get'
  })

}
