// 处理菜单
export function handleMenus(menus, result) {
  if (menus.children && menus.children.length > 0){
    for (let item of menus.children) {
      if (item.children && item.children.length > 0){
        handleMenus(item.children, result)
      } else {
        result.push(item);
      }
    }
  } else {
    result.push(menus);
  }
}
