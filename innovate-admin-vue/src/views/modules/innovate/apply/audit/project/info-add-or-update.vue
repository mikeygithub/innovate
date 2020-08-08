<template>
  <el-dialog
    width="60%"
    @close="closeDialog"
    v-loading="dataListLoading"
    :title="!dataForm.id ? '新增大创信息' : '修改大创信息'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="20">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="16rem" style="width: 94%; margin: 0 auto">
        <el-col :span="24">
          <el-form-item label="大创项目名称" prop="declareName">
            <el-input v-model="dataForm.declareName" placeholder="请输入大创项目名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="申报类型" prop="declareType">
            <el-select v-model="dataForm.declareType"  placeholder="请选择">
              <el-option v-for="item in declareTypeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="项目等级" prop="declareGrade">
            <el-select v-model="dataForm.declareGrade"  placeholder="请选择">
              <el-option v-for="item in declareGradeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="项目年限" prop="declareYear">
            <el-select v-model="dataForm.declareYear"  placeholder="请选择">
              <el-option v-for="item in declareYearList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col>
        <el-form-item label="所属专业类" prop="subjectId">
          <el-select v-model="dataForm.subjectId" placeholder="请选择">
            <el-option
              v-for="item in subjectList"
              :key="item.subjectId"
              :label="item.subjectName"
              :value="item.subjectId">
            </el-option>
          </el-select>
        </el-form-item>
        </el-col>
        <!--<el-col>-->
        <!--<el-form-item label="所在二级学院" prop="instituteId">-->
          <!--<el-select v-model="dataForm.instituteId" placeholder="请选择">-->
            <!--<el-option-->
              <!--v-for="item in instituteList"-->
              <!--:key="item.instituteId"-->
              <!--:label="item.instituteName"-->
              <!--:value="item.instituteId">-->
            <!--</el-option>-->
          <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="24">
          <el-form-item label="项目简介(200字之内)" prop="declareDescribe">

            <el-input type="textarea" maxlength="200"
                      :rows="5"
                      v-model="dataForm.declareDescribe"
                      placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="项目指导老师" prop="teacherLists">
            <el-button size="mini"
                       v-if="addVisible(teacherLists)"
                       type="primary" plain @click="addTeacher()">添加</el-button>
            <template v-for="(item,index) in teacherLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-for="user in userTeacherInfoEntities"
                        :key="user.userId"
                        v-if="user.userId === item.userId"
                        v-text="'姓名：' + user.sysUserEntity.name">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addTeacher()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addTeacher(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delTeacher(item, index)">删除</el-button>
              </el-col>
            </template>
            <span v-if="isTeacherInfoNullVisible" style="color: crimson">*请联系指导老师完善个人信息</span>
          </el-form-item>
          <!--<el-form-item label="指导老师" prop="teacherLists">-->
            <!--<el-button size="mini"-->
                       <!--v-if="addVisible(teacherLists)"-->
                       <!--type="primary" plain @click="addTeacher()">添加</el-button>-->
            <!--<template v-for="(item,index) in teacherLists" v-if="item.isDel !== 1">-->
              <!--<el-col :span="24">-->
                <!--<el-tag style="margin-right: 1rem"-->
                        <!--v-for="user in userTeacherInfoEntities"-->
                        <!--:key="user.userId"-->
                        <!--v-if="user.userId === item.userId"-->
                        <!--v-text="'姓名：' + user.sysUserEntity.name">-->
                <!--</el-tag>-->
                <!--<el-button size="mini" type="primary" plain @click="addTeacher()">添加</el-button>-->
                <!--<el-button size="mini" type="primary" plain @click="addTeacher(item, index)">修改</el-button>-->
                <!--<el-button size="mini" type="danger" @click="delTeacher(item, index)">删除</el-button>-->
              <!--</el-col>-->
            <!--</template>-->
            <!--<span v-if="isTeacherInfoNullVisible" style="color: crimson">*请联系指导老师完善个人信息</span>-->
          <!--</el-form-item>-->
        </el-col>
        <el-col :span="24">
          <el-form-item label="项目参与者信息" prop="staffInfoLists">
            <el-button size="mini"
                       v-if="addVisible(staffInfoLists)" type="primary" plain @click="addStaff()">添加</el-button>
            <template v-for="(item,index) in staffInfoLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-text="item.staffName">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addStaff()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addStaff(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delStaff(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <template>
            <el-form-item label="附件要求">
              <el-alert
                title="附件要求"
                :closable=false
                type="success"
                :description="fileAskContent">
              </el-alert>
            </el-form-item>
          </template>
        </el-col>
        <!--独立附件start-->
        <el-col :span="24">
        <el-form-item label="提交申报书">
          <el-upload
            class="upload-demo"
            :action="url"
            :data="{declareName: dataForm.declareName}"
            :on-remove="removeFileHandle"
            :on-success="successHandle1"
            :file-list="fileList">
            <el-button size="small" icon="el-icon-upload" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        </el-col>
        <!--独立附件end-->
        <!--项目参与学生信息表独立附件start-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="项目参与学生信息表" prop="reportSalesName">-->
            <!--<el-upload-->
              <!--class="upload-demo"-->
              <!--:action="url"-->
              <!--:data="{declareName: dataForm.declareName}"-->
              <!--:on-success="successHandle1">-->
              <!--<el-button size="small" type="primary">点击上传</el-button>-->
              <!--<label>-->
                <!--（以附件形式上传项目参与学生信息表的扫描件）-->
              <!--</label>-->
            <!--</el-upload>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--&lt;!&ndash;项目参与学生信息表独立附件end&ndash;&gt;-->
        <!--&lt;!&ndash;项目指导教师一览表独立附件start&ndash;&gt;-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="项目指导教师一览表" prop="reportSalesName">-->
            <!--<el-upload-->
              <!--class="upload-demo"-->
              <!--:action="url"-->
              <!--:data="{declareName: dataForm.declareName}"-->
              <!--:on-success="successHandle1">-->
              <!--<el-button size="small" type="primary">点击上传</el-button>-->
              <!--<label>-->
                <!--（以附件形式上传项目指导教师一览表的扫描件）-->
              <!--</label>-->
            <!--</el-upload>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--项目指导教师一览表独立附件end-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="附件列表" prop="attachLists">-->
            <!--<template v-for="(item,index) in attachLists" v-if="item.isDel !== 1">-->
              <!--<el-col :span="24">-->
                <!--<el-tag style="margin-right: 1rem"-->
                        <!--v-text="item.attachName">-->
                <!--</el-tag>-->
                <!--<el-button size="mini" type="danger" @click="delAttach(item, index)">删除</el-button>-->
              <!--</el-col>-->
            <!--</template>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="文件上传">-->
            <!--<el-upload-->
              <!--multiple-->
              <!--ref="upLoadFiles"-->
              <!--list-type="card"-->
              <!--:data="upLoadData"-->
              <!--:action="upLoadUrl"-->
              <!--:on-preview="upLoadPreview"-->
              <!--:on-remove="upLoadRemove"-->
              <!--:on-success="upLoadSuccess"-->
              <!--:on-change="upLoadChange"-->
              <!--:file-list="fileList"-->
              <!--:auto-upload="false">-->
              <!--<el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
              <!--<el-button style="margin-left: 10px;" size="small" type="success" @click="upLoadSubmit">添加到附件列表</el-button>-->
              <!--<div slot="tip" class="el-upload__tip">注意文件需要添加到附件列表后确定保存才能添加成功</div>-->
            <!--</el-upload>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
    <teacher-add-or-update v-if="teacherAddOrUpdateVisible" ref="teacherAddOrUpdate" @refreshDataList="teacherRef"></teacher-add-or-update>
    <person-add-or-update v-if="personAddOrUpdateVisible" ref="personAddOrUpdate" @refreshDataList="personRef"></person-add-or-update>
    <!--<award-add-or-update v-if="awardAddOrUpdateVisible" ref="awardAddOrUpdate" @refreshDataList="awardRef"></award-add-or-update>-->
    <staff-add-or-update v-if="staffAddOrUpdateVisible" ref="staffAddOrUpdate" @refreshDataList="staffRef"></staff-add-or-update>
  </el-dialog>
</template>

<script>
  import TeacherAddOrUpdate from './teacher-add-or-update'
  import StaffAddOrUpdate from './staff-add-or-update'

  class DeclareAttachment {
    constructor (file) {
      this.name = file.attachName
      this.url = file.attachPath
      this.file = file
    }
  }

  export default {
    components: {
      StaffAddOrUpdate, // 项目参与者
      TeacherAddOrUpdate
    },
    data () {
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
      var validateAttach = (rule, value, callback) => {
        if (this.attachLists.length === 0) {
          callback(new Error('附件不能为空'))
        } else {
          let notNull = false
          for (let index = 0; index < this.attachLists.length; index++) {
            if (this.attachLists[index].isDel === 0) {
              notNull = true
            }
          }
          if (notNull) {
            callback()
          } else {
            callback(new Error('附件不能为空'))
          }
        }
      }
      return {
        url: '',
        upLoadUrl: '',
        upLoadData: {},
        fileAskContent: '无',
        tables: [],
        fileList: [],
        teacherLists: [],
        personInfoList: [],
        attachLists: [],
        staffInfoLists: [],
        awardInfoLists: [],
        addLoading: false,
        dataListLoading: false,
        teacherAddOrUpdateVisible: false,
        isTeacherInfoNullVisible: false,
        personAddOrUpdateVisible: false,
        legalAddOrUpdateVisible: false,
        staffAddOrUpdateVisible: false,
        moneyAddOrUpdateVisible: false,
        stationVisible: false,
        visible: false,
        staffInfoVisible: false,
        leadVisible: false,
        ptStaffInfoVisible: false,
        projectTeaInfoVisible: false,
        // stationList: [],
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
        subjectList: this.$store.state.user.subject,
        instituteList: this.$store.state.user.institute, // 二级学院
        statusList: [{value: 1, label: '是'}, {value: 2, label: '否'}],
        declareTypeList: [
          {value: 1, label: '创新训练项目'},
          {value: 2, label: '创业训练项目'},
          {value: 3, label: '创业实践项目'}
        ],
        declareYearList: [
          {value: 1, label: '一年期'},
          {value: 2, label: '二年期'}
        ],
        declareGradeList: [
          {value: 1, label: '国家级'},
          {value: 2, label: '自治区级'}
        ],
        dataForm: {
          declareId: '',
          eventId: '',
          subjectId: '',
          projectUserId: this.$store.state.user.id,
          declareName: '',
          declareType: '',
          declareGrade: '',
          declareYear: '',
          // matchDescribe: '',
          // matchBrightSpot: '',
          // matchExpect: '',
          instituteId: this.$store.state.user.instituteId,
          projectAuditApplyStatus: 1,
          isUpdate: 0,
          isDel: 0
        },
        dataRule: {
          declareName: [
            { required: true, message: '大创项目名称不能为空', trigger: 'blur' }
          ],
          declareDescribe: [
            { required: true, message: '大创项目简介', trigger: 'blur' }
          ],
          // eventId: [
          //   { required: true, message: '请选择比赛赛事', trigger: 'blur' }
          // ],
          instituteId: [
            { required: true, message: '请选择所属二级学院', trigger: 'blur' }
          ],
          subjectId: [
            { required: true, message: '请选择所属学科', trigger: 'blur' }
          ],
          declareType: [
            { required: true, message: '请选择申报类型', trigger: 'blur' }
          ],
          declareYear: [
            { required: true, message: '请选择申报年限', trigger: 'blur' }
          ],
          declareGrade: [
            { required: true, message: '请选择申报等级', trigger: 'blur' }
          ],
          // matchDescribe: [
          //   { required: true, message: '项目概述不能为空', trigger: 'blur' }
          // ],
          // matchBrightSpot: [
          //   { required: true, message: '项目创新之处和亮点不能为空', trigger: 'blur' }
          // ],
          // matchExpect: [
          //   { required: true, message: '项目预期或已取得的成果不能为空', trigger: 'blur' }
          // ],
          attachLists: [
            { validator: validateAttach, trigger: 'blur' }
          ],
          teacherLists: [
            { validator: validateTeacher, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.url = this.$http.adornUrl(`/innovate/declare/attach/upload?token=${this.$cookie.get('token')}`)
        // this.eventLists = this.$store.state.eventLists
        this.visible = true
        this.dataListLoading = true
        this.dataForm.declareId = id || 0
        // if (this.isAuth('innovate:station:list')) {
        //   this.$http({
        //     url: this.$http.adornUrl(`/innovate/base/station/list`),
        //     method: 'get',
        //     params: this.$http.adornParams()
        //   }).then(({data}) => {
        //     if (data && data.code === 0) {
        //       this.stationList = data.page.list
        //     }
        //   })
        // }
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.declareId) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/declare/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'declareId': this.dataForm.declareId,
                'apply': 'project_audit_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.declareInfo.declareInfoEntity
                this.teacherLists = data.declareInfo.declareTeacherEntities
                this.personInfoList = data.declareInfo.userPersonInfoEntities
                this.staffInfoLists = data.declareInfo.declareStaffInfoEntities
                this.awardInfoLists = data.declareInfo.declareAwardEntities
                this.dataForm.auditNoPass = 0
                this.isTeacherInfoNull()
                this.attachLists = data.declareInfo.declareAttachEntities
                // 附件回显
                let tempDeclareAtta = []
                for (var i = 0; i < this.attachLists.length; i++) {
                  tempDeclareAtta.push(new DeclareAttachment(this.attachLists[i]))
                }
                this.fileList = tempDeclareAtta
                this.dataListLoading = false
              }
            })
          } else {
            this.dataListLoading = false
          }
          // 获取文件要求：类型=>1 大创,2 中期检查,3 赛事,4 结题
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl(`/innovate/sys/file/ask/query`),
            method: 'get',
            params: this.$http.adornParams({
              'fileAskType': 1,
              'fileAskTime': new Date().getFullYear()
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.fileAskContent = data.fileAsk == null ? '无' : data.fileAsk.fileAskContent
              this.dataListLoading = false
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.declareId = this.dataForm.declareId || undefined
          if (valid) {
            if (this.dataForm.declareId) {
              this.dataForm.projectSetDate = Number(this.dataForm.projectSetDate)
              this.dataForm.projectInDate = Number(this.dataForm.projectInDate)
              this.dataForm.projectRegDate = Number(this.dataForm.projectRegDate)
            }

            this.$confirm(`即将提交项目申请，提交后将不能再对申请信息进行修改，确认要提交吗？`, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.dataForm.isUpdate = 0
              this.addLoading = true
              this.$http({
                url: this.$http.adornUrl(`/innovate/declare/info/${!this.dataForm.declareId ? 'save' : 'update'}`),
                method: 'post',
                data: this.$http.adornData({
                  'declareInfoEntity': this.dataForm,
                  // 'projectLegalInfoEntities': this.legalLists,
                  'userPersonInfoEntities': this.personInfoList,
                  'declareTeacherEntities': this.teacherLists,
                  'declareAttachEntities': this.attachLists,
                  'declareStaffInfoEntities': this.staffInfoLists,
                  'declareAwardEntities': this.awardInfoLists
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
                  this.addLoading = true
                }
              })
            }).catch(() => {})
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
        data.declareId = this.dataForm.declareId
        this.teacherAddOrUpdateVisible = false
        if (index) {
          this.teacherLists[index - 1] = data
        } else {
          this.teacherLists.push(data)
        }
        this.isTeacherInfoNull()
      },
      // isTeacherInfoNull () {
      //   this.isTeacherInfoNullVisible = false
      //   var teacherNum = 0
      //   var teacherLength = 0
      //   for (let item in this.teacherLists) {
      //     for (let teacher in this.userTeacherInfoEntities) {
      //       if (this.teacherLists[item].userId !== null) {
      //         if (this.userTeacherInfoEntities[teacher].userId === this.teacherLists[item].userId && this.teacherLists[item].isDel !== 1) {
      //           teacherNum++
      //         }
      //       }
      //     }
      //     if (this.teacherLists[item].isDel !== 1) {
      //       teacherLength++
      //     }
      //   }
      //   if (teacherNum < teacherLength) {
      //     this.isTeacherInfoNullVisible = true
      //   }
      // },
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
      personRef (data, index) {
        data.declareId = this.dataForm.declareId
        this.personAddOrUpdateVisible = false
        if (index) {
          this.personInfoList[index - 1] = data
        } else {
          this.personInfoList.push(data)
        }
      },
      // delAttach: function (data, index) {
      //   if (data.isDel !== 1) {
      //     data.isDel = 1
      //     this.attachLists[index] = data
      //   } else {
      //     this.attachLists.splice(index, 1)
      //   }
      // },
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
        data.declareId = this.dataForm.declareId
        this.awardAddOrUpdateVisible = false
        if (index) {
          this.awardLists[index - 1] = data
        } else {
          this.awardLists.push(data)
        }
      },
      addStaff (item, index) {
        this.staffAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['staffAddOrUpdate'].init(item, index + 1)
        })
      },
      delStaff: function (data, index) {
        data.isDel = 1
        this.staffInfoLists[index] = data
      },
      staffRef (data, index) {
        data.declareId = this.dataForm.declareId
        this.staffAddOrUpdateVisible = false
        if (index) {
          this.staffInfoLists[index - 1] = data
        } else {
          this.staffInfoLists.push(data)
        }
      },

      upLoadSubmit () {
        this.$refs.upLoadFiles.submit()
      },
      upLoadChange () {
        this.upLoadData = {
          'declareName': this.dataForm.declareName,
          'token': this.$cookie.get('token')
        }
        this.upLoadUrl = this.$http.adornUrl(`/innovate/declare/attach/upload`)
      },
      upLoadRemove (file, fileList) {
        console.log(file, fileList)
      },
      upLoadPreview (file) {
        console.log(file)
      },
      upLoadSuccess (data) {
        if (data && data.code === 0) {
          this.attachLists.push(data.declareAttachEntity)
        } else {
          this.$message.error(data.msg)
        }
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      },
      // 文件移除
      removeFileHandle (file, fileList) {
        // 移除attachList中的附件
        let tempFileList = []
        for (var index = 0; index < this.attachLists.length; index++) {
          if (this.attachLists[index].attachName !== file.name) {
            tempFileList.push(this.attachLists[index])
          }
        }
        this.attachLists = tempFileList
      },
      // 上传成功
      successHandle1 (response, file, fileList) {
        if (response && response.code === 0) {
          this.attachLists.push(response.declareAttachEntity)
        } else {
          this.$message.error(response.msg)
        }
      }
    }
  }
</script>

