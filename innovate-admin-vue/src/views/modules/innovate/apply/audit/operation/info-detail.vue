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
          <th colspan="2">申报项目名称</th>
          <td colspan="3"><span style="font-size: 1.1rem; font-weight: bolder" v-text="declareInfo.declareName" align="center"></span></td>
        <!--</tr>-->
        <!--<tr align='center'>-->
          <!--1-创新训练项目，2-创业训练项目，3-创业实践项目-->
          <th colspan="2">申报类型</th>
          <td colspan="3" v-for="item in declareTypeList"
              :key="item.value"
              v-if="item.value === declareInfo.declareType">
            <span v-text="item.label" align="center"></span>
          </td>
        </tr>
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">二级学院</th>
          <td colspan="3">
              <span v-for="item in instituteList"
                    v-if="item.instituteId === declareInfo.instituteId"
                    v-text="item.instituteName"
                    align="center"></span>
          </td>
        <!--</tr>-->
        <!--<tr align='center' style="height: 2.5rem">-->
          <th colspan="2">申报时间</th>
          <td colspan="3"><span  v-text="(declareInfo.declareTime || '').split(' ')[0]" align="center"></span></td>
        <!--</tr>-->
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">推荐级别</th>
          <td colspan="3"><span  v-text="declareInfo.declareRecommendLevel" align="center"></span></td>
        <!--</tr>-->
        <!--<tr align='center' style="height: 2.5rem">-->
        <th colspan="2">申报平均分</th>
        <td colspan="3"><span  v-text="declareInfo.declareScoreAvg" align="center"></span></td>
        <!--</tr>-->
        <tr align='center' style="height: 2.5rem">
          <th colspan="2">排序号</th>
          <td colspan="3"><span  v-text="declareInfo.declareOrderNo" align="center"></span></td>
        <!--</tr>-->
        <!--<tr align='center' style="height: 2.5rem">-->
        <th colspan="2">二级学院筛选结果</th>
        <td colspan="3"><span  v-text="declareInfo.declareResult" align="center"></span></td>
        </tr>
        <tr align='center'>
          <th colspan="2">项目简介</th>
          <td colspan="8">
            <span v-text="declareInfo.declareDescribe" align="center"></span>
          </td>
        </tr>
        <!--项目负责人开始-->
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents"><th colspan="10">项目负责人信息</th></tr>
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
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr class="contents"><th colspan="10">项目参与者信息</th></tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align='center'>
          <th>姓名</th>
          <th>性别</th>
          <th>学号</th>
          <th>所在二级学院</th>
          <th>所在年级</th>
          <th>所在班级</th>
          <th>所在宿舍</th>
          <th>联系电话</th>
          <th>QQ号</th>
          <th>个人电子邮箱</th>
        </tr>
        <template>
          <tr v-for="item in staffList" align="center">
            <td v-text="item.staffName"></td>
            <td v-for="sex in sexList"
                :key="sex.value"
                v-if="item.staffSex=== sex.value"
                v-text="sex.label">
            </td>
            <td v-text="item.staffStuNo"></td>
            <td>
              <span v-for="institute in instituteList"
                    v-if="item.instituteId === institute.instituteId"
                    v-text="institute.instituteName"
                    align="center"></span>
            </td>
            <td>
              <span v-for="grade in gradeList"
                    v-if="item.gradeId === grade.gradeId"
                    v-text="grade.gradeName"
                    align="center">
              </span>
            </td>
            <td colspan="1" v-text="item.staffClassNo"></td>
            <td colspan="1" v-text="item.staffCormNo"></td>
            <td colspan="1" v-text="item.staffTel"></td>
            <td colspan="1" v-text="item.staffQq"></td>
            <td colspan="1" v-text="item.staffEmail"></td>
          </tr>
        </template>
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


        <!--成果/奖项开始-->
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr align="center" class="contents">
          <th colspan="10">所获成果/奖项</th>
        </tr>
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <tr>
          <th colspan="1">获奖等级</th>
          <th colspan="3">所获总奖金（元）</th>
          <th colspan="3">所获区级奖金（元）</th>
          <th colspan="3">所获校级奖金（元）</th>
          <!--<th colspan="1">操作</th>-->
        </tr>
        <template>
          <tr v-for="item in awardList" align="center">
            <td colspan="1"
                v-for="type in awardTypeList"
                :key="type.value"
                v-if="type.value === item.awardType"
                v-text="type.label">
            </td>
            <td colspan="3" v-text="item.awardMoneyAll"></td>
            <td colspan="3" v-text="item.awardMoneyDistrict"></td>
            <td colspan="3" v-text="item.awardMoneySchool"></td>
            <!--<td colspan="1" v-text="item.awardFileName"></td>-->
          </tr>
        </template>
        <!--成果/奖项结束-->

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
        <tr align='center'>
          <td colspan="10" style="height: 1.2rem"></td>
        </tr>
        <template>
          <tr v-for="item in attachLists"
              align="center">
            <td colspan="7" v-text="item.attachName"></td>
            <td colspan="3"><el-button @click="attachDown(item)" :loading="downloadLoading">下载</el-button></td>
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
            <td colspan="5" v-text="declareInfo.declareScoreAvg + '分'"></td>
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
                      v-if="type.value === item.apply && type.value === 'project_audit_apply_status'" >
              <tr align="center">
                <template v-if="isAuth('sys:user:info')">
                  <td colspan="2" v-for="reviewName in reviewNameList"  v-if="reviewName.userId === item.userId" v-text="reviewName.name"></td>
                </template>
                <td colspan="2" v-if="!isAuth('sys:user:info', item.userId)" v-text="'评委:' + (index + 1)"></td>
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
  // import FileSaver from 'file-saver'
  // import XLSX from 'xlsx'

  export default {
    data () {
      return {
        visible: false,
        dataListLoading: false,
        downloadLoading: false,
        declareInfo: {},
        instituteList: this.$store.state.user.institute,
        gradeList: this.$store.state.user.grade,
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
        reviewNameList: [],
        moneyList: [],
        station: {},
        roleLists: this.$store.state.user.roleIdList,
        stationList: [],
        teacherSet: '',
        teacherList: [],
        awardList: [],
        staffList: [],
        reviewList: [],
        attachLists: [],
        awardLists: [],
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
        awardTypeList: [
          {value: 1, label: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        declareTypeList: [
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
        this.id = id
        this.dataForm.id = this.id || 0
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/declare/info/info`),
            method: 'get',
            params: this.$http.adornParams({
              'declareId': this.dataForm.id
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.declareInfo = data.declareInfo.declareInfoEntity
              if (data.declareInfo.declareTeacherEntities.length > 0) {
                this.teacherList = data.declareInfo.declareTeacherEntities
                this.userTeacherInfoEntities = data.userTeacherInfoEntities
              } else {
                this.teacherList = []
              }
              if (data.declareInfo.userPersonInfoEntities.length > 0) {
                this.perInfoList = data.declareInfo.userPersonInfoEntities
              } else {
                this.perInfoList = []
                this.perInfoList.sysUserEntity = []
              }
              if (data.declareInfo.declareAttachEntities.length > 0) {
                this.attachLists = data.declareInfo.declareAttachEntities
              } else {
                this.attachLists = []
              }
              if (data.declareInfo.declareStaffInfoEntities.length > 0) {
                this.staffList = data.declareInfo.declareStaffInfoEntities
              } else {
                this.staffList = []
              }
              if (data.declareInfo.declareReviewEntities.length > 0) {
                this.reviewList = data.declareInfo.declareReviewEntities
              } else {
                this.reviewList = []
              }
              if (data.declareInfo.declareAwardEntities.length > 0) {
                this.awardList = data.declareInfo.declareAwardEntities
              } else {
                this.awardList = []
              }
              for (let index = 0; index < this.reviewList.length; index++) {
                this.$http({
                  url: this.$http.adornUrl(`/sys/user/info/${this.reviewList[index].userId}`),
                  method: 'get',
                  params: this.$http.adornParams()
                }).then(({data}) => {
                  let user = {userId: data.user.userId, name: data.user.name}
                  this.reviewNameList.push(user)
                })
              }
              // 获取指导老师签署意见
              this.$http({
                url: this.$http.adornUrl(`/innovate/declare/signingopinion/info`),
                method: 'get',
                params: this.$http.adornParams({
                  'declareId': this.dataForm.id
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
      attachDown (attach) {
        this.downloadLoading = true
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/innovate/declare/attach/download`),
          method: 'post',
          params: this.$httpFile.adornParams({
            'filePath': attach.attachPath
            // 'apply': 'project_base_apply_status'
          })
        }).then(response => {
          if (!response) {
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
