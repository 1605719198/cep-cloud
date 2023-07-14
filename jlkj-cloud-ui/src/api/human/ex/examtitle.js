import request from '@/utils/request'

// 取得考试的标题信息
export function getTitle(userCode) {
    return request({
      url: '/human/onlineexam/examtitle/' + userCode,
      method: 'get'
    })
  }
