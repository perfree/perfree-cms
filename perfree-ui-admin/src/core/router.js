import {createRouter, createWebHistory} from "vue-router";
import {CONSTANTS} from "@/core/utils/constants";

// 创建一个路由器实例
const router = createRouter({
  history: createWebHistory(),
  base: "/",
  routes: []
});


// 路由守卫
router.beforeEach((to, from, next) => {
  let userInfo = localStorage.getItem(CONSTANTS.STORAGE_USER_INFO);
  if (userInfo) {
    userInfo = JSON.parse(userInfo);
  }
  // 判断是否登录
  if (!userInfo || !userInfo.token || userInfo.token === '') {
    if (to.path === '/login') {
      next();
      return;
    }
    next('/login');
  }
  else {
    next();
  }
})
export default router;
