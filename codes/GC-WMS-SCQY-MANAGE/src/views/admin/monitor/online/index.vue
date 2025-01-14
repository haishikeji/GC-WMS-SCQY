<template>
  <div class="app-container">
    <!-- <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="登录地址" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="请输入登录地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>

    </el-form> -->

    <el-table
      v-loading="listLoading"
      :data="tableData"
      border
      stripe
      fit
      highlight-current-row
      style="width: 100%;"
      :header-cell-style="{background:'#eef1f6',color:'#606266'}"
    >
      <el-table-column label="序号" prop="id" align="center" min-width="50" type="index" />
      <el-table-column label="会话编号" align="center" prop="token" min-width="150" />
      <el-table-column label="用户名" align="center" prop="username" :show-overflow-tooltip="true" />
      <el-table-column label="真实姓名" align="center" prop="realName" />
      <el-table-column label="登录ip" align="center" prop="ip" :show-overflow-tooltip="true" />
      <el-table-column label="登录时间" align="center" prop="loginTime" />
      <!-- <el-table-column label="浏览器" align="center" prop="browser" /> -->

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-link type="danger" icon="el-icon-delete" :underline="false" @click="handleForceLogout(scope.row)">强退</el-link>
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

  </div>
</template>

<script>
import { list, forceLogout } from '@/api/monitor/online'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import Mixin from '@/utils/mixin.js'

export default {
  name: 'Online',
  components: { Pagination },
  mixins: [Mixin],
  data() {
    return {
      listApi: list
    }
  },
  created() {
    this.getPageList()
  },
  methods: {

    /** 强退按钮操作 */
    async handleForceLogout(row) {
      this.$confirm('是否确认强退名称为' + row.realName + '的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        const { code, msg } = await forceLogout(row.token)
        if (code === 0) {
          this.getPageList()
          this.$message.success(msg)
        }
      })
    }
  }
}
</script>
