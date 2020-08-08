<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="教师" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('innovate:teacher:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('innovate:teacher:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="teacherId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="teacherName"
        header-align="center"
        align="center"
        label="教师姓名">
      </el-table-column>
      <el-table-column
        prop="teacherSex"
        header-align="center"
        align="center"
        label="教师性别">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.teacherSex === 1" size="small" type="">男</el-tag>
          <el-tag v-if="scope.row.teacherSex === 2" size="small" type="">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="teacherUnit"
        header-align="center"
        align="center"
        label="教师单位">
      </el-table-column>
      <el-table-column
        prop="teacherWorkStatus"
        header-align="center"
        align="center"
        label="全职/兼职">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.teacherWorkStatus === 1" size="small" type="">全职</el-tag>
          <el-tag v-if="scope.row.teacherWorkStatus === 2" size="small" type="">兼职</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="teacherPhone"
        header-align="center"
        align="center"
        label="手机号码">
      </el-table-column>
      <el-table-column
        prop="teacherJob"
        header-align="center"
        align="center"
        label="教师职务">
      </el-table-column>
      <el-table-column
        prop="teacherInstinct"
        header-align="center"
        align="center"
        label="职称">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.teacherInstinct === 1" size="small" type="">正高级</el-tag>
          <el-tag v-if="scope.row.teacherInstinct === 2" size="small" type="">副高级</el-tag>
          <el-tag v-if="scope.row.teacherInstinct === 3" size="small" type="">中级</el-tag>
          <el-tag v-if="scope.row.teacherInstinct === 4" size="small" type="">初级</el-tag>
          <el-tag v-if="scope.row.teacherInstinct === 5" size="small" type="">无职称</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="baseId"
        header-align="center"
        align="center"
        label="所属基地">
        <template slot-scope="scope">
          <el-tag v-for="base in baseList" v-if="scope.row.baseId === base.baseId" size="small" v-text="base.baseName" :key="base.baseId"></el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('innovate:teacher:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.teacherId)">修改</el-button>
          <el-button v-if="isAuth('innovate:teacher:delete')" type="text" size="small" @click="deleteHandle(scope.row.teacherId)">删除</el-button>
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
  </div>
</template>

<script>
  import AddOrUpdate from './teacher-add-or-update'
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
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/innovate/base/info/list`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.baseList = data.page.list
          }
        })
        this.$http({
          url: this.$http.adornUrl('/innovate/base/teacher/list'),
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
      // 删除
      deleteHandle (id) {
        var teacherIds = id ? [id] : this.dataListSelections.map(item => {
          return item.teacherId
        })
        this.$confirm(`确定对[id=${teacherIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/base/teacher/delete'),
            method: 'post',
            data: this.$http.adornData(teacherIds, false)
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
