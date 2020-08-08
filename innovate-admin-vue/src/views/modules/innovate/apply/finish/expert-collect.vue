<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
          @change="getDataList"
          :editable="false"
          v-model="dataForm.expertTime"
          align="right"
          type="year"
          placeholder="请选择年度">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('finish:expert:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('finish:expert:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        label="序号">
        <template slot-scope="props">
          <p v-text="props.$index+1"></p>
        </template>
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="expertCollectId"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="">-->
      <!--</el-table-column>-->
      <el-table-column
        prop="expertCollectTime"
        header-align="center"
        align="center"
        label="年度">
        <template slot-scope="scope">
          <el-tag type="small" v-text="scope.row.expertCollectTime"></el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="expertCollectInstituteId"
        header-align="center"
        align="center"
        label="学院">
        <template slot-scope="scope">
          <el-tag type="small" :key="index" v-for="temp in instituteList" v-if="scope.row.expertCollectInstituteId.toString() === temp.instituteId.toString()" v-text="temp.instituteName"></el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="expertCollectInstituteEmail"
        header-align="center"
        align="center"
        label="邮箱">
      </el-table-column>
      <el-table-column
        prop="expertCollectWriter"
        header-align="center"
        align="center"
        label="填表人">
      </el-table-column>
      <el-table-column
        prop="expertCollectWriterPhone"
        header-align="center"
        align="center"
        label="填表人联系电话">
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="isDel"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="是否删除">-->
      <!--</el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-if="updateVisible(scope.row)" @click="addOrUpdateHandle(scope.row.expertCollectId)">修改</el-button>
          <el-button type="text" size="small" @click="detailHandle(scope.row.expertCollectId)">详情</el-button>
          <el-button type="text" size="small" v-if="submitVisible(scope.row)" @click="submitHandle(scope.row.expertCollectId)">提交</el-button>
          <el-button type="text" size="small" v-if="deleteVisible(scope.row)" @click="deleteHandle(scope.row.expertCollectId)">删除</el-button>
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
    <info-detail v-if="infoDetailVisible" ref="detail" @refreshDataList="getDataList"></info-detail>
  </div>
</template>

<script>
  import AddOrUpdate from './project/expert-collect-add-or-update'
  import InfoDetail from './operation/expert-info-detail'
  export default {
    data () {
      return {
        dataForm: {
          key: '',
          expertTime: new Date()
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        instituteList: this.$store.state.user.institute, // 二级学院
        addOrUpdateVisible: false,
        infoDetailVisible: false
      }
    },
    components: {
      AddOrUpdate,
      InfoDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/innovate/finish/expert/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'instituteId': this.$store.state.user.instituteId, // 二级学院
            'key': this.dataForm.key,
            'year': this.dataForm.expertTime.getFullYear()
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
      detailHandle (id) {
        this.infoDetailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      // 删除
      deleteVisible (item) {
        if (item.expertApply === 0) {
          return true
        }
        return false
      },
      // 更新
      updateVisible (item) {
        if (item.expertApply === 0) {
          return true
        }
        return false
      },
      // 提交
      submitVisible (item) {
        if (item.expertApply === 0) {
          return true
        }
        return false
      },
      // 审批
      submitHandle (id) {
        this.$confirm('确认提交，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/finish/expert/apply'),
            method: 'post',
            params: this.$http.adornParams({
              'expertCollectId': id,
              'status': 1,
              'instituteId': this.$store.state.user.instituteId
            }, false)
          }).then(({data}) => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.getDataList()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          })
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.expertCollectId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/finish/expert/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
        })
      }
    }
  }
</script>
