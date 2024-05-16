import request from '@/utils/request'

export function login1(data) {
  return request({
    url: '/vue-admin-template/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export const add = (username, role) => {
  return request({
    url: '/user/add',
    method: 'get',
    params: {
      username, role
    }
  })
}

export const list = (data) => {
  return request({
    url: '/user/list',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const batchDelete = (ids) => {
  return request({
    url: '/user/delete/' + ids,
    method: 'delete'
  })
}

export const change = (id, password, role) => {
  return request({
    url: '/user/change',
    method: 'get',
    params: {
      id, password, role
    }
  })
}

export const login = (username, password) => {
  return request({
    url: '/user/login',
    method: 'get',
    params: {
      username, password
    }
  })
}
