<template>
  <div class="app-wrap" v-if="!hide" style="margin-top: 20px;">
    <div class="app-w app-menu clearfix">
      <!-- 左边 -->
      <div class="app-menu-right clearfix"  style="margin-left: 0;">
        <ul class="fl-l app-menu-rleft" style="width: 620px;">
          <li v-if="left" v-for="(item, index) in left" :key="index"><a :href="item.url" :target="item.target || '_blank'"><img :src="item.src" :alt="item.src" :title="item.src"></a></li>
        </ul>
        <div class="app-menu-rright" style="margin-left: 630px;">
          <div class="gg-box">
            <h3 class="gg-title">通知公告</h3>
            <div class="gg-content" style="height: 160px;">
              <p class="gg-item"><a href="#" class="gg-text">习近平告诉你 我们制度优势如何成为治理体系优势习近平告诉你 我们制度优势如何成为治理体系优势</a></p>
              <p class="gg-item"><a href="#" class="gg-text">政治局集体学习专议这项技术发展 习近平有何深远考量政治局集体学习专议这项技术发展 习近平有何深远考量</a></p>
              <p class="gg-item"><a href="#" class="gg-text">系统集成 协同高效 改革协调推进系统集成 协同高效 改革协调推进</a></p>
              <p class="gg-item"><a href="#" class="gg-text">第二届进博会11月5日开幕 凸显五大亮点第二届进博会11月5日开幕 凸显五大亮点</a></p>
              <p class="gg-item"><a href="#" class="gg-text">人民日报钟声：相约进博会 书写新篇章人民日报钟声：相约进博会 书写新篇章</a></p>
              <p class="gg-item"><a href="#" class="gg-text">习近平告诉你 我们制度优势如何成为治理体系优势习近平告诉你 我们制度优势如何成为治理体系优势</a></p>
              <p class="gg-item"><a href="#" class="gg-text">政治局集体学习专议这项技术发展 习近平有何深远考量政治局集体学习专议这项技术发展 习近平有何深远考量</a></p>
              <p class="gg-item"><a href="#" class="gg-text">系统集成 协同高效 改革协调推进系统集成 协同高效 改革协调推进</a></p>
              <p class="gg-item"><a href="#" class="gg-text">第二届进博会11月5日开幕 凸显五大亮点第二届进博会11月5日开幕 凸显五大亮点</a></p>
              <p class="gg-item"><a href="#" class="gg-text">人民日报钟声：相约进博会 书写新篇章人民日报钟声：相约进博会 书写新篇章</a></p>
            </div>
          </div>
          <div class="gg-box" style="height: 295px;margin-top: 13px;">
            <h3 class="gg-title">最新项目发布</h3>
            <div class="gg-content" style="height: 280px">
              <ul class="gg-pc" :style="'margin-top:' + -top + 'px'">
                <li class="gg-pro" v-for="item in da">
                  <div v-if="item.data!=null">
                    <p class="gg-textl">
                      <a href="#" class="gg-text">{{item.data.proName}}</a>
                    </p>
                    <p class="gg-textl">
                      <span class="gg-text">{{item.data.proOrigin}}</span>&nbsp;<span class="gg-text">|</span>
                      <span class="gg-text">{{item.data.proIntroduce}}</span>
                    </p>
                    <p class="gg-textl" v-on:click="joinProject(item.data.proInfoId)">
                      <span class="gg-text">点击查看项目</span>
                    </p>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 详情 -->
    <project-join v-if="xiangqing" ref="pj"/>
  </div>
</template>
<script>
  import ProjectJoin from './project-join'
  import Image01 from '@/assets/img/1.jpg'
  import Image02 from '@/assets/img/2.jpg'
  import Image03 from '@/assets/img/3.jpg'
  // 快捷菜单
  export default {
    name: 'boss-menu',
    props: { hide: Boolean },
    data () {
      return {
        pageIndex: 1,
        pageSize: 12,
        hasApply: '1',
        xiangqing: false,
        proType: 0,
        top: 0,
        stepN: 1,
        height: 125,
        max: 0,
        da: [1, 2, 3, 4, 5, 6, 7, 8, 9],
        left: [{url: '#', target: '_blank', src: Image01}, {url: '#', target: '_blank', src: Image02}, {url: '#', target: '_blank', src: Image03}]
      }
    },
    components: {
      ProjectJoin
    },
    mounted () {
      this.scrollTip()
      this.getPeojectInfos()
    },
    methods: {
      // 加入项目详情
      joinProject: function (id) {
        if (!this.$cookie.get('token')) {
          this.$message.warning('您还未登入,不能查看加入项目')
          return
        }
        this.xiangqing = true
        this.$nextTick(() => {
          this.$refs.pj.init('加入项目详情', id)
        })
      },
      getPeojectInfos () {
        this.$http({
          url: this.$http.adornUrl('/webpage/newProjectInfos'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'inApply': this.hasApply,
            'proType': this.proType
          })
        }).then(({data}) => {
          // console.log(data)
          if (data.code === 500) {
            this.$message.error(data.msg)
          } else {
            if (data.data) {
              let that = this
              const result = []
              data.data.records && data.data.records.forEach(function (item, index, arr) {
                result.push(that.invokeDatas(item))
              })
              this.da = result
              // console.log(this.da)
            }
          }
        })
      },
      // 处理数据格式
      invokeDatas (data) {
        return {
          url: '#',
          target: '_blank',
          data: data
        }
      },
      scrollTip () {
        this.da.concat(this.da)
        this.max = this.height * (this.da.length / 2 - 1)
        this.play()
      },
      play () {
        let that = this
        setInterval(function () {
          // console.log(that.top >= that.max)
          if (that.top >= that.max + 60) {
            that.top = 0
          }
          that.top = that.top + that.stepN
        }, 30)
      }
    }
  }
</script>
<style>
  @import "../../../../static/plugins/scroll/scroll.css";
</style>
<style scoped>
  .gg-box{max-width: 540px;padding:10px;background-color: #fff;border-radius: 5px;box-shadow: 0 0 10px 0px #e8e5e5;}
  .gg-title{font-weight: normal;color: #5dd5c8;font-size: 20px;line-height: 39px;border-bottom: 1px solid #e8e5e5;}
  .gg-content{height: 200px;overflow: hidden;}
  .gg-item,.gg-textl{line-height: 30px;height: 30px;margin-top: 1px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;border-bottom: 1px dashed #e8e5e5;}
  .gg-text{color: #4b4b4b;font-size: 16px;transition: .5s;}
  .gg-item:hover{background-color: #eee;}
  .gg-text:hover{margin-left: 5px;color: #00d7c6;}
  .gg-pc{padding: 0 10px;}
  .gg-pro{height: 99px;padding:8px;box-shadow: 0 0 10px 0px #e8e5e5;margin-top:10px;border-radius: 8px;cursor: pointer;}
  .gg-pro:hover{background-color: #f3f3f3;}
  .gg-textl:hover .gg-text{color: #00d7c6;}
</style>
