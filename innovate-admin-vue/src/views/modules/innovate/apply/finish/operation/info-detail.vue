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
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents" align="center">
          <th colspan="10">
            项目基本信息
          </th>
        </tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">申报名称</th>
          <td colspan="8"><span style="font-size: 1.1rem; font-weight: bolder" v-text="finishInfo.finishName" align="center"></span></td>
        </tr>
        <!--<tr align='center'>-->
          <!--<th colspan="2">赛事名</th>-->
          <!--<td colspan="8" v-for="item in eventLists"-->
              <!--:key="item.eventName"-->
              <!--v-if="item.eventId === matchInfo.eventId">-->
            <!--<span v-text="item.eventName" align="center"></span>-->
          <!--</td>-->
        <!--</tr>-->
        <!--<tr align='center'>-->
          <!--<th colspan="2">申报类型</th>-->
          <!--<td colspan="8" v-for="item in finishTypeList"-->
              <!--:key="item.value"-->
              <!--v-if="item.value === finishInfo.finishType">-->
            <!--<span v-text="item.label" align="center"></span>-->
          <!--</td>-->
        <!--</tr>-->
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">申报时间</th>
          <td colspan="3"><span style="font-size: 1.1rem; font-weight: bolder" v-text="finishInfo.finishTime" align="center"></span></td>
        <!--</tr>-->
        <!--<tr align='center' style="height: 2.5rem">-->
          <th colspan="2">申报平均分</th>
          <td colspan="3"><span style="font-size: 1.1rem; font-weight: bolder" v-text="finishInfo.finishScoreAvg" align="center"></span></td>
        </tr>
        <tr align='center' style="height: 10rem">
          <th colspan="2">项目概述</th>
          <td colspan="8">
            <span v-text="finishInfo.finishDescribe" align="center"></span>
          </td>
        </tr>
        <!--<tr align='center'>-->
          <!--<th colspan="2">项目创新之处和亮点</th>-->
          <!--<td colspan="8">-->
            <!--<span v-text="finishInfo.finishBrightSpot" align="center"></span>-->
          <!--</td>-->
        <!--</tr>-->
        <!--<tr align='center'>-->
          <!--<th colspan="2">项目预期或已取得的成果</th>-->
          <!--<td colspan="8">-->
            <!--<span v-text="matchInfo.matchExpect" align="center" style="padding: 0 1rem"></span>-->
          <!--</td>-->
        <!--</tr>-->

        <!--项目负责人开始-->
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents"><th colspan="10">参赛项目负责人信息</th></tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <template v-for="perInfo in perInfoList">
          <tr align='center'>
            <th>姓名</th>
            <td><span v-text="perInfo.sysUserEntity.name" align="center"></span></td>
            <th>性别</th>
            <template v-for="sex in sexList">
              <td v-if="perInfo.perSex === sex.value" v-text="sex.label"></td>
            </template>
            <th>企业职务</th>
            <td><span v-text="perInfo.perPost" align="center"></span></td>
            <th>联系电话</th>
            <td><span v-text="perInfo.sysUserEntity.mobile" align="center"></span></td>
            <th>QQ号码</th>
            <td><span v-text="perInfo.perQq" align="center"></span></td>
          </tr>

          <tr align='center'>
            <th>政治面貌</th>
            <td><span v-text="perInfo.perPoliticsType" align="center"></span></td>
            <th>学号</th>
            <td><span v-text="perInfo.perStuNo" align="center"></span></td>
            <th>所在二级学院</th>
            <td>
              <span v-for="item in instituteList"
                    v-if="item.instituteId === perInfo.sysUserEntity.instituteId"
                    v-text="item.instituteName"
                    align="center"></span>
            </td>
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
          </tr>
          <tr align='center'>
            <th>所在宿舍</th>
            <td><span v-text="perInfo.perCormNo" align="center"></span></td>
            <th>个人电子邮箱</th>
            <td colspan="2"><span v-text="perInfo.sysUserEntity.email" align="center"></span></td>
<!--            <th colspan="1">身份证号码</th>-->
<!--            <td colspan="4"><span v-text="perInfo.perCardNo" align="center"></span></td>-->
          </tr>
          <tr align='center'>
            <th colspan="2">在校期间担任学生职务情况</th>
            <td colspan="8"><span v-text="perInfo.perSchoolPost" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th colspan="2">在校期间所获等级证书及技能证书</th>
            <td colspan="8"><span v-text="perInfo.perSchoolHonor" align="center"></span></td>
          </tr>
          <tr align='center'>
            <th colspan="2">社会实践主要成绩简述</th>
            <td colspan="8"><span v-text="perInfo.perSocialPractice" align="center"></span></td>
          </tr>
        </template>
        <!--项目负责人结束-->

        <!--员工信息开始-->
        <!--<tr align='center'>-->
          <!--<td colspan="10" style="height: 1.2rem"></td>-->
        <!--</tr>-->
        <!--<tr class="contents"><th colspan="10">项目参与者信息</th></tr>-->
        <!--<tr align='center'>-->
          <!--<td colspan="10" style="height: 1.2rem"></td>-->
        <!--</tr>-->
        <!--<tr align='center'>-->
          <!--<th>姓名</th>-->
          <!--<th>性别</th>-->
          <!--<th>学号</th>-->
          <!--<th>所在二级学院</th>-->
          <!--<th>所在年级</th>-->
          <!--<th>所在班级</th>-->
          <!--<th>所在宿舍</th>-->
          <!--<th>联系电话</th>-->
          <!--<th>QQ号</th>-->
          <!--<th>个人电子邮箱</th>-->
        <!--</tr>-->
        <!--<template>-->
          <!--<tr v-for="item in staffList" align="center">-->
            <!--<td v-text="item.staffName"></td>-->
            <!--<td v-for="sex in sexList"-->
                <!--:key="sex.value"-->
                <!--v-if="item.staffSex=== sex.value"-->
                <!--v-text="sex.label">-->
            <!--</td>-->
            <!--<td v-text="item.staffStuNo"></td>-->
            <!--<td>-->
              <!--<span v-for="institute in instituteList"-->
                    <!--v-if="item.instituteId === institute.instituteId"-->
                    <!--v-text="institute.instituteName"-->
                    <!--align="center"></span>-->
            <!--</td>-->
            <!--<td>-->
              <!--<span v-for="grade in gradeList"-->
                    <!--v-if="item.gradeId === grade.gradeId"-->
                    <!--v-text="grade.gradeName"-->
                    <!--align="center">-->
              <!--</span>-->
            <!--</td>-->
            <!--<td colspan="1" v-text="item.staffClassNo"></td>-->
            <!--<td colspan="1" v-text="item.staffCormNo"></td>-->
            <!--<td colspan="1" v-text="item.staffTel"></td>-->
            <!--<td colspan="1" v-text="item.staffQq"></td>-->
            <!--<td colspan="1" v-text="item.staffEmail"></td>-->
          <!--</tr>-->
        <!--</template>-->
        <!--员工信息结束-->

        <!--导师信息开始-->
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents" align="center">
          <th colspan="10">指导老师信息</th>
        </tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>姓名</th>
          <th>性别</th>
          <th colspan="2">所在单位/二级学院</th>
          <th>职务</th>
          <th>职称</th>
          <th colspan="2">邮箱</th>
          <th colspan="2">联系电话</th>
          <!--<th colspan="2">身份证号</th>-->
        </tr>
        <template v-for="item in userTeacherInfoEntities">
          <tr align="center">
            <td v-text="item.sysUserEntity.name"></td>
            <td v-for="sex in sexList"
                :key="sex.value"
                v-if="item.teacherSex === sex.value"
                v-text="sex.label">
            </td>
            <td colspan="2"
                v-for="institute in instituteList"
                v-if="item.sysUserEntity.instituteId === institute.instituteId"
                v-text="institute.instituteName">
            </td>
            <td v-text="item.teacherPost"></td>
            <td colspan="1">
              <span v-for="teacherTitle in teacherTitleList" v-if="item.teacherTitle === teacherTitle.titleId" v-text="teacherTitle.titleName"></span>
            </td>
            <td colspan="2">{{item.sysUserEntity.email}}</td>
            <td colspan="2">{{item.sysUserEntity.mobile}}</td>
            <!--<td colspan="2" v-text="item.teacherCardNo"></td>-->
          </tr>
        </template>
        <!--导师信息结束-->

        <!--&lt;!&ndash;成果/奖项开始&ndash;&gt;-->
        <!--<tr align='center'>-->
          <!--<td colspan="10" style="height: 1.2rem"></td>-->
        <!--</tr>-->
        <!--<tr align="center" class="contents">-->
          <!--<th colspan="10">所获成果/奖项</th>-->
        <!--</tr>-->
        <!--<tr align='center'>-->
          <!--<td colspan="10" style="height: 1.2rem"></td>-->
        <!--</tr>-->
        <!--<tr>-->
          <!--<th colspan="2">获奖等级</th>-->
          <!--<th colspan="2">奖项名称</th>-->
          <!--<th colspan="1">奖项等级</th>-->
          <!--<th colspan="1">所获奖金（万元）</th>-->
          <!--<th colspan="2">获奖时间</th>-->
          <!--<th colspan="1">证书</th>-->
          <!--<th colspan="1">操作</th>-->
        <!--</tr>-->
        <!--<template>-->
          <!--<tr v-for="item in awardList" align="center">-->
            <!--<td colspan="2"-->
                <!--v-for="type in awardTypeList"-->
                <!--:key="type.value"-->
                <!--v-if="type.value === item.awardType"-->
                <!--v-text="type.label">-->
            <!--</td>-->
            <!--<td colspan="2" v-text="item.awardName"></td>-->
            <!--<td colspan="1"-->
                <!--v-for="type in awardRankList"-->
                <!--:key="type.value"-->
                <!--v-if="type.value === item.awardRank"-->
                <!--v-text="type.label">-->
            <!--</td>-->
            <!--<td colspan="1" v-text="item.awardMoney"></td>-->
            <!--<td colspan="2" v-text="(item.awardTime || '').split(' ')[0]"></td>-->
            <!--<td colspan="1" v-text="item.awardFileName"></td>-->
            <!--<td colspan="1"><button @click="awardDown(item)">下载</button></td>-->
          <!--</tr>-->
        <!--</template>-->
        <!--成果/奖项结束-->

        <!--指导老师签署意见开始-->
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="10">指导老师签署意见</th>
        </tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th colspan="2">签署意见</th>
          <th colspan="8" v-if="signingOpinionEntity!==null" v-text="signingOpinionEntity.signingOpinion"></th>
        </tr>
        <!--指导老师签署意见结束-->

        <!--附件开始-->
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="10">附件</th>
        </tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th colspan="7">附件名</th>
          <th colspan="3">操作</th>
        </tr>
        <!--补助类型：1投资，2管理服务，3房租和宽带，4水电费，5一次性创业，6吸纳困难群体，7社会保险，8创业担保贷款-->
        <template>
          <tr v-for="item in attachLists"
              align="center">
            <td colspan="7" v-text="item.attachName"></td>
            <td colspan="3"><el-button @click="attachDown(item)" :loading="downloadLoading"><span v-text="downloadText"></span></el-button></td>
          </tr>
        </template>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <!--附件结束-->

        <!--评分开始-->
        <template v-for="item in roleLists"
                  v-if="item === 5 || item === 1">
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr align="center" class="contents">
            <th colspan="10">评分详情</th>
          </tr>
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr align='center'>
            <td colspan="5">平均分</td>
            <td colspan="5" v-if="finishInfo.finishScoreAvg!==null" v-text="finishInfo.finishScoreAvg + '分'"></td>
            <td colspan="5" v-if="finishInfo.finishScoreAvg===null" v-text="'暂无'"></td>
          </tr>
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr align='center'>
            <th colspan="2">评委</th>
            <th colspan="3">流程</th>
            <th colspan="2">分数</th>
            <th colspan="3">建议</th>
          </tr>
          <template v-for="type in reviewTypeList">
            <template v-for="(item, index) in reviewList"
                      v-if="type.value === item.apply && type.value === 'project_finish_apply_status'" >
              <tr align="center">
                <td colspan="2" v-text="'评委:' + (index + 1)"></td>
                <td colspan="3" v-text="type.label"></td>
                <td colspan="2" v-text="item.score"></td>
                <td colspan="3" v-text="item.opinion"></td>
              </tr>
            </template>
          </template>
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
        </template>
        <!--评分结束-->
      </table>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataListLoading: false,
        downloadLoading: false,
        downloadText: '下载',
        finishInfo: {},
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
        userTeacherInfoEntities: [],
        moneyList: [],
        station: {},
        roleLists: this.$store.state.user.roleIdList,
        stationList: [],
        teacherSet: '',
        teacherList: [],
        // awardList: [],
        staffList: [],
        reviewList: [],
        attachLists: [],
        subList: [],
        perInfoList: [],
        signingOpinionEntity: {
          signingOpinion: '',
          signingOpinionTime: ''
        },
        reviewTypeList: [
          {value: 'project_audit_apply_status', label: '训练申请流程'},
          {value: 'project_base_apply_status', label: '中心申请流程'},
          {value: 'project_match_apply_status', label: '比赛申请流程'},
          {value: 'project_finish_apply_status', label: '结题申请流程'}
        ],
        eventLists: this.$store.state.eventLists,
        finishTypeList: [
          {value: 1, label: '创新训练项目'},
          {value: 2, label: '创业训练项目'},
          {value: 3, label: '创业实践项目'}
        ],
        sexList: [
          {value: 1, label: '男'}, {value: 2, label: '女'}
        ],
        teacherTitleList: this.$store.state.user.title,
        dataForm: {
          id: 0,
          teacherName: '',
          teacherSex: '',
          teacherUnit: '',
          teacherWorkStatus: '',
          teacherPhone: '',
          teacherJob: '',
          teacherInstinct: '',
          signingOpinionEntity: {
            signingOpinion: '',
            signingOpinionTime: ''
          },
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
            url: this.$http.adornUrl(`/innovate/finish/info/info`),
            method: 'get',
            params: this.$http.adornParams({
              'finishId': this.dataForm.id
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.finishInfo = data.finishInfo.finishInfoEntity
              if (data.finishInfo.finishTeacherEntities.length > 0) {
                this.teacherList = data.finishInfo.finishTeacherEntities
                this.userTeacherInfoEntities = data.userTeacherInfoEntities
              } else {
                this.teacherList = []
              }
              if (data.finishInfo.userPersonInfoEntities.length > 0) {
                this.perInfoList = data.finishInfo.userPersonInfoEntities
              } else {
                this.perInfoList = []
                this.perInfoList.sysUserEntity = []
              }
              if (data.finishInfo.finishAttachEntities.length > 0) {
                this.attachLists = data.finishInfo.finishAttachEntities
              } else {
                this.attachLists = []
              }
              if (data.finishInfo.finishStaffInfoEntities.length > 0) {
                this.staffList = data.finishInfo.finishStaffInfoEntities
              } else {
                this.staffList = []
              }
              if (data.finishInfo.finishReviewEntities.length > 0) {
                this.reviewList = data.finishInfo.finishReviewEntities
              } else {
                this.reviewList = []
              }
              // 获取指导老师签署意见
              this.$http({
                url: this.$http.adornUrl(`/innovate/finish/signingopinion/info`),
                method: 'get',
                params: this.$http.adornParams({
                  'finishId': this.dataForm.id
                })
              }).then(({data}) => {
                if (data && data.code === 0) {
                  this.signingOpinionEntity = data.sighingOpinion
                }
              })
              this.dataListLoading = false
            }
          })
        } else {
          this.dataListLoading = false
        }
      },
      awardDown (award) {
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/innovate/finish/attach/download`),
          method: 'post',
          params: this.$httpFile.adornParams({
            'filePath': award.awardPath
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
          link.setAttribute('download', award.awardFileName)
          document.body.appendChild(link)
          link.click()
        }).catch(err => {
          console.log(err)
        })
      },
      attachDown (attach) {
        this.downloadLoading = true
        this.downloadText = '正在下载'
        this.$notify({
          title: '下载提示',
          message: '文件正在后台下载,请您稍后!',
          duration: 0,
          type: 'success'
        })
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/innovate/finish/attach/download`),
          method: 'post',
          params: this.$httpFile.adornParams({
            'filePath': attach.attachPath
          })
        }).then(response => {
          if (!response) {
            this.$notify({
              title: '下载提示',
              message: '下载失败',
              duration: 0,
              type: 'error'
            })
            this.downloadLoading = false
            return
          }
          let url = window.URL.createObjectURL(new Blob([response.data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', attach.attachName)
          document.body.appendChild(link)
          link.click()
          this.$notify({
            title: '下载成功',
            message: '后台文件下载成功',
            duration: 0,
            type: 'success'
          })
          this.downloadText = '下载'
          this.downloadLoading = false
        }).catch(err => {
          console.log(err)
          this.downloadLoading = false
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
