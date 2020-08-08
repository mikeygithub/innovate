<template>
  <el-dialog
    @close="closeDialog"
    append-to-body
    :title="!id ? '新增' : '不通过原因'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <table border="1" cellspacing="0" width="100%" class="table">
      <tr align='center'>
        <td colspan="8" style="height: 0.6rem"></td>
      </tr>
      <tr class="contents" align="center">
        <th colspan="8">
          不通过历史记录
        </th>
      </tr>

      <!--申请开始-->
      <tr align='center'>
        <th colspan="5">不通过原因</th>
        <!--<th colspan="2">申请返回建议</th>-->
        <th colspan="3">退回流程</th>
      </tr>
      <template>
        <tr v-for="item in historyList"
            align="center">
          <td colspan="5" v-text="item.retreatOption"></td>
          <td colspan="3">
            <span v-if="item.applyStatus === 0">项目负责人</span>
            <span v-if="item.applyStatus === 1">指导老师</span>
            <span v-if="item.applyStatus === 2">二级学院</span>
            <span v-if="item.applyStatus === 3">管理员</span>
            <span v-if="item.applyStatus === 4">评委</span>
            <span v-if="item.applyStatus === 5">管理员</span>
            <span v-if="item.applyStatus === 6">超级管理员</span>
          </td>
        </tr>
      </template>
      <tr align='center'>
        <td colspan="8" style="height: 0.6rem"></td>
      </tr>
      <!--申请结束-->
    </table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: 'retreat-history',
    data () {
      return {
        visible: false,
        dataListLoading: false,
        id: 0,
        dataForm: {
          reason: ''
        },
        historyList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.id = id || 0
        this.$nextTick(() => {
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl('/innovate/match/retreat/query'),
              method: 'post',
              params: this.$http.adornParams({
                'matchId': this.id
                // 'userId': this.$store.state.user.id
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                if (data.retreatEntityList.length > 0) {
                  this.historyList = data.retreatEntityList
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
      backApply (item) {
        this.$confirm(`确定要撤回申请？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          item.result = 2
          this.$http({
            url: this.$http.adornUrl('/innovate/match/apply/update/update'),
            method: 'post',
            data: this.$http.adornParams(item)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
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
