<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="编号" prop="subjectNum">
        <el-input v-model="dataForm.subjectNum" placeholder="请输入编号"></el-input>
      </el-form-item>
      <el-form-item label="学科" prop="subjectName">
        <el-input v-model="dataForm.subjectName" placeholder="请输入学科"></el-input>
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
        roleList: [],
        dataForm: {
          id: 0,
          subjectNum: '',
          subjectName: ''
        },
        dataRule: {
          subjectNum: [
            { required: true, message: '编号不能为空', trigger: 'blur' }
          ],
          subjectName: [
            { required: true, message: '学科不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/innovate/sys/subject/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.subjectNum = data.subject.subjectNum
                this.dataForm.subjectName = data.subject.subjectName
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
              url: this.$http.adornUrl(`/innovate/sys/subject/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'subjectId': this.dataForm.id || undefined,
                'subjectNum': this.dataForm.subjectNum,
                'subjectName': this.dataForm.subjectName
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
