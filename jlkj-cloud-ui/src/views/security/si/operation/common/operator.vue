<template>
  <div>
    <div style="text-align: right;padding:0 40px 10px 0;">
      <el-button v-if="isEdit" type="primary" @click="personOpen = true">添加作业人</el-button>
    </div>
    <el-table size="small" :data="person_list" stripe v-loading="loading">
      <el-table-column prop="person_name" label="作业人"/>
      <el-table-column prop="construction_unit_name" label="所属单位名称"/>
      <el-table-column prop="type" label="所属单位类型">
        <template slot-scope="scope">
          {{ scope.row.type === 1 ? '本厂':scope.row.type === 2 ? '外委':'' }}
        </template>
      </el-table-column>
      <el-table-column prop="work_type_name" label="特种作业工种"/>
      <el-table-column prop="work_card_code" label="特种作业证号"/>
      <el-table-column label="操作" width="100px" v-if="isEdit">
        <template slot-scope="scope">
          <el-button size="mini" plain icon="el-icon-delete" type="text"
                     @click="handleDelete(scope.$index)"/>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="personOpen">
      <el-dialog class="customDialogStyle" title="作业人员" :visible.sync="personOpen" width="600px"
                 append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <operatorAdd @close="personOpen=false" @setPersonForm="setPersonForm" :personList="person_list"/>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";
  import operatorAdd from "./operatorAdd";

  export default {
    name: "operator",
    props: ['data', 'isEdit', 'person', 'type'],
    components: {
      operatorAdd,
    },
    data() {
      return {
        person_list: [], loading: true, personOpen: false,
      }
    },
    mounted() {
      if (this.data.copyId) {
        req('get', this.type + '/person/list', {id: this.data.copyId}).then(res => {
          this.person_list = res.data.data;//表格数据
          this.data.person_list = this.person_list;
          this.loading = false;
        }, error => {
          this.loading = false;
          window.console.log(error);
        });
      }else if (this.data.id) {
        req('get', this.type + '/person/list', {id: this.data.id}).then(res => {
          this.person_list = res.data.data;//表格数据
          this.data.person_list = this.person_list;
          this.loading = false;
        }, error => {
          this.loading = false;
          window.console.log(error);
        });
      } else {
        this.loading = false;
      }
    },
    methods: {
      setPersonForm(form) {
        this.person_list.push(form);
        this.data.person_list = this.person_list;
        this.personOpen = false;
      },
      handleDelete(index) {
        delete this.person_list.splice(index, 1);
        this.data.person_list = this.person_list;
      },
    },
  }
</script>

<style scoped>

</style>
