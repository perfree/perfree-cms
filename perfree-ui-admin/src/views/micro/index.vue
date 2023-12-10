<template>
  <div class="miro-son1">
    <micro-app
        name="micro1"
        url="http://localhost:5173/"
        iframe
        :data='{data: randomData}'
    ></micro-app>
  </div>
</template>

<script setup>
const randomData = ref(Math.random());

</script>

<style scoped lang="scss">
</style>