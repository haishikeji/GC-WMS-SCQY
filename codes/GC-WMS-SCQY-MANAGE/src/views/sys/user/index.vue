<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="queryForm.realName"
        placeholder="姓名"
        class="filter-item"
        clearable
        @keyup.enter.native="search"
      />
      <el-select
        v-model="queryForm.deptId"
        placeholder="部门"
        class="filter-item"
      >
        <el-option
          v-for="item in deptOpt"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
      <el-select
        v-model="queryForm.status"
        placeholder="状态"
        class="filter-item"
      >
        <el-option
          v-for="item in $commonArray.options['statusOpt']"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="search"
      >查 询</el-button>
    </div>
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
      <el-table-column
        label="NO"
        prop="id"
        align="center"
        width="40"
        type="index"
      />
      <el-table-column label="姓名" align="center" prop="realName" />
      <el-table-column label="部门" align="center" prop="dept">
      <template slot-scope="{ row }">
          <span>{{ getOptNameById('deptOpt', row.deptId) }}</span>
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
          >
            {{ $commonArray.methods.getArrayVal('statusOpt', row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色" align="center" min-width="120" />
      <el-table-column prop="createTime" label="创建时间" align="center" min-width="120">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        min-width="100"
      >
        <template slot-scope="{ row }">
          <el-link
            :underline="false"
            type="primary"
            @click="handleResetPwd(row)"
          >重置密码</el-link>
          <el-divider direction="vertical" />
          <el-link
            :underline="false"
            type="primary"
            @click="handleUpdate(row)"
          >编辑</el-link>
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
      :role-opt="roleOpt"
      :dept-opt="deptOpt"
    />
  </div>
</template>

<script>
import {
  listUser, resetPwd, statusUser
} from '@/api/sys/user'
import Edit from './components/edit.vue'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

import Mixin from '@/utils/mixin.js'
import Dictionaries from '@/utils/dictionaries.js'

export default {
  name: 'User',
  components: { Pagination, Edit },
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
      listApi: listUser,
      statusApi: statusUser,
      user: {
        id: '',
        name: ''
      },
      password: '_abc000123_',
      roleCheckedKeys: []
    }
  },
  computed: {
    rolesData() {
      return this.roles
    }
  },
  created() {
    this.getRoleOptList()
    this.getDeptOptList()
    this.getPageList()
  },
  methods: {
    // inputPattern: /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{6,16}$/,
    // inputErrorMessage: '请输入6-16位数字与字母组合密码',
    //     inputPlaceholder: '请输入6-16位数字与字母组合密码',
    async handleResetPwd(row) {
      this.$prompt('新密码', '重置密码', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        inputValue: 'abc000123'
      }).then(async({ value }) => {
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
<style>
.lugDisplay {
  display: true;
}
.add-dialog .el-dialog__body {
  padding: 5px 5px;
}
</style>
