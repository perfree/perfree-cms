import request from '@/utils/request'

// 查询客户信息列表
export function listClientUser(query) {
  return request({
    url: '/system/clientUser/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息详细
export function getClientUser(clientId) {
  return request({
    url: '/system/clientUser/' + clientId,
    method: 'get'
  })
}

// 新增客户信息
export function addClientUser(data) {
  return request({
    url: '/system/clientUser',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateClientUser(data) {
  return request({
    url: '/system/clientUser',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delClientUser(clientId) {
  return request({
    url: '/system/clientUser/' + clientId,
    method: 'delete'
  })
}


export function getAllClientUser() {
  return request({
    url: '/system/clientUser/getAll',
    method: 'get'
  })
}