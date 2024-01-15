import { createApp } from "vue";
import router from "./router";
import store from "./store/";
import App from "./App.vue";

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const app = createApp(App);
// 接口
import axios from "./api/axios";
window.axios = axios;

if (process.env.NODE_ENV === "development") {
  //开启debug模式
  app.config.debug = true;
}
app.use(router)
app.use(store)
app.use(ElementPlus, {
  locale: zhCn,
})

// 全局挂载和注册 element-plus 的所有 icon
app.config.globalProperties.$icons = []
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.config.globalProperties.$icons.push(key)
  app.component(key, component)
}
app.mount('#app')
