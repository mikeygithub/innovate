<template>
  <el-dialog
    :title="'注册'"
    @close="closeRegister"
    :close-on-click-modal="false"
    width="20%"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>
      <el-form-item prop="type" style="text-align: center">
        <template>
          <el-row>
            <el-col :span="11" >
              <el-radio v-model="dataForm.type" label="1" border style="width: 100%">学生</el-radio>
            </el-col>
            <el-col :span="12" :offset="1">
              <el-radio v-model="dataForm.type" label="2" border style="width: 100%">教师</el-radio>
            </el-col>
            <!--<el-col :span="7" :offset="1">-->
              <!--<el-radio v-model="dataForm.type" label="3" border style="width: 100%">企业</el-radio>-->
            <!--</el-col>-->
          </el-row>
        </template>
      </el-form-item>
      <!-- S 企业基本信息 -->
      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entName">-->
        <!--<el-input v-model="dataForm.ent.entName" placeholder="企业名称"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entCorporate">-->
        <!--<el-input v-model="dataForm.ent.entCorporate" placeholder="法人代表"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entCode">-->
        <!--<el-input v-model="dataForm.ent.entCode" placeholder="统一信用代码"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entRegister">-->
        <!--<el-input v-model.number="dataForm.ent.entRegister" placeholder="注册资本"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" >-->
        <!--<el-row>-->
          <!--<el-col :span="8" prop="ent.newHighZones" style="text-align: center;">-->
            <!--<span>是否高新区</span>-->
            <!--<el-radio v-model="dataForm.ent.newHighZones" label="0" border style="width: 30%">是</el-radio>-->
            <!--<el-radio v-model="dataForm.ent.newHighZones" label="1" border style="width: 30%">否</el-radio>-->
          <!--</el-col>-->
          <!--<el-col :span="14" :offset="1">-->
            <!--<span >成立时间</span>-->
            <!--<el-date-picker v-model="dataForm.ent.entFoundingTime" type="datetime" style="width: 45%" prop="ent.entFoundingTime" placeholder="成立时间"></el-date-picker>-->
            <!--<span >经营状态</span>-->
            <!--<el-select placeholder="请选择经营状态"  v-model="dataForm.ent.entStatus" style="width: 25%" prop="ent.entStatus" >-->
              <!--<el-option v-for="(item, index) in entStatus" :label="item.lable" :value="item.value" :key="index"></el-option>-->
            <!--</el-select>-->
          <!--</el-col>-->
        <!--</el-row>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" >-->
        <!--<el-row>-->
          <!--<el-col :span="8" style="text-align: center;">-->
            <!--<span >企业类型</span>-->
            <!--<el-select placeholder="企业类型"  v-model="dataForm.ent.entType" style="width: 70%" prop="ent.entType" >-->
              <!--<el-option v-for="(item, index) in entType" :label="item.lable" :value="item.lable" :key="index"></el-option>-->
            <!--</el-select>-->
          <!--</el-col>-->
        <!--</el-row>-->
      <!--</el-form-item>-->

      <!--<el-form-item v-if="dataForm.type === '3'">-->
        <!--<el-row>-->
          <!--<el-col :span="7">-->
            <!--<span>企业LOGO</span>-->
            <!--<el-upload-->
              <!--class="avatar-uploader"-->
              <!--:action="url"-->
              <!--:show-file-list="false"-->
              <!--:on-success="handleAvatarSuccess"-->
              <!--:before-upload="beforeAvatarUpload">-->
              <!--<img v-if="imageUrl" :src="imageUrl" class="avatar">-->
              <!--<i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
              <!--<i v-if="upload" class="el-icon-plus">上传中</i>-->
            <!--</el-upload>-->
          <!--</el-col>-->
          <!--<el-col :span="16" :offset="1">-->
            <!--<span>企业证明材料</span>-->
            <!--<el-upload-->
              <!--class="upload-demo"-->
              <!--:action="url"-->
              <!--:on-change="handleChange"-->
              <!--:file-list="dataForm.attachments">-->
              <!--<el-button size="small" type="primary">点击上传</el-button>-->
              <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5mb</div>-->
            <!--</el-upload>-->
          <!--</el-col>-->
        <!--</el-row>-->
      <!--</el-form-item>-->

      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entBusiness">-->
        <!--<el-input type="textarea" :rows="2" v-model.number="dataForm.ent.entBusiness" placeholder="经营范围"></el-input>-->
      <!--</el-form-item>-->

      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entRegisterAddress">-->
        <!--<el-input type="textarea" :rows="2" v-model.number="dataForm.ent.entRegisterAddress" placeholder="注册地址"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entIntroduce">-->
        <!--<el-input type="textarea" :rows="2" v-model.number="dataForm.ent.entIntroduce" placeholder="企业介绍"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item v-if="dataForm.type === '3'" prop="ent.entAddress">-->
        <!--<el-input type="textarea" :rows="2" v-model.number="dataForm.ent.entAddress" placeholder="企业地址"></el-input>-->
      <!--</el-form-item>-->
      <!--&lt;!&ndash; E 企业基本信息 &ndash;&gt;-->
      <!--<el-form-item v-if="dataForm.type === '3'">-->
        <!--<div style="text-align: center;border-bottom: 1px solid rgb(226, 226, 226);">下一步</div>-->
      <!--</el-form-item>-->

      <el-form-item prop="username">
        <el-input v-model="dataForm.username" placeholder="学号/工号"></el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="dataForm.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item prop="userPhone">
        <el-input v-model="dataForm.userPhone" placeholder="手机号"></el-input>
      </el-form-item>
      <!--<el-form-item prop="userEmail">-->
        <!--<el-input v-model="dataForm.userEmail" placeholder="邮箱"></el-input>-->
      <!--</el-form-item>-->
      <el-form-item prop="userPassword">
        <el-input v-model="dataForm.userPassword" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item prop="surePassword">
        <el-input v-model="dataForm.surePassword" type="password" placeholder="确认密码"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.type !== '3'" prop="instituteId">
        <el-select v-model="dataForm.instituteId" placeholder="请选择所属部门">
          <el-option v-for="item in instituteList" :key="item.instituteId" :label="item.instituteName"
                     :value="item.instituteId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="captcha">
        <el-row :gutter="24">
          <el-col :span="14">
            <el-input v-model="dataForm.captcha" placeholder="请输入验证码"></el-input>
          </el-col>
          <el-col :span="8">
            <el-button @click="getCaptchaSubmit()" type="success" :disabled="codeDisable" v-if="codeDisable==false">获取验证码</el-button>
            <el-button @click="getCaptchaSubmit()" type="success" :disabled="codeDisable" v-if="codeDisable==true">{{codeSendMsg}}</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="registerLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

<script>
  import {isMobile} from '@/utils/validate'

  export default {
    data () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      // var validateEmail = (rule, value, callback) => {
      //   if (!isEmail(value)) {
      //     callback(new Error('邮箱格式错误'))
      //   } else {
      //     callback()
      //   }
      // }
      var validateSurePassword = (rule, value, callback) => {
        if (this.dataForm.userPassword !== this.dataForm.surePassword) {
          callback(new Error('密码不一致'))
        } else {
          callback()
        }
      }
      return {
        imageUrl: '',
        url: '',
        upload: false,
        entType: [
          {lable: '合资', value: 1},
          {lable: '独资', value: 2},
          {lable: '国有', value: 3},
          {lable: '私营', value: 4},
          {lable: '全民所有制', value: 5},
          {lable: '集体所有制', value: 6},
          {lable: '股份制', value: 7},
          {lable: '有限责任', value: 8}
        ],
        entStatus: [
          {lable: '存续(开业)', value: 1},
          {lable: '在业', value: 2},
          {lable: '吊销', value: 3},
          {lable: '注销', value: 4},
          {lable: '迁入', value: 5},
          {lable: '迁出', value: 6},
          {lable: '停业', value: 7},
          {lable: '清算', value: 8}
        ],
        codeSendMsg: '重新发送',
        time: 0,
        visible: false,
        loading: false,
        codeDisable: false,
        registerLoading: false,
        // instituteList: this.$store.state.user.institute,
        instituteList: [],
        dataForm: {
          id: 0,
          username: '',
          name: '',
          userPassword: '',
          surePassword: '',
          instituteId: '',
          userPhone: '',
          userEmail: '',
          type: '',
          captcha: '',
          attachments: [], // 证明材料
          // 企业注册信息
          ent: {
            entLogo: '', // 企业LOGO
            newHighZones: '', // 是否高新区:0是，1否
            entName: '', // 企业名称
            entCorporate: '', // 法人代表
            entBusiness: '', // 经营范围
            entCode: '', // 统一信用代码
            entRegister: '', // 注册资本
            entFoundingTime: '', // 成立时间
            entType: '', // 企业类型
            entStatus: 0, // 经营状态:1存续(开业)，2在业，3吊销，4注销，5迁入，6迁出，7停业，8清算
            entRegisterAddress: '', // 注册地址
            entAddress: '', // 企业地址
            entIntroduce: '' // 企业介绍
          }
        },
        dataRule: {
          // ent: {
          //   entType: [
          //     {required: true, message: '企业类型不能为空', trigger: 'blur'}
          //   ],
          //   entBusiness: [
          //     {required: true, message: '经营范围不能为空', trigger: 'blur'}
          //   ],
          //   newHighZones: [
          //     {required: true, message: '是否高新区不能为空', trigger: 'blur'}
          //   ],
          //   entStatus: [
          //     {required: true, message: '经营状态不能为空', trigger: 'blur'}
          //   ],
          //   entRegisterAddress: [
          //     {required: true, message: '注册地址不能为空', trigger: 'blur'}
          //   ],
          //   entAddress: [
          //     {required: true, message: '企业地址不能为空', trigger: 'blur'}
          //   ],
          //   entIntroduce: [
          //     {required: true, message: '企业介绍不能为空', trigger: 'blur'}
          //   ],
          //   entFoundingTime: [
          //     {required: true, message: '成立时间不能为空', trigger: 'blur'}
          //   ],
          //   entRegister: [
          //     { required: true, type: 'number', message: '只能输入数字', trigger: 'blur' }
          //   ],
          //   entCode: [
          //     {required: true, message: '统一信用代码', trigger: 'blur'}
          //   ],
          //   entCorporate: [
          //     {required: true, message: '法人代表不能为空', trigger: 'blur'}
          //   ],
          //   entName: [
          //     {required: true, message: '企业名称不能为空', trigger: 'blur'}
          //   ]
          // },
          username: [
            {required: true, message: '账号不能为空', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '姓名不能为空', trigger: 'blur'}
          ],
          // userEmail: [
          //   {required: true, message: '邮箱不能为空', trigger: 'blur'},
          //   {validator: validateEmail, trigger: 'blur'}
          // ],
          instituteId: [
            {required: true, message: '所属部门不能为空', trigger: 'blur'}
          ],
          userPhone: [
            {required: true, message: '手机号不能为空', trigger: 'blur'},
            {validator: validateMobile, trigger: 'blur'}
          ],
          userPassword: [
            {required: true, message: '密码不能为空', trigger: 'blur'}
          ],
          surePassword: [
            {required: true, message: '确认密码不能为空', trigger: 'blur'},
            {validator: validateSurePassword, trigger: 'blur'}
          ],
          type: [
            {required: true, message: '角色不能为空', trigger: 'blur'}
          ],
          captcha: [
            {required: true, message: '验证码不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    activated () {
      this.getDataList()
      this.dataForm.ent.newHighZones = '0'
      this.dataForm.type = '1'
      this.dataForm.ent.entStatus = 1
      this.url = this.$http.adornUrl('/common/file/upload')
    },
    mounted () {
      this.dataForm.ent.newHighZones = '0'
      this.dataForm.type = '1'
      this.dataForm.ent.entStatus = 1
      this.url = this.$http.adornUrl('/common/file/upload')
    },
    methods: {
      handleChange (file, fileList) {
        console.log(fileList)
        this.dataForm.attachments = fileList.slice(-3)
        console.log('结果', this.dataForm.attachments)
      },
      handleAvatarSuccess (res, file) {
        // console.log(res, file)
        this.upload = false
        this.dataForm.ent.entLogo = res.data
        this.imageUrl = URL.createObjectURL(file.raw)
      },
      beforeAvatarUpload (file) {
        // console.log(this.url)
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      init () {
        this.getDataList()
      },
      getDataList () {
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/institute/all'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.instituteList = data.institute
            this.visible = true
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      getCaptchaSubmit () {
        if (isMobile(this.dataForm.userPhone)) {
          this.codeDisable = true
          this.time = 60
          this.timer()
          this.$http({
            url: this.$http.adornUrl(`/sys/message`),
            method: 'post',
            data: this.$http.adornData({
              'mobile': this.dataForm.userPhone
            })
          }).then(({data}) => {
            this.$message.success('发送短信成功')
            console.info(data)
          })
        } else {
          this.$message.error('请输入正确的手机号！')
        }
      },
      // 60S倒计时
      timer () {
        if (this.time > 0) {
          this.time--
          this.codeSendMsg = this.time + 's后重新获取'
          setTimeout(this.timer, 1000)
        } else {
          this.time = 0
          this.codeSendMsg = '获取验证码'
          this.codeDisable = false
        }
      },
      // 表单提交
      dataFormSubmit () {
        // this.registerLoading = true
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/sys/register`),
              method: 'post',
              data: this.$http.adornData({
                'username': this.dataForm.username,
                'name': this.dataForm.name,
                'password': this.dataForm.userPassword,
                'instituteId': this.dataForm.instituteId,
                'mobile': this.dataForm.userPhone,
                'email': this.dataForm.userEmail,
                'type': this.dataForm.type,
                'captcha': this.dataForm.captcha,
                'ent': this.dataForm.ent,
                'attachments': this.dataForm.attachments
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
                this.registerLoading = false
              }
            })
          } else {
            setTimeout(function () {
              this.registerLoading = false
            }, 500)
          }
        })
      },
      closeRegister () {
        this.visible = false
        this.$emit('refreshDataList')
      }
    }
  }
</script>
