<template>
  <el-dialog
    :title="'详情'"
    width="68%"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row>
      <table border="1" cellspacing="0" width="100%">
        <tr align='center'>
        <th>基地名称</th>
        <td width="90px"><el-card v-text="baseInfo.baseName" align="center"></el-card></td>
        <th>基地类型</th>
        <td width="90px"><el-card v-text="baseInfo.baseStationType" align="center"></el-card></td>
        <th>场所面积(平方米)</th>
        <td width="90px"><el-card v-text="baseInfo.baseArea" align="center"></el-card></td>

      </tr>
        <tr align='center'>
          <th>工位数量</th>
          <td><el-card v-text="baseInfo.stationNum" align="center"></el-card></td>
          <th>管理服务人数</th>
          <td><el-card v-text="baseInfo.baseServiceNum" align="center"></el-card></td>
          <th>基地获得投资金额总数(万元)</th>
          <td><el-card v-text="baseInfo.baseProInvestAllMoney" align="center"></el-card></td>
        </tr>

        <tr align='center'>
          <th>当前就业见习基地数量</th>
          <td><el-card v-text="baseInfo.baseNowBaseNum" align="center"></el-card></td>
          <th>场地利用率</th>
          <td><el-card v-text="baseInfo.baseAreaUseRate" align="center"></el-card></td>
          <th>众创空间总收入(万元)</th>
          <td><el-card v-text="baseInfo.baseAllInMoney" align="center"></el-card></td>
        </tr>
        <tr :align='center'>
          <th>众创空间运营成本(万元)</th>
          <td><el-card v-text="baseInfo.baseOperateCost" align="center"></el-card></td>
          <th>常驻团队和使用面积(万元)</th>
          <td><el-card v-text="baseInfo.baseTeamArea" align="center"></el-card></td>
          <th>基地公共服务面积(万元)</th>
          <td><el-card v-text="baseInfo.baseServiceArea" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>导师总人数</th>
          <td><el-card v-text="baseInfo.baseAllTeacherNum" align="center"></el-card></td>
          <th>全职教师总数</th>
          <td><el-card v-text="baseInfo.baseFullTeacherNum" align="center"></el-card></td>
          <th>兼职教师数量</th>
          <td><el-card v-text="baseInfo.basePartTeacherNum" align="center"></el-card></td>

        </tr>
        <tr align='center'>
          <th>现有已注册企业数量</th>
          <td><el-card v-text="baseInfo.baseCompanyNum" align="center"></el-card></td>
          <th>现有项目组数量</th>
          <td><el-card v-text="baseInfo.baseItemNum" align="center"></el-card></td>
          <th>累积已注册企业数</th>
          <td><el-card v-text="baseInfo.baseAllCompanyNum" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>累计项目组数量</th>
          <td><el-card v-text="baseInfo.baseAllItemNum" align="center"></el-card></td>
          <th>累计总投资额(万元)</th>
          <td><el-card v-text="baseInfo.baseAllInvest" align="center"></el-card></td>
          <th>累计总营业额(万元)</th>
          <td><el-card v-text="baseInfo.baseAllSales" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>累计场地和水电补贴(万元)</th>
          <td><el-card v-text="baseInfo.baseAllSubsidy" align="center"></el-card></td>
          <th>一次性创业补贴(万元)</th>
          <td><el-card v-text="baseInfo.baseFirstSubsidy" align="center"></el-card></td>
          <th>吸纳困难群体补贴(万元)</th>
          <td><el-card v-text="baseInfo.baseAllEmpHard" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>社会保险补贴(万元)</th>
          <td><el-card v-text="baseInfo.baseInsurance" align="center"></el-card></td>
          <th>担保贷款总额(万元)</th>
          <td><el-card v-text="baseInfo.baseAllLoan" align="center"></el-card></td>
          <th>吸纳就业见习人数</th>
          <td><el-card v-text="baseInfo.baseYearProbate" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>就业见习补贴金额(万元)</th>
          <td><el-card v-text="baseInfo.baseYearGrant" align="center"></el-card></td>
          <th>带动的就业人数</th>
          <td><el-card v-text="baseInfo.baseWorkNum" align="center"></el-card></td>
          <th>孵化出园率</th>
          <td><el-card v-text="baseInfo.baseOutRate" align="center"></el-card></td>
        </tr>
        <tr :align='center'>
          <th>直接就业人数</th>
          <td><el-card v-text="baseInfo.baseDirectNum" align="center"></el-card></td>
          <th>孵化出园数量</th>
          <td><el-card v-text="baseInfo.baseOutNum" align="center"></el-card></td>
          <th>当年实体数量</th>
          <td><el-card v-text="baseInfo.baseEntityNum" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>当年初创企业数量</th>
          <td><el-card v-text="baseInfo.baseInitCompany" align="center"></el-card></td>
          <th>累积初创企业数量</th>
          <td><el-card v-text="baseInfo.baseAllInitCompany" align="center"></el-card></td>
          <th>累计初创数量</th>
          <td><el-card v-text="baseInfo.baseAllInit" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>当年获得融资的企业数量</th>
          <td><el-card v-text="baseInfo.baseProInvestNum" align="center"></el-card></td>
          <th>累计获得融资的企业数量</th>
          <td><el-card v-text="baseInfo.baseProAllInvestNum" align="center"></el-card></td>
          <th>企业当年获得投资总额(万元)</th>
          <td><el-card v-text="baseInfo.baseProInvestAllMoney" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>企业当年获得社会投资总额(万元)</th>
          <td><el-card v-text="baseInfo.baseProInvestSocietyMoney" align="center"></el-card></td>
          <th>企业当年获得基地投资总额(万元)</th>
          <td><el-card v-text="baseInfo.baseProInvestBaseMoney" align="center"></el-card></td>
          <th>企业累积获得投资总额(万元)</th>
          <td><el-card v-text="baseInfo.baseProAllInvestAllMoney" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>企业累积获得社会投资总额(万元)</th>
          <td><el-card v-text="baseInfo.baseProAllInvestSocietyMoney" align="center"></el-card></td>
          <th>企业累积获得基地投资总额(万元)</th>
          <td><el-card v-text="baseInfo.baseProAllInvestBaseMoney" align="center"></el-card></td>
          <th>大学生创业数量</th>
          <td><el-card v-text="baseInfo.baseStuNum" align="center"></el-card></td>
        </tr>
        <tr align='center'>
          <th>科技人员创业数量</th>
          <td><el-card v-text="baseInfo.baseScienceNum" align="center"></el-card></td>
          <th>连续创业数量</th>
          <td><el-card v-text="baseInfo.baseSeriesNum" align="center"></el-card></td>
            <th>拥有有效知识产权数量</th>
          <td><el-card v-text="baseInfo.baseIprNum" align="center"></el-card></td>
        </tr>
          <tr align='center'>
          <th>新注册企业数量</th>
          <td><el-card v-text="baseInfo.baseNewNum" align="center"></el-card></td>
            <td colspan="7"></td>
          </tr>
        <tr style="height: 250px;overflow: scroll">
          <th>实体简况</th>
          <td colspan="7"><p style="height: 250px;overflow: auto;text-indent:2em;">
          <el-card v-text="baseInfo.baseMessage" align="left"></el-card>
           </p></td>
        </tr>
      </table>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { isMobile } from '@/utils/validate'
  export default {
    data () {
      var validateMobile = (rule, value, callback) => {
        if (!isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        baseInfo: [],
        costList: [],
        moneyList: [],
        stationList: [],
        teacherList: [],
        statusList: [{
          value: 1,
          label: '全职'
        }, {
          value: 2,
          label: '兼职'
        }],
        sexList: [{
          value: 1,
          label: '男'
        }, {
          value: 2,
          label: '女'
        }],
        instinctList: [{
          value: 1,
          label: '正高级'
        }, {
          value: 2,
          label: '副高级'
        }, {
          value: 3,
          label: '中级'
        }, {
          value: 4,
          label: '初级'
        }, {
          value: 5,
          label: '无职称'
        }],
        dataForm: {
          id: 0,
          teacherName: '',
          teacherSex: '',
          teacherUnit: '',
          teacherWorkStatus: '',
          teacherPhone: '',
          teacherJob: '',
          teacherInstinct: '',
          baseId: ''
        },
        dataRule: {
          teacherName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          teacherPhone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' },
            { validator: validateMobile, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.dataForm.id = id || 0
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/info/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.baseInfo = data.baseInfo
            }
          })
        }
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/cost/list`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.costList = data.page.list
            }
          })
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/money/list`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.moneyList = data.page.list
            }
          })
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/station/list`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.stationList = data.page.list
            }
          })
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/teacher/list`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.teacherList = data.page.list
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/base/teacher/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'teacherId': this.dataForm.id || undefined,
                'teacherName': this.dataForm.teacherName,
                'teacherSex': this.dataForm.teacherSex,
                'teacherUnit': this.dataForm.teacherUnit,
                'teacherWorkStatus': this.dataForm.teacherWorkStatus,
                'teacherPhone': this.dataForm.teacherPhone,
                'teacherJob': this.dataForm.teacherJob,
                'teacherInstinct': this.dataForm.teacherInstinct,
                'baseId': this.dataForm.baseId
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
