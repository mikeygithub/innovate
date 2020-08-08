<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="工位号" prop="projectInfoEntity.stationId">
        <el-select v-model="dataForm.projectInfoEntity.stationId" placeholder="请选择">
          <el-option
            v-for="item in baseProjectStationEntities"
            :key="item.stationId"
            v-if="item.stationId === dataForm.projectInfoEntity.stationId"
            :label="'工位号:' + item.stationNum + '-面积:' + item.stationArea + '（已选）'"
            :value="item.stationId">
          </el-option>
          <el-option
            v-for="item in baseProjectStationEntities"
            :key="item.stationId"
            v-if="item.hasApply === 0"
            :label="'工位号:' + item.stationNum + '-面积:' + item.stationArea"
            :value="item.stationId">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: 'station-add-or-update',
    data () {
      return {
        addLoading: false,
        visible: false,
        loading: false,
        userMap: [],
        statusList: [{
          value: 0,
          label: '无状态'
        }, {
          value: 1,
          label: '在驻'
        }, {
          value: 2,
          label: '孵化成功出园'
        }, {
          value: 3,
          label: '孵化失败出园'
        }],
        id: 0,
        baseProjectStationEntities: [],
        dataForm: {
          projectInfoEntity: {
            stationId: 0
          }
        },
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataRule: {
          'projectInfoEntity.stationId': [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (index) {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/station/all`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              if (data.baseProjectStationEntities.length > 0) {
                this.baseProjectStationEntities = data.baseProjectStationEntities
              }
            }
          })
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/project/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'projectId': this.id
                // 'apply': 'project_match_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.projectInfo
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
              url: this.$http.adornUrl(`/innovate/project/info/station`),
              method: 'post',
              data: this.$http.adornData(this.dataForm)
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
