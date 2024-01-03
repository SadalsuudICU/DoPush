import request from '@/utils/request'

export const user = (receiver) => {
  return request({
    url: '/trace/user',
    method: 'post',
    data: {
      receiver
    }
  })
}

export const message = (messageId) => {
  return request({
    url: '/trace/message',
    method: 'post',
    data: {
      messageId
    }
  })
}

export const messageTemplate = (businessId) => {
  return request({
    url: '/trace/messageTemplate',
    method: 'post',
    data: {
      businessId
    }
  })
}

export const sms = (data) => {
  return request({
    url: '/trace/sms',
    method: 'post',
    data: {
      receiver: data.receiver,
      dateTime: data.dateTime
    }
  })
}
