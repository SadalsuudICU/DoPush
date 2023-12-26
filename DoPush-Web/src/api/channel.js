import request from '@/utils/request'

export const list = (data) => {
  return request({
    url: '/account/list',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const query = (data) => {
  return request({
    url: '/account/queryByChannelType',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const save = (data) => {
  return request({
    url: '/account/save',
    method: 'post',
    data
  })
}

