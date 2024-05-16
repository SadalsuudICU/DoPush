<script>
import { transTimestampToDate } from '@/utils/date'
import { batchDelete, queryMaterialList } from '@/api/material'

export default {
  data() {
    return {
      originalData: [],
      tableData: [],
      keywords: '',
      placeholder: '通过关键字搜索',
      pageSizes: [5, 10, 20],
      currentPage: 1,
      currentPerPage: 5,
      count: 0,
      selectedRows: [],
      typeOptions: [
        { label: 'image', value: 10 },
        { label: 'voice', value: 20 },
        { label: 'file', value: 30 },
        { label: 'video', value: 40 }
      ],
      columns: [
        {
          typeLine: 'selection'
        },
        {
          'name': 'id',
          'label': 'ID',
          'width': 20,
          'sortable': true
        },
        {
          'name': 'name',
          'label': '素材名称'
        },
        {
          'name': 'path',
          'label': '素材路径'
        },
        {
          'name': 'type',
          'label': '类型',
          filters: [
            { text: 'image', value: 'image' },
            { text: 'voice', value: 'voice' },
            { text: 'file', value: 'file' },
            { text: 'video', value: 'video' }
          ]
        },
        {
          'name': 'creator',
          'label': '创建者'
        },
        {
          'name': 'createTime',
          'label': '创建时间',
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
      data.keywords = this.keywords
      data.creator = 'Sadalsuud'
      data.page = this.currentPage
      data.perPage = this.currentPerPage
      queryMaterialList(data).then(res => {
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
        this.typeOptions.some(item => {
          if (item.value === data.type) {
            data.type = item.label
          }
        })
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
    },
    batchDelete() {
      const rows = this.selectedRows
      const ids = []
      rows.map(item => {
        ids.push(item.id)
      })
      const deleteIds = ids.join(',')
      batchDelete(deleteIds).then(res => {
        console.log(res)
        if (res.status === '200') {
          this.$message.success('delete materials successfully')
          this.search()
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <el-page-header content="素材市场" @back="goBack" />
    <el-divider />
    <el-input
      v-model="keywords"
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
    <el-button
      type="danger"
      style="float: right"
      @click="batchDelete"
    >
      批量删除
    </el-button>
    <p />
    <el-button
      type="primary"
      @click="clearFilter"
    >
      清除所有过滤器
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
      <!--      <el-table-column label="操作">-->
      <!--        <template v-slot="scope">-->
      <!--          <div class="btns">-->
      <!--            <el-button-->
      <!--              class="btn"-->
      <!--              size="danger"-->
      <!--              @click="alert(scope.id)"-->
      <!--            >删除</el-button>-->
      <!--          </div>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
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
