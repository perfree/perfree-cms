import request from '@/utils/request'

// 查询订单信息列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息详细
export function getOrder(orderCode) {
  return request({
    url: '/system/order/' + orderCode,
    method: 'get'
  })
}

// 新增订单信息
export function addOrder(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateOrder(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delOrder(orderCode) {
  return request({
    url: '/system/order/' + orderCode,
    method: 'delete'
  })
}
