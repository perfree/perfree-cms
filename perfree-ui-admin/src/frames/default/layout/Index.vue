<template>
  <div class="common-layout">
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu active-text-color="#ffd04b"  background-color="#545c64" class="el-menu-vertical-demo" default-active="2"
                   text-color="#fff" :default-active="'/home'" router>
            <el-menu-item :index="'/elastic/ecs'" >
              <el-icon><setting /></el-icon>
              <span>Navigator Four</span>
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
const menus = store.getters.menus;

console.log(menus)
// router.replace('/elastic/ecs');
</script>

<style lang="less">
html,body,.full{
    height: 100%;
    .container{
        height: 100%;
        .sider{
            height: 100%;
        }
    }
}
</style>