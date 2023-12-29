<template>
  <div class="app-container">
    <el-page-header content="短信发送数据查询" @back="goBack" />
    <el-divider />
    <el-tag>查询条件：用户ID[只能查询当天的数据]</el-tag>
    <p />
    <el-input
      v-model="creator"
      :placeholder="placeholder"
      prefix-icon="el-icon-search"
      style="width: 350px;"
      clearable
      @clear="search"
      @keydown.enter.native="search"
    />
    <el-date-picker
      v-model="date"
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

export default {
  data() {
    return {
      date: '',
      creator: '',
      channelType: '',
      placeholder: '通过用户ID搜索, 如邮箱ID: XXX@qq.com',
      tableData: [],
      selectedRows: [],
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
        { value: '110', label: '飞书机器人' },
        { label: '支付宝小程序', value: '120' }
      ],
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
      ],
      dialogTableVisible: false,
      form: {
        name: '',
        sendChannel: '',
        accountConfig: ''
      },
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
      },
      sendChannelOptions: [
        {
          'label': 'PUSH通知栏',
          'value': 20
        },
        {
          'label': '短信',
          'value': 30
        },
        {
          'label': '邮箱',
          'value': 40
        },
        {
          'label': '微信服务号（模板消息）',
          'value': 50
        },
        {
          'label': '微信小程序（订阅消息）',
          'value': 60
        },
        {
          'label': '企业微信（应用消息）',
          'value': 70
        },
        {
          'label': '企业微信（机器人）',
          'value': 100
        },
        {
          'label': '钉钉群机器人',
          'value': 80
        },
        {
          'label': '钉钉工作消息',
          'value': 90
        },
        {
          'label': '飞书机器人',
          'value': 110
        },
        {
          'label': '支付宝小程序',
          'value': 120
        }
      ],
      fieldSet: [
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '10',
          'mode': 'normal',
          'inline': true,
          'label': 'IM账号样例：'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '30',
          'mode': 'normal',
          'inline': true,
          'label': '腾讯云账号样例：{"url":"sms.tencentcloudapi.com","region":"ap-guangzhou","secretId":"AKIDhDxxxxxxxx1WljQq","secretKey":"B4hwww39yxxxrrrrgxyi","smsSdkAppId":"1423123125","templateId":"1182097","signName":"Java3y公众号","supplierId":10,"supplierName":"腾讯云","scriptName":"TencentSmsScript"}</br></br>  云片账号配置样例：{"url":"https://sms.yunpian.com/v2/sms/tpl_batch_send.json","apikey":"caffff8234234231b5cd7","tpl_id":"523333332","supplierId":20,"supplierName":"云片","scriptName":"YunPianSmsScript"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '20',
          'mode': 'normal',
          'inline': true,
          'label': '个推push账号样例：{"appId":"23423423","appKey":"234234234","masterSecret":"2342342342342"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '40',
          'mode': 'normal',
          'inline': true,
          'label': 'QQ邮箱账号样例：{"host":"smtp.qq.com","port":465,"user":"23423432@qq.com","pass":"234324324","from":"123123@qq.com","starttlsEnable":"true","auth":true,"sslEnable":true} </br></br> 163邮箱配置账号样例：{"host":"smtp.163.com","port":465,"user":"23423423@163.com","pass":"234234324","from":"112312312@163.com","starttlsEnable":"false","auth":true,"sslEnable":true}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '50',
          'mode': 'normal',
          'inline': true,
          'label': '服务号账号样例：{"appId":"wx27f83ca10e06b325","secret":"203299484df873a18621d076db46fa99"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '60',
          'mode': 'normal',
          'inline': true,
          'label': '小程序账号样例：{"appId":"wx993a5323432ba86","appSecret":"7ffffcc59e1fsdafsdafsdc0650ac1ada7"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '70',
          'mode': 'normal',
          'inline': true,
          'label': '企业微信（应用消息）账号样例：{"corpId":"23423423","corpSecret":"-234324234","agentId":1000002,"token":"234234","aesKey":"23423423"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '100',
          'mode': 'normal',
          'inline': true,
          'label': '企业微信（机器人）消息样例：{"webhook":"https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=5699eac1-b073-47da-9e97-xxxxxxxx"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '80',
          'mode': 'normal',
          'inline': true,
          'label': '钉钉群机器人样例：{"secret":"234324324324","webhook":"https://oapi.dingtalk.com/robot/send?access_token=8d03b68d081f732343243242343247328b0c3003d164715d2c6c6e56"}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '90',
          'mode': 'normal',
          'inline': true,
          'label': '钉钉工作消息样例：{\n    "appKey": "23423423",\n    "appSecret": "tQpvmkR863dYcuKDVfM23432432432423Nlx_fYLLLlpPJWHvWKbTu",\n    "agentId": "1523423423242"\n}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '110',
          'mode': 'normal',
          'inline': true,
          'label': '飞书机器人消息样例：{\n    "webhook": "https://open.feishu.cn/open-apis/bot/v2/hook/xxxx-9aa3-xxx-9239-xxxxxx"\n}'
        },
        {
          'type': 'static',
          'name': 'id',
          'visibleOn': '120',
          'mode': 'normal',
          'inline': true,
          'label': '支付宝小程序消息样例：{"privateKey":"MIIEvQIBADANB......","alipayPublicKey":"MIIBIjANBg...","appId":"2014********7148","userTemplateId":"MDI4YzIxMDE2M2I5YTQzYjUxNWE4MjA4NmU1MTIyYmM=","page":"page/component/index"}'
        }
      ]
    }
  },
  mounted() {
    this.search()
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

