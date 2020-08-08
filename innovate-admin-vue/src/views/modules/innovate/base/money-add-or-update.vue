<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="收入来源" prop="moneySource">
        <el-input v-model="dataForm.moneySource" placeholder="请输入收入来源"></el-input>
      </el-form-item>
      <el-form-item label="收入种类" prop="moneyType">
        <el-select v-model="dataForm.moneyType" placeholder="请选择收入种类">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收入金额（万元）" prop="moneyNum">
        <el-input v-model="dataForm.moneyNum" placeholder="请输入收入金额"></el-input>
      </el-form-item>
      <el-form-item label="收入时间" prop="moneyTime">
        <el-date-picker
          v-model="dataForm.moneyTime"
          type="datetime"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="timestamp"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="所属中心" prop="baseId">
        <el-select v-model="dataForm.baseId" placeholder="请选择所属中心">
          <el-option
            v-for="item in baseList"
            :key="item.baseId"
            :label="item.baseName"
            :value="item.baseId">
          </el-option>
        </el-select>
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
        baseList: [],
        typeList: [{
          value: 1,
          label: '服务收入'
        }, {
          value: 2,
          label: '投资收入'
        }, {
          value: 3,
          label: '房租及物业收入'
        }, {
          value: 4,
          label: '财政补贴'
        }, {
          value: 5,
          label: '其他'
        }],
        dataForm: {
          id: 0,
          moneyType: '',
          moneyNum: '',
          moneySource: '',
          moneyTime: '',
          baseId: ''
        },
        dataRule: {
          moneySource: [
            { required: true, message: '收入来源不能为空', trigger: 'blur' }
          ],
          moneyType: [
            { required: true, message: '收入种类不能为空', trigger: 'blur' }
          ],
          moneyTime: [
            { required: true, message: '收入时间不能为空', trigger: 'blur' }
          ],
          moneyNum: [
            { required: true, message: '收入金额不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          baseId: [
            { required: true, message: '请选择所属中心', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataForm.id = id || 0
        this.$http({
          url: this.$http.adornUrl(`/innovate/base/info/list`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.baseList = data.page.list
          }
        })
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/money/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.moneyType = data.baseMoney.moneyType
                this.dataForm.moneyNum = data.baseMoney.moneyNum
                this.dataForm.moneySource = data.baseMoney.moneySource
                this.dataForm.moneyTime = new Date(data.baseMoney.moneyTime)
                this.dataForm.baseId = data.baseMoney.baseId
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/money/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'moneyId': this.dataForm.id || undefined,
                'moneyType': this.dataForm.moneyType,
                'moneyNum': this.dataForm.moneyNum,
                'moneySource': this.dataForm.moneySource,
                'moneyTime': Number(this.dataForm.moneyTime),
                'baseId': this.dataForm.baseId
              })
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
