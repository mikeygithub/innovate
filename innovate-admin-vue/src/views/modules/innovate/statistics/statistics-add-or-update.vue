<template>
  <el-dialog
    width="50%"
    @close="closeDialog"
    v-if="visible"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="12rem">
      <el-form-item label="项目选择" prop="projectId">
        <el-select v-model="dataForm.projectId"  placeholder="请选择">
          <el-option v-for="item in projectInfoEntities"
                     :key="item.projectId"
                     v-if="item.projectStatus"
                     :label="item.projectName"
                     :value="item.projectId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="本月投入资金（万元）" prop="reportInvestCapital">
        <el-input v-model="dataForm.reportInvestCapital" placeholder="请输入本月投入资金"></el-input>
      </el-form-item>
      <el-form-item label="本月利润总额（万元）" prop="reportProfits">
        <el-input v-model="dataForm.reportProfits" placeholder="请输入本月利润总额"></el-input>
      </el-form-item>
      <el-form-item prop="reportProfitsName">
        <label>
          证明材料（以附件形式上传相关证明材料的扫描件）
        </label>
        <el-upload
          class="upload-demo"
          :action="url"
          :data="{projectName: 'statistics1'}"
          :on-success="successHandle1">
          <el-button size="small" type="primary">点击上传</el-button>
          <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
        </el-upload>
      </el-form-item>
      <el-form-item label="本月销售额（万元）" prop="reportSales">
        <el-input v-model="dataForm.reportSales" placeholder="请输入本月销售额"></el-input>
      </el-form-item>
      <el-form-item prop="reportSalesName">
        <label>
          证明材料（以附件形式上传相关证明材料的扫描件）
        </label>
        <el-upload
          class="upload-demo"
          :action="url"
          :data="{projectName: 'statistics2'}"
          :on-success="successHandle2">
          <el-button size="small" type="primary">点击上传</el-button>
          <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
        </el-upload>
      </el-form-item>
      <el-form-item label="本月税金总额（万元）" prop="reportTax">
        <el-input v-model="dataForm.reportTax" placeholder="请输入本月税金总额"></el-input>
      </el-form-item>
      <el-form-item prop="reportTaxName">
        <label>
          证明材料（以附件形式上传相关证明材料的扫描件）
        </label>
        <el-upload
          class="upload-demo"
          :action="url"
          :data="{projectName: 'statistics3'}"
          :on-success="successHandle3">
          <el-button size="small" type="primary">点击上传</el-button>
          <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
        </el-upload>
      </el-form-item>
      <el-form-item label="报表月份" prop="reportTime">
        <el-date-picker
          v-model="dataForm.reportTime"
          type="month"
          format="yyyy-MM"
          value-format="timestamp"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
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
    name: 'statistics-add-or-update',
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
        roleList: [],
        projectInfoEntities: [],
        dataForm: {
          reportId: '',
          projectId: '',
          reportInvestCapital: '',
          reportProfits: '',
          reportProfitsName: '',
          reportProfitsPath: '',
          reportSales: '',
          reportSalesName: '',
          reportSalesPath: '',
          reportTax: '',
          reportTaxName: '',
          reportTaxPath: '',
          reportTime: '',
          isDel: 0
        },
        dataRule: {
          projectId: [
            { required: true, message: '项目选择不能为空', trigger: 'blur' }
          ],
          reportInvestCapital: [
            { required: true, message: '投入资金不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          reportProfits: [
            { required: true, message: '利润总额不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          reportProfitsName: [
            { required: true, message: '利润总额材料不能为空', trigger: 'blur' }
          ],
          reportSales: [
            { required: true, message: '销售额不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          reportSalesName: [
            { required: true, message: '销售额材料不能为空', trigger: 'blur' }
          ],
          reportTax: [
            { required: true, message: '税金总额不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          reportTaxName: [
            { required: true, message: '税金总额材料不能为空', trigger: 'blur' }
          ],
          reportTime: [
            { required: true, message: '报表月份不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.url = this.$http.adornUrl(`/innovate/match/attach/uploadfile?token=${this.$cookie.get('token')}`)
        this.dataForm.id = id || 0
        this.visible = true
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/all'),
          method: 'get',
          params: this.$http.adornParams({
            // 'apply': 'project_audit_apply_status',
            // 'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.projectInfoEntities = data.projectInfoEntities
          }
        })
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/project/report/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.monthlyReport
                this.dataForm.reportTime = new Date(this.dataForm.reportTime)
              }
            })
          }
        })
      },
      // 上传成功
      successHandle1 (response, file, fileList) {
        if (response && response.code === 0) {
          this.dataForm.reportProfitsName = file.name
          this.dataForm.reportProfitsPath = response.filePath
        } else {
          this.$message.error(response.msg)
        }
      },
      // 上传成功
      successHandle2 (response, file, fileList) {
        if (response && response.code === 0) {
          this.dataForm.reportSalesName = file.name
          this.dataForm.reportSalesPath = response.filePath
        } else {
          this.$message.error(response.msg)
        }
      },
      // 上传成功
      successHandle3 (response, file, fileList) {
        if (response && response.code === 0) {
          this.dataForm.reportTaxName = file.name
          this.dataForm.reportTaxPath = response.filePath
        } else {
          this.$message.error(response.msg)
        }
      },

      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.dataForm.reportTime = Number(this.dataForm.reportTime)
            this.$http({
              url: this.$http.adornUrl(`/innovate/project/report/${!this.dataForm.reportId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData(this.dataForm)
            }).then(({data}) => {
              if (data && data.code === 0) {
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
