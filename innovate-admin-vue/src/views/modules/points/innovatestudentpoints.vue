<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button  v-if="!isAuth('points:innovatestudentpoints:save')" type="success">当前积分：<span v-text="points"></span></el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="isAuth('points:innovatestudentpoints:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="isAuth('points:innovatestudentpoints:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
      <el-form-item  v-if="isAuth('points:innovatestudentpoints:delete')">
        <el-upload
          class="upload-demo"
          :action="inputUrl"
          :on-success="successHandle"
          :on-error="errorHandle"
          multiple
          :limit="1"
          :file-list="fileList">
          <el-button type="primary">批量导入</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item  v-if="isAuth('points:innovatestudentpoints:save')">
        <el-button type="primary" @click="exportModul()">下载模板</el-button>
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
        width="40">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        width="60"
        label="序号">
        <template slot-scope="props">
          <p v-text="props.$index+1"></p>
        </template>
      </el-table-column>
      <el-table-column
        prop="stuNum"
        header-align="center"
        align="center"
        width="200"
        label="学号">
      </el-table-column>
      <el-table-column
        prop="points"
        header-align="center"
        align="center"
        width="100"
        label="添加分数">
      </el-table-column>
      <el-table-column
        prop="pointsReason"
        header-align="center"
        align="center"
        label="加分原因">
      </el-table-column>
      <div v-if="isAuth('points:innovatestudentpoints:save')">
        <el-table-column
          prop="operationTime"
          header-align="center"
          align="center"
          label="操作时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
            <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </div>
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
    <table v-show="false" border="1" id="out-table">　
      <tr>
        <td>学号</td><td>积分</td><td>加分原因</td>
      </tr>
    </table>
  </div>
</template>

<script>
  import AddOrUpdate from './innovatestudentpoints-add-or-update'
  import FileSaver from 'file-saver'
  import XLSX from 'xlsx'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        fileList: [],
        inputUrl: this.$http.adornUrl(`/points/innovatestudentpoints/input?token=${this.$cookie.get('token')}`),
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        points: 0,
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
          url: this.$http.adornUrl('/points/innovatestudentpoints/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'userId': this.isAuth('points:innovatestudentpoints:save') === true ? null : this.$store.state.user.id
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.dataList = data.page.list
            this.points = data.total
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
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/points/innovatestudentpoints/delete'),
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
      },
      successHandle (response, file, fileList) {
        //
        this.fileList = []
        this.$message.success(response.msg)
        this.getDataList()
      },
      errorHandle (err, file, fileList) {
        //
        this.fileList = []
        this.$message.error(err)
        this.getDataList()
      },
    // 导出
      exportModul () {
        var msg = '操作成功'
        var type = 'success'
        var wb = XLSX.utils.table_to_book(document.querySelector('#out-table'))
        var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'})
        try {
          FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '导入模板' + '.xlsx')
        } catch (e) {
          if (typeof console !== 'undefined') console.log(e, wbout)
          msg = '操作失败'
          type = 'error'
        }
        this.$message({
          message: msg,
          type: type,
          duration: 1500
        })
        return wbout
      }
    }
  }
</script>
