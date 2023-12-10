<template>
  <component :is="type" v-bind="linkProps()" @click="handleClick">
    <slot />
  </component>
</template>

<script setup>
import { isExternal } from '@/utils/validate'

const props = defineProps({
  to: {
    type: [String, Object],
    required: true
  },
  menu: {}
})

const isExt = computed(() => {
  return isExternal(props.to)
})

const type = computed(() => {
  if (isExt.value) {
    return 'a'
  }
  return 'router-link'
})

function linkProps() {
  if (isExt.value) {
    return {
      href: props.to,
      target: props.menu.target === 0 ? '_self' : '_blank',
      rel: 'noopener'
    }
  }
  return {
    to: props.to
  }
}

function handleClick(e) {
  // if (props.message.isFrame === '0') {
  //   alert('122')
  //   return
  // }
  // console.log(props.message.isFrame)
/*  if(e.preventDefault) {
    e.preventDefault();
  } else {
    e.returnValue = false;
  }*/
}
</script>
