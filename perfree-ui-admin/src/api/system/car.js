import request from '@/utils/request'

// 查询车辆信息列表
export function listCar(query) {
  return request({
    url: '/system/car/list',
    method: 'get',
    params: query
  })
}

// 查询车辆信息详细
export function getCar(carId) {
  return request({
    url: '/system/car/' + carId,
    method: 'get'
  })
}

// 新增车辆信息
export function addCar(data) {
  return request({
    url: '/system/car',
    method: 'post',
    data: data
  })
}

// 修改车辆信息
export function updateCar(data) {
  return request({
    url: '/system/car',
    method: 'put',
    data: data
  })
}

// 删除车辆信息
export function delCar(carId) {
  return request({
    url: '/system/car/' + carId,
    method: 'delete'
  })
}

export function getAll() {
  return request({
    url: '/system/car/getAll',
    method: 'get'
  })
}
