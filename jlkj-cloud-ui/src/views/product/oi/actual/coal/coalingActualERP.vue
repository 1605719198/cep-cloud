<template>
  <div class="main" style="width: 99%;border: 0 ">
    <div class="button">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="add"
                 style="float:right ; margin-top:10px">新 增
      </el-button>
    </div>
    <div>
      <el-form style="padding:20px 10px 0px 20px">
        <el-table height="80vh" size="small" :data="tableData" stripe>
          <template v-for="column in columns">
            <el-table-column :prop="column.prop" :label="column.label" :sortable="column.sortable"
                             :width="column.width" :min-width="column.minWidth" :key="column.prop"/>
          </template>
        </el-table>
      </el-form>
    </div>
    <el-dialog :visible.sync="dialogVisible"
               :title="title" width="600px"
               :destroy-on-close="true"
               :close-on-click-modal="false"
               append-to-body>
      <CoalingActualERPEditor @close="dialogVisible = false" />
    </el-dialog>
  </div>
</template>

<script>

import CoalingActualERPEditor from "@/views/product/oi/actual/coal/CoalingActualERPEditor";

export default {
  //import引入的组件
  components: {
    CoalingActualERPEditor
  },
  data() {
    return {
      tableData: [],
      columns: [
        {label: '编号', type: "index", minWidth: '100px'},
        {label: '班别', prop: "class_name", sortable: true, minWidth: '80px'},
        {
          label: '班次', prop: "shift_name", sortable: true, minWidth: '80px', formatter(row) {
            switch (row.shift_name) {
              case "01":
                return "常白班";
              case "2201":
                return "白班";
              case "2202":
                return "夜班";
              case "00":
                return "休息";
              default:
                return row.shift_name;
            }
          }
        },
        {label: '上煤仓号', prop: "1", sortable: true, minWidth: '100px'},
        {label: '堆取料机号', prop: "2", sortable: true, minWidth: '150px'},
        {label: '料仓名称', prop: "3", sortable: true, minWidth: '150px'},
        {label: '储位名称', prop: "4", sortable: true, minWidth: '150px'},
        {
          label: '上煤开始', prop: "5", sortable: true, minWidth: '150px',
          // formatter(row) {
          //     return row.start_time === null ? "" : moment(row.start_time).format('YYYY-MM-DD HH:mm:ss');
          // }
        },
        {
          label: '上煤结束', prop: "6", sortable: true, minWidth: '150px',
          // formatter(row) {
          //     return row.end_time === null ? "" : moment(row.end_time).format('YYYY-MM-DD HH:mm:ss');
          // }
        },
        {label: '上煤时长(min)', prop: "7", sortable: true, minWidth: '150px'},
        {label: '上煤量(t)', prop: "8", sortable: true, minWidth: '100px'},
        {label: '物料名称', prop: "9", sortable: true, minWidth: '100px'},
        {
          label: '创建时间', prop: "11", sortable: true, minWidth: '150px',
          //  formatter(row) {
          //     return row.create_time === null ? "" : moment(row.create_time).format('YYYY-MM-DD HH:mm:ss');
          // }
        },
        {label: '新增ERP时间', prop: "12", sortable: true, minWidth: '150px'},

      ],
      dialogVisible: false,
      query: {classType: '   '},
      title: "上煤实绩新增",
    };
  },
  //方法集合
  methods: {
    add() {
      this.dialogVisible = true;
    },
  }
  ,
  //生命周期 - 挂载完成（可以访问DOM元素）

}
</script>
<style scoped>
.main {
  height: calc(93vh - 10px);
  background-color: #fff;
  margin-left: 10px;
}

.button {
  padding-top: 10px;
  padding-right: 10px;
  padding-right: 10px;
  text-align: right;
}
</style>
