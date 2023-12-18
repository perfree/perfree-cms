<template>
  <div>
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
  const frame = await _import("frames", info.name, info.host, info.version);
  console.log(frame)
  frame.routerStatic.forEach( r => {
    // 路由注册
    router.addRoute(r);
  })

  // Store 注册
  for (let name in frame.store) {
    store.registerModule(name, frame.store[name]);
  }
  // 嵌套路由 / 默认两级路由
  return frame.routerStatic.filter(item => item.path == "/");
}

/*async function assemblePuzzles(menus, childRouter) {
  let pages = childRouter[0].children;
  // 尝试获取模块 / 异步获取
  for (let puzzle of menus)
    _import("modules", puzzle.id, puzzle.host, puzzle.version)
        .then(p => {
          // 需要生成路由的菜单
          let menusRouter = [];
          handleMenus(puzzle.children, menusRouter);
          // 路由
          childRouter[0].children = p
              .router(menusRouter, puzzle.id)
              .concat(p.routerStatic);
          pages.push(...childRouter[0].children);
          console.log(childRouter)
          //router.addRoute(childRouter)
          for (let childrenKey in childRouter[0].children) {
            router.addRoute("111",childRouter[0].children[childrenKey]);
          }
          // Store
          for (let name in p.store)
            store.registerModule(name, p.store[name]);
        })
        .catch(err => {});
  // 储存路由表
  store.commit("SET_PAGES", pages);
}*/

async function assemblePuzzles(menus, childRouter) {
  let pages = childRouter[0].children;
  // 尝试获取模块 / 异步获取
  const promises = menus.map(menu => {
    console.log(menu)
    return _import("modules", menu.id, menu.host, menu.version)
        .then(p => {
          console.log(p);
          // 需要生成路由的菜单
          let menusRouter = [];
          handleMenus(menu.children, menusRouter);
          // 路由
          childRouter[0].children = p
              .router(menusRouter, menu.id)
              .concat(p.routerStatic);
          pages.push(...childRouter[0].children);
          for (let childrenKey in childRouter[0].children) {
            router.addRoute("111",childRouter[0].children[childrenKey]);
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
  console.log(111);
}

init().then(() => {
  console.log(router.getRoutes());
  router.replace(router.currentRoute.value.path);
}).catch(err => {
  console.log(err);
})
</script>