<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增项目参与者信息' : '修改项目参与者信息'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem">
      <el-form-item label="姓名" prop="staffName">
        <el-input v-model="dataForm.staffName" placeholder="请输入"></el-input>
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
      <el-form-item label="学号" prop="staffStuNo">
        <el-input v-model="dataForm.staffStuNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="所在二级学院" prop="instituteId">
        <el-select v-model="dataForm.instituteId" placeholder="请选择">
          <el-option
            v-for="item in instituteList"
            :key="item.instituteId"
            :label="item.instituteName"
            :value="item.instituteId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在年级" prop="gradeId">
        <el-select v-model="dataForm.gradeId" placeholder="请选择">
          <el-option
            v-for="item in gradeList"
            :key="item.gradeId"
            :label="item.gradeName"
            :value="item.gradeId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在班级" prop="staffClassNo">
        <el-input v-model="dataForm.staffClassNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="所学专业" prop="staffCormNo">
        <el-input v-model="dataForm.staffCormNo" placeholder="请输入"></el-input>
      </el-form-item>
      <!--<el-form-item label="所在宿舍" prop="staffCormNo">-->
        <!--<el-input v-model="dataForm.staffCormNo" placeholder="请输入"></el-input>-->
      <!--</el-form-item>-->
      <el-form-item label="联系电话" prop="staffTel">
        <el-input v-model="dataForm.staffTel" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="QQ号" prop="staffQq">
        <el-input v-model="dataForm.staffQq" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="个人电子邮箱" prop="staffEmail">
        <el-input v-model="dataForm.staffEmail" placeholder="请输入"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile, isEmail, isQQ } from '@/utils/validate'
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
      var validateEmail = (rule, value, callback) => {
        if (!isEmail(value)) {
          callback(new Error('邮箱格式错误'))
        } else {
          callback()
        }
      }
      var validateQQ = (rule, value, callback) => {
        if (!isQQ(value)) {
          callback(new Error('QQ格式错误'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
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
          staffStuNo: '',
          instituteId: '',
          gradeId: '',
          staffClassNo: '',
          staffCormNo: '',
          staffTel: '',
          staffQq: '',
          staffEmail: '',
          isDel: 0
        },
        dataRule: {
          staffName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          staffSex: [
            { required: true, message: '请选择性别', trigger: 'blur' }
          ],
          staffStuNo: [
            { required: true, message: '学号不能为空', trigger: 'blur' }
          ],
          instituteId: [
            { required: true, message: '所在二级学院不能为空', trigger: 'blur' }
          ],
          gradeId: [
            { required: true, message: '所在年级不能为空', trigger: 'blur' }
          ],
          staffClassNo: [
            { required: true, message: '班级不能为空', trigger: 'blur' }
          ],
          staffCormNo: [
            { required: true, message: '所在宿舍不能为空', trigger: 'blur' }
          ],
          staffTel: [
            { required: true, message: '联系方式不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ],
          staffQq: [
            { required: true, message: 'QQ号不能为空', trigger: 'blur' },
            { validator: validateQQ, trigger: 'blur' }
          ],
          staffEmail: [
            { required: true, message: '个人电子邮箱不能为空', trigger: 'blur' },
            { validator: validateEmail, trigger: 'blur' }
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
            this.dataForm.staffId = ''
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
