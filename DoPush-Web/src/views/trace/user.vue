<template>
  <div class="app-container">
    <el-page-header content="用户全链路追踪" @back="goBack" />
    <el-divider />
    <el-tag>查询条件：用户ID[只能查询当天的数据]</el-tag>
    <p />
    <el-input
      v-model="receiver"
      :placeholder="placeholder"
      prefix-icon="el-icon-search"
      style="width: 350px;"
      clearable
      @clear="search"
      @keydown.enter.native="search"
    />
    <el-button
      icon="el-icon-search"
      type="primary"
      @click="search"
    >
      搜索
    </el-button>
    <p />
    <el-table
      ref="table"
      :data="tableData"
      style="width: 100%"
      tooltip-effect="dark"
    >
      <el-table-column
        v-for="item in columns"
        :key="item.name"
        :prop="item.name"
        :label="item.label"
        :type="item.typeLine"
      />
    </el-table>
  </div>
</template>

<script>

import { user } from '@/api/trace'

export default {
  data() {
    return {
      tableData: [],
      receiver: '',
      placeholder: '通过用户ID搜索, 如邮箱ID: XXX@qq.com',
      columns: [
        {
          'name': 'businessId',
          'label': '业务ID'
        },
        {
          'name': 'title',
          'label': '模板名称'
        },
        {
          'name': 'sendType',
          'label': '发送类型'
        },
        {
          'name': 'creator',
          'label': '模板创建者'
        },
        {
          'name': 'detail',
          'label': '发送细节'
        }
      ]
    }
  },
  // mounted() {
  //   this.search()
  // },
  methods: {
    goBack() {
      this.$router.back()
    },
    search() {
      if (this.receiver === '') {
        this.$message({
          type: 'error',
          message: 'invalidate receiver'
        })
        return
      }
      user(this.receiver).then(res => {
        console.log(res)
        this.tableData = res.data.items
      })
    }
  }
}
</script>

<style scoped>
</style>

