import {getMenus} from "@/core/api";
import _import from "@/core/scripts/_import";
import {handleMenus} from "@/core/scripts/utils";

export async function loadMenuAndModule(store, router) {
    const [ menus] = await Promise.all([getMenus()]);
    store.commit("SET_MENUS", menus);
    // 组装模块
    await assemblePuzzles(menus, store.getters.frameChildRouter, store, router);
}

async function assemblePuzzles(menus, childRouter, store, router) {
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