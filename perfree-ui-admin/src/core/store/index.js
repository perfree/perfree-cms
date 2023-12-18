import { createStore } from "vuex";
// 模块
import core from "./modules/core";

const store = createStore({
  modules: {
    core
  }
});

export default store;
