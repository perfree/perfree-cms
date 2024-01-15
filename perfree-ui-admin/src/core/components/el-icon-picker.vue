<script setup>
import { ref, defineEmits, defineProps, getCurrentInstance } from 'vue'

const { appContext: {app: { config: { globalProperties } } } } = getCurrentInstance()

const props = defineProps({
  modelValue: String
});
const emits = defineEmits(['update:modelValue'])
const iconSelect = ref();
function selectIcon(icon) {
  emits('update:modelValue', icon);
  iconSelect.value.hide();
}

</script>

<template>
  <el-popover  trigger="click" width="500" placement="bottom-start" ref="iconSelect">
    <template #reference>
      <el-input
          :modelValue="modelValue"
          class="w-50 m-2"
          placeholder="请选择图标"
          :prefix-icon="modelValue"
      />
    </template>
    <div class="el-icon-picker">
      <component v-for="icon in globalProperties.$icons" :key="icon"
                 :class="[icon, 'icon', { 'icon-active': icon === modelValue }]"
                 :is="icon"
                 @click="selectIcon(icon)">
      </component>
    </div>
  </el-popover>
</template>

<style scoped>
.el-icon-picker {
  height: 256px;
  overflow-y: scroll;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.icon {
  display: inline-block;
  width: 20px;
  height: 20px;
  color: var(--el-text-color-regular);
  font-size: 20px;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
  line-height: 45px;
  margin: 5px;
}

.icon:hover {
  color: var(--el-color-primary);
}

.icon-active {
  color: var(--el-color-primary);
}
</style>