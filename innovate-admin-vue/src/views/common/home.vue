<template>
  <div class="mod-home">
    <el-row :gutter="20">
      <el-col :span="24">
        <div style="padding-bottom: 1rem">
          <el-alert
            :title="roleTitle"
            type="success">
          </el-alert>
        </div>
      </el-col>
      <!--超级管理员-->
      <template v-for="role in roleList">
        <template v-if="role === 1">
          <el-col :span="8">
            <el-card v-if="totalPageBaseSuperAdmin > 0">
              <div slot="header">
                <span>中心申请流程管理->超级管理员项目列表(超级管理员)</span>
              </div>
              <div>
                <span>{{totalPageBaseSuperAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/base/super">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageMatchSuperAdmin > 0">
              <div slot="header">
                <span>双创赛事管理->超级管理员项目列表(超级管理员)</span>
              </div>
              <div>
                <span>{{totalPageMatchSuperAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/match/super">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->

          <!--中期检查e-->
          <el-col :span="8">
            <el-card v-if="totalPageMatchSuperAdmin > 0">
              <div slot="header">
                <span>大创申请管理->超级管理员项目列表(超级管理员)</span>
              </div>
              <div>
                <span>{{totalPageMatchSuperAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/match/super">跳转</router-link>
              </div>
            </el-card>
          </el-col>
        </template>
        <!--项目负责人-->
        <template v-if="role === 2">
          <el-col :span="8">
            <el-card v-if="noPassProjects.length > 0">
              <div slot="header">
                <span>中心申请流程管理->不通过项目列表(项目负责人)</span>
              </div>
              <div v-for="item in noPassProjects">
                <li>
                  项目：{{item.projectName}}
                  <span v-if="item.baseNoPass === 1">（中心申请流程）、</span>
                  不通过，请修改
                  <router-link to="/innovate-apply/base/nopass">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="baseUpdateList.length > 0">
              <div slot="header">
                <span>中心申请流程管理->申请修改通过项目列表(项目负责人)</span>
              </div>
              <div>
                <li v-for="item in baseUpdateList">
                  项目：{{item.projectInfoEntity.projectName}}（修改申请）已通过，请修改
                  <router-link to="/innovate-apply/base/update">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="noPassMatchs.length > 0">
              <div slot="header">
                <span>双创赛事管理->不通过项目列表(项目负责人)</span>
              </div>
              <div>
                <li v-for="item in noPassMatchs">
                  项目：{{item.matchInfoEntity.matchName}}
                  <span v-if="item.matchInfoEntity.matchNoPass === 1">、</span>
                  不通过，请修改
                  <router-link to="/innovate-apply/match/nopass">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->
          <el-col :span="8">
            <el-card v-if="submitChecks.length > 0">
              <div slot="header">
                <span>中期流程管理->待提交项目列表(项目负责人)</span>
              </div>
              <div>
                <li v-for="item in submitChecks">
                  项目：{{item.declareInfoEntity.declareName}}
                  <span v-if="item.innovateCheckInfoEntity.checkNoPass === 0">、</span>
                  待提交
                  <router-link to="/innovate-apply/check/student">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="noPassChecks.length > 0">
              <div slot="header">
                <span>中期流程管理->不通过项目列表(项目负责人)</span>
              </div>
              <div>
                <li v-for="item in noPassChecks">
                  项目：{{item.declareInfoEntity.declareName}}
                  <span v-if="item.innovateCheckInfoEntity.checkNoPass === 1">、</span>
                  不通过，请修改
                  <router-link to="/innovate-apply/check/nopass">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
          <!--中期检查e-->
          <el-col :span="8">
            <el-card v-if="noPassAudits.length > 0">
              <div slot="header">
                <span>双创赛事管理->不通过项目列表(项目负责人)</span>
              </div>
              <div>
                <li v-for="item in noPassAudits">
                  项目：{{item.declareInfoEntity.declareName}}
                  <span v-if="item.declareInfoEntity.auditNoPass === 1">、</span>
                  不通过，请修改
                  <router-link to="/innovate-apply/audit/nopass">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="noPassFinishs.length > 0">
              <div slot="header">
                <span>结题管理->不通过项目列表(项目负责人)</span>
              </div>
              <div>
                <li v-for="item in noPassFinishs">
                  项目：{{item.finishInfoEntity.finishName}}
                  <span v-if="item.finishInfoEntity.finishNoPass === 1">、</span>
                  不通过，请修改
                  <router-link to="/innovate-apply/finish/nopass">跳转</router-link>
                </li>
              </div>
            </el-card>
          </el-col>
        </template>
        <!--指导老师-->
        <template v-if="role === 3">
          <el-col :span="8">
            <el-card v-if="totalPageBaseTeacher > 0">
              <div slot="header">
                <span>中心申请流程管理->指导老师审批列表(指导老师)</span>
              </div>
              <div>
                <span>{{totalPageBaseTeacher}}条、待审批</span>
                <router-link to="/innovate-apply/base/teacher">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageMatchTeacher > 0">
              <div slot="header">
                <span>双创赛事管理->指导老师审批列表(指导老师)</span>
              </div>
              <div>
                <span>{{totalPageMatchTeacher}}条、待审批</span>
                <router-link to="/innovate-apply/match/teacher">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageAuditTeacher > 0">
              <div slot="header">
                <span>大创申请管理->指导老师审批列表(指导老师)</span>
              </div>
              <div>
                <span>{{totalPageAuditTeacher}}条、待审批</span>
                <router-link to="/innovate-apply/audit/teacher">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->
          <!--<el-col :span="8">-->
            <!--<el-card v-if="totalPageCheckTeacher > 0">-->
              <!--<div slot="header">-->
                <!--<span>大创申请管理->指导老师审批列表(指导老师)</span>-->
              <!--</div>-->
              <!--<div>-->
                <!--<span>{{totalPageCheckTeacher}}条、待审批</span>-->
                <!--<router-link to="/innovate-apply/check/teacher">跳转</router-link>-->
              <!--</div>-->
            <!--</el-card>-->
          <!--</el-col>-->
          <!--中期检查e-->
          <el-col :span="8">
            <el-card v-if="totalPageFinishTeacher > 0">
              <div slot="header">
                <span>结题申请管理->指导老师审批列表(指导老师)</span>
              </div>
              <div>
                <span>{{totalPageFinishTeacher}}条、待审批</span>
                <router-link to="/innovate-apply/finish/teacher">跳转</router-link>
              </div>
            </el-card>
          </el-col>
        </template>
        <!--二级学院-->
        <template v-if="role === 4">
          <el-col :span="8">
            <el-card v-if="totalPageMatchEr > 0">
              <div slot="header">
                <span>双创赛事管理->二级学院审批列表(二级学院)</span>
              </div>
              <div>
                <span>{{totalPageMatchEr}}条、待审批</span>
                <router-link to="/innovate-apply/match/er">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageAuditEr > 0">
              <div slot="header">
                <span>大创申请管理->二级学院审批列表(二级学院)</span>
              </div>
              <div>
                <span>{{totalPageAuditEr}}条、待审批</span>
                <router-link to="/innovate-apply/audit/er">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->
          <el-col :span="8">
            <el-card v-if="totalPageCheckEr > 0">
              <div slot="header">
                <span>中期检查管理->二级学院审批列表(二级学院)</span>
              </div>
              <div>
                <span>{{totalPageCheckEr}}条、待审批</span>
                <router-link to="/innovate-apply/check/er">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查e-->
          <el-col :span="8">
            <el-card v-if="totalPageFinishEr > 0">
              <div slot="header">
                <span>结题管理->二级学院审批列表(二级学院)</span>
              </div>
              <div>
                <span>{{totalPageFinishEr}}条、待审批</span>
                <router-link to="/innovate-apply/finish/er">跳转</router-link>
              </div>
            </el-card>
          </el-col>
        </template>
        <!--分配评委-->
        <template v-if="role === 5">
          <el-col :span="8">
            <el-card v-if="totalPageBaseAdmin > 0">
              <div slot="header">
                <span>中心申请流程管理->分配评委组列表(分配评委)</span>
              </div>
              <div>
                <span>{{totalPageBaseAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/base/admin">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageMatchAdmin > 0">
              <div slot="header">
                <span>双创赛事管理->分配评委组列表(分配评委)</span>
              </div>
              <div>
                <span>{{totalPageMatchAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/match/admin">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageAuditAdmin > 0">
              <div slot="header">
                <span>大创申请管理->分配评委组列表(分配评委)</span>
              </div>
              <div>
                <span>{{totalPageAuditAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/audit/admin">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->
          <el-col :span="8">
            <el-card v-if="totalPageCheckAdmin > 0">
              <div slot="header">
                <span>中期检查管理->分配评委组列表(分配评委)</span>
              </div>
              <div>
                <span>{{totalPageCheckAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/check/admin">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查e-->
          <el-col :span="8">
            <el-card v-if="totalPageFinishAdmin > 0">
              <div slot="header">
                <span>结题管理->分配评委组列表(分配评委)</span>
              </div>
              <div>
                <span>{{totalPageFinishAdmin}}条、待审批</span>
                <router-link to="/innovate-apply/finish/admin">跳转</router-link>
              </div>
            </el-card>
          </el-col>
        </template>
        <!--评委-->
        <template v-if="role === 6">
          <el-col :span="8">
            <el-card v-if="totalPageBaseJury > 0">
              <div slot="header">
                <span>中心申请流程管理->评委打分列表(评委)</span>
              </div>
              <div>
                <span>{{totalPageBaseJury}}条、待审批</span>
                <router-link to="/innovate-apply/base/jury">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageMatchJury > 0">
              <div slot="header">
                <span>双创赛事管理->评委打分列表(评委)</span>
              </div>
              <div>
                <span>{{totalPageMatchJury}}条、待审批</span>
                <router-link to="/innovate-apply/match/jury">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageAuditJury > 0">
              <div slot="header">
                <span>大创申请管理->评委打分列表(评委)</span>
              </div>
              <div>
                <span>{{totalPageAuditJury}}条、待审批</span>
                <router-link to="/innovate-apply/audit/jury">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->
          <el-col :span="8">
            <el-card v-if="totalPageCheckJury > 0">
              <div slot="header">
                <span>中期检查管理->评委打分列表(评委)</span>
              </div>
              <div>
                <span>{{totalPageCheckJury}}条、待审批</span>
                <router-link to="/innovate-apply/check/jury">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查e-->
          <el-col :span="8">
            <el-card v-if="totalPageFinishJury > 0">
              <div slot="header">
                <span>结题管理->评委打分列表(评委)</span>
              </div>
              <div>
                <span>{{totalPageFinishJury}}条、待审批</span>
                <router-link to="/innovate-apply/finish/jury">跳转</router-link>
              </div>
            </el-card>
          </el-col>
        </template>
        <!--管理员-->
        <template v-if="role === 5">
          <el-col :span="8">
            <el-card v-if="totalPageBaseExamine > 0">
              <div slot="header">
                <span>中心申请流程管理->审批申请修改项目列表(管理员)</span>
              </div>
              <div>
                <span>{{totalPageBaseExamine}}条、待审批</span>
                <router-link to="/innovate-apply/base/examine2">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageBaseAdmin2 > 0">
              <div slot="header">
                <span>中心申请流程管理->管理员审批列表(管理员)</span>
              </div>
              <div>
                <span>{{totalPageBaseAdmin2}}条、待审批</span>
                <router-link to="/innovate-apply/base/admin2">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageMatchAdmin2 > 0">
              <div slot="header">
                <span>双创赛事管理->管理员审批列表(管理员)</span>
              </div>
              <div>
                <span>{{totalPageMatchAdmin2}}条、待审批</span>
                <router-link to="/innovate-apply/match/admin2">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card v-if="totalPageAuditAdmin2 > 0">
              <div slot="header">
                <span>大创申请管理->管理员审批列表(管理员)</span>
              </div>
              <div>
                <span>{{totalPageAuditAdmin2}}条、待审批</span>
                <router-link to="/innovate-apply/audit/admin2">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查s-->
          <el-col :span="8">
            <el-card v-if="totalPageCheckAdmin2 > 0">
              <div slot="header">
                <span>中期检查申请管理->管理员审批列表(管理员)</span>
              </div>
              <div>
                <span>{{totalPageCheckAdmin2}}条、待审批</span>
                <router-link to="/innovate-apply/check/admin2">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--中期检查e-->
          <!--结题s-->
          <el-col :span="8">
            <el-card v-if="totalPageFinishAdmin2 > 0">
              <div slot="header">
                <span>结题申请管理->管理员审批列表(管理员)</span>
              </div>
              <div>
                <span>{{totalPageFinishAdmin2}}条、待审批</span>
                <router-link to="/innovate-apply/finish/admin2">跳转</router-link>
              </div>
            </el-card>
          </el-col>
          <!--结题e-->
        </template>
      </template>
    </el-row>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        roleTitle: '欢迎您：',
        hasApply: 1,
        hasReview: 1,
        roleList: this.$store.state.user.roleIdList,
        baseUpdateList: '',
        totalPageBaseTeacher: '',
        totalPageMatchTeacher: '',
        totalPageAuditTeacher: '',
        totalPageCheckTeacher: '',
        totalPageFinishTeacher: '',
        totalPageBaseEr: '',
        totalPageMatchEr: '',
        totalPageAuditEr: '',
        totalPageCheckEr: '',
        totalPageFinishEr: '',
        totalPageBaseAdmin: '',
        totalPageMatchAdmin: '',
        totalPageAuditAdmin: '',
        totalPageCheckAdmin: '',
        totalPageFinishAdmin: '',
        totalPageBaseJury: '',
        totalPageMatchJury: '',
        totalPageAuditJury: '',
        totalPageCheckJury: '',
        totalPageFinishJury: '',
        totalPageBaseAdmin2: '',
        totalPageMatchAdmin2: '',
        totalPageAuditAdmin2: '',
        totalPageCheckAdmin2: '',
        totalPageFinishAdmin2: '',
        totalPageBaseSuperAdmin: '',
        totalPageMatchSuperAdmin: '',
        totalPageAuditSuperAdmin: '',
        totalPageBaseExamine: '',
        submitChecks: '',
        noPassProjects: '',
        noPassMatchs: '',
        noPassChecks: '',
        noPassFinishs: '',
        noPassAudits: ''
      }
    },
    mounted () {
      // 调度刷新
      // setInterval(this.getDataList, 100000)
      this.getDataList()
    },
    methods: {
      getDataList () {
        this.roleTitle = '欢迎您：'
        for (let index = 0; index < this.roleList.length; index++) {
          switch (this.roleList[index]) {
            case 1: {
              this.roleTitle += '超级管理员、'
              this.getSuperAdmin()
              break
            }
            case 2: {
              this.roleTitle += '项目负责人、'
              this.getStudent()
              break
            }
            case 3: {
              this.roleTitle += '指导老师、'
              this.getTeacher()
              break
            }
            case 4: {
              this.roleTitle += '二级学院、'
              this.getEr()
              break
            }
            case 5: {
              this.roleTitle += '管理员、'
              this.getAdmin()
              break
            }
            case 6: {
              this.roleTitle += '评委、'
              this.getJury()
              break
            }
          }
        }
      },
      getSuperAdmin () {
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'base_no_pass',
            'noPassStatus': 0,
            'apply': 'project_base_apply_status',
            'applyStatus': 5,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageBaseSuperAdmin = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'match_no_pass',
            'noPassStatus': 0,
            'apply': 'project_match_apply_status',
            'applyStatus': 6,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageMatchSuperAdmin = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'apply': 'project_audit_apply_status',
            'applyStatus': 6,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageAuditSuperAdmin = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
      },
      getStudent () {
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'noPass': 'match_no_pass',
            'noPassStatus': 1,
            'isStudent': true,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.noPassMatchs = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'noPass': 'audit_no_pass',
            'noPassStatus': 1,
            'isStudent': true,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.noPassAudits = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl(`/innovate/project/info/nopass`),
          method: 'get',
          params: this.$http.adornParams({
            'userId': this.$store.state.user.id
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.noPassProjects = data.projectInfoEntities
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'isUpdate': 1,
            'isStudent': true,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.baseUpdateList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.baseUpdateList = []
            this.totalPage = 0
          }
        })
        // 待提交中期检查项目
        this.$http({
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'checkNoPass': 0,
            'projectCheckApplyStatus': 0,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.submitChecks = data.page.list
          } else {
            this.submitChecks = []
          }
        })
        // 中期不通过
        this.$http({
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'checkNoPass': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.noPassChecks = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
        })
        // 结题不通过
        this.$http({
          url: this.$http.adornUrl('/innovate/finish/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'noPass': 'finish_no_pass',
            'noPassStatus': 1,
            'isStudent': true,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.noPassFinishs = data.page.list
            console.log(this.noPassFinishs)
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
        })
      },
      getTeacher () {
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'base_no_pass',
            'noPassStatus': 0,
            'isTeacher': true,
            'apply': 'project_base_apply_status',
            'applyStatus': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageBaseTeacher = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'match_no_pass',
            'noPassStatus': 0,
            'isTeacher': true,
            'apply': 'project_match_apply_status',
            'applyStatus': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageMatchTeacher = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'isTeacher': true,
            'apply': 'project_audit_apply_status',
            'applyStatus': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageAuditTeacher = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        // 结题
        this.$http({
          url: this.$http.adornUrl('/innovate/finish/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            // 'projectName': this.dataForm.projectName,
            // 'finishTime': this.dataForm.finishTime == null ? '' : this.dataForm.finishTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'finish_no_pass',
            'noPassStatus': 0,
            'isTeacher': true,
            'apply': 'project_finish_apply_status',
            'applyStatus': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageFinishTeacher = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
      },
      getEr () {
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'match_no_pass',
            'noPassStatus': 0,
            'erInstituteId': this.$store.state.user.instituteId,
            'isEr': true,
            'apply': 'project_match_apply_status',
            'applyStatus': 2,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageMatchEr = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'erInstituteId': this.$store.state.user.instituteId,
            'isEr': true,
            'apply': 'project_audit_apply_status',
            'applyStatus': 2,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageAuditEr = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        // 中期检查
        this.$http({
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'hasApply': this.hasApply,
            'checkNoPass': 0,
            'projectCheckApplyStatus': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageCheckEr = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
        // 结题
        this.$http({
          url: this.$http.adornUrl('/innovate/finish/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            // 'projectName': this.dataForm.projectName,
            // 'finishTime': this.dataForm.finishTime == null ? '' : this.dataForm.finishTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'finish_no_pass',
            'noPassStatus': 0,
            'erInstituteId': this.$store.state.user.instituteId,
            'isEr': true,
            'apply': 'project_finish_apply_status',
            'applyStatus': 2,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageFinishEr = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
      },
      getAdmin () {
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'base_no_pass',
            'noPassStatus': 0,
            'apply': 'project_base_apply_status',
            'applyStatus': 2,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageBaseAdmin = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'match_no_pass',
            'noPassStatus': 0,
            'apply': 'project_match_apply_status',
            'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageMatchAdmin = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'apply': 'project_audit_apply_status',
            'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageAuditAdmin = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'applyUpdate': 1,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageBaseExamine = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'base_no_pass',
            'noPassStatus': 0,
            'apply': 'project_base_apply_status',
            'applyStatus': 4,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageBaseAdmin2 = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'match_no_pass',
            'noPassStatus': 0,
            'apply': 'project_match_apply_status',
            'applyStatus': 5,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageMatchAdmin2 = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'apply': 'project_audit_apply_status',
            'applyStatus': 5,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageAuditAdmin2 = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        // 中期检查分配评委组
        this.$http({
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'hasApply': this.hasApply,
            'checkNoPass': 0,
            'projectCheckApplyStatus': 2,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageCheckAdmin = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
        // 中期检查管理员审批
        this.$http({
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'hasApply': this.hasApply,
            'checkNoPass': 0,
            'projectCheckApplyStatus': 4,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageCheckAdmin2 = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
        // 结题分配评委组
        this.$http({
          url: this.$http.adornUrl('/innovate/finish/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            // 'projectName': this.dataForm.projectName,
            // 'finishTime': this.dataForm.finishTime == null ? '' : this.dataForm.finishTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': '1',
            'noPass': 'finish_no_pass',
            'noPassStatus': 0,
            'apply': 'project_finish_apply_status',
            'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageFinishAdmin = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
        // 结题管理员审批
        this.$http({
          url: this.$http.adornUrl('/innovate/finish/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            // 'projectName': this.dataForm.projectName,
            // 'finishTime': this.dataForm.finishTime == null ? '' : this.dataForm.finishTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': '1',
            'noPass': 'finish_no_pass',
            'noPassStatus': 0,
            'apply': 'project_finish_apply_status',
            'applyStatus': 5,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageFinishAdmin2 = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
      },
      getJury () {
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasReview': this.hasReview,
            'noPass': 'base_no_pass',
            'noPassStatus': 0,
            'apply': 'project_base_apply_status',
            'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageBaseJury = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/match/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasReview': this.hasReview,
            'noPass': 'match_no_pass',
            'noPassStatus': 0,
            'apply': 'project_match_apply_status',
            'applyStatus': 4,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageMatchJury = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasReview': this.hasReview,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'apply': 'project_audit_apply_status',
            'applyStatus': 4,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageAuditJury = data.page.totalCount
          } else {
            this.totalPage = 0
          }
        })
        // 中期检查
        this.$http({
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'reviewUserId': this.$store.state.user.id,
            'hasReview': this.hasReview,
            'isReview': true,
            'checkNoPass': 0,
            'projectCheckApplyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.totalPageCheckJury = data.page.totalCount
          } else {
            this.dataList = []
          }
        })
      }
    }
  }
</script>

<style>
  .mod-home {
    line-height: 1.5;
  }
</style>

