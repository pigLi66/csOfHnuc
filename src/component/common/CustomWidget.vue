<script lang="ts">
import { defineComponent } from "vue";
import store from "../../store";
import { ElMessage } from "element-plus";

export default defineComponent({
  name: "ToolCard",

  props: {
    name: {
      type: String,
      required: true,
    },
    html: {
      type: String,
      required: true,
    },
    fixed: Boolean,
  },

  data() {
    return {
      initial: true,
      opacity: 0,
      state: store.state,
    };
  },
  methods: {
    addFixedToolCard() {
      const fixedToolCard = this.state.fixedWidgetCache;
      if (fixedToolCard[this.name]) {
        ElMessage.error(`已经添加了，请勿重复添加`);
      } else {
        const position = { top: 32, left: 32 };
        fixedToolCard[this.name] = position;
        localStorage[`ToolCard:${this.name}`] = JSON.stringify(position);
      }
    },

    deleteFixedToolCard() {
      const fixedToolCard = this.state.fixedWidgetCache;
      if (fixedToolCard[this.name]) {
        delete fixedToolCard[this.name];
        delete localStorage[`ToolCard:${this.name}`];
      }
    },

    isFixed(): boolean {
      const fixedToolCard = this.state.fixedWidgetCache;
      return fixedToolCard[this.name] !== undefined;
    },

    appendScript(htmlString: string) {
      const parser = new DOMParser();
      const doc = parser.parseFromString(htmlString, "text/html");
      const scriptTags = doc.getElementsByTagName("script");
      const scripts = Array.from(scriptTags).map((script) => {
        return {
          src: script.src,
          text: script.innerHTML,
        };
      });
      const srcScriptPromise = scripts
        .filter((it) => it.src)
        .map((it) => this.loadSrcScript(it.src));

      Promise.all(srcScriptPromise).then(() => {
        scripts
          .filter((it) => it.text)
          .forEach((it) => {
            const script = document.createElement("script");
            script.type = "text/javascript";
            script.text = it.text;
            document.body.appendChild(script);
          });
      });
      return scripts;
    },

    loadSrcScript(src: string) {
      return new Promise((resolve, reject) => {
        const script = document.createElement("script");
        script.type = "text/javascript";
        script.src = src;
        script.addEventListener("load", resolve);
        script.addEventListener("error", reject);
        document.body.appendChild(script);
      });
    },
  },

  mounted() {
    this.appendScript(this.html);
  },
});
</script>

<template>
  <div v-html="html"></div>
</template>

<style scoped lang="scss"></style>
