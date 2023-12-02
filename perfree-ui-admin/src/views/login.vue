<template>
  <div class="login">
    <div class="login-box">
      <div class="login-left"> </div>
      <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title">登录</h3>
        <el-form-item prop="account">
          <el-input
              v-model="loginForm.account"
              type="text"
              size="large"
              auto-complete="off"
              placeholder="账号"
          >
            <template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template>
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
            <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
          </el-input>
        </el-form-item>

        <el-form-item style="width:100%;">
          <el-button
              :loading="loading"
              size="large"
              type="primary"
              style="width:100%;height: 50px"
              @click.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
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
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const router = useRouter();
const { proxy } = getCurrentInstance();

const loginForm = ref({
  account: "",
  password: "",
  rememberMe: false,
  uuid: ""
});

const loginRules = {
  account: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }]
};

const codeUrl = ref("");
const loading = ref(false);
// 注册开关
const register = ref(false);
const redirect = ref(undefined);

function handleLogin() {
  proxy.$refs.loginRef.validate(valid => {
    if (valid) {
      loading.value = true;
      // 调用action的登录方法
      userStore.login(loginForm.value).then(() => {
        router.push({ path: redirect.value || "/" });
      }).catch(() => {
        loading.value = false;
      });
    }
  });
}
</script>

<style lang='scss' scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.login-box{
  border-radius: 6px;
  background: #ffffff;
  width: 800px;
  display: flex;
  overflow: hidden;
  box-shadow: 0px 1px 8px 4px rgb(0 0 0 / 10%);
}
.login-left{
  width: 400px;
  height: 600px;
  padding-top: 200px;
  background-image: url(../assets/images/login-left.png);
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 20px 0 0 20px;
}
.left-title{
  color: white;
  text-align: center;
  font-size: 28px;
}
.title {
  margin: 0px auto 50px auto;
  text-align: center;
  font-size: 28px;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 80px 25px 5px 25px;
  margin-left: auto;
  .el-input {
    height: 50px;
    input {
      height: 50px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 40px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
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
.login-code-img {
  height: 40px;
  padding-left: 12px;
}
.el-form-item--default{
  margin-bottom: 30px;
}
</style>
