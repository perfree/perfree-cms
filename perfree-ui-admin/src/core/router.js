import {createWebHistory, createRouter, createWebHashHistory} from "vue-router";


// 创建一个路由器实例
const router = createRouter({
  history: createWebHistory(),
  base: "/",
  routes: []
});

export default router;
