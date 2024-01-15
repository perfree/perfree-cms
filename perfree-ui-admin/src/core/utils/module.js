import _import from "@/core/scripts/_import";
import {getAllRouter} from "@/core/scripts/utils";
import {menuList} from "@/frames/default/api/system";
import {ElMessage} from "element-plus";

export async function loadMenuAndModule(store, router) {
    const [ menus] = await Promise.all([menuList().then((res) => {
        if (res.code === 200) {
            return res.data;
        } else {
            ElMessage.error(res.msg);
            return null;
        }
    })]);
    store.commit("SET_MENUS", menus);
    // 组装模块
    await assemblePerfree(menus, store.getters.frameChildRouter, store, router);
}

async function assemblePerfree(menus, childRouter, store, router) {
    let pages = childRouter[0].children;
    let allRouter = [];
    getAllRouter(menus, allRouter);
    // 按module分类数据
    const categorizedData = allRouter.reduce((acc, item) => {
        if (!acc[item.module]) {
            acc[item.module] = [];
        }
        acc[item.module].push(item);
        return acc;
    }, {});
    // 收集所有 _import 函数返回的 Promise 对象
    let importPromises = [];

    for (const module in categorizedData) {
        if (categorizedData.hasOwnProperty(module)) {
            // 将每个 _import 函数返回的 Promise 存入数组
            importPromises.push(
                _import("modules", module,  Date.now())
                    .then(p => {
                        // 路由
                        childRouter[0].children = p
                            .router(categorizedData[module], module)
                            .concat(p.routerStatic);
                        pages.push(...childRouter[0].children);
                        for (let childrenKey in childRouter[0].children) {
                            router.addRoute("frame", childRouter[0].children[childrenKey]);
                        }
                        // Store
                        for (let name in p.store) {
                            store.registerModule(name, p.store[name]);
                        }
                    })
                    .catch(err => {
                        // 错误处理
                    })
            );
        }
    }

    // 等待所有 Promise 执行完毕
    await Promise.all(importPromises);
    // 储存路由表
    store.commit("SET_PAGES", pages);
}