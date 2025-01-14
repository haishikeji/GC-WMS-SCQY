<template>
  <div :class="leftDiv">
    <!-- 树显示/隐藏按钮 -->
    <el-tooltip class="item" effect="light" :content="treeText" placement="right">
      <el-button :type="butType" :icon="butIcon" circle :title="treeText" @click="toggleTree" />
    </el-tooltip>
    <!-- 租户树 -->
    <transition name="el-zoom-in-center"
      >
      <el-tree
        v-show="showTree"
        ref="tree"
        :data="treeData"
        :props="defaultProps"
        :default-expand-all="true"
        highlight-current
        node-key="id"
        :expand-on-click-node="false"
        :current-node-key="defTenantId"
        class="lug-tree"
        @node-click="handleNodeClick"
      />
    </transition>
  </div>
</template>

<script>
import { listOptTenant } from '@/api/sys/tenant'

export default {
  name: 'TenantTree',
  data() {
    return {
      butIcon: 'el-icon-d-arrow-left', // 切换树按钮图标
      butType: 'success', // 切换树按钮类型
      treeText: '收起租户树', // 切换树按钮文本
      leftDiv: 'tenant-div', // 左侧div类名
      showTree: true, // 切换树变量
      treeData: [], // 租户树数据
      defTenantId: 1, // 默认选中节点id
      defaultProps: {
        children: 'children',
        label: function (a, b) {
          return a.name
        }
      }
    }
  },
  created() {
    // 初始加载租户树数据
    this.listTenantTree()
  },
  methods: {
    // 加载租户树数据
    async listTenantTree() {
      const { retObj } = await listOptTenant()
      this.tenantOpt = retObj
      // 构造父级
      const parent = {
        id: 0,
        name: '全部租户',
        children: retObj
      }
      this.treeData.push(parent)
      // 设备查询条件租户id为默认选中id
      this.$parent.queryForm.tenantId = this.defTenantId
      this.$parent.getPageList()
    },
    // 树节点单击事件
    handleNodeClick(data) {
      this.$parent.queryForm = {}
      this.$parent.queryForm.tenantId = data.id
      this.$parent.getPageList()
    },
    // 切换树显示/隐藏
    toggleTree() {
      if (this.showTree) {
        this.showTree = false
        this.leftDiv = 'tenant-div-hide'
        // 右侧div宽度类名
        this.$parent.rightDiv = 'width-100'
        this.treeText = '展开租户树'
        this.butType = 'primary'
        this.butIcon = 'el-icon-d-arrow-right'
      } else {
        this.showTree = true
        this.leftDiv = 'tenant-div'
        this.$parent.rightDiv = 'width-80'
        this.treeText = '收起租户树'
        this.butType = 'success'
        this.butIcon = 'el-icon-d-arrow-left'
      }
    }
  }
}
</script>
<style lang="scss" scoped>
// 树选中节点背景颜色，字体颜色
::v-deep .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #13ce66;
  color: #fff;
}
</style>
