<template>
  <div class="mod-oss">
    <el-form :inline="true" :model="dataForm">
      <el-form-item>
        <el-button type="primary" @click="configHandle()">云存储配置</el-button>
        <el-button type="primary" @click="uploadHandle()">上传文件</el-button>
        <el-button type="primary" @click="downloadServerHandle()">下载服务器全部文件</el-button>
        <el-button type="danger" @click="deleteServerHandle()">删除服务器全部文件</el-button>
        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="id"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="url"
        header-align="center"
        align="center"
        label="URL地址">
      </el-table-column>
      <el-table-column
        prop="createDate"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
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
    <!-- 弹窗, 云存储配置 -->
    <config v-if="configVisible" ref="config"></config>
    <!-- 弹窗, 上传文件 -->
    <upload v-if="uploadVisible" ref="upload" @refreshDataList="getDataList"></upload>
  </div>
</template>

<script>
  import Config from './oss-config'
  import Upload from './oss-upload'
  export default {
    data () {
      return {
        dataForm: {},
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        configVisible: false,
        uploadVisible: false
      }
    },
    components: {
      Config,
      Upload
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sys/oss/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize
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
      // 云存储配置
      configHandle () {
        this.configVisible = true
        this.$nextTick(() => {
          this.$refs.config.init()
        })
      },
      // 上传文件
      uploadHandle () {
        this.uploadVisible = true
        this.$nextTick(() => {
          this.$refs.upload.init()
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
            url: this.$http.adornUrl('/sys/oss/delete'),
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
        }).catch(() => {})
      },
      // 下载服务器全部文件
      downloadServerHandle () {
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/sys/oss/download`),
          method: 'post',
          params: this.$httpFile.adornParams()
        }).then(response => {
          if (!response) {
            this.downloadLoading = false
            return
          }
          let url = window.URL.createObjectURL(new Blob([response.data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', '服务器备份文件')
          document.body.appendChild(link)
          link.click()
          this.downloadLoading = false
        }).catch(err => {
          console.log(err)
          this.downloadLoading = false
        })
      },
      // 清空服务器全部文件
      deleteServerHandle () {
        this.$confirm(`请确保已经将所有文件备份、此操作将服务器所有文件删除且不可撤销是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/oss/del'),
            method: 'post',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message.success('操作成功')
            } else {
              this.$message.error('操作失败')
            }
            this.dataListLoading = false
          })
        })
      }
    }
  }
</script>
