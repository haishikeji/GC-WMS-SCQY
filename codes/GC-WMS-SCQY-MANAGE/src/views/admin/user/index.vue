<template>
  <div style="display: flex">
    <!-- 租户树 -->
    <TenantTree />
    <div class="app-container" :class="rightDiv">
      <div class="filter-container">
        <!-- <el-select v-model="queryForm.tenantId" class="filter-item" placeholder="所属租户" clearable>
          <el-option v-for="item in tenantOpt" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>-->
        <el-input
          v-model="queryForm.name"
          placeholder="姓名"
          class="filter-item"
          clearable
          @keyup.enter.native="search"
        />
        <el-input
          v-model="queryForm.mobile"
          placeholder="手机号"
          class="filter-item"
          clearable
          @keyup.enter.native="search"
        />
        <el-select v-model="queryForm.status" placeholder="状态" class="filter-item" clearable>
          <el-option
            v-for="item in $commonArray.options['statusOpt']"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search">查 询</el-button>
      </div>
      <div class="filter-container">
        <el-button class="filter-item" type="success" icon="el-icon-plus" @click="handleAdd()">新 增</el-button>
      </div>

      <el-table
        v-loading="listLoading"
        row-key="id"
        :data="tableData"
        border
        stripe
        fit
        highlight-current-row
        :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      >
        <el-table-column label="NO" prop="id" align="center" width="40" type="index" />
        <el-table-column label="所属租户" align="center">
          <template slot-scope="{ row }">
            <span>{{ getOptNameById('tenantOpt', row.tenantId) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="姓名" align="center" prop="realName" />
        <el-table-column label="手机号" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.mobile }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户名" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" min-width="60">
          <template slot-scope="{ row }">
            <el-tag
              style="cursor: pointer"
              :type="row.status | statusFilter"
              @click="handleStatus(row)"
            >{{ $commonArray.methods.getArrayVal('statusOpt', row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="roleName"
          label="角色"
          align="center"
          min-width="120"
          show-overflow-tooltip
        />
        <el-table-column prop="createTime" label="创建时间" align="center" min-width="120">
          <template slot-scope="{ row }">
            <span>{{ row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" min-width="100">
          <template slot-scope="{ row }">
            <el-link :underline="false" type="primary" @click="handleResetPwd(row)">重置密码</el-link>
            <el-divider direction="vertical" />
            <el-link :underline="false" type="primary" @click="handleUpdate(row)">编辑</el-link>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="pageTotal > 0"
        :total="pageTotal"
        :page.sync="page"
        :limit.sync="rows"
        @pagination="getPageList"
      />
      <Edit
        v-if="editShow"
        ref="edit"
        :edit-show="editShow"
        :edit-type="editType"
        :edit-id="editId"
        :tenant-opt="tenantOpt"
        :tenant-id="queryForm.tenantId"
      />
    </div>
  </div>
</template>

<script>
import { adminListUser, resetPwd, statusUser } from '@/api/sys/user'
import Edit from './components/edit.vue'
import TenantTree from '../tenantTree.vue'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

import Mixin from '@/utils/mixin.js'
import Dictionaries from '@/utils/dictionaries.js'

export default {
  name: 'User',
  components: { Pagination, Edit, TenantTree },
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
  mixins: [Mixin, Dictionaries],
  data() {
    return {
      listApi: adminListUser,
      statusApi: statusUser,
      user: {
        id: '',
        name: ''
      },
      password: '_abc000123_'
    }
  },
  computed: {
    rolesData() {
      return this.roles
    }
  },
  created() {
    this.listTenantOpt()
  },
  methods: {
    //     inputPattern: /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{6,16}$/,
    //     inputErrorMessage: '请输入6-16位数字与字母组合密码',
    //     inputPlaceholder: '请输入6-16位数字与字母组合密码',
    async handleResetPwd(row) {
      this.$prompt('新密码', '重置密码', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        inputValue: 'abc000123'
      }).then(({ value }) => {
        this.resetPwdData(row.id, value)
      })
    },
    resetPwdData(id, pwd) {
      resetPwd({ id: id, password: pwd }).then(res => {
        if (res.code === 0) {
          this.$message.success(res.msg)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.lugDisplay {
  display: true;
}
.add-dialog .el-dialog__body {
  padding: 5px 5px;
}
::v-deep .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #13ce66;
  color: #fff;
}
</style>
