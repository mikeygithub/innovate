<template>
  <el-dialog title="testref" :visible.sync="visible" @close="closeDialog()">
    <p>testref</p>
    <div>{{msg}}</div>
  </el-dialog>
</template>

<script>
    export default {
      data () {
        return {
          visible: true,
          msg: 'rrr',
          jj: 'sss'
        }
      },
      methods: {
        closeDialog () {
          // this.$emit('dd')
          this.$store.commit('dd')
          this.$message.success('dddd' + this.msg)
        }
      }
    }
</script>

<style scoped>

</style>
