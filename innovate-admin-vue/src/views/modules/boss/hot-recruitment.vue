<template>
  <!-- 城市热招 -->
  <div class="app-wrap" v-if="!hide">
    <div class="app-w app-qy mar-t30">
      <h3 class="text-not-wrap">
        <span class="cur">{{datas.title}}</span>
      </h3>
      <!-- 二级关联 -->
      <div class="app-hot-box clearfix box-s-b">
        <!-- 列表项 -->
        <ul class="show clearfix">
          <!-- 子项 -->
          <li v-if="datas" v-for="(item, index) in datas.imgList" :key="index">
            <a :href="item.href" target="_blink">
              <div class="hot-bg" style="text-align: center;line-height: 100px;color:#acacac;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">{{item.desc}}</div>
              <img :src="item.src" alt="">
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  import AchiImg1 from '@/assets/img/city/city1.jpg'
  import AchiImg2 from '@/assets/img/city/city2.jpg'
  import AchiImg3 from '@/assets/img/city/city3.jpg'
  import AchiImg4 from '@/assets/img/city/city4.jpeg'
  import AchiImg5 from '@/assets/img/city/city5.jpg'
  import AchiImg6 from '@/assets/img/city/city6.jpg'
  import AchiImg7 from '@/assets/img/city/city7.jpg'
  import AchiImg8 from '@/assets/img/city/city8.jpg'
  import AchiImg9 from '@/assets/img/city/city9.png'
  import AchiImg10 from '@/assets/img/city/city10.jpg'
  import AchiImg11 from '@/assets/img/city/city11.jpg'
  import AchiImg12 from '@/assets/img/city/city12.jpg'

  // 双创成果
  export default {
    props: {hide: Boolean},
    data () {
      return {
        datas: {
          title: '梧州学院 | 双创成果',
          imgList: [
            {src: AchiImg1, href: 'http://cxcy.gxuwz.edu.cn/info/1005/1551.htm', desc: '我校学生在第五届“互联网＋”大学生创新创业大赛广西选拔赛中喜获佳绩'},
            {src: AchiImg2, href: 'http://cxcy.gxuwz.edu.cn/info/1090/1193.htm', desc: '创业学生农达烁：以后的你会感谢现在拼命的自己'},
            {src: AchiImg3, href: 'http://cxcy.gxuwz.edu.cn/info/1091/1663.htm', desc: '创新创业政策知识讲座'},
            {src: AchiImg4, href: 'http://cxcy.gxuwz.edu.cn/info/1091/1623.htm', desc: '“梧院约你创”约到了书记和校长'},
            {src: AchiImg5, href: 'http://cxcy.gxuwz.edu.cn/info/1091/1623.htm', desc: '梧院约你创”约到了书记和校长'},
            {src: AchiImg6, href: 'http://cxcy.gxuwz.edu.cn/info/1091/1622.htm', desc: '与万秀区税务局共同举办“新时代，新税务”活动'},
            {src: AchiImg7, href: 'http://cxcy.gxuwz.edu.cn/info/1091/1622.htm', desc: '与万秀区税务局共同举办“新时代，新税务”活动'},
            {src: AchiImg8, href: 'http://cxcy.gxuwz.edu.cn/info/1091/1621.htm', desc: ' “梧院双创邀您说”讲坛之科技专家说'},
            {src: AchiImg9, href: 'http://cxcy.gxuwz.edu.cn/info/1005/1502.htm', desc: '我校学生在第四届梧州市创新创业大赛决赛中喜获佳绩'},
            {src: AchiImg10, href: 'http://cxcy.gxuwz.edu.cn/info/1005/1240.htm', desc: '我校学生获第八届全国大学生机械创新设计大赛一等奖'},
            {src: AchiImg11, href: 'http://cxcy.gxuwz.edu.cn/info/1005/1203.htm', desc: '大学生创新创业大赛获“一金三银”'},
            {src: AchiImg12, href: 'http://cxcy.gxuwz.edu.cn/info/1005/1190.htm', desc: '学校与梧州市工业园区共建创新创业孵化基地'}
          ]
        }
      }
    }
  }
</script>

<style scoped>

</style>
