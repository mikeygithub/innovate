<!--管理员账号创建比赛报名人口，命名赛事-->
<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="赛事名称" prop="eventName">
        <el-input v-model="dataForm.eventName" placeholder="请输入"></el-input>
      </el-form-item>

      <el-col>
        <el-form-item label="赛事上传文件要求" prop="fileAskContent">
          <el-input v-model="dataForm.fileAskContent" placeholder="请输入赛事上传文件要求" type="textarea"></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="赛事评分规则" prop="reportSalesName">
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="url"
            :data="{matchName: dataForm.eventName}"
            :on-success="successHandle"
            :on-remove="fileRemoveHandler"
            :on-exceed="fileExceed"
            :limit="1"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-col>

      <!--<el-form-item label="获奖数量" prop="eventAwardNum">-->
        <!--<el-input v-model.number="dataForm.eventAwardNum" placeholder="请输入"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="项目团队数量" prop="eventProjectNum">-->
        <!--<el-input v-model.number="dataForm.eventProjectNum" placeholder="请输入"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="奖金数量（万元）" prop="eventAwardMoney">-->
        <!--<el-input v-model.number="dataForm.eventAwardMoney" placeholder="请输入"></el-input>-->
      <!--</el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  // import { isFloatNumber } from '@/utils/validate'
  class MatchAttachment {
    constructor (file) {
      this.name = file.attachName
      this.url = file.attachPath
      this.file = file
    }
  }

  export default {
    name: 'event-add-or-update',
    data () {
      // var validateFloatNumber = (rule, value, callback) => {
      //   if (!isFloatNumber(value)) {
      //     callback(new Error('至少保留小数点后一位'))
      //   } else {
      //     callback()
      //   }
      // }
      return {
        url: '',
        addLoading: false,
        visible: false,
        id: 0,
        fileList: [],
        attachLists: [],
        dataForm: {
          eventId: '',
          eventName: '',
          fileAskContent: '',
          attachName: '',
          attachPath: ''
        },
        dataRule: {
          eventName: [
            { required: true, message: '赛事名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (index) {
        this.url = this.$http.adornUrl(`/innovate/match/attach/upload?token=${this.$cookie.get('token')}`)
        this.visible = true
        this.id = index || 0
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$refs.upload.clearFiles()
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/event/info/` + index),
              method: 'get',
              params: this.$http.adornParams({
                'eventId': this.id,
                'apply': 'project_match_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.matchEventEntity
                if (data.matchEventEntity.attachName !== null) {
                  var tempFile = []
                  tempFile.push(new MatchAttachment(data.matchEventEntity))
                  this.fileList = tempFile
                }
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.eventId = this.id || undefined
          if (this.dataForm.attachPath === '' || this.dataForm.attachName === '') {
            this.$message.error('请上传评分规则')
            return
          }
          if (valid) {
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/event/${!this.dataForm.eventId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData(this.dataForm)
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.addLoading = false
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.addLoading = false
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
      // 上传成功
      successHandle (response, file, fileList) {
        if (response && response.code === 0) {
          this.dataForm.attachName = response.matchAttachEntity.attachName
          this.dataForm.attachPath = response.matchAttachEntity.attachPath
        } else {
          this.$message.error(response.msg)
        }
      },
      fileRemoveHandler (file, fileList) {
        this.dataForm.attachName = ''
        this.dataForm.attachPath = ''
      },
      fileExceed (files, fileList) {
        this.$message.error('文件超出个数限制')
      }
    }
  }
</script>
