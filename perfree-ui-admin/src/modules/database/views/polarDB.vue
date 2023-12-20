<template>
  <div class="page">
    这是子模块database的polarDB页面
  </div>
 
</template>

<script>
import moment from "moment";

export default {
  data() {
    return {
      moment: moment
    };
  },
  created() {
    console.log(moment);
  }
};
</script>
