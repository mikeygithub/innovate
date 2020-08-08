<!--添加或者更新明细汇总表-->
<template>
  <el-dialog
    width="60%"
    @close="closeDialog"
    v-loading="dataListLoading"
    :title="!dataForm.id ? '新增专家评审明细汇总表' : '修改专家评审明细汇总表'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="20">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="16rem" style="width: 94%; margin: 0 auto">
        <el-col>
        <el-form-item label="二级学院名称" prop="instituteId">
          <el-select v-model="dataForm.instituteId" placeholder="请选择">
            <el-option
              v-for="item in instituteList"
              :key="item.instituteId"
              :label="item.instituteName"
              :value="item.instituteId">
            </el-option>
          </el-select>
        </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="学院电子邮箱" prop="perName">
            <el-input v-model="dataForm.instituteEmail" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :span="24">-->
          <!--<el-form-item label="申报类型" prop="finishType">-->
            <!--<el-select v-model="dataForm.finishType"  placeholder="请选择">-->
              <!--<el-option v-for="item in finishTypeList" :key="item.value" :label="item.label" :value="item.value">-->
              <!--</el-option>-->
            <!--</el-select>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="24">
          <el-form-item label="添加专家" prop="teacherLists">
            <el-button size="mini"
                       v-if="addVisible(teacherLists)"
                       type="primary" plain @click="addTeacher()">从系统中添加</el-button>
            <template v-for="(item,index) in teacherLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-for="user in userTeacherInfoEntities"
                        :key="user.userId"
                        v-if="user.userId === item.userId"
                        v-text="'姓名：' + user.name">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addTeacher()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addTeacher(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delTeacher(item, index)">删除</el-button>
              </el-col>
            </template>
            <span v-if="isTeacherInfoNullVisible" style="color: crimson">*请联系指导老师完善个人信息</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="添加校外人员" prop="staffInfoLists">
            <el-button size="mini"
                       v-if="addVisible(staffInfoLists)" type="primary" plain @click="addExpert()">添加</el-button>
            <template v-for="(item,index) in staffInfoLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-text="item.staffName">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addExpert()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addExpert(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delExpert(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="填表人" prop="perName">
            <el-input v-model="dataForm.water" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="填表人联系电话" prop="perName">
            <el-input v-model="dataForm.writerPhoneNumber" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    <teacher-add-or-update v-if="teacherAddOrUpdateVisible" ref="teacherAddOrUpdate" @refreshDataList="teacherRef"></teacher-add-or-update>
    <expert-add-or-update v-if="expertAddOrUpdateVisible" ref="expertAddOrUpdate" @refreshDataList="expertRef"></expert-add-or-update>
    </el-dialog>
</template>

<script>
  import { isMobile, isEmail } from '@/utils/validate'
  import TeacherAddOrUpdate from './teacher-add-or-update'
  import ExpertAddOrUpdate from './expert-add-or-update'
  export default {
    components: {
      ExpertAddOrUpdate,
      TeacherAddOrUpdate
    },
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
      var validateTeacher = (rule, value, callback) => {
        if (this.teacherLists.length === 0) {
          callback(new Error('指导老师信息不能为空'))
        } else {
          let notNull = false
          for (let index = 0; index < this.teacherLists.length; index++) {
            if (this.teacherLists[index].isDel === 0) {
              notNull = true
            }
          }
          if (notNull) {
            callback()
          } else {
            callback(new Error('指导老师信息不能为空'))
          }
        }
      }
      return {
        instituteList: this.$store.state.user.institute,
        writerPhoneNumber: '',
        writer: '',
        url: '',
        upLoadUrl: '',
        upLoadData: {},
        tables: [],
        fileList: [],
        teacherLists: [],
        personInfoList: [],
        attachLists: [],
        // awardLists: [],
        staffInfoLists: [],
        // eventLists: [],
        dataListLoading: false,
        teacherAddOrUpdateVisible: false,
        isTeacherInfoNullVisible: false,
        expertAddOrUpdateVisible: false,
        legalAddOrUpdateVisible: false,
        moneyAddOrUpdateVisible: false,
        stationVisible: false,
        visible: false,
        staffInfoVisible: false,
        leadVisible: false,
        ptStaffInfoVisible: false,
        projectTeaInfoVisible: false,
        stationList: [],
        userTeacherInfoEntities: [],
        statusList: [{value: 1, label: '是'}, {value: 2, label: '否'}],
        finishTypeList: [
          {value: 1, label: '创新训练项目'},
          {value: 2, label: '创业训练项目'},
          {value: 3, label: '创业实践项目'}
        ],
        dataForm: {
          finishId: '',
          writer: '',
          writerPhoneNumber: '',
          projectUserId: this.$store.state.user.id,
          finishName: '',
          finishType: '',
          finishExpect: '',
          projectFinishApplyStatus: 0,
          isUpdate: 0,
          isDel: 0
        },
        dataRule: {
          writer: [
            { required: true, message: '填写人姓名不能为空', trigger: 'blur' }
          ],
          writerPhoneNumber: [
            { required: true, message: '填写人联系电话不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ],
          instituteId: [
            { required: true, message: '学院不能为空', trigger: 'blur' }
          ],
          instituteEmail: [
            { required: true, message: '学院邮箱不能为空', trigger: 'blur' },
            { validator: validateEmail, trigger: 'blur' }
          ],
          teacherLists: [
            { validator: validateTeacher, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataListLoading = true
        this.dataForm.finishId = id || 0
        if (this.isAuth('innovate:station:list')) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/station/list`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.stationList = data.page.list
            }
          })
        }
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.userTeacherInfoEntities = data.sysTeacherEntities
            }
          })
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.finishId) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/finish/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'finishId': this.dataForm.finishId,
                'apply': 'project_finish_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.finishInfo.finishInfoEntity
                this.teacherLists = data.finishInfo.finishTeacherEntities
                this.personInfoList = []
                this.attachLists = data.finishInfo.finishAttachEntities
                this.staffInfoLists = data.finishInfo.finishStaffInfoEntities
                this.dataForm.finishNoPass = 0
                this.isTeacherInfoNull()
                this.dataListLoading = false
              }
            })
          } else {
            this.dataListLoading = false
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.finishId = this.dataForm.finishId || undefined
          if (valid) {
            if (this.dataForm.finishId) {
              this.dataForm.projectSetDate = Number(this.dataForm.projectSetDate)
              this.dataForm.projectInDate = Number(this.dataForm.projectInDate)
              this.dataForm.projectRegDate = Number(this.dataForm.projectRegDate)
            }
            this.dataForm.isUpdate = 0
            this.$http({
              url: this.$http.adornUrl(`/innovate/finish/info/${!this.dataForm.finishId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'finishInfoEntity': this.dataForm,
                'projectLegalInfoEntities': this.legalLists,
                'userPersonInfoEntities': this.personInfoList,
                'finishTeacherEntities': this.teacherLists,
                'finishAttachEntities': this.attachLists,
                'finishStaffInfoEntities': this.staffInfoLists
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
      },
      // 子组件的各类方法
      addVisible (list) {
        let isDelNum = 0
        let visible = false
        for (let index in list) {
          if (list.length > 0 && list[index].isDel === 1) {
            isDelNum++
          }
        }
        if (isDelNum === list.length) {
          visible = true
        }
        return visible
      },
      addTeacher (item, index) {
        this.teacherAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['teacherAddOrUpdate'].init(item, index + 1)
        })
        this.isTeacherInfoNull()
      },
      delTeacher: function (data, index) {
        data.isDel = 1
        this.teacherLists[index] = data
        this.isTeacherInfoNull()
      },
      teacherRef (data, index) {
        data.finishId = this.dataForm.finishId
        this.teacherAddOrUpdateVisible = false
        if (index) {
          this.teacherLists[index - 1] = data
        } else {
          this.teacherLists.push(data)
        }
        this.isTeacherInfoNull()
      },
      isTeacherInfoNull () {
        this.isTeacherInfoNullVisible = false
        var teacherNum = 0
        var teacherLength = 0
        for (let item in this.teacherLists) {
          for (let teacher in this.userTeacherInfoEntities) {
            if (this.teacherLists[item].userId !== null) {
              if (this.userTeacherInfoEntities[teacher].userId === this.teacherLists[item].userId && this.teacherLists[item].isDel !== 1) {
                teacherNum++
              }
            }
          }
          if (this.teacherLists[item].isDel !== 1) {
            teacherLength++
          }
        }
        if (teacherNum < teacherLength) {
          this.isTeacherInfoNullVisible = true
        }
      },
      expertRef (data, index) {
        data.finishId = this.dataForm.finishId
        this.personAddOrUpdateVisible = false
        if (index) {
          this.personInfoList[index - 1] = data
        } else {
          this.personInfoList.push(data)
        }
      },
      addAward (item, index) {
        this.awardAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['awardAddOrUpdate'].init(item, index + 1)
        })
      },
      delAward: function (data, index) {
        data.isDel = 1
        this.awardLists[index] = data
      },
      awardRef (data, index) {
        data.finishId = this.dataForm.finishId
        this.awardAddOrUpdateVisible = false
        if (index) {
          this.awardLists[index - 1] = data
        } else {
          this.awardLists.push(data)
        }
      },
      addExpert (item, index) {
        this.expertAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['expertAddOrUpdate'].init(item, index + 1)
        })
      },
      delExpert: function (data, index) {
        data.isDel = 1
        this.staffInfoLists[index] = data
      },
      staffRef (data, index) {
        data.finishId = this.dataForm.finishId
        this.staffAddOrUpdateVisible = false
        if (index) {
          this.staffInfoLists[index - 1] = data
        } else {
          this.staffInfoLists.push(data)
        }
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      }

    }
  }
</script>

