<template>
  <div class="applyfor-edit">
    <el-dialog
      :title="textMap[editType]"
      :visible.sync="editShow"
      :before-close="handleClose"
      :close-on-click-modal="false"
      custom-class="customWidth_80"
    >
      <el-form ref="editForm" class="myForm" :model="editForm" :rules="editType==='update'?rulesUpdate:rules" size="mini" label-position="right" label-width="7.5rem">
        <el-row>
          <el-col :span="8">
            <el-form-item label="名称" prop="name">
              <el-input v-model="editForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="导出表名" prop="tableName">
              <el-input v-model="editForm.tableName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="导出标识" prop="tag">
              <el-input v-model="editForm.tag" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="导出文件名" prop="name">
              <el-input v-model="editForm.fileName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="排序字段">
              <el-input v-model="editForm.orderColumn" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="备注">
              <el-input v-model="editForm.remark" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="配置导出字段" name="first">
            <div class="filter-container">
              <el-button class="filter-item" type="info" icon="el-icon-plus" @click="loadField(editForm.tableName)">加载主表字段</el-button>
              <el-button class="filter-item" type="danger" icon="el-icon-delete" @click="cleanField(editForm.tableName)">清空字段</el-button>
              <el-button class="filter-item" type="success" icon="el-icon-plus" @click="addField">增加字段</el-button>
            </div>
            <el-table
              :data="editForm.columns"
              border
              stripe
              fit
              highlight-current-row
              :header-cell-style="{background:'#eef1f6',color:'#606266'}"
            >
              <el-table-column label="NO" prop="id" sortable="custom" align="center" min-width="40" type="index" />
              <el-table-column label="字段标识" align="center" min-width="160">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.tag" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="导出名称" align="center" min-width="120">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.name" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="导出宽度" align="center" prop="columnWidth" min-width="90">
                <template slot="header">
                  <span title="单击查看填写提示" @click="open"><i style="color:#409eff" class="el-icon-question" />导出宽度</span>
                </template>
                <template slot-scope="{row}">
                  <span><el-input v-model="row.columnWidth" oninput="value=value.replace(/[^0-9]/g,'')" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="日期格式" align="center" min-width="120">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.dateStyle" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="固定值" align="center" min-width="120">
                <template slot="header">
                  <span title="单击查看填写提示" @click="open1"><i style="color:#409eff" class="el-icon-question" />导出宽度</span>
                </template>
                <template slot-scope="{row}">
                  <span><el-input v-model="row.fixed" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="字典标识" align="center" min-width="100">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.dictTag" clearable /></span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="关联其他类" align="center" min-width="90">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.otherClass" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="关联方法" align="center" min-width="90">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.otherMethod" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="关联字段" align="center" min-width="90">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.otherField" clearable /></span>
                </template>
              </el-table-column> -->
              <el-table-column label="操作" align="center" min-width="50" fixed="right">
                <template slot-scope="scope">
                  <span><el-link type="primary" :underline="false" @click="deleteField(scope.$index)">删除</el-link></span>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="配置查询条件" name="second">
            <div class="filter-container">
              <el-button class="filter-item" type="success" icon="el-icon-plus" @click="addWhere">增加条件</el-button>
            </div>
            <el-table
              :data="editForm.wheres"
              border
              stripe
              fit
              highlight-current-row
              :header-cell-style="{background:'#eef1f6',color:'#606266'}"
            >
              <el-table-column label="NO" prop="id" sortable="custom" align="center" width="40" type="index" />
              <el-table-column label="查询表名" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.tableName" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="查询字段" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.baseColumn" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="查询类型" align="center">
                <template slot-scope="{row}">
                  <span>
                    <el-select v-model="row.whereType" clearable>
                      <el-option
                        v-for="item in whereTypeOpt"
                        :key="item"
                        :value="item"
                      />
                    </el-select>
                  </span>
                </template>
              </el-table-column>
              <el-table-column label="默认值" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.val" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="登录用户值" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.userField" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="60">
                <template slot-scope="scope">
                  <span><el-link type="primary" :underline="false" @click="deleteWhere(scope.$index)">删除</el-link></span>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="配置关联表" name="third">
            <div class="filter-container">
              <el-button class="filter-item" type="success" icon="el-icon-plus" @click="addJoin">增加关联</el-button>
            </div>
            <el-table
              :data="editForm.joins"
              border
              stripe
              fit
              highlight-current-row
              :header-cell-style="{background:'#eef1f6',color:'#606266'}"
            >
              <el-table-column label="NO" prop="id" sortable="custom" align="center" width="40" type="index" />
              <el-table-column label="主表名" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.tableName" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="连接表名" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.joinTableName" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="表连接方式" align="center">
                <template slot-scope="{row}">
                  <span>
                    <el-select v-model="row.joinType" clearable>
                      <el-option
                        v-for="item in joinTypeOpt"
                        :key="item"
                        :value="item"
                      />
                    </el-select>
                  </span>
                </template>
              </el-table-column>
              <el-table-column label="主表字段" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.dateStyle" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="连接表字段" align="center">
                <template slot-scope="{row}">
                  <span><el-input v-model="row.fixed" clearable /></span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="100">
                <template slot-scope="scope">
                  <span>
                    <el-link type="primary" :underline="false" title="加载子关联表字段" @click="loadField(scope.row.joinTableName)">加载</el-link>
                    <el-divider direction="vertical" />
                    <el-link type="primary" :underline="false" @click="deleteJoin(scope.$index)">删除</el-link>
                  </span>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="handleClose">取 消</el-button>
        <el-button v-show="editType!=='view'" :disabled="isDisable" type="primary" @click="confirmClick">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { loadColumn, getExpConfig, addExpConfig, updateExpConfig } from '@/api/expConfig/exp'

export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['editShow', 'editType', 'parentEdit', 'editId'],
  data() {
    var checkNum = (rule, value, callback) => {
      const reg = /^(0|([1-9]\d*))?$/g
      if (!reg.test(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      isDisable: false,
      notify: undefined,
      notify1: undefined,
      activeName: 'first',
      editForm: {
        columns: [],
        wheres: [],
        joins: []
      },
      joinTypeOpt: ['left join', 'right join', 'inner join', 'cross join'],
      whereTypeOpt: ['=', '<>', 'like', '>', '>=', '<', '<=', 'in', 'not in', 'between', 'not between'],
      rules: {
        name: [{ required: true, message: '用户名不能为空', trigger: 'change' }],
        tag: [{ required: true, message: '性别不能为空', trigger: 'change' }],
        tableName: [{ required: true, message: '部门不能为空', trigger: 'change' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'change' }],
        confirmPwd: [{ required: true, message: '确认密码不能为空', trigger: 'change' }]
      },
      rulesUpdate: {
        name: [{ required: true, message: '姓名不能为空', trigger: 'change' }],
        sex: [{ required: true, message: '性别不能为空', trigger: 'change' }],
        deptId: [{ required: true, message: '部门不能为空', trigger: 'change' }],
        mobile: [{ validator: checkNum, message: '手机号必须为数字' }]
      },
      textMap: {
        update: '编辑',
        add: '新增'
      }
    }
  },
  created() {
    if (this.editType === 'update') {
      this.getInfo()
    }
  },
  methods: {
    open() {
      const _that = this
      if (!_that.notify) {
        _that.notify = _that.$notify({
          title: '长度填写提示',
          dangerouslyUseHTMLString: true,
          duration: 0,
          position: 'top-left',
          type: 'warning',
          message: '<p>1个字符或文字：2</p><p>多个字符或文字：长度/2+2</p><p>标题大于内容：标题长度</p>',
          onClose() {
            _that.notify = undefined
          }
        })
      } else {
        this.notify.close()
      }
    },
    open1() {
      const _that = this
      if (!_that.notify1) {
        _that.notify1 = _that.$notify({
          title: '固定值填写提示',
          dangerouslyUseHTMLString: true,
          duration: 0,
          position: 'top-left',
          type: 'warning',
          message: '<p>示例: 1:男,2:女,3:未知</p>',
          onClose() {
            _that.notify1 = undefined
          }
        })
      } else {
        this.notify1.close()
      }
    },

    // 信息详情
    async getInfo() {
      const { code, retObj } = await getExpConfig({ id: this.editId })
      if (code === 0) {
        this.editForm = retObj
      }
    },

    handleClose(index) {
      if (this.notify) {
        this.notify.close()
      }
      this.$parent.toggleEdit(false, index)
    },

    // 提交
    confirmClick() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          this.isDisable = true
          if (this.editType === 'update') {
            const { code, msg } = await updateExpConfig({ ...this.editForm })
            if (code === 0) {
              this.$message.success(msg)
              this.handleClose(1)
            }
          } else {
            const { code, msg } = await addExpConfig({ ...this.editForm })
            if (code === 0) {
              this.$message.success(msg)
              this.handleClose(1)
            }
          }
          this.isDisable = false
        } else {
          return false
        }
      })
    },
    //  增加
    addField() {
      this.editForm.columns.push({})
    },
    //  增加
    addWhere() {
      this.editForm.wheres.push({ 'tableName': this.editForm.tableName })
    },
    //  增加
    addJoin() {
      this.editForm.joins.push({ 'tableName': this.editForm.tableName })
    },
    //  删除
    deleteField(index) {
      this.editForm.columns.splice(index, 1)
    },
    //  清空所有字段
    cleanField() {
      this.editForm.columns = []
    },
    //  删除
    deleteWhere(index) {
      this.editForm.wheres.splice(index, 1)
    },
    //  删除
    deleteJoin(index) {
      this.editForm.joins.splice(index, 1)
    },
    // 加载表字段
    async loadField(tableName) {
      if (tableName) {
        const { code, retObj } = await loadColumn({ 'tableName': tableName })
        if (code === 0) {
          this.editForm.columns.push(...retObj)
          this.activeName = 'first'
        }
      } else {
        this.$message.warning('请输入表名')
      }
    }
    // method end
  }
}
</script>

<style lang="scss" scoped>

::v-deep .el-dialog__body {
  padding:20px 30px;
}

</style>
