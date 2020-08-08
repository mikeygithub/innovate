<!--<template>-->
  <!--<div>-->
    <!--<el-dialog-->
      <!--:title="''"-->
      <!--:close-on-click-modal="false"-->
      <!--width="26rem"-->
      <!--:visible.sync="visible">-->
      <!--<h3 class="login-title">用户登录</h3>-->
          <!--<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>-->
            <!--<el-form-item prop="userName">-->
              <!--<el-input v-model="dataForm.userName" placeholder="帐号"></el-input>-->
            <!--</el-form-item>-->
            <!--<el-form-item prop="password">-->
              <!--<el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>-->
            <!--</el-form-item>-->
            <!--<el-form-item prop="captcha">-->
              <!--<el-row :gutter="20">-->
                <!--<el-col :span="14">-->
                  <!--<el-input v-model="dataForm.captcha" placeholder="验证码">-->
                  <!--</el-input>-->
                <!--</el-col>-->
                <!--<el-col :span="10" class="login-captcha">-->
                  <!--<img style="display: block;width: 100%;" :src="captchaPath" @click="getCaptcha()" alt="">-->
                <!--</el-col>-->
                <!--<el-col :span="10">-->
                  <!--<el-button type="text" @click="forgotPassword()">忘记账号/密码？</el-button>-->
                <!--</el-col>-->
              <!--</el-row>-->
            <!--</el-form-item>-->
            <!--<el-form-item>-->
              <!--<el-row :gutter="38">-->
                <!--<el-col :span="12">-->
                  <!--<el-button class="login-btn-submit" type="primary" @click="dataFormSubmit()" :loading="loginLoading">-->
                    <!--登录-->
                  <!--</el-button>-->
                <!--</el-col>-->
                <!--<el-col :span="12">-->
                  <!--<el-button class="login-btn-submit" type="primary" @click="visible = false">取消</el-button>-->
                <!--</el-col>-->
              <!--</el-row>-->
            <!--</el-form-item>-->
          <!--</el-form>-->
    <!--</el-dialog>-->
    <!--&lt;!&ndash; 忘记密码弹窗 &ndash;&gt;-->
    <!--<forget-details v-if="forget" ref="forget"/>-->
  <!--</div>-->
<!--</template>-->

<!--<script>-->
  <!--import { getUUID } from '@/utils'-->
  <!--import ForgetDetails from './forget'-->

  <!--export default {-->
    <!--data () {-->
      <!--return {-->
        <!--visible: false,-->
        <!--registerVisible: false,-->
        <!--loginLoading: false,-->
        <!--forget: false,-->
        <!--dataForm: {-->
          <!--userName: '',-->
          <!--password: '',-->
          <!--uuid: '',-->
          <!--captcha: ''-->
        <!--},-->
        <!--instituteList: this.$store.state.user.institute,-->
        <!--dataRule: {-->
          <!--userName: [-->
            <!--{ required: true, message: '帐号不能为空', trigger: 'blur' }-->
          <!--],-->
          <!--password: [-->
            <!--{ required: true, message: '密码不能为空', trigger: 'blur' }-->
          <!--],-->
          <!--captcha: [-->
            <!--{ required: true, message: '验证码不能为空', trigger: 'blur' }-->
          <!--]-->
        <!--},-->
        <!--captchaPath: ''-->
      <!--}-->
    <!--},-->
    <!--components: {-->
      <!--ForgetDetails-->
    <!--},-->
    <!--created () {-->
      <!--this.getCaptcha()-->
    <!--},-->
    <!--mounted () {-->
      <!--this.getDate()-->
    <!--},-->
    <!--methods: {-->
      <!--init () {-->
        <!--this.visible = true-->
      <!--},-->
      <!--// 提交表单-->
      <!--dataFormSubmit () {-->
        <!--this.$refs['dataForm'].validate((valid) => {-->
          <!--if (valid) {-->
            <!--this.loginLoading = true-->
            <!--this.$http({-->
              <!--url: this.$http.adornUrl('/sys/login'),-->
              <!--method: 'post',-->
              <!--data: this.$http.adornData({-->
                <!--'username': this.dataForm.userName,-->
                <!--'password': this.dataForm.password,-->
                <!--'uuid': this.dataForm.uuid,-->
                <!--'captcha': this.dataForm.captcha-->
              <!--})-->
            <!--}).then(({data}) => {-->
              <!--this.loginLoading = false-->
              <!--if (data && data.code === 0) {-->
                <!--this.$notify({-->
                  <!--title: '系统提示',-->
                  <!--message: '登入成功,欢迎使用校企合作及双创管理平台',-->
                  <!--// duration: 1000,-->
                  <!--type: 'success'-->
                <!--})-->
                <!--this.$cookie.set('token', data.token)-->
                <!--// 登入成功-->
                <!--// this.$message.success('登入成功')-->
                <!--this.$nextTick(function () {-->
                  <!--this.$router.replace({ name: 'index' })-->
                  <!--this.visible = false-->
                  <!--this.$router.go(0)-->
                <!--})-->
              <!--} else {-->
                <!--this.getCaptcha()-->
                <!--this.$message.error(data.msg)-->
                <!--this.dataForm.captcha = ''-->
              <!--}-->
            <!--})-->
            <!--this.loginLoading = false-->
          <!--}-->
        <!--})-->
      <!--},-->
      <!--registerSubmit () {-->
        <!--this.registerVisible = true-->
        <!--this.$nextTick(() => {-->
          <!--this.$refs.register.init()-->
        <!--})-->
      <!--},-->
      <!--// 获取验证码-->
      <!--getCaptcha () {-->
        <!--this.registerVisible = false-->
        <!--this.dataForm.uuid = getUUID()-->
        <!--this.captchaPath = this.$http.adornUrl(`/captcha.jpg?uuid=${this.dataForm.uuid}`)-->
      <!--},-->
        <!--// 忘记密码窗口弹窗-->
      <!--forgotPassword: function () {-->
        <!--this.forget = true-->
        <!--this.$nextTick(() => {-->
          <!--this.$refs.forget.init()-->
        <!--})-->
      <!--},-->
      <!--// 获取数据信息-->
      <!--getDate () {-->
        <!--this.$http({-->
          <!--url: this.$http.adornUrl('/innovate/sys/institute/all'),-->
          <!--method: 'get',-->
          <!--params: this.$http.adornParams()-->
        <!--}).then(({data}) => {-->
          <!--if (data && data.code === 0) {-->
            <!--this.$store.state.user.institute = data.institute-->
          <!--}-->
        <!--})-->
      <!--}-->
    <!--}-->
  <!--}-->
<!--</script>-->

<!--<style lang="scss">-->
  <!--.site-wrapper.site-page&#45;&#45;login {-->
    <!--position: absolute;-->
    <!--top: 0;-->
    <!--right: 0;-->
    <!--bottom: 0;-->
    <!--left: 0;-->
    <!--background-color: rgba(38, 50, 56, .6);-->
    <!--overflow: hidden;-->
    <!--&:before {-->
      <!--position: fixed;-->
      <!--top: 0;-->
      <!--left: 0;-->
      <!--z-index: -1;-->
      <!--width: 100%;-->
      <!--height: 100%;-->
      <!--content: "";-->
      <!--/*background-image: url(~@/assets/img/login.png);*/-->
      <!--background-size: 100% 100%;-->
    <!--}-->
    <!--.site-content__wrapper {-->
      <!--position: absolute;-->
      <!--top: 0;-->
      <!--right: 0;-->
      <!--bottom: 0;-->
      <!--left: 0;-->
      <!--padding: 0;-->
      <!--margin: 0;-->
      <!--overflow-x: hidden;-->
      <!--overflow-y: auto;-->
      <!--background-color: transparent;-->
    <!--}-->
    <!--.site-content {-->
      <!--min-height: 100%;-->
      <!--padding: 30px 500px 30px 30px;-->
    <!--}-->
    <!--.brand-info {-->
      <!--margin: 220px 100px 0 90px;-->
      <!--color: #fff;-->
    <!--}-->
    <!--.brand-info__text {-->
      <!--margin:  0 0 22px 0;-->
      <!--font-size: 48px;-->
      <!--font-weight: 400;-->
      <!--text-transform : uppercase;-->
    <!--}-->
    <!--.brand-info__intro {-->
      <!--margin: 10px 0;-->
      <!--font-size: 16px;-->
      <!--line-height: 1.58;-->
      <!--opacity: .6;-->
    <!--}-->
    <!--.login-main {-->
      <!--position: absolute;-->
      <!--top: 0;-->
      <!--right: 0;-->
      <!--padding: 150px 60px 180px;-->
      <!--width: 470px;-->
      <!--min-height: 100%;-->
      <!--background-color: #fff;-->
    <!--}-->
    <!--.login-title {-->
      <!--font-size: 16px;-->
    <!--}-->
    <!--.login-captcha {-->
      <!--overflow: hidden;-->
      <!--&gt; img {-->
        <!--width: 100%;-->
        <!--cursor: pointer;-->
      <!--}-->
    <!--}-->
    <!--.login-btn-submit {-->
      <!--width: 100%;-->
      <!--margin-top: 38px;-->
    <!--}-->
  <!--}-->
<!--</style>-->
<template>
  <div class="site-wrapper site-page--login">
    <div class="site-content__wrapper">
      <div class="site-content">
        <div class="brand-info">
          <h2 class="brand-info__text">创新创业管理信息系统</h2>
          <p class="brand-info__intro">双创管理系统</p>
        </div>
        <div class="login-main">
          <h3 class="login-title">登录</h3>
          <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>
            <el-form-item prop="userName">
              <el-input v-model="dataForm.userName" placeholder="帐号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="captcha">
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-input v-model="dataForm.captcha" placeholder="验证码">
                  </el-input>
                </el-col>
                <el-col :span="10" class="login-captcha">
                  <img :src="captchaPath" @click="getCaptcha()" alt="">
                </el-col>
              </el-row>
            </el-form-item>

            <el-row :gutter="21" style="line-height: 10px">
              <el-col :span="21">
                <el-button type="text" @click="forgotPassword()">忘记账号/密码？</el-button>
              </el-col>
            </el-row>
            <el-form-item>
              <el-row :gutter="38">
                <el-col :span="12">
                  <el-button class="login-btn-submit" type="primary" @click="dataFormSubmit()">登录</el-button>
                </el-col>
                <el-col :span="12">
                  <el-button class="login-btn-submit" type="primary" @click="registerSubmit()">注册</el-button>
                </el-col>
              </el-row>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <Register v-if="registerVisible" ref="register" @refreshDataList="getCaptcha"/>
    <forget-details v-if="forget" ref="forget"  @refreshDataList="getCaptcha"/>
  </div>
</template>

<script>
  import { getUUID } from '@/utils'
  import Register from './register'
  import ForgetDetails from './forget'

  export default {
    data () {
      return {
        registerVisible: false,
        forget: false,
        dataForm: {
          userName: '',
          password: '',
          uuid: '',
          captcha: ''
        },
        dataRule: {
          userName: [
            { required: true, message: '帐号不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          captcha: [
            { required: true, message: '验证码不能为空', trigger: 'blur' }
          ]
        },
        captchaPath: ''
      }
    },
    components: {
      Register,
      ForgetDetails
    },
    created () {
      this.getCaptcha()
    },
    methods: {
      // 提交表单
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl('/sys/login'),
              method: 'post',
              data: this.$http.adornData({
                'username': this.dataForm.userName,
                'password': this.dataForm.password,
                'uuid': this.dataForm.uuid,
                'captcha': this.dataForm.captcha
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$cookie.set('token', data.token)
                this.$router.replace({ name: 'home' })
              } else {
                this.getCaptcha()
                this.$message.error(data.msg)
                this.dataForm.captcha = ''
              }
            })
          }
        })
      },
      registerSubmit () {
        this.registerVisible = true
        this.$nextTick(() => {
          this.$refs.register.init()
        })
      },
      // 获取验证码
      getCaptcha () {
        this.registerVisible = false
        this.dataForm.uuid = getUUID()
        this.captchaPath = this.$http.adornUrl(`/captcha.jpg?uuid=${this.dataForm.uuid}`)
      },
      // 忘记密码窗口弹窗
      forgotPassword () {
        this.forget = true
        this.$nextTick(() => {
          this.$refs.forget.init()
        })
      }
    }
  }
</script>

<style lang="scss">
  .site-wrapper.site-page--login {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(38, 50, 56, .6);
    overflow: hidden;
    &:before {
      position: fixed;
      top: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      content: "";
      background-image: url(~@/assets/img/login.png);
      background-size: 100% 100%;
    }
    .site-content__wrapper {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      padding: 0;
      margin: 0;
      overflow-x: hidden;
      overflow-y: auto;
      background-color: transparent;
    }
    .site-content {
      min-height: 100%;
      padding: 30px 500px 30px 30px;
    }
    .brand-info {
      margin: 220px 100px 0 90px;
      color: #fff;
    }
    .brand-info__text {
      margin:  0 0 22px 0;
      font-size: 48px;
      font-weight: 400;
      text-transform : uppercase;
    }
    .brand-info__intro {
      margin: 10px 0;
      font-size: 16px;
      line-height: 1.58;
      opacity: .6;
    }
    .login-main {
      position: absolute;
      top: 0;
      right: 0;
      padding: 150px 60px 180px;
      width: 470px;
      min-height: 100%;
      background-color: #fff;
    }
    .login-title {
      font-size: 16px;
    }
    .login-captcha {
      overflow: hidden;
      > img {
        width: 100%;
        cursor: pointer;
      }
    }
    .login-btn-submit {
      width: 100%;
      margin-top: 38px;
    }
  }
</style>
