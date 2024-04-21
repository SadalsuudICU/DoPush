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
        :label="columns[0].label"
      >
        <template v-slot:default="scope">
          <span style="margin-left: 10px">{{ scope.row[columns[0].name] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="columns[1].label"
      >
        <template v-slot:default="scope">
          <span style="margin-left: 10px">{{ scope.row[columns[1].name] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="columns[2].label"
      >
        <template v-slot:default="scope">
          <span style="margin-left: 10px">{{ scope.row[columns[2].name] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="columns[3].label"
      >
        <template v-slot:default="scope">
          <i class="el-icon-user" />
          <span style="margin-left: 10px">{{ scope.row[columns[3].name] }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="columns[4].label"
      >
        <template v-slot:default="scope">
          <i class="date" />
          <span style="margin-left: 10px">下发次数: {{ scope.row[columns[4].name].length }}</span>
          <el-button size="mini" @click="checkSendDetail(scope.row)">
            <i class="el-icon-collection" />
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="title" :visible.sync="detailTableVisible">
      <el-table
        ref="table"
        :data="checkDetailList"
        style="width: 100%"
        tooltip-effect="dark"
      >
        <el-table-column
          v-for="item in detailColumns"
          :key="item.name"
          :prop="item.name"
          :label="item.label"
        />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>

import { user } from '@/api/trace'

export default {
  data() {
    return {
      tableData: [],
      checkDetailList: [],
      receiver: '',
      detailTableVisible: false,
      title: '',
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
      ],
      detailColumns: [
        {
          'name': 'time',
          'label': '发送时间'
        },
        {
          'name': 'state',
          'label': '下发状态'
        }
      ]
    }
  },
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
        this.tableData = res.data.items
        if (this.tableData.length > 0) {
          this.$message.success('查询成功')
        } else {
          this.$message.info('暂无相关数据')
          return
        }
        this.tableData.forEach(item => {
          const detail = item.detail
          let details = detail.split('==>')
          details = details.slice(0, details.length - 1)
          if (details.length > 0) {
            item.detail = details
          }
        })
      })
    },
    checkSendDetail(data) {
      this.title = ''
      this.checkDetailList = []
      for (let i = 0; i < 4; i++) {
        this.title += '/' + data[this.columns[i].name]
      }
      const detail = data[this.columns[4].name]
      detail.forEach(item => {
        const d = item.split(':')
        item = {
          'time': d.slice(0, d.length - 1).join(':'),
          'state': d[d.length - 1] || ''
        }
        this.checkDetailList.push(item)
      })
      console.log(this.checkDetailList)
      this.detailTableVisible = true
    }
  }
}
</script>

<style scoped>
.el-table .cell {
  white-space: pre-line;
}
</style>

