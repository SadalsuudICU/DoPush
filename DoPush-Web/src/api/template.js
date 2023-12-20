import request from '@/utils/request'

export const templateList = (data) => {
  return request({
    url: '/messageTemplate/list',
    method: 'get',
    params: {
      ...data
    }
  })
}
