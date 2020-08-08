<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
          @change="getDataList"
          v-model="dataForm.declareTime"
          align="right"
          type="year"
          placeholder="请选择年度">
        </el-date-picker>
      </el-form-item>
      <!--<el-form-item>-->
        <!--<el-input v-model="dataForm.projectName" placeholder="项目名" clearable></el-input>-->
      <!--</el-form-item>-->
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <!--<el-button v-if="isAuth('innovate:project:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
        <!--<el-button v-if="isAuth('innovate:project:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <!--<el-card>-->
      <!--<el-radio-group v-model="hasApply" @change="getDataList">-->
        <!--<el-radio label="1">未评分</el-radio>-->
        <!--<el-radio label="2">已评分</el-radio>-->
      <!--</el-radio-group>-->
    <!--</el-card>-->
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      :default-sort="{prop: 'declareInfoEntity.declareId', order: 'ascending'}"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        header-align="center"
        align="center"
        label="序号"
        width="50">
        <template slot-scope="props">
          <p v-text="props.$index+1"></p>
        </template>
      </el-table-column>
      <!--<el-table-column-->
        <!--hidden-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="50"-->
        <!--label="ID">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--sortable-->
        <!--hidden-->
        <!--type="expand"-->
        <!--prop="declareInfoEntity.declareId"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="120"-->
        <!--label="展开流程进度">-->
        <!--<template slot-scope="props">-->
          <!--<el-row>-->
            <!--<el-card style=": 0.1rem">-->
              <!--<el-col :span="3">-->
                <!--<el-tag>大创申请审批进度</el-tag>-->
              <!--</el-col>-->
              <!--<el-col :span="21">-->
                <!--<el-steps-->
                  <!--:active="props.row.declareInfoEntity.projectAuditApplyStatus"-->
                  <!--finish-status="success">-->
                  <!--<el-step title="项目负责人提交"></el-step>-->
                  <!--<el-step title="指导老师审批"></el-step>-->
                  <!--<el-step title="二级学院审批"></el-step>-->
                  <!--<el-step title="管理员分配评委组"></el-step>-->
                  <!--<el-step title="评委审批"></el-step>-->
                  <!--<el-step title="管理员审批"></el-step>-->
                  <!--&lt;!&ndash;<el-step title="超级管理员审批"></el-step>&ndash;&gt;-->
                <!--</el-steps>-->
              <!--</el-col>-->
            <!--</el-card>-->
          <!--</el-row>-->
        <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column
        sortable
        prop="declareInfoEntity.declareName"
        header-align="center"
        align="center"
        label="大创项目名称">
      </el-table-column>

      <el-table-column
        sortable
        prop="sysUserEntity.name"
        header-align="center"
        align="center"
        label="评委姓名">
      </el-table-column>

      <el-table-column
        sortable
        prop="sysUserEntity.mobile"
        header-align="center"
        align="center"
        label="联系电话">
      </el-table-column>
      <el-table-column
        sortable
        prop="declareInfoEntity.declareTime"
        header-align="center"
        align="center"
        label="申报时间">
        <template slot-scope="scope">
          <!--<el-tag v-if="scope.row.declareInfoEntity.declareTime === null" size="small">未申报</el-tag>-->
          <el-tag v-if="scope.row.declareInfoEntity.declareTime != null" size="small">{{scope.row.declareInfoEntity.declareTime}}</el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column-->
        <!--fixed="right"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="220"-->
        <!--label="操作">-->
        <!--<template slot-scope="scope">-->
          <!--<el-button v-if="isAuth('innovate:project:list')" type="text" size="small" @click="detailHandle(scope.row.declareInfoEntity.declareId)">详情</el-button>-->
          <!--<el-button v-if="addOrUpadate(scope.row.declareInfoEntity)" type="text" size="small" @click="addOrUpdateHandle(scope.row.declareInfoEntity.declareId)">修改</el-button>-->
          <!--<el-button v-if="isDelete(scope.row.declareInfoEntity)" type="text" size="small" @click="deleteHandle(scope.row.declareInfoEntity.matchId)">删除</el-button>-->
          <!--<br v-if="applyDeclareIsVisible(scope.row.declareInfoEntity)">-->
          <!--&lt;!&ndash;<el-button v-if="applyDeclareIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="applyDeclareHandle(scope.row.declareInfoEntity.declareId)">通过</el-button>&ndash;&gt;-->
          <!--<el-button v-if="applyDeclareIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="sighingOpinionsHandle(scope.row.declareInfoEntity.declareId)">签署意见</el-button>-->
          <!--<el-button v-if="retreatIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="retreatHandle(scope.row.declareInfoEntity)">不通过</el-button>-->
        <!--</template>-->
      <!--</el-table-column>-->
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
    <!--<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
    <!--<update-history v-if="isHistoyVisible" ref="updateHistory" @refreshDataList="getDataList"></update-history>-->
    <!--<update-add-or-update v-if="isUpdateVisible" ref="isUpdate" @refreshDataList="getDataList"></update-add-or-update>-->
    <!--<detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></detail>-->
    <!--<retreat-add-or-update v-if="retreatVisible" ref="retreat" @refreshDataList="getDataList"></retreat-add-or-update>-->
    <!--<sighing-opinions v-if="sighingOpinionsVisible" ref="sighingOpinions" @refreshDataList="getDataList"></sighing-opinions>-->
  </div>
</template>

<script>
  import AddOrUpdate from './project/info-add-or-update'
  import Detail from './operation/info-detail'
  import UpdateAddOrUpdate from './operation/update-add-or-update'
  import UpdateHistory from './operation/update-history'
  import RetreatAddOrUpdate from './operation/retreat-add-or-update'
  import SighingOpinions from './operation/sighing-opinions'

  export default {
    data () {
      return {
        hasApply: '1',
        dataForm: {
          projectName: '',
          baseId: '',
          declareTime: new Date(),
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
        retreatVisible: false,
        isHistoyVisible: false,
        sighingOpinionsVisible: false// 签署意见
      }
    },
    components: {
      SighingOpinions,
      UpdateHistory,
      UpdateAddOrUpdate,
      RetreatAddOrUpdate,
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
          url: this.$http.adornUrl('/innovate/declare/review/unReview'),
          method: 'get',
          params: this.$http.adornParams({
            'projectName': this.dataForm.projectName,
            'declareTime': this.dataForm.declareTime == null ? '' : this.dataForm.declareTime.getFullYear(),
            'page': this.pageIndex,
            'limit': this.pageSize
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
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
      applyDeclareIsVisible (item) {
        if (this.isAuth('innovate:project:apply:audit')) {
          if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
            if (item.projectAuditApplyStatus === 1) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 3) {
                  return true
                }
              }
            }
          }
        }
        return false
      },
      retreatIsVisible (item) {
        if (this.isAuth('innovate:declare:retreat')) {
          if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
            if (item.projectAuditApplyStatus === 1) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 3) {
                  return true
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
      // 审批通过签署意见
      sighingOpinionsHandle (id) {
        this.sighingOpinionsVisible = true
        this.$nextTick(() => {
          this.$refs.sighingOpinions.init(id)
        })
      },
      // 不通过
      retreatHandle (item) {
        this.retreatVisible = true
        this.$nextTick(() => {
          this.$refs.retreat.init(item.declareId, 'project_audit_apply_status', item.projectAuditApplyStatus)
        })
      },
      // 审批
      applyDeclareHandle (id) {
        this.$confirm('此操作将使该项目进入下一个审批流程，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/declare/apply/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'declareId': id,
              'apply': 'project_audit_apply_status',
              'roleId': 3
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
        if ((item.projectAuditApplyStatus === 0) &&
          this.isAuth('innovate:declare:update')) {
          return true
        }
      },
      isDelete (item) {
        if ((item.projectAuditApplyStatus === 0) &&
          this.isAuth('innovate:declare:delete')) {
          return true
        }
      },
      // 删除
      deleteHandle (id) {
        var canDelete = true
        var declareIds = id ? [id] : this.dataListSelections.map(item => {
          if ((item.projectInfoEntity.projectAuditApplyStatus > 0) ||
            !this.isAuth('innovate:declare:delete')) {
            canDelete = false
          } else {
            canDelete = false
          }
          return item.projectInfoEntity.declareId
        })
        this.$confirm(`确定要进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (canDelete) {
            this.$http({
              url: this.$http.adornUrl('/innovate/declare/info/delete'),
              method: 'post',
              data: this.$http.adornData(declareIds, false)
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
