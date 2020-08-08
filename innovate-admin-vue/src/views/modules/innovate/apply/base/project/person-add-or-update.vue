<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="性别" prop="perSex">
        <el-select v-model="dataForm.perSex" placeholder="请选择">
          <el-option
            v-for="item in sexList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学院" prop="instituteId">
        <el-select v-model="dataForm.instituteId" placeholder="请选择">
          <el-option
            v-for="item in instituteList"
            :key="item.instituteId"
            :label="item.instituteName"
            :value="item.instituteId">
          </el-option>
        </el-select>
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
      <el-form-item label="姓名" prop="perName">
        <el-input v-model="dataForm.perName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="企业职务" prop="perPost">
        <el-input v-model="dataForm.perPost" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="政治面貌" prop="perPoliticsType">
        <el-input v-model="dataForm.perPoliticsType" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="学号" prop="perStuNo">
        <el-input v-model="dataForm.perStuNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="班级号" prop="perClassNo">
        <el-input v-model="dataForm.perClassNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="宿舍号" prop="perCormNo">
        <el-input v-model="dataForm.perCormNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="来源" prop="perNative">
        <el-input v-model="dataForm.perNative" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="perTel">
        <el-input v-model="dataForm.perTel" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="QQ" prop="perQq">
        <el-input v-model="dataForm.perQq" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="Email" prop="perEmail">
        <el-input v-model="dataForm.perEmail" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="在校职务" prop="perSchoolPost">
        <el-input v-model="dataForm.perSchoolPost" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="在校荣誉" prop="perSchoolHonor">
        <el-input v-model="dataForm.perSchoolHonor" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="社会实践" prop="perSocialPractice">
        <el-input v-model="dataForm.perSocialPractice" placeholder="请输入"></el-input>
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
    name: 'person-add-or-update',
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
          perSex: 0,
          instituteId: '',
          gradeId: '',
          perName: '',
          perPost: '',
          perPoliticsType: '',
          perStuNo: '',
          perClassNo: '',
          perCormNo: '',
          perNative: '',
          perTel: '',
          perQq: '',
          perEmail: '',
          perSchoolPost: '',
          perSchoolHonor: '',
          perSocialPractice: '',
          isDel: 0
        },
        dataRule: {
          perName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          perEmail: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' },
            { validator: isEmail, trigger: 'blur' }
          ],
          perTel: [
            { required: true, message: '原密码不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ],
          perSex: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          perStuNo: [
            { required: true, message: '学号不能为空', trigger: 'blur' },
            {type: 'number', message: '请输入正确的学号'}
          ],
          instituteId: [
            { required: true, message: '学院不能为空', trigger: 'blur' }
          ],
          gradeId: [
            { required: true, message: '年级不能为空', trigger: 'blur' }
          ],
          perClassNo: [
            { required: true, message: '班级不能为空', trigger: 'blur' }
          ],
          perCormNo: [
            { required: true, message: '宿舍不能为空', trigger: 'blur' }
          ],
          perPost: [
            { required: true, message: '企业职务不能为空', trigger: 'blur' }
          ],
          perNative: [
            { required: true, message: '籍贯不能为空', trigger: 'blur' }
          ],
          perPoliticsType: [
            { required: true, message: '政治面貌不能为空', trigger: 'blur' }
          ],
          perQq: [
            { required: true, message: '原密码不能为空', trigger: 'blur' },
            {type: 'number', message: '请输入正确的QQ号码'}
          ],
          perSchoolPost: [
            { required: true, message: '在校职务不能为空', trigger: 'blur' }
          ],
          perSchoolHonor: [
            { required: true, message: '在校荣誉不能为空', trigger: 'blur' }
          ],
          perSocialPractice: [
            { required: true, message: '社会实践不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (item, index) {
        this.visible = true
        this.id = index || 0
        this.$nextTick(() => {
          this.dataForm.legalId = ''
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
