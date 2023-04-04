<!--
  储煤塔列表
-->
<template>
  <div class="avue-crud">
    <div style="padding-top:20px;">
      <el-table size="small" v-loading="table.loading" :data="tableData" stripe>
        <el-table-column label="储煤塔号" minWidth="150" align="left" prop="coal_tower_number" />
        <el-table-column label="煤余量（kg）" minWidth="150" align="left" prop="coal_surplus">
        </el-table-column>
        <!-- <el-table-column label="料位"
                         minWidth="150"
                         align="left"
                         prop="material_level" /> -->
        <el-table-column fixed="right" label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button size="mini" plain icon="el-icon-plus" type="primary"
              @click="handleEdit('edit', scope.$index, scope.row)">配煤
            </el-button>
            <el-button size="mini" plain type="info" icon="el-icon-info"
              @click="handleView('view', scope.$index, scope.row)">详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="el-dialog__footer" style="padding:50px 0 25px 0;">
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>
    <!-- 弹窗 -->
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title" :visible.sync="dialog.visible" :width="dialog.width" class="customDialogStyle"
        append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template>
          <Update v-if="dialog.type === 'edit'" :type="dialog.type" :data="dialog.data" @submitSave="submitSave"
            @close="dialog.visible = false" />
          <Views v-if="dialog.type === 'view'" :type="dialog.type" :data="dialog.data" @submitSave="submitSave"
            @sumRefresh="sumRefresh" @close="dialog.visible = false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import Update from "./coalBlending"
import Views from "./towerDetailsList"
import { getTowerList } from "@/api/production/oi/handCoalBlending";
export default {
  components: {
    Update,
    Views
  },
  data() {
    return {
      table: {
        border: true,
        loading: false,
      },
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '60%',
        data: {},
      },
      tableData: [
        {
          coal_tower_number: '1',
          coal_surplus: '0',
          material_level: '0'
        },
        {
          coal_tower_number: '2',
          coal_surplus: '0',
          material_level: '0'
        },
        {
          coal_tower_number: '3',
          coal_surplus: '0',
          material_level: '0'
        },
        {
          coal_tower_number: '4',
          coal_surplus: '0',
          material_level: '0'
        }
      ],
    }
  },
  created() {
    this.onLoad()
  },
  methods: {
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      getTowerList().then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        // 更新tableData中的数据
        this.tableData.forEach((item1, index) => {
          const arr = data.filter(item2 => {
            return item2.coal_tower_number == item1.coal_tower_number // 找到coal_tower_number一样的item
          })
          arr[0] && this.tableData.splice(index, 1, arr[0]) // 找到一样的item后，进行替换
        })

      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
    },
    //配煤
    handleEdit(type, index, row) {
      this.dialog.type = type
      this.dialog.title = '配煤'
      this.dialog.data = row;
      this.dialog.visible = true;
    },
    //详情
    handleView(type, index, row) {
      this.dialog.type = type
      this.dialog.title = '详情'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    cancel() {
      this.$emit('close')
    },
    //提供给子类调用的方法
    submitSave() {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad()
    },
    //子组件刷新数据
    sumRefresh() {
      this.onLoad()
    }
  }
}
</script>

<style scoped>

</style>
