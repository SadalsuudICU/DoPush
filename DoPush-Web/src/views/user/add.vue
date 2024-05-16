<script>
import { add } from '@/api/user'

export default {
  data() {
    return {
      username: '',
      password: '',
      role: ''
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    add() {
      add(this.username, this.role).then(res => {
        if (res.status === '200') {
          this.$message.success('添加用户成功')
          this.$router.push('/user/permission')
        } else {
          this.$message.error('添加失败')
        }
      })
    }
  }
}
</script>

<template>
  <div class="app-container">
    <el-page-header content="添加用户" @back="goBack" />
    <el-input v-model="username" placeholder="用户名" />
    <el-radio v-model="user.role" label="0">用户管理</el-radio>
    <el-radio v-model="user.role" label="1">模板审核</el-radio>
    <el-radio v-model="user.role" label="2">业务员</el-radio>
    <p style="color: chocolate; font-size: 0.8rem">用户初始密码为: 123456</p>
    <el-button type="success" @click="add">添加</el-button>
  </div>
</template>

<style scoped>

</style>
