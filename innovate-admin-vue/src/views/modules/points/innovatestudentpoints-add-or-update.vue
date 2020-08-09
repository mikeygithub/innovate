<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="学号" prop="stuNum">
      <el-input v-model="dataForm.stuNum" placeholder="学号"></el-input>
    </el-form-item>
    <el-form-item label="添加分数" prop="points">
      <el-input v-model="dataForm.points" placeholder="添加分数"></el-input>
    </el-form-item>
    <el-form-item label="加分原因" prop="pointsReason">
      <el-input v-model="dataForm.pointsReason" placeholder="加分原因"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        addLoading: false,
        dataForm: {
          id: 0,
          stuNum: '',
          points: '',
          pointsReason: ''
        },
        dataRule: {
          stuNum: [
            { required: true, message: '学号不能为空', trigger: 'blur' }
          ],
          points: [
            { required: true, message: '添加分数不能为空', trigger: 'blur' }
          ],
          pointsReason: [
            { required: true, message: '加分原因不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/points/innovatestudentpoints/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.stuNum = data.innovatestudentpoints.stuNum
                this.dataForm.points = data.innovatestudentpoints.points
                this.dataForm.pointsReason = data.innovatestudentpoints.pointsReason
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
              url: this.$http.adornUrl(`/points/innovatestudentpoints/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'stuNum': this.dataForm.stuNum,
                'points': this.dataForm.points,
                'pointsReason': this.dataForm.pointsReason
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
      }
    }
  }
</script>
