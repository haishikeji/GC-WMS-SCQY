<template>
  <div class="applyfor-edit">
    <el-drawer
      :with-header="false"
      :visible.sync="editShow"
      direction="rtl"
      :wrapper-closable="false"
    >
      <div class="drawer-header" :style="{'color':theme}">
        <span class="con">{{ name }}角色用户</span>
        <i class="el-icon-close drawer-icon" @click="handleClose" />
      </div>
      <div class="drawer_body">
        <el-table
          :data="userList"
          :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
          border
        >
          <el-table-column
            label="NO"
            prop="id"
            align="center"
            width="40"
            type="index"
          />
          <el-table-column
            align="center"
            prop="name"
            label="名称"
            min-width="150"
          />
          <el-table-column
            label="操作"
            align="center"
            min-width="100"
          >
            <template slot-scope="{ row }">
              <el-button type="text" @click="handleDeleteUser(row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="drawer-bootom-button">
        <el-button type="danger" @click="handleClose">关闭</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
// import { deepClone } from "@/utils"
import { listRoleUser, deleteUserRole } from '@/api/sys/role'

export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editId', 'name'],
  data() {
    return {
      userList: []
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    }
  },
  created() {
    this.getChildList()
  },
  methods: {
    // 获取用户信息
    async getChildList() {
      const { retObj } = await listRoleUser({ id: this.editId })
      this.userList = retObj
    },
    // 删除角色下的用户
    async handleDeleteUser(row) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        const { code } = await deleteUserRole({
          userId: row.id,
          roleId: this.editId
        })
        if (code === 0) {
          this.getChildList()
        }
      })
    },
    // 关闭窗口
    handleClose() {
      this.$parent.roleUserShow = false
    }
    // end
  }
}
</script>

<style lang="scss" scoped>
.drawer_body {
  height:calc(100vh - 100px);
  overflow-y: scroll;
  padding: 20px 30px;
}
.drawer-bootom-button {
  position: absolute;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  left: 0;
  background: #fff;
  border-radius: 0 0 2px 2px;
  z-index: 100;
}
.drawer-header {
  font-size: 16px;
  // color: #14889a;
  border-bottom: 1px solid #e8e8e8;
  padding: 16px 16px;
  .drawer-icon {
    float: right;
  }
}
::v-deep .el-icon-close:before {
  color: #000;
}
::v-deep .el-dialog__body {
  max-height: 420px;
  overflow-y: auto;
}

::v-deep .el-checkbox__label {
  padding-left: 1px;
  font-size: 12px;
}
::v-deep .el-tree-node__content {
  font-size: 12px;
}
</style>
