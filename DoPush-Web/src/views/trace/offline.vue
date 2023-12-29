<template>
  <div class="app-container">
    <el-page-header content="离线消息全链路追踪" @back="goBack" />
    <el-divider />
    <div style="color: darkred">
      功能待上线
    </div>
  </div>
</template>

<script>

export default {
  methods: {
    goBack() {
      this.$router.back()
    }
  }
}
</script>

<style scoped>
</style>

