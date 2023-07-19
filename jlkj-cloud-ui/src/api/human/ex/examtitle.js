import request from '@/utils/request'

// 取得考试的标题信息
export function getTitle(userCode) {
debugger
    return request({
      url: '/human/onlineexam/' + userCode,
      method: 'get'
    })
  }
