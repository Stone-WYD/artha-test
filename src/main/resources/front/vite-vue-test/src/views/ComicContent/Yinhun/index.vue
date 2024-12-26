<template>
  <el-tabs v-model="reqData.type" class="demo-tabs" @tab-change="tabChange">
    <el-tab-pane default label="图片" :name="1" />
    <el-tab-pane label="切片" :name="2" />
  </el-tabs>
  <template v-if="reqData.type === 1">
      <!-- 图片展示 -->
      <div class="demo-image__lazy">
      <el-image v-for="url in contents" :key="url" :src="url" lazy />
  </div>
  </template>
  <template v-else-if="reqData.type === 2">

  </template>
</template>
<script lang="ts" setup>
import { ref, onMounted } from "vue";
import type { ContentQuery } from "@/api/model/query/ComicQuery";
import {useRoute} from 'vue-router';
import { getComicContent } from '@/api/comic'

const route = useRoute()
const reqData = ref<ContentQuery>({
  comicName: String(route.query.comicName),
  type: 1,
  pageSize: 10,
  pageNum: 1,
});
// 动漫内容数据
const contents = ref<String[]>([]);
const getContent = async () => {
	const res = await getComicContent(reqData.value)
  console.log('内容接口返回：' + res.data)
  contents.value = res.data.map(str => import.meta.env.VITE_URL + str)
  console.log(contents.value)
};
onMounted(()=> getContent());

const tabChange = () => {
  // TODO 标签切换时更新查询数据
  reqData.value.pageNum = 1;
  getContent()
};
</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.demo-image__lazy {
  height: 800px;
  overflow-y: auto;
}
.demo-image__lazy .el-image {
  display: block;
  min-height: 50px;
  margin-bottom: 10px;
}
.demo-image__lazy .el-image:last-child {
  margin-bottom: 0;
}
</style>
