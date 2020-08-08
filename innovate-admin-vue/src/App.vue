<template>
  <transition name="fade">
    <router-view></router-view>
  </transition>
</template>

<script>
  export default {
    created () {
      document.body.removeChild(document.getElementById('Loading'))
    }
  }
</script>
