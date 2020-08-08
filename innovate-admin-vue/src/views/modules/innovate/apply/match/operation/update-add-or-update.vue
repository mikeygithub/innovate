<template>
  <el-dialog
    width="38%"
    @close="closeDialog"
    append-to-body
    :title="!id ? '新增' : '申请修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="7rem">
      <el-form-item label="修改申请原因" prop="reason">
        <el-input
          type="textarea" v-model="dataForm.reason" placeholder="请输入原因"></el-input>
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
    name: 'update-add-or-update',
    data () {
      return {
        addLoading: false,
        visible: false,
        dataListLoading: false,
        id: 0,
        dataForm: {
          reason: ''
        },
        historyList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataRule: {
          reason: [
            { required: true, message: '请输入修改申请原因', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.id = id || 0
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl('/innovate/project/apply/update/list'),
              method: 'get',
              params: this.$http.adornParams({
                'projectId': this.id
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                if (data.page.length > 0) {
                  for (let index = 0; index < data.page.length; index++) {
                    if (data.page[index].result === null) {
                      this.historyList.push(data.page[index])
                    }
                  }
                } else {
                  this.historyList = []
                }
              }
              this.dataListLoading = false
            })
          }
        })
      },

      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.init(this.id)
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.init(this.id)
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (this.historyList.length === 0) {
            if (valid) {
              this.addLoading = true
              this.$http({
                url: this.$http.adornUrl(`/innovate/project/apply/update/apply`),
                method: 'post',
                params: this.$http.adornParams({
                  'projectId': this.id,
                  'reason': this.dataForm.reason
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
              this.visible = false
              this.dataForm.subTime = Number(this.dataForm.subTime)
              this.$emit('refreshDataList', this.dataForm, this.id)
            }
          } else {
            this.$message.error('您已经提交过申请，请勿重新提交！')
          }
        })
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      }
    }
  }
</script>
<style>
  .table {
    border: 1px solid #ddd;
    /*为表格设置合并边框模型*/
    border-collapse: collapse;
    /*列宽由表格宽度和列宽度设定*/
    table-layout: fixed;
  }
  .table td {
    /*允许在单词内换行。*/
    word-break: break-word;
    /*设置宽度*/
    width: 100%;
  }
</style>
