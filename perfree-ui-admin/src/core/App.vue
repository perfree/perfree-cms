<template>
  <div class="main">
    <router-view></router-view>
  </div>
</template>

<script setup>
import { getUser, getMenus } from "./api";
import _import from "./scripts/_import";
import { handleMenus } from "./scripts/utils";
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
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

async function assemblePuzzles(menus, childRouter) {
  let pages = childRouter[0].children;
  // 尝试获取模块 / 异步获取
  const promises = menus.map(menu => {
    if (!menu.module || menu.module === '') {
      return;
    }
    return _import("modules", menu.module,  menu.version)
        .then(p => {
          // 需要生成路由的菜单
          let menusRouter = [];
          handleMenus(menu, menusRouter);
          // 路由
          childRouter[0].children = p
              .router(menusRouter, menu.module)
              .concat(p.routerStatic);
          pages.push(...childRouter[0].children);
          for (let childrenKey in childRouter[0].children) {
            router.addRoute("frame",childRouter[0].children[childrenKey]);
          }
          // Store
          for (let name in p.store) {
            store.registerModule(name, p.store[name]);
          }
        })
        .catch(err => {
          // 错误处理
        });
  });

  // 等待所有的_import方法执行完毕
  await Promise.all(promises);

  // 储存路由表
  store.commit("SET_PAGES", pages);
}

async function init() {
  document.title = PERFREE_CONFIG.appName;
  // 获取数据
  const [user, menus] = await Promise.all([getUser(), getMenus()]);
  store.commit("SET_USER", user);
  store.commit("SET_MENUS", menus);
  // 组装基座并获取基座嵌套路由
  let childRouter = await assembleFrame(user.frame);
  // 组装模块
  await assemblePuzzles(menus, childRouter);
}

init().then(() => {
  router.replace(router.currentRoute.value.path);
}).catch(err => {
  console.log(err);
})
</script>

<style lang="less">
html,body,.full{
  height: 100%;
}
#app, .main{
  height: 100%;
}
</style>