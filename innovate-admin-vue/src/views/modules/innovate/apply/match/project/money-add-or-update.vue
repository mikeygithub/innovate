<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增资金收入' : '修改资金收入'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="资金种类" prop="subType">
        <el-select v-model="dataForm.subType" placeholder="请选择">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="金额（万元）" prop="subMoney">
        <el-input v-model="dataForm.subMoney" placeholder="请输入金额"></el-input>
      </el-form-item>
      <el-form-item label="资金来源" prop="subSource">
        <el-input v-model="dataForm.subSource" placeholder="请输入来源"></el-input>
      </el-form-item>
      <el-form-item label="收入时间" prop="subTime">
        <el-date-picker
          v-model="dataForm.subTime"
          type="date"
          format="yyyy年MM月dd日"
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
    name: 'money-add-or-update',
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
        typeList: [{
          value: 1,
          label: '投资'
        }, {
          value: 2,
          label: '管理服务'
        }, {
          value: 3,
          label: '房租和宽带'
        }, {
          value: 4,
          label: '水电费'
        }, {
          value: 5,
          label: '一次性创业'
        }, {
          value: 6,
          label: '吸纳困难群体'
        }, {
          value: 7,
          label: '社会保险'
        }, {
          value: 8,
          label: '创业担保贷款'
        }],
        id: 0,
        dataForm: {
          subMoney: '',
          subSource: '',
          subType: '',
          subTime: '',
          projectId: '',
          isDel: 0
        },
        dataRule: {
          subType: [
            { required: true, message: '请选择资金种类', trigger: 'blur' }
          ],
          subMoney: [
            { required: true, message: '金额不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          subSource: [
            { required: true, message: '资金来源不能为空', trigger: 'blur' }
          ],
          subTime: [
            { required: true, message: '收入时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (item, index) {
        this.visible = true
        this.id = index || 0
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
            this.dataForm.subId = ''
            this.dataForm = JSON.parse(JSON.stringify(item))
            this.dataForm.subTime = new Date(this.dataForm.subTime)
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.visible = false
            this.dataForm.subTime = Number(this.dataForm.subTime)
            this.$emit('refreshDataList', this.dataForm, this.id)
          }
        })
      }
    }
  }
</script>
