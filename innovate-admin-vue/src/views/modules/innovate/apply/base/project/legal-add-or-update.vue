<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增企业法人代表信息' : '修改企业法人代表信息'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <!--<el-form-item label="学院" prop="instituteId">-->
        <!--<el-select v-model="dataForm.instituteId" placeholder="请选择">-->
          <!--<el-option-->
            <!--v-for="item in instituteList"-->
            <!--:key="item.instituteId"-->
            <!--:label="item.instituteName"-->
            <!--:value="item.instituteId">-->
          <!--</el-option>-->
        <!--</el-select>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="年级" prop="gradeId">-->
        <!--<el-select v-model="dataForm.gradeId" placeholder="请选择">-->
          <!--<el-option-->
            <!--v-for="item in gradeList"-->
            <!--:key="item.gradeId"-->
            <!--:label="item.gradeName"-->
            <!--:value="item.gradeId">-->
          <!--</el-option>-->
        <!--</el-select>-->
      <!--</el-form-item>-->
      <el-form-item label="姓名" prop="legalName">
        <el-input v-model="dataForm.legalName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="legalSex">
        <el-select v-model="dataForm.legalSex" placeholder="请选择">
          <el-option
            v-for="item in sexList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="政治面貌" prop="legalFace">
        <el-select v-model="dataForm.legalFace" placeholder="请选择">
          <el-option
            v-for="item in faceList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="身份证号" prop="legalCardNo">-->
<!--        <el-input  v-model="dataForm.legalCardNo" placeholder="请输入"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="籍贯" prop="legalNative">
        <el-input v-model="dataForm.legalNative" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="年级" prop="gradeId">
        <el-select v-model="dataForm.gradeId" placeholder="请选择">
          <el-option
            v-for="item in gradeList"
            :key="item.gradeId"
            :label="item.gradeName"
            :value="item.gradeId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在班级/单位" prop="legalClassNo">
        <el-input v-model="dataForm.legalClassNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="所在单位职务" prop="legalPost">
        <el-input v-model="dataForm.legalPost" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="legalTel">
        <el-input v-model="dataForm.legalTel" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="legalEmail">
        <el-input v-model="dataForm.legalEmail" placeholder="请输入"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile, isEmail } from '@/utils/validate'
  export default {
    name: 'legal-add-or-update',
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
      // var validateIdNo = (rule, value, callback) => {
      //   if (!isIdNo(value)) {
      //     callback(new Error('请输入正确的身份证号'))
      //   } else {
      //     callback()
      //   }
      // }
      return {
        visible: false,
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
        faceList: [{
          value: 1,
          label: '中共党员'
        }, {
          value: 2,
          label: '共青团员'
        }, {
          value: 3,
          label: '群众'
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
          projectId: '',
          gradeId: '',
          legalName: '',
          legalSex: '',
          legalCardNo: '',
          legalFace: '',
          legalNative: '',
          legalClassNo: '',
          legalPost: '',
          legalTel: '',
          legalEmail: '',
          isDel: 0
        },
        dataRule: {
          legalName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          legalSex: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          legalFace: [
            { required: true, message: '政治面貌不能为空', trigger: 'blur' }
          ],
          // legalCardNo: [
          //   { required: true, message: '身份证不能为空', trigger: 'blur' },
          //   { validator: validateIdNo, trigger: 'blur' }
          // ],
          legalNative: [
            { required: true, message: '籍贯不能为空', trigger: 'blur' }
          ],
          gradeId: [
            { required: true, message: '年级不能为空', trigger: 'blur' }
          ],
          legalClassNo: [
            { required: true, message: '所在班级/单位不能为空', trigger: 'blur' }
          ],
          legalPost: [
            { required: true, message: '所在单位职务', trigger: 'blur' }
          ],
          legalTel: [
            { required: true, message: '联系电话不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ],
          legalEmail: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' },
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
          this.dataForm.staffId = ''
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
