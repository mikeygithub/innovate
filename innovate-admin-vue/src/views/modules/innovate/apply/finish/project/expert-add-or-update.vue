<!--添加专家或者更新-->
<template>
  <el-dialog
    append-to-body
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="专家姓名" prop="expertName">
        <el-input v-model="dataForm.expertName" placeholder="专家姓名"></el-input>
      </el-form-item>
      <el-form-item label="职称" prop="expertTitles">
        <el-input v-model="dataForm.expertTitles" placeholder="职称"></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="expertUnit">
        <el-input v-model="dataForm.expertUnit" placeholder="单位"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="expertPhone">
        <el-input v-model="dataForm.expertPhone" placeholder="联系电话"></el-input>
      </el-form-item>
<!--      <el-form-item label="身份证" prop="expertIdentity">-->
<!--        <el-input v-model="dataForm.expertIdentity" placeholder="身份证"></el-input>-->
<!--      </el-form-item>-->
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
    name: 'expert-add-or-update',
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
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
        id: 0,
        dataForm: {
          expertId: 0,
          expertName: '',
          expertTitles: '',
          expertUnit: '',
          expertPhone: '',
          expertIdentity: '',
          expertCollectId: '',
          isDel: 0
        },
        dataRule: {
          expertName: [
            { required: true, message: '专家姓名不能为空', trigger: 'blur' }
          ],
          expertTitles: [
            { required: true, message: '职称不能为空', trigger: 'blur' }
          ],
          expertUnit: [
            { required: true, message: '单位不能为空', trigger: 'blur' }
          ],
          expertPhone: [
            { required: true, message: '联系电话不能为空', trigger: 'blur' },
            {validator: validateMobile, trigger: 'blur'}
          ]
          // expertIdentity: [
          //   { required: true, message: '身份证不能为空', trigger: 'blur' }
          // ]

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
            // this.dataForm = JSON.parse(JSON.stringify(item))
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
