<template>
  <el-dialog
    append-to-body
    @close="closeDialog"
    :title="!id ? '新增员工信息' : '修改员工信息'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="姓名" prop="staffName">
        <el-input v-model="dataForm.staffName" placeholder="请输入"></el-input>
      </el-form-item>
      <!--<el-col :span="14">-->
      <el-form-item label="员工类别" prop="staffType">
        <el-select v-model="dataForm.staffType" placeholder="请选择">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <!--</el-col>-->
      <!--<el-col :span="8" >-->
      <template v-if="dataForm.staffType === 1">
        <el-form-item label="合同">
          <el-upload
            class="upload-demo"
            :action="url"
            :data="{projectName: dataForm.projectName}"
            :on-success="successHandle1">
            <el-tooltip class="item" effect="dark" content="以附件形式上传提交上传合同的扫描件" placement="bottom-end">
              <el-button size="small" type="primary">点击上传合同</el-button>
            </el-tooltip>
            <label>
              （以附件形式上传提交创业计划书的扫描件）
            </label>
          </el-upload>
        </el-form-item>
      </template>
      <!--</el-col>-->
      <el-form-item label="性别" prop="staffSex">
        <el-select v-model="dataForm.staffSex" placeholder="请选择">
          <el-option
            v-for="item in sexList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="staffClassNo">
        <el-input v-model="dataForm.staffClassNo" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="企业职务" prop="staffPost">
        <el-input v-model="dataForm.staffPost" placeholder="请输入"></el-input>
      </el-form-item>
<!--      <el-form-item label="身份证号" prop="staffIdentify">-->
<!--        <el-input v-model="dataForm.staffIdentify" placeholder="请输入"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="联系电话" prop="staffTel">
        <el-input v-model="dataForm.staffTel" placeholder="请输入"></el-input>
      </el-form-item>
      <el-col :span="24">
        <el-form-item label="学生证">
          <el-upload
            class="upload-demo"
            :action="url"
            :data="{projectName: dataForm.projectName}"
            :on-success="successHandle1">
          <el-tooltip class="item" effect="dark" content="以附件形式上传提交上传学生证的扫描件" placement="bottom-end">
            <el-button size="small" type="primary">点击上传学生证</el-button>
          </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-col>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile } from '@/utils/validate'
  export default {
    name: 'staff-add-or-update',
    data () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      return {
        url: '',
        visible: false,
        typeList: [{
          value: 1,
          label: '正式员工'
        }, {
          value: 2,
          label: '见习员工'
        }],
        sexList: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
        id: 0,
        dataForm: {
          staffId: '',
          projectId: '',
          staffIdentify: '',
          staffName: '',
          staffSex: '',
          staffClassNo: '',
          staffPost: '',
          staffTel: '',
          staffType: '',
          staffStuCard: '',
          staffCompact: '',
          isDel: 0
        },
        dataRule: {
          staffName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          staffType: [
            { required: true, message: '请选择员工类别', trigger: 'blur' }
          ],
          staffSex: [
            { required: true, message: '请选择性别', trigger: 'blur' }
          ],
          staffClassNo: [
            { required: true, message: '班级不能为空', trigger: 'blur' }
          ],
          staffPost: [
            { required: true, message: '企业职务不能为空', trigger: 'blur' }
          ],
          staffTel: [
            { required: true, message: '联系方式不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (item, index) {
        this.url = this.$http.adornUrl(`/innovate/project/attach/upload?token=${this.$cookie.get('token')}`)
        this.visible = true
        this.id = index || 0
        this.$nextTick(() => {
          this.dataForm.staffId = ''
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
            this.dataForm = JSON.parse(JSON.stringify(item))
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.visible = false
            this.$emit('refreshDataList', this.dataForm, this.id)
          }
        })
      },
      closeDialog () {
        this.visible = false
      },
      // 上传学生证成功
      successHandle1 (response, file, fileList) {
        if (response && response.code === 0) {
          // this.staffStuCard = response.projectAttachEntity.attachPath;
        } else {
          this.$message.error(response.msg)
        }
      },
      // 上传合同成功
      successHandle2 (response, file, fileList) {
        if (response && response.code === 0) {
          // this.staffCompact = response.projectAttachEntity.attachPath
        } else {
          this.$message.error(response.msg)
        }
      }
    }
  }
</script>
