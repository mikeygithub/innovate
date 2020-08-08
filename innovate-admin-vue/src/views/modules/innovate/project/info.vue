<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="项目名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('innovate:project:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('innovate:project:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      :default-sort="{prop: 'projectInfoEntity.projectId', order: 'ascending'}"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        hidden
        header-align="center"
        align="center"
        width="50"
        label="ID">
        <template slot-scope="props">
          <p v-text="props.$index+1"></p>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        hidden
        type="expand"
        prop="projectInfoEntity.projectId"
        header-align="center"
        align="center"
        width="120"
        label="展开流程进度">
        <template slot-scope="props">
          <el-row>
            <el-card style=": 0.1rem">
              <el-col :span="3">
                <el-tag>大创申请审批进度</el-tag>
              </el-col>
              <el-col :span="21">
                <el-steps
                  :active="props.row.projectInfoEntity.projectAuditApplyStatus"
                  finish-status="success">
                  <el-step title="项目负责人提交"></el-step>
                  <el-step title="指导老师审批"></el-step>
                  <el-step title="二级学院审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="评委审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="超级管理员审批"></el-step>
                </el-steps>
              </el-col>
            </el-card>
          </el-row>
          <el-row>
            <el-card style=": 0.1rem">
              <el-col :span="3">
                <el-tag>基地申请审批进度</el-tag>
              </el-col>
              <el-col :span="21">
                <el-steps
                  :active="props.row.projectInfoEntity.projectBaseApplyStatus"
                  finish-status="success">
                  <el-step title="项目负责人提交"></el-step>
                  <el-step title="指导老师审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="评委审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="超级管理员审批"></el-step>
                </el-steps>
              </el-col>
            </el-card>
          </el-row>
          <el-row>
            <el-card style=": 0.1rem">
              <el-col :span="3">
                <el-tag>比赛申请审批进度</el-tag>
              </el-col>
              <el-col :span="21">
                <el-steps
                  :active="props.row.projectInfoEntity.projectMatchApplyStatus"
                  finish-status="success">
                  <el-step title="项目负责人提交"></el-step>
                  <el-step title="指导老师审批"></el-step>
                  <el-step title="二级学院审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="评委审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="超级管理员审批"></el-step>
                </el-steps>
              </el-col>
            </el-card>
          </el-row>
          <el-row>
            <el-card style=": 0.1rem">
              <el-col :span="3">
                <el-tag>结题申请审批进度</el-tag>
              </el-col>
              <el-col :span="21">
                <el-steps
                  :active="props.row.projectInfoEntity.projectFinishApplyStatus"
                  finish-status="success">
                  <el-step title="项目负责人提交"></el-step>
                  <el-step title="指导老师审批"></el-step>
                  <el-step title="二级学院审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="评委审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="超级管理员审批"></el-step>
                </el-steps>
              </el-col>
            </el-card>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        prop="projectInfoEntity.projectName"
        header-align="center"
        align="center"
        label="企业项目名称">
      </el-table-column>
      <el-table-column
        sortable
        prop="projectInfoEntity.projectRegStatus"
        header-align="center"
        align="center"
        label="是否工商注册">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.projectInfoEntity.projectRegStatus === 1" size="small">是</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectRegStatus === 2" size="small">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        prop="projectInfoEntity.projectIndustry"
        header-align="center"
        align="center"
        label="所属行业">
        <template slot-scope="scope">
          <span v-for="item in statusList"
                  :key="item.value"
                  v-if="scope.row.projectInfoEntity.projectIndustry === item.value"
                  v-text="item.label"
                  size="small">
          </span>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        prop="projectInfoEntity.projectType"
        header-align="center"
        align="center"
        label="企业类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.projectInfoEntity.projectType === 1" size="small">先进制造业</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectType === 2" size="small">现代服务业</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectType === 3" size="small">现代农业</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectType === 4" size="small">批发零售业</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectType === 5" size="small">其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        prop="projectInfoEntity.projectInitMoney"
        header-align="center"
        align="center"
        label="初始投资（万元）">
      </el-table-column>
      <el-table-column
        sortable
        prop="projectInfoEntity.projectStatus"
        header-align="center"
        align="center"
        label="企业当前状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.projectInfoEntity.projectStatus === ''
          || scope.row.projectInfoEntity.projectStatus === 0
          || scope.row.projectInfoEntity.projectStatus === null"
                  size="small">
            未入驻
          </el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectStatus === 1" size="small">在驻</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectStatus === 2" size="small">孵化成功出园</el-tag>
          <el-tag v-if="scope.row.projectInfoEntity.projectStatus === 3" size="small">孵化失败出园</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="220"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('innovate:project:list')" type="text" size="small" @click="detailHandle(scope.row.projectInfoEntity.projectId)">详情</el-button>
          <el-button v-if="addOrUpadate(scope.row.projectInfoEntity)" type="text" size="small" @click="addOrUpdateHandle(scope.row.projectInfoEntity.projectId)">修改</el-button>
          <el-button v-if="isDelete(scope.row.projectInfoEntity)" type="text" size="small" @click="deleteHandle(scope.row.projectInfoEntity.projectId)">删除</el-button>
          <br v-if="isUpadate(scope.row.projectInfoEntity)"/>
          <el-button v-if="isUpadate(scope.row.projectInfoEntity)" type="text" size="small" @click="isUpdateHandle(scope.row.projectInfoEntity.projectId)">申请修改</el-button>
          <el-button v-if="isUpadate(scope.row.projectInfoEntity)" type="text" size="small" @click="updateHistoryHandle(scope.row.projectInfoEntity.projectId)">申请记录</el-button>
          <br v-if="applyAuditIsVisible(scope.row.projectInfoEntity)"/>
          <el-button v-if="applyAuditIsVisible(scope.row.projectInfoEntity)" type="text" size="small" @click="applyAuditHandle(scope.row.projectInfoEntity.projectId)">提交创新申请</el-button>
          <br v-if="applyBaseIsVisible(scope.row.projectInfoEntity)">
          <el-button v-if="applyBaseIsVisible(scope.row.projectInfoEntity)" type="text" size="small" @click="applyBaseHandle(scope.row.projectInfoEntity.projectId)">提交基地申请</el-button>
          <br v-if="applyMatchIsVisible(scope.row.projectInfoEntity)">
          <el-button v-if="applyMatchIsVisible(scope.row.projectInfoEntity)" type="text" size="small" @click="applyMatchHandle(scope.row.projectInfoEntity.projectId)">提交比赛申请</el-button>
          <br v-if="applyFinishIsVisible(scope.row.projectInfoEntity)">
          <el-button v-if="applyFinishIsVisible(scope.row.projectInfoEntity)" type="text" size="small" @click="applyFinishHandle(scope.row.projectInfoEntity.projectId)">提交结题申请</el-button>
          </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[5, 10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <update-history v-if="isHistoyVisible" ref="updateHistory" @refreshDataList="getDataList"></update-history>
    <update-add-or-update v-if="isUpdateVisible" ref="isUpdate" @refreshDataList="getDataList"></update-add-or-update>
    <detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></detail>
  </div>
</template>

<script>
  import AddOrUpdate from './info-add-or-update'
  import Detail from './info-detail'
  import UpdateAddOrUpdate from './update-add-or-update'
  import UpdateHistory from './update-history'

  export default {
    data () {
      return {
        projectList: [],
        userTeacherInfoEntities: [],
        dataForm: {
          baseId: '',
          idDel: 0
        },
        statusList: [
          {value: 1, label: '农、林、牧、渔业'}, {value: 2, label: '采矿业'},
          {value: 3, label: '制造业'}, {value: 4, label: '电力、热力、燃气及水的生产和供应业'},
          {value: 5, label: '环境和公共设施管理业'}, {value: 6, label: '建筑业'},
          {value: 7, label: '交通运输、仓储业和邮政业'}, {value: 8, label: '信息传输、计算机服务和软件业'},
          {value: 9, label: '批发和零售业'}, {value: 10, label: '住宿、餐饮业'},
          {value: 11, label: '金融、保险业'}, {value: 12, label: '房地产业'},
          {value: 13, label: '租赁和商务服务业'}, {value: 14, label: '科学研究、技术服务和地质勘查业'},
          {value: 15, label: '水利、环境和公共设施管理业'}, {value: 16, label: '居民服务和其他服务业'},
          {value: 17, label: '教育'}, {value: 18, label: '卫生、社会保障和社会服务业'},
          {value: 19, label: '文化、体育、娱乐业'}, {value: 20, label: '综合（含投资类、主业不明显）'},
          {value: 21, label: '其它'}
        ],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        detailVisible: false,
        applyVisible: false,
        isUpdateVisible: false,
        isHistoyVisible: false
      }
    },
    components: {
      UpdateHistory,
      UpdateAddOrUpdate,
      AddOrUpdate,
      Detail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.addOrUpdateVisible = false
        this.detailVisible = false
        this.isUpdateVisible = false
        this.isHistoyVisible = false
        this.$http({
          url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
          method: 'get',
          params: this.$http.adornParams({
            'like': ''
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.userTeacherInfoEntities = data.userTeacherInfoEntities
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'username': this.dataForm.userName,
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            // 'isTeacher': true
            'isStudent': true,
            // 'apply': 'project_audit_apply_status',
            // 'applyStatus': 3,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            this.$store.state.user.grade = data.grade
            this.$store.state.user.institute = data.institute
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 新增 / 修改
      isUpdateHandle (id) {
        this.isUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.isUpdate.init(id)
        })
      },
      // 历史记录
      updateHistoryHandle (id) {
        this.isHistoyVisible = true
        this.$nextTick(() => {
          this.$refs.updateHistory.init(id)
        })
      },
      applyAuditIsVisible (item) {
        if (item.projectStatus === 0 || item.projectStatus === 1) {
          if (this.isAuth('innovate:project:apply:audit')) {
            if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
              if (item.projectAuditApplyStatus === 0) {
                let roleIdList = this.$store.state.user.roleIdList
                for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                  if (roleIdList[roleIndex] === 2) {
                    return true
                  }
                }
              }
            }
          }
        }
        return false
      },
      applyBaseIsVisible (item) {
        if (item.projectStatus === 0 || item.projectStatus === 1) {
          if (this.isAuth('innovate:project:apply:base')) {
            if (item.projectBaseApplyStatus !== null || item.projectBaseApplyStatus !== '') {
              if (item.projectBaseApplyStatus === 0) {
                let roleIdList = this.$store.state.user.roleIdList
                for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                  if (roleIdList[roleIndex] === 2) {
                    return true
                  }
                }
              }
            }
          }
        }
        return false
      },
      applyMatchIsVisible (item) {
        if (item.projectStatus === 0 || item.projectStatus === 1) {
          if (this.isAuth('innovate:project:apply:match')) {
            if (item.projectMatchApplyStatus !== null || item.projectMatchApplyStatus !== '') {
              if (item.projectMatchApplyStatus === 0) {
                let roleIdList = this.$store.state.user.roleIdList
                for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                  if (roleIdList[roleIndex] === 2) {
                    return true
                  }
                }
              }
            }
          }
        }
        return false
      },
      applyFinishIsVisible (item) {
        if (item.projectStatus === 0 || item.projectStatus === 1) {
          if (item.projectMatchApplyStatus === 7) {
            if (this.isAuth('innovate:project:apply:finish')) {
              if (item.projectFinishApplyStatus !== null || item.projectFinishApplyStatus !== '') {
                if (item.projectFinishApplyStatus === 0) {
                  let roleIdList = this.$store.state.user.roleIdList
                  for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                    if (roleIdList[roleIndex] === 2) {
                      return true
                    }
                  }
                }
              }
            }
          }
        }
        return false
      },
      // 详情
      detailHandle (id) {
        this.detailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      // 审批
      applyAuditHandle (id) {
        this.$confirm('此操作将使该项目进入不可修改状态，并进入大创审批流程，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/project/apply/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'projectId': id,
              'apply': 'project_audit_apply_status',
              'roleId': 2
            }, false)
          }).then(({data}) => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.getDataList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消申请'
          })
        })
      },
      // 审批
      applyBaseHandle (id) {
        this.$confirm('此操作将使该项目进入不可修改状态，并进入基地审批流程，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/project/apply/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'projectId': id,
              'apply': 'project_base_apply_status',
              'roleId': 2
            }, false)
          }).then(({data}) => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.getDataList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消申请'
          })
        })
      },
      // 审批
      applyMatchHandle (id) {
        this.$confirm('此操作将使该项目进入不可修改状态，并进入基地审批流程，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/project/apply/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'projectId': id,
              'apply': 'project_match_apply_status',
              'roleId': 2
            }, false)
          }).then(({data}) => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.getDataList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消申请'
          })
        })
      },
      // 审批
      applyFinishHandle (id) {
        this.$confirm('此操作将使该项目进入不可修改状态，并进入基地审批流程，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/project/apply/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'projectId': id,
              'apply': 'project_finish_apply_status',
              'roleId': 2
            }, false)
          }).then(({data}) => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.getDataList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消申请'
          })
        })
      },
      addOrUpadate (item) {
        if (item.projectStatus === 0) {
          if ((item.projectAuditApplyStatus === 0 || item.projectAuditApplyStatus >= 7) &&
            (item.projectBaseApplyStatus === 0 || item.projectBaseApplyStatus >= 6) &&
            (item.projectMatchApplyStatus === 0 || item.projectMatchApplyStatus >= 7) &&
            (item.projectFinishApplyStatus === 0 || item.projectFinishApplyStatus >= 7) &&
            this.isAuth('innovate:project:update')) {
            return true
          }
        }
      },
      isDelete (item) {
        if (item.projectStatus === 0) {
          if ((item.projectAuditApplyStatus === 0 || item.projectAuditApplyStatus >= 7) &&
            (item.projectBaseApplyStatus === 0 || item.projectBaseApplyStatus >= 6) &&
            (item.projectMatchApplyStatus === 0 || item.projectMatchApplyStatus >= 7) &&
            (item.projectFinishApplyStatus === 0 || item.projectFinishApplyStatus >= 7) &&
            this.isAuth('innovate:project:delete')) {
            return true
          }
        }
      },
      isUpadate (item) {
        if (item.projectStatus === 1) {
          return true
        }
      },
      // 删除
      deleteHandle (id) {
        var canDelete = true
        var projectIds = id ? [id] : this.dataListSelections.map(item => {
          if (item.projectInfoEntity.projectStatus === 0) {
            if ((item.projectInfoEntity.projectAuditApplyStatus > 0 && item.projectInfoEntity.projectAuditApplyStatus < 7) ||
              (item.projectInfoEntity.projectBaseApplyStatus > 0 && item.projectInfoEntity.projectBaseApplyStatus < 6) ||
              (item.projectInfoEntity.projectMatchApplyStatus > 0 && item.projectInfoEntity.projectMatchApplyStatus < 7) ||
              (item.projectInfoEntity.projectFinishApplyStatus > 0 && item.projectInfoEntity.projectFinishApplyStatus < 7) ||
              !this.isAuth('innovate:project:delete')) {
              canDelete = false
            }
          } else {
            canDelete = false
          }
          return item.projectInfoEntity.projectId
        })
        this.$confirm(`确定要进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (canDelete) {
            this.$http({
              url: this.$http.adornUrl('/innovate/project/info/delete'),
              method: 'post',
              data: this.$http.adornData(projectIds, false)
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.getDataList()
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          } else {
            this.$message({
              message: '包含不可删除项目',
              type: 'error',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          }
        }).catch(() => {})
      }
    }
  }
</script>
<style>
  .el-step__title {
    font-size: 12px;
    line-height: 28px;
  }
  .el-table__expanded-cell[class*=cell] {
    padding: 5px 5px;
  }
</style>
