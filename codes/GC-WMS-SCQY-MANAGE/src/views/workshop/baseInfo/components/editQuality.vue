<template>
  <div class="app-container">
    <!-- rfid基础信息登记表表单 -->
    <el-dialog
      :title="textMap[editType] + '质量'"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
      custom-class="customWidth_30"
    >
      <el-form
        ref="editForm"
        :rules="editType === 'update' ? rules : rules"
        :model="editForm"
        label-position="right"
        label-width="7rem"
        style="width: 90%; margin-left: 2%"
      >
        <el-form-item label="质量" prop="qualityStatus">
          <el-select v-model="editForm.qualityStatus" class="filter-item" placeholder="请选择">
            <el-option
              v-for="item in qualityOpt"
              :key="item.val"
              :label="item.name"
              :value="item.val"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="handleClose">关 闭</el-button>
        <el-button
          v-show="editType !== 'view'"
          type="primary"
          :disabled="isDisable"
          @click="confirmClick"
        >保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getBaseInfo, updateBaseInfoQuality } from '@/api/workshop/baseInfo'
import MixinEdit from '@/utils/mixin-edit.js'

export default {
  mixins: [MixinEdit],
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editType', 'editId', 'qualityOpt'],
  data() {
    return {
      saveApi: updateBaseInfoQuality, // 添加
      getApi: getBaseInfo, // 获取
      rules: {
        qualityStatus: [{ required: true, message: '质量不能为空', trigger: 'change' }]
      }
    }
  },
  created() {
    if (this.editType === 'update') {
      this.getData(this.back)
    }
  },
  mounted() {},
  methods: {
    back() {
      if (this.editForm.qualityStatus) {
        this.$set(this.editForm, 'qualityStatus', this.editForm.qualityStatus + '')
      } else {
        this.$set(this.editForm, 'qualityStatus', '1')
      }
    }
    // methods-end
  }
}
</script>

<style scoped lang="scss">
</style>

