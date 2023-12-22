<template>
  <div class="main">
    <router-view></router-view>
  </div>
</template>

<script setup>
import { getUser, getMenus } from "./api";
import _import from "./scripts/_import";
import { loadMenuAndModule } from "./utils/module";
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import {CONSTANTS} from "@/core/utils/constants";
const router = useRouter();
const store = useStore();

async function  assembleFrame(info) {
  const frame = await _import("frames", info.name,  info.version);
  frame.routerStatic.forEach( r => {
    // 路由注册
    router.addRoute(r);
  })

  // Store 注册
  for (let name in frame.store) {
    store.registerModule(name, frame.store[name]);
  }
  // 嵌套路由 / 默认两级路由
  return frame.routerStatic.filter(item => item.path === "/");
}

async function init() {
  document.title = PERFREE_CONFIG.appName;
  // 获取数据
  const [user] = await Promise.all([getUser()]);
  store.commit("SET_USER", user);
  // 组装基座并获取基座嵌套路由
  let childRouter = await assembleFrame(user.frame);
  store.commit("SET_FRAME_CHILD_ROUTER", childRouter);

}

init().then(() => {
  let userInfo = localStorage.getItem(CONSTANTS.STORAGE_USER_INFO);
  if (userInfo) {
    userInfo = JSON.parse(userInfo);
  }
  // 判断是否登录
  if (!userInfo || !userInfo.token || userInfo.token === '') {
    router.replace('/login');
  } else {
    loadMenuAndModule(store, router).then(() => {
      router.replace(router.currentRoute.value.path);
    }).catch(err => {
      console.log(err);
    })
  }
}).catch(err => {
  console.log(err);
})
</script>

<style lang="less">
:root{
  --primary-color: var(--el-color-primary);
  --side-background-color: #304156;
}
html,body,.full{
  height: 100%;
}
#app, .main{
  height: 100%;
}
#nprogress .bar {
  background: var( --primary-color) !important; //自定义颜色
}
</style>