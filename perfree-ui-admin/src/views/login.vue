<template>
  <div class="login">
    <div class="login-box">
      <div class="login-left"> </div>
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <div class="login-title">{{ projectName }}</div>
        <el-form-item prop="account">
          <el-input
              v-model="loginForm.account"
              type="text"
              auto-complete="off"
              placeholder="请输入账号"
          >
            <template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="请输入密码"
              @keyup.enter="handleLogin"
          >
            <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
        </el-form-item>

        <el-form-item style="width:100%;">
          <el-button
              :loading="loading"
              size="large"
              type="primary"
              style="width:100%;height: 36px"
              @click.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
        </el-form-item>

        <div style="position: relative">
          <a style="position: absolute;left: 0;color: var(--el-color-primary);font-size: 13px">忘记密码</a>
          <a style="position: absolute;right: 0;color: var(--el-color-primary);font-size: 13px">注册新账号</a>
        </div>
      </el-form>
    </div>

    <!--  底部  -->
    <div class="el-login-footer">
      <span>{{copyright}}</span>
    </div>
  </div>
</template>

<script>
import useUserStore from '@/store/modules/user'
export default {
  name: "Login",
  data() {
    return {
      projectName: '管理系统',
      copyright: 'Copyright © 2018-2023  All Rights Reserved.',
      loginForm: {
        account: "",
        password: "",
        rememberMe: false
      },
      loginRules: {
        account: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
        password: [{ required: true, trigger: "blur", message: "请输入您的密码" }]
      },
      loading: false
    }
  },
  created() {

  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          // 调用action的登录方法
          useUserStore().login(this.loginForm).then(() => {
            this.$router.push({path: this.redirect || "/"}).catch(() => {});
          }).catch(() => {
            this.loading = false;
          });
        }
      });
    }
  }
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
  background-color: #bbd9fa;
}
.login-box{
  border-radius: 20px;
  background: #ffffff;
  width: 800px;
  display: flex;
  overflow: hidden;
  box-shadow: 0 1px 8px 4px rgb(0 0 0 / 10%);
}
.login-left{
  width: 400px;
  height: 500px;
  padding-top: 200px;
  background-image: url(../assets/images/login-left.png);
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 20px 0 0 20px;
}
.login-title {
  text-align: center;
  font-size: 20px;
  color: rgb(64, 144, 247);
  font-weight: 600;
  margin-bottom: 40px;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 80px 25px 5px 25px;
  margin-left: auto;
  .el-input {
    height: 36px;
    input {
      height: 36px;
    }
  }
  .input-icon {
    height: 36px;
    width: 14px;
    margin-left: 0;
  }
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
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
