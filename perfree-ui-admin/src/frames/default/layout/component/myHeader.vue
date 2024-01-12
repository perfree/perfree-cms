<template>
 <div class="header">
   <div class="header-left">
     <span @click="changeMenu" class="menu-change-btn">
       <el-icon v-if="!menuIsCollapse"><Fold /></el-icon>
       <el-icon v-else><Expand /></el-icon>
     </span>

     <el-breadcrumb class="breadcrumb" separator="/">
         <el-breadcrumb-item v-for="(item,index) in levelList" :to="{ path: item.path }">
           <span v-if="item.redirect==='noRedirect'||index===levelList.length-1" class="no-redirect">{{ item.meta.name }}</span>
           <a v-else>{{ item.meta.name }}</a>
         </el-breadcrumb-item>
     </el-breadcrumb>
   </div>

   <div class="header-right">
        <span @click="fullScreen" class="fullScreen-btn">
          <el-icon><FullScreen /></el-icon>
       </span>

     <div>
       <el-dropdown trigger="click" class="header-user-box">
        <span class="el-dropdown-link">
          <el-avatar  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
          <span>admin</span>
          <el-icon class="el-icon--right"><arrow-down /></el-icon>
        </span>
         <template #dropdown>
           <el-dropdown-menu>
             <el-dropdown-item>个人中心</el-dropdown-item>
             <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
           </el-dropdown-menu>
         </template>
       </el-dropdown>
     </div>
   </div>
 </div>
</template>
<script setup>
import { ref } from 'vue';
import {useRouter, onBeforeRouteUpdate} from "vue-router";
import screenfull from 'screenfull';
import {CONSTANTS} from "@/core/utils/constants";

const props = defineProps({
  menuIsCollapse: false
});

const router = useRouter();

const emits = defineEmits(['update:menuIsCollapse']);
let levelList = ref(null);

/**
 * 监听路由改变
 */
onBeforeRouteUpdate((to) => {
  if (to.path.startsWith('/redirect/')) {
    return
  }
  getBreadcrumb(to)
});

/**
 * 展开/缩放侧边栏
 */
function changeMenu() {
  if (props.menuIsCollapse) {
    emits('update:menuIsCollapse', false)
  } else {
    emits('update:menuIsCollapse', true)
  }
}

/**
 * 获取面包屑
 * @param route
 */
function getBreadcrumb(route) {
  let matched = route.matched.filter(item => item.meta && item.meta.name)
  const first = matched[0]
  if (!isDashboard(first)) {
    matched = [{ path: '/home', meta: { name: '首页' }}].concat(matched)
  }
  levelList.value = matched.filter(item => item.meta && item.meta.name && item.meta.breadcrumb !== false)
}

/**
 * 是否为首页
 * @param route
 * @returns {boolean}
 */
function isDashboard(route) {
  const name = route && route.meta.name
  if (!name) {
    return false
  }
  return name.trim() === '首页'
}

/**
 * 全屏
 */
function fullScreen() {
  if (!screenfull.isEnabled) {
    return false
  }
  screenfull.toggle()
}

/**
 * 退出登录
 */
function logout() {
  localStorage.removeItem(CONSTANTS.STORAGE_TOKEN);
  router.replace("/login")
}

getBreadcrumb(router.currentRoute.value);
</script>
<style lang="less">
.header{
  height: 100%;
  display: flex;
  line-height: 50px;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}
.header-left{
  color: var(--el-text-color-regular);
  display: flex;
}
.menu-change-btn, .fullScreen-btn{
  display: inline-block;
  line-height: 50px;
  width: 50px;
  text-align: center;
  cursor: pointer;
  font-size: 16px;
}
.breadcrumb{
  line-height: 50px;
}
.breadcrumb .el-breadcrumb__inner a, .breadcrumb .el-breadcrumb__inner.is-link{
  font-weight: 400;
}
.breadcrumb .el-breadcrumb__separator{
  font-weight: 400;
}

.header-right{
  margin-left: auto;
  display: flex;
}
.header-user-box{
  height: 50px;
  line-height: 50px;
  cursor: pointer;
  margin-right: 20px;
}
.header-user-box .el-dropdown-link{
  display: flex;
  align-items: center;
}
</style>