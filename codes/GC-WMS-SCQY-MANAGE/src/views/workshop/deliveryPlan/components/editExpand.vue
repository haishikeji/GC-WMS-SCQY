<template>
  <div class="app-container">
    <el-table :data="tableData" border stripe fit highlight-current-row>
      <el-table-column label="NO" prop="id" align="center" width="40" type="index" />
      <el-table-column label="编号" align="center" prop="serialNumber" />
      <el-table-column label="冠字号" align="center" prop="crownBetweenStr" min-width="240" />
      <el-table-column label="皮重(kg)" align="center" prop="tareWeight" />
      <el-table-column label="毛重(kg)" align="center" prop="grossWeight" />
      <el-table-column label="称重人" align="center" prop="weightor" />
      <el-table-column label="打印时间" align="center" prop="printTime" min-width="120" />
      <el-table-column label="卡号" align="center" prop="rfid" min-width="100" />
    </el-table>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['tableData'],
  data() {
    return {}
  },
  created() {},
  mounted() {},
  methods: {
    // methods-end
  }
}
</script>

<style scoped lang="scss">
</style>

