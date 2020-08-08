<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item v-show="1 == 2">
        <table border="1" cellspacing="0" width="100%" class="table" id="out-table">
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem">账号填工号/学号，密码默认：123456，多用户类型用“,”分隔开。</td>
          </tr>
          <tr class="contents" align="center">
            <th colspan="10">
              备注：项目负责人的用户类型为2，指导老师为3，指导老师为11，指导老师为12。
            </th>
          </tr>
          <tr align='center'>
            <th>账号</th>
            <th>姓名</th>
            <th>手机号</th>
            <th>邮箱</th>
            <th>用户类型</th>
          </tr>
        </table>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <!--<el-button v-if="isAuth('sys:eruser:save')" type="primary" @click="userBatchAddHandle()">新增</el-button>-->
        <el-button plain type="info" @click="outExcelTemplate()">导出模板</el-button>
        <el-button plain v-if="isAuth('sys:eruser:batchsave')" type="success" @click="userBatchAddHandle()">数据导入</el-button>
      </el-form-item>
    </el-form>
    <el-card>
      <el-radio-group v-model="roleId" @change="getDataList">
        <el-radio v-for="role in roleList" :key="role.roleId"  :label="role.roleId">{{role.roleName}}</el-radio>
      </el-radio-group>
   </el-card>
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
        sortable
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
        prop="username"
        header-align="center"
        align="center"
        label="帐号">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="email"
        header-align="center"
        align="center"
        label="邮箱">
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">禁用</el-tag>
          <el-tag v-else size="small">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <!--<el-table-column-->
        <!--fixed="right"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="150"-->
        <!--label="操作">-->
        <!--<template slot-scope="scope">-->
          <!--<el-button v-if="isAuth('sys:eruser:update')" type="text" size="small" @click="userBatchAddHandle(scope.row.userId)">修改</el-button>-->
        <!--</template>-->
      <!--</el-table-column>-->
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
    <!-- 弹窗, 录入数据 -->
    <user-batch-add v-if="userBatchAddVisible" ref="userBatchAdd" @refreshDataList="getDataList"></user-batch-add>
  </div>
</template>

<script>
  import UserBatchAdd from './er-user-batch-add'
  import FileSaver from 'file-saver'
  import XLSX from 'xlsx'
  export default {
    data () {
      return {
        url: '',
        dataForm: {
          userName: ''
        },
        roleId: 2,
        roleList: [
          {roleId: 2, roleName: '项目负责人'},
          {roleId: 3, roleName: '指导老师'},
          {roleId: 11, roleName: '学生'},
          {roleId: 12, roleName: '老师'}
        ],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        downloadLoading: false,
        dataListSelections: [],
        userBatchAddVisible: false
      }
    },
    components: {
      UserBatchAdd
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sys/user/erlist'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'roleId': this.roleId,
            'erInstituteId': this.$store.state.user.instituteId,
            'name': this.dataForm.userName
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
      userBatchAddHandle (id) {
        this.userBatchAddVisible = true
        this.$nextTick(() => {
          this.$refs.userBatchAdd.init(id)
        })
      },
      // 导出模板
      outExcelTemplate () {
        var msg = '操作成功'
        var type = 'success'
        var wb = XLSX.utils.table_to_book(document.querySelector('#out-table'))
        var wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'})
        try {
          FileSaver.saveAs(new Blob([wbout], {type: 'application/octet-stream'}), '用户注册模板' + '.xlsx')
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
      },
      // 上传成功
      successHandle1 (response, file, fileList) {
        if (response && response.code === 0) {
          this.attachLists.push(response.declareAttachEntity)
        } else {
          this.$message.error(response.msg)
        }
      }
    }
  }
</script>
