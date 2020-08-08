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
          <td colspan="21" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents" align="center">
          <th colspan="21">
            梧州学院“互联网+”大学生创新创业大创项目汇总表
          </th>
        </tr>
        <tr align='center' style="height: 3.0rem">
          <th>二级学院名称(盖章)：</th>
          <td colspan="8" style="height: 1rem"></td>
          <th>联系人：</th>
          <td colspan="5" style="height: 1rem"></td>
          <th>联系电话：</th>
          <td colspan="5" style="height: 1rem"></td>
        </tr>
        <tr align='center' v-if="">
          <th>序号</th>
          <th>项目级别</th>
          <th>项目编号</th>
          <th>项目名称</th>
          <th>项目年限</th>
          <th>所属专业类</th>
          <th>负责人姓名</th>
          <th>负责人学号</th>
          <!--<th>申报组别</th>-->
          <th>项目类型</th>
          <th>参与学生人数</th>
          <th colspan="2">项目其他成员信息</th>
          <th>指导老师姓名</th>
          <th>指导教师职称</th>
          <th>总经费(元)</th>
          <th>区财政(元)</th>
          <th>校拨(元)</th>
          <th colspan="2">项目简介</th>
          <td>平均分</td>
          <td>所属学院</td>
        </tr>

        <template>
          <tr align='center' v-if="declareInfoList.length === 0">
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td colspan="1">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <!--<th>申报组别</th>-->
            <td>暂无数据</td>
            <td colspan="1">暂无数据</td>
            <td colspan="2">暂无数据</td>
            <!--李四/112583030116,王五/112583030114  -->
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td>暂无数据</td>
            <td colspan="2">暂无数据</td>
            <td>暂无数据</td>
          </tr>
        </template>
        <template>
          <tr v-for="(item,index) in declareInfoList" align="center"
              v-if="item.declareInfoEntity.projectAuditApplyStatus !==0 && item.declareInfoEntity.auditNoPass === 0">
            <td v-text="index+1"></td>
            <td>
              <!--项目最高级别-->
              <!--<span v-for="award in awardTypeList" v-if="item.declareAwardEntities.length > 0 && award.value === item.declareAwardEntities[0].awardType" v-text="award.label"></span>-->
              <span v-for="grade in declareGradeList" v-if="grade.value === item.declareInfoEntity.declareGrade" v-text="grade.label"></span>
            </td>
            <td>
              <!--项目编号-->
              <span v-text="item.declareInfoEntity.declareNum"></span>
            </td>
            <td v-text="item.declareInfoEntity.declareName"></td>
            <td>
              <!--项目最高级别-->
              <!--<span v-for="award in awardTypeList" v-if="item.declareAwardEntities.length > 0 && award.value === item.declareAwardEntities[0].awardType" v-text="award.label"></span>-->
              <span v-for="year in declareYearList" v-if="year.value === item.declareInfoEntity.declareYear" v-text="year.label"></span>
            </td>
            <td>
              <!--项目所属一级学科-->
              <span v-for="subject in subjectList" v-if="subject.subjectId === item.declareInfoEntity.subjectId" v-text="subject.subjectName"></span>
            </td>
            <!--负责人-->
            <td colspan="1">
                <span v-for="user in item.userPersonInfoEntities" >
                  <span v-text="user.sysUserEntity.name"></span>
                </span>
            </td>
            <td>
                <span v-for="user in item.userPersonInfoEntities" >
                  <span v-text="user.perStuNo"></span>
                </span>
            </td>
            <td>
              <span v-for="temp in declareTypeList" v-if="temp.value === item.declareInfoEntity.declareType" v-text="temp.label"></span>
            </td>
            <!--<td>-->
            <!--<span v-for="temp in declareType" v-if="temp.value === item.declareInfoEntity.declareType" v-text="temp.label"/>-->
            <!--</td>-->
            <!--学生人数-->
            <td v-text="item.declareStaffInfoEntities.length+1"></td>
            <td colspan="2">
              <span v-for="staff in item.declareStaffInfoEntities" v-text="staff.staffName+'  '" align="center"></span>
            </td>
            <td>
                <span v-for="teacher in userTeacherInfoEntities">
                  <span v-for="teacher2 in item.declareTeacherEntities"
                        v-if="teacher.userId === teacher2.userId"
                        v-text="teacher.sysUserEntity.name+'  '" align="center">
                  </span>
                </span>
            </td>
            <td>
              <span v-for="teacher in userTeacherInfoEntities">
                 <span v-for="teacher2 in item.declareTeacherEntities"
                       v-if="teacher.userId === teacher2.userId">
                        <!--v-text="teacher.teacherTitle" align="center">-->
                   <!--teacherTitleList-->
                     <span v-for="temp in teacherTitleList" v-if="temp.titleId === teacher.teacherTitle" v-text="temp.titleName"></span>
                  </span>
                </span>
            </td>
            <td>
              <!--总经费(元)-->
              <span v-if="item.declareAwardEntities.length > 0" v-text="item.declareAwardEntities[0].awardMoneyAll"></span>
            </td>
            <td>
              <!--区财政(元)-->
              <span v-if="item.declareAwardEntities.length > 0" v-text="item.declareAwardEntities[0].awardMoneyDistrict"></span>
            </td>
            <td>
              <!-- 校拨(元)-->
              <span v-if="item.declareAwardEntities.length > 0" v-text="item.declareAwardEntities[0].awardMoneySchool"></span>
            </td>
            <td colspan="2" class="info-content">
              <!-- 项目简介-->
              <span v-text="item.declareInfoEntity.declareDescribe"></span>
            </td>
            <td>
              <span v-text="item.declareInfoEntity.declareScoreAvg"></span>
            </td>
            <td colspan="1">
              <span v-for="inst in instituteList" v-if="item.declareInfoEntity.instituteId === inst.instituteId" v-text="inst.instituteName"></span>
            </td>
          </tr>
        </template>
        <!--<template>-->
        <!--<tr v-for="(item,index) in declareInfoList" align="center"-->
        <!--v-if="item.declareInfoEntity.projectAuditApplyStatus !==0 && item.declareInfoEntity.auditNoPass === 0">-->
        <!--<td v-text="index+1"></td>-->
        <!--<td v-text="item.declareInfoEntity.declareName"></td>-->
        <!--<td>-->
        <!--<span v-for="user in item.userPersonInfoEntities" >-->
        <!--<span v-text="user.sysUserEntity.name"></span>-->
        <!--</span>-->
        <!--</td>-->
        <!--<td>-->
        <!--<span v-for="temp in declareGroupTypeList" v-if="temp.value === item.declareInfoEntity.declareGroupType" v-text="temp.label"></span>-->
        <!--</td>-->
        <!--<td>-->
        <!--<span v-for="temp in declareType" v-if="temp.value === item.declareInfoEntity.declareType" v-text="temp.label"/>-->
        <!--</td>-->
        <!--<td v-text="item.declareStaffInfoEntities.length+1"></td>-->
        <!--<td colspan="3">-->
        <!--<span v-for="staff in item.declareStaffInfoEntities" v-text="staff.staffName+'  '" align="center"></span>-->
        <!--</td>-->
        <!--<td>-->
        <!--<span v-for="teacher in userTeacherInfoEntities">-->
        <!--<span v-for="teacher2 in item.declareTeacherEntities"-->
        <!--v-if="teacher.userId === teacher2.userId"-->
        <!--v-text="teacher.sysUserEntity.name+'  '" align="center">-->
        <!--</span>-->
        <!--</span>-->
        <!--</td>-->
        <!--<td><span v-text="item.declareInfoEntity.declareScoreAvg"></span></td>-->
        <!--</tr>-->
        <!--</template>-->
        <!--员工信息结束-->
        <tr align='center'  style="height: 3.0rem">
          <th>备注：</th>
          <td colspan="9" style="height: 1.5rem">已核实所有参赛队员学籍信息，均符合参赛要求</td>
          <th>二级学院领导签名：</th>
          <td colspan="10" style="height: 1.5rem"></td>
        </tr>
        <!--附件结束-->
        <tr align='center'>
          <td colspan="21" style="height: 1.2rem"></td>
        </tr>
      </table>
      <!--<table border="1" cellspacing="0" width="100%" class="table" id="out-table">-->
        <!--<tr align='center'>-->
          <!--<td colspan="20" style="height: 1.2rem"></td>-->
        <!--</tr>-->
        <!--<tr class="contents" align="center">-->
          <!--<th colspan="20">梧州学院2019“互联网+”大学生创新创业大创项目汇总表</th>-->
          <!--&lt;!&ndash;<th colspan="20">梧州学院{{declareYear}}“互联网+”大学生创新创业大创项目汇总表</th>&ndash;&gt;-->
        <!--</tr>-->
        <!--<tr align='center' style="height: 3.0rem">-->
          <!--<th>二级学院名称(盖章)：</th>-->
          <!--<td colspan="7" style="height: 1rem"></td>-->
          <!--<th>联系人：</th>-->
          <!--<td colspan="5" style="height: 1rem"></td>-->
          <!--<th>联系电话：</th>-->
          <!--<td colspan="5" style="height: 1rem"></td>-->
        <!--</tr>-->
        <!--<tr align='center' v-if="">-->
          <!--<th>序号</th>-->
          <!--<th>项目最高级别</th>-->
          <!--<th>项目编号</th>-->
          <!--<th>项目名称</th>-->
          <!--<th colspan="1">项目负责人姓名</th>-->
          <!--<th colspan="2">项目负责人学号</th>-->
          <!--&lt;!&ndash;<th>申报组别</th>&ndash;&gt;-->
          <!--<th>项目类型</th>-->
          <!--<th colspan="1">参与学生人数</th>-->
          <!--<th colspan="2">项目其他成员信息</th>-->
          <!--&lt;!&ndash;李四/112583030116,王五/112583030114  &ndash;&gt;-->
          <!--<th>指导老师姓名</th>-->
          <!--<th>指导教师职称</th>-->
          <!--<th>项目所属一级学科</th>-->
          <!--<th>总经费(元)</th>-->
          <!--<th>区财政(元)</th>-->
          <!--<th>校拨(元)</th>-->
          <!--<th colspan="2">项目简介</th>-->
          <!--<td>平均分</td>-->
        <!--</tr>-->

        <!--<template>-->
          <!--<tr align='center' v-if="declareInfoList.length === 0">-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td colspan="1">暂无数据</td>-->
            <!--<td colspan="2">暂无数据</td>-->
            <!--&lt;!&ndash;<th>申报组别</th>&ndash;&gt;-->
            <!--<td>暂无数据</td>-->
            <!--<td colspan="1">暂无数据</td>-->
            <!--<td colspan="2">暂无数据</td>-->
            <!--&lt;!&ndash;李四/112583030116,王五/112583030114  &ndash;&gt;-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td>暂无数据</td>-->
            <!--<td colspan="2">暂无数据</td>-->
            <!--<td>暂无数据</td>-->
          <!--</tr>-->
        <!--</template>-->
        <!--<template>-->
          <!--<tr v-for="(item,index) in declareInfoList" align="center"-->
              <!--v-if="item.declareInfoEntity.projectAuditApplyStatus !==0 && item.declareInfoEntity.auditNoPass === 0">-->
            <!--<td v-text="index+1"></td>-->
            <!--<td>-->
              <!--&lt;!&ndash;项目最高级别&ndash;&gt;-->
              <!--<span v-for="award in awardTypeList" v-if="item.declareAwardEntities.length > 0 && award.value === item.declareAwardEntities[0].awardType" v-text="award.label"></span>-->
            <!--</td>-->
            <!--<td>-->
              <!--&lt;!&ndash;项目编号&ndash;&gt;-->
              <!--<span v-text="item.declareInfoEntity.declareNum"></span>-->
            <!--</td>-->
            <!--<td v-text="item.declareInfoEntity.declareName"></td>-->
            <!--<td colspan="1">-->
                <!--<span v-for="user in item.userPersonInfoEntities" >-->
                  <!--<span v-text="user.sysUserEntity.name"></span>-->
                <!--</span>-->
            <!--</td>-->
            <!--<td colspan="2">-->
                <!--<span v-for="user in item.userPersonInfoEntities" >-->
                  <!--<span v-text="user.perStuNo"></span>-->
                <!--</span>-->
            <!--</td>-->
            <!--<td>-->
              <!--<span v-for="temp in declareTypeList" v-if="temp.value === item.declareInfoEntity.declareType" v-text="temp.label"></span>-->
            <!--</td>-->
            <!--&lt;!&ndash;<td>&ndash;&gt;-->
            <!--&lt;!&ndash;<span v-for="temp in declareType" v-if="temp.value === item.declareInfoEntity.declareType" v-text="temp.label"/>&ndash;&gt;-->
            <!--&lt;!&ndash;</td>&ndash;&gt;-->
            <!--<td colspan="1" v-text="item.declareStaffInfoEntities.length+1"></td>-->
            <!--<td colspan="2">-->
              <!--&lt;!&ndash;数据库没有员工学号阿老哥&ndash;&gt;-->
              <!--&lt;!&ndash;<span v-for="staff in item.declareStaffInfoEntities" v-text="staff.staffName+'/'+staff.perStuNo+'  '" align="center"></span>&ndash;&gt;-->
              <!--<span v-for="staff in item.declareStaffInfoEntities" v-text="staff.staffName+'  '" align="center"></span>-->
            <!--</td>-->
            <!--<td>-->
                <!--<span v-for="teacher in userTeacherInfoEntities">-->
                  <!--<span v-for="teacher2 in item.declareTeacherEntities"-->
                        <!--v-if="teacher.userId === teacher2.userId"-->
                        <!--v-text="teacher.sysUserEntity.name+'  '" align="center">-->
                  <!--</span>-->
                <!--</span>-->
            <!--</td>-->
            <!--<td>-->
              <!--<span v-for="teacher in userTeacherInfoEntities">-->
                  <!--<span v-for="teacher2 in item.declareTeacherEntities"-->
                        <!--v-if="teacher.userId === teacher2.userId">-->
                        <!--&lt;!&ndash;v-text="teacher.teacherTitle" align="center">&ndash;&gt;-->
                    <!--&lt;!&ndash;teacherTitleList&ndash;&gt;-->
                     <!--<span v-for="temp in teacherTitleList" v-if="temp.titleId === teacher.teacherTitle" v-text="temp.titleName"></span>-->
                  <!--</span>-->
                <!--</span>-->
            <!--</td>-->
            <!--<td>-->
              <!--&lt;!&ndash;项目所属一级学科&ndash;&gt;-->
              <!--<span v-for="subject in subjectList" v-if="subject.subjectId === item.declareInfoEntity.subjectId" v-text="subject.subjectName"></span>-->
            <!--</td>-->
            <!--<td>-->
              <!--&lt;!&ndash;总经费(元)&ndash;&gt;-->
              <!--<span v-if="item.declareAwardEntities.length > 0" v-text="item.declareAwardEntities[0].awardMoneyAll"></span>-->
            <!--</td>-->
            <!--<td>-->
              <!--&lt;!&ndash;区财政(元)&ndash;&gt;-->
              <!--<span v-if="item.declareAwardEntities.length > 0" v-text="item.declareAwardEntities[0].awardMoneyDistrict"></span>-->
            <!--</td>-->
            <!--<td>-->
              <!--&lt;!&ndash; 校拨(元)&ndash;&gt;-->
              <!--<span v-if="item.declareAwardEntities.length > 0" v-text="item.declareAwardEntities[0].awardMoneySchool"></span>-->
            <!--</td>-->
            <!--<td colspan="2" class="info-content">-->
              <!--&lt;!&ndash; 项目简介&ndash;&gt;-->
              <!--<span v-text="item.declareInfoEntity.declareDescribe"></span>-->
            <!--</td>-->
          <!--<td>-->
            <!--<span v-text="item.declareInfoEntity.declareScoreAvg"></span>-->
          <!--</td>-->
          <!--</tr>-->
        <!--</template>-->
        <!--&lt;!&ndash;<template>&ndash;&gt;-->
        <!--&lt;!&ndash;<tr v-for="(item,index) in declareInfoList" align="center"&ndash;&gt;-->
        <!--&lt;!&ndash;v-if="item.declareInfoEntity.projectAuditApplyStatus !==0 && item.declareInfoEntity.auditNoPass === 0">&ndash;&gt;-->
        <!--&lt;!&ndash;<td v-text="index+1"></td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td v-text="item.declareInfoEntity.declareName"></td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td>&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-for="user in item.userPersonInfoEntities" >&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-text="user.sysUserEntity.name"></span>&ndash;&gt;-->
        <!--&lt;!&ndash;</span>&ndash;&gt;-->
        <!--&lt;!&ndash;</td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td>&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-for="temp in declareGroupTypeList" v-if="temp.value === item.declareInfoEntity.declareGroupType" v-text="temp.label"></span>&ndash;&gt;-->
        <!--&lt;!&ndash;</td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td>&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-for="temp in declareType" v-if="temp.value === item.declareInfoEntity.declareType" v-text="temp.label"/>&ndash;&gt;-->
        <!--&lt;!&ndash;</td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td v-text="item.declareStaffInfoEntities.length+1"></td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td colspan="3">&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-for="staff in item.declareStaffInfoEntities" v-text="staff.staffName+'  '" align="center"></span>&ndash;&gt;-->
        <!--&lt;!&ndash;</td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td>&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-for="teacher in userTeacherInfoEntities">&ndash;&gt;-->
        <!--&lt;!&ndash;<span v-for="teacher2 in item.declareTeacherEntities"&ndash;&gt;-->
        <!--&lt;!&ndash;v-if="teacher.userId === teacher2.userId"&ndash;&gt;-->
        <!--&lt;!&ndash;v-text="teacher.sysUserEntity.name+'  '" align="center">&ndash;&gt;-->
        <!--&lt;!&ndash;</span>&ndash;&gt;-->
        <!--&lt;!&ndash;</span>&ndash;&gt;-->
        <!--&lt;!&ndash;</td>&ndash;&gt;-->
        <!--&lt;!&ndash;<td><span v-text="item.declareInfoEntity.declareScoreAvg"></span></td>&ndash;&gt;-->
        <!--&lt;!&ndash;</tr>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->
        <!--&lt;!&ndash;员工信息结束&ndash;&gt;-->
        <!--<tr align='center'  style="height: 3.0rem">-->
          <!--<th>备注：</th>-->
          <!--<td colspan="9" style="height: 1.5rem">已核实所有参赛队员学籍信息，均符合参赛要求</td>-->
          <!--<th>二级学院领导签名：</th>-->
          <!--<td colspan="9" style="height: 1.5rem"></td>-->
        <!--</tr>-->
        <!--&lt;!&ndash;附件结束&ndash;&gt;-->
        <!--<tr align='center'>-->
          <!--<td colspan="20" style="height: 1.2rem"></td>-->
        <!--</tr>-->
      <!--</table>-->
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button v-if="declareInfoList.length !== 0" type="primary" @click="exportDeclare">导出</el-button>
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
        declareInfo: {},
        instituteList: this.$store.state.user.institute,
        subjectList: this.$store.state.user.subject,
        gradeList: this.$store.state.user.grade,
        declareInfoList: [],
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
        moneyList: [],
        station: {},
        roleLists: this.$store.state.user.roleIdList,
        teacherTitleList: this.$store.state.user.title,
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
        awardTypeList: [
          {value: 1, label: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        declareYearList: [
          {value: 1, label: '一年期'},
          {value: 2, label: '二年期'}
        ],
        declareGradeList: [
          {value: 1, label: '国家级'},
          {value: 2, label: '自治区级'}
        ],
        eventLists: this.$store.state.eventLists,
        declareTypeList: [
          {value: 1, label: '创新训练项目'}, {value: 2, label: '创业训练项目'}, {value: 3, label: '创业实践项目'}
        ],
        declareGroupTypeList: [
          {value: 1, label: '创意组'}, {value: 2, label: '初创组'},
          {value: 3, label: '成长组'}, {value: 4, label: '师生共创组'},
          {value: 5, label: '"青年红色梦之旅"赛道'}
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
      init (id, time) {
        this.visible = true
        this.dataListLoading = true
        this.dataForm.id = id || 0
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/declare/info/list`),
            method: 'get',
            params: this.$http.adornParams({
              'instituteId': this.dataForm.id,
              'project_audit_apply_status_more': 2,
              'noPassStatus': 0,
              'noPass': 'audit_no_pass',
              'isDel': 0,
              'pageSize': 1000000,
              'currPage': 1,
              'declareTime': time.getFullYear()
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.declareInfoList = data.page.list
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
  .info-content{
    width: 20px;    /*根据自己项目进行定义宽度*/
    overflow: hidden;     /*设置超出的部分进行影藏*/
    text-overflow: ellipsis;     /*设置超出部分使用省略号*/
    white-space:nowrap ;    /*设置为单行*/
  }
</style>
