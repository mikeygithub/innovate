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
      </el-form-item>
    </el-form>
    <el-card>
      <el-radio-group v-model="hasReview" @change="getDataList">
        <el-radio label="1">未打分</el-radio>
        <el-radio label="2">等待他人打分</el-radio>
        <el-radio label="3">已打分</el-radio>
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
                  <el-step title="管理员分配评委组"></el-step>
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
          <el-button v-if="isAuth('innovate:declare:list')" type="text" size="small" @click="detailHandle(scope.row.declareInfoEntity.declareId)">详情</el-button>
          <el-button v-if="applyIsVisible(scope.row.declareInfoEntity)" type="text" size="small" @click="applyHandle(scope.row.declareInfoEntity.declareId)">评分</el-button>
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
    <score-add-or-update v-if="scoreVisible" ref="score" @refreshDataList="getDataList"></score-add-or-update>
  </div>
</template>

<script>
  import Detail from './operation/info-detail'
  import ScoreAddOrUpdate from './operation/score-add-or-update'

  export default {
    data () {
      return {
        declareList: [],
        sysTeacherEntities: [],
        hasReview: '1',
        dataForm: {
          projectName: '',
          declareTime: new Date(),
          baseId: ''
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
        scoreVisible: false
      }
    },
    components: {
      ScoreAddOrUpdate,
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
        this.scoreVisible = false
        this.$http({
          url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
          method: 'get',
          params: this.$http.adornParams({
            'like': ''
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.sysTeacherEntities = data.sysTeacherEntities
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/declare/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'projectName': this.dataForm.projectName,
            'declareTime': this.dataForm.declareTime == null ? '' : this.dataForm.declareTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasReview': this.hasReview,
            'noPass': 'audit_no_pass',
            'noPassStatus': 0,
            // 'isTeacher': true
            // 'isStudent': true
            'apply': 'project_audit_apply_status',
            'applyStatus': 4,
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
      applyIsVisible (item) {
        if (this.isAuth('innovate:project:apply:audit')) {
          if (item.projectAuditApplyStatus !== null || item.projectAuditApplyStatus !== '') {
            if (item.projectAuditApplyStatus === 4) {
              let roleIdList = this.$store.state.user.roleIdList
              for (let roleIndex = 0; roleIndex < roleIdList.length; roleIndex++) {
                if (roleIdList[roleIndex] === 6) {
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
            if (item.projectAuditApplyStatus === 2) {
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
      // 审批
      applyHandle (id) {
        this.scoreVisible = true
        this.$nextTick(() => {
          this.$refs.score.init(id)
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
