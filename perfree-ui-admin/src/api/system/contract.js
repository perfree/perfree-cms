import request from '@/utils/request'

// 查询合同信息列表
export function listContract(query) {
  return request({
    url: '/system/contract/list',
    method: 'get',
    params: query
  })
}

// 查询合同信息详细
export function getContract(contractID) {
  return request({
    url: '/system/contract/' + contractID,
    method: 'get'
  })
}

// 新增合同信息
export function addContract(data) {
  return request({
    url: '/system/contract',
    method: 'post',
    data: data
  })
}

// 修改合同信息
export function updateContract(data) {
  return request({
    url: '/system/contract',
    method: 'put',
    data: data
  })
}

// 删除合同信息
export function delContract(contractID) {
  return request({
    url: '/system/contract/' + contractID,
    method: 'delete'
  })
}
