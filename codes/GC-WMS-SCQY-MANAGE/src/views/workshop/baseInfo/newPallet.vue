<template>
  <div class="app-container">
    <!-- 查询 -->
    <div class="filter-container">
      <el-select v-model="queryForm.deptId" class="filter-item" placeholder="部门" clearable>
        <el-option v-for="item in workShopOpt" :key="item.val" :label="item.name" :value="item.val" />
      </el-select>
      <el-select v-model="queryForm.securitiesId" class="filter-item" placeholder="品名" clearable>
        <el-option
          v-for="item in securitiesOpt"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
      <el-date-picker
        v-model="queryForm.startDate"
        class="filter-item"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="开始日期"
        clearable
      />
      <el-date-picker
        v-model="queryForm.endDate"
        class="filter-item"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="结束日期"
        clearable
      />
      <el-select v-model="queryForm.locWareId" class="filter-item" placeholder="区域" clearable>
        <el-option v-for="item in wareOpt" :key="item.val" :label="item.name" :value="item.val" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search">查 询</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-refresh" @click="resetQuery">重 置</el-button>
    </div>
    <!-- 功能 -->
    <!-- <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-download" @click="handleExp">导 出</el-button>
    </div> -->
    <!-- 新拍记录列表 -->
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
      <!-- <el-table-column type="selection" align="center" prop="id" /> -->
      <el-table-column label="NO" prop="id" align="center" width="40" type="index" />
      <el-table-column label="部门" align="center" prop="dept" />
      <el-table-column label="品名" align="center" prop="securities" />
      <el-table-column label="编号" align="center" prop="serialNumber" />
      <el-table-column label="原冠字号" align="center" prop="oldCrownBetween" min-width="220" />
      <el-table-column label="原卡号" align="center" prop="oldRfid" min-width="120" />
      <el-table-column label="分拆冠字号" align="center" prop="crownBetween" min-width="240" />
      <el-table-column label="新卡号" align="center" prop="rfid" min-width="100" />
      <el-table-column label="区域" align="center">
        <template slot-scope="{ row }">
          <!-- <span v-if="row.status === 12">已发货</span> -->
          <span>{{ getDictNameByVal('locationOpt', row.locWareId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="origin_type">
        <template slot-scope="{ row }">
          <!-- originTypeMap[row.originType]  -->
          <span>{{  row.originType===4?'整拍登记':'拆拍登记'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" align="center" prop="createTime" min-width="140" />
      <el-table-column label="操作人" align="center" prop="createBy" />
      <el-table-column label="复核人" align="center" prop="checker" />
    </el-table>
    <!-- 分页 -->
    <pagination
      v-show="pageTotal>0"
      :total="pageTotal"
      :page.sync="page"
      :limit.sync="rows"
      @pagination="getPageList"
    />
  </div>
</template>

<script>
import { pageBaseInfoNewPallet } from '@/api/workshop/baseInfo'
import Pagination from '@/components/Pagination'
import Mixin from '@/utils/mixin.js'
import Dictionaries from '@/utils/dictionaries.js'

export default {
  name: 'BaseInfo',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'danger',
        3: 'info',
        0: 'danger'
      }
      return statusMap[status]
    }
  },
  mixins: [Mixin, Dictionaries],
  data() {
    return {
      listApi: pageBaseInfoNewPallet, // 列表
      originTypeMap : {
        2: '拆拍登记',
        4: '整拍登记'
      }
    }
  },
  created() {
    this.getDictOptList('workShopOpt') // 车间部门选项
    this.getDictOptList('wareOpt') // 总库选项
    this.getDictOptList('locationOpt') // 总库选项
    this.listSecuritiesOpt() // 品名选项
    this.getPageList()
  },
  mounted() {},
  methods: {
    // methods-end
  }
}
</script>

<style scoped lang="scss">
</style>
