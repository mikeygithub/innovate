<template>
  <div class="app-public" v-if="!hide" >
    <div class="app-ptop clearfix">
      <!-- 左边区域 -->
      <div class="app-pleft fl-l clearfix">
        <!-- logo -->
        <div class="app-plogo fl-l">
          <a href="#"><img :src="logo"  alt="logo" title="logo"></a>
        </div>
        <!-- 定位区域 -->
        <div v-if="false" class="app-pposition fl-l position-r">
          <p class="nav-city">
            <i class="icon-poi"></i>
            <span class="nav-city-selected">深圳</span>
            <span class="switchover-city">[切换城市]</span>
          </p>
          <div class="city-box clearfix" style="display: none;">
            <ul class="dorpdown-province">
              <li class="">热门</li>
              <li ka="sel-province-1" class="">北京</li>
              <li ka="sel-province-5" class="cur">黑龙江</li>
            </ul>
            <!-- 二级菜单 -->
            <div class="dorpdown-city">
              <ul class="">
                <li ka="hot-city-100010000" data-val="100010000" class="cur">全国</li>
              </ul>
            </div>
          </div>
        </div>
        <!-- 快捷菜单 -->
        <div v-if="false" class="app-pnav">
          <ul>
            <li v-for="(item, index) in menu" :key="index" @click="openMenuItem(index)"><a :class="{cur: item.cur}" :href="item.url" :target="item.target || '_blank'">{{item.name}}</a></li>
          </ul>
        </div>
      </div>
      <!-- 右边区域 -->
      <div class="app-pright fl-r">
        <!-- 用户信息 -->
        <div class="user-nav position-r">
          <ul>
            <li v-if="false" class="position-r"><a href="#">信息<span class="nav-chat-num"></span></a></li>
            <li v-if="false" class="position-r"><a href="#">简历</a></li>
            <li class="position-r nav-figure" v-if="isLogin">
              <a href="javascript:;" @click="showInfo(true)">
                <span class="label-text">{{this.getUserInfo() && this.$store.state.user.name }}</span>
                <img :src=headImag alt="头像" title="头像">
              </a>
              <div class="self-box position-a" v-show="showUserInfo" @mouseleave="showInfo(false)">
                <a v-if="false" href="#" >个人中心<span>推荐职位、编辑在线简历</span></a>
                <a v-if="false" href="#" >账号设置<span>修改密码、打招呼语和常用语</span></a>
                <a v-if="false" href="#" >隐私设置</a>
                <a v-if="false" href="#" class="link-mall" >求职助手</a>
                <a href="javascript:;" class="link-recruit" @click="manager">进入后台管理系统</a>
                <a href="javascript:;" class="link-logout" @click="logout">退出登录</a>
              </div>
            </li>
            <li v-else class="position-r"><a href="javascript:;"><span @click="register">注册</span></a>&nbsp;| &nbsp;<a href="javascript:;" @click="login"><span>登录</span></a></li>
          </ul>
        </div>
        <!-- 基本信息 -->
      </div>
    </div>
  </div>
</template>

<script>
  import TopBarImg from '@/assets/img/logo-2x.png'
  import HeadImg from '@/assets/img/avatar_13.png'
  // 公共区域
  export default {
    name: 'top-bar',
    props: ['hide', 'login', 'register', 'logout', 'manager'],
    data () {
      return {
        logo: TopBarImg,
        headImag: HeadImg,
        menu: [{name: '首页', url: '#', target: '_blank', cur: true}, {name: '职位', url: '#', target: '_blank', cur: false}, {name: '公司', url: '#', target: '_blank', cur: false},
          {name: 'APP', url: '#', target: '_blank', cur: false}, {name: '资讯', url: '#', target: '_blank', cur: false}],
        isLogin: false,
        showUserInfo: false
      }
    },
    mounted () {
      if (this.$cookie.get('token') === null) {
        this.isLogin = false
        this.$nextTick(() => {
          // TODO:init not exist
          // this.$refs.login.init()
        })
      } else {
        this.isLogin = true
      }
    },
    methods: {
      // 获取当前管理员信息
      getUserInfo () {
        this.$http({
          url: this.$http.adornUrl('/sys/user/info'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          // console.log(data)
          if (data && data.code === 0) {
            this.loading = false
            this.$store.state.user.username = data.user.username
            this.$store.state.user.name = data.user.name
            this.$store.state.user.instituteId = data.user.instituteId
            this.$store.state.user.roleIdList = data.user.roleIdList
          }
        })
        return true
      },
      showInfo (flag) {
        if (flag) {
          this.showUserInfo = true
        } else {
          this.showUserInfo = false
        }
      },
      openMenuItem (index) {
        // console.log(type, index)
        let that = this
        this.menu.forEach((item, aindex, arr) => {
          // console.log(item, aindex, arr)
          item['cur'] = false
          if (index === aindex) {
            that.menu[aindex].cur = true
            // TODO请求网络数据，回填list
          }
        })
      }
    }
  }
</script>

<style scoped>
  /*.top-bg {*/
    /*background-image: url("~@/assets/img/logo-2x.png");*/
  /*}*/
</style>
