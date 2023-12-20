<template>
  <div class="common-layout">
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu active-text-color="#ffd04b"  background-color="#545c64" class="el-menu-vertical-demo" default-active="2"
                   text-color="#fff" :default-active="'/home'" router>
            <menu-tree :menu-list="menuList"></menu-tree>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import MenuTree from "@/frames/default/layout/component/menuTree.vue";

const store = useStore();
const router = useRouter();
const menuList = store.getters.menus;
console.log(router.getRoutes())
console.log(menuList)
// router.replace('/elastic/ecs');



</script>

<style lang="less">
.common-layout,.el-container,.el-aside,.el-menu{
  height: 100%;
}
</style>