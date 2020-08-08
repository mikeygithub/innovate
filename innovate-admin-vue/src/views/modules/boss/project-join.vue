<template>
  <el-dialog
    :title="'项目信息'"
    :width="'60%'"
    v-loading="loading"
    :visible.sync="visible">
    <!-- 详情 -->
    <div class="info-box" v-if="result">
      <!-- 顶部 -->
      <div class="info-top">
        <!-- 左边 -->
        <div class="top-left s">
          <h4>{{result.proName}}</h4>
          <div>项目类型:{{result.proType}}</div>
          <div>项目来源:{{result.proOrigin}}</div>
          <div>项目经费:{{result.proOutlay}}</div>
        </div>
        <!-- 右边 -->
        <div class="top-right s">
          <div class="t-r-b">
            <span class="info-btn" @click="join2">立即加入</span>
          </div>
        </div>
        <div style="clear: both;"></div>
        <!-- 主要主题项目内容 -->
        <div class="info-content">
          <div class="detail-bottom-title">项目介绍</div>
          <div class="detail-bottom-text">{{result.proIntroduce}}</div>
        </div>
      </div>
    </div>

  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        loading: true,
        visible: false,
        proInfoId: 0,
        result: null,
        proTypeList: [
          {value: 1, label: '科研项目'},
          {value: 2, label: '横向项目'},
          {value: 3, label: '企业项目'},
          {value: 4, label: '大创项目'},
          {value: 5, label: '企业招聘'},
          {value: 6, label: '实习项目对接'}
        ]
      }
    },
    methods: {
      // 加入项目
      join2 () {
        console.log('加入项目', this.result.proInfoId)
        this.$http({
          url: this.$http.adornUrl(`/enterprise/person/cooperation/save`),
          method: 'get',
          params: this.$http.adornParams({
            'proInfoId': this.result.proInfoId
          })
        }).then(({data}) => {
          console.log(data)
          this.loading = false
          if (data && data.code === 0) {
            this.$message.success(data.msg)
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      init (hasType, id) {
        console.log(hasType, id)
        this.visible = true
        this.proInfoId = id
        this.$http({
          url: this.$http.adornUrl(`/webpage/projectInfo/${this.proInfoId}/`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          console.log(data)
          this.loading = false
          if (data && data.code === 0) {
            this.result = data.data
            if (this.result.proType === 1) {
              this.result.proType = '科研项目'
            }
            if (this.result.proType === 2) {
              this.result.proType = '横向项目'
            }
            if (this.result.proType === 3) {
              this.result.proType = '企业项目'
            }
            if (this.result.proType === 4) {
              this.result.proType = '大创项目'
            }
            if (this.result.proType === 5) {
              this.result.proType = '企业招聘'
            }
            if (this.result.proType === 6) {
              this.result.proType = '实习项目对接'
            }
          }
        })
      }
    }
  }
</script>

<style >
  .s{box-sizing: border-box;}
  .info-box{width: 100%;}
  .info-top{padding: 20px;background-color: #f5f8fc;}
  .top-left{float: left;width: 50%;color: #666;font-size: 22px;}
  .top-left h4{font-weight: 400;line-height: 50px;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
  .top-left p{font-weight: 400;font-size: 18px;color: #aaa;line-height: 20px;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
  .top-right{margin-left: 50%;}
  .t-r-b{margin-top: 20px;text-align: right;}
  .info-btn{ height: 30px;line-height: 30px;display: inline-block;font-size: 13px;min-width: 85px;    color: #fff;letter-spacing: 1px;background: #5dd5c8;text-align: center;cursor: pointer;border: 1px #5dd5c8 solid;margin-top: 0;}
  .info-btn:hover{background-color: #84ece1;color: #eee;}

  .info-content{margin-top:20px;background: #fff;padding: 0 20px 23px;font-size: 13px;}
  .detail-bottom-title{padding: 10px 0 0;font-size: 14px;color: #414a60;}
  .detail-bottom-text{max-height: 260px;overflow: hidden;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 10;font-size: 14px;    white-space: normal; color: #abafba;line-height: 26px;}

</style>
