<template>
  <div>
    <el-button type="primary" @click="switchChinese()">切换中文</el-button>
    <el-button type="primary" @click="switchEnlish()">切换英文</el-button>
    <p>{{$t('message.hello')}}</p>

    <div @click="testRef()">显示/隐藏</div>
    <!--<el-dialog title="收货地址" :visible.sync="isTestRef">-->
      <!--<p>dui</p>-->
    <!--</el-dialog>-->
    <template v-if="isTestRef">文字{{msg}}
    </template>

    <!--<el-dialog title="testref" :visible.sync="isTestRef">-->
      <!--<p>testref</p>-->
    <!--</el-dialog>-->
    <test-ref @dd="dd" v-if="isTestRef"></test-ref>
    <el-row :gutter="20">
      <el-col class="el-row" :span="4">
        <el-row style="background: #0000cc" :gutter="20">
          <el-col style="background: #00ff00"  :span="4">4</el-col>
          <el-col style="background: #ff7800" :span="4">4</el-col>
          <el-col :span="4">4</el-col>
          <el-col :span="4">4</el-col>
          <el-col :span="4">4</el-col>
          <el-col :span="4">4</el-col>
        </el-row>
      </el-col>
      <el-col class="el-row" :span="4">4{{msg}}</el-col>
      <el-col class="el-row" :span="4">4</el-col>
      <el-col class="el-row" :span="4">4</el-col>
      <el-col class="el-row" :span="4">4</el-col>
      <el-col class="el-row" :span="4">4</el-col>
    </el-row>
  </div>
</template>

<script>
  import { mapMutations } from 'vuex'
  import TestRef from './testref'
  export default {
    components: {
      TestRef
    },
    data () {
      return {
        isTestRef: false,
        msg: 'ddd'
      }
    },
    methods: {
      ...mapMutations([
        'dd'
      ]),
      testRef () {
        // this.$alert('ddd')
        this.isTestRef = !this.isTestRef
        this.$message.info('kkkkk')
      },
      dd () {
        this.isTestRef = !this.isTestRef
        this.msg = 'jj'
      },
      switchChinese () {
        this.$i18n.locale = 'cn'
      },
      switchEnlish () {
        this.$i18n.locale = 'en'
      }
    }
  }
</script>

<style scoped>
  .el-row {
    background: #0BB2D4;
  }
</style>
