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
      <el-form-item label="资金来源单位部门" prop="subSource">
        <el-input type="textarea"
                  :rows="3"
                  v-model="dataForm.subSource" placeholder="请输入来源"></el-input>
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
          label: '获得投资情况'
        }, {
          value: 2,
          label: '享受管理服务补助资金情况'
        }, {
          value: 3,
          label: '享受房租和宽带接入补助情况'
        }, {
          value: 4,
          label: '享受水电费补助情况'
        }, {
          value: 5,
          label: '享受一次性创业补贴情况'
        }, {
          value: 6,
          label: '享受吸纳就业困难群体一次性补助情况'
        }, {
          value: 7,
          label: '享受社会保险补贴情况'
        }, {
          value: 8,
          label: '获得创业担保贷款情况'
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
          this.dataForm.subId = ''
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
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
