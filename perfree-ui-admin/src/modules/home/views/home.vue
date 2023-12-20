<template>
  <div class="page">
    首页,当前用户: {{userInfo.userName}}

    <el-button type="primary" @click="logout">退出登录</el-button>
  </div>
 
</template>

<script setup>
import {useRouter} from "vue-router";
import {CONSTANTS} from "@/core/utils/constants";

let userInfo = JSON.parse(localStorage.getItem(CONSTANTS.STORAGE_USER_INFO));
const router = useRouter();
function logout() {
  localStorage.removeItem(CONSTANTS.STORAGE_USER_INFO);
  router.replace("/login")
}
</script>
