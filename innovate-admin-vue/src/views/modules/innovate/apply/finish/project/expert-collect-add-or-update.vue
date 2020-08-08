<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="24">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-col :span="12">
        <el-form-item label="年度" prop="expertCollectTime">
          <el-form-item>
            <el-date-picker
              v-model="dataForm.expertCollectTime"
              align="right"
              :editable="false"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="year"
              placeholder="请选择年度">
            </el-date-picker>
          </el-form-item>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="学院" prop="expertCollectInstituteId">
          <el-select v-model="dataForm.expertCollectInstituteId" placeholder="请选择" :disabled="true">
            <el-option
              v-for="item in instituteList"
              :key="item.instituteId"
              :label="item.instituteName"
              :value="item.instituteId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="24"></el-col>
      <el-col :span="12">
        <el-form-item label="邮箱" prop="expertCollectInstituteEmail">
          <el-input v-model="dataForm.expertCollectInstituteEmail" placeholder="邮箱"></el-input>
        </el-form-item>
      </el-col>
    <el-col :span="10">
      <el-form-item label="填表人" prop="expertCollectWriter">
        <el-input v-model="dataForm.expertCollectWriter" placeholder="填表人"></el-input>
      </el-form-item>
    </el-col>
    <el-col>
      <el-form-item label="联系电话" prop="expertCollectWriterPhone">
        <el-input v-model="dataForm.expertCollectWriterPhone" placeholder="填表人联系电话"></el-input>
      </el-form-item>
    </el-col>
    <!--专家start-->
    <el-col :span="24">
      <el-form-item label="添加专家" prop="teacherLists">
        <el-button size="mini" type="primary" plain @click="addTeacher()">从系统中添加</el-button>
        <template v-for="(item,index) in finishInExpertEntities" v-if="item.isDel !== 1">
          <el-col :span="3">
            <el-tag style="margin-right: 1rem"
                    v-for="user in userTeacherInfoEntities"
                    :key="user.userId"
                    v-if="user.userId === item.sysUserEntity.userId"
                    v-text="user.name">
            </el-tag>
            <el-button size="mini" type="danger" @click="delTeacher(item, index)">删除</el-button>
          </el-col>
        </template>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="校外人员" prop="finishOutExpertEntities">
        <el-button size="mini" type="primary" plain @click="addExpert()">添加校外人员</el-button>
        <template v-for="(item,index) in finishOutExpertEntities" v-if="item.isDel !== 1">
          <el-col :span="3" style="margin-right: 2rem">
            <el-tag style="margin-right: 1rem" v-text="item.expertName"></el-tag>
            <el-button size="mini" type="danger" @click="delExpert(item, index)">删除</el-button>
          </el-col>
        </template>
      </el-form-item>
    </el-col>
    <!--专家end-->
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
  import TeacherAddOrUpdate from './teacher-add-or-update'
  import ExpertAddOrUpdate from './expert-add-or-update'

  class SysUserEntity {
    constructor (id) {
      this.userId = id
    }
  }

class FinishExpertEntity {
    constructor (expert) {
      this.expertUserId = expert.userId
      this.sysUserEntity = new SysUserEntity(expert.userId)
      this.isDel = expert.isDel
    }
}
  export default {
    components: {
      TeacherAddOrUpdate,
      ExpertAddOrUpdate
    },
    data () {
      return {
        visible: false,
        teacherAddOrUpdateVisible: false,
        expertAddOrUpdateVisible: false,
        isTeacherInfoNullVisible: false,
        personAddOrUpdateVisible: false,
        teacherLists: [],
        finishInExpertEntities: [], // 内部
        finishOutExpertEntities: [], // 外部
        userTeacherInfoEntities: [],
        instituteList: this.$store.state.user.institute, // 二级学院
        dataForm: {
          expertCollectId: 0,
          expertCollectTime: '',
          expertCollectInstituteId: this.$store.state.user.instituteId,
          expertCollectInstituteEmail: '',
          expertCollectWriter: '',
          expertCollectWriterPhone: '',
          instituteList: this.$store.state.user.institute // 二级学院
        },
        dataRule: {
          expertCollectTime: [
            { required: true, message: '年度不能为空', trigger: 'blur' }
          ],
          expertCollectInstituteId: [
            { required: true, message: '学院不能为空', trigger: 'blur' }
          ],
          expertCollectInstituteEmail: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          expertCollectWriter: [
            { required: true, message: '填表人不能为空', trigger: 'blur' }
          ],
          expertCollectWriterPhone: [
            { required: true, message: '填表人联系电话不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.expertCollectId = id || 0
        this.visible = true
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
          this.teacherLists = []
          this.finishInExpertEntities = [] // 内部
          this.finishOutExpertEntities = [] // 外部
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.expertCollectId) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/finish/expert/info/${this.dataForm.expertCollectId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.expertCollectTime = data.innovateFinishExpertCollect.expertCollectTime
                this.dataForm.expertCollectInstituteEmail = data.innovateFinishExpertCollect.expertCollectInstituteEmail
                this.dataForm.expertCollectWriter = data.innovateFinishExpertCollect.expertCollectWriter
                this.dataForm.expertCollectWriterPhone = data.innovateFinishExpertCollect.expertCollectWriterPhone
                this.finishOutExpertEntities = data.innovateFinishExpertCollect.finishOutExpertEntities
                this.finishInExpertEntities = data.innovateFinishExpertCollect.finishInExpertEntities
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/finish/expert/${!this.dataForm.expertCollectId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'expertCollectId': this.dataForm.expertCollectId || undefined,
                'expertCollectTime': this.dataForm.expertCollectTime,
                'expertCollectInstituteId': this.dataForm.expertCollectInstituteId,
                'expertCollectInstituteEmail': this.dataForm.expertCollectInstituteEmail,
                'expertCollectWriter': this.dataForm.expertCollectWriter,
                'expertCollectWriterPhone': this.dataForm.expertCollectWriterPhone,
                'finishOutExpertEntities': this.finishOutExpertEntities, // 外部人员
                'finishInExpertEntities': this.finishInExpertEntities // 内部人员
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
      addTeacher (item, index) {
        this.teacherAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['teacherAddOrUpdate'].init(item, index + 1)
        })
        this.isTeacherInfoNull()
      },
      addExpert (item, index) {
        this.expertAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['expertAddOrUpdate'].init(item, index + 1)
        })
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
      teacherRef (data, index) {
        data.finishId = this.dataForm.finishId
        this.teacherAddOrUpdateVisible = false
        if (index) {
          this.finishInExpertEntities[index - 1] = data
        } else {
          this.finishInExpertEntities.push(new FinishExpertEntity(data))
        }
        // this.isTeacherInfoNull()
      },
      expertRef (data, index) {
        data.expertId = this.dataForm.finishId
        this.personAddOrUpdateVisible = false
        if (index) {
          this.finishOutExpertEntities[index - 1] = data
        } else {
          this.finishOutExpertEntities.push(Object.assign({}, data)) // 深拷贝
        }
      },
      delExpert: function (data, index) {
        data.isDel = 1
        this.finishOutExpertEntities[index] = data
      },
      delTeacher: function (data, index) {
        data.isDel = 1
        this.finishInExpertEntities[index] = data
        this.isTeacherInfoNull()
      },
      getFinishInExpertEntities () {
        var tempFinishInExpertEntities = []
        for (var index = 0; index < this.teacherLists.length; index++) {
          tempFinishInExpertEntities.push(new FinishExpertEntity(this.teacherLists[index]))
        }
        return tempFinishInExpertEntities
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      }
    }
  }
</script>
