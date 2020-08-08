<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-select v-model="dataForm.instituteId" placeholder="请选择二级学院" @change="getDataList">
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
          v-model="dataForm.finishTime"
          align="right"
          :editable="false"
          type="year"
          placeholder="请选择年度">
        </el-date-picker>
        <!--年度 end-->
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-button type="primary" @click="allFinishCollectDetail(dataForm.instituteId,dataForm.finishTime)">导出</el-button>
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
        prop="finishInfoEntity.finishName"
        header-align="center"
        align="center"
        label="申报名称">
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
        width="180"
        label="学号">
      </el-table-column>
      <el-table-column
        prop="finishInfoEntity.finishType"
        header-align="center"
        align="center"
        width="100"
        label="类型">
        <template slot-scope="scope">
          <span v-for="temp in finishTypeList" v-if="temp.value === scope.row.finishInfoEntity.finishType" v-text="temp.label"></span>
        </template>
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="finishStaffInfoEntities.length"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--width="60"-->
        <!--label="学生人数">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--prop="finishStaffInfoEntities"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="成员信息">-->
        <!--<template slot="" slot-scope="scope">-->
          <!--<span v-for="stu in scope.row.finishStaffInfoEntities" v-text="stu.staffName+' '"></span>-->
        <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column
        prop="finishUserTeacherInfoEntities"
        header-align="center"
        align="center"
        width="180"
        label="指导老师">
        <template slot-scope="scope">
          <span v-for="teacher in scope.row.finishUserTeacherInfoEntities" v-text="teacher.sysUserEntity.name+' '"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="finishUserTeacherInfoEntities"
        header-align="center"
        align="center"
        width="100"
        label="职称">
        <template slot-scope="scope">
          <span v-for="teacher in scope.row.finishUserTeacherInfoEntities">
              <span v-for="temp in teacherTitleList" v-if="temp.titleId === teacher.teacherTitle" v-text="temp.titleName"></span>
          </span>
        </template>
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="finishInfoEntity.subjectId"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="一级学科">-->
        <!--<template slot-scope="scope">-->
          <!--<span v-for="subject in subjectList" v-if="subject.subjectId === scope.row.finishInfoEntity.subjectId" v-text="subject.subjectName"></span>-->
        <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column
        prop="finishInfoEntity.finishScoreAvg"
        header-align="center"
        align="center"
        width="200"
        label="学院">
        <template slot-scope="scope">
          <!--<el-tag type="small" v-if="scope.row.finishInfoEntity.finishScoreAvg === null || scope.row.finishInfoEntity.finishScoreAvg === ''">未评分</el-tag>-->
          <!--<el-tag type="small" v-if="scope.row.finishInfoEntity.finishScoreAvg != null" v-text="scope.row.finishInfoEntity.finishScoreAvg"></el-tag>-->
          <span v-if="scope.row.userPersonInfoEntities!==null&&scope.row.userPersonInfoEntities.length!==0" :key="inst.index" v-for="inst in instituteList">
              <span v-if="scope.row.userPersonInfoEntities[0].sysUserEntity.instituteId === inst.instituteId" v-text="inst.instituteName"></span>
            </span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="60"
        label="操作">
        <template slot-scope="scope">
          <el-button  type="text" size="small" @click="openFinishInfoDetail(scope.row.finishInfoEntity.finishId)">查看</el-button>
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
    <er-finish-collect-detail v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></er-finish-collect-detail>
    <all-finish-collect-detail v-if="allFinishCollectDetailVisible" ref="allFinishCollectDetail" @refreshDataList="getDataList"></all-finish-collect-detail>
    <FinishInfoDetail v-if="finishInfoDetailVisible" ref="detail" @refreshDataList="getDataList" ></FinishInfoDetail>
  </div>
</template>

<script>
  import ErFinishCollectDetail from './operation/er-finish-collect-detail'
  import AllFinishCollectDetail from './operation/all-finish-collect-detail'
  import FinishInfoDetail from './operation/info-detail'
  export default {
    data () {
      return {
        dataForm: {
          userName: '',
          instituteId: '',
          finishTime: new Date()
        },
        instituteId: '',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        finishInfoDetailVisible: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        allFinishCollectDetailVisible: false,
        instituteList: this.$store.state.user.institute,
        teacherTitleList: this.$store.state.user.title,
        finishTypeList: [
          {value: 1, label: '创新训练项目'}, {value: 2, label: '创业训练项目'}, {value: 3, label: '创业实践项目'}
        ]
      }
    },
    components: {
      ErFinishCollectDetail,
      AllFinishCollectDetail,
      FinishInfoDetail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.$http({
          url: this.$http.adornUrl(`/innovate/finish/info/list`),
          method: 'get',
          params: this.$http.adornParams({
            'instituteId': this.dataForm.instituteId,
            'finishTime': this.dataForm.finishTime == null ? '' : this.dataForm.finishTime.getFullYear(),
            'currPage': this.pageIndex,
            'pageSize': this.pageSize,
            'userId': this.$store.state.user.id,
            'hasApply': 2,
            'noPass': 'finish_no_pass',
            'noPassStatus': 0,
            'apply': 'project_finish_apply_status',
            'applyStatus': 2,
            'isDel': 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            this.dataListLoading = false
            console.log(this.dataList)
          } else {
            this.$message.error(data.msg)
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
      collectDetail (id, time) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, time)
        })
      },
      allFinishCollectDetail (instituteId, finishTime) {
        this.allFinishCollectDetailVisible = true
        this.$nextTick(() => {
          this.$refs.allFinishCollectDetail.init(instituteId, finishTime)
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
      openFinishInfoDetail (id) {
        this.finishInfoDetailVisible = true
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
