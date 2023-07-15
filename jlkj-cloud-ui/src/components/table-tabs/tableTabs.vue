<template>
  <el-row>
    <div class="titleClass">
      <i class="el-icon-s-flag" />
      <span>&nbsp;&nbsp;&nbsp;{{title}}</span>
    </div>
    <el-menu default-active="0"
             class="el-menu-vertical-demo">
      <el-menu-item v-for="(item,i) in tableList"
                    :key="i"
                    :index="i+''"
                    @click="handleChange(item.code)">
        <i :class="item.icon" />
        <span slot="title">{{item.title}}</span>
      </el-menu-item>
    </el-menu>
  </el-row>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: {
    title: {
      type: String
    },
    tableList: {
      type: Array,
      require: true
    },
  },
  data () {
    return {

    };
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  created () {
  },
  methods: {
    handleChange (val) {
      this.$emit('handleChange', val)
    }
  }
}
</script>

<style scoped>
.titleClass {
  height: 70px;
  text-align: center;
  line-height: 70px;
  font-size: 20px;
  color: #999999;
  font-weight: 400;
  text-decoration: none;
}

.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
