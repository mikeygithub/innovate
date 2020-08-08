<template>
  <el-dialog
    @close="closeDialog"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="true"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem">
      <el-form-item label="评委组名" prop="groupName">
        <el-input v-model="dataForm.groupName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="评委组描述" prop="groupContent">
        <el-input v-model="dataForm.groupContent" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="评委组成员" prop="groupUserList">
        <el-transfer v-model="groupUserList"
                     :props="{
                        key: 'key',
                        label: 'label'
                      }"
                     :titles="['用户列表', '已选用户列表']"
                     :data="userList"></el-transfer>
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
    data () {
      return {
        visible: false,
        roleList: [],
        userList: [],
        groupUserList: [],
        sysUserEntities: [],
        dataForm: {
          groupId: 0,
          groupName: '',
          groupContent: '',
          innovateReviewGroupUserEntities: []
        },
        dataRule: {
          groupName1: [
            { required: true, message: '评委组名不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.groupId = id || 0
        this.visible = true
        // 从后台获取左边用户信息列表
        this.$http({
          url: this.$http.adornUrl(`/sys/user/all`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.sysUserEntities = data.sysUserEntities
            this.userList = []
            for (let index = 0; index < this.sysUserEntities.length; index++) {
              this.userList.push({
                key: this.sysUserEntities[index].userId,
                label: this.sysUserEntities[index].name
              })
            }
          }
        })
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.groupId) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/sys/group/info/${this.dataForm.groupId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.group
                this.groupUserList = []
                for (let index = 0; index < this.dataForm.innovateReviewGroupUserEntities.length; index++) {
                  this.groupUserList[index] = this.dataForm.innovateReviewGroupUserEntities[index].userId
                  let temp = 0
                  for (let item = 0; item < this.sysUserEntities.length; item++) {
                    if (this.dataForm.innovateReviewGroupUserEntities[index].userId === this.sysUserEntities[item].userId) {
                      continue
                    } else {
                      temp++
                    }
                  }
                  if (temp === this.sysUserEntities.length) {
                    this.userList.push({
                      key: this.dataForm.innovateReviewGroupUserEntities[index].userId,
                      label: '已不是评委请从列表中移除'
                    })
                  }
                }
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.dataForm.innovateReviewGroupUserEntities = []
            for (let item = 0; item < this.groupUserList.length; item++) {
              this.dataForm.innovateReviewGroupUserEntities.push({
                groupUserId: '',
                userId: this.groupUserList[item],
                groupId: this.dataForm.groupId
              })
            }
            this.$http({
              url: this.$http.adornUrl(`/innovate/sys/group/${!this.dataForm.groupId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'groupId': this.dataForm.groupId || undefined,
                'groupName': this.dataForm.groupName,
                'groupContent': this.dataForm.groupContent,
                'innovateReviewGroupUserEntities': this.dataForm.innovateReviewGroupUserEntities
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
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      }
    }
  }
</script>
