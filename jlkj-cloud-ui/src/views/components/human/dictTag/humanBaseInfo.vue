<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.dicNo)&&(isId==='false')&&(uuid==='false')">
        <span
          :key="item.index"
          :index="index"
        >{{ item.dicName }}</span
        >
      </template>
      <template v-if="(isId==='true')&&(uuid==='false')&&values.includes(item.id.toString())">
        <span
          :key="item.id"
          :index="index"
        >{{ item.dicName }}</span
        >
      </template>
      <template v-if="values.includes(item.uuid)&&(uuid==='true')&&(isId==='false')">
        <span
          :key="item.uuid"
          :index="index"
        >{{ item.dicName }}</span
        >
      </template>
    </template>
  </div>
</template>

<script>
export default {
  name: "DictTag",
  props: {
    options: {
      type: Array,
      default: null,
    },
    value: [Number, String, Array],
    isId: {
      type: String,
      default: 'false'
    },
    uuid:{
      type: String,
      default: 'false'
    }
  },
  computed: {
    values() {
      if (this.value !== null && typeof this.value !== 'undefined') {
        return Array.isArray(this.value) ? this.value : [String(this.value)];
      } else {
        return [];
      }
    },
  },
};
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
