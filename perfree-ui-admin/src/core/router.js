import {createRouter, createWebHistory} from "vue-router";
import {CONSTANTS} from "@/core/utils/constants";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

// 创建一个路由器实例
const router = createRouter({
  history: createWebHistory(),
  base: "/",
  routes: []
});


NProgress.configure({
  easing: 'ease',  // 动画方式
  speed: 500,  // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3 // 初始化时的最小百分比
})

// 路由守卫
router.beforeEach((to, from, next) => {
  NProgress.start();
  let token_info = localStorage.getItem(CONSTANTS.STORAGE_TOKEN);
  if (token_info) {
    token_info = JSON.parse(token_info);
  }
  // 判断是否登录
  if (!token_info || !token_info.accessToken || token_info.accessToken === '') {
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

router.afterEach(() => {
  // 关闭进度条
  NProgress.done()
})
export default router;
