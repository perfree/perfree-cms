<template>
  <template v-for="menu in props.menuList">
    <template v-if="menu.children && menu.children.length > 0">
      <el-sub-menu :index="menu.id">
        <template #title>
          <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
          <span>{{menu.name}}</span>
        </template>
        <menuTree :menuList="menu.children"></menuTree>
      </el-sub-menu>
    </template>
    <template v-else>
      <el-menu-item :index="menu.path" >
        <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
        <template #title>{{menu.name}}</template>
      </el-menu-item>
    </template>

  </template>
</template>
<script setup>
const props = defineProps({
  menuList: []
})
</script>

<style lang="less">

</style>