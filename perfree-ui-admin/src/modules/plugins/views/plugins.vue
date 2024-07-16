<template>
  <div class="page">
    首页,当前用户: {{userInfo.userName}}

    <el-button type="primary" @click="logout">退出登录</el-button>

    <div id="xxx"></div>
  </div>

</template>

<script setup>
import {useRouter} from "vue-router";
import {CONSTANTS} from "@/core/utils/constants";
import {onMounted, ref} from "vue";
import 'cherry-markdown/dist/cherry-markdown.css';
import Cherry from 'cherry-markdown/dist/cherry-markdown.core';
import {loadMicroApp} from "qiankun";

let userInfo = JSON.parse(localStorage.getItem(CONSTANTS.STORAGE_USER_INFO));
const router = useRouter();
loadMicroApp({
  name: 'app-vue3',
  entry: '//127.0.0.1:4201/admin/plugin/demo',
  container: '#xxx',
});
let cherryInstance =  null;
const markdownContainer = ref();
function logout() {
  localStorage.removeItem(CONSTANTS.STORAGE_USER_INFO);
  router.replace("/login")
}

onMounted(() => {
  cherryInstance =  new Cherry({
    el: markdownContainer.value,
    value: '写点什么?',
    forceAppend: false,
    engine: {
      global: {
        classicBr: true,
      },
      syntax: {
        table: {
          enableChart: false,
        },
      }
    },
    toolbars: {
      // 定义顶部工具栏
      toolbar: ['undo', 'redo', '|',
        'header',
        {bold:['bold', 'italic', 'underline','hr', 'br', 'strikethrough']},
        'justify','quote',
        'list', 'link', 'code','table'
      ],
      // 定义侧边栏，默认为空
      sidebar: ['theme', 'mobilePreview', 'copy'],
      // 定义顶部右侧工具栏，默认为空
      toolbarRight: ['fullScreen', 'export'],
      // 定义选中文字时弹出的“悬浮工具栏”，默认为 ['bold', 'italic', 'underline', 'strikethrough', 'sub', 'sup', 'quote', '|', 'size', 'color']
      bubble: ['bold', 'italic', 'underline', 'strikethrough', 'quote'],
      // 定义光标出现在行首位置时出现的“提示工具栏”，默认为 ['h1', 'h2', 'h3', '|', 'checklist', 'quote', 'table', 'code']
      float: ['h1', 'h2', 'h3', '|', 'checklist', 'quote', 'table', 'code'],
    },
    previewer: {
      enablePreviewerBubble: false,
    }
  });
})


</script>
