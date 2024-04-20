<template>
  <div class="dashboard-container">
    <div class="dashboard-text">{{ name }}</div>
    <div class="charts">
      <div id="template" />
      <div id="today" />
      <div id="this-month" />
      <div id="channel" />
    </div>
  </div>
</template>

<script>
// import { mapGetters } from 'vuex'
import * as echarts from 'echarts'

export default {
  name: 'Dashboard',
  data() {
    return {
      name: '消息推送平台-领域驱动设计、高扩展、高可用'
    }
  },
  mounted() {
    this.draw('template', null)
    this.draw('today', null)
  },
  methods: {
    draw(id, data) {
      const template = echarts.init(document.getElementById(id))
      const option = data || {
        title: {
          text: 'Referer of a Website',
          subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      option && template.setOption(option)
    }
  }
  // computed: {
  //   ...mapGetters([
  //     'name'
  //   ])
  // }
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
  #template, #channel, #today, #this-month {
    border: 0.05rem solid rgba(255, 255, 255, 0.42);
    box-shadow: 0 0 15px 15px rgba(0, 0, 0, 0.15);
    background: rgba(0, 0, 0, 0.02);
    width: 25rem;
    height: 21rem;
    margin: 1rem;
  }
}
</style>
