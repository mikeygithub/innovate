<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="项目" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('innovate:report:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('innovate:report:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
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
        prop="reportInvestCapital"
        header-align="center"
        align="center"
        label="投入资金">
      </el-table-column>
      <el-table-column
        prop="reportProfits"
        header-align="center"
        align="center"
        label="利润总额">
      </el-table-column>
      <el-table-column
        prop="reportSales"
        header-align="center"
        align="center"
        label="销售额">
      </el-table-column>
      <el-table-column
        prop="reportTax"
        header-align="center"
        align="center"
        label="税金总额">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('innovate:report:info')" type="text" size="small" @click="detailHandle(scope.row.reportId)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
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
    <statistics-detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></statistics-detail>
    <statistics-add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></statistics-add-or-update>
  </div>
</template>

<script>
  import StatisticsDetail from './statistics-detail'
  import StatisticsAddOrUpdate from './statistics-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          reportId: '',
          projectId: '',
          reportInvestCapital: '',
          reportProfits: '',
          reportSales: '',
          reportTax: '',
          reportTime: '',
          isDel: 0
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        detailVisible: false
      }
    },
    components: {
      StatisticsAddOrUpdate,
      StatisticsDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.addOrUpdateVisible = false
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/innovate/project/report/list'),
          method: 'get',
          params: this.$http.adornParams({
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            // 'isTeacher': true
            // 'isStudent': true,
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
      // 详情
      detailHandle (id) {
        this.detailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var reportIds = id ? [id] : this.dataListSelections.map(item => {
          return item.reportId
        })
        this.$confirm(`确定对[id=${reportIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/project/report/delete'),
            method: 'post',
            data: this.$http.adornData(reportIds, false)
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
