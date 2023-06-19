<script lang="ts">

import {defineComponent} from "vue";

export default defineComponent({

  data() {
    return {
      modelName: 'tororo',
      live2d: {
        live2d: {
          jsonPath: "https://unpkg.com/live2d-widget-model-tororo/assets/tororo.model.json",
          scale: 1 // 比例
        },
        display: {
          position: "right", // 看板娘的表现位置
          width: 100, // 模型的宽度
          height: 200, // 模型的高度
          hOffset: 0,
          vOffset: -20
        },
        mobile: {
          show: true,
          scale: 0.5
        },
        react: {
          opacityDefault: 0.7,
          opacityOnHover: 0.2
        }
      }
    }
  },

  methods: {
    getModelUrl(modelName: string) {
      return `https://unpkg.com/live2d-widget-model-${modelName}/assets/${modelName}.model.json`
    }
  },

  mounted() {
    // 引入远程js
    let scriptEl = document.createElement("script")
    scriptEl.type = 'text/javascript'
    scriptEl.src = 'https://eqcn.ajz.miesnfu.com/wp-content/plugins/wp-3d-pony/live2dw/lib/L2Dwidget.min.js'

    document.body.appendChild(scriptEl)

    scriptEl.onload = () => {
      let L2DWidget;
      L2DWidget.init(this.live2d);
    }
  }
})
</script>

<template>

</template>

<style scoped lang="scss">

</style>
