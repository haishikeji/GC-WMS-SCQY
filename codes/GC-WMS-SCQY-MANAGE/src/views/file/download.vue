<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="queryForm.name"
        placeholder="文档名称"
        style="width: 150px"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter"
        >查 询</el-button
      >
    </div>
    <el-table
      ref="table"
      v-loading="listLoading"
      row-key="id"
      :data="tableData"
      border
      stripe
      fit
      highlight-current-row
      style="width: 100%"
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
    >
      <el-table-column
        label="NO"
        prop="id"
        sortable="custom"
        align="center"
        min-width="40"
        type="index"
      />
      <el-table-column label="文件名称" align="center" min-width="100">
        <template slot-scope="{ row }">
          <span
            ><el-link :underline="false" @click="download1(row)">{{ row.name }}</el-link></span
          >
        </template>
      </el-table-column>
      <el-table-column label="文件大小" align="center" min-width="40">
        <template slot-scope="{ row }">
          <span>{{ row.fileSize }}</span>
        </template>
      </el-table-column>

      <el-table-column label="备注" align="center" min-width="80" show-overflow-tooltip>
        <template slot-scope="{ row }">
          <span>{{ row.remark }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="上传时间" align="center" min-width="40">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="createTime" label="上传人" align="center" min-width="40">
        <template slot-scope="{ row }">
          <span>{{ row.createBy }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        label="操作"
        align="center"
        min-width="40"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{ row }">
          <span>
            <el-link type="primary" :underline="false" @click="download1(row)">下载</el-link>
          </span>
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
import { listFile } from '@/api/file'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import Mixin from '@/utils/mixin.js'
import { downloadFile } from '@/utils/download'

export default {
  name: 'Download',
  components: { Pagination },
  mixins: [Mixin],
  data() {
    return {
      listApi: listFile // 列表
    }
  },
  computed: {},
  created() {
    this.getPageList()
  },
  methods: {
    handleFilter() {
      this.queryForm.page = 1
      this.getPageList()
    },
    download1(row) {
      downloadFile(window.Glod.baseURLConfig + row.url, row.name)
    }
  }
}
</script>
<style>
</style>
  