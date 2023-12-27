<template>
  <div class="app-container">
    <el-page-header content="新增模板" @back="goBack" />
    <el-divider />
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-tag>基本配置</el-tag>
      <el-form-item label="模板名称" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="模板业务放" prop="proposer">
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
        <el-upload
          v-if="form.templateType === 10"
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          multiple
          :limit="3"
          :file-list="form.cronCrowdPath"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">1、文件格式：csv文件 2、内容格式：列名userId,占位符变量1,占位符变量2</div>
        </el-upload>
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
      <div v-if="form.sendChannel === 20">
        <el-form-item label="Push账号" prop="sendAccount">
          <el-select v-model="form.sendAccount" placeholder="请选择">
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
      <div v-if="form.sendChannel === 30">
        <el-form-item label="短信账号" prop="sendAccount">
          <el-select v-model="form.sendAccount" placeholder="请选择">
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
      <div v-if="form.sendChannel === 40">
        <el-form-item label="邮件账号" prop="sendAccount">
          <el-select v-model="form.sendAccount" placeholder="请选择">
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
      <div v-if="form.sendChannel === 80">
        <el-form-item label="钉钉群机器人账号" prop="sendAccount">
          <el-select v-model="form.sendAccount" placeholder="请选择">
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
      <div v-if="form.sendChannel === 90">
        <el-form-item label="钉钉应用" prop="sendAccount">
          <el-select v-model="form.sendAccount" placeholder="请选择">
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
      <div v-if="form.sendChannel === 110">
        <el-form-item label="飞书机器人" prop="sendAccount">
          <el-select v-model="form.sendAccount" placeholder="请选择">
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
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
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
        ],
        url: [
          { required: true, message: 'url', trigger: 'blur' }
        ]
      },
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
    }
  }
}
</script>

<style scoped>
</style>

