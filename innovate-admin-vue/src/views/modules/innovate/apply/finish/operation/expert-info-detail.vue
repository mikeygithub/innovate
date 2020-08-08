  <template>
    <el-dialog
      :title="'详情'"
      width="80%"
      v-loading="dataListLoading"
      @close="closeDialog"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-row>
        <table border="1" cellspacing="0" width="100%" class="table">
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr class="contents" align="center">
            <th colspan="10">
              专家明细汇总表基本信息
            </th>
          </tr>
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr align='center' style="height: 2.5rem">
            <th colspan="2">年度</th>
            <td colspan="3"><span style="font-size: 1.1rem; font-weight: bolder" v-text="innovateFinishExpertCollect.expertCollectTime" align="center"></span></td>
            <th colspan="2">学院</th>
            <td colspan="3"><span style="font-size: 1.1rem; font-weight: bolder" v-for="item in instituteList" v-if="item.instituteId.toString() === innovateFinishExpertCollect.expertCollectInstituteId.toString()" v-text="item.instituteName" align="center"></span></td>
          </tr>
          <tr>
            <th colspan="2">邮箱</th>
            <td colspan="3" align="center"><span style="font-size: 1.1rem; font-weight: bolder" v-text="innovateFinishExpertCollect.expertCollectInstituteEmail"></span></td>
            <th colspan="2">填表人</th>
            <td colspan="3" align="center"><span style="font-size: 1.1rem; font-weight: bolder" v-text="innovateFinishExpertCollect.expertCollectWriter"></span></td>
          </tr>
          <tr>
            <th colspan="2">填表人联系电话</th>
            <td colspan="8" align="center"><span style="font-size: 1.1rem; font-weight: bolder" v-text="innovateFinishExpertCollect.expertCollectWriterPhone"></span></td>
          </tr>
          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr><th align="center" colspan="10" style="height: 3rem"><span><strong>校内人员</strong></span></th></tr>
          <tr align="center">
            <th colspan="2">姓名</th>
            <th colspan="4">联系电话</th>
            <th colspan="4">职称</th>
          </tr>
          <tr align="center"  v-for="item in innovateFinishExpertCollect.finishInExpertEntities">
            <td colspan="2"><span v-if="item.sysUserEntity!=null" v-text="item.sysUserEntity.name"></span></td>
            <td colspan="4"><span v-if="item.sysUserEntity!=null" v-text="item.sysUserEntity.mobile"></span></td>
            <td colspan="4"><span v-if="item.sysUserEntity!=null" v-text="item.sysUserEntity.email"></span></td>
          </tr>

          <tr align='center'>
            <td colspan="10" style="height: 1.2rem"></td>
          </tr>
          <tr><th align="center" colspan="10" style="height: 3rem"><span><strong>校外专家</strong></span></th></tr>
          <tr align="center">
            <th colspan="2">姓名</th>
            <th colspan="2">联系电话</th>
            <th colspan="2">职称</th>
            <th colspan="2">单位</th>
<!--            <th colspan="2">身份证号</th>-->
          </tr>
          <tr v-for="item in innovateFinishExpertCollect.finishOutExpertEntities">
            <td align="center" colspan="2"><span v-text="item.expertName"></span></td>
            <td align="center" colspan="2"><span v-text="item.expertPhone"></span></td>
            <td align="center" colspan="2"><span v-text="item.expertTitles"></span></td>
            <td align="center" colspan="2"><span v-text="item.expertUnit"></span></td>
<!--            <td align="center" colspan="2"><span v-text="item.expertIdentity"></span></td>-->
          </tr>
        </table>
      </el-row>
      <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
    </el-dialog>
  </template>

<script>
    export default {
      name: 'expert-info-detail',
      data () {
        return {
          dataForm: {
            expertCollectId: ''
          },
          visible: false,
          dataListLoading: false,
          instituteList: this.$store.state.user.institute,
          userTeacherInfoEntities: [],
          teacherSet: '',
          teacherList: [],
          perInfoList: [],
          innovateFinishExpertCollect: {},
          sexList: [
            {value: 1, label: '男'}, {value: 2, label: '女'}
          ],
          teacherTitleList: this.$store.state.user.title
        }
      },
      methods: {
        init (id) {
          this.visible = true
          this.dataListLoading = true
          this.dataForm.expertCollectId = id || 0
          this.$http({
            url: this.$http.adornUrl(`/innovate/finish/expert/info/${this.dataForm.expertCollectId}`),
            method: 'get',
            params: this.$http.adornParams({
              'expertCollectId': this.dataForm.expertCollectId
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.innovateFinishExpertCollect = data.innovateFinishExpertCollect
              this.dataListLoading = false
            } else {
              this.$message.error(data.msg)
            }
          })
          this.dataListLoading = false
        },
        closeDialog () {
          this.visible = false
          this.$emit('refreshDataList')
        }
      }
    }
</script>

  <style>
    .contents{
      height: 60px;
      font-size: 18px;
    }
    .el-card__body {
      padding: 10px;
    }
    .el-step__title {
      font-size: 12px;
      line-height: 28px;
    }
    .table {
      border: 1px solid #ddd;
      /*为表格设置合并边框模型*/
      border-collapse: collapse;
      /*列宽由表格宽度和列宽度设定*/
      table-layout: fixed;
    }
    .table td {
      /*允许在单词内换行。*/
      word-break: break-word;
      /*设置宽度*/
      width: 80%;
    }
    .table th {
      /*允许在单词内换行。*/
      word-break: break-word;
      /*设置宽度*/
      width: 80%;
    }
  </style>
