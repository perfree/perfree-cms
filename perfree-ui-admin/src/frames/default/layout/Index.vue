<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside width="200px" class="side-container">
          <div class="header-logo">
            xxx管理系统
          </div>
          <el-menu active-text-color="rgb(64, 158, 255)"  background-color="#304156" class="el-menu-vertical-demo" default-active="2"
                   text-color="#fff" :default-active="'/home'" router>
            <menu-tree :menu-list="menuList"></menu-tree>
          </el-menu>
        </el-aside>
        <el-container>
          <el-header>
            <my-header></my-header>
          </el-header>
          <el-main>
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
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import MenuTree from "./component/menuTree.vue";
import MyHeader from "@/frames/default/layout/component/myHeader.vue";

const store = useStore();
const router = useRouter();
const menuList = store.getters.menus;
console.log(router.getRoutes())
console.log(menuList)
// router.replace('/elastic/ecs');



</script>

<style lang="less">
.common-layout,.el-container,.el-aside{
  height: 100%;
}
.el-menu{
  height: calc(100% - 50px);
}
.fade-enter-from{   /* 进入时的透明度为0 和 刚开始进入时的原始位置通过active透明度渐渐变为1 */
  opacity: 0;
  transform: translateX(-100%);
}

.fade-enter-to{   /*定义进入完成后的位置 和 透明度 */
  transform: translateX(0%);
  opacity: 1;
}

.fade-leave-active,.fade-enter-active {
  transition: all 0.5s ease-out;
}

.fade-leave-from { /* 页面离开时一开始的css样式,离开后为leave-to，经过active渐渐透明 */
  transform: translateX(0%);
  opacity: 1;
}

.fade-leave-to{   /* 这个是离开后的透明度通过下面的active阶段渐渐变为0 */
  transform: translateX(100%);
  opacity: 0;
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
  background: #304156;
}
.side-container{
  width: 200px;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
</style>