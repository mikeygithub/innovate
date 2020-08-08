<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem">
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="基地名称" prop="baseName" >
            <el-input v-model="dataForm.baseName" placeholder="请输入基地名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="场所面积(平方米)" prop="baseName">
            <el-input v-model="dataForm.baseArea" placeholder="请输入场所面积"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="基地类型" prop="baseStationType">
            <el-select v-model="dataForm.baseStationType" placeholder="请选择">
              <el-option
                v-for="item in baseType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="管理服务人数" prop="baseMessage">
            <el-input v-model="dataForm.baseServiceNum" placeholder="请输入管理服务人数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="孵化出园率" prop="baseName">
            <el-input v-model="dataForm.baseOutRate" placeholder="请输入孵化出园率"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="公共服务面积(平方米)" prop="baseName">
           <el-input v-model="dataForm.baseServiceArea" placeholder="请输入基地公共服务面积"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="直接就业人数" prop="baseName">
            <el-input  v-model="dataForm.baseDirectNum" placeholder="请输入直接就业人数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="孵化出园数量" prop="baseName">
            <el-input v-model="dataForm.baseOutNum" placeholder="请输入孵化出园数量"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="当年实体数量" prop="baseName">
            <el-input v-model="dataForm.baseEntityNum" placeholder="请输入当年实体数量"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="科技人员创业数量" prop="baseName">
            <el-input v-model="dataForm.baseScienceNum" placeholder="请输入科技人员创业数量"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="连续创业数量" prop="baseName">
            <el-input v-model="dataForm.baseSeriesNum" placeholder="请输入连续创业数量"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="管理服务人数" prop="baseName">
            <el-input v-model="dataForm.baseServiceNum" placeholder="请输入管理服务人数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <!--<el-form-item label="实体简况" prop="baseName" label-width="80px" >-->
            <!--<el-input v-model="dataForm.baseMessage" placeholder="请输入实体简况"></el-input>-->
          <!--</el-form-item>-->
          <label>实体简况</label>
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            v-model="textarea">
          </el-input>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        baseList: [],
        baseType: [{
          value: 1,
          label: '田园型'
        }, {
          value: 2,
          label: '楼宇型'
        }],
        dataForm: {
          id: 0,
          baseName: '',
          baseStationType: '',
          baseMessage: '',
          baseArea: '',
          stationNum: '',
          baseServiceNum: '',
          baseInvestMoney: '',
          baseNowBaseNum: '',
          baseAreaUseRate: '',
          baseAllInMoney: '',
          baseOperateCost: '',
          baseTeamArea: '',
          baseServiceArea: '',
          baseAllTeacherNum: '',
          baseFullTeacherNum: '',
          basePartTeacherNum: '',
          baseCompanyNum: '',
          baseItemNum: '',
          baseAllCompanyNum: '',
          baseAllItemNum: '',
          baseAllInvest: '',
          baseAllSales: '',
          baseAllSubsidy: '',
          baseFirstSubsidy: '',
          baseAllEmpHard: '',
          baseInsurance: '',
          baseAllLoan: '',
          baseYearProbate: '',
          baseYearGrant: '',
          baseWorkNum: '',
          baseOutRate: '',
          baseDirectNum: '',
          baseOutNum: '',
          baseEntityNum: '',
          baseInitCompany: '',
          baseAllInitCompany: '',
          baseAllInit: '',
          baseProInvestNum: '',
          baseProAllInvestNum: '',
          baseProInvestAllMoney: '',
          baseProInvestSocietyMoney: '',
          baseProInvestBaseMoney: '',
          baseProAllInvestAllMoney: '',
          baseProAllInvestSocietyMoney: '',
          baseProAllInvestBaseMoney: '',
          baseStuNum: '',
          baseScienceNum: '',
          baseSeriesNum: '',
          baseIprNum: '',
          baseNewNum: ''
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataForm.id = id || 0
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/info/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.baseName = data.baseInfo.baseName
                this.dataForm.baseMessage = data.baseInfo.baseMessage
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/info/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'baseId': this.dataForm.id || undefined,
                'baseName': this.dataForm.baseName,
                'baseMessage': this.dataForm.baseMessage
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
