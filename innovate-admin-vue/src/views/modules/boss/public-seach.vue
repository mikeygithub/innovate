<template>
  <div class="app-wrap app-sbox user-select" v-if="!hide" >
    <div class="app-w app-sform box-s-b clearfix">
      <!-- logo -->
      <div class="app-plogo fl-l">
        <a v-if="form.logo" :href="form.logo.url" :target="form.logo.target || '_blank'"><img :src="logoImg" alt="logo" title="logo"></a>
      </div>
      <div class="app-form">
        <div action="#" class="position-r" method="POST" onsubmit="return false;">
          <!-- 类型 -->
          <div class="app-ftype position-r" v-if="form">
            <p class="app-ftxt" v-if="form.select && form.select.title" @click="showOrHide">{{form.select.title}}</p>
            <!-- 职位类型 -->
            <div class="fl-l position-a app-ftbox" style="top: 50px;" v-if="form.select && form.select.show">
              <div class="city-box clearfix" style="">
                <ul class="dorpdown-province" v-if="form.select.first">
                  <li v-for="(item, index) in form.select.first" :class="{cur: item.cur}" :key="index" :data-seach="item.data">{{item.name}}</li>
                </ul>
                <!-- 二级菜单 -->
                <div v-if="false" class="dorpdown-city">
                  <ul v-if="form.select.second">
                    <li v-for="(item, index) in form.select.second" :class="{cur: item.cur}" :key="index" :data-seach="item.data">{{item.name}}</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <input name="keywords" style="width: 660px;" v-model="form.keywords" class="app-finput" type="text" :placeholder="form.placeholder">
          <input class="app-seach" :type="form.type" :value="form.submit">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  // 搜索信息区域
  import LogoImg from '@/assets/img/logo-2x.png'
  export default {
    name: 'public-seach',
    props: {hide: Boolean},
    data () {
      return {
        logoImg: LogoImg,
        form: {
          logo: {url: '#', target: '_blank', src: '/src/assets/img/logo-2x.png'},
          select: {
            show: false,
            title: '项目类型',
            first: [{cur: true, name: '不限', data: ''}, {cur: false, name: '科研项目', data: ''}, {cur: false, name: '横向项目', data: ''}, {cur: false, name: '企业项目', data: ''}, {cur: false, name: '大创项目', data: ''},
              {cur: false, name: '企业招聘', data: ''}, {cur: false, name: '实习项目对接', data: ''}]
          },
          type: 'submit',
          keywords: '',
          placeholder: '搜索职位、项目、公司',
          submit: '搜索'
        },
        jobs: {
          title: '热门职位:',
          list: [
            {target: '_blank', url: '#', name: 'Java'}, {target: '_blank', url: '#', name: 'PHP'}, {target: '_blank', url: '#', name: 'C++'},
            {target: '_blank', url: '#', name: 'web前端'}, {target: '_blank', url: '#', name: 'iOS'}, {target: '_blank', url: '#', name: 'Android'},
            {target: '_blank', url: '#', name: '产品经理'}, {target: '_blank', url: '#', name: 'UI设计师'}, {target: '_blank', url: '#', name: '产品运营'}
          ]
        }
      }
    },
    methods: {
      showOrHide () {
        this.form.select.show = !this.form.select.show
      }
    }
  }
</script>

<style>
 .city-box{width: auto;}
</style>
