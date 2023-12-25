<template>
  <div class="app-container">
    <el-page-header content="新增模板" @back="goBack" />
    <el-divider />
    <el-input
      v-model="creator"
      :placeholder="placeholder"
      prefix-icon="el-icon-search"
      style="width: 350px;"
      clearable
      @clear="search"
      @keydown.enter.native="search"
    />
    <el-select v-model="channelType" placeholder="请选择">
      <el-option
        v-for="item in channelTypeOption"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-button
      icon="el-icon-search"
      type="primary"
      @click="search"
    >
      搜索
    </el-button>
    <p />
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        v-for="item in columns"
        :key="item.name"
        :prop="item.name"
        :label="item.label"
      />
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >Edit</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { list, query } from '@/api/channel'

export default {
  data() {
    return {
      creator: '',
      channelType: '',
      placeholder: '通过关键字搜索',
      tableData: [],
      channelTypeOption: [
        { value: '', label: '不选择' },
        { value: '20', label: 'PUSH通知栏' },
        { value: '30', label: '短信' },
        { value: '40', label: '邮箱' },
        { value: '50', label: '微信服务号（模板消息）' },
        { value: '60', label: '微信小程序（订阅消息）' },
        { value: '70', label: '企业微信（应用消息）' },
        { value: '80', label: '钉钉群机器人' },
        { value: '90', label: '钉钉工作消息' },
        { value: '100', label: '企业微信（机器人）' },
        { value: '110', label: '飞书机器人' }
      ],
      columns: [
        {
          'name': 'id',
          'label': 'ID',
          'width': 20,
          'sortable': true
        },
        {
          'name': 'name',
          'label': '账号名称'
        },
        {
          'name': 'accountConfig',
          'label': '账号配置信息'
        },
        {
          'name': 'sendChannel',
          'label': '发送渠道',
          'type': 'mapping',
          'map': {
            '20': 'PUSH通知栏',
            '30': '短信',
            '40': '邮箱',
            '50': '微信服务号（模板消息）',
            '60': '微信小程序（订阅消息）',
            '70': '企业微信应用消息',
            '80': '钉钉群机器人',
            '90': '钉钉工作消息',
            '100': '企业微信机器人',
            '110': '飞书机器人'
          }
        }
      ]
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    search() {
      const data = {
        creator: this.creator === '' ? 'Sadalsuud' : this.creator,
        channelType: this.channelType
      }
      if (this.channelType === '') {
        list(data).then(res => {
          this.tableData = this.dataConvert(res.data)
        }).catch(err => {
          console.log(err)
        })
      } else {
        query(data).then(res => {
          this.tableData = this.dataConvert(res.data)
        }).catch(err => {
          console.log(err)
        })
      }
    },
    batchDelete() {
      console.log()
    },
    dataConvert(datas) {
      for (let i = 0; i < datas.length; i++) {
        const data = datas[i]
        data.sendChannel = this.columns[3].map[data.sendChannel]
      }
      return datas
    },
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    }
  }
}
</script>

<style scoped>
</style>

