<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="活动名称" prop="activityName">
      <el-input v-model="dataForm.activityName" placeholder="活动名称"></el-input>
    </el-form-item>
    <el-form-item label="活动地点" prop="activitySite">
      <el-input v-model="dataForm.activitySite" placeholder="活动地点"></el-input>
    </el-form-item>
    <el-form-item label="活动学分" prop="activityPoint">
      <el-input v-model="dataForm.activityPoint" placeholder="活动学分"></el-input>
    </el-form-item>
    <el-form-item label="活动时间" prop="activityTime">
      <div class="block">
        <el-date-picker
          v-model="dataForm.activityTime"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="选择日期时间">
        </el-date-picker>
      </div>
    </el-form-item>
    <el-form-item label="结束时间" prop="activityEndTime">
      <div class="block">
        <el-date-picker
          v-model="dataForm.activityEndTime"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="选择日期时间">
        </el-date-picker>
      </div>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          activityName: '',
          activitySite: '',
          activityPoint: '',
          activityTime: '',
          activityEndTime: ''
        },
        dataRule: {
          activityName: [
            { required: true, message: '活动名称不能为空', trigger: 'blur' }
          ],
          activitySite: [
            { required: true, message: '活动地点不能为空', trigger: 'blur' }
          ],
          activityPoint: [
            { required: true, message: '活动学分不能为空', trigger: 'blur' }
          ],
          activityTime: [
            { required: true, message: '活动时间不能为空', trigger: 'blur' }
          ],
          activityEndTime: [
            { required: true, message: '活动结束时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/points/innovatestudentactivity/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.activityName = data.innovatestudentactivity.activityName
                this.dataForm.activitySite = data.innovatestudentactivity.activitySite
                this.dataForm.activityPoint = data.innovatestudentactivity.activityPoint
                this.dataForm.activityTime = data.innovatestudentactivity.activityTime
                this.dataForm.activityEndTime = data.innovatestudentactivity.activityEndTime
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
              url: this.$http.adornUrl(`/points/innovatestudentactivity/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'activityName': this.dataForm.activityName,
                'activitySite': this.dataForm.activitySite,
                'activityPoint': this.dataForm.activityPoint,
                'activityTime': this.dataForm.activityTime,
                'activityEndTime': this.dataForm.activityEndTime
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
      }
    }
  }
</script>
