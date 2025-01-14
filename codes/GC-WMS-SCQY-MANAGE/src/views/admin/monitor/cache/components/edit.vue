<template>
  <div class="applyfor-edit">
    <el-dialog
      title="缓存信息"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
    >
      <el-form ref="editForm" :model="editForm" label-position="right" label-width="5.8rem">
        <el-form-item label="缓存key" prop="">
          <el-input v-model="editForm.key" disabled />
        </el-form-item>
        <el-form-item label="缓存value" prop="value">
          <el-input v-model="editForm.value" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="handleClose">取 消</el-button>
        <el-button :disabled="isDisable" type="primary" @click="confirmClick">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getCache, updateCache } from '@/api/monitor/cache'
import MixinEdit from '@/utils/mixin-edit.js'

export default {
  mixins: [MixinEdit],
  // eslint-disable-next-line vue/require-prop-types
  props: ['cacheKey', 'editShow'],
  data() {
    return {
      saveApi: updateCache,
      getApi: getCache,
      rules: {
        value: [{ required: true, message: '所属租户不能为空', trigger: 'change' }],
        name: [{ required: true, message: '部门名称不能为空', trigger: 'change' }]
      },
    }
  },
  created() {
    this.$set(this.editForm, 'key', this.cacheKey)
    this.getLocalData()
  },
  methods: {
    async getLocalData() {
      const { code, retObj } = await this.getApi({ key: this.cacheKey })
      if (code === 0) {
        this.$set(this.editForm, 'value', retObj)
      }
    },
    confirmClick() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.isDisable = true
          const { code, msg } = await this.saveApi(this.editForm)
          if (code === 0) {
            this.$message.success(msg)
            this.$parent.editShow = false
          }
          this.isDisable = false
        } else {
          return false
        }
      })
    },
    // methods-end
  }
}
</script>

<style lang="scss" scoped>
</style>
