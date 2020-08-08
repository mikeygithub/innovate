<template>
  <el-dialog
    append-to-body
    :title="!id ? '所获成果/奖项' : '所获成果/奖项'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="编号" prop="declareNum">
        <el-input v-model="dataForm.declareNum" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="奖金类型" prop="awardType">
        <el-select v-model="dataForm.awardType" placeholder="请选择">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所获总奖金（元）" prop="awardMoneyAll">
        <el-input v-model="dataForm.awardMoneyAll" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="所获区级奖金（元）" prop="awardMoneyDistrict">
        <el-input v-model="dataForm.awardMoneyDistrict" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="所获校级奖金（元）" prop="awardMoneySchool">
        <el-input v-model="dataForm.awardMoneySchool" placeholder="请输入"></el-input>
      </el-form-item>
      <!--<el-upload-->
        <!--drag-->
        <!--:action="url"-->
        <!--:on-success="successHandle"-->
        <!--multiple-->
        <!--:data="awardFileName"-->
        <!--:file-list="fileList"-->
        <!--style="text-align: center;">-->
        <!--<i class="el-icon-upload"></i>-->
        <!--<div class="el-upload__text">将证书文件拖到此处，或<em>点击上传</em></div>-->
        <!--&lt;!&ndash;<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>&ndash;&gt;-->
      <!--</el-upload>-->
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
        id: 0,
        fileList: [],
        matchInfoEntity: [],
        declareNum: '',
        dataForm: {
          awardId: '',
          declareId: '',
          awardType: '',
          awardMoneyAll: '',
          awardMoneySchool: '',
          awardMoneyDistrict: '',
          isPrize: '',
          isDel: 0
        },
        dataRule: {
          awardType: [
            { required: true, message: '请选择奖金类型', trigger: 'blur' }
          ],
          declareNum: [
            { required: true, message: '请输入编号', trigger: 'blur' }
          ],
          awardMoneyAll: [
            { required: true, message: '不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          awardMoneySchool: [
            { required: true, message: '不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          awardMoneyDistrict: [
            { required: true, message: '不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (index) {
        // this.url = this.$http.adornUrl(`/innovate/declare/attach/uploadfile?token=${this.$cookie.get('token')}`)
        this.visible = true
        this.id = index || 0
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/declare/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'declareId': this.id,
                'apply': 'project_audit_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.declareInfoEntity = data.declareInfo
                if (this.declareInfoEntity.declareAwardEntities.length > 0) {
                  this.dataForm = this.declareInfoEntity.declareAwardEntities[0]
                }
                // this.dataForm.awardTime = new Date(this.dataForm.awardTime)
              }
            })
          }
        })
      },
      // // 上传之前
      // beforeUploadHandle (file) {
      //   if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
      //     this.$message.error('只支持jpg、png、gif格式的图片！')
      //     return false
      //   }
      //   this.num++
      // },
      // // 上传成功
      // successHandle (response, file, fileList) {
      //   console.info(response)
      //   console.info(file)
      //   if (response && response.code === 0) {
      //     this.dataForm.awardFileName = file.name
      //     this.dataForm.awardPath = response.filePath
      //   } else {
      //     this.$message.error(response.msg)
      //   }
      // },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.declareId = this.id || undefined
          if (valid) {
            this.dataForm.awardTime = Number(this.dataForm.awardTime)
            this.declareInfoEntity.declareAwardEntities.push(this.dataForm)
            this.declareInfoEntity.declareInfoEntity.declareNum = this.declareNum
            this.$http({
              url: this.$http.adornUrl(`/innovate/declare/info/update`),
              method: 'post',
              data: this.$http.adornData(this.declareInfoEntity)
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$http({
                  url: this.$http.adornUrl('/innovate/declare/apply/apply'),
                  method: 'post',
                  params: this.$http.adornParams({
                    'declareId': this.id,
                    'apply': 'project_audit_apply_status',
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
