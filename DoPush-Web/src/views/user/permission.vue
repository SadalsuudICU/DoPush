<script>
import { transTimestampToDate } from '@/utils/date'
import { batchDelete, change, list } from '@/api/user'

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
      user: {
        id: 0,
        username: '',
        password: '',
        role: 0
      },
      updateTableVisible: false,
      columns: [
        {
          typeLine: 'selection'
        },
        {
          'name': 'id',
          'label': 'ID'
        },
        {
          'name': 'username',
          'label': '用户名'
        },
        {
          'name': 'role',
          'label': '角色'
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
      data.page = this.currentPage
      data.perPage = this.currentPerPage
      list(data).then(res => {
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
        let role = ''
        switch (data.role) {
          case 0:
            role = '用户管理'
            break
          case 1:
            role = '模板审核'
            break
          case 2:
            role = '业务管理'
            break
          default:
            role = 'unknown'
        }
        data.role = role
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
        this.$message.success('delete materials successfully')
        this.search()
      })
    },
    edit(scope) {
      this.user.username = scope.username
      this.user.id = scope.id
      this.user.role = scope.role
      this.updateTableVisible = true
      console.log(scope)
    },
    update() {
      const user = this.user
      if (!(user.id && user.role)) {
        this.$message.error('请输入')
        return
      }
      change(user.id, user.password, user.role).then(res => {
        if (res.status === '200') {
          this.$message.success('modifying user successfully')
          this.search()
          this.updateTableVisible = false
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <el-page-header content="用户管理" @back="goBack" />
    <el-divider />
    <el-input
      v-model="keywords"
      placeholder="输入用户名模糊查询"
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
        :type="item.typeLine"
        :prop="item.name"
        :label="item.label"
      />
      <el-table-column label="操作">
        <template v-slot="scope">
          <div class="btns">
            <el-button
              class="btn"
              @click="edit(scope.row)"
            >编辑</el-button>
          </div>
        </template>
      </el-table-column>
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

    <el-dialog title="用户信息修改" :visible.sync="updateTableVisible">
      <el-input v-model="user.username" placeholder="用户名" disabled />
      <p />
      <el-input v-model="user.password" placeholder="密码" />
      <p />
      <el-radio v-model="user.role" label="0">用户管理</el-radio>
      <el-radio v-model="user.role" label="1">模板审核</el-radio>
      <el-radio v-model="user.role" label="2">业务员</el-radio>
      <p />
      <el-button type="success" @click="update">保存</el-button>
    </el-dialog>
  </div>
</template>

<style scoped>
</style>
