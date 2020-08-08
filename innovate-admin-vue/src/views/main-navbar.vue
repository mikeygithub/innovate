<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">创新创业管理信息系统</a>
        <a class="site-navbar__brand-mini" href="javascript:;">双创</a>
      </h1>
    </div>
    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="zhedie"></icon-svg>
        </el-menu-item>
      </el-menu>
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
        <!--<el-menu-item index="1" @click="openFrontPage()">-->
          <!--<template slot="title">-->
            <!--<el-badge>-->
              <!--<icon-svg name="shezhi" class="el-icon-setting"></icon-svg>-->
              <!--前台信息页面-->
            <!--</el-badge>-->
          <!--</template>-->
        <!--</el-menu-item>-->
        <el-menu-item index="1" @click="$router.push({ name: 'theme' })">
          <template slot="title">
            <el-badge>
              <icon-svg name="shezhi" class="el-icon-setting"></icon-svg>
              主题
            </el-badge>
          </template>
        </el-menu-item>
        <!--<el-submenu index="3">-->
        <!--<template slot="title">中文/EN</template>-->
        <!--<el-menu-item index="2-1"><el-button type="primary" @click="switchChinese()">切换中文</el-button></el-menu-item>-->
        <!--<el-menu-item index="2-2"><el-button type="primary" @click="switchEnlish()">切换英文</el-button></el-menu-item>-->
        <!--</el-submenu>-->
        <!--<el-menu-item index="2">-->
        <!--<el-badge value="4">-->
        <!--<a href="//www.renren.io/" target="_blank">官方社区</a>-->
        <!--</el-badge>-->
        <!--</el-menu-item>-->
        <!--<el-submenu index="3">-->
        <!--<template slot="title">Git源码</template>-->
        <!--<el-menu-item index="2-1"><a href="//github.com/daxiongYang/renren-fast-vue" target="_blank">前端</a></el-menu-item>-->
        <!--<el-menu-item index="2-2"><a href="//git.oschina.net/renrenio/renren-fast" target="_blank">后台</a></el-menu-item>-->
        <!--<el-menu-item index="2-3"><a href="//git.oschina.net/renrenio/renren-generator" target="_blank">代码生成器</a></el-menu-item>-->
        <!--</el-submenu>-->
        <el-menu-item class="site-navbar__avatar" index="3">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <!--<img src="~@/assets/img/user.png" :alt="userName">-->
              <icon-svg name="admin" class="el-icon-setting"></icon-svg>
              {{ this.$store.state.user.name }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="isStudent()" @click.native="studentAddOrUpdateHandle()">负责人信息</el-dropdown-item>
              <el-dropdown-item v-if="isTeacher()" @click.native="teacherAddOrUpdateHandle()">教师信息</el-dropdown-item>
              <el-dropdown-item @click.native="updatePasswordHandle()">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
    <student-add-or-update v-if="studentAddOrUpdateVisible" ref="studentAddOrUpdate" @refreshDataList="getDataList"></student-add-or-update>
    <teacher-add-or-update v-if="teacherAddOrUpdateVisible" ref="teacherAddOrUpdate" @refreshDataList="getDataList"></teacher-add-or-update>
  </nav>
</template>

<script>
  import { clearLoginInfo } from '@/utils'
  import UpdatePassword from './main-navbar-update-password'
  import StudentAddOrUpdate from './student-addOrUpdate-info'
  import TeacherAddOrUpdate from './teacher-addOrUpdate-info'
  export default {
    data () {
      return {
        studentAddOrUpdateVisible: false,
        teacherAddOrUpdateVisible: false,
        updatePassowrdVisible: false,
        instituteList: this.$store.state.user.institute,
        userRole: this.$store.state.user.roleIdList
      }
    },
    components: {
      TeacherAddOrUpdate,
      StudentAddOrUpdate,
      UpdatePassword
    },
    computed: {
      navbarLayoutType: {
        get () { return this.$store.state.common.navbarLayoutType }
      },
      sidebarFold: {
        get () { return this.$store.state.common.sidebarFold },
        set (val) { this.$store.commit('common/updateSidebarFold', val) }
      },
      mainTabs: {
        get () { return this.$store.state.common.mainTabs },
        set (val) { this.$store.commit('common/updateMainTabs', val) }
      },
      userName: {
        get () { return this.$store.state.user.name }
      }
    },
    mounted () {
      this.init()
    },
    methods: {
      init () {
        this.$http({
          url: this.$http.adornUrl(`/innovate/use/teacher/all`),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.userTeacherInfoEntities = data.userTeacherInfoEntities
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/institute/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.user.institute = data.institute
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/grade/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.user.grade = data.grade
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/school/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.user.school = data.school
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/subject/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.user.subject = data.subject
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/title/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.user.title = data.title
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/match/event/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$store.state.eventLists = data.matchEventEntityList
          }
        })
        for (var item in this.$store.state.user.roleIdList) {
          if (this.$store.state.user.roleIdList[item] === 2) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/use/person/info/${this.$store.state.user.id}`),
              method: 'get',
              data: this.$http.adornData()
            }).then(({data}) => {
              if (data && data.code === 0) {
                if (data.userPerson.userPerId === '' || data.userPerson.userPerId === null) {
                  this.studentAddOrUpdateHandle()
                }
              } else {
                this.$message.error(data.msg)
              }
            })
          }
          if (this.$store.state.user.roleIdList[item] === 3) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/use/teacher/info/${this.$store.state.user.id}`),
              method: 'get',
              data: this.$http.adornData()
            }).then(({data}) => {
              if (data && data.code === 0) {
                if (data.userTeacher.userTeacherId === '' || data.userTeacher.userTeacherId === null) {
                  this.teacherAddOrUpdateHandle()
                }
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        }
      },
      getDataList () {
        location.reload()
      },
      isStudent () {
        for (var item in this.userRole) {
          if (this.userRole[item] === 2) {
            return true
          }
        }
        return false
      },
      isTeacher () {
        for (var item in this.userRole) {
          if (this.userRole[item] === 3) {
            return true
          }
        }
        return false
      },
      // 修改密码
      updatePasswordHandle () {
        this.updatePassowrdVisible = true
        this.$nextTick(() => {
          this.$refs.updatePassowrd.init()
        })
      },
      studentAddOrUpdateHandle () {
        this.studentAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.studentAddOrUpdate.init()
        })
      },
      teacherAddOrUpdateHandle () {
        this.teacherAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.teacherAddOrUpdate.init()
        })
      },
      switchChinese () {
        this.$i18n.locale = 'cn'
      },
      switchEnlish () {
        this.$i18n.locale = 'en'
      },
      // 打开前台页面
      openFrontPage () {
        this.$router.push({ name: 'index' })
      },
      // 退出
      logoutHandle () {
        this.$confirm(`确定进行[退出]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/logout'),
            method: 'post',
            data: this.$http.adornData()
          }).then(({data}) => {
            if (data && data.code === 0) {
              clearLoginInfo()
              this.$router.push({ name: 'login' })
            }
          })
        }).catch(() => {})
      }
    }
  }
</script>
