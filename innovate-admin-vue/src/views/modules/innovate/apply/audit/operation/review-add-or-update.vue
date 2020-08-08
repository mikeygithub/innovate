<template>
  <el-dialog
    @close="closeDialog"
    append-to-body
    :title="!id ? '新增' : '分配评委组'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="评委组" prop="declareInfoEntity.groupId">
        <el-select v-model="dataForm.declareInfoEntity.groupId" placeholder="请选择">
          <el-option
            v-for="item in groupList"
            :key="item.groupId"
            :label="item.groupName"
            :value="item.groupId">
          </el-option>
        </el-select>
        <el-tag v-if="reApply == 'true'" type="danger">*注意：重新分配评委组将导致之前分数丢失，请谨慎操作</el-tag>
      </el-form-item>
    </el-form>
    <el-card>
      当前评委组人员：
      <template v-for="item in groupList" v-if="item.groupId === dataForm.declareInfoEntity.groupId">
        <template v-for="groupUser in item.innovateReviewGroupUserEntities">
          <template v-for="user in sysUserEntities" v-if="groupUser.userId === user.userId">
            {{user.name}}
            <el-tag v-for="teacher in dataForm.declareTeacherEntities"
                    :key="teacher.declareTeacherId"
                    v-if="groupUser.userId === teacher.userId"
                    type="danger">
              该评委不能审核自己的项目
            </el-tag>、
          </template>
        </template>
      </template>
    </el-card>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: 'review-add-or-update',
    data () {
      return {
        userId: this.$store.state.user.id,
        visible: false,
        loading: false,
        addLoading: false,
        reApply: 'false',
        userMap: [],
        sysUserEntities: [],
        groupList: [],
        id: 0,
        dataForm: {
          declareInfoEntity: {
            groupId: ''
          }
        },
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataRule: {
          'declareInfoEntity.groupId': [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (index, reApply) {
        this.visible = true
        this.reApply = reApply
        this.$http({
          url: this.$http.adornUrl(`/innovate/sys/group/all`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.groupList = data.innovateReviewGroupEntity
          }
        })
        // 从后台获取评委用户信息列表
        this.$http({
          url: this.$http.adornUrl(`/sys/user/all`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.sysUserEntities = data.sysUserEntities
          }
        })
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/declare/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'declareId': this.id
                // 'apply': 'project_match_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.declareInfo
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl(`/innovate/declare/review/review`),
              method: 'post',
              data: this.$http.adornData({
                'groupId': this.dataForm.declareInfoEntity.groupId,
                'apply': 'project_audit_apply_status',
                'declareId': this.id,
                'roleId': 5,
                'userId': this.userId,
                'reApply': this.reApply
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.addLoading = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.addLoading = false
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
