<template>
  <div class="common-layout">
    <el-container>
<!--      <el-header>Header</el-header>-->
      <el-container>
        <el-aside width="200px">
          <el-menu active-text-color="#ffd04b"  background-color="#545c64" class="el-menu-vertical-demo" default-active="2"
                   text-color="#fff" :default-active="'/home'" router>
            <el-menu-item :index="'/home'" >
              <el-icon><el-icon><House /></el-icon></el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-menu-item :index="'/ecs'" >
              <el-icon><setting /></el-icon>
              <span>子模块elastic</span>
            </el-menu-item>

            <el-menu-item :index="'/polarDB'" >
              <el-icon><setting /></el-icon>
              <span>子模块database</span>
            </el-menu-item>

            <el-menu-item :index="'/test'" >
              <el-icon><setting /></el-icon>
              <span>不存在的模块</span>
            </el-menu-item>
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

const store = useStore();
const router = useRouter();

// router.replace('/elastic/ecs');



</script>

<style lang="less">
.common-layout,.el-container,.el-aside,.el-menu{
  height: 100%;
}
</style>