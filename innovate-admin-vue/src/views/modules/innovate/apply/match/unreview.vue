<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
          @change="getDataList"
          v-model="dataForm.matchTime"
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
      :default-sort="{prop: 'matchInfoEntity.matchId', order: 'ascending'}"
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
      <el-table-column
        sortable
        prop="matchInfoEntity.matchName"
        header-align="center"
        align="center"
        label="赛事项目名称">
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
        prop="matchInfoEntity.matchTime"
        header-align="center"
        align="center"
        label="申报时间">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.matchInfoEntity.matchTime != null" size="small">{{scope.row.matchInfoEntity.matchTime}}</el-tag>
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
          matchTime: new Date(),
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
          url: this.$http.adornUrl('/innovate/match/review/unReview'),
          method: 'get',
          params: this.$http.adornParams({
            'projectName': this.dataForm.projectName,
            'matchTime': this.dataForm.matchTime == null ? '' : this.dataForm.matchTime.getFullYear(),
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
