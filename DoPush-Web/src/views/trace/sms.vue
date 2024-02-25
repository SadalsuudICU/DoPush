<template>
  <div class="app-container">
    <el-page-header content="短信发送数据查询" @back="goBack" />
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
    <el-date-picker
      v-model="dateTime"
      type="date"
      placeholder="选择日期"
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

import { sms } from '@/api/trace'

export default {
  data() {
    return {
      receiver: '',
      dateTime: '',
      placeholder: 'Phone: XXXXXXXXXXX',
      tableData: [],
      selectedRows: [],
      columns: [
        {
          'name': 'businessId',
          'label': '业务ID'
        },
        {
          'name': 'content',
          'label': '发送内容'
        },
        {
          'name': 'sendTime',
          'label': '发送时间'
        },
        {
          'name': 'sendType',
          'label': '发送状态'
        },
        {
          'name': 'receiveTime',
          'label': '回执时间'
        },
        {
          'name': 'receiveType',
          'label': '回执状态'
        },
        {
          'name': 'receiveContent',
          'label': '回执报告'
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
      const data = {
        receiver: this.receiver,
        dateTime: this.dateTime
      }
      if (!(data.receiver && data.dateTime)) {
        this.$message({
          type: 'error',
          message: 'invalidate receiver or dateTime'
        })
        return
      }
      sms(data).then(res => {
        console.log(res.data.items)
        this.tableData = res.data.items
      })
    }
  }
}
</script>

<style scoped>
</style>

