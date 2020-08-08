<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="姓名" prop="staffName">
        <el-input v-model="dataForm.staffName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="员工类别" prop="staffType">
        <el-select v-model="dataForm.staffType" placeholder="请选择">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="staffSex">
        <el-select v-model="dataForm.staffSex" placeholder="请选择">
          <el-option
            v-for="item in sexList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="staffClassNo">
        <el-input v-model="dataForm.staffClassNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="职称" prop="staffPost">
        <el-input v-model="dataForm.staffPost" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="staffTel">
        <el-input v-model="dataForm.staffTel" placeholder="请输入"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile } from '@/utils/validate'
  export default {
    name: 'staff-add-or-update',
    data () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        typeList: [{
          value: 1,
          label: '全职'
        }, {
          value: 2,
          label: '兼职'
        }],
        sexList: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
        id: 0,
        dataForm: {
          staffName: '',
          staffSex: '',
          staffClassNo: '',
          staffPost: '',
          staffTel: '',
          staffType: '',
          projectId: '',
          isDel: 0
        },
        dataRule: {
          staffName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          staffType: [
            { required: true, message: '请选择员工类别', trigger: 'blur' }
          ],
          staffSex: [
            { required: true, message: '请选择性别', trigger: 'blur' }
          ],
          staffClassNo: [
            { required: true, message: '班级不能为空', trigger: 'blur' }
          ],
          staffPost: [
            { required: true, message: '职称不能为空', trigger: 'blur' }
          ],
          staffTel: [
            { required: true, message: '联系方式不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
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
            this.dataForm = JSON.parse(JSON.stringify(item))
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.visible = false
            this.$emit('refreshDataList', this.dataForm, this.id)
          }
        })
      }
    }
  }
</script>
