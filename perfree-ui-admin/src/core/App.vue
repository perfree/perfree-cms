<template>
  <div class="main">
    <router-view></router-view>
  </div>
</template>

<script setup>
import _import from "./scripts/_import";
import {loadMenuAndModule} from "./utils/module";
import {useRouter} from 'vue-router';
import {useStore} from 'vuex';
import {CONSTANTS} from "@/core/utils/constants";
import {getOptionByNoAuth} from "@/core/api/system";
import {getOptionByKey, OPTION_KEY} from "@/core/utils/option";
import {getAllOption} from "@/frames/default/api/system";

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
  let frame;
  getOptionByNoAuth().then(async (res) => {
    let options = {};
    res.data.forEach(item => {
      options[item.key] = item;
    })
    store.commit("SET_OPTIONS", options);
    frame = {
      name: getOptionByKey(OPTION_KEY.DEFAULT_ADMIN_FRAME).value,
      version: "1.0.0"
    }
    let childRouter = await assembleFrame(frame);
    store.commit("SET_FRAME_CHILD_ROUTER", childRouter);
  });
  // 获取数据
  const [options] = await Promise.all([getOptionByNoAuth()]);
  store.commit("SET_USER", options);
  // 组装基座并获取基座嵌套路由
  let childRouter = await assembleFrame(frame);
  store.commit("SET_FRAME_CHILD_ROUTER", childRouter);

}

init().then(() => {
  let token_info = localStorage.getItem(CONSTANTS.STORAGE_TOKEN);
  if (token_info) {
    token_info = JSON.parse(token_info);
  }
  // 判断是否登录
  if (!token_info || !token_info.accessToken || token_info.accessToken === '') {
    router.replace('/login');
  } else {
    Promise.all([loadMenuAndModule(store, router), getAllOption()]).then(([menuAndModuleRes, optionRes]) => {
      let options = {};
      optionRes.data.forEach(item => {
        options[item.key] = item;
      })
      store.commit("SET_OPTIONS", options);
      router.replace(router.currentRoute.value.path);
    }).catch(err => {
      console.log(err);
    })
  }
}).catch(err => {
  console.log(err);
})

const debounce = (callback, delay) => {
  let tid;
  return function (...args) {
    const ctx = self;
    tid && clearTimeout(tid);
    tid = setTimeout(() => {
      callback.apply(ctx, args);
    }, delay);
  };
};

const _ = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ {
  constructor(callback) {
    callback = debounce(callback, 20);
    super(callback);
  }
};

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
.right-tool{
  margin-left: auto;
}
.table-box{
  margin-top: 8px;
  .el-pagination{
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
  .el-table{
    .el-table__header{
      th{
        word-break: break-word;
        background-color: #f8f8f9!important;
        color: #515a6e;
        height: 40px;
        font-size: 13px;
      }
    }
    .el-table__cell{
      .el-button{
        padding: 5px 3px;
      }
      .el-button+.el-button{
        margin-left: 3px;
      }
    }
  }
}
</style>