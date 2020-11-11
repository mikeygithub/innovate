<template>
  <div style="margin: 10%">
    <div v-show="inputVisible">
      <div style="margin-top: 30%">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()">
          <!--      <el-form-item label="活动id" prop="activityId">-->
          <!--        <el-input v-model="dataForm.activityId" placeholder="活动id"></el-input>-->
          <!--      </el-form-item>-->
          <h1 v-text="dataForm.activityName"></h1>
          <h2>活动进场签到</h2>
          <el-form-item  prop="studentId">
            <el-input v-model="dataForm.studentId" placeholder="学生学号"></el-input>
          </el-form-item>
          <el-form-item>
          </el-form-item>
        </el-form>
      </div>
      <el-button style="width: 100%" type="primary" @click="dataFormSubmit()" :loading="addLoading">确定签到</el-button>
    </div>
    <div v-show="!inputVisible" style="margin-top: 30%;">
      <h1 align="center">
      <img :src="src">
      </h1>
      <h1 align="center">成功</h1>
    </div>
    <div style="margin-top: 80%">
      <h5 align="center">
        <a style="color: #f0f0f0" href="https://mikeygithub.github.io">Power by Mikey</a>
      </h5>
    </div>
  </div>
</template>

<script>
  import {getUrlKey} from '../../../utils'
  import finishImage from '@/assets/img/finish.png'
  export default {
    data () {
      return {
        src: finishImage,
        visible: false,
        addLoading: false,
        inputVisible: true,
        dataForm: {
          id: 0,
          activityId: '',
          activityName: '',
          studentId: '',
          activitySite: '',
          activityPoint: '',
          activityTime: '',
          activityEndTime: ''
        },
        dataRule: {
          activityId: [
            { required: true, message: '活动id不能为空', trigger: 'blur' }
          ],
          studentId: [
            { required: true, message: '学生学号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init () {
        this.dataForm.id = getUrlKey('activity')
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/points/innovatestudentactivity/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.activityName = data.innovateStudentActivity.activityName
                this.dataForm.activitySite = data.innovateStudentActivity.activitySite
                this.dataForm.activityPoint = data.innovateStudentActivity.activityPoint
                this.dataForm.activityTime = data.innovateStudentActivity.activityTime
                this.dataForm.activityEndTime = data.innovateStudentActivity.activityEndTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl(`/points/innovatestudentsignin/save`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'activityId': this.dataForm.id,
                'studentId': this.dataForm.studentId
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '签到成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.inputVisible = false
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
      }
    },
    created () {
      this.init()
    }
  }
</script>
