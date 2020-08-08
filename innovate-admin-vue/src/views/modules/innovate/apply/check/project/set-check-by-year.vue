<template>
  <el-dialog
    width="40%"
    @close="closeDialog"
    v-loading="dataListLoading"
    :title="!dataForm.id ? '设置中期检查' : '设置中期检查'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="20">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="16rem" style="width: 94%; margin: 0 auto">
        <el-col :span="24">
          <el-form-item label="选择年度">
            <el-date-picker
              v-model="dataForm.checkTime"
              align="right"
              type="year"
              placeholder="请选择年度">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="addLoading">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  // import {ElNotificationOptions as onClose} from "element-ui";

  export default {
    data () {
      return {
        addLoading: false,
        dataListLoading: false,
        visible: false,
        leadVisible: false,
        dataForm: {
          checkTime: new Date()
        },
        dataRule: {
          checkTime: [
            {required: true, message: '年度不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.visible = true
      },
      // 表单提交
      dataFormSubmit () {
        this.dataForm.isUpdate = 0
        this.addLoading = true
        this.$http({
          url: this.$http.adornUrl(`/innovate/check/saveByTime`),
          params: this.$http.adornParams({
            'checkTime': this.dataForm.checkTime.getFullYear()
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$notify({
              title: '操作成功',
              duration: '3000',
              message: '按照年度设置中期检查成功',
              type: 'success',
              onClose: () => {
                this.visible = false
                this.$emit('refreshDataList')
              }
            })
          } else {
            this.$notify({
              title: '操作失败',
              duration: '3000',
              message: data.msg,
              type: 'error',
              onClose: () => {
                this.addLoading = true
              }
            })
          }
        })
      },
    // 子组件的各类方法
      addVisible (list) {
        let isDelNum = 0
        let visible = false
        for (let index in list) {
          if (list.length > 0 && list[index].isDel === 1) {
            isDelNum++
          }
        }
        if (isDelNum === list.length) {
          visible = true
        }
        return visible
      },
      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      }
    }
  }
</script>

