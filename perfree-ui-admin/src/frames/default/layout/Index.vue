<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside class="side-container">
          <div class="header-logo">
            <div class="header-logo-box">
              <img src="../static/images/logo.png" class="header-logo-img">
              <div  v-if="!menuIsCollapse" class="header-title">xxx管理系统</div>
            </div>
          </div>
          <el-menu active-text-color="rgb(64, 158, 255)"  background-color="#304156" class="side-menu"
                   text-color="#fff" :default-active="currRouter" router :collapse="menuIsCollapse" :collapse-transition="false">
            <menu-tree :menu-list="menuList"></menu-tree>
          </el-menu>
        </el-aside>
        <el-container>
          <el-header>
            <my-header v-model:menu-is-collapse="menuIsCollapse"></my-header>
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
import { ref } from 'vue';
import MenuTree from "./component/menuTree.vue";
import MyHeader from "@/frames/default/layout/component/myHeader.vue";

const store = useStore();
const router = useRouter();
const currRouter = router.currentRoute.value.path;
const menuList = store.getters.menus;
let menuIsCollapse = ref(false);
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
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  width: auto;
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
}
.header-logo-img{
  margin-left: 12px;
  margin-right: 12px;
  width: 40px;
}
.header-title{
  width: 136px;
  text-align: left;
  color: white;
}
</style>