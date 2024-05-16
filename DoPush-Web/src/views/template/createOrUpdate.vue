<template>
  <div class="app-container">
    <el-page-header content="新增模板" @back="goBack" />
    <el-divider />
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-tag>基本配置</el-tag>
      <el-form-item label="模板名称" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="模板业务方" prop="proposer">
        <el-input v-model="form.proposer" />
      </el-form-item>
      <el-form-item label="接收者id类型" prop="idType">
        <el-radio-group v-model="form.idType">
          <el-radio v-for="item in idTypeOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="消息类型" prop="msgType">
        <el-radio-group v-model="form.msgType">
          <el-radio v-for="item in msgTypeOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="屏蔽类型" prop="shieldType">
        <el-radio-group v-model="form.shieldType">
          <el-radio v-for="item in shieldTypeOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="消息模板类型" prop="templateType">
        <el-radio-group v-model="form.templateType">
          <el-radio v-for="item in templateTypeOptions" :key="item.value" :label="item.value">{{ item.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.templateType === 10" label="corn表达式" prop="expectPushTime">
        <el-input v-model="form.expectPushTime" placeholder="需要【立即发送】时填0" />
        <p>1、 生成cron表达式页面：
          <a href="https://www.matools.com/cron" target="_blank">https://www.matools.com/cron</a>
          2、需要【立即发送】时填0
        </p>
      </el-form-item>
      <el-form-item v-if="form.templateType === 10" label="人群文件" prop="cronCrowPath">
        <el-radio-group v-if="form.templateType === 10" v-model="uploadOrReference">
          <el-radio v-for="item in crowFile" :key="item.value" :label="item.value">{{ item.label }}
          </el-radio>
        </el-radio-group>
        <el-upload
          v-if="form.templateType === 10 && uploadOrReference === 'upload'"
          ref="upload"
          class="upload-demo"
          action="#"
          :limit="1"
          :auto-upload="false"
          :on-exceed="handleExceed"
          :on-change="handleChange"
          :on-remove="handleRemove"
        >
          <div slot="tip" class="el-upload__tip">1、文件格式：csv文件 2、内容格式：列名userId,占位符变量1,占位符变量2</div>
          <el-button size="small" type="primary">选择文件</el-button>
          <el-button size="small" type="success" @click.stop="uploadCrowFile">上传到服务器</el-button>
        </el-upload>
        <div
          v-if="form.templateType === 10 && uploadOrReference === 'reference'"
        >
          <el-input v-model="materialId" placeholder="请输入素材id" />
          <el-button type="success" @click="getReference">确定引用</el-button>
        </div>
      </el-form-item>
      <el-form-item v-if="form.templateType === 10" label="人群上传路径" prop="cronCrowdPath">
        <el-input v-model="form.cronCrowdPath" placeholder="上传成功后自动编辑(无需填写)" disabled />
      </el-form-item>
      <el-tag>渠道配置</el-tag>
      <el-form-item label="发送渠道" prop="sendChannel">
        <el-radio-group v-model="form.sendChannel">
          <el-radio v-for="item in sendChannelOptions" :key="item.value" :label="item.value">{{ item.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <!--  =================push================= -->
      <div v-if="form.sendChannel === 20">
        <el-form-item label="Push账号" prop="sendAccount">
          <el-select
            v-model="form.sendAccount"
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
        <el-form-item label="发送标题" prop="title">
          <el-input v-model="form.title" placeholder="可用占位符${title}" />
        </el-form-item>
        <el-form-item label="发送内容" prop="content">
          <el-input v-model="form.content" placeholder="可用占位符${content}" />
        </el-form-item>
        <el-form-item label="发送链接" prop="url">
          <el-input v-model="form.url" placeholder="可用占位符${url}, 最好使用短链" />
        </el-form-item>
      </div>
      <!--  =================sms================= -->
      <div v-if="form.sendChannel === 30">
        <el-form-item label="短信账号" prop="sendAccount">
          <el-select
            v-model="form.sendAccount"
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
        <el-form-item label="短信内容" prop="content">
          <el-input v-model="form.content" placeholder="可用占位符${content}" />
        </el-form-item>
        <el-form-item label="短信链接" prop="url">
          <el-input v-model="form.url" placeholder="可用占位符${url}, 最好使用短链" />
        </el-form-item>
      </div>
      <!--  =================email================= -->
      <div v-if="form.sendChannel === 40">
        <el-form-item label="邮件账号" prop="sendAccount">
          <el-select
            v-model="form.sendAccount"
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
        <el-form-item label="邮件标题" prop="title">
          <el-input v-model="form.title" placeholder="可用占位符${title}" />
        </el-form-item>
        <el-form-item label="邮件内容" prop="content">
          <el-input v-model="form.content" placeholder="可用占位符${content}" />
        </el-form-item>
        <el-form-item label="邮件链接" prop="url">
          <el-input v-model="form.url" placeholder="可用占位符${url}, 最好使用短链" />
        </el-form-item>
      </div>
      <div v-if="form.sendChannel === 50">
        <span style="color: darkred">暂不支持该渠道</span>
      </div>
      <div v-if="form.sendChannel === 60">
        <span style="color: darkred">暂不支持该渠道</span>
      </div>
      <div v-if="form.sendChannel === 70">
        <span style="color: darkred">暂不支持该渠道</span>
      </div>
      <div v-if="form.sendChannel === 100">
        <span style="color: darkred">暂不支持该渠道</span>
      </div>
      <!--  =================dingTalkRobot================= -->
      <div v-if="form.sendChannel === 80">
        <el-form-item label="钉钉群机器人账号" prop="sendAccount">
          <el-select
            v-model="form.sendAccount"
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
        <el-form-item label="发送类型" prop="title">
          <el-radio-group v-model="form.dingTalkRobotSendType">
            <el-radio v-for="item in dingTalkRobotSendTypeOptions" :key="item.value" :label="item.label">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
      <!--  =================dingTalkApp================= -->
      <div v-if="form.sendChannel === 90">
        <el-form-item label="钉钉应用" prop="sendAccount">
          <el-select
            v-model="form.sendAccount"
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
        <el-form-item label="发送类型" prop="title">
          <el-radio-group v-model="form.dingTalkWorkSendType">
            <el-radio v-for="item in dingTalkWorkSendTypeOptions" :key="item.value" :label="item.label">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
      <!--  =================fei shu================= -->
      <div v-if="form.sendChannel === 110">
        <el-form-item label="飞书机器人" prop="sendAccount">
          <el-select
            v-model="form.sendAccount"
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
        <el-form-item label="飞书内容" prop="content">
          <el-input v-model="form.content" placeholder="可用占位符${content}" />
        </el-form-item>
      </div>
      <el-button
        @click="handelSaveOrUpdate"
      >Save</el-button>
    </el-form>
  </div>
</template>

<script>
import { query } from '@/api/channel'
import { save, upload } from '@/api/template'
import { reference } from '@/api/material'

export default {
  props: {
    // eslint-disable-next-line vue/require-default-prop
    update: Object,
    operable: Boolean
  },
  data() {
    return {
      isEdit: false,
      uploadOrReference: 'upload',
      materialId: '',
      form: {
        id: '',
        name: '',
        proposer: '',
        idType: '',
        msgType: '',
        shieldType: '',
        templateType: '',
        expectPushTime: '',
        file: '',
        cronCrowdPath: [],
        sendChannel: '',
        sendAccount: '',
        content: '',
        msgContent: '',
        url: '',
        title: '',
        templateId: '',
        linkType: '',
        miniProgramId: '',
        path: '',
        page: '',
        sendType: '',
        mediaId: '',
        dingTalkRobotSendType: '',
        dingTalkWorkSendType: ''
      },
      file: '',
      rules: {
        name: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        idType: [
          { required: true, message: 'idType', trigger: 'blur' }
        ],
        msgType: [
          { required: true, message: 'msgType', trigger: 'blur' }
        ],
        shieldType: [
          { required: true, message: 'shieldType', trigger: 'blur' }
        ],
        templateType: [
          { required: true, message: 'templateType', trigger: 'blur' }
        ],
        sendChannel: [
          { required: true, message: 'sendChannel', trigger: 'blur' }
        ],
        sendAccount: [
          { required: true, message: 'sendAccount', trigger: 'blur' }
        ],
        title: [
          { required: true, message: 'title', trigger: 'blur' }
        ],
        content: [
          { required: true, message: 'content', trigger: 'blur' }
        ]
        // ,
        // url: [
        //   { required: true, message: 'url', trigger: 'blur' }
        // ]
      },
      crowFile: [
        {
          label: '上传',
          value: 'upload'
        },
        {
          label: '引用素材',
          value: 'reference'
        }],
      idTypeOptions: [
        {
          label: '用户ID',
          value: 10
        },
        {
          label: '设备号',
          value: 20
        },
        {
          label: '手机号',
          value: 30
        },
        {
          label: 'openID',
          value: 40
        },
        {
          label: '邮箱地址',
          value: 50
        },
        {
          label: '企业微信userId',
          value: 60
        },
        {
          label: '钉钉userId',
          value: 70
        },
        {
          label: '推送通知栏cid',
          value: 80
        },
        {
          label: '飞书userId',
          value: 90
        }
      ],
      msgTypeOptions: [
        {
          label: '通知类',
          value: 10
        },
        {
          label: '营销类',
          value: 20
        },
        {
          label: '验证码',
          value: 30
        }
      ],
      shieldTypeOptions: [
        {
          label: '夜间不屏蔽',
          value: 10
        },
        {
          label: '夜间屏蔽',
          value: 20
        },
        {
          label: '夜间屏蔽(次日早上9点发送)',
          value: 30
        }
      ],
      templateTypeOptions: [
        {
          label: '定时',
          value: 10
        },
        {
          label: '实时',
          value: 20
        }
      ],
      sendChannelOptions: [
        {
          label: 'PUSH通知栏',
          value: 20
        },
        {
          label: '短信',
          value: 30
        },
        {
          label: '邮箱',
          value: 40
        },
        {
          label: '微信服务号（模板消息）',
          value: 50
        },
        {
          label: '微信小程序（订阅消息）',
          value: 60
        },
        {
          label: '企业微信（应用消息）',
          value: 70
        },
        {
          label: '企业微信（机器人）',
          value: 100
        },
        {
          label: '钉钉群机器人',
          value: 80
        },
        {
          label: '钉钉工作消息',
          value: 90
        },
        {
          label: '飞书机器人',
          value: 110
        }
      ],
      accountOptions: [],
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
      dingTalkWorkSendTypeOptions: [
        {
          'label': '文本(text)',
          'value': '10'
        },
        {
          'label': '语音(voice)',
          'value': '20'
        },
        {
          'label': '文件(file)',
          'value': '60'
        },
        {
          'label': 'markdown类型(markdown)',
          'value': '80'
        },
        {
          'label': '图片(image)',
          'value': '100'
        },
        {
          'label': '链接消息(link)',
          'value': '110'
        },
        {
          'label': '卡片消息(action_card)',
          'value': '120'
        },
        {
          'label': 'OA消息(oa)',
          'value': '130'
        }
      ]
    }
  },
  created() {
    if (this.update) {
      this.form = this.update
      console.log(this.form)
      this.isEdit = true
    }
  },
  methods: {
    goBack() {
      this.$router.back()
    },
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    getAvailableAccounts() {
      const accounts = []
      const channelType = this.form.sendChannel
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
    },
    handelSaveOrUpdate() {
      this.form.msgContent = {
        'title': this.form.title,
        'content': this.form.content,
        'url': this.form.url,
        'mediaId': this.form.mediaId
      }
      save(this.form).then(res => {
        console.log(res)
        this.$message({
          type: 'success',
          message: 'save successfully'
        })
        if (this.isEdit) {
          this.$bus.$emit('templateUpdate', true)
        }
        this.$router.push('/template/list-template')
      })
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
    uploadCrowFile() {
      upload(this.file).then(res => {
        console.log(res)
        if (res.status === '200') {
          const path = res.data.value
          console.log('crow path', path)
          if (path === null) {
            this.$message.error('上传失败, 未能保存')
            return
          }
          this.form.cronCrowdPath = path
          const h = this.$createElement
          this.$message({
            message: h('p', null, [
              h('span', null, '人群文件上传成功 '),
              h('i', { style: 'color: teal' }, path)
            ])
          })
        }
      }).catch(err => {
        console.log(err)
        this.$message.error('上传失败, 接口故障')
      })
    },
    getReference() {
      const _materialId = this.materialId
      if (!_materialId || _materialId === '') {
        this.$message.error('请输入素材id再进行引用')
      }
      reference(_materialId).then(res => {
        console.log(res.data)
        if (res.status === '200') {
          const material = res.data
          this.$message.success('成功引用到素材: ' + material.name)
          this.form.cronCrowdPath = material.path
        } else {
          this.$message.error(res.data)
        }
      }).catch(err => {
        console.log(err)
        this.$message.error('请求服务器失败, 请联系管理员')
      })
    }
  }
}
</script>

<style scoped>
</style>

