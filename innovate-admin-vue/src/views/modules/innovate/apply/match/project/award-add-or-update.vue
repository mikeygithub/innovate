<template>
  <el-dialog
    append-to-body
    :title="!id ? '所获成果/奖项' : '所获成果/奖项'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="奖项名称" prop="awardName">
        <el-input v-model="dataForm.awardName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="获奖等级" prop="awardType">
        <el-select v-model="dataForm.awardType" placeholder="请选择">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="奖项等级" prop="awardRank">
        <el-select v-model="dataForm.awardRank" placeholder="请选择">
          <el-option
            v-for="item in rankList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="获奖时间" prop="awardTime">
        <el-date-picker
          v-model="dataForm.awardTime"
          type="date"
          format="yyyy年MM月dd日"
          value-format="timestamp"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="所获奖金（万元）" prop="awardMoney">
        <el-input v-model="dataForm.awardMoney" placeholder="请输入"></el-input>
      </el-form-item>
      <el-upload
        drag
        :action="url"
        :on-success="successHandle"
        multiple
        :data="awardFileName"
        :file-list="fileList"
        style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将证书文件拖到此处，或<em>点击上传</em></div>
        <!--<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>-->
      </el-upload>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isFloatNumber } from '@/utils/validate'
  export default {
    name: 'award-add-or-update',
    data () {
      var validateFloatNumber = (rule, value, callback) => {
        if (!isFloatNumber(value)) {
          callback(new Error('至少保留小数点后一位'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        url: '',
        awardFileName: {
          matchName: ''
        },
        typeList: [{
          value: 1,
          label: '国际级'
        }, {
          value: 2,
          label: '国家级'
        }, {
          value: 3,
          label: '省级'
        }, {
          value: 4,
          label: '市厅级'
        }, {
          value: 5,
          label: '县局级'
        }, {
          value: 6,
          label: '校级'
        }],
        rankList: [{
          value: 1,
          label: '特等奖'
        }, {
          value: 2,
          label: '一等奖'
        }, {
          value: 3,
          label: '二等奖'
        }, {
          value: 4,
          label: '三等奖'
        }, {
          value: 5,
          label: '优秀奖'
        }, {
          value: 6,
          label: '金奖'
        }, {
          value: 7,
          label: '银奖'
        }, {
          value: 8,
          label: '铜奖'
        }, {
          value: 9,
          label: '第一名'
        }, {
          value: 10,
          label: '第二名'
        }, {
          value: 11,
          label: '第三名'
        }, {
          value: 12,
          label: '第四名'
        }, {
          value: 13,
          label: '第五名'
        }, {
          value: 14,
          label: '其他'
        }],
        id: 0,
        fileList: [],
        matchInfoEntity: {
          matchAwardEntities: []
        },
        dataForm: {
          awardId: '',
          matchId: '',
          awardName: '',
          awardType: '',
          awardRank: '',
          awardMoney: '',
          awardFileName: '',
          awardPath: '',
          awardTime: '',
          isPrize: '',
          isDel: 0
        },
        dataRule: {
          awardType: [
            { required: true, message: '请选择奖金类型', trigger: 'blur' }
          ],
          awardName: [
            { required: true, message: '奖项名称不能为空', trigger: 'blur' }
          ],
          awardRank: [
            { required: true, message: '奖项等级不能为空', trigger: 'blur' }
          ],
          awardTime: [
            { required: true, message: '获奖时间不能为空', trigger: 'blur' }
          ],
          awardMoney: [
            { required: true, message: '所获奖金不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
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
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'matchId': this.id,
                'apply': 'project_match_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.matchInfoEntity = data.matchInfo
                this.awardFileName.matchName = this.matchInfoEntity.matchInfoEntity.matchName + '/award'
                if (this.matchInfoEntity.matchAwardEntities.length > 0) {
                  this.dataForm = this.matchInfoEntity.matchAwardEntities[0]
                }
                // this.dataForm.awardTime = new Date(this.dataForm.awardTime)
              }
            })
          }
        })
      },
      // 上传之前
      beforeUploadHandle (file) {
        if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
          this.$message.error('只支持jpg、png、gif格式的图片！')
          return false
        }
        this.num++
      },
      // 上传成功
      successHandle (response, file, fileList) {
        if (response && response.code === 0) {
          this.dataForm.awardFileName = file.name
          this.dataForm.awardPath = response.matchAttachEntity.attachPath
        } else {
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.matchId = this.id || undefined
          if (valid) {
            this.dataForm.awardTime = Number(this.dataForm.awardTime)
            this.matchInfoEntity.matchAwardEntities.push(this.dataForm)
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/info/award`),
              method: 'post',
              data: this.$http.adornData(this.matchInfoEntity)
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$http({
                  url: this.$http.adornUrl('/innovate/match/apply/apply'),
                  method: 'post',
                  params: this.$http.adornParams({
                    'matchId': this.id,
                    'apply': 'project_match_apply_status',
                    'roleId': 5
                  }, false)
                })
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
