<template>
  <div class="container">
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
  .container{
    height: 100%;
    width: 100%;
    background-color: #e6ebf2;
    background-image: url('./login-bg.png');
    background-size: cover;
  }
  </style>