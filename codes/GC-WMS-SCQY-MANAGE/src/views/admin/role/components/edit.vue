<template>
  <div class="applyfor-edit">
    <el-dialog
      :title="textMap[editType] +'角色'"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
      custom-class="customWidth_30"
    >
      <el-form
        ref="editForm"
        class="myForm"
        :model="editForm"
        :rules="rules"
        label-position="right"
        label-width="25%"
      >
        <el-form-item label="所属租户" prop="tenantId">
          <el-select
            v-model="editForm.tenantId"
            class="filter-item"
            placeholder="请选择"
          >
            <el-option
              v-for="item in tenantOpt"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="角色名称" prop="name">
          <el-input v-model="editForm.name" placeholder="角色名称 " />
        </el-form-item>
        <el-form-item v-show="editType === 'copy'" label="复制权限">
          <el-radio-group v-model="editForm.copyPermission">
            <el-radio v-for="item in $commonArray.options['showOpt']" :key="item.id" :label="item.id">{{ item.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="editForm.id > 0" label="角色状态" prop="status">
          <el-radio-group v-model="editForm.status">
            <el-radio v-for="item in $commonArray.options['statusOpt']" :key="item.id" :label="item.id">{{ item.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input
            v-model="editForm.remark"
            :autosize="{ minRows: 2, maxRows: 5}"
            type="textarea"
            placeholder="角色描述"
          />
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
import { adminAddRole, updateRole, getRole, adminCopyRole } from '@/api/sys/role'

export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editType', 'editId', 'tenantOpt', 'parentEdit', 'tenantId'],
  data() {
    return {
      api: '',
      editForm: {},
      rules: {
        tenantId: [{ required: true, message: '所属租户不能为空', trigger: 'change' }],
        name: [{ required: true, message: '角色名称不能为空', trigger: 'change' }]
      },
      isDisable: false,
      textMap: {
        copy: '复制',
        update: '编辑',
        add: '新增'
      }
    }
  },
  created() {
    if (this.editType === 'update') {
      this.api = updateRole
      this.getInfo()
    } else if (this.editType === 'copy') {
      this.api = adminCopyRole
      this.parentEdit.tenantId = ''
      this.editForm = this.parentEdit
      this.editForm.roleId = this.parentEdit.id
      // 默认复制权限
      this.$set(this.editForm, 'copyPermission', 1)
    } else {
      this.api = adminAddRole
      if (this.tenantId > 0) {
        this.$set(this.editForm, 'tenantId', this.tenantId)
      }
    }
  },
  methods: {
    // 信息详情
    async getInfo() {
      const { code, retObj } = await getRole({ id: this.editId })
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
          const { code, msg } = await this.api({ ...this.editForm })
          if (code === 0) {
            this.$message.success(msg)
            this.handleClose(1)
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-radio__label{font-size:13px}
</style>
