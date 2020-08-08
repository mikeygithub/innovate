<template>
  <el-dialog
    :title="'详情'"
    width="68%"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row>
      <table border="1" cellspacing="0" width="100%" class="table">
        <tr align='center'>
          <th>所属项目</th>
          <th>报表月份</th>
          <th>本月投入资金(万元)</th>
        </tr>
        <tr align="center">
          <td><span v-for="item in projectInfoEntities" :key="item.projectId" v-if="dataForm.projectId === item.projectId" v-text="item.projectName" align="center"></span></td>
          <td><span v-text="(dataForm.reportTime || '').split('-')[0]+ '年' + (dataForm.reportTime || '').split('-')[1] + '月'" align="center"></span></td>
          <td><span v-text="dataForm.reportInvestCapital" align="center"></span></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>本月利润总额（万元）</th>
          <th>证明材料名</th>
          <th>材料下载</th>
        </tr>
        <tr align="center">
          <td><span v-text="dataForm.reportProfits" align="center"></span></td>
          <td><span v-text="dataForm.reportProfitsName" align="center"></span></td>
          <td><button @click="fileDown(dataForm.reportProfitsName, dataForm.reportProfitsPath)">下载</button></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>本月销售额（万元）</th>
          <th>证明材料名</th>
          <th>材料下载</th>
        </tr>
        <tr align="center">
          <td><span v-text="dataForm.reportSales" align="center"></span></td>
          <td><span v-text="dataForm.reportSalesName" align="center"></span></td>
          <td><button @click="fileDown(dataForm.reportSalesName, dataForm.reportSalesPath)">下载</button></td>
        </tr>
        <tr align='center'>
          <td style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>本月税金总额（万元）</th>
          <th>证明材料名</th>
          <th>材料下载</th>
        </tr>
        <tr align="center">
          <td><span v-text="dataForm.reportTax" align="center"></span></td>
          <td><span v-text="dataForm.reportTaxName" align="center"></span></td>
          <td><button @click="fileDown(dataForm.reportTaxName, dataForm.reportTaxPath)">下载</button></td>
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
        dataForm: {
          id: 0,
          isDel: 0,
          projectId: '',
          reportId: '',
          reportInvestCapital: '',
          reportProfits: '',
          reportProfitsName: '',
          reportProfitsPath: '',
          reportSales: '',
          reportSalesName: '',
          reportSalesPath: '',
          reportTax: '',
          reportTaxName: '',
          reportTaxPath: '',
          reportTime: ''
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataForm.id = id || 0
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
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/project/report/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.monthlyReport
            }
          })
        }
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
