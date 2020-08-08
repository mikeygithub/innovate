<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem">
      <el-form-item label="姓名" prop="teacherName">
        <el-input v-model="dataForm.teacherName" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="teacherSex">
        <el-select v-model="dataForm.teacherSex" placeholder="请选择性别">
          <el-option
            v-for="item in sexList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="教师单位" prop="teacherUnit">
        <el-input v-model="dataForm.teacherUnit" placeholder="请输入教师单位"></el-input>
      </el-form-item>
      <el-form-item label="全职/兼职" prop="teacherWorkStatus">
        <el-select v-model="dataForm.teacherWorkStatus" placeholder="请选择全职/兼职">
          <el-option
            v-for="item in statusList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="手机号码" prop="teacherPhone">
        <el-input v-model="dataForm.teacherPhone" placeholder="请输入手机号码"></el-input>
      </el-form-item>
      <el-form-item label="教师职务" prop="teacherJob">
        <el-input v-model="dataForm.teacherJob" placeholder="请输入教师职务"></el-input>
      </el-form-item>
      <el-form-item label="职称" prop="teacherInstinct">
        <el-select v-model="dataForm.teacherInstinct" placeholder="请选择职称">
          <el-option
            v-for="item in instinctList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属中心" prop="baseId">
        <el-select v-model="dataForm.baseId" placeholder="请选择所属中心">
          <el-option
            v-for="item in baseList"
            :key="item.baseId"
            :label="item.baseName"
            :value="item.baseId">
          </el-option>
        </el-select>
      </el-form-item>
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
    data () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        baseList: [],
        statusList: [{
          value: 1,
          label: '全职'
        }, {
          value: 2,
          label: '兼职'
        }],
        sexList: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
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
        dataRule: {
          teacherName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          teacherSex: [
            { required: true, message: '请选择性别', trigger: 'blur' }
          ],
          teacherUnit: [
            { required: true, message: '教师单位不能为空', trigger: 'blur' }
          ],
          teacherWorkStatus: [
            { required: true, message: '请选择全职/兼职', trigger: 'blur' }
          ],
          teacherPhone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ],
          teacherJob: [
            { required: true, message: '教师职务不能为空', trigger: 'blur' }
          ],
          teacherInstinct: [
            { required: true, message: '请选择职称', trigger: 'blur' }
          ],
          baseId: [
            { required: true, message: '请选择所属中心', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataForm.id = id || 0
        this.$http({
          url: this.$http.adornUrl(`/innovate/base/info/list`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.baseList = data.page.list
          }
        })
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/teacher/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.teacherName = data.teacherInfo.teacherName
                this.dataForm.teacherSex = data.teacherInfo.teacherSex
                this.dataForm.teacherUnit = data.teacherInfo.teacherUnit
                this.dataForm.teacherWorkStatus = data.teacherInfo.teacherWorkStatus
                this.dataForm.teacherPhone = data.teacherInfo.teacherPhone
                this.dataForm.teacherJob = data.teacherInfo.teacherJob
                this.dataForm.teacherInstinct = data.teacherInfo.teacherInstinct
                this.dataForm.baseId = data.teacherInfo.baseId
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/teacher/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'teacherId': this.dataForm.id || undefined,
                'teacherName': this.dataForm.teacherName,
                'teacherSex': this.dataForm.teacherSex,
                'teacherUnit': this.dataForm.teacherUnit,
                'teacherWorkStatus': this.dataForm.teacherWorkStatus,
                'teacherPhone': this.dataForm.teacherPhone,
                'teacherJob': this.dataForm.teacherJob,
                'teacherInstinct': this.dataForm.teacherInstinct,
                'baseId': this.dataForm.baseId
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
              }
            })
          }
        })
      }
    }
  }
</script>
