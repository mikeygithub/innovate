<template>
  <el-dialog
    title="修改信息"
    :visible.sync="visible"
    :show-close=false
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :append-to-body="true">
    <el-row>
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="7rem">
        <el-form-item label="姓名" prop="sysUserEntity.name">
          <el-input  v-model="dataForm.sysUserEntity.name"></el-input>
        </el-form-item>
        <el-col :span="12">
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
        </el-col>
        <el-col :span="12">
          <el-form-item label="电子邮箱" prop="sysUserEntity.email">
            <el-input  v-model="dataForm.sysUserEntity.email"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
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
        </el-col>
        <el-col :span="24">
          <el-form-item label="学号" prop="perStuNo">
            <el-input v-model="dataForm.perStuNo" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :span="12">-->
          <!--<el-form-item label="学院" prop="sysUserEntity.instituteId">-->
            <!--<el-select v-model="dataForm.sysUserEntity.instituteId" placeholder="请选择">-->
              <!--<el-option-->
                <!--v-for="item in instituteList"-->
                <!--:key="item.instituteId"-->
                <!--:label="item.instituteName"-->
                <!--:value="item.instituteId">-->
              <!--</el-option>-->
            <!--</el-select>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="12">
          <el-form-item label="手机号" prop="sysUserEntity.mobile">
            <el-input  v-model="dataForm.sysUserEntity.mobile"></el-input>
          </el-form-item>
        </el-col>
        <!--start-->
        <el-col :span="12">
          <el-form-item label="政治面貌" prop="perPoliticsType">
            <el-select v-model="dataForm.perPoliticsType" placeholder="请选择">
              <el-option
                v-for="item in politicsList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="班级" prop="perClassNo">
            <el-input  v-model="dataForm.perClassNo"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="宿舍" prop="perCormNo">
            <el-input  v-model="dataForm.perCormNo"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业职务" prop="perPost">
            <el-input  v-model="dataForm.perPost"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="QQ" prop="perQq">
            <el-input  v-model="dataForm.perQq"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="在校职务" prop="perSchoolPost">
            <el-input  v-model="dataForm.perSchoolPost"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="所获荣誉" prop="perSchoolHonor">
            <el-input  v-model="dataForm.perSchoolHonor"></el-input>
          </el-form-item>
        </el-col>
<!--        <el-col :span="24">-->
<!--          <el-form-item label="身份证号" prop="perCardNo">-->
<!--            <el-input  v-model="dataForm.perCardNo"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :span="24">
          <el-form-item label="籍贯" prop="perNative">
            <el-input v-model="dataForm.perNative"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="社会实践经历" prop="perSocialPractice">
            <el-input v-model="dataForm.perSocialPractice"></el-input>
          </el-form-item>
        </el-col>
        <!--end-->
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <!--<el-button @click="visible = false">取消</el-button>-->
      <!--<el-button @click="noPerfectInfo()">取消</el-button>-->
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile, isEmail, isStuNum, isQQ } from '@/utils/validate'
  export default {
    name: 'student-add-or-update',
    data () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      var validateStuNum = (rule, value, callback) => {
        if (!isStuNum(value)) {
          callback(new Error('学号格式错误'))
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
      // var validateIdNo = (rule, value, callback) => {
      //   if (!isIdNo(value)) {
      //     callback(new Error('请输入正确的身份证号'))
      //   } else {
      //     callback()
      //   }
      // }
      return {
        visible: false,
        addLoading: false,
        politicsList: [{value: '群众', label: '群众'}, {value: '共青团员', label: '共青团员'}, {value: '中共党员', label: '中共党员'}],
        dataForm: {
          userPerId: '',
          userId: '',
          gradeId: '',
          perCardNo: '',
          perSex: '',
          perPost: '',
          perPoliticsType: '',
          perStuNo: '',
          perClassNo: '',
          perCormNo: '',
          perNative: '',
          perQq: '',
          perSchoolPost: '',
          perSchoolHonor: '',
          perSocialPractice: '',
          sysUserEntity: {
            name: '',
            email: '',
            instituteId: '',
            mobile: ''
          }
        },
        sexList: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
        gradeList: this.$store.state.user.grade,
        instituteList: this.$store.state.user.institute,
        dataRule: {
          'sysUserEntity.name': [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          'sysUserEntity.email': [
            { required: true, message: '邮箱不能为空', trigger: 'blur' },
            { validator: validateEmail, trigger: 'blur' }
          ],
          'sysUserEntity.mobile': [
            { required: true, message: '手机号不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ],
          perSex: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          perStuNo: [
            { required: true, message: '学号不能为空', trigger: 'blur' },
            { validator: validateStuNum, trigger: 'blur' }
          ],
          'sysUserEntity.instituteId': [
            { required: true, message: '学院不能为空', trigger: 'blur' }
          ],
          gradeId: [
            { required: true, message: '年级不能为空', trigger: 'blur' }
          ],
          // perCardNo: [
          //   { required: true, message: '身份证号不能为空', trigger: 'blur' },
          //   { validator: validateIdNo, trigger: 'blur' }
          // ],
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
            { required: true, message: 'QQ号不能为空', trigger: 'blur' },
            {validator: validateQQ, message: '请输入正确的QQ号'}
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
    computed: {
      name: {
        get () { return this.$store.state.user.name }
      },
      mainTabs: {
        get () { return this.$store.state.common.mainTabs },
        set (val) { this.$store.commit('common/updateMainTabs', val) }
      }
    },
    methods: {
      // 初始化
      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
            this.$http({
              url: this.$http.adornUrl(`/innovate/use/person/info/${this.$store.state.user.id}`),
              method: 'get',
              data: this.$http.adornData()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.userPerson
              } else {
                this.$message.error(data.msg)
              }
            })
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl('/innovate/use/person/update'),
              method: 'post',
              data: this.$http.adornData({
                'userPerId': this.dataForm.userPerId,
                'userId': this.$store.state.user.id,
                'instituteId': this.dataForm.instituteId,
                'gradeId': this.dataForm.gradeId,
                'perCardNo': this.dataForm.perCardNo,
                'perSex': this.dataForm.perSex,
                'perPost': this.dataForm.perPost,
                'perPoliticsType': this.dataForm.perPoliticsType,
                'perStuNo': this.dataForm.perStuNo,
                'perClassNo': this.dataForm.perClassNo,
                'perCormNo': this.dataForm.perCormNo,
                'perNative': this.dataForm.perNative,
                'perQq': this.dataForm.perQq,
                'perSchoolPost': this.dataForm.perSchoolPost,
                'perSchoolHonor': this.dataForm.perSchoolHonor,
                'perSocialPractice': this.dataForm.perSocialPractice,
                'sysUserEntity': this.dataForm.sysUserEntity
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.addLoading = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
                this.addLoading = false
              }
            })
          }
        })
      },
      // 取消完善个人信息
      noPerfectInfo () {
        this.$confirm('不完善个人信息，将无法正常进行大创申报，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.visible = false
        }).catch(() => {
          this.visible = true
          // this.$message({
          //   type: 'info',
          //   message: '已取消申请'
          // })
        })
      }
    }
  }
</script>

