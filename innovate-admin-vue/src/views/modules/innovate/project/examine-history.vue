<template>
  <el-dialog
    @close="closeDialog"
    append-to-body
    :title="!id ? '新增' : '申请修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <table border="1" cellspacing="0" width="100%" class="table">
      <tr align='center'>
        <td colspan="8" style="height: 0.6rem"></td>
      </tr>
      <tr class="contents" align="center">
        <th colspan="8">
          申请历史记录
        </th>
      </tr>

      <!--申请开始-->
      <tr align='center'>
        <th colspan="5">申请原因</th>
        <th colspan="1">申请结果</th>
        <!--<th colspan="2">申请返回建议</th>-->
        <th colspan="2">操作</th>
      </tr>
      <template>
        <tr v-for="item in historyList"
            align="center">
          <td colspan="5" v-text="item.reason"></td>
          <td colspan="1">
            <span v-if="item.result === null">待审批</span>
            <span v-if="item.result === 0">不通过</span>
            <span v-if="item.result === 1">通过</span>
            <span v-if="item.result === 2">已撤回</span>
          </td>
          <!--<td colspan="2" v-text="item.applyOption"></td>-->
          <td colspan="1">
            <el-button type="info" size="mini" v-if="item.result === null" @click="backApply(item, 1)">通过</el-button>
          </td>
          <td colspan="1">
            <el-button type="info" size="mini" v-if="item.result === null" @click="backApply(item, 0)">不通过</el-button>
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
    name: 'examine-history',
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
              url: this.$http.adornUrl('/innovate/project/apply/update/list'),
              method: 'get',
              params: this.$http.adornParams({
                'projectId': this.id
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                if (data.page.length > 0) {
                  this.historyList = data.page
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
      backApply (item, result) {
        this.$confirm(`确定${result ? '通过' : '不通过'}申请？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          item.result = result
          this.$http({
            url: this.$http.adornUrl('/innovate/project/apply/update/update'),
            method: 'post',
            data: this.$http.adornParams(item)
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
