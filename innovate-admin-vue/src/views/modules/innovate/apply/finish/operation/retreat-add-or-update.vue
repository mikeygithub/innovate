<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增' : '不通过'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="6rem">
      <el-form-item label="修改建议" prop="option">
        <el-input
          type="textarea"
          :rows="5"
          placeholder="请输入"
          v-model="dataForm.option">
        </el-input>
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
    name: 'retreat-add-or-update',
    data () {
      return {
        visible: false,
        loading: false,
        userMap: [],
        id: 0,
        apply: '',
        applyStatus: '0',
        dataForm: {
          option: ''
        },
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataRule: {
          option: [
            { required: true, message: '修改建议不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (index, apply, applyStatus) {
        this.apply = apply
        this.applyStatus = applyStatus
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/finish/retreat/retreat`),
              method: 'post',
              params: this.$http.adornParams({
                'apply': this.apply,
                'finishId': this.id,
                'userId': this.$store.state.user.id,
                'option': this.dataForm.option,
                'applyStatus': this.applyStatus
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
