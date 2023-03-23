<template>
  <div class="avue-crud">
    <el-table size="small" :data="tableData" stripe>
      <el-table-column prop="safety_measures" label="安全措施">
        <template slot-scope="scope">
          <el-input v-if="scope.row.is_confirm === 0 && data.status === 0" v-model="scope.row.safety_measures"/>
          <template v-else>{{scope.row.safety_measures}}</template>
        </template>
      </el-table-column>
      <el-table-column prop="confirm_time" label="确认时间" width="130px" fixed="right"/>
      <el-table-column prop="confirm_person_name" label="确认人" width="100px" fixed="right"/>
      <el-table-column label="确认" width="100px" fixed="right" v-if="data.status === 0">
        <template slot-scope="scope">
          <el-button v-if="scope.row.is_confirm === 0" size="mini" plain type="primary"
                     @click="handleConfirm(scope.row)">确认
          </el-button>
          <el-button v-else size="mini" plain type="text" @click="handleCancel(scope.row.id)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="confirmUserOpen">
      <personnel-selection v-if="confirmUserOpen" :userDialogVisible="confirmUserOpen"
                           @close-dialog="confirmUserOpen = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
    </div>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";
  import {mapGetters} from "vuex";

  export default {
    name: "heightInfoSecurity",
    props: ['data', 'type', 'ticketId'],
    data() {
      return {
        form: {
          id: ''
        },
        loading: true,
        tableData: [],
        row: null,
        confirmUserOpen: false,
      };
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      if (!this.data.id) {
        this.data.status = 0;
      }
      this.form = this.data;
      this.onLoad();
    },
    methods: {
      onLoad() {
        if (this.form.id) {
          req('get', 'high/safetymeasures/list', {id: this.form.id}).then(res => {
            this.tableData = res.data.data;//表格数据
            this.loading = false;
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        } else {
          req('get', 'high/safetymeasures/list', {id: this.ticketId}).then(res => {
            this.tableData = res.data.data;//表格数据
            this.loading = false;
          }, error => {
            this.loading = false;
            window.console.log(error);
          });
        }
      },
      handleConfirm(row) {
        this.confirmUserOpen = true;
        this.row = row;
      },
      handleNodeClick(data) {
        let confirm_person_id = data.id;
        let confirm_person_name = data.full_name;
        req('post', 'high/confirm/safetymeasures', {
          id: this.row.id,
          safety_measures: this.row.safety_measures,
          confirm_person_id: confirm_person_id,
          confirm_person_name: confirm_person_name,
        }).then(() => {
          this.$message({type: "success", message: "确认成功！"});
          this.onLoad();
        }, error => {
          window.console.log(error);
        });
      },
      handleCancel(id) {
        req('post', 'high/cancel/safetymeasures', {id: id}).then(() => {
          this.$message({type: "success", message: "取消成功！"});
          this.onLoad();
        }, error => {
          window.console.log(error);
        });
      },
    },
    watch: {
      ticketId() {
        this.onLoad();
      }
    }
  }
</script>

<style scoped>

</style>
