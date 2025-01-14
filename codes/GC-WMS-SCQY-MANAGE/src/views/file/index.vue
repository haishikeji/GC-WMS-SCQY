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
    <div class="filter-container">
      <el-button class="filter-item" type="success" icon="el-icon-plus" @click="handleUpload"
        >添加</el-button
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
      <el-table-column prop="createTime" label="上传时间" align="center" min-width="60">
        <template slot-scope="{ row }">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="上传人" align="center" min-width="40">
        <template slot-scope="{ row }">
          <span>{{ row.createBy }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" min-width="80" show-overflow-tooltip>
        <template slot-scope="{ row }">
          <span>{{ row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        min-width="70"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{ row }">
          <span>
            <el-link type="primary" :underline="false" @click="handleUpload(row)">重新上传</el-link>
            <el-divider direction="vertical" />
            <el-link type="primary" :underline="false" @click="handleUpdate(row)">编辑</el-link>
            <el-divider direction="vertical" />
            <el-link type="danger" :underline="false" @click="handleDelete(row)">删除</el-link>
            <!-- <el-divider direction="vertical" />
            <el-link type="primary" :underline="false" @click="download1(row)">下载</el-link> -->
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
    <!-- 编辑 -->
    <Edit
      v-if="editShow"
      ref="edit"
      :edit-show="editShow"
      :edit-type="editType"
      :edit-id="editId"
    />

    <el-dialog title="上传文件" :visible.sync="dialogFormVisible" custom-class="customWidth_30">
      <el-upload
        ref="upload"
        :data="uploadData"
        :action="uploadUrl"
        :headers="token"
        :on-change="fileChange"
        :show-file-list="true"
        :before-upload="beforeAvatarUpload1024"
        :on-success="uploadSuccess"
        :auto-upload="true"
        :file-list="fileList"
        class="upload-demo"
        style="text-align: center"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip" style="margin-bottom: 20px">
          请选择文件，不超过1024Mb"
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="dialogFormVisible = false"> 取消 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFile, addFile, updateFile, getFile, delFile } from '@/api/file'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { getToken } from '@/utils/auth'
import Mixin from '@/utils/mixin.js'
import { downloadFile } from '@/utils/download'
import Edit from './components/edit'

export default {
  name: 'Download',
  components: { Pagination, Edit },
  mixins: [Mixin],
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
  data() {
    return {
      listApi: listFile, // 列表
      delApi: delFile, // 删除
      token: { token: '' },
      uploadData: {},
      isDisabled: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新增'
      },
      fileList: [],
      uploadUrl: `${window.Glod.baseURLConfig}/file/imp`
    }
  },
  computed: {},
  created() {
    console.log(this.uploadUrl, 'uploadUrl')
    this.token.token = getToken()
    this.getPageList()
  },
  methods: {
    download1(row) {
      downloadFile(window.Glod.baseURLConfig + row.url, row.name)
    },
    handleUpload(row) {
      if (row.id) {
        this.uploadData.id = row.id
      } else {
        this.fileList = []
        this.uploadData.id = 0
      }
      this.dialogFormVisible = true
    },
    handleFilter() {
      this.queryForm.page = 1
      this.getPageList()
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    uploadSuccess(response) {
      if (response.code === -1) {
        this.$message.error(response.msg)
      } else if (response.code === 0) {
        this.$message.success(response.msg)
        this.cancelUpload()
        this.getPageList()
      }
    },
    cancelUpload() {
      this.uploadData = {}
      this.fileList = []
      this.dialogFormVisible = false
    },
    fileChange(file) {
      this.fileList = []
      //   this.fileList.splice(1)
      this.fileList.push(file)
    },
    beforeAvatarUpload1024(file) {
      const isLt2M = file.size / 1024 / 1024 < 1024
      if (!isLt2M) {
        this.$message.success('上传文件大小不能超过 1024MB!')
      }
      return isLt2M
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
