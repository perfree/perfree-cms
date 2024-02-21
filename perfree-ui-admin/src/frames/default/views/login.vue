<template>
  <div class="container">
    <img src="../static/images/login-bg.png" class="login-bg">
    <div class="login-box">
      <div class="login-left">
        <img src="../static/images/login-left.png" class="login-left-bg">
      </div>
      <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title">登录</h3>
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.username"
              type="text"
              size="large"
              auto-complete="off"
              placeholder="账号"
          >
            <template #prefix>
              <el-icon  class="no-inherit">
                <User />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              size="large"
              auto-complete="off"
              placeholder="密码"
              @keyup.enter="handleLogin"
          >
            <template #prefix>
              <el-icon  class="no-inherit">
                <Lock />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              size="large"
              style="width: 63%"
              @keyup.enter="handleLogin"
          >
            <template #prefix>
              <el-icon  class="no-inherit">
                <Guide />
              </el-icon>
            </template>
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>

        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>

        <el-form-item style="width:100%;">
          <el-button
              :loading="loading"
              size="large"
              type="primary"
              style="width:100%;"
              @click.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>

<!--          <div style="position: relative;width: 100%;">
            <a class="forget-password" href="javascript:;">忘记密码</a>
            <router-link class="link-type" :to="'/register'" v-if="register">立即注册</router-link>
          </div>-->
        </el-form-item>
      </el-form>
    </div>

    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2023  All Rights Reserved.</span>
    </div>
  </div>
  </template>
  
  <script setup>
  import { ref, getCurrentInstance } from 'vue';
  import {useStore} from "vuex";
  import {useRouter} from "vue-router";
  import {getCodeImg, login, userInfo} from "@/frames/default/api/system";
  import {ElMessage} from "element-plus";
  import {CONSTANTS} from "@/core/utils/constants";
  import {loadMenuAndModule} from "@/core/utils/module";

  const store = useStore();
  const router = useRouter();
  const { proxy } = getCurrentInstance();

  const loginForm = ref({
    username: "",
    password: "",
    rememberMe: false,
    code: "",
    uuid: ""
  });

  const loginRules = {
    username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
    password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
    code: [{ required: true, trigger: "blur", message: "请输入验证码" }]
  };

  let codeUrl = ref("");
  const loading = ref(false);
  // 注册开关
  const register = ref(false);
  // 验证码开关
  const captchaEnabled = ref(true);

  /**
   * 登录
   */
  function handleLogin() {
    proxy.$refs.loginRef.validate(valid => {
      if (valid) {
        loading.value = true;
        // 调用action的登录方法
        login(loginForm.value).then((res) => {
          if (res.code === 200) {
            localStorage.setItem(CONSTANTS.STORAGE_TOKEN, JSON.stringify(res.data));
            Promise.all([loadMenuAndModule(store, router), userInfo()]).then(([menuAndModuleRes, userInfoRes]) => {
              loading.value = false;
              localStorage.setItem(CONSTANTS.STORAGE_USER_INFO, JSON.stringify(userInfoRes.data));
              router.replace("/admin");
            }).catch(err => {
              loading.value = false;
              console.log(err);
            });
          } else {
            ElMessage.error(res.msg);
            loginForm.value.code = "";
            loading.value = false;
            getCode();
          }
        }).catch(() => {
          loading.value = false;
        });
      }
    });
  }

  /**
   * 获取验证码
   */
  function getCode() {
    getCodeImg().then(res => {
      captchaEnabled.value = res.captchaEnabled === undefined ? true : res.captchaEnabled;
      if (captchaEnabled.value) {
        codeUrl.value = "data:image/gif;base64," + res.data.img;
        loginForm.value.uuid = res.data.uuid;
      }
    });
  }

  getCode();
  </script>
  
  <style lang="css" scoped>
  .container{
    height: 100%;
    width: 100%;
    background-color: #e6ebf2;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .login-bg{
    width: 100%;
    height: 100%;
    position: fixed;
  }
  .login-box{
    border-radius: 20px;
    background: #ffffff;
    width: 900px;
    display: flex;
    overflow: hidden;
    box-shadow: 0px 1px 8px 4px rgb(0 0 0 / 10%);
    z-index: 1;
  }
  .login-left{
    width: 400px;
    height: 550px;
  }
  .login-left-bg{
    width: 100%;
    height: 100%;
  }
  .left-title{
    color: white;
    text-align: center;
    font-size: 28px;
  }
  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    font-size: 28px;
    color: #707070;
  }

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 500px;
    padding: 80px 70px 5px 70px;
    margin-left: auto;
    .el-input {
      margin-bottom: 5px;
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 37%;
    height: 40px;
    float: right;
    margin-bottom: 6px;
    img {
      cursor: pointer;
      vertical-align: middle;
      height: 40px;
      padding-left: 5px;
    }
  }
  .forget-password{
    position: absolute;
    left: 0;
    color: #4090f7;
    font-size: 13px;
  }
  .link-type{
    position: absolute;
    right: 0;
    color: #4090f7;
    font-size: 13px
  }
  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
  </style>