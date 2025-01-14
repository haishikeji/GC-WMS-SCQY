<template>
  <div class="app-container">
    <!-- 查询 -->
    <div class="filter-container">
      <el-input v-model="queryForm.orderNo" class="filter-item" placeholder="发货单号" clearable />
      <el-select v-model="queryForm.securitiesId" class="filter-item" placeholder="品名" clearable>
        <el-option
          v-for="item in securitiesOpt"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
      <el-select v-model="queryForm.addressId" class="filter-item" placeholder="目的地" clearable>
        <el-option
          v-for="item in addressOpt"
          :key="item.val"
          :label="item.name"
          :value="item.val"
        />
      </el-select>
      <el-date-picker
        v-model="queryForm.deliveryTime"
        class="filter-item"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="发货日期"
        clearable
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search"
        >查 询</el-button
      >
      <el-button class="filter-item" type="primary" icon="el-icon-refresh" @click="resetQuery"
        >重 置</el-button
      >
    </div>
    <!-- 功能 -->
    <!-- <div class="filter-container">
      <el-button class="filter-item" type="success" icon="el-icon-plus" @click="handleAddLocal()"
        >新 增</el-button
      >
    </div> -->
    <!-- 发货单主表列表 -->
    <el-table
      ref="table"
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
      <el-table-column label="品名" align="center" min-width="60" prop="securities" />
      <el-table-column label="发货单号" align="center" min-width="80" prop="orderNo" />
      <el-table-column label="合计拍数-计划" align="center" min-width="120" prop="planPar" />
      <el-table-column label="合计箱数" align="center" min-width="80" prop="planTotalBox" />
      <el-table-column label="零箱数" align="center" min-width="60" prop="planBoxNum" />
      <el-table-column label="发货地址名称" align="center" min-width="120" prop="addressCn" />
      <el-table-column label="日期" align="center" min-width="140" prop="deliveryTime" />
      <el-table-column label="付出人" align="center" min-width="100" prop="operator">
        <template slot-scope="{ row }">
          <span>
            {{ row.operator }}
            <span v-if="row.operator && row.checker">,</span>
            {{ row.checker }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="押运接收人" align="center" min-width="100" prop="transportUrl">
        <template slot-scope="{ row }">
          <span>
            <el-link type="primary" :underline="false" @click="handleUpload(row, 1)">上传图片</el-link>
            <el-link  v-if="row.transportUrl" type="primary" :underline="false" @click="handleImg(row.transportUrl)">查看</el-link>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="分行接收人" align="center" min-width="100" prop="branchUrl">
        <template slot-scope="{ row }">
          <span>
            <el-link type="primary" :underline="false" @click="handleUpload(row, 2)">上传图片</el-link>
            <el-link  v-if="row.branchUrl" type="primary" :underline="false" @click="handleImg(row.branchUrl)">查看</el-link>
          </span>
        </template>
      </el-table-column>
      <!-- 展开内容列  -->
      <el-table-column type="expand" width="1">
        <template slot-scope="{ row }">
          <div style="background: #ccc; min-height: 50px; text-align: center; margin: 0 5px">
            <EditExpand v-if="childList.length > 0" :table-data="childList" />
            <div v-else style="line-height: 50px">无明细数据</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="100">
        <template slot-scope="{ row }">
          <span v-if="row.status === 1">
            <el-link type="primary" :underline="false" @click="handleUpdate(row)">编辑</el-link>
            <el-divider direction="vertical" />
            <el-link type="primary" :underline="false" @click="handleDelete(row)">删除</el-link>
          </span>
          <span v-else>
            <el-link
              v-show="row.expanded"
              type="primary"
              :underline="false"
              @click="toogleExpand(row)"
              >收起</el-link
            >
            <el-link
              v-show="!row.expanded"
              type="primary"
              :underline="false"
              @click="toogleExpand(row)"
              >明细</el-link
            >
            <el-divider direction="vertical" />
            <el-link type="primary" :underline="false" @click="handlePrint(row)">打印</el-link>
          </span>
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
    <!-- 新增/编辑 -->
    <Edit
      v-if="editShow"
      ref="edit"
      :edit-show="editShow"
      :edit-type="editType"
      :edit-id="editId"
      :address-opt="addressOpt"
      :securities-opt="securitiesOpt"
      :username="username"
    />
    <!-- 打印 -->
    <div :key="printEdit.title" class="printContent" style="display: none">
      <div class="content">
        <p class="title">{{ printEdit.title }}</p>
        <p class="sub-title">
          <span>单号：</span>
          {{ printEdit.orderNo }}
        </p>
        <p class="sub-title">
          <span>解缴企业：</span>
          {{ printEdit.enterprise }}
        </p>
        <p class="sub-title">
          <span>目的地：</span>
          {{ printEdit.addressCn }}
        </p>
        <p class="sub-title">
          <span>品名：</span>
          {{ printEdit.securities }}
        </p>
        <p class="sub-title">
          <span>日期：</span>
          {{ printEdit.printDate }}
        </p>
        <el-table
          row-key="id"
          :data="printEdit.list"
          border
          fit
          :header-cell-style="{ background: '#ccc', color: '#606266' }"
        >
          <el-table-column label="序号" prop="id" align="center" width="50" type="index" />
          <el-table-column label="编号" align="center" prop="serialNumber" width="100" />
          <el-table-column label="皮重(kg)" align="center" prop="tareWeight" width="100" />
          <el-table-column label="毛重(kg)" align="center" prop="grossWeight" width="100" />
          <el-table-column label="净重(kg)" align="center" prop="netWeight" width="100">
            <template slot-scope="{ row }">
              {{ row.grossWeight - row.tareWeight }}
            </template>
          </el-table-column>
          <el-table-column label="称重人" align="center" prop="weightor" width="90" />
          <el-table-column label="起止冠字号" align="center" prop="crownBetweenStr" width="280" />
          <el-table-column label="打印时间" align="center" prop="printTime" width="200" />
        </el-table>
        <p class="sub-title mt30">
          合计：
          <!-- <span v-show=" printEdit.planPar > 0" class="ml30">拍数：</span
          ><span v-show=" printEdit.planPar > 0" class="ml30">{{ printEdit.planPar }}</span
          > -->
          <span class="ml30">箱数：</span><span class="ml30">{{ printEdit.planTotalBox }}</span>
        </p>
        <div class="sub-title mt30 border">
          <div class="ml30 right">管库员签字(2人)：</div><span class="ml30">{{ printEdit.operator }}</span
          ><span class="ml30">{{ printEdit.checker }}</span>
        </div>
        <div class="sub-title mt30 border">
          <div class="ml30 right">押运人员签字(2人含领队)：</div>
        </div>
        <!-- <div class="sub-title mt30 border">
          <div class="ml30 right">接收人：</div>
        </div> -->
       <!-- <div style="width: 100%;text-align: center;left:0px;bottom:0px;margin-top:20px">北京智慧蛮牛科技有限公司提供技术支持</div> -->

      </div>
    </div>

    <el-dialog title="上传图片" :visible.sync="dialogFormVisible" custom-class="customWidth_30">
        <el-upload
          ref="upload"
          :data="uploadData"
          :action="uploadUrl"
          :headers="token"
          :on-change="fileChange"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload1024"
          :on-success="uploadSuccess"
          :auto-upload="true"
          accept=".jpg, .jpeg, .png"
          class="upload-demo"
          style="text-align: center"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip" style="margin-bottom: 20px">只能上传jpg/png文件，单个文件不超过50Mb</div>
        </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="dialogFormVisible = false"> 取消 </el-button>
      </div>
    </el-dialog>
    <el-dialog title="查看图片" :visible.sync="dialogImageVisible">
      <el-image
      :src="dialogImageUrl"
      fit="contain"></el-image>
    </el-dialog>
    <!-- end -->
  </div>
</template>

<script>
import { listDelivery, delDelivery, statusDelivery, printDelivery } from '@/api/workshop/delivery'
import { listDeliveryDetail } from '@/api/workshop/deliveryDetail'
import Edit from './components/edit'
import Pagination from '@/components/Pagination'
import Mixin from '@/utils/mixin.js'
import Dictionaries from '@/utils/dictionaries.js'
import EditExpand from './components/editExpand.vue'
import printHtml from '@/utils/print.js'
import { formatTimeToStr } from '@/utils/date.js'
import { getToken } from '@/utils/auth'

export default {
  name: 'Delivery',
  components: { Pagination, Edit, EditExpand },
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
      listApi: listDelivery, // 列表
      delApi: delDelivery, // 删除
      statusApi: statusDelivery, // 状态
      printApi: printDelivery, // 打印
      dialogFormVisible: false,
      dialogImageVisible: false,
      dialogImageUrl: '',
      token: { token: '' },
      uploadData: {},
      uploadUrl: `${window.Glod.baseURLConfig}delivery/imp`,
      expUrl: `${window.Glod.baseURLConfig}`,
      username: '', // 操作人
      childList: [], // 明细数据
      printEdit: {}, // 打印对象
      titleList: {
        // 打印标题
        0: '实物收发卡'
      }
    }
  },
  created() {
    this.listSecuritiesOpt() // 品名选项
    this.getDictOptList('addressOpt') // 发货目的地选项
    this.getPageList()
    this.token.token = getToken()
  },
  mounted() {
    this.tableData.forEach((item) => {
      this.$set(item, 'expanded', false)
    })
  },
  methods: {
    handleImg(url) {
      this.dialogImageUrl = this.expUrl + url
      this.dialogImageVisible = true
    },
    handleUpload(row, type) {
      if (row.id) {
        this.uploadData.type = type
        this.uploadData.id = row.id
      } else {
        this.fileList = []
        this.uploadData.id = 0
      }
      this.dialogFormVisible = true
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
      if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
        this.$message.success('上传图片只能是 JPG PNG 格式!')
        return false
      }
      const isLt2M = file.size / 1024 / 1024 < 50
      if (!isLt2M) {
        this.$message.success('上传图片大小不能超过 50MB!')
      }
      return isLt2M
    },
    // 新增
    handleAddLocal() {
      this.username = sessionStorage.getItem('username')
      this.handleAdd()
    },
    // 监听展开变化      <!-- @expand-change="expandChange" -->
    // expandChange(row, expanded) {
    // let isExpand = expanded.find((v) => v.id == row.id) //如果有匹配项代表已经展开
    // row.isExpand = isExpand
    // if (isExpand) {
    //   // 加载明细数据
    //   //this.getDetailList(row)
    //   //this.updateChildTable = !this.updateChildTable;//实时修改这个值刷新子表格渲染数
    //   // row.detailList = row.detailList.slice(0)
    // }
    // },
    // 展开切换事件
    async toogleExpand(row) {
      if (!row.expanded) {
        row.expanded = true
        this.getDetailList(row, 0)
      } else {
        row.expanded = false
      }
      const $table = this.$refs.table
      this.tableData.map((item) => {
        if (row.id !== item.id) {
          item.expanded = false
          $table.toggleRowExpansion(item, false)
        }
      })
      $table.toggleRowExpansion(row)
    },
    // 加载明细数据
    async getDetailList(row, type) {
      const { code, retObj } = await listDeliveryDetail({ deliveryId: row.id })
      if (code === 0) {
        if (type === 1) {
          this.$set(this.printEdit, 'list', retObj)
          setTimeout(() => {
            this.print(0)
          }, 300)
        } else {
          this.childList = retObj
        }
      }
    },
    // 打印
    handlePrint(row) {
      this.printEdit = Object.assign({}, row)
      this.$set(this.printEdit, 'title', '分支机构产品箱号明细表')
      this.$set(this.printEdit, 'enterprise', '沈阳制造有限公司')
      this.$set(this.printEdit, 'printDate', formatTimeToStr(new Date(), 'yyyy-MM-dd'))
      this.getDetailList(row, 1)
    },
    // 打印
    print(index) {
      const printData = document.getElementsByClassName('printContent')[index].innerHTML
      // 第二个参数无值打印横版，有值打印竖版
      printHtml(printData)
    }
    // methods-end
  }
}
</script>

<style scoped lang="scss">
.content {
  padding: 20px 50px 50px 50px;
  width: 100%;
  font-size: 14px;
}
.title {
  font-size: 24px;
}
.sub-title {
  font-size: 18px;
}
.mt30 {
  margin-top: 30px;
}
.ml30 {
  margin-left: 30px;
}
.border {
  border: 1px #999 solid;
  height: 50px;
  line-height: 50px;
  display: flex;
}
.right {
  width: 220px;
  text-align: right;
}
::v-deep .el-table__empty-block {
  min-height: 50px;
}

</style>
