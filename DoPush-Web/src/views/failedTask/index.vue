<script>
import { transTimestampToDate } from '@/utils/date'
import { search } from '@/api/failTask'
export default {
  data() {
    return {
      originalData: [],
      tableData: [],
      keywords: '',
      pageSizes: [5, 10, 20],
      currentPage: 1,
      currentPerPage: 5,
      count: 0,
      selectedRows: [],
      columns: [
        {
          typeLine: 'selection'
        },
        {
          'name': 'messageId',
          'label': 'ID'
        },
        {
          'name': 'bizId',
          'label': '消息发送id'
        },
        {
          'name': 'messageTemplateId',
          'label': '消息模板id'
        },
        {
          'name': 'businessId',
          'label': '业务id'
        },
        {
          'name': 'receiver',
          'label': '接收者'
        },
        {
          'name': 'time',
          'label': '失败时间',
          'sortable': true
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
      const data = {}
      data.messageTemplateId = this.keywords
      data.page = this.currentPage
      data.perPage = this.currentPerPage
      search(data).then(res => {
        const rows = res.rows
        this.originData = rows
        console.log(rows)
        this.tableData = this.dataConvert(rows)
        // 解决原始数据被修改无法使用的问题, 采用序列化方式实现数据的深拷贝
        // this.tableData = this.dataConvert(JSON.parse(JSON.stringify(rows)))
        this.count = res.count
      }).catch(err => {
        console.log(err)
      })
      // console.log(this.keyword)
    },
    filterTag(value, row) {
      return row.sendChannel === value ||
        row.templateType === value ||
        row.msgType === value ||
        row.auditStatus === value
    },
    clearFilter() {
      this.$refs.table.clearFilter()
    },
    handleSelectionChange(val) {
      this.selectedRows = val
    },
    dataConvert(datas) {
      for (let i = 0; i < datas.length; i++) {
        const data = datas[i]
        console.log(data)
        data.createTime = transTimestampToDate(data.createTime)
        console.log(data.type)
      }
      return datas
    },
    handleSizeChange(val) {
      this.currentPerPage = val
      this.search()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.search()
    }
  }
}
</script>

<template>
  <div>
    <el-page-header content="失败任务信息" @back="goBack" />
    <el-divider />
    <el-input
      v-model="keywords"
      placeholder="请输入模板id进行查询"
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
      查询
    </el-button>
    <el-table
      ref="table"
      :data="tableData"
      style="width: 100%"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        v-for="item in columns"
        :key="item.name"
        :prop="item.name"
        :label="item.label"
        :type="item.typeLine"
        :filters="item.filters"
        :filter-method="item.filters ? filterTag : undefined"
      />
    </el-table>
    <el-pagination
      :current-page="currentPage"
      :page-sizes="pageSizes"
      :page-size="currentPerPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="count"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<style scoped>

</style>
