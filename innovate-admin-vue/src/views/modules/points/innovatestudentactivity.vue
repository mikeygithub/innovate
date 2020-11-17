<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('points:innovatestudentactivity:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('points:innovatestudentactivity:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        header-align="center"
        align="center"
        label="序号"
        width="50">
        <template slot-scope="props">
          <p v-text="props.$index+1"></p>
        </template>
      </el-table-column>
      <el-table-column
        prop="activityName"
        header-align="center"
        align="center"
        label="活动名称">
      </el-table-column>
      <el-table-column
        prop="activitySite"
        header-align="center"
        align="center"
        label="活动地点">
      </el-table-column>
      <el-table-column
        prop="activityPoint"
        header-align="center"
        align="center"
        label="活动积分">
      </el-table-column>
      <el-table-column
        prop="activityTime"
        header-align="center"
        align="center"
        label="活动时间">
      </el-table-column>
      <el-table-column
        prop="activitySignQr"
        header-align="center"
        align="center"
        label="签到二维码">
        <template slot-scope="scope">
          <img
            style="width: 100px; height: 100px"
            :src="$http.adornUrl(`/points/innovatestudentactivity/image/` + scope.row.activitySignQr.substring(scope.row.activitySignQr.lastIndexOf('/')+1,scope.row.activitySignQr.length))"
            fit="fill">
        </template>
      </el-table-column>
      <el-table-column
        prop="activityPointQr"
        header-align="center"
        align="center"
        label="离场二维码">
        <template slot-scope="scope">
          <img
            style="width: 100px; height: 100px"
            :src="$http.adornUrl(`/points/innovatestudentactivity/image/` + scope.row.activityPointQr.substring(scope.row.activityPointQr.lastIndexOf('/')+1,scope.row.activityPointQr.length))"
            fit="fill">
        </template>>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="downloadQRCodeHandle(scope.row.activitySignQr,0)">下载签到二维码</el-button>
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="downloadQRCodeHandle(scope.row.activityPointQr,1)">下载离场二维码</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
  import AddOrUpdate from './innovatestudentactivity-add-or-update'
  export default {
    data () {
      return {
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        dataForm: {
          key: ''
        },
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
          url: this.$http.adornUrl('/points/innovatestudentactivity/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
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
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/points/innovatestudentactivity/delete'),
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
      // 下载二维码
      downloadQRCodeHandle (filePath, type) {
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/points/innovatestudentactivity/download`),
          method: 'post',
          params: this.$httpFile.adornParams({
            'filePath': filePath
          })
        }).then(response => {
          if (!response) {
            return
          }
          let url = window.URL.createObjectURL(new Blob([response.data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', type === 0 ? '签到.png' : '离场.png')
          document.body.appendChild(link)
          link.click()
          link.remove()
        }).catch(err => {
          console.log(err)
        })
      }
    }
  }
</script>
