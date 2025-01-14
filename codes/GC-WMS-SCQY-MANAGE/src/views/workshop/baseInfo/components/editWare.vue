<template>
  <div>
    <!-- rfid基础信息登记表表单 -->
    <el-dialog
      :title="wareMap[editId] + '库位状态'"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
      custom-class="customWidth_99"
      top="2vh"
    >
      <el-card class="box-card" shadow="always" v-for="item in detailList" :key="item.areaId">
        <div slot="header" class="clearfix">
          <span>{{ item.areaId }}号库位</span>
        </div>
        <div v-for="(item1, $index) in item.detailList" :key="$index" class="lug">
          <div class="content-right">
            <div class="content-header">
              <p class="con">库存产品：{{ item1.securities }}</p>
              <p>拍数：{{ item1.racketNum }}</p>
              <p>零箱数：{{ item1.boxNum }}</p>
            </div>
          </div>
          <div class="bottom clearfix">
            <el-button type="text" class="button" @click="handleAddChild(item1)">实物收发卡</el-button>
          </div>
        </div>
        <div v-if="item.detailList.length === 0" class="lug">
          <div class="content-right">
            <div class="content-header">
              <p class="con">库存产品：</p>
              <p>拍数：</p>
              <p>零箱数：</p>
            </div>
          </div>
        </div>
      </el-card>
      <!-- 实物收发卡' -->
      <el-dialog
        :title="'实物收发卡'"
        :visible.sync="innerVisible"
        :close-on-click-modal="false"
        custom-class="customWidth_99"
        top="2vh"
        append-to-body
      >
        <p style="font-size: 24px">实物收发卡</p>
        <p>品名：{{ securities }}</p>
        <p>位置：{{ wareMap[editId] }}</p>
        <el-table :loading="isLoading" v-loadmore="getData1">
          <el-table-column label="序号" prop="id" align="center" width="50px" type="index" />
          <el-table-column label="日期" align="center" prop="createTime" />
          <el-table-column label="摘要" align="center" prop="digest" />
          <el-table-column label="收入" align="center" prop="incomeQty">
            <template slot-scope="{ row }">{{ converIncome(row) }}</template>
          </el-table-column>
          <el-table-column label="付出" align="center" prop="payQty">
            <template slot-scope="{ row }">{{ converPay(row) }}</template>
          </el-table-column>
          <el-table-column label="内部收入" align="center" prop="innerIncomeQty" />
          <el-table-column label="内部付出" align="center" prop="innerPayQty" />
          <el-table-column label="库存" align="center" prop="inventory" />
          <el-table-column label="库管员" align="center">
            <template slot-scope="{ row }">
              <span>{{ row.operator }},{{ row.checker }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import { getWare } from '@/api/workshop/stock'
import { wareExp, getWareList } from '@/api/workshop/workshopAccount'

import MixinEdit from '@/utils/mixin-edit.js'

export default {
  mixins: [MixinEdit],
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editId'],
  data() {
    return {
      getApi: getWare,
      expApi: wareExp, // 导出
      detailList: [], // 库位状态集合
      innerVisible: false,
      cardList: [], // 实物收发卡集合
      securities: '',
      wareMap: {
        201: '总库1',
        202: '总库2'
      },
      isLoading: false,
      pageSize: 5,
      pageNum: 1,
      total: 0,
      edit: {}
    }
  },
  created() {
    this.getData()
  },
  mounted() {},
  methods: {
    async getData() {
      const { code, retObj } = await this.getApi(this.editId)
      if (code === 0) {
        this.detailList = retObj
      }
    },
    // 实物收发卡
    async handleAddChild(param) {
      // this.$router.push(`/ware1`)
      // window.open("ware1")
      const href = this.$router.resolve({
        path: `warePage1`,
        query: { id: this.editId, securitiesId: param.securitiesId }
      }).href
      // 打开新的页面 到 href 这个页面
      window.open(href, '_blank')

      // const { code, retObj } = await this.expApi({
      //   wareId: param.wareId,
      //   securitiesId: param.securitiesId
      // })
      // if (code === 0) {
      //   window.open(window.Glod.baseURLConfig + retObj.url)
      // }
      // this.edit = param
      // this.innerVisible = true
      // var params = {
      //     page: this.pageNum,
      //     rows: this.pageSize,
      //     ...param
      //   }
      // this.securities = param.securities
      // const { code, retObj } = await getWareList(params)
      // if (code === 0) {
      //   this.cardList = retObj.records
      // }
    },
    // 请求数据
    async getData1(a) {
      this.pageNum += 1
      if (a === 'down') {
        this.isLoading = true
        var params = {
          page: this.pageNum,
          rows: this.pageSize,
          ...this.edit
        }
        var retObj = await getWareList(params)
        this.isLoading = false

        if (retObj) {
          this.total = retObj.total
          if (this.total >= this.cardList.length) {
            this.cardList = this.cardList.concat(retObj.records)
          }
        }
      }
    },
    converIncome(row) {
      if (row.redFlashFlag === 2 && row.payQty > 0) {
        return row.payQty
      }
    },
    converPay(row) {
      if (row.redFlashFlag === 2 && row.payQty > 0) {
        return ''
      }
    }
    // methods-end
  }
}
</script>

<style scoped lang="scss">
::v-deep .clearfix {
  text-align: center;
  font-size: 24px;
}

::v-deep .el-dialog__body {
  min-height: calc(100vh - 100px);
  height: calc(100vh - 100px);
  overflow-y: auto;
}
::v-deep .el-card__header {
  background: #e6ebf5;
}
::v-deep .el-card__body {
  min-height: 300px;
  width: 100%;
  padding: 5px 5px;
  display: grid;
  font-size: 20px;
}
::v-deep .bottom {
  display: flex;
  justify-content: center;
  // align-items: center;
  // flex-wrap: wrap;
}
::v-deep .content-right {
  vertical-align: middle;
  display: inline;
  text-align: center;
  justify-content: center;
  align-items: center;
  border: 1px #fff solid;
  .content-header {
    vertical-align: middle;
    display: inline-block;
    align-items: center;
    // flex-wrap: wrap;
    text-align: center;
    // font-size: 20px;
    width: 100%;
    p {
      width: 100%;
      height: 30px;
      margin-block-start: 0em;
      margin-block-end: 0em;
    }
  }
}
::v-deep .el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.lug {
  place-self: center;
}
.box-card {
  width: 23%;
  padding: 0 0px 20px;
  margin-bottom: 20px;
  z-index: 9999;
  float: left;
  margin-left: 1.5%;
}
.button {
  font-size: 16px;
}
</style>

