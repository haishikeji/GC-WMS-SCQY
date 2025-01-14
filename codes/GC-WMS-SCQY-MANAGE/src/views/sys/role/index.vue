<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        type="success"
        icon="el-icon-plus"
        @click="handleAdd()"
      >
        新 增
      </el-button>
    </div>
    <!-- 列表 -->
    <el-table
      v-loading="listLoading"
      :data="tableData"
      row-key="id"
      class="table-width"
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
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column
        align="center"
        label="状态"
        min-width="40"
      >
        <template slot-scope="{row}">
          <el-tag
            style="cursor: pointer"
            :type="row.status | statusFilter"
            @click="handleStatus(row)"
          >
            {{ $commonArray.methods.getArrayVal('statusOpt', row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="描述"
        min-width="140"
        prop="remark"
        show-overflow-tooltip
      />
      <el-table-column align="center" label="操作" min-width="120">
        <template slot-scope="{ row }">
          <el-link type="primary" :underline="false" @click="handleUpdate(row)">编辑</el-link>
          <el-divider direction="vertical" />
          <el-link
            type="primary"
            :underline="false"
            @click="handleRoleUser(row)"
          >查看用户</el-link>
          <el-divider direction="vertical" />
          <el-link
            type="success"
            :underline="false"
            @click="handleRoleMenu(row)"
          >配置权限</el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination
      v-show="pageTotal > 0"
      :total="pageTotal"
      :page.sync="page"
      :limit.sync="rows"
      class="table-width"
      @pagination="getPageList"
    />
    <!-- 新增/编辑 -->
    <Edit
      v-if="editShow"
      ref="edit"
      :edit-show="editShow"
      :edit-type="editType"
      :edit-id="editId"
    />
    <!-- 配置权限 -->
    <AuthRole
      v-if="authRoleShow"
      :edit-show="authRoleShow"
      :edit-id="editId"
      :name="name"
    />
    <!-- 角色用户 -->
    <RoleUser
      v-if="roleUserShow"
      :edit-show="roleUserShow"
      :edit-id="editId"
      :name="name"
    />
  </div>
</template>

<script>

import {
  listRole,
  statusRole
} from '@/api/sys/role'
import Edit from './components/edit'
import AuthRole from './components/authRole'
import RoleUser from './components/roleUser'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

import Mixin from '@/utils/mixin.js'

export default {
  name: 'Role',
  components: { Pagination, Edit, AuthRole, RoleUser },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'danger',
        0: 'danger'
      }
      return statusMap[status]
    }
  },
  mixins: [Mixin],
  data() {
    return {
      listApi: listRole, // 列表
      statusApi: statusRole,
      roleUserShow: false,
      authRoleShow: false,
      name: ''
    }
  },
  created() {
    this.getPageList()
  },
  methods: {
    // 查看角色用户
    handleRoleUser(row) {
      this.roleUserShow = true
      this.editId = row.id
      this.name = row.name
    },
    // 角色配置权限
    handleRoleMenu(row) {
      this.authRoleShow = true
      this.editId = row.id
      this.name = row.name
    }
    // end
  }
}
</script>

<style lang="scss" scoped>
.table-width {
  width: 90%;
}
</style>
