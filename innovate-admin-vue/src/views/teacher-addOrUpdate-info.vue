<template>
  <el-dialog
    title="修改信息"
    :visible.sync="visible"
    :show-close=false
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :append-to-body="true">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="姓名" prop="sysUserEntity.name">
        <el-input  v-model="dataForm.sysUserEntity.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="teacherSex">
        <el-select v-model="dataForm.teacherSex" placeholder="请选择">
          <el-option
            v-for="item in sexList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
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
      <el-form-item label="职务" prop="teacherPost">
        <el-input  v-model="dataForm.teacherPost"></el-input>
      </el-form-item>
      <el-form-item label="职称" prop="teacherTitle">
        <el-select
          v-model="dataForm.teacherTitle"
          filterable
          remote
          reserve-keyword
          placeholder="请输入关键词"
          :remote-method="selectTitle"
          :loading="loadingTitle">
          <el-option
            v-for="item in teacherTitleList"
            :key="item.titleId"
            :label="item.titleName"
            :value="item.titleId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电子邮箱" prop="sysUserEntity.email">
        <el-input  v-model="dataForm.sysUserEntity.email"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="sysUserEntity.mobile">
        <el-input  v-model="dataForm.sysUserEntity.mobile"></el-input>
      </el-form-item>
<!--      <el-form-item label="身份证号" prop="teacherCardNo">-->
<!--        <el-input  v-model="dataForm.teacherCardNo"></el-input>-->
<!--      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <!--<el-button @click="visible = false">取消</el-button>-->
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile, isEmail } from '@/utils/validate'
  export default {
    name: 'teacher-add-or-update',
    data () {
      // var validateIdNo = (rule, value, callback) => {
      //   if (!isIdNo(value)) {
      //     callback(new Error('请输入正确的身份证号'))
      //   } else {
      //     callback()
      //   }
      // }
      var validateEmail = (rule, value, callback) => {
        if (!isEmail(value)) {
          callback(new Error('邮箱格式错误'))
        } else {
          callback()
        }
      }
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        addLoading: false,
        loadingTitle: false,
        dataForm: {
          userTeacherId: '',
          userId: '',
          teacherCardNo: '',
          teacherSex: '',
          teacherPost: '',
          teacherTitle: '',
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
        teacherTitleList: this.$store.state.user.title,
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
          'sysUserEntity.instituteId': [
            { required: true, message: '学院不能为空', trigger: 'blur' }
          ],
          // teacherCardNo: [
          //   { required: true, message: '不能为空', trigger: 'blur' },
          //   { validator: validateIdNo, trigger: 'blur' }
          // ],
          teacherSex: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          teacherPost: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          teacherTitle: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
          this.$http({
            url: this.$http.adornUrl(`/innovate/use/teacher/info/${this.$store.state.user.id}`),
            method: 'get',
            data: this.$http.adornData()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.userTeacher
              this.instituteList = data.institute
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl('/innovate/use/teacher/update'),
              method: 'post',
              data: this.$http.adornData({
                'userTeacherId': this.dataForm.userTeacherId,
                'userId': this.$store.state.user.id,
                'instituteId': this.dataForm.instituteId,
                'teacherCardNo': this.dataForm.teacherCardNo,
                'teacherSex': this.dataForm.teacherSex,
                'teacherPost': this.dataForm.teacherPost,
                'teacherTitle': this.dataForm.teacherTitle,
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
                this.addLoading = false
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
      selectTitle (query) {
        if (query !== '') {
          this.loadingTitle = true
          setTimeout(() => {
            this.loadingTitle = false
            console.info(query)
            this.$http({
              url: this.$http.adornUrl(`/innovate/sys/title/title`),
              method: 'get',
              params: this.$http.adornParams({
                'like': query
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.teacherTitleList = data.innovateTitleEntities
              }
            })
          }, 200)
        } else {
          this.teacherTitleList = []
        }
      }
    }
  }
</script>

