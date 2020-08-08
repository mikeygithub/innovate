<template>
  <el-dialog
    :title="'详情'"
    width="80%"
    v-loading="dataListLoading"
    @close="closeDialog"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row>
      <table border="1" cellspacing="0" width="100%" class="table">
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents" align="center">
          <th colspan="8">
            项目基本信息
          </th>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="1">项目名称</th>
          <td colspan="7"><span style="font-size: 1.1rem; font-weight: bolder" v-text="projectInfo.projectName" align="center"></span></td>
        </tr>
        <tr align='center'>
          <th>所属行业</th>
          <td v-for="item in statusList"
              :key="item.value"
              v-if="item.value === projectInfo.projectIndustry">
            <span v-text="item.label" align="center"></span>
          </td>
          <th>企业类型</th>
          <td v-for="item in projectTypeList"
              :key="item.value"
              v-if="item.value === projectInfo.projectType">
            <span v-text="item.label" align="center"></span>
          </td>
          <th>初始投资资金</th>
          <td><span v-text="projectInfo.projectInitMoney+'（万元）'" align="center"></span></td>
          <th>企业状态</th>
          <!--1-在驻，2-孵化成功出园，3-孵化失败出园-->
          <td><span v-for="item in projectStatusList"
                    :key="item.value"
                    v-if="projectInfo.projectStatus === item.value"
                    v-text="item.label" align="center"></span></td>
        </tr>

        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>

        <tr align='center'>
          <th>拥有有效知识产权</th>
          <td><span v-text="projectInfo.projectProperty===1?'是':'否'" align="center"></span></td>
          <th>是否已进行工商注册</th>
          <td ><span v-text="projectInfo.projectRegStatus===1?'是':'否'" align="center"></span></td>
          <th>注册时间</th>
          <td>
            <span v-text="projectInfo.projectRegStatus===1?(projectInfo.projectRegDate || '').split(' ')[0]: '未工商注册'" align="center"></span>
          </td>
          <th>注册金额</th>
          <td><span v-text="projectInfo.projectRegStatus===1?projectInfo.projectRegMoney+'（万元）': '未工商注册'" align="center"></span></td>
        </tr>

        <tr align='center'>
          <th>项目成立时间</th>
          <td><span v-text="(projectInfo.projectSetDate || '').split(' ')[0]" align="center"></span></td>
          <th>项目入驻时间</th>
          <td><span v-text="(projectInfo.projectInDate || '').split(' ')[0]" align="center"></span></td>
          <th>工位编号</th>
          <td>
            <span v-if="projectInfo.stationId === null">未分配工位号</span>
            <span v-for="station in stationList" v-if="projectInfo.stationId === station.stationId" v-text="station.stationNum" align="center"></span>
          </td>
          <th>项目办公场地面积</th>
          <td>
            <span v-if="projectInfo.stationId === null">未分配工位号</span>
            <span v-for="station in stationList" v-if="projectInfo.stationId === station.stationId" v-text="station.stationArea + '（平方米）'" align="center"></span>
          </td>
        </tr>

        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>是否就业见习基地</th>
          <td><span v-text="projectInfo.projectBase===1?'是':'否'" align="center"></span></td>
          <th>本年度吸纳就业人数</th>
          <td><span v-text="projectInfo.perNumAbsorbEm" align="center"></span></td>
          <th>本年度吸纳实习见习人数</th>
          <td><span v-text="projectInfo.perNumAbsorbPt" align="center"></span></td>
          <th>成员中的应届毕业生人数</th>
          <td><span v-text="projectInfo.perNumFresh" align="center"></span></td>
        </tr>
        <tr align='center'>
          <th>提供勤工助学岗位数</th>
          <td><span v-text="projectInfo.projectStudyNum" align="center"></span></td>
          <th>累计带动就业人数</th>
          <td><span v-text="projectInfo.perNumDriveEmNum" align="center"></span></td>
          <th>累计带动实习见习人数</th>
          <td><span v-text="projectInfo.perNumDrivePtNum" align="center"></span></td>
          <th>成员中的历届毕业生人数</th>
          <td><span v-text="projectInfo.perNumPast" align="center"></span></td>
        </tr>
        <tr align='center'>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>项目经营内容</th>
          <td colspan="7">
            <p v-text="projectInfo.projectContent" align="center" style="padding: 0 1rem"></p>
          </td>
        </tr>

        <!--项目负责人开始-->
          <tr align='center'>
            <td colspan="8" style="height: 1.2rem"></td>
          </tr>
          <tr class="contents"><th colspan="8">负责人信息</th></tr>
          <tr align='center'>
            <td colspan="8" style="height: 1.2rem"></td>
          </tr>
          <template v-for="perInfo in perInfoList">
          <tr align='center'>
            <th>姓名</th>
            <td><span v-text="perInfo.sysUserEntity.username" align="center"></span></td>
            <th>性别</th>
            <template v-for="sex in sexList">
              <td v-if="perInfo.perSex === sex.value" v-text="sex.label"></td>
            </template>
            <th>企业职务</th>
            <td><span v-text="perInfo.perPost" align="center"></span></td>
            <th>联系电话</th>
            <td><span v-text="perInfo.sysUserEntity.mobile" align="center"></span></td>
          </tr>

          <tr align='center'>
            <th>政治面貌</th>
            <td><span v-text="perInfo.perPoliticsType" align="center"></span></td>
            <th>学号</th>
            <td><span v-text="perInfo.perStuNo" align="center"></span></td>
            <th>所在二级学院</th>
            <td>
              <span v-for="item in instituteList"
                    v-if="item.instituteId === perInfo.instituteId"
                    v-text="item.instituteName"
                    align="center"></span>
            </td>
            <th>QQ号码</th>
            <td><span v-text="perInfo.perQq" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th>所在年级</th>
            <td>
              <span v-for="item in gradeList"
                    v-if="item.gradeId === perInfo.gradeId"
                    v-text="item.gradeName"
                    align="center">
              </span>
            </td>
            <th>所在班级</th>
            <td><span v-text="perInfo.perClassNo" align="center"></span></td>
            <th>所在宿舍</th>
            <td><span v-text="perInfo.perCormNo" align="center"></span></td>
            <th>个人电子邮箱</th>
            <td><span v-text="perInfo.sysUserEntity.email" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th colspan="1">在校期间担任学生职务情况</th>
            <td colspan="3"><span v-text="perInfo.perSchoolPost" align="center"></span></td>
<!--            <th colspan="1">身份证号码</th>-->
<!--            <td colspan="3"><span v-text="perInfo.perCardNo" align="center"></span></td>-->
          </tr>
          <tr align='center'>
            <th colspan="2">在校期间所获等级证书及技能证书</th>
            <td colspan="6"><span v-text="perInfo.perSchoolHonor" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th colspan="2">社会实践主要成绩简述</th>
            <td colspan="6"><span v-text="perInfo.perSocialPractice" align="center"></span></td>
          </tr>
        </template>
        <!--项目负责人结束-->

        <!--企业法人开始-->
          <tr align='center'>
            <td colspan="8" style="height: 1.2rem"></td>
          </tr>
          <tr class="contents"><th colspan="8">法人代表信息</th></tr>
          <tr align='center'>
            <td colspan="8" style="height: 1.2rem"></td>
          </tr>
          <template v-for="perInfo in perInfoList">
          <tr align='center'>
            <th>姓名</th>
            <td><span v-text="perInfo.sysUserEntity.username" align="center"></span></td>
            <th>性别</th>
            <template v-for="sex in sexList">
              <td v-if="perInfo.perSex === sex.value" v-text="sex.label"></td>
            </template>
            <th>政治面貌</th>
            <td><span v-text="perInfo.perPoliticsType" align="center"></span></td>
            <th>所在单位职务</th>
            <td><span v-text="perInfo.perPost" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th>所在年级</th>
            <td>
              <span v-for="item in gradeList"
                    v-if="item.gradeId === perInfo.gradeId"
                    v-text="item.gradeName"
                    align="center">
              </span>
            </td>
            <th>所在班级/单位</th>
            <td><span v-text="perInfo.perSchoolPost" align="center"></span></td>
            <th>联系电话</th>
            <td><span v-text="perInfo.sysUserEntity.mobile" align="center"></span></td>
            <th >邮箱</th>
            <td><span v-text="perInfo.sysUserEntity.email" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th>籍贯</th>
            <td colspan="7"><span v-text="perInfo.perNative" align="center"></span></td>
          </tr>
        </template>
        <!--企业法人结束-->

        <!--员工信息开始-->
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents"><th colspan="8">员工信息</th></tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>姓名</th>
          <th>性别</th>
          <th>全职/兼职</th>
          <th colspan="2">企业职务</th>
          <th colspan="2">联系电话</th>
          <th>所在班级/单位</th>
        </tr>
        <template>
          <tr v-for="item in staffList" align="center">
            <td>{{item.staffName}}</td>
            <template v-for="sex in sexList">
              <td v-if="item.staffSex=== sex.value" v-text="sex.label"></td>
            </template>
            <td v-text="item.staffType===1?'全职':'兼职'"></td>
            <td colspan="2">{{item.staffPost}}</td>
            <td colspan="2">{{item.staffTel}}</td>
            <td>{{item.staffClassNo}}</td>
          </tr>
        </template>
        <!--员工信息结束-->

        <!--导师信息开始-->
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents" align="center">
          <th colspan="8">指导老师信息</th>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>姓名</th>
          <th>性别</th>
          <th>职务</th>
          <th>职称</th>
          <th>邮箱</th>
          <th>联系电话</th>
<!--          <th>身份证号</th>-->
          <th>所在单位/二级学院</th>
        </tr>
        <template v-for="item in userTeacherInfoEntities">
          <tr align="center">
            <td v-text="item.sysUserEntity.username"></td>
            <template v-for="sex in sexList">
              <td v-if="item.teacherSex === sex.value" v-text="sex.label"></td>
            </template>
            <td v-text="item.teacherPost"></td>
            <td v-text="item.teacherTitle"></td>
            <td>{{item.sysUserEntity.email}}</td>
            <td>{{item.sysUserEntity.mobile}}</td>
<!--            <td v-text="item.teacherCardNo"></td>-->
            <td v-for="institute in instituteList"
                v-if="item.instituteId === institute.instituteId"
                v-text="institute.instituteName">
            </td>
          </tr>
        </template>
        <!--导师信息结束-->

        <!--成果/奖项开始-->
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="8">所获成果/奖项</th>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr>
          <th colspan="2">获奖时间</th>
          <th colspan="2">奖项等级</th>
          <th colspan="2">奖项名称</th>
          <th colspan="2">奖项等级</th>
        </tr>
        <template>
          <tr v-for="item in awardList" align="center">
            <td colspan="2" v-text="(item.awardTime || '').split(' ')[0]"></td>
            <td colspan="2"
                v-for="type in awardTypeList"
                :key="type.value"
                v-if="type.value === item.awardType"
                v-text="type.label">
            </td>
            <td colspan="2" v-text="item.awardName"></td>
            <td colspan="2"
                v-for="type in awardRankList"
                :key="type.value"
                v-if="type.value === item.awardRank"
                v-text="type.label">
            </td>
          </tr>
        </template>
        <!--成果/奖项结束-->

        <!--资金收入开始-->
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="8">资金收入</th>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th colspan="2">收入类型</th>
          <th colspan="2">投资方名称</th>
          <th colspan="2">获得投资时间</th>
          <th colspan="2">投资金额(万元)</th>
        </tr>
        <!--补助类型：1投资，2管理服务，3房租和宽带，4水电费，5一次性创业，6吸纳困难群体，7社会保险，8创业担保贷款-->
        <template>
          <tr v-for="item in subList"
              align="center">
            <template v-for="type in moneyTypeList"
                      v-if="type.value === item.subType">
              <td colspan="2" v-text="type.label"></td>
            </template>
            <td colspan="2" v-text="item.subSource"></td>
            <td colspan="2" v-text="(item.subTime || '').split(' ')[0]"></td>
            <td colspan="2" v-text="item.subMoney"></td>
          </tr>
        </template>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <!--资金收入结束-->

        <!--附件开始-->
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="8">附件</th>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th colspan="6">附件名</th>
          <th colspan="2">操作</th>
        </tr>
        <template>
          <tr v-for="item in attachLists"
              align="center">
            <td colspan="6" v-text="item.attachName"></td>
            <td colspan="2"><button @click="attachDown(item)">下载</button></td>
          </tr>
        </template>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <!--附件结束-->


        <!--评分开始-->
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="8">评分详情</th>
        </tr>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th colspan="2">评委</th>
          <th colspan="2">流程</th>
          <th colspan="2">分数</th>
          <th colspan="2">建议</th>
        </tr>
        <template>
          <tr v-for="(item, index) in reviewList"
              align="center">
            <td colspan="2" v-text="'评委:' + ++index"></td>
            <template v-for="type in reviewTypeList"
                      v-if="type.value === item.apply">
              <td colspan="2" v-text="type.label"></td>
            </template>
            <td colspan="2" v-text="item.score"></td>
            <td colspan="2" v-text="item.opinion"></td>
          </tr>
        </template>
        <tr align='center'>
          <td colspan="8" style="height: 1.2rem"></td>
        </tr>
        <!--评分结束-->

      </table>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import axios from 'axios'
  export default {
    data () {
      return {
        visible: false,
        dataListLoading: false,
        projectInfo: {},
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
        userTeacherInfoEntities: [],
        moneyList: [],
        station: {},
        stationList: [],
        teacherSet: '',
        teacherList: [],
        awardList: [],
        staffList: [],
        reviewList: [],
        attachLists: [],
        subList: [],
        perInfoList: [],
        reviewTypeList: [
          {value: 'project_audit_apply_status', label: '训练申请流程'},
          {value: 'project_base_apply_status', label: '基地申请流程'},
          {value: 'project_match_apply_status', label: '比赛申请流程'},
          {value: 'project_finish_apply_status', label: '结题申请流程'}
        ],
        statusList: [
          {value: 1, label: '农、林、牧、渔业'}, {value: 2, label: '采矿业'},
          {value: 3, label: '制造业'}, {value: 4, label: '电力、热力、燃气及水的生产和供应业'},
          {value: 5, label: '环境和公共设施管理业'}, {value: 6, label: '建筑业'},
          {value: 7, label: '交通运输、仓储业和邮政业'}, {value: 8, label: '信息传输、计算机服务和软件业'},
          {value: 9, label: '批发和零售业'}, {value: 10, label: '住宿、餐饮业'},
          {value: 11, label: '金融、保险业'}, {value: 12, label: '房地产业'},
          {value: 13, label: '租赁和商务服务业'}, {value: 14, label: '科学研究、技术服务和地质勘查业'},
          {value: 15, label: '水利、环境和公共设施管理业'}, {value: 16, label: '居民服务和其他服务业'},
          {value: 17, label: '教育'}, {value: 18, label: '卫生、社会保障和社会服务业'},
          {value: 19, label: '文化、体育、娱乐业'}, {value: 20, label: '综合（含投资类、主业不明显）'},
          {value: 21, label: '其它'}
        ],
        projectStatusList: [{
          value: 0,
          label: '未入驻'
        }, {
          value: 1,
          label: '在驻'
        }, {
          value: 2,
          label: '孵化成功出园'
        }, {
          value: 3,
          label: '孵化失败出园'
        }],
        projectTypeList: [
          {value: 1, label: '先进制造业'}, {value: 2, label: '现代服务业'},
          {value: 3, label: '现代农业'}, {value: 4, label: '批发零售业'},
          {value: 5, label: '其他'}
        ],
        sexList: [
          {value: 1, label: '男'}, {value: 2, label: '女'}
        ],
        instinctList: [{
          value: 1,
          label: '正高级'
        }, {
          value: 2,
          label: '副高级'
        }, {
          value: 3,
          label: '中级'
        }, {
          value: 4,
          label: '初级'
        }, {
          value: 5,
          label: '无职称'
        }],
        moneyTypeList: [
          {value: 1, label: '投资'}, {value: 2, label: '管理服务'},
          {value: 3, label: '房租和宽带'}, {value: 4, label: '水电费'},
          {value: 5, label: '一次性创业'}, {value: 6, label: '吸纳困难群体'},
          {value: 7, label: '社会保险'}, {value: 8, label: '创业担保贷款'}
        ],
        awardTypeList: [
          {value: 1, abel: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        awardRankList: [
          {value: 1, label: '特等奖'}, {value: 2, label: '一等奖'},
          {value: 3, label: '二等奖'}, {value: 4, label: '三等奖'},
          {value: 5, label: '优秀奖'}
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
            url: this.$http.adornUrl(`/innovate/project/info/info`),
            method: 'get',
            params: this.$http.adornParams({
              'projectId': this.dataForm.id
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.projectInfo = data.projectInfo.projectInfoEntity
              if (data.projectInfo.projectTeacherEntities.length > 0) {
                this.teacherList = data.projectInfo.projectTeacherEntities
                this.userTeacherInfoEntities = data.userTeacherInfoEntities
              } else {
                this.teacherList = []
              }
              if (data.projectInfo.userPersonInfoEntities.length > 0) {
                this.perInfoList = data.projectInfo.userPersonInfoEntities
              } else {
                this.perInfoList = []
                this.perInfoList.sysUserEntity = []
              }
              if (data.projectInfo.projectAttachEntities.length > 0) {
                this.attachLists = data.projectInfo.projectAttachEntities
              } else {
                this.attachLists = []
              }
              if (data.projectInfo.projectSubMoneyEntities.length > 0) {
                this.subList = data.projectInfo.projectSubMoneyEntities
              } else {
                this.subList = []
              }
              if (data.projectInfo.projectStaffInfoEntities.length > 0) {
                this.staffList = data.projectInfo.projectStaffInfoEntities
              } else {
                this.staffList = []
              }
              if (data.projectInfo.projectAwardEntities.length > 0) {
                this.awardList = data.projectInfo.projectAwardEntities
              } else {
                this.awardList = []
              }
              if (data.projectInfo.projectReviewEntities.length > 0) {
                this.reviewList = data.projectInfo.projectReviewEntities
              } else {
                this.reviewList = []
              }
              if (data.baseProjectStationEntities.length > 0) {
                this.stationList = data.baseProjectStationEntities
              } else {
                this.stationList = []
              }
              this.dataListLoading = false
            }
          })
        } else {
          this.dataListLoading = false
        }
      },
      attachDown (attach) {
        let param = new URLSearchParams()
        let url = this.$http.adornUrl(`/innovate/project/attach/download`)
        param.append('attachId', attach.attachId)
        param.append('token', this.$cookie.get('token'))
        axios.post(url, param, {
          responseType: 'blob'
        }).then(response => {
          if (!response) {
            return
          }
          let url = window.URL.createObjectURL(new Blob([response.data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', attach.attachName)

          document.body.appendChild(link)
          link.click()
        }).catch(err => {
          console.log(err)
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
    width: 100%;
  }
</style>
