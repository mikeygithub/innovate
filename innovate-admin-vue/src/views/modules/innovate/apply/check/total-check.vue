<!--二级学院大创汇总表-->
<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
          v-model="dataForm.checkTime"
          align="right"
          type="year"
          placeholder="请选择年度"
          @change="getDataList">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <template>
        <el-select v-model="instituteId" filterable placeholder="请选择学院"
        @change="getDataList">
        <el-option
        v-for="item in instituteList"
        :key="item.instituteId"
        :label="item.instituteName"
        :value="item.instituteId">
        </el-option>
        </el-select>
        </template>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.projectName" placeholder="项目名" clearable></el-input>
      </el-form-item>
      <el-form-item>

        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-card>

      <el-radio-group v-model="hasApply" @change="getDataList">
        <el-radio label="1">未提交</el-radio>
        <el-radio label="2">已提交</el-radio>
        <el-radio label="5">全部</el-radio>
      </el-radio-group>
    </el-card>
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
        prop="innovateCheckInfoEntity.checkId"
        header-align="center"
        align="center"
        width="120"
        label="展开流程进度">
        <template slot-scope="props">
          <el-row>
            <el-card style=": 0.1rem">
              <el-col :span="3">
                <el-tag>中期检查审批进度</el-tag>
              </el-col>
              <el-col :span="21">
                <el-steps
                  :active="props.row.innovateCheckInfoEntity.projectCheckApplyStatus"
                  finish-status="success">
                  <el-step title="项目负责人提交"></el-step>
                  <!--<el-step title="指导老师审批"></el-step>-->
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
          <el-tag v-if="scope.row.innovateCheckInfoEntity.checkTime != null" size="small">{{scope.row.innovateCheckInfoEntity.checkTime}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="220"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('innovate:project:list')" type="text" size="small" @click="detailHandle(scope.row.innovateCheckInfoEntity.declareId)">详情</el-button>
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
    <detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></detail>
  </div>
</template>

<script>
  import Detail from './operation/info-detail'

  export default {
    data () {
      return {
        instituteId: '',
        userTeacherInfoEntities: [],
        instituteList: this.$store.state.user.institute,
        hasApply: '1',
        dataForm: {
          projectName: '',
          baseId: '',
          checkTime: new Date(),
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
        isHistoyVisible: false
      }
    },
    components: {
      Detail
    },
    activated () {
      this.getDataList()
      this.getAllInstituteList()
    },
    methods: {
      // 查询二级学院
      getAllInstituteList () {
        this.$http({
          url: this.$http.adornUrl('/innovate/sys/institute/list'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.instituteList = data.page.list
          } else {
            this.instituteList = []
          }
        })
      },
      // 获取数据列表
      getDataList () {
        // if (this.instituteId === '' || this.instituteId === null) {
        //   this.$message({
        //     type: 'error',
        //     message: '请先选择学院!'
        //   })
        //   return
        // }
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
          url: this.$http.adornUrl('/innovate/check/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'hasApply': this.hasApply,
            'projectCheckApplyStatus': 0,
            'checkNoPass': 0,
            'instituteId': this.instituteId,
            'checkTime': this.dataForm.checkTime.getFullYear(),
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
      // selectInstitute () {
      //   console.log('selectInstitute' + this.instituteId)
      // },
      // 公布立项项目
      publicDeclareHandle (id) {
      },
      // "导出项目信息
      exportDeclareHandle (id) {
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
        if (this.isAuth('innovate:declare:retreat')) {
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
        if (this.isAuth('innovate:declare:retreat')) {
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
          this.$refs.detail.init(id)
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
        this.awardVisible = true
        this.$nextTick(() => {
          this.$refs.award.init(id)
        })
      },
      // 审批
      applyDeclareRef (id) {
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/apply/apply'),
          method: 'post',
          params: this.$http.adornParams({
            'declareId': id,
            'apply': 'project_audit_apply_status',
            'roleId': 5
          }, false)
        }).then(({data}) => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
          this.getDataList()
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
