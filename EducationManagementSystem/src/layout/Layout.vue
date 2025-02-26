<template>
  <Header/>
  <!-- 主体 -->
  <div class="container">
    <!-- 侧边栏 -->
    <Aside/>
    <!-- 内容区域 -->
    <router-view/>
  </div>
</template>

<script setup>
import Header from '../components/Header.vue';
import Aside from '../components/Aside.vue';
</script>

<style scoped>
.container {
  display: flex;
}
</style>
