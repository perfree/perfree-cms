<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside :class="{'side-container': true, 'side-container-small': menuIsCollapse}">
          <div class="header-logo">
            <div class="header-logo-box">
              <img src="../static/images/logo.png" class="header-logo-img">
              <div class="header-title" >xxx管理系统</div>
            </div>
          </div>

          <el-menu active-text-color="var( --primary-color)"  background-color="var(--side-background-color)" class="side-menu"
                   text-color="#fff" :default-active="currRouter" router :collapse="menuIsCollapse" :collapse-transition="false">
            <menu-tree :menu-list="menuList"></menu-tree>
          </el-menu>

        </el-aside>

        <el-container>

          <el-header>
            <my-header v-model:menu-is-collapse="menuIsCollapse"></my-header>
          </el-header>

          <el-main class="app-main">
            <transition name="fade">
              <router-view></router-view>
            </transition>
          </el-main>

        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {onBeforeRouteUpdate, useRouter} from "vue-router";
import {useStore} from "vuex";
import { ref } from 'vue';
import MenuTree from "./component/menuTree.vue";
import MyHeader from "@/frames/default/layout/component/myHeader.vue";

const store = useStore();
const router = useRouter();
let currRouter = ref(router.currentRoute.value.path);
const menuList = store.getters.menus;
let menuIsCollapse = ref(false);

onBeforeRouteUpdate((to) => {
  currRouter.value = to.path;
});
// router.replace('/elastic/ecs');



</script>

<style lang="less">
.common-layout,.el-container,.el-aside{
  height: 100%;
}
.el-menu{
  height: calc(100% - 50px);
}
.el-header{
  height: 50px;
  padding: 0;
}
.el-menu{
  border: none;
}
.header-logo{
  height: 50px;
  background: var(--side-background-color);
}
.side-container{
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  width: 200px;
  transition: width 0.28s;
  background: var(--side-background-color);
}
.side-menu{
  max-width: 200px;
}
.header-logo-box{
  text-align: center;
  line-height: 50px;
  display: flex;
  align-items: center;
  height: 50px;
  width: 200px;
}
.side-container-small{
  width: 64px;
}
.header-logo-img{
  margin-left: 12px;
  margin-right: 12px;
  width: 40px;
}
.header-title{
  text-align: left;
  color: white;
}

/** 动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>