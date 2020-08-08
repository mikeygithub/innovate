<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem">
      <el-form-item label="人员费用（元）" prop="personCost">
        <el-input v-model="dataForm.personCost" placeholder="请输入金额"></el-input>
      </el-form-item>
      <el-form-item label="管理费用（元）" prop="manageCost">
        <el-input v-model="dataForm.manageCost" placeholder="请输入金额"></el-input>
      </el-form-item>
      <el-form-item label="场地费用（元）" prop="areaCost">
        <el-input v-model="dataForm.areaCost" placeholder="请输入金额"></el-input>
      </el-form-item>
      <el-form-item label="其它费用（元）" prop="anotherCost">
        <el-input v-model="dataForm.anotherCost" placeholder="请输入金额"></el-input>
      </el-form-item>
      <el-form-item label="开始时间" prop="costStartTime">
        <el-date-picker
          v-model="dataForm.costStartTime"
          type="datetime"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="timestamp"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="costEndTime">
        <el-date-picker
          v-model="dataForm.costEndTime"
          type="datetime"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="timestamp"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="所属中心" prop="baseId">
        <el-select v-model="dataForm.baseId" placeholder="请选择">
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
        dataForm: {
          id: 0,
          personCost: '',
          manageCost: '',
          anotherCost: '',
          areaCost: '',
          costStartTime: '',
          costEndTime: '',
          baseId: ''
        },
        dataRule: {
          personCost: [
            { required: true, message: '人员费用不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          manageCost: [
            { required: true, message: '管理费用不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          areaCost: [
            { required: true, message: '场地费用不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          anotherCost: [
            { required: true, message: '其它费用不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          costStartTime: [
            { required: true, message: '开始时间不能为空', trigger: 'blur' }
          ],
          costEndTime: [
            { required: true, message: '结束时间不能为空', trigger: 'blur' }
          ],
          baseId: [
            { required: true, message: '中心不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/innovate/base/cost/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.personCost = data.cost.personCost
                this.dataForm.manageCost = data.cost.manageCost
                this.dataForm.anotherCost = data.cost.anotherCost
                this.dataForm.areaCost = data.cost.areaCost
                this.dataForm.costStartTime = new Date(data.cost.costStartTime)
                this.dataForm.costEndTime = new Date(data.cost.costEndTime)
                this.dataForm.baseId = data.cost.baseId
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
              url: this.$http.adornUrl(`/innovate/base/cost/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'costId': this.dataForm.id || undefined,
                'personCost': this.dataForm.personCost,
                'manageCost': this.dataForm.manageCost,
                'anotherCost': this.dataForm.anotherCost,
                'areaCost': this.dataForm.areaCost,
                'costStartTime': Number(this.dataForm.costStartTime),
                'costEndTime': Number(this.dataForm.costEndTime),
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

