<template>
  <div>

  </div>
</template>

<script>

export default {
  name: "loan",

  data() {
    return {}
  },
  props: ['billNo'],
  mounted() {
    this.$watch('billNo', function (newVal) {
      console.log(newVal+'billNo');

    })

  },
  created() {


  },
  methods: {}
};
</script>
