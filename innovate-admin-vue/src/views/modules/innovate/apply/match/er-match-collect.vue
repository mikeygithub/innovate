<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-select v-model="dataForm.instituteId" placeholder="请选择二级学院">
          <el-option
            v-for="inst in instituteList"
            :key="inst.instituteName"
            :label="inst.instituteName"
            :value="inst.instituteId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.matchTime"
          align="right"
          type="year"
          placeholder="请选择年度">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="allErMatchCollectDetail(dataForm.instituteId,dataForm.matchTime)">导出</el-button>
        <!--<el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        type="index"
        header-align="center"
        align="center"
        width="80"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="matchInfoEntity.matchName"
        header-align="center"
        align="center"
        width="200"
        label="项目名称">
      </el-table-column>
      <el-table-column
        prop="matchInfoEntity.matchTeamName"
        header-align="center"
        align="center"
        width="100"
        label="团队名称">
      </el-table-column>
      <el-table-column
        prop="userPersonInfoEntities"
        header-align="center"
        align="center"
        width="70"
        label="负责人">
        <template slot-scope="scope">
          <span v-for="user in scope.row.userPersonInfoEntities" v-text="user.sysUserEntity.name"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="matchInfoEntity"
        header-align="center"
        align="center"
        label="申报类型">
        <template slot-scope="scope">
          <span v-for="type in matchTypeList" v-if="type.value === scope.row.matchInfoEntity.matchType" v-text="type.label"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="matchTypeList"
        header-align="center"
        align="center"
        label="申报组别">
        <template slot-scope="scope">
          <span v-for="type in matchTypeList" v-if="type.value === scope.row.matchInfoEntity.matchGroupType" v-text="type.label"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="matchStaffInfoEntities.length"
        header-align="center"
        align="center"
        width="60"
        label="团队人数">
      </el-table-column>
      <el-table-column
        prop="matchStaffInfoEntities"
        header-align="center"
        align="center"
        label="团队成员姓名">
        <template slot-scope="scope">
          <span v-for="staff in scope.row.matchStaffInfoEntities" v-text="staff.staffName+' '"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="userTeacherInfoEntities"
        header-align="center"
        align="center"
        label="指导老师">
        <template slot-scope="scope">
          <span v-for="teacher in scope.row.userTeacherInfoEntities" v-text="teacher.sysUserEntity.name+' '"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="matchInfoEntity.matchScoreAvg"
        header-align="center"
        align="center"
        width="80"
        label="平均得分">
        <template slot-scope="scope">
          <el-tag type="small" v-if="scope.row.matchInfoEntity.matchScoreAvg === null || scope.row.matchInfoEntity.matchScoreAvg === ''">未评分</el-tag>
          <el-tag type="small" v-if="scope.row.matchInfoEntity.matchScoreAvg != null" v-text="scope.row.matchInfoEntity.matchScoreAvg"></el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="80"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openMatchInfoDetail(scope.row.matchInfoEntity.matchId)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--<el-table-->
      <!--:data="dataList"-->
      <!--border-->
      <!--v-loading="dataListLoading"-->
      <!--@selection-change="selectionChangeHandle"-->
      <!--style="width: 100%;">-->
      <!--<el-table-column-->
        <!--type="selection"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="50">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--prop="instituteId"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="80"-->
        <!--label="ID">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--prop="instituteName"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="二级学院">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--fixed="right"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="150"-->
        <!--label="操作">-->
        <!--<template slot-scope="scope">-->
          <!--&lt;!&ndash;<el-button v-if="isAuth('sys:user:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.instituteId)">修改</el-button>&ndash;&gt;-->
          <!--&lt;!&ndash;<el-button v-if="isAuth('sys:user:delete')" type="text" size="small" @click="deleteHandle(scope.row.instituteId)">删除</el-button>&ndash;&gt;-->
          <!--<el-button  type="text" size="small" @click="collectDetail(scope.row.instituteId)">查看</el-button>-->
        <!--</template>-->
      <!--</el-table-column>-->
    <!--</el-table>-->
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <er-match-collect-detail v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></er-match-collect-detail>
    <all-er-match-collect-detail v-if="allErMatchCollectDetailVisible" ref="allErMatchCollectDetail" @refreshDataList="getDataList"></all-er-match-collect-detail>
    <MatchInfoDetail v-if="matchInfoDetailVisible" ref="detail" @refreshDataList="getDataList"></MatchInfoDetail>
  </div>
</template>

<script>
  import ErMatchCollectDetail from './operation/er-match-collect-detail'
  import AllErMatchCollectDetail from './operation/all-er-match-collect-detail'
  import MatchInfoDetail from './operation/info-detail'

  export default {
    data () {
      return {
        dataForm: {
          userName: '',
          instituteId: '',
          matchTime: new Date()
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        matchInfoDetailVisible: false,
        allErMatchCollectDetailVisible: false,
        instituteList: this.$store.state.user.institute,
        matchTypeList: [
          {value: 1, label: '论文、调研报告'}, {value: 2, label: '视频'},
          {value: 3, label: '设计作品'}, {value: 4, label: '创业计划书'},
          {value: 5, label: '创新创业实践项目'}
        ],
        matchGroupTypeList: [
          {value: 1, label: '创意组'}, {value: 2, label: '初创组'},
          {value: 3, label: '成长组'}, {value: 4, label: '师生共创组'},
          {value: 5, label: '"青年红色梦之旅"赛道'}
        ]
      }
    },
    components: {
      ErMatchCollectDetail,
      AllErMatchCollectDetail,
      MatchInfoDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/innovate/match/info/list`),
          method: 'get',
          params: this.$http.adornParams({
            'instituteId': this.dataForm.instituteId,
            'matchTime': this.dataForm.matchTime == null ? '' : this.dataForm.matchTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'project_audit_apply_status_more': 2,
            'noPassStatus': 0,
            'noPass': 'match_no_pass',
            'isInstitute': true,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            this.dataListLoading = false
          } else {
            this.dataList = []
            this.totalPage = 0
            this.$message.error(data.msg)
            this.dataListLoading = false
          }
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
      collectDetail (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      allErMatchCollectDetail (instituteId, matchTime) {
        this.allErMatchCollectDetailVisible = true
        this.$nextTick(() => {
          this.$refs.allErMatchCollectDetail.init(instituteId, matchTime)
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 详情
      openMatchInfoDetail (id) {
        this.matchInfoDetailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var instituteIds = id ? [id] : this.dataListSelections.map(item => {
          return item.instituteId
        })
        this.$confirm(`确定对[id=${instituteIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/sys/institute/delete'),
            method: 'post',
            data: this.$http.adornData(instituteIds, false)
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
        }).catch(() => {})
      }
    }
  }
</script>
