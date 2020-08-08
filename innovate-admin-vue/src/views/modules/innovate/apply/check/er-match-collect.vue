<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-select v-model="dataForm.instituteId" placeholder="请选择二级学院">
          <el-option
            v-for="inst in instituteList"
            :key="inst.instituteName"
            :label="inst.instituteName"
            :value="inst.instituteId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <!--年度 start-->
        <el-date-picker
          @change="getDataList"
          v-model="dataForm.declareTime"
          align="right"
          type="year"
          placeholder="请选择年度">
        </el-date-picker>
        <!--年度 end-->
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-button type="primary" @click="allErMatchCollectDetail(dataForm.instituteId,dataForm.declareTime)">导出</el-button>
        <!--<el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="40">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="60"
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="declareInfoEntity.declareName"
        header-align="center"
        align="center"
        width="200"
        label="项目名称">
      </el-table-column>
      <el-table-column
        prop="userPersonInfoEntities[0].sysUserEntity.name"
        header-align="center"
        align="center"
        width="80"
        label="负责人">
      </el-table-column>
      <el-table-column
        prop="userPersonInfoEntities[0].perStuNo"
        header-align="center"
        align="center"
        label="学号">
      </el-table-column>
      <el-table-column
        prop="declareInfoEntity.declareType"
        header-align="center"
        align="center"
        label="类型">
        <template slot-scope="scope">
          <span v-for="temp in declareTypeList" v-if="temp.value === scope.row.declareInfoEntity.declareType" v-text="temp.label"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="declareStaffInfoEntities.length"
        header-align="center"
        align="center"
        width="60"
        label="学生人数">
      </el-table-column>
      <el-table-column
        prop="declareStaffInfoEntities"
        header-align="center"
        align="center"
        label="成员信息">
        <template slot="" slot-scope="scope">
          <span v-for="stu in scope.row.declareStaffInfoEntities" v-text="stu.staffName+' '"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="declareUserTeacherInfoEntities"
        header-align="center"
        align="center"
        width="80"
        label="指导老师">
        <template slot-scope="scope">
          <span v-for="teacher in scope.row.declareUserTeacherInfoEntities" v-text="teacher.sysUserEntity.name+' '"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="declareUserTeacherInfoEntities"
        header-align="center"
        align="center"
        width="100"
        label="职称">
        <template slot-scope="scope">
          <span v-for="teacher in scope.row.declareUserTeacherInfoEntities">
              <span v-for="temp in teacherTitleList" v-if="temp.titleId === teacher.teacherTitle" v-text="temp.titleName"></span>
          </span>
        </template>
      </el-table-column>
      <el-table-column
        prop="declareInfoEntity.subjectId"
        header-align="center"
        align="center"
        label="一级学科">
        <template slot-scope="scope">
          <span v-for="subject in subjectList" v-if="subject.subjectId === scope.row.declareInfoEntity.subjectId" v-text="subject.subjectName"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="declareInfoEntity.declareScoreAvg"
        header-align="center"
        align="center"
        width="80"
        label="平均分">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="60"
        label="操作">
        <template slot-scope="scope">
          <el-button  type="text" size="small" @click="openDeclareInfoDetail(scope.row.declareInfoEntity.declareId)">查看</el-button>
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
    <er-match-collect-detail v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></er-match-collect-detail>
    <all-er-match-collect-detail v-if="allErMatchCollectDetailVisible" ref="allErMatchCollectDetail" @refreshDataList="getDataList"></all-er-match-collect-detail>
    <DeclareInfoDetail v-if="declareInfoDetailVisible" ref="detail" @refreshDataList="getDataList" ></DeclareInfoDetail>
  </div>
</template>

<script>
  import ErMatchCollectDetail from './operation/er-match-collect-detail'
  import AllErMatchCollectDetail from './operation/all-er-match-collect-detail'
  import DeclareInfoDetail from './operation/info-detail'

  export default {
    data () {
      return {
        dataForm: {
          projectName: '',
          instituteId: '',
          declareTime: new Date()
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        declareYears: [],
        dataListLoading: false,
        declareInfoDetailVisible: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        allErMatchCollectDetailVisible: false,
        instituteList: this.$store.state.user.institute,
        teacherTitleList: this.$store.state.user.title,
        subjectList: this.$store.state.user.subjectsu,
        declareTypeList: [
          {value: 1, label: '创新训练项目'}, {value: 2, label: '创业训练项目'}, {value: 3, label: '创业实践项目'}
        ]
      }
    },
    components: {
      ErMatchCollectDetail,
      AllErMatchCollectDetail,
      DeclareInfoDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl(`/innovate/declare/info/list`),
          method: 'get',
          params: this.$http.adornParams({
            'instituteId': this.dataForm.instituteId,
            'declareTime': this.dataForm.declareTime == null ? '' : this.dataForm.declareTime.getFullYear(),
            'project_audit_apply_status_more': 2,
            'noPassStatus': 0,
            'noPass': 'audit_no_pass',
            'isDel': 0,
            'currPage': this.pageIndex,
            'pageSize': this.pageSize
            // 'isEr': true
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            this.dataListLoading = false
          } else {
            this.$message.error(data.msg)
            this.dataListLoading = false
          }
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
      collectDetail (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      allErMatchCollectDetail (instituteId, declareTime) {
        this.allErMatchCollectDetailVisible = true
        this.$nextTick(() => {
          this.$refs.allErMatchCollectDetail.init(instituteId, declareTime)
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 打开详情
      openDeclareInfoDetail (id) {
        this.declareInfoDetailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
        // 删除
      deleteHandle (id) {
        var instituteIds = id ? [id] : this.dataListSelections.map(item => {
          return item.instituteId
        })
        this.$confirm(`确定对[id=${instituteIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/innovate/sys/institute/delete'),
            method: 'post',
            data: this.$http.adornData(instituteIds, false)
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
