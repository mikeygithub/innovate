<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <!--评分指标开始-->
    <el-row>
  <table border="1" cellspacing="0" width="100%" class="table">
    <!--<tr align='center'>-->
      <!--<td colspan="24" style="height: 1.2rem"></td>-->
    <!--</tr>-->
    <tr class="contents" align="center">
      <th colspan="24">项目评分要点</th>
    </tr>
    <!--<tr align='center'>-->
      <!--<td colspan="24" style="height: 1.2rem"></td>-->
    <!--</tr>-->
    <tr align='center' style="height: 3rem">
      <th colspan="17">评审要点文件名</th>
      <th colspan="7">操作</th>
    </tr>
    <!--<template v-for="item in matchReviewPointEntityList">-->
      <!--<tr align="center">-->
        <!--<td colspan="2" v-text="item.reviewPoint"></td>-->
        <!--<td colspan="20" v-text="item.reviewContent"></td>-->
        <!--<td colspan="2" v-text="item.reviewScores"></td>-->
      <!--</tr>-->
    <!--</template>-->
    <tr align="center">
      <td colspan="17" align="center"><el-tag type="small" v-text="eventEntity.attachName"></el-tag></td>
      <td colspan="7" align="center"><el-button v-if="eventEntity.awardPath!==''" @click="awardDown(eventEntity)">下载</el-button></td>
    </tr>
    </table>
    </el-row>
    <!--评分指标结束-->
    <br>
    <br>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="8rem">
      <el-form-item label="指标1打分:" prop="indexOne">
        <el-input v-model.number="dataForm.indexOne" placeholder="请输入" maxlength="2" @blur="sumScores()"></el-input>
      </el-form-item>
      <el-form-item label="指标2打分:" prop="indexTwo">
        <el-input v-model.number="dataForm.indexTwo" placeholder="请输入" maxlength="2" @blur="sumScores()"></el-input>
      </el-form-item>
      <el-form-item label="指标3打分:" prop="indexThirty">
        <el-input v-model.number="dataForm.indexThirty" placeholder="请输入" maxlength="2" @blur="sumScores()"></el-input>
      </el-form-item>
      <el-form-item label="指标4打分:" prop="indexFour">
        <el-input v-model.number="dataForm.indexFour" placeholder="请输入" maxlength="2" @blur="sumScores()"></el-input>
      </el-form-item>
      <el-form-item label="总分(自动计算)" prop="score">
        <el-input v-model.number="dataForm.score" placeholder="请输入" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="评语" prop="opinion">
        <el-input
          type="textarea"
          :rows="5"
          placeholder="请输入"
          v-model="dataForm.opinion">
        </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  // import { isFloatNumber } from '@/utils/validate'
  import { isNumber } from '@/utils/validate'
  export default {
    name: 'score-add-or-update',
    data () {
      // var validateFloatNumber = (rule, value, callback) => {
      //   if (!isFloatNumber(value)) {
      //     callback(new Error('至少保留小数点后一位'))
      //   } else {
      //     callback()
      //   }
      // }
      //  var validateIsNumber = (rule, value, callback) => {
      //    if (!isNumber(value)) {
      //      callback(new Error('请输入整数'))
      //    } else {
      //      callback()
      //    }
      //  }
      return {
        addLoading: false,
        visible: false,
        loading: false,
        userMap: [],
        matchReviewPointEntityList: [],
        eventEntity: {},
        id: 0,
        dataForm: {
          reviewId: '',
          apply: 'project_match_apply_status',
          userId: this.$store.state.user.id,
          refereeNo: this.$store.state.user.id,
          matchId: this.id,
          opinion: '',
          indexOne: '',
          indexTwo: '',
          indexThirty: '',
          indexFour: '',
          score: ''
        },
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataRule: {
          // score: [
          //   { required: true, message: '分数不能为空', trigger: 'blur' }
          //   { validator: validateFloatNumber, trigger: 'blur' }
          // ],
          indexOne: [
            { required: true, message: '分数不能为空', trigger: 'blur' },
            {type: 'number', message: '分数必须为数字', trigger: 'blur'}
            // { validator: validateFloatNumber, trigger: 'blur' }
          ],
          indexTwo: [
            { required: true, message: '分数不能为空', trigger: 'blur' },
            {type: 'number', message: '分数必须为数字', trigger: 'blur'}
            // { validator: validateFloatNumber, trigger: 'blur' },
          ],
          indexThirty: [
            { required: true, message: '分数不能为空', trigger: 'blur' },
            {type: 'number', message: '分数必须为数字', trigger: 'blur'}
            // { validator: validateFloatNumber, trigger: 'blur' }
          ],
          indexFour: [
            { required: true, message: '分数不能为空', trigger: 'blur' },
            {type: 'number', message: '分数必须为数字', trigger: 'blur'}
            // { validator: validateFloatNumber, trigger: 'blur' }
          ],
          opinion: [
            { required: true, message: '评语不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (index, eventId, reviewType) {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/review/info`),
              method: 'get',
              params: this.$http.adornParams({
                'apply': 'project_match_apply_status',
                'userId': this.$store.state.user.id,
                'matchId': this.id,
                'isDel': 0
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.matchReviewEntity
              }
            })
          }
        })
        // 获取赛事信息
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/innovate/match/event/info/` + eventId),
            method: 'get',
            params: this.$http.adornParams({
              'eventId': this.eventId,
              'apply': 'project_match_apply_status'
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.eventEntity = data.matchEventEntity
            }
          })
        })
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/innovate/match/reviewPoint/list`),
            method: 'get',
            params: this.$http.adornParams({
              'reviewType': reviewType,
              'isDel': 0
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.matchReviewPointEntityList = data.matchReviewPointEntityList
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$http({
              url: this.$http.adornUrl(`/innovate/match/review/score`),
              method: 'post',
              data: this.$http.adornData(this.dataForm)
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
      },
      // 对各个指标分数进行求和
      sumScores () {
        if (isNumber(this.dataForm.indexOne) && isNumber(this.dataForm.indexTwo) && isNumber(this.dataForm.indexThirty) && isNumber(this.dataForm.indexFour)) {
          this.dataForm.score = this.dataForm.indexOne + this.dataForm.indexTwo + this.dataForm.indexThirty + this.dataForm.indexFour
        }
      },
      // 文件下载
      awardDown (award) {
        this.$httpFile({
          url: this.$httpFile.adornUrl(`/innovate/match/attach/download`),
          method: 'post',
          params: this.$httpFile.adornParams({
            'filePath': award.attachPath
          })
        }).then(response => {
          if (!response) {
            return
          }
          let url = window.URL.createObjectURL(new Blob([response.data]))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', award.attachName)
          document.body.appendChild(link)
          link.click()
        }).catch(err => {
          console.log(err)
        })
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
