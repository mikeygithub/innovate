<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="150px">
      <el-form-item label="工位编号" prop="stationNum">
        <el-input v-model="dataForm.stationNum" placeholder="请输入工位编号"></el-input>
      </el-form-item>
      <el-form-item label="工位面积（平方米）" prop="stationArea">
        <el-input v-model="dataForm.stationArea" placeholder="请输入工位面积"></el-input>
      </el-form-item>
      <el-form-item label="所属中心" prop="baseId">
        <el-select v-model="dataForm.baseId" placeholder="请选择所属中心">
          <el-option
            v-for="item in baseList"
            :key="item.baseId"
            :label="item.baseName"
            :value="item.baseId">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {isFloatNumber} from '@/utils/validate'

export default {
  data () {
    var validateFloatNumber = (rule, value, callback) => {
      if (!isFloatNumber(value)) {
        callback(new Error('至少保留小数点后一位'))
      } else {
        callback()
      }
    }
    return {
      visible: false,
      baseList: [],
      dataForm: {
        id: 0,
        entName: '',
        entCorporate: '',
        entBusiness: '',
        entCode: '',
        entRegister: '',
        entFoundingTime: '',
        entType: '',
        entStatus: '',
        entRegisterAddress: '',
        entAddress: '',
        entIntroduce: '',
        newHighZones: '',
        entInTime: '',
        inApply: ''
      },
      dataRule: {
        stationNum: [
                    {required: true, message: '工位编号不能为空', trigger: 'blur'}
        ],
        stationArea: [
                    {required: true, message: '工位面积不能为空', trigger: 'blur'},
                    {validator: validateFloatNumber, trigger: 'blur'}
        ],
        baseId: [
                    {required: true, message: '请选择所属中心', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.visible = true
      this.dataForm.id = id || 0
      this.$http({
        url: this.$http.adornUrl(`/innovate/base/info/list`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.baseList = data.page.list
        }
      })
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/station/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            console.info(data)
            if (data && data.code === 0) {
              this.dataForm.baseId = data.station.baseId
              this.dataForm.stationNum = data.station.stationNum
              this.dataForm.stationArea = data.station.stationArea
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
            url: this.$http.adornUrl(`/innovate/base/station/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'stationId': this.dataForm.id || undefined,
              'stationNum': this.dataForm.stationNum,
              'stationArea': this.dataForm.stationArea,
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
