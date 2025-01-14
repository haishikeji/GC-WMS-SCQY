<template>
  <div class="app-container">
    <!-- rfid基础信息登记表表单 -->
    <el-dialog
      :title="textMap[editType]"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
    >
      <el-form
        ref="editForm"
        :model="editForm"
        label-position="right"
        label-width="7rem"
        style="width: 90%; margin-left:2%;"
      >
      <el-form-item label="文件名称" prop="name">
            <el-input
          v-model="editForm.name"
        />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
            <el-input
          v-model="editForm.remark"
          :autosize="{ minRows: 2, maxRows: 6}"
          type="textarea"
        />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="handleClose"> 取消 </el-button>
        <el-button type="primary" @click="confirmClick">
          保存
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getFile, updateFile } from '@/api/file'
import MixinEdit from '@/utils/mixin-edit.js'

export default {
  mixins: [MixinEdit],
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editType', 'editId'],
  data() {
    return {
      getApi: getFile,
      saveApi: updateFile
    }
  },
  created() {
    if (this.editType === 'update') {
      this.getData()
    }
  },
  mounted() {},
  methods: {
    // methods-end
  }
}
</script>

<style scoped lang="scss">
</style>

