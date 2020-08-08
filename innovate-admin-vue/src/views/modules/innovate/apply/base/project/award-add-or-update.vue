<template>
  <el-dialog
    append-to-body
    :title="!id ? '新增所获成果/奖项' : '修改所获成果/奖项'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="奖项名称" prop="awardName">
        <el-input v-model="dataForm.awardName" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="奖金类型" prop="awardType">
        <el-select v-model="dataForm.awardType" placeholder="请选择">
          <el-option
            v-for="item in typeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="奖项等级" prop="awardRank">
        <el-select v-model="dataForm.awardRank" placeholder="请选择">
          <el-option
            v-for="item in rankList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="获奖时间" prop="awardTime">
        <el-date-picker
          v-model="dataForm.awardTime"
          type="date"
          format="yyyy年MM月dd日"
          value-format="timestamp"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: 'award-add-or-update',
    data () {
      return {
        visible: false,
        typeList: [{
          value: 1,
          label: '国际级'
        }, {
          value: 2,
          label: '国家级'
        }, {
          value: 3,
          label: '省级'
        }, {
          value: 4,
          label: '市厅级'
        }, {
          value: 5,
          label: '县局级'
        }, {
          value: 6,
          label: '校级'
        }],
        // rankList: [{
        //   value: 1,
        //   label: '特等奖'
        // }, {
        //   value: 2,
        //   label: '一等奖'
        // }, {
        //   value: 3,
        //   label: '二等奖'
        // }, {
        //   value: 4,
        //   label: '三等奖'
        // }, {
        //   value: 5,
        //   label: '优秀奖'
        // }],
        rankList: [
          {value: 1, label: '特等奖'}, {value: 2, label: '一等奖'},
          {value: 3, label: '二等奖'}, {value: 4, label: '三等奖'},
          {value: 5, label: '优秀奖'}, {value: 6, label: '金奖'},
          {value: 7, label: '银奖'}, {value: 8, label: '铜奖'},
          {value: 9, label: '第一名'}, {value: 10, label: '第二名'},
          {value: 11, label: '第三名'}, {value: 12, label: '第四名'},
          {value: 13, label: '第五名'}, {value: 14, label: '其他'}
        ],
        id: 0,
        dataForm: {
          awardName: '',
          awardRank: '',
          awardType: '',
          awardTime: '',
          projectId: '',
          isDel: 0
        },
        dataRule: {
          awardType: [
            { required: true, message: '请选择奖金类型', trigger: 'blur' }
          ],
          awardName: [
            { required: true, message: '奖项名称不能为空', trigger: 'blur' }
          ],
          awardRank: [
            { required: true, message: '奖项等级不能为空', trigger: 'blur' }
          ],
          awardTime: [
            { required: true, message: '获奖时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (item, index) {
        this.visible = true
        this.id = index || 0
        this.$nextTick(() => {
          this.dataForm.awardId = ''
          this.$refs['dataForm'].resetFields()
          this.id = index || 0
          if (this.id) {
            this.dataForm = JSON.parse(JSON.stringify(item))
            this.dataForm.awardTime = new Date(this.dataForm.awardTime)
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.visible = false
            this.dataForm.awardTime = Number(this.dataForm.awardTime)
            this.$emit('refreshDataList', this.dataForm, this.id)
          }
        })
      }
    }
  }
</script>
