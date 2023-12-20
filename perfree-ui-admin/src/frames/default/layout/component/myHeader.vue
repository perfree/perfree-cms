<template>
 <div class="header">
   <div class="header-right">
     <el-button type="primary" text  @click="changeMenu">
       <el-icon><Fold /></el-icon>
     </el-button >
   </div>
 </div>
</template>
<script setup>
const props = defineProps({
  menuIsCollapse: false
})
const emits = defineEmits(['update:menuIsCollapse'])
function changeMenu() {
  if (props.menuIsCollapse) {
    emits('update:menuIsCollapse', false)
  } else {
    emits('update:menuIsCollapse', true)
  }

}
</script>
<style lang="less">
.header{
  height: 100%;
  display: flex;
  line-height: 50px;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}
</style>