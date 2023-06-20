<script lang="ts">

import {defineComponent} from "vue";

export default defineComponent({
  props: {
    value: String
  },
  data() {
    return {
      keyword: this.value,
      isFocus: false
    }
  },
  methods: {
    valueIsNotBlank(): boolean {
      if (!this.keyword) {
        return false
      }
      return this.keyword !== ''
    },

    openSearchInput(): boolean {
      return this.isFocus || this.valueIsNotBlank()
    },

    input(newVar: string) {
      this.keyword = newVar
      this.$emit("change", this.keyword)
    }
  }
})

</script>

<template>

  <div :class="{
    'search-box': true,
    'open-search-input': openSearchInput()
  }">
    <!--suppress TypeScriptValidateTypes -->
    <input class="search-input" type="text"
           v-model="keyword"
           @input="input($event.target.value)"
           @focusin="isFocus=true"
           @focusout="isFocus=false"
    />
    <el-button :class="{'search-btn': true,
    'open-search-input-btn': openSearchInput()
    }" circle>
      <font-awesome-icon
          class="search-icon"
          :icon="['fas', 'magnifying-glass']"
      />
    </el-button>
  </div>
</template>

<style scoped lang="scss">
.search-box {
  height: 40px;
  border-radius: 40px;
  padding: 0px 10px;

  background: rgba(#ffffff, 0.5);
  backdrop-filter: blur(10px);
  width: 40px;
  position: relative;
  transition: all 0.3s;
  box-shadow: 0 3px 12px rgba(193, 202, 203, 0.8);
}


.search-box:hover,
.open-search-input {
  width: 100%;
  background: #ffffff;
}

.search-btn {
  color: #9d9d9d;
  float: right;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: #ffffff;
  display: flex;
  justify-content: center;
  align-item: center;
  text-decoration: none;
  transition: all 0.3s;
  position: absolute;
  top: 0px;
  left: 0px;
}

.open-search-input-btn,
.search-box:hover > .search-btn {
  background: none;
  left: calc(100% - 40px);
}


.search-input {
  padding: 0;
  border: none;
  background: none;
  outline: none;
  float: left;
  margin-top: 3px;
  color: #4d4d4d;
  font-size: 16px;
  transition: 0.2s;
  line-height: 36px;
  width: calc(100% - 25px);
}

.el-input__wrapper {
  box-shadow: none;
}
</style>
