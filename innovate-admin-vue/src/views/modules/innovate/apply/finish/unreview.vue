<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
          @change="getDataList"
          v-model="dataForm.finishTime"
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
      :default-sort="{prop: 'finishInfoEntity.finishId', order: 'ascending'}"
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
        prop="finishInfoEntity.finishName"
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
        prop="finishInfoEntity.finishTime"
        header-align="center"
        align="center"
        label="申报时间">
        <template slot-scope="scope">
          <!--<el-tag v-if="scope.row.declareInfoEntity.declareTime === null" size="small">未申报</el-tag>-->
          <el-tag v-if="scope.row.finishInfoEntity.finishTime != null" size="small">{{scope.row.finishInfoEntity.finishTime}}</el-tag>
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
  export default {
    data () {
      return {
        hasApply: '1',
        dataForm: {
          projectName: '',
          baseId: '',
          finishTime: new Date(),
          idDel: 0
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        isUpdateVisible: false
      }
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
          url: this.$http.adornUrl('/innovate/finish/review/unReview'),
          method: 'get',
          params: this.$http.adornParams({
            'projectName': this.dataForm.projectName,
            'finishTime': this.dataForm.finishTime == null ? '' : this.dataForm.finishTime.getFullYear(),
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
