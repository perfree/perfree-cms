<template>
  <div class="block text-center">
    登录页
    <el-button type="primary" @click="login">登录</el-button>
  </div>
  </template>
  
  <script setup>
  import {useStore} from "vuex";
  import {useRouter} from "vue-router";
  import {loadMenuAndModule} from "@/core/utils/module";

  const store = useStore();
  const router = useRouter();

  function login() {
    localStorage.setItem("user_info", JSON.stringify({id: 1, userName: 'admin', token: '1231256456456'}));
    loadMenuAndModule(store, router).then(() => {
      console.log(router.getRoutes())
      router.replace('/');
    }).catch(err => {
      console.log(err);
    })
  }
  </script>
  
  <style lang="less" scoped>
  .demonstration {
    color: var(--el-text-color-secondary);
  }

  .el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
    text-align: center;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
  }
  </style>