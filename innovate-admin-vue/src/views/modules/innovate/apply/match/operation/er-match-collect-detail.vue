<template>
  <el-dialog
    :title="'详情'"
    width="80%"
    v-loading="dataListLoading"
    @close="closeDialog"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row>
      <table border="1" cellspacing="0" width="100%" class="table" id="out-table">
          <tr align='center'>
            <td colspan="12" style="height: 1.2rem"></td>
          </tr>
          <tr class="contents" align="center">
            <th colspan="12">
              梧州学院2019“互联网+”大学生创新创业大赛项目汇总表
            </th>
          </tr>
          <tr align='center' style="height: 3.0rem">
            <th>二级学院名称(盖章)：</th>
            <td colspan="4" style="height: 1rem"></td>
            <th>联系人：</th>
            <td colspan="1" style="height: 1rem"></td>
            <th>联系电话：</th>
            <td colspan="4" style="height: 1rem"></td>
          </tr>

          <tr align='center' v-if="">
            <th>序号</th>
            <th>项目名称</th>
            <th>团队名称</th>
            <th>项目负责人姓名</th>
            <th>申报组别</th>
            <th>申报类型</th>
            <th>团队人数</th>
            <th colspan="3">团队成员姓名</th>
            <th>指导老师</th>
            <th>平均得分</th>
          </tr>
        <template>
        <tr align='center' v-if="matchInfoList.length === 0">
          <td>暂无数据</td>
          <td>暂无数据</td>
          <td>暂无数据</td>
          <td>暂无数据</td>
          <td>暂无数据</td>
          <td>暂无数据</td>
          <td>暂无数据</td>
          <td colspan="3">暂无数据</td>
          <td>暂无数据</td>
          <td>暂无数据</td>
        </tr>
        </template>
          <template>
            <tr v-for="(item,index) in matchInfoList" align="center"
                v-if="item.matchInfoEntity.projectMatchApplyStatus!==0 && item.matchInfoEntity.matchNoPass === 0">
              <td v-text="index+1"></td>
              <td v-text="item.matchInfoEntity.matchName"></td>
              <td v-text="item.matchInfoEntity.matchTeamName"></td>
              <td>
                <span v-for="user in item.userPersonInfoEntities" >
                  <span v-text="user.sysUserEntity.name"></span>
                </span>
              </td>
              <td>
                <span v-for="temp in matchGroupTypeList" v-if="temp.value === item.matchInfoEntity.matchGroupType" v-text="temp.label"></span>
              </td>
              <td>
                <span v-for="temp in matchTypeList" v-if="temp.value === item.matchInfoEntity.matchType" v-text="temp.label"/>
              </td>
              <td v-text="item.matchStaffInfoEntities.length+1"></td>
              <td colspan="3">
                <span v-for="staff in item.matchStaffInfoEntities" v-text="staff.staffName+'  '" align="center"></span>
              </td>
              <td>
                <span v-for="teacher in userTeacherInfoEntities">
                  <span v-for="teacher2 in item.matchTeacherEntities"
                      v-if="teacher.userId === teacher2.userId"
                      v-text="teacher.sysUserEntity.name+'  '" align="center">
                  </span>
                </span>
              </td>
              <td><span v-text="item.matchInfoEntity.matchScoreAvg"></span></td>
            </tr>
          </template>
          <!--员工信息结束-->
          <tr align='center'  style="height: 3.0rem">
            <th>备注：</th>
            <td colspan="5" style="height: 1.5rem">已核实所有参赛队员学籍信息，均符合参赛要求</td>
            <th>二级学院领导签名：</th>
            <td colspan="5" style="height: 1.5rem"></td>
          </tr>
          <!--附件结束-->
        <tr align='center'>
          <td colspan="12" style="height: 1.2rem"></td>
        </tr>
        </table>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button v-if="matchInfoList.length !== 0" type="primary" @click="exportDeclare">导出</el-button>
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import FileSaver from 'file-saver'
  import XLSX from 'xlsx'
  export default {
    data () {
      return {
        visible: false,
        dataListLoading: false,
        matchInfo: {},
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
        matchInfoList: [],
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
        moneyList: [],
        station: {},
        roleLists: this.$store.state.user.roleIdList,
        stationList: [],
        teacherSet: '',
        teacherList: [],
        awardList: [],
        staffList: [],
        reviewList: [],
        attachList: [],
        subList: [],
        perInfoList: [],
        reviewTypeList: [
          {value: 'project_audit_apply_status', label: '训练申请流程'},
          {value: 'project_base_apply_status', label: '中心申请流程'},
          {value: 'project_match_apply_status', label: '比赛申请流程'},
          {value: 'project_finish_apply_status', label: '结题申请流程'}
        ],
        eventLists: this.$store.state.eventLists,
        matchTypeList: [
          {value: 1, label: '论文、调研报告'}, {value: 2, label: '视频'},
          {value: 3, label: '设计作品'}, {value: 4, label: '创业计划书'},
          {value: 5, label: '创新创业实践项目'}
        ],
        matchGroupTypeList: [
          {value: 1, label: '创意组'}, {value: 2, label: '初创组'},
          {value: 3, label: '成长组'}, {value: 4, label: '师生共创组'},
          {value: 5, label: '"青年红色梦之旅"赛道'}
        ],
        sexList: [
          {value: 1, label: '男'}, {value: 2, label: '女'}
        ],
        instinctList: this.$store.state.user.title,
        awardTypeList: [
          {value: 1, label: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        awardRankList: [
          {value: 1, label: '特等奖'}, {value: 2, label: '一等奖'},
          {value: 3, label: '二等奖'}, {value: 4, label: '三等奖'},
          {value: 5, label: '优秀奖'}, {value: 6, label: '金奖'},
          {value: 7, label: '银奖'}, {value: 8, label: '铜奖'},
          {value: 9, label: '第一名'}, {value: 10, label: '第二名'},
          {value: 11, label: '第三名'}, {value: 12, label: '第四名'},
          {value: 13, label: '第五名'}, {value: 14, label: '其他'}
        ],
        dataForm: {
          id: 0,
          teacherName: '',
          teacherSex: '',
          teacherUnit: '',
          teacherWorkStatus: '',
          teacherPhone: '',
          teacherJob: '',
          teacherInstinct: '',
          baseId: ''
        },
        staticList: [
          '在驻',
          '孵化成功出园',
          '孵化失败出园'
        ]
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataListLoading = true
        this.dataForm.id = id || 0
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/match/info/erCollect`),
            method: 'get',
            params: this.$http.adornParams({
              'instituteId': this.dataForm.id
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.matchInfoList = data.matchInfoList
              this.dataListLoading = false
            }
          })
        } else {
          this.dataListLoading = false
        }
      },
      // 导出
      exportDeclare () {
        this.$confirm(`确定要进行[导出项目信息]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var msg = '操作成功'
          var type = 'success'
          var wb = XLSX.utils.table_to_book(document.querySelector('#out-table'))
          var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'})
          try {
            FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '大赛项目汇总表' + '.xlsx')
          } catch (e) {
            if (typeof console !== 'undefined') console.log(e, wbout)
            msg = '操作失败'
            type = 'error'
          }
          this.$message({
            message: msg,
            type: type,
            duration: 1500
          })
          return wbout
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
  .contents{
    height: 60px;
    font-size: 18px;
  }
  .el-card__body {
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
    width: 80%;
  }
  .table th {
    /*允许在单词内换行。*/
    word-break: break-word;
    /*设置宽度*/
    width: 80%;
  }
</style>
