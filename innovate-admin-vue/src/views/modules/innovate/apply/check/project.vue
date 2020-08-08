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
      <el-form-item>
        <el-input v-model="dataForm.projectName" placeholder="项目名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量设置中期检查</el-button>
        <el-button type="primary" @click="setByYearHandle()">按照年度批量设置</el-button>
      </el-form-item>
    </el-form>
    <!--<el-card>-->
      <!--<el-radio-group v-model="hasApply" @change="getDataList">-->
        <!--<el-radio label="1">未审批</el-radio>-->
        <!--<el-radio label="2">已审批</el-radio>-->
        <!--<el-radio label="10">未打分项目</el-radio>-->
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
        prop="declareInfoEntity.declareId"
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
                  :active="props.row.declareInfoEntity.projectAuditApplyStatus"
                  finish-status="success">
                  <el-step title="项目负责人提交"></el-step>
                  <el-step title="指导老师审批"></el-step>
                  <el-step title="二级学院审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <el-step title="评委审批"></el-step>
                  <el-step title="管理员审批"></el-step>
                  <!--<el-step title="超级管理员审批"></el-step>-->
                </el-steps>
              </el-col>
            </el-card>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        prop="declareInfoEntity.declareName"
        header-align="center"
        align="center"
        label="大创项目名称">
      </el-table-column>
      <el-table-column
        sortable
        prop="declareInfoEntity.declareType"
        header-align="center"
        align="center"
        label="申报类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.declareInfoEntity.declareType === 1" size="small">创新训练项目</el-tag>
          <el-tag v-if="scope.row.declareInfoEntity.declareType === 2" size="small">创业训练项目</el-tag>
          <el-tag v-if="scope.row.declareInfoEntity.declareType === 3" size="small">创业实践项目</el-tag>
        </template>
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
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="220"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="detailHandle(scope.row.declareInfoEntity.declareId)">详情</el-button>
          <!--<el-button v-if="addOrUpdate(scope.row.declareInfoEntity)" type="text" size="small" @click="addOrUpdateHandle(scope.row.declareInfoEntity.declareId)">修改</el-button>-->
          <!--<el-button v-if="isDelete(scope.row.declareInfoEntity)" type="text" size="small" @click="deleteHandle(scope.row.declareInfoEntity.declareId)">删除</el-button>-->
          <!--<br v-if="applyDeclareIsVisible(scope.row.declareInfoEntity)">-->
          <!--<el-button v-if="applyDeclareIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="applyAwardHandle(scope.row.declareInfoEntity.declareId)">评奖</el-button>-->
          <!--<el-button v-if="applyDeclareIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="applyDeclareRef(scope.row.declareInfoEntity.declareId)">通过</el-button>-->
          <!--<el-button v-if="retreatIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="retreatHandle(scope.row.declareInfoEntity)">不通过</el-button>-->
          <!--<br v-if="exportDeclareIsVisible(scope.row.declareInfoEntity)">-->
          <!--<el-button v-if="publicDeclareIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="publicDeclareHandle(scope.row.declareInfoEntity.declareId)">公布立项项目</el-button>-->
          <!--<el-button v-if="exportDeclareIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="exportDeclareHandle(scope.row.declareInfoEntity.declareId)">导出项目信息</el-button>-->
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
    <!--<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
    <set-check-by-year v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></set-check-by-year>
    <update-history v-if="isHistoyVisible" ref="updateHistory" @refreshDataList="getDataList"></update-history>
    <update-add-or-update v-if="isUpdateVisible" ref="isUpdate" @refreshDataList="getDataList"></update-add-or-update>
    <detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></detail>
    <retreat-add-or-update v-if="retreatVisible" ref="retreat" @refreshDataList="getDataList"></retreat-add-or-update>
    <award-add-or-update v-if="awardVisible" ref="award" @refreshDataList="getDataList"></award-add-or-update>
    <export-info-detail v-if="exportVisible" ref="ExportInfoDetail" @refreshDataList="getDataList"></export-info-detail>
  </div>
</template>

<script>
  import AddOrUpdate from './project/info-add-or-update'
  import SetCheckByYear from './project/set-check-by-year'
  import Detail from './operation/info-detail'
  import UpdateAddOrUpdate from './operation/update-add-or-update'
  import UpdateHistory from './operation/update-history'
  import RetreatAddOrUpdate from './operation/retreat-add-or-update'
  import AwardAddOrUpdate from './project/award-add-or-update'
  import ExportInfoDetail from './operation/export-info-detail'

  export default {
    data () {
      return {
        // projectList: [],
        // eventLists: [],
        userTeacherInfoEntities: this.$store.state.userTeacherInfoEntities,
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
        awardVisible: false,
        isHistoyVisible: false,
        exportVisible: false
      }
    },
    components: {
      ExportInfoDetail,
      AwardAddOrUpdate,
      UpdateHistory,
      UpdateAddOrUpdate,
      RetreatAddOrUpdate,
      AddOrUpdate,
      Detail,
      SetCheckByYear
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
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'projectName': this.dataForm.projectName,
            'declareTime': this.dataForm.declareTime == null ? '' : this.dataForm.declareTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': this.hasApply,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            'apply': 'project_audit_apply_status',
            'project_audit_apply_status_more': 4,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
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
      // 审批
      applyDeclareHandle (id) {
        this.awardVisible = true
        this.$nextTick(() => {
          this.$refs.award.init(id)
        })
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
      setByYearHandle (id) {
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
      // 公布立项项目
      publicDeclareHandle (id) {
        this.$message({
          message: '功能未实现',
          type: 'error',
          duration: 1500
        })
      },
      // "导出项目信息
      exportDeclareHandle (id) {
        this.exportVisible = true
        this.$nextTick(() => {
          this.$refs.ExportInfoDetail.init(id)
        })
      },
      applyDeclareIsVisible (item) {
        if (this.isAuth('innovate:project:apply:audit')) {
          if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
            if (item.projectAuditApplyStatus === 5) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 5) {
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
            if (item.projectAuditApplyStatus === 5) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 5) {
                  return true
                }
              }
            }
          }
        }
        return false
      },
      publicDeclareIsVisible (item) {
        if (this.isAuth('innovate:declare:retreat')) { // innovate:declare:public
          if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
            if (item.projectAuditApplyStatus === 6) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 5) {
                  return true
                }
              }
            }
          }
        }
        return false
      },
      exportDeclareIsVisible (item) {
        if (this.isAuth('innovate:declare:retreat')) { // innovate:declare:export
          if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
            if (item.projectAuditApplyStatus === 6) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 5) {
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
          this.$refs.detail.init(id, false)
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
      applyAwardHandle (id) {
        this.awardVisible = true
        this.$nextTick(() => {
          this.$refs.award.init(id)
        })
      },
      addOrUpdate (item) {
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
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.declareInfoEntity.declareId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '中期检查' : '批量中期检查'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/check/saveByDeclareBatchIds'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$notify({
                title: '结果提示',
                duration: '3000',
                message: '操作成功',
                type: 'success',
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$notify({
                title: '操作失败',
                duration: '3000',
                message: data.msg,
                type: 'error',
                onClose: () => {
                  this.$message.error(data.msg)
                }
              })
            }
          })
        })
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
