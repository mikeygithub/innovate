<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="项目名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <!--<el-button v-if="isAuth('innovate:project:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
        <!--<el-button v-if="isAuth('innovate:project:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
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
        sortable
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
                <el-tag>中心申请审批进度</el-tag>
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
                  <!--<el-step title="超级管理员审批"></el-step>-->
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
          <el-button v-if="applyUpadate(scope.row.projectInfoEntity)" type="text" size="small" @click="applyUpadateHandle(scope.row.projectInfoEntity.projectId)">审批操作</el-button>
          <!--<el-button v-if="isAuth('innovate:project:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.projectInfoEntity.projectId)">修改</el-button>-->
          <!--<el-button v-if="isAuth('innovate:project:delete')" type="text" size="small" @click="deleteHandle(scope.row.projectInfoEntity.projectId)">删除</el-button>-->
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
    <examine-history v-if="applyUpdateVisible" ref="applyUpdate" @refreshDataList="getDataList"></examine-history>
    <detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></detail>
  </div>
</template>

<script>
  import AddOrUpdate from './project/info-add-or-update'
  import Detail from './operation/info-detail'
  import ExamineHistory from './operation/examine-history'

  export default {
    data () {
      return {
        projectList: [],
        sysTeacherEntities: [],
        dataForm: {
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
        applyUpdateVisible: false,
        detailVisible: false
      }
    },
    components: {
      ExamineHistory,
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
        this.$http({
          url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.sysTeacherEntities = data.sysTeacherEntities
            this.$store.state.sysTeacherEntities = data.sysTeacherEntities
            this.$store.state.userTeacherInfoEntities = data.userTeacherInfoEntities
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/project/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'username': this.dataForm.userName,
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            // 'userId': this.$store.state.user.id,
            'applyUpdate': 1,
            // 'isStudent': true
            // 'apply': 'project_audit_apply_status',
            // 'applyStatus': 3
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
      // 详情
      detailHandle (id) {
        this.detailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      applyUpadate (item) {
        if (item.applyUpdate === 1) {
          return true
        }
      },
      applyUpadateHandle (id) {
        console.info(id)
        this.applyUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.applyUpdate.init(id)
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
