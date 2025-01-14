<template>
  <div class="app-container">
    <!-- 查询 -->
    <div class="filter-container">
      <!-- <el-select v-model="queryForm.deptId" class="filter-item w80" placeholder="部门" clearable>
        <el-option v-for="item in workShopOpt" :key="item.val" :label="item.name" :value="item.val" />
      </el-select> -->
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
      <!-- <el-select v-model="queryForm.locWareId" class="filter-item" placeholder="位置" clearable>
        <el-option
          v-for="item in locationOpt"
          :key="item.val"
          :label="item.name"
          :value="item.val"
        />
      </el-select> -->
      <el-select v-model="queryForm.qualityStatus" class="filter-item" placeholder="质量" clearable>
        <el-option v-for="item in qualityOpt" :key="item.val" :label="item.name" :value="item.val" />
      </el-select>

      <el-input v-model="queryForm.crown" class="filter-item" placeholder="单拍编号" clearable />

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search">查 询</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-refresh" @click="resetQuery">重 置</el-button>
    </div>
    <!-- 功能 -->
    <!-- <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-download" @click="handleExp">导 出</el-button>
    </div> -->
    <!-- rfid基础信息登记表列表 -->
    <el-table
      v-loading="listLoading"
      row-key="id"
      :data="tableData"
      border
      stripe
      fit
      highlight-current-row
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      height="calc(100vh - 208px)"
    >
      <!-- <el-table-column type="selection" align="center" prop="id" /> -->
      <el-table-column label="NO" prop="id" align="center" width="40" type="index" />
      <el-table-column label="部门" align="center" prop="dept" />
      <el-table-column label="品名" align="center" prop="securities" />
      <el-table-column label="编号" align="center" prop="serialNumber" />
      <el-table-column label="冠字号" align="center" min-width="240">
        <template slot-scope="{ row }">
          <span
            v-if="row.crownFrom"
          >{{ row.crownPrefix + '-' + row.crownFrom }} 至 {{ row.crownPrefix + '-' + row.crownTo }}</span>
          <span v-if="row.child.length > 0">
            <br>
            <span
              v-for="item in row.child"
              :key="item.id"
            >{{ item.crownPrefix + '-' + item.crownFrom }} 至 {{ item.crownPrefix + '-' + item.crownTo }}</span>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="皮重(kg)" align="center" prop="tareWeight" />
      <el-table-column label="毛重(kg)" align="center" prop="grossWeight" />
      <el-table-column label="称重人" align="center" prop="weightor" min-width="60" />
      <el-table-column label="打印时间" align="center" prop="printTime" min-width="140">
        <template slot-scope="{ row }">
          {{ row.weightDate + ' ' + row.weightTime}}
        </template>
      </el-table-column>
      <el-table-column label="卡号" min-width="110" align="center" prop="rfid" />
      <el-table-column label="位置" min-width="90" align="center">
        <template slot-scope="{ row }">
          <!-- <span v-if="row.status === 12">已发货</span> -->
          <span>{{ getDictNameByVal('locationOpt', row.locWareId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质量" align="center" min-width="60">
        <template slot-scope="{ row }">
          <el-tag
            :type="row.qualityStatus | statusFilter"
          >{{ getDictNameByVal('qualityOpt', row.qualityStatus===0?2:row.qualityStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="60">
        <template slot-scope="{ row }">
          <el-link type="primary" :underline="false" @click="handleUpdate(row)">编辑</el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination
      v-show="pageTotal > 0"
      :total="pageTotal"
      :page.sync="page"
      :limit.sync="rows"
      @pagination="getPageList"
    />
    <!-- 编辑质量 -->
    <Edit
      v-if="editShow"
      ref="edit"
      :edit-show="editShow"
      :edit-type="editType"
      :edit-id="editId"
      :quality-opt="qualityOpt"
    />
  </div>
</template>

<script>
import { pageBaseInfo1, statusBaseInfo } from '@/api/workshop/baseInfo'
import Edit from './components/editQuality'
import Pagination from '@/components/Pagination'
import Mixin from '@/utils/mixin.js'
import Dictionaries from '@/utils/dictionaries.js'

export default {
  name: 'BaseInfo',
  components: { Pagination, Edit },
  mixins: [Mixin, Dictionaries],
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'warning',
        3: 'danger',
        0: 'warning'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      listApi: pageBaseInfo1, // 列表
      statusApi: statusBaseInfo // 状态
    }
  },
  created() {
    this.getDictOptList('workShopOpt') // 车间部门选项
    this.listSecuritiesOpt() // 品名选项
    this.getDictOptList('locationOpt') // 位置选项
    this.getDictOptList('qualityOpt') // 质量选项
    this.getPageList()
  },
  mounted() {},
  methods: {
    // methods-end
  }
}
</script>

<style scoped lang="scss">
::v-deep .filter-container .el-input {
    width: 130px;
}
</style>
