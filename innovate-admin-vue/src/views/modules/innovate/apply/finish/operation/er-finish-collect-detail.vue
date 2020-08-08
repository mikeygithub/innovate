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
          <td colspan="20" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents" align="center">
          <th colspan="20">梧州学院 “互联网+”大学生创新创业结题项目汇总表</th>
          <!--<th colspan="20">梧州学院{{finishYear}}“互联网+”大学生创新创业结题项目汇总表</th>-->
        </tr>
        <tr align='center' style="height: 3.0rem">
          <th>二级学院名称(盖章)：</th>
          <td colspan="7" style="height: 1rem"></td>
          <th>联系人：</th>
          <td colspan="5" style="height: 1rem"></td>
          <th>联系电话：</th>
          <td colspan="5" style="height: 1rem"></td>
        </tr>
        <tr align='center' v-if="">
          <th>序号</th>
          <th colspan="3">项目名称</th>
          <th colspan="1">项目等级</th>
          <th colspan="1">立项年份</th>
          <th colspan="2">项目负责人姓名</th>
          <th colspan="2">项目负责人学号</th>
          <th colspan="2">申报类型</th>
          <th colspan="2">申报时间</th>
          <!--<th colspan="2">参与学生人数</th>-->
          <!--<th colspan="3">项目其他成员信息</th>-->
          <!--李四/112583030116,王五/112583030114  -->
          <th colspan="2">指导老师姓名</th>
          <th colspan="2">指导教师职称</th>
          <!--<td>平均分</td>-->
          <th colspan="2">学院</th>
        </tr>

        <template>
          <tr align='center' v-if="finishInfoList.length === 0">
            <td>暂无数据</td>
            <td colspan="5">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <!--<td colspan="2">暂无数据</td>-->
            <!--<td colspan="3">暂无数据</td>-->
            <td colspan="2">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <td colspan="2">暂无数据</td>
          </tr>
        </template>
        <template>
          <tr v-for="(item,index) in finishInfoList" align="center">
              <!--v-if="item.finishInfoEntity.projectFinishApplyStatus !==0 && item.finishInfoEntity.finishNoPass === 0">-->
            <td v-text="index+1"></td>
            <td colspan="3" v-text="item.finishInfoEntity.finishName"></td>
            <td colspan="1"><span v-for="grade in finishGradeList" v-if="grade.value === item.finishInfoEntity.finishGrade" v-text="grade.label"></span></td>
            <td colspan="1" v-text="item.finishInfoEntity.finishYear"></td>
            <td colspan="2">
                <span v-for="user in item.userPersonInfoEntities" >
                  <span v-text="user.sysUserEntity.name"></span>
                </span>
            </td>
            <td colspan="2">
                <span v-for="user in item.userPersonInfoEntities" >
                  <span v-text="user.perStuNo"></span>
                </span>
            </td>
            <td colspan="2">
              <span v-for="temp in finishTypeList" v-if="temp.value === item.finishInfoEntity.finishType" v-text="temp.label"></span>
            </td>
            <td colspan="2">
              <span v-text="item.finishInfoEntity.finishTime"></span>
            </td>
            <!--<td colspan="2" v-text="item.finishStaffInfoEntities.length+1"></td>-->
            <!--<td colspan="3">-->
              <!--<span v-for="staff in item.finishStaffInfoEntities" v-text="staff.staffName+'/'+staff.staffStuNo+','" align="center"></span>-->
            <!--</td>-->
            <td colspan="2">
                <span v-for="teacher in userTeacherInfoEntities">
                  <span v-for="teacher2 in item.finishTeacherEntities"
                        v-if="teacher.userId === teacher2.userId"
                        v-text="teacher.sysUserEntity.name+'  '" align="center">
                  </span>
                </span>
            </td>
            <td colspan="2">
              <span v-for="teacher in userTeacherInfoEntities">
                  <span v-for="teacher2 in item.finishTeacherEntities"
                        v-if="teacher.userId === teacher2.userId">
                     <span v-for="temp in teacherTitleList" v-if="temp.titleId === teacher.teacherTitle" v-text="temp.titleName"></span>
                  </span>
                </span>
            </td>
          <td colspan="2">
            <!--<span v-text="item.finishInfoEntity.finishScoreAvg"></span>-->
            <span v-if="item.userPersonInfoEntities!==null&&item.userPersonInfoEntities.length!==0" :key="index" v-for="inst in instituteList">
              <span v-if="item.userPersonInfoEntities[0].sysUserEntity.instituteId === inst.instituteId" v-text="inst.instituteName"></span>
            </span>
          </td>
          </tr>
        </template>
        <tr align='center'  style="height: 3.0rem">
          <th>备注：</th>
          <td colspan="9" style="height: 1.5rem">已核实所有参赛队员学籍信息，均符合参赛要求</td>
          <th>二级学院领导签名：</th>
          <td colspan="9" style="height: 1.5rem"></td>
        </tr>
        <!--附件结束-->
        <tr align='center'>
          <td colspan="20" style="height: 1.2rem"></td>
        </tr>
      </table>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button v-if="finishInfoList.length !== 0" type="primary" @click="exportFinish">导出</el-button>
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
        finishInfo: {},
        instituteList: this.$store.state.user.institute,
        finishInfoList: [],
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
        teacherTitleList: this.$store.state.user.title,
        teacherSet: '',
        teacherList: [],
        staffList: [],
        reviewList: [],
        attachList: [],
        perInfoList: [],
        reviewTypeList: [
          {value: 'project_finish_apply_status', label: '训练申请流程'},
          {value: 'project_base_apply_status', label: '中心申请流程'},
          {value: 'project_match_apply_status', label: '比赛申请流程'},
          {value: 'project_finish_apply_status', label: '结题申请流程'}
        ],
        awardTypeList: [
          {value: 1, label: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        eventLists: this.$store.state.eventLists,
        finishTypeList: [
          {value: 1, label: '创新训练项目'}, {value: 2, label: '创业训练项目'}, {value: 3, label: '创业实践项目'}
        ],
        sexList: [
          {value: 1, label: '男'}, {value: 2, label: '女'}
        ],
        instinctList: this.$store.state.user.title,
        awardRankList: [
          {value: 1, label: '特等奖'}, {value: 2, label: '一等奖'},
          {value: 3, label: '二等奖'}, {value: 4, label: '三等奖'},
          {value: 5, label: '优秀奖'}, {value: 6, label: '金奖'},
          {value: 7, label: '银奖'}, {value: 8, label: '铜奖'},
          {value: 9, label: '第一名'}, {value: 10, label: '第二名'},
          {value: 11, label: '第三名'}, {value: 12, label: '第四名'},
          {value: 13, label: '第五名'}, {value: 14, label: '其他'}
        ],
        finishGradeList: [
          {value: 1, label: '国家级'},
          {value: 2, label: '自治区级'}
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
        }
      }
    },
    methods: {
      init (id, time) {
        this.visible = true
        this.dataListLoading = true
        this.dataForm.id = id || 0
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/finish/info/list`),
            method: 'get',
            params: this.$http.adornParams({
              'instituteId': this.dataForm.id,
              'project_finish_apply_status_more': 2,
              'noPassStatus': 0,
              'noPass': 'finish_no_pass',
              'isEr': true,
              'isDel': 0,
              'pageSize': 1000000,
              'currPage': 1,
              'finishTime': time.getFullYear()
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.finishInfoList = data.page.list
              this.dataListLoading = false
            }
          })
        } else {
          this.dataListLoading = false
        }
      },
      // 导出
      exportFinish () {
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
            FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '结题项目汇总表' + '.xlsx')
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
  .info-content{
    width: 20px;    /*根据自己项目进行定义宽度*/
    overflow: hidden;     /*设置超出的部分进行影藏*/
    text-overflow: ellipsis;     /*设置超出部分使用省略号*/
    white-space:nowrap ;    /*设置为单行*/
  }
</style>
