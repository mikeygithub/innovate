<template>
  <el-dialog
    :title="'详情'"
    width="68%"
    @close="closeDialog"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row>
      <el-card>
        <el-date-picker
          v-model="dataForm.totalStartTime"
          type="month"
          format="yyyy-MM"
          value-format="timestamp"
          placeholder="选择开始日期">
        </el-date-picker>
        <el-date-picker
          v-model="dataForm.totalEndTime"
          type="month"
          format="yyyy-MM"
          value-format="timestamp"
          placeholder="选择结束日期">
        </el-date-picker>
        <el-button @click="changeTime">搜索</el-button>
      </el-card>
      <table border="1" cellspacing="0" width="100%" class="table">
        <tr align="center">
          <td>所属项目</td>
          <td>
            <span v-for="item in projectInfoEntities"
                    :key="item.projectId"
                    v-if="dataForm.projectId === item.projectId"
                    v-text="item.projectName" align="center">
            </span>
          </td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align="center">
          <td>累计投入资金数额（万元）</td>
          <td><span v-text="dataForm.totalInvestCapital" align="center"></span></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align="center">
          <td>累计营业额（万元）</td>
          <td><span v-text="dataForm.totalSales" align="center"></span></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align="center">
          <td>累计利润情况（万元）</td>
          <td><span v-text="dataForm.totalProfits" align="center"></span></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align="center">
          <td>累计上缴税金情况（万元）</td>
          <td><span v-text="dataForm.totalTax" align="center"></span></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align="center">
          <td><span v-text="dataForm.reportTax" align="center"></span></td>
          <td><span v-text="dataForm.reportTaxName" align="center"></span></td>
        </tr>
      </table>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: 'statistics-detail',
    data () {
      return {
        visible: false,
        baseInfo: [],
        costList: [],
        moneyList: [],
        stationList: [],
        teacherList: [],
        projectInfoEntities: [],
        statusList: [{
          value: 1,
          label: '全职'
        }, {
          value: 2,
          label: '兼职'
        }],
        id: 0,
        dataForm: {
          isDel: 0,
          projectId: '',
          totalInvestCapital: '',
          totalSales: '',
          totalProfits: '',
          totalTax: '',
          totalStartTime: Number(new Date().setMonth(new Date().getMonth() - 1)),
          totalEndTime: Number(new Date())
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.id = id || 0
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/all'),
          method: 'get',
          params: this.$http.adornParams({
            // 'apply': 'project_audit_apply_status',
            // 'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.projectInfoEntities = data.projectInfoEntities
          }
        })
        if (this.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/project/report/total`),
            method: 'post',
            params: this.$http.adornParams({
              'projectId': this.id,
              'totalStartTime': this.dataForm.totalStartTime,
              'totalEndTime': this.dataForm.totalEndTime
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.projectMonthlyReportTotalEntity
              this.dataForm.totalStartTime = Number(new Date(this.dataForm.totalStartTime))
              this.dataForm.totalEndTime = Number(new Date(this.dataForm.totalEndTime))
            }
          })
        }
      },
      changeTime () {
        this.$http({
          url: this.$http.adornUrl(`/innovate/project/report/total`),
          method: 'post',
          params: this.$http.adornParams({
            'projectId': this.id,
            'totalStartTime': this.dataForm.totalStartTime,
            'totalEndTime': this.dataForm.totalEndTime
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataForm = data.projectMonthlyReportTotalEntity
            this.dataForm.totalStartTime = Number(new Date(this.dataForm.totalStartTime))
            this.dataForm.totalEndTime = Number(new Date(this.dataForm.totalEndTime))
          }
        })
      },

      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      },
      fileDown (fileName, filePath) {
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/innovate/match/attach/download`),
          method: 'post',
          params: this.$httpFile.adornParams({
            'filePath': filePath
            // 'apply': 'project_base_apply_status'
          })
        }).then(response => {
          if (!response) {
            return
          }
          let url = window.URL.createObjectURL(new Blob([response.data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', fileName)
          document.body.appendChild(link)
          link.click()
        }).catch(err => {
          console.log(err)
        })
      }
    }
  }
</script>
<style>
  .contents{
    height: 60px;
    font-size: 18px;
  }
  .span__body {
    padding: 10px;
  }
  .el-step__title {
    font-size: 12px;
    line-height: 28px;
  }
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
