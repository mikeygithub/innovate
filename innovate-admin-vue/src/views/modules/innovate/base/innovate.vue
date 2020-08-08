<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="基地名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="matchId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="baseName"
        header-align="center"
        align="center"
        label="项目名称">
      </el-table-column>
      <el-table-column
        prop="baseAllInMoney"
        header-align="center"
        align="center"
        label="本月销售营业额">
      </el-table-column>
      <el-table-column
        prop="baseMessage"
        header-align="center"
        align="center"
        label="本月利润总额">
      </el-table-column>
      <el-table-column
        prop="baseMessage"
        header-align="center"
        align="center"
        label="本月上缴税金总额">
      </el-table-column>
      <el-table-column
        prop="baseMessage"
        header-align="center"
        align="center"
        label="累计投入资金数额">
      </el-table-column>
      <el-table-column
        prop="baseMessage"
        header-align="center"
        align="center"
        label="累计营业额">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:user:update')" type="text" size="small" @click="detailHandle(scope.row.baseId)">详情</el-button>
          <el-button v-if="isAuth('sys:user:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.baseId)">修改</el-button>
          <el-button v-if="isAuth('sys:user:delete')" type="text" size="small" @click="deleteHandle(scope.row.baseId)">删除</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <Detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></Detail>
  </div>
</template>

<script>
  import AddOrUpdate from './innovate-add-or-update'
  import Detail from './innovate-detail'

  export default {
    data () {
      return {
        dataForm: {
          userName: ''
        },
        baseList: [],
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
        this.$http({
          url: this.$http.adornUrl('/innovate/base/info/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'username': this.dataForm.userName
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
      detailHandle (id) {
        this.detailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var baseIds = id ? [id] : this.dataListSelections.map(item => {
          return item.baseId
        })
        this.$confirm(`确定对[id=${baseIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/base/info/delete'),
            method: 'post',
            data: this.$http.adornData(baseIds, false)
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
