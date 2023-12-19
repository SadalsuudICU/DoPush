<template>
  <div class="app-container">
    <el-page-header content="新增模板" @back="goBack" />
    <el-divider />
    <el-input
      v-model="keyword"
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
    <el-select placeholder="请选择" value="">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-button
      type="primary"
      style="float: right"
      @click="batchDelete"
    >
      批量删除
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
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keyword: '',
      placeholder: '通过关键字搜索',
      columns: [
        {
          'name': 'id',
          'label': 'ID',
          'width': 20,
          'sortable': true
        },
        {
          'name': 'name',
          'label': '模板消息名'
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
            '70': '企业微信（应用消息）',
            '80': '钉钉群机器人',
            '90': '钉钉工作消息',
            '100': '企业微信（机器人）',
            '110': '飞书机器人'
          }
        },
        {
          'name': 'templateType',
          'label': '模板类型',
          'type': 'mapping',
          'map': {
            '10': '定时任务发送',
            '20': '实时调用接口'
          }
        },
        {
          'name': 'msgType',
          'label': '消息类型',
          'type': 'mapping',
          'map': {
            '10': '通知类',
            '20': '营销类',
            '30': '验证码'
          }
        },
        {
          'name': 'creator',
          'label': '创建者'
        },
        {
          'name': 'idType',
          'label': '接收者ID类型',
          'type': 'mapping',
          'map': {
            '10': '用户ID',
            '20': '设备号',
            '30': '手机号',
            '40': 'openId',
            '50': '邮箱地址',
            '60': '企业微信userId',
            '70': '钉钉userId',
            '80': '推送通知栏cid',
            '90': '飞书userId'
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
      console.log(this.keyword)
    },
    batchDelete() {
      console.log()
    }
  }
}
</script>

<style scoped>
</style>

