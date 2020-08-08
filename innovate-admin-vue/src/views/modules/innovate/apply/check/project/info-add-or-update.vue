<template>
  <el-dialog
    width="60%"
    @close="closeDialog"
    v-loading="dataListLoading"
    :title="!dataForm.id ? '上传材料信息' : '修改材料信息'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="20">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="16rem" style="width: 94%; margin: 0 auto">
        <!--<el-col :span="24">-->
          <!--<el-form-item label="大创项目名称" prop="declareName">-->
            <!--<el-input v-model="dataForm.declareName" placeholder="请输入大创项目名称"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="申报类型" prop="declareType">-->
            <!--<el-select v-model="dataForm.declareType"  placeholder="请选择">-->
              <!--<el-option v-for="item in declareTypeList" :key="item.value" :label="item.label" :value="item.value">-->
              <!--</el-option>-->
            <!--</el-select>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col>-->
        <!--<el-form-item label="所属学科" prop="subjectId">-->
          <!--<el-select v-model="dataForm.subjectId" placeholder="请选择">-->
            <!--<el-option-->
              <!--v-for="item in subjectList"-->
              <!--:key="item.subjectId"-->
              <!--:label="item.subjectName"-->
              <!--:value="item.subjectId">-->
            <!--</el-option>-->
          <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
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
        <!--<el-col :span="24">-->
          <!--<el-form-item label="项目简介(300字之内)" prop="declareDescribe">-->

            <!--<el-input type="textarea" maxlength="400"-->
                      <!--:rows="5"-->
                      <!--v-model="dataForm.declareDescribe"-->
                      <!--placeholder="请输入"></el-input>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="项目指导老师" prop="teacherLists">-->
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
          <!--&lt;!&ndash;<el-form-item label="指导老师" prop="teacherLists">&ndash;&gt;-->
            <!--&lt;!&ndash;<el-button size="mini"&ndash;&gt;-->
                       <!--&lt;!&ndash;v-if="addVisible(teacherLists)"&ndash;&gt;-->
                       <!--&lt;!&ndash;type="primary" plain @click="addTeacher()">添加</el-button>&ndash;&gt;-->
            <!--&lt;!&ndash;<template v-for="(item,index) in teacherLists" v-if="item.isDel !== 1">&ndash;&gt;-->
              <!--&lt;!&ndash;<el-col :span="24">&ndash;&gt;-->
                <!--&lt;!&ndash;<el-tag style="margin-right: 1rem"&ndash;&gt;-->
                        <!--&lt;!&ndash;v-for="user in userTeacherInfoEntities"&ndash;&gt;-->
                        <!--&lt;!&ndash;:key="user.userId"&ndash;&gt;-->
                        <!--&lt;!&ndash;v-if="user.userId === item.userId"&ndash;&gt;-->
                        <!--&lt;!&ndash;v-text="'姓名：' + user.sysUserEntity.name">&ndash;&gt;-->
                <!--&lt;!&ndash;</el-tag>&ndash;&gt;-->
                <!--&lt;!&ndash;<el-button size="mini" type="primary" plain @click="addTeacher()">添加</el-button>&ndash;&gt;-->
                <!--&lt;!&ndash;<el-button size="mini" type="primary" plain @click="addTeacher(item, index)">修改</el-button>&ndash;&gt;-->
                <!--&lt;!&ndash;<el-button size="mini" type="danger" @click="delTeacher(item, index)">删除</el-button>&ndash;&gt;-->
              <!--&lt;!&ndash;</el-col>&ndash;&gt;-->
            <!--&lt;!&ndash;</template>&ndash;&gt;-->
            <!--&lt;!&ndash;<span v-if="isTeacherInfoNullVisible" style="color: crimson">*请联系指导老师完善个人信息</span>&ndash;&gt;-->
          <!--&lt;!&ndash;</el-form-item>&ndash;&gt;-->
        <!--</el-col>-->
        <!--<el-col :span="24">-->
          <!--<el-form-item label="项目参与者信息" prop="staffInfoLists">-->
            <!--<el-button size="mini"-->
                       <!--v-if="addVisible(staffInfoLists)" type="primary" plain @click="addStaff()">添加</el-button>-->
            <!--<template v-for="(item,index) in staffInfoLists" v-if="item.isDel !== 1">-->
              <!--<el-col :span="24">-->
                <!--<el-tag style="margin-right: 1rem"-->
                        <!--v-text="item.staffName">-->
                <!--</el-tag>-->
                <!--<el-button size="mini" type="primary" plain @click="addStaff()">添加</el-button>-->
                <!--<el-button size="mini" type="primary" plain @click="addStaff(item, index)">修改</el-button>-->
                <!--<el-button size="mini" type="danger" @click="delStaff(item, index)">删除</el-button>-->
              <!--</el-col>-->
            <!--</template>-->
          <!--</el-form-item>-->
        <!--</el-col>-->

        <!--<el-col :span="24">-->
          <!--<el-form-item label="附件" prop="attachLists">-->
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
        <!--独立附件start-->
        <!--<el-col :span="24">-->
        <!--<el-form-item label="提交申报书">-->
          <!--<el-upload-->
            <!--class="upload-demo"-->
            <!--:action="url"-->
            <!--:data="{declareId: dataForm.declareInfoEntity.declareId}"-->
            <!--:on-success="successHandle1">-->
            <!--<el-button size="small" type="primary">点击上传</el-button>-->
          <!--<label>-->
            <!--（以附件形式上传提交申报书的扫描件）-->
          <!--</label>-->
          <!--</el-upload>-->
        <!--</el-form-item>-->
        <!--</el-col>-->

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
        <el-col :span="24">
          <template>
            <el-alert
              title="附件要求"
              type="success"
              :description="fileAskContent">
            </el-alert>
          </template>
        </el-col>
        <el-col :span="24">
          <el-form-item label="附件列表" prop="attachLists">
            <template v-for="(item,index) in attachLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-text="item.attachName">
                </el-tag>
                <el-button size="mini" type="danger" @click="delAttach(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文件上传">
            <el-upload
              multiple
              ref="upLoadFiles"
              list-type="card"
              :data="upLoadData"
              :action="upLoadUrl"
              :on-preview="upLoadPreview"
              :on-remove="upLoadRemove"
              :on-success="upLoadSuccess"
              :on-change="upLoadChange"
              :file-list="fileList"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" @click="upLoadSubmit">添加到附件列表</el-button>
              <div slot="tip" class="el-upload__tip">注意文件需要添加到附件列表后确定保存才能添加成功</div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
    <!--<teacher-add-or-update v-if="teacherAddOrUpdateVisible" ref="teacherAddOrUpdate" @refreshDataList="teacherRef"></teacher-add-or-update>-->
    <!--<person-add-or-update v-if="personAddOrUpdateVisible" ref="personAddOrUpdate" @refreshDataList="personRef"></person-add-or-update>-->
    <!--<award-add-or-update v-if="awardAddOrUpdateVisible" ref="awardAddOrUpdate" @refreshDataList="awardRef"></award-add-or-update>-->
    <!--<staff-add-or-update v-if="staffAddOrUpdateVisible" ref="staffAddOrUpdate" @refreshDataList="staffRef"></staff-add-or-update>-->
  </el-dialog>
</template>

<script>
  export default {
    data () {
      // var validateTeacher = (rule, value, callback) => {
      //   if (this.teacherLists.length === 0) {
      //     callback(new Error('指导老师信息不能为空'))
      //   } else {
      //     let notNull = false
      //     for (let index = 0; index < this.teacherLists.length; index++) {
      //       if (this.teacherLists[index].isDel === 0) {
      //         notNull = true
      //       }
      //     }
      //     if (notNull) {
      //       callback()
      //     } else {
      //       callback(new Error('指导老师信息不能为空'))
      //     }
      //   }
      // }
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
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
        subjectList: this.$store.state.user.subject,
        instituteList: this.$store.state.user.institute, // 二级学院
        statusList: [{value: 1, label: '是'}, {value: 2, label: '否'}],
        declareTypeList: [
          {value: 1, label: '创新训练项目'},
          {value: 2, label: '创业训练项目'},
          {value: 3, label: '创业实践项目'}
        ],
        dataForm: {
          declareId: '',
          checkId: '',
          eventId: '',
          instituteId: '',
          subjectId: '',
          projectUserId: this.$store.state.user.id,
          declareName: '',
          declareType: '',
          projectAuditApplyStatus: 0,
          isUpdate: 0,
          isDel: 0
        },
        dataRule: {
          declareName: [
            {required: true, message: '大创项目名称不能为空', trigger: 'blur'}
          ],
          declareDescribe: [
            {required: true, message: '大创项目简介', trigger: 'blur'}
          ],
          instituteId: [
            {required: true, message: '请选择所属二级学院', trigger: 'blur'}
          ],
          subjectId: [
            {required: true, message: '请选择所属学科', trigger: 'blur'}
          ],
          declareType: [
            {required: true, message: '请选择申报类型', trigger: 'blur'}
          ],
          attachLists: [
            {validator: validateAttach, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.url = this.$http.adornUrl(`/innovate/check/attach/upload?token=${this.$cookie.get('token')}`)
        this.visible = true
        this.dataListLoading = true
        this.dataForm.checkId = id || 0
        this.$nextTick(() => {
          // this.$refs['dataForm'].resetFields()
          this.$http({
            url: this.$http.adornUrl(`/innovate/check/info`),
            method: 'get',
            params: this.$http.adornParams({
              'checkId': this.dataForm.checkId
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.info
              this.attachLists = data.info.innovateCheckAttachEntities
              this.dataListLoading = false
            }
          })
          this.dataListLoading = false
          // 获取文件要求：类型=>1 大创,2 中期检查,3 赛事,4 结题
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl(`/innovate/sys/file/ask/query`),
            method: 'get',
            params: this.$http.adornParams({
              'fileAskType': 2,
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
        this.dataForm.isUpdate = 0
        this.addLoading = true
        this.$http({
          url: this.$http.adornUrl(`/innovate/check/${!this.dataForm.checkId ? 'save' : 'update'}`),
          method: 'post',
          data: this.$http.adornData({
            'innovateCheckInfoEntity': this.dataForm.innovateCheckInfoEntity,
            'innovateCheckAttachEntities': this.attachLists,
            'innovateCheckRetreatEntities': this.dataForm.innovateCheckRetreatEntities
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
      delAttach: function (data, index) {
        if (data.isDel !== 1) {
          data.isDel = 1
          this.attachLists[index] = data
        } else {
          this.attachLists.splice(index, 1)
        }
      },
      upLoadSubmit () {
        this.$refs.upLoadFiles.submit()
        // this.$refs.upLoadFiles.clearFiles()
      },
      upLoadChange () {
        this.upLoadData = {
          'checkId': this.dataForm.innovateCheckInfoEntity.checkId,
          'declareId': this.dataForm.declareInfoEntity.declareId,
          'token': this.$cookie.get('token')
        }
        this.upLoadUrl = this.$http.adornUrl(`/innovate/check/attach/upload`)
      },
      upLoadRemove (file, fileList) {
        console.log(file, fileList)
      },
      upLoadPreview (file) {
        console.log(file)
      },
      upLoadSuccess (data) {
        if (data && data.code === 0) {
          this.attachLists.push(data.checkAttachEntity)
        } else {
          this.$message.error(data.msg)
        }
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
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

