<template>
  <div class="applyfor-edit">
    <el-dialog
      :title="textMap[editType]"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
      custom-class="customWidth_30"
    >
      <el-form ref="editForm" class="myForm" :model="editForm" :rules="rules" label-position="right" label-width="6rem">
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="editForm.name" />
        </el-form-item>
        <el-form-item label="部门排序" prop="sorted">
          <el-input v-model="editForm.sorted" oninput="value=value.replace(/[^0-9]/g,'')" maxlength="3" placeholder="最多3位整数" />
        </el-form-item>
        <el-form-item v-show="editForm.id > 0" label="部门状态" prop="status">
          <el-radio-group v-model="editForm.status">
            <el-radio v-for="item in $commonArray.options['statusOpt']" :key="item.id" :label="item.id">{{ item.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="handleClose">取 消</el-button>
        <el-button type="primary" :disabled="isDisable" @click="confirmClick">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { addDept, updateDept, getDept } from '@/api/sys/dept'

export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editType', 'editId'],
  data() {
    // var checkNum = (rule, value, callback) => {
    //   let reg = /^(0|([1-9]\d*))?$/g
    //   if (!reg.test(value)) {
    //     callback(new Error('请输入数字'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      editForm: {},
      rules: {
        name: [{ required: true, message: '部门名称不能为空', trigger: 'change' }]
      },
      isDisable: false,
      textMap: {
        update: '编辑部门',
        add: '新增部门'
      }
    }
  },
  created() {
    if (this.editType === 'update') {
      this.getInfo()
    }
  },
  methods: {
    // 信息详情
    async getInfo() {
      const { code, retObj } = await getDept({ id: this.editId })
      if (code === 0) {
        this.editForm = retObj
      }
    },
    // 关闭窗口
    handleClose(index) {
      this.$parent.toggleEdit(false, index)
    },

    // 提交
    confirmClick() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.isDisable = true
          if (this.editType === 'update') {
            const { code, msg } = await updateDept({ ...this.editForm })
            if (code === 0) {
              this.$message.success(msg)
            }
          } else {
            const { code, msg } = await addDept({ ...this.editForm })
            if (code === 0) {
              this.$message.success(msg)
            }
          }
          this.isDisable = false
          this.handleClose(1)
        } else {
          return false
        }
      })
    }
  // end
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-radio__label{font-size:13px}
</style>
