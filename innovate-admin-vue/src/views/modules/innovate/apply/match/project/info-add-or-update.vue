<template>
  <el-dialog
    width="60%"
    @close="closeDialog"
    v-loading="dataListLoading"
    :title="dataForm.matchId === 0 ? '新增比赛信息' : '修改比赛信息'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="20">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem" style="width: 94%; margin: 0 auto">
        <el-col :span="24">
          <el-form-item label="项目名称" prop="matchName">
            <el-input v-model="dataForm.matchName" placeholder="请输入企业项目名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="团队名称" prop="matchTeamName">
            <el-input v-model="dataForm.matchTeamName" placeholder="请输入团队名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="比赛赛事名" prop="eventId">
            <el-select v-model="dataForm.eventId"  placeholder="请选择" @change="fileAskContentHandler($event)" >
              <el-option v-for="item in eventLists" :key="item.eventId" :label="item.eventName" :value="item.eventId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="参赛作品类型" prop="matchType">
            <el-select v-model="dataForm.matchType"  placeholder="请选择">
              <el-option v-for="item in matchTypeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="参赛组别" prop="matchGroupType">
            <el-select v-model="dataForm.matchGroupType"  placeholder="请选择">
              <el-option v-for="item in matchGroupTypeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="项目简介(300字之内)" prop="matchDescribe">

            <el-input type="textarea" maxlength="400"
                      :rows="5"
                      v-model="dataForm.matchDescribe"
                      placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <!--<el-form-item label="项目创新之处和亮点" prop="matchBrightSpot">-->
            <el-form-item label="项目所获投资情况" prop="matchBrightSpot">
            <el-input type="textarea"
                      :rows="5"
                      v-model="dataForm.matchBrightSpot"
                      placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <!--<el-form-item label="项目预期或已取得的成果" prop="matchExpect">-->
          <el-form-item label="项目所获专利情况" prop="matchExpect">
            <el-input type="textarea"
                      :rows="5"
                      v-model="dataForm.matchExpect"
                      placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="指导老师" prop="teacherLists">
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
        <!--<el-col :span="24">-->
          <!--<el-form-item label="项目获奖情况" prop="awardLists">-->
            <!--<el-button size="mini"-->
                       <!--v-if="addVisible(awardLists)"-->
                       <!--type="primary" plain @click="addAward()">添加</el-button>-->
            <!--<template v-for="(item,index) in awardLists" v-if="item.isDel !== 1">-->
              <!--<el-col :span="24">-->
                <!--<template-->
                  <!--v-for="runk in awardRankList"-->
                  <!--v-if="item.awardRank === runk.value">-->
                  <!--<el-tag style="margin-right: 1rem"-->
                          <!--v-for="award in awardTypeList"-->
                          <!--:key="award.value"-->
                          <!--v-if="item.awardType === award.value"-->
                          <!--v-text="item.awardName + '：' + award.label + '（' + runk.label + '）'">-->
                  <!--</el-tag>-->
                <!--</template>-->
                <!--<el-button size="mini" type="primary" plain @click="addAward()">添加</el-button>-->
                <!--<el-button size="mini" type="primary" plain @click="addAward(item, index)">修改</el-button>-->
                <!--<el-button size="mini" type="danger" @click="delAward(item, index)">删除</el-button>-->
              <!--</el-col>-->
            <!--</template>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="24" style="padding-bottom: 2em">
          <template>
            <el-alert
              title="附件要求"
              type="success"
              :description="fileAskContent">
            </el-alert>
          </template>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文件上传">
            <el-upload
              multiple
              ref="upLoadFiles"
              list-type="card"
              :data="upLoadData"
              :action="url"
              :on-preview="upLoadPreview"
              :on-remove="upLoadRemove"
              :on-success="upLoadSuccess"
              :on-change="upLoadChange"
              :beforeUpload="beforeAvatarUpload"
              :file-list="fileList">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <!--<el-button style="margin-left: 10px;" size="small" type="success" @click="upLoadSubmit">添加到附件上传列表</el-button>-->
              <!--<div slot="tip" class="el-upload__tip" style="color: red">注意文件需要添加到附件列表后确定保存才能添加成功</div>-->
              <div slot="tip" class="el-upload__tip" style="color: red">上传文件大小需小于100M</div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
    <teacher-add-or-update v-if="teacherAddOrUpdateVisible" ref="teacherAddOrUpdate" @refreshDataList="teacherRef"></teacher-add-or-update>
    <person-add-or-update v-if="personAddOrUpdateVisible" ref="personAddOrUpdate" @refreshDataList="personRef"></person-add-or-update>
    <already-award-add-or-update v-if="awardAddOrUpdateVisible" ref="awardAddOrUpdate" @refreshDataList="awardRef"></already-award-add-or-update>
    <staff-add-or-update v-if="staffAddOrUpdateVisible" ref="staffAddOrUpdate" @refreshDataList="staffRef"></staff-add-or-update>
  </el-dialog>
</template>

<script>
  import TeacherAddOrUpdate from './teacher-add-or-update'
  import StaffAddOrUpdate from './staff-add-or-update'
  import AlreadyAwardAddOrUpdate from './already-award-add-or-update'

  class MatchAttachment {
    constructor (file) {
      this.name = file.attachName
      this.url = file.attachPath
      this.file = file
    }
  }

  export default {
    components: {
      StaffAddOrUpdate,
      TeacherAddOrUpdate,
      AlreadyAwardAddOrUpdate
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
      return {
        url: '',
        upLoadData: {},
        fileAskContent: '无',
        tables: [],
        fileList: [],
        teacherLists: [],
        personInfoList: [],
        attachLists: [],
        tempAttachLists: [],
        awardLists: [],
        staffInfoLists: [],
        eventLists: [],
        addLoading: false,
        dataListLoading: false,
        teacherAddOrUpdateVisible: false,
        isTeacherInfoNullVisible: false,
        personAddOrUpdateVisible: false,
        legalAddOrUpdateVisible: false,
        staffAddOrUpdateVisible: false,
        moneyAddOrUpdateVisible: false,
        awardAddOrUpdateVisible: false,
        stationVisible: false,
        visible: false,
        staffInfoVisible: false,
        leadVisible: false,
        ptStaffInfoVisible: false,
        projectTeaInfoVisible: false,
        stationList: [],
        userTeacherInfoEntities: [],
        statusList: [{value: 1, label: '是'}, {value: 2, label: '否'}],
        matchTypeList: [
          {value: 1, label: '论文、调研报告'}, {value: 2, label: '视频'},
          {value: 3, label: '设计作品'}, {value: 4, label: '创业计划书'},
          {value: 5, label: '创新创业实践项目'}
        ],
        matchGroupTypeList: [
          {value: 1, label: '创意组'}, {value: 2, label: '初创组'},
          {value: 3, label: '成长组'}, {value: 4, label: '师生共创组'},
          {value: 5, label: '"青年红色梦之旅"赛道'}
        ],
        awardTypeList: [
          {value: 1, abel: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        awardRankList: [
          {value: 1, label: '特等奖'}, {value: 2, label: '一等奖'},
          {value: 3, label: '二等奖'}, {value: 4, label: '三等奖'},
          {value: 5, label: '优秀奖'}, {value: 6, label: '金奖'},
          {value: 7, label: '银奖'}, {value: 8, label: '铜奖'},
          {value: 9, label: '第一名'}, {value: 10, label: '第二名'},
          {value: 11, label: '第三名'}, {value: 12, label: '第四名'},
          {value: 13, label: '第五名'}, {value: 14, label: '其他'}
        ],
        dataForm: {
          matchId: '',
          eventId: '',
          projectUserId: this.$store.state.user.id,
          matchName: '',
          matchTeamName: '',
          matchType: '',
          matchGroupType: '',
          matchDescribe: '',
          matchBrightSpot: '',
          matchExpect: '',
          projectMatchApplyStatus: 0,
          isUpdate: 0,
          isDel: 0
        },
        dataRule: {
          matchName: [
            { required: true, message: '项目名称不能为空', trigger: 'blur' }
          ],
          matchTeamName: [
            { required: true, message: '团队名称不能为空', trigger: 'blur' }
          ],
          eventId: [
            { required: true, message: '请选择比赛赛事', trigger: 'blur' }
          ],
          matchType: [
            { required: true, message: '请选择比赛类型', trigger: 'blur' }
          ],
          matchGroupType: [
            { required: true, message: '请选择参赛组别', trigger: 'blur' }
          ],
          matchDescribe: [
            { required: true, message: '项目简介不能为空', trigger: 'blur' }
          ],
          matchBrightSpot: [
            { required: true, message: '项目创新之处和亮点不能为空', trigger: 'blur' }
          ],
          matchExpect: [
            { required: true, message: '项目预期或已取得的成果不能为空', trigger: 'blur' }
          ],
          teacherLists: [
            { validator: validateTeacher, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.url = this.$http.adornUrl(`/innovate/match/attach/upload?token=${this.$cookie.get('token')}`)
        this.eventLists = this.$store.state.eventLists
        this.visible = true
        this.dataListLoading = true
        this.dataForm.matchId = id || 0
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
            url: this.$http.adornUrl(`/innovate/use/teacher/all`),
            method: 'get',
            params: this.$http.adornParams({
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.userTeacherInfoEntities = data.userTeacherInfoEntities
            }
          })
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.matchId) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'matchId': this.dataForm.matchId,
                'apply': 'project_match_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.matchInfo.matchInfoEntity
                this.teacherLists = data.matchInfo.matchTeacherEntities
                this.personInfoList = []
                this.staffInfoLists = data.matchInfo.matchStaffInfoEntities
                this.dataForm.matchNoPass = 0
                this.isTeacherInfoNull()
                this.attachLists = data.matchInfo.matchAttachEntities
                // 附件回显
                let tempMatchAtta = []
                for (var i = 0; i < this.attachLists.length; i++) {
                  tempMatchAtta.push(new MatchAttachment(this.attachLists[i]))
                }
                this.fileList = tempMatchAtta
                this.dataListLoading = false
              }
            })
          } else {
            this.dataListLoading = false
          }
          // 获取文件要求：类型=>1 大创,2 中期检查,3 赛事,4 结题
          // this.dataListLoading = true
          // this.$http({
          //   url: this.$http.adornUrl(`/innovate/sys/file/ask/query`),
          //   method: 'get',
          //   params: this.$http.adornParams({
          //     'fileAskType': 3,
          //     'fileAskTime': new Date().getFullYear()
          //   })
          // }).then(({data}) => {
          //   if (data && data.code === 0) {
          //     this.fileAskContent = data.fileAsk == null ? '无' : data.fileAsk.fileAskContent
          //     this.dataListLoading = false
          //   }
          // })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.matchId = this.dataForm.matchId || undefined
          if (valid) {
            if (this.dataForm.matchId) {
              this.dataForm.projectSetDate = Number(this.dataForm.projectSetDate)
              this.dataForm.projectInDate = Number(this.dataForm.projectInDate)
              this.dataForm.projectRegDate = Number(this.dataForm.projectRegDate)
            }
            this.dataForm.isUpdate = 0
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/info/${!this.dataForm.matchId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'matchInfoEntity': this.dataForm,
                'projectLegalInfoEntities': this.legalLists,
                'userPersonInfoEntities': this.personInfoList,
                'matchTeacherEntities': this.teacherLists,
                'matchAttachEntities': this.attachLists,
                'matchStaffInfoEntities': this.staffInfoLists,
                'matchAwardEntities': this.awardLists
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
        data.matchId = this.dataForm.matchId
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
      personRef (data, index) {
        data.matchId = this.dataForm.matchId
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
        data.matchId = this.dataForm.matchId
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
        data.matchId = this.dataForm.matchId
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
          'matchName': this.dataForm.matchName,
          'token': this.$cookie.get('token')
        }
        this.upLoadUrl = this.$http.adornUrl(`/innovate/match/attach/upload`)
      },
      // 移除文件
      upLoadRemove (file, fileList) {
        let tempFileList = []
        for (var index = 0; index < fileList.length; index++) {
          tempFileList.push(fileList[index].file)
        }
        this.fileList = fileList
        if (this.dataForm.matchId === 0) { // 新增
          this.deleteFileHandle(file)
        } else {
          this.attachLists = tempFileList // 修改
        }
        this.$message.success('已移除')
      },
      upLoadPreview (file) {
        console.log(file)
      },
      // 上传成功
      upLoadSuccess (data) {
        if (data && data.code === 0) {
          this.attachLists.push(data.matchAttachEntity)
          this.tempAttachLists = this.attachLists // 临时存放
        } else {
          this.$message.error(data.msg)
        }
      },
      beforeAvatarUpload (file) {
        // 756907362
        const isLt2M = file.size / 1000000 < 100
        if (!isLt2M) {
          this.$message({
            message: '上传文件大小不能超过 100MB',
            type: 'warning',
            duration: 1500
          })
        }
        return isLt2M
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      },
      deleteFileHandle (file) {
        var temp = []
        for (var index = 0; index < this.tempAttachLists.length; index++) {
          if (this.tempAttachLists[index].attachName === file.name) {
            continue
          }
          temp.push(this.tempAttachLists[index])
        }
        this.tempAttachLists = temp
        this.attachLists = temp
      },
      fileAskContentHandler (event) {
        this.fileAskContent = this.eventLists[event - 1].fileAskContent
      }
    }
  }
</script>

