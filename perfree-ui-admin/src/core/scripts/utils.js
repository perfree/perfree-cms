// 获取模块/路由
export function getAllRouter(menus, result) {
  for(let item of menus) {
    if (item.children && item.children.length > 0){
      getAllRouter(item.children, result)
    } else {
      result.push(item);
    }
  }
}
