<template>
  <div class="app-container">
    <div class="title">临时库登记列表</div>
    <!-- 功能 -->
    <div class="filter-container">
      <el-button class="filter-item" type="primary" icon="el-icon-view" @click="handleUpdate({'id': wareId})">查看库位状态</el-button>
    </div>
    <!-- 标签打印列表 -->
    <el-table
      v-loading="listLoading"
      :data="tableData"
      border
      fit
      :row-style="tableRowStyle"
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
      :height="tableHeight"
    >
      <el-table-column label="NO" prop="id" align="center" width="40" type="index" />
      <el-table-column label="部门" align="center" prop="dept" />
      <el-table-column label="品名" align="center" prop="securities" />
      <el-table-column label="编号" align="center" prop="serialNumber" />
      <el-table-column label="冠字号" align="center" min-width="240" prop="crownBetweenStr" />
      <el-table-column label="皮重(kg)" align="center" prop="tareWeight" />
      <el-table-column label="毛重(kg)" align="center" prop="grossWeight" />
      <el-table-column label="称重人" align="center" prop="weightor" />
      <el-table-column label="打印时间" align="center" prop="printTime" min-width="120" />
      <el-table-column label="卡号" align="center" prop="rfid" min-width="110" />
      <el-table-column label="区域" align="center" prop="locWare"  min-width="100"  />
      <el-table-column label="出入库" align="center">
        <template slot-scope="{ row }">
          <span v-show="row.inOrOut===2">入库</span>
          <span v-show="row.inOrOut===3">出库</span>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" align="center" prop="operateTime" min-width="140" />
      <el-table-column label="操作人" align="center" prop="createBy" />
      <el-table-column label="复核人" align="center" prop="checker" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="{ row }">{{ handleStatus(row)}}</template>
      </el-table-column>
    </el-table>
    <!-- 新增/编辑 -->
    <Edit v-if="editShow" ref="edit" :edit-show="editShow" :edit-type="editType" :edit-id="editId" />
  </div>
</template>

<script>
import { shopViewList } from '@/api/workshop/baseInfo'
// import { getWareId } from '@/api/sys/self'
import Edit from './components/editShopWare'
import Mixin from '@/utils/mixin.js'
import Dictionaries from '@/utils/dictionaries.js'

export default {
  name: 'BaseInfo',
  components: { Edit },
  mixins: [Mixin, Dictionaries],
  data() {
    return {
      listApi: shopViewList, // 列表
      tableHeight: '',
      socket: '',
      title: '消息通知',
      wareId: '', // 库id
      statusMap: {
        '-1': '复核异常',
        '1': '',
        '2': '解缴出库',
        '3': '解缴已入库',
        '4': '返检待入库',  
        '5': '返检已入库',
        '6': '内部出库'
      }
    }
  },
  created() {
    this.getTableHeight()
    this.wareId = this.$route.query.wareId
    if (this.wareId) {
      this.getList()
      this.initWebSocket()
    } else {
      this.$message.error('页面路由地址异常，请联系技术人员')
    }
    // this.handleWareId(this.back)
  },
  destroyed() {
    // 销毁监听
    this.socket.close()
  },
  mounted() {
    // 挂载window.onresize事件 动态设置table高度
    const _this = this
    window.onresize = () => {
      if (_this.resizeFlag) {
        clearTimeout(_this.resizeFlag)
      }
      _this.resizeFlag = setTimeout(() => {
        _this.getTableHeight()
        _this.resizeFlag = null
      }, 100)
    }
  },
  methods: {
    // async handleWareId(callback) {
    //   const { code, retObj } = await getWareId()
    //   if (code === 0) {
    //     this.wareId = retObj
    //   }
    //   if (callback) {
    //     callback()
    //   }
    // },
    // back() {
    //   this.getList()
    //   this.initWebSocket()
    // },
    // 获取页面列表
    async getList() {
      this.listLoading = true
      const { code, retObj } = await this.listApi({ id: this.wareId })
      if (code === 0) {
        this.tableData = retObj
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    // 状态
    handleStatus(row) {
      return this.statusMap[row.status + '']
    },
    // 表格行动态颜色
    tableRowStyle(data, rowIndex) {
      const rowColor = {
        'background-color': '#f00',
        color: 'rgb(8 8 8)'
      }
      const rowColor1 = {
        'background-color': '#ff0',
        color: 'rgb(8 8 8)'
      }
      const rowColor3 = {
        'background-color': '#51b331',
        color: 'rgb(8 8 8)'
      }
      if (data.row.qualityStatus > 1) {
        return rowColor
      }
      // ：临时库-1复核异常 1入库 2解缴出库 3解缴已入库 4返检待入库 5返检已入库
      if (data.row.status === 2 || data.row.status === 4) {
        return rowColor1
      } else if (data.row.status === -1) {
        return rowColor
      } else if (data.row.status === 3 || data.row.status === 5) {
        return rowColor3
      }
    },
    // 添加或者删除数据
    addData(item) {
      const that = this
      // 临时库-1复核异常 1入库 2解缴出库 3解缴已入库
      if (item.status === 1) {
        // 入库
        that.tableData.unshift(item)
        this.$notify({
          title: that.title,
          message: '新增入库信息' + item.rfid,
          type: 'success'
        })
      } else if (item.status === 2) {
        // 解缴出库
        // const index = this.tableData.findIndex(v => v.rfid === item.rfid)
        // if (index !== undefined) {
        //   this.tableData.splice(index, 1)
        // }
        that.tableData.unshift(item)
        this.$notify({
          title: that.title,
          message: '新增解缴出库信息' + item.rfid,
          type: 'success'
        })
      } else if (item.status === 3) {
        // 解缴已入库
        const index = this.tableData.findIndex(v => v.rfid === item.rfid && v.status === 2)
        // 存在先删除
        if (index !== undefined) {
          this.tableData.splice(index, 1)
        }
        that.tableData.unshift(item)
        this.$notify({
          title: that.title,
          message: '新增解缴已入库信息' + item.rfid,
          type: 'success'
        })
      } else if (item.status === 4) {
        that.tableData.unshift(item)
        this.$notify({
          title: that.title,
          message: '新增返检待出库信息' + item.rfid,
          type: 'success'
        })
      } else if (item.status === 5) {
        // 返检已入库
        const index = this.tableData.findIndex(v => v.rfid === item.rfid && v.status === 4)
        // 存在先删除
        if (index !== undefined) {
          this.tableData.splice(index, 1)
        }
        that.tableData.unshift(item)
        this.$notify({
          title: that.title,
          message: '新增返检已出库信息' + item.rfid,
          type: 'success'
        })
      } else if (item.status === 6) {
          that.tableData.unshift(item)
          this.$notify({
            title: that.title,
            message: '新增内部出库信息' + item.rfid,
            type: 'success'
          })
      } else if (item.status === -1) {
        // 复核异常
        const index = this.tableData.findIndex(v => v.rfid === item.rfid)
        // 存在删除
        if (index !== undefined) {
          this.tableData.splice(index, 1)
        }
        // 在加入
        that.tableData.unshift(item)
        if (item.errType === 'err_check') {
          this.$notify({
            title: that.title,
            message: '复核异常' + item.rfid,
            type: 'error'
          })
        } else if (item.errType === 'err_quality') {
          this.$notify({
            title: that.title,
            message: '质量异常' + item.rfid,
            type: 'error'
          })
        }
      }
      if (that.tableData.length > 100) {
        that.tableData.splice(100, that.tableData.length - 100)
      }
    },
    // 获取高度
    getTableHeight() {
      // 标题高度
      const tableH = 150
      const tableHeightDetail = window.innerHeight - tableH
      if (tableHeightDetail <= 300) {
        this.tableHeight = 300
      } else {
        this.tableHeight = window.innerHeight - tableH
      }
    },
    initWebSocket: function() {
      if (typeof WebSocket === 'undefined') {
        this.$message({
          message: '您的浏览器不支持socket',
          type: 'success'
        })
        //  alert('您的浏览器不支持socket')
      } else {
        //  实例化socket
        const socket_url = window.Glod.WEBSOCKET_URL
        this.socket = new WebSocket(socket_url + this.wareId)
        //  监听socket连接
        this.socket.onopen = this.open
        //  监听socket错误信息
        this.socket.onerror = this.error
        //  监听socket消息
        this.socket.onmessage = this.getMessage
        this.socket.onclose = this.close
      }
    },
    open: function() {
      console.log('socket连接成功')
      this.socket.send('66666666666666666666')
    },
    error: function() {
      console.log('连接错误')
    },
    getMessage: function(msg) {
      if (msg.data !== '' && msg.data.startsWith('{')) {
        const data = JSON.parse(msg.data)
        this.addData(data)
      }
    },
    send: function() {
      this.socket.send('')
    },
    close: function() {
      console.log('socket已经关闭')
    }
    // methods-end
  }
}
</script>

<style scoped lang="scss">
.title {
  font-size: 36px;
  font-weight: 900;
  text-align: center;
  padding-bottom: 20px;
}
.table-header {
  background: '#eef1f6';
  color: '#606266';
}
// 取消点击变色 移入变色
::v-deep .current-row {
  background-color: transparent !important;
  color: inherit !important;
}
::v-deep .el-table--enable-row-hover .el-table__body tr:hover>td {
  background-color: transparent !important;
  color: inherit !important;
}
</style>

