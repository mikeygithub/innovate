<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增指导老师' : '修改指导老师'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="教师姓名" prop="userId">
        <el-select
          v-model="dataForm.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入关键词"
          :remote-method="selectUser"
          :loading="loading">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.name"
            :value="item.userId">
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
  export default {
    name: 'teacher-add-or-update',
    data () {
      return {
        visible: false,
        loading: false,
        instituteList: this.$store.state.user.institute,
        userList: [],
        userMap: [],
        sexList: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
        id: 0,
        teacherName: '',
        dataForm: {
          projectTeacherId: '',
          projectId: '',
          userId: '',
          isDel: 0
        },
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataRule: {
          userId: [
            { required: true, message: '请选择教师姓名', trigger: 'blur' }
          ]
        }
      }
    },
    mounted () {
      this.userMap = this.userList.map(item => {
        return { value: item, label: item }
      })
    },
    methods: {
      init (item, index) {
        this.visible = true
        this.$http({
          url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
          method: 'get',
          params: this.$http.adornParams({
            'like': ''
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.userList = data.sysTeacherEntities
          }
        })
        this.$nextTick(() => {
          this.dataForm.projectTeacherId = ''
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
      selectUser (query) {
        if (query !== '') {
          this.loading = true
          setTimeout(() => {
            this.loading = false
            this.$http({
              url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
              method: 'get',
              params: this.$http.adornParams({
                'like': query
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.userList = data.sysTeacherEntities
              }
            })
          }, 200)
        } else {
          this.userList = []
        }
      }
    }
  }
</script>
