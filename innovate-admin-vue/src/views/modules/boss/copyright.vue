<template>
  <div class="app-wrap comp-info mar-t30" v-if="!hide">
    <div class="app-w clearfix">
      <div class="footer-about clearfix">
        <dl class="fl-l" v-if="compaby">
          <dt v-if="compaby.title">{{compaby.title}}</dt>
          <dd v-if="compaby.list" v-for="(item, index) in compaby.list" :key="index"><a :href="item.url" :target="item.target || '_blank' ">{{item.name}}</a></dd>
        </dl>
        <dl class="fl-l" v-if="help">
          <dt v-if="help.title">{{help.title}}</dt>
          <dd v-if="help.list" v-for="(item, index) in help.list" :key="index"><a :href="item.url" :target="item.target || '_blank'">{{item.name}}</a></dd>
        </dl>
        <dl class="fl-l" v-if="address">
          <dt v-if="address.title">{{address.title}}</dt>
          <dd v-if="address.list" v-for="(item, index) in address.list" :key="index"><a :href="item.url" :target="item.target || '_blank'">{{item.name}}</a></dd>
        </dl>
        <dl class="fl-r comp-infos" v-if="link">
          <dt v-if="link.title"><p>{{link.title}}</p></dt>
          <dd v-if="link.list" v-for="(item, index) in link.list" :key="index"><p>{{item.name}}</p></dd>
        </dl>
      </div>
    </div>
    <div class="app-w">
      <div class="copyright">
        <span>{{copyright.copyright}}</span>
        <span>{{copyright.beian}}</span>
        <span v-if="copyright.zhizhao"><a :href="copyright.zhizhao.url || '#'" :target="copyright.zhizhao || '_blank'">{{copyright.zhizhao.name}}</a></span>
        <span v-if="copyright.gonganju"><a :href="copyright.gonganju.url || '#'" :target="copyright.gonganju || '_blank'">{{copyright.gonganju.name}}</a></span>
        <span v-if="copyright.alams"><a :href="copyright.alams.url || '#'" :target="copyright.alams || '_blank'">{{copyright.alams.name}}</a></span>
      </div>
    </div>
  </div>
</template>

<script>
  // 版权信息相关
  export default {
    props: {hide: Boolean},
    data: function () {
      return {
        compaby: {
          title: '企业服务',
          // eslint-disable-next-line standard/object-curly-even-spacing
          list: [{ name: '职位搜索', url: '#', target: '_blank'}, { name: '新闻资讯', url: '#', target: '_blank'}]
        },
        help: {
          title: '使用与帮助',
          list: [{name: '用户协议与隐私政策', url: '#', target: '_blank'}, {name: '防骗指南', url: '#', target: '_blank'}, {name: '职位发布规则', url: '#', target: '_blank'}, {name: '使用帮助', url: '#', target: '_blank'}]
        },
        address: {
          title: '联系创新创业学院',
          list: [{name: '梧州学院创新创业学院', url: '#', target: '_blank'}, {name: '地址:梧州市万秀区梧州学院发展中心负一楼', url: '#', target: '_blank'},
            {name: '邮政编码：543000', url: '#', target: '_blank'}, {name: '联系邮箱：www.wzxycxcyjy@163.com', url: '#', target: '_blank'}]
        },
        link: {
          title: '梧州学院校企合作网',
          list: [{name: '工作日 8:00 - 22:00'}, {name: '休息日 9:30 - 18:30'}]
        },
        copyright: {
          copyright: '版权所有©梧州学院',
          // eslint-disable-next-line standard/object-curly-even-spacing
          beian: '桂ICP备05000941号',
          // eslint-disable-next-line standard/object-curly-even-spacing
          gonganju: { name: '桂公网安备 45040302000045号', url: '#', target: '_blank'}
        }
      }
    }
  }
</script>

<style scoped>

</style>
