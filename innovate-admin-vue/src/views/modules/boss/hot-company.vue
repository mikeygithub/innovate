 <template>
  <div class="app-wrap" v-if="!hide">
    <div class="app-w app-qy mar-t30">
      <h3 class="text-not-wrap" v-if="menu">
        <span v-for="(item, index) in menu" :class="{ 'cur': item.cur }" :key="index" @click="getListInfo(item.name, index)">{{item.name}}</span>
      </h3>
      <!-- 二级关联 -->
      <div class="app-qy-box clearfix box-s-b">
        <!-- 列表项 -->
        <ul class="show clearfix" v-if="list">
          <!-- 子项 -->
          <li v-for="(item, index) in list" :key="index">
            <div class="sub-li">
              <a :href="item.url" :target="item.target || '_blank'" class="company-info">
                <img :src="item.img" :alt="item.img" :title="item.img">
                <div class="conpany-text">
                  <h4>{{item.title}}</h4>
                  <p>{{item.desc && item.desc.value}}<span class="vline"></span>{{item.desc && item.desc.type}}</p>
                </div>
              </a>
              <a :href="item.url" :target="item.target || '_blank'" class="about-info">
                <p>
                  <span class="pull-right"><span class="text-blue">{{item.company && item.company.value}}</span>{{item.company && item.company.name}}</span>
                  <span class="text-blue">{{item.desc && item.desc.value}}</span>
                  {{item.desc && item.desc.name}}
                </p>
              </a>
            </div>
          </li>
        </ul>
      </div>
      <p class="common-tab-more" v-if="more"><a class="btn btn-outline" :href="more.url" :target="more.target || '_blank'">{{more.name}}</a></p>
    </div>
  </div>
</template>

<script>
  // 热门企业
  export default {
    props: {hide: Boolean},
    data () {
      return {
        list: [{url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}},
          {url: '#', target: '_blank', img: '/src/assets/img/qm.jpg', title: '全民直播', desc: {value: 'A轮', type: '互联网'}, company: {title: {value: '3', name: '位BOSS在线'}, desc: {value: '8', name: '个热招职位'}}}],
        menu: [{name: '热门企业', cur: true}, {name: '行业巨头', cur: false}, {name: '新锐公司', cur: false}, {name: '上市公司', cur: false}],
        more: {name: '查看更多', url: '#'}
      }
    },
    methods: {
      getListInfo (type, index) {
        // console.log(type, index)
        let that = this
        this.menu.forEach((item, aindex, arr) => {
          // console.log(item, aindex, arr)
          item['cur'] = false
          if (index === aindex) {
            that.menu[aindex].cur = true
            // TODO请求网络数据，回填list
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
