<script setup>
import { ref, onMounted } from 'vue';

const props = defineProps({
  width: {
    type: String,
    default: '100px'
  },
  height: {
    type: String,
    default: '40px'
  },
  length: {
    type: Number,
    default: 4
  }
});

const emit = defineEmits(['input']);

const codeList = ref([]);

const createCode = () => {
  const len = props.length;
  const newCodeList = [];
  const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789';
  const charsLen = chars.length;

  for (let i = 0; i < len; i++) {
    const rgb = [Math.round(Math.random() * 220), Math.round(Math.random() * 240), Math.round(Math.random() * 200)];
    newCodeList.push({
      code: chars.charAt(Math.floor(Math.random() * charsLen)),
      color: `rgb(${rgb})`,
      fontSize: `${10 + (+[Math.floor(Math.random() * 10)] + 6)}px`,
      padding: `${[Math.floor(Math.random() * 10)]}px`,
      transform: `rotate(${Math.floor(Math.random() * 45) - Math.floor(Math.random() * 45)}deg)`
    });
  }

  codeList.value = newCodeList;
  emit('input', newCodeList.map(item => item.code).join(''));
};

const refreshCode = () => {
  createCode();
};

const getStyle = (data) => {
  return `color: ${data.color}; font-size: ${data.fontSize}; padding: ${data.padding}; transform: ${data.transform}`;
};

onMounted(() => {
  createCode();
});
</script>


<template>
  <div
      class="validCode disabled-select"
      :style="`width:${width}; height:${height}`"
      @click="refreshCode"
  >
    <span
        v-for="(item, index) in codeList"
        :key="index"
        :style="getStyle(item)"
    >{{ item.code }}</span>
  </div>
</template>


<style scoped>
.validCode {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.validCode span {
  display: inline-block;
}
</style>
