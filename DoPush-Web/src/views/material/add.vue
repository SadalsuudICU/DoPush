<script>
import { query } from '@/api/channel'
import { uploadMaterial } from '@/api/material'

export default {
  data() {
    return {
      form: {
        name: '',
        creator: '',
        type: '',
        account: ''
      },
      msg: '',
      accountOptions: [],
      materailTypeOptions: [
        {
          'label': '图片',
          'value': '10'
        },
        {
          'label': '语音',
          'value': '20'
        },
        {
          'label': '视频',
          'value': '30'
        },
        {
          'label': '其他文件',
          'value': '40'
        }
      ],
      dingTalkRobotSendTypeOptions: [
        {
          'label': '文本',
          'value': '10'
        },
        {
          'label': '图文',
          'value': '40'
        },
        {
          'label': '文件',
          'value': '60'
        },
        {
          'label': 'markdown类型',
          'value': '80'
        },
        {
          'label': '图片',
          'value': '100'
        }
      ],
      file: ''
    }
  },
  computed: {
    tip() {
      return '文件类型不匹配 ' + this.msg + ' '
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    handleExceed(file, fileList) {
      console.log('out of size, change to', file)
      this.$refs.upload.clearFiles()
      const _file = file
      // _file.id = generateId()
      this.file = _file
      this.$refs.upload.handleStart(_file)
    },
    handleChange(file, fileList) {
      console.log('upload', file)
      this.file = file
    },
    handleRemove(file, fileList) {
      this.$refs.upload.clearFiles()
      console.log('remove file', file)
      this.file = ''
    },
    uploadFile() {
      // file, sendAccount, sendChannel, fileType, creator, name
      const name = this.form.name
      const fileType = this.form.type
      const sendAccount = this.form.account
      const sendChannel = 80
      const creator = creator || 'Sadalsuud'
      // if (!(name && name !== '' && fileType && sendAccount && sendChannel)) {
      if (!(name && name !== '' && fileType && this.file)) {
        console.log(name, fileType, sendChannel, sendAccount, creator, this.file.raw)
        this.$message.error('请完整填写表单')
        return
      }
      const formData = new FormData()
      formData.append('file', this.file.raw)
      formData.append('fileType', fileType)
      formData.append('name', name)
      formData.append('sendAccount', sendAccount)
      formData.append('sendChannel', sendChannel)
      formData.append('creator', creator)
      // TODO 文件类型检验
      uploadMaterial(formData).then(res => {
        console.log(res)
        if (res.status === '200') {
          console.log(res)
          const h = this.$createElement
          this.$message({
            message: h('p', null, [
              h('span', null, '文件上传成功 '),
              h('i', { style: 'color: teal' }, res.data.path)
            ])
          })
        }
      }).catch(err => {
        console.log(err)
        this.$message.error('上传失败, 接口故障')
      })
    },
    getAvailableAccounts() {
      const accounts = []
      // 固定为钉钉
      const channelType = 80
      const params = {
        'channelType': channelType,
        'creator': 'Sadalsuud'
      }
      query(params).then(res => {
        res.data.map(item => {
          accounts.push({
            'label': item.name,
            'value': item.id
          })
        })
        console.log(accounts)
        this.accountOptions = accounts
      })
    }
  }
}
</script>

<template>
  <div>
    <el-page-header content="新增素材" @back="goBack" />
    <el-divider />
    <el-form
      ref="form"
      label-width="120px"
      :model="form"
    >
      <el-tag>基本配置</el-tag>
      <el-form-item label="素材名称" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="素材类型" prop="type">
        <el-radio-group v-model="form.type">
          <el-radio v-for="item in materailTypeOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="发送渠道(非必须)" prop="sendAccount">
        <el-select
          v-model="form.account"
          placeholder="请选择"
          @focus="getAvailableAccounts"
        >
          <el-option
            v-for="item in accountOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-upload
          ref="upload"
          class="upload-demo"
          action="#"
          :limit="1"
          :auto-upload="false"
          :on-exceed="handleExceed"
          :on-change="handleChange"
          :on-remove="handleRemove"
        >
          <div slot="tip" class="el-upload__tip">
            <span v-show="msg && msg !== ''" style="font-size: 1rem;color: red">{{ tip }}</span>
            <span v-if="!file" style="font-size: 0.8rem;color: #ee1d93">请根据所选文件类型上传文件</span>
          </div>
          <el-button size="small" type="primary">选择文件</el-button>
          <el-button size="small" type="success" @click.stop="uploadFile">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>

</style>
