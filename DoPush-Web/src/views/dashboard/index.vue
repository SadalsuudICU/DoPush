<template>
  <div class="dashboard-container">
    <div class="dashboard-text">{{ name }}</div>
    <div class="charts">
      <div id="auditTemplate" />
      <div id="msgStatusTemplate" />
      <div id="material" />
      <div id="account" />
    </div>
  </div>
</template>

<script>
// import { mapGetters } from 'vuex'
import * as echarts from 'echarts'
import { templateData } from '@/api/template'
import { accountData } from '@/api/channel'
import { materialData } from '@/api/material'

export default {
  name: 'Dashboard',
  data() {
    return {
      name: '消息推送平台-领域驱动设计、高扩展、高可用',
      auditTemplate: '',
      msgStatusTemplate: '',
      account: '',
      material: ''
    }
  },
  mounted() {
    this.templateData()
    this.accountData()
    this.materialData()
  },
  methods: {
    templateData() {
      templateData().then(res => {
        if (res.status === '200') {
          const { audit, msgStatus } = res.data
          // this.auditTemplate = this.getDrawOptions(this.convertData(audit))
          // this.msgStatusTemplate = this.getDrawOptions(this.convertData(msgStatus))
          const auditTemplate = echarts.init(document.getElementById('auditTemplate'))
          const msgStatusTemplate = echarts.init(document.getElementById('msgStatusTemplate'))
          const auditX = []
          const auditY = []
          for (const key in audit) {
            auditX.push(key)
            auditY.push(audit[key])
          }
          const msgStatusX = []
          const msgStatusY = []
          for (const key in msgStatus) {
            msgStatusX.push(key)
            msgStatusY.push(msgStatus[key])
          }
          const optionAudit = {
            title: {
              text: '审核状态模板分布'
            },
            tooltip: {},
            legend: {
              data: ['数量']
            },
            xAxis: {
              data: auditX,
              axisTick: {
                alignWithLabel: true // true：标签位于刻度线正下方；false：标签位于2个刻度线中间
              }
            },
            yAxis: {},
            series: [{
              name: '数量',
              type: 'bar',
              data: auditY
            }]
          }
          auditTemplate.setOption(optionAudit)

          const optionMsgStatus = {
            title: {
              text: '消息状态模板分布'
            },
            tooltip: {},
            legend: {
              data: ['数量']
            },
            xAxis: {
              data: msgStatusX,
              axisLabel: {
                interval: 0,
                formatter: function(value) {
                  return value.split('').join('\n')
                }
              }
            },
            yAxis: {},
            series: [{
              name: '数量',
              type: 'bar',
              data: msgStatusY
            }]
          }
          msgStatusTemplate.setOption(optionMsgStatus)
        }
      })
    },
    accountData() {
      accountData().then(res => {
        if (res.status === '200') {
          const data = res.data

          const account = echarts.init(document.getElementById('account'))
          const accountX = []
          const accountY = []
          console.log(accountX)
          console.log(accountY)
          for (const key in data) {
            accountX.push(key)
            accountY.push(data[key])
          }

          const optionAccount = {
            title: {
              text: '渠道账号分布'
            },
            tooltip: {},
            legend: {
              data: ['数量']
            },
            xAxis: {
              data: accountX,
              axisLabel: {
                interval: 0,
                rotate: 45
                // formatter: function(value) {
                //   return value.split('').join('\n')
                // }
              }
            },
            yAxis: {},
            series: [{
              name: '数量',
              type: 'bar',
              data: accountY
            }]
          }
          account.setOption(optionAccount)
        }
      })
    },
    materialData() {
      materialData().then(res => {
        if (res.status === '200') {
          const data = res.data

          const material = echarts.init(document.getElementById('material'))
          const materialX = []
          const materialY = []
          for (const key in data) {
            materialX.push(key)
            materialY.push(data[key])
          }

          const optionMaterial = {
            title: {
              text: '素材类型分布'
            },
            tooltip: {},
            legend: {
              data: ['数量']
            },
            xAxis: {
              data: materialX,
              axisLabel: {
                interval: 0,
                rotate: 45
              }
            },
            yAxis: {},
            series: [{
              name: '数量',
              type: 'bar',
              data: materialY
            }]
          }
          material.setOption(optionMaterial)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.charts {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  background: rgba(255, 255, 255, 0.45);
  #auditTemplate, #msgStatusTemplate, #material, #account {
    border: 0.05rem solid rgba(255, 255, 255, 0.42);
    box-shadow: 0 0 15px 15px rgba(0, 0, 0, 0.15);
    background: rgba(0, 0, 0, 0.02);
    width: 21rem;
    height: 18rem;
    margin: 1rem;
  }
}
</style>
