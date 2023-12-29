<template>
  <div class="app-container">
    <el-page-header content="消息模板全链路追踪" @back="goBack" />
    <el-divider />
    <el-tag>查询条件：模板id[非当天数据]</el-tag>
    <p />
    <div style="color: darkred;font-size: 10px">
      businessId=模板类型+模板ID+当天日期
      <br>
      eg:2000044620230129
      <br>前2位为模板类型：10、定时类的模板(后台定时调用) 20、实时类的模板(接口实时调用)
      <br>中间6位为模板Id：446为模板Id，不够6位在前面补0
      <br>后8位为下发的日期：20230129
      <br>(固定16位)
    </div>
    <p />
    <el-input
      v-model="businessId"
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
    <div style="box-shadow: 0 2px 4px rgba(115,113,113,0.12), 0 0 6px rgba(0,0,0,0.17);min-height: 20rem;">
      <span>图表</span>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      businessId: '',
      placeholder: '查询非当天的模板数据, 输入businessId',
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
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        sendChannel: [
          { required: true, message: 'idType', trigger: 'blur' }
        ],
        accountConfig: [
          { required: true, message: 'msgType', trigger: 'blur' }
        ]
      }
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
        console.log(data)
      } else {
        console.log()
      }
    }
  }
}
</script>

<style scoped>
</style>

