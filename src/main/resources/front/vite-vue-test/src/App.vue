<script setup lang="ts">
import type { Router } from '@/api/model/ComicModel.ts'
import {onMounted, ref} from 'vue';
import { getComicRouters } from '@/api/comic';
const routers = ref([] as Router[]);

const getRouters = async () => {
	const res = await getComicRouters()
	routers.value = res.data
};
onMounted(()=> getRouters());

</script>

<template>
	<div class="container-fluid wraper">
		<h1 class="title">
			wyd's test, comic.
		</h1>
		<hr>
		<div class="row">
			<div class="col-xs-3 col-md-3 col-lg-3 col-xl-3">
				<!-- 导航区 -->
				<div v-for="router in routers" :key="router.order">
					<router-link active-class="active" class="list-group-item"
            :to="`${router.to}?comicName=${router.name}`">
            {{ router.name }}</router-link>
				</div>
			</div>
			<div class="col-xs-9 col-md-9 col-lg-9 col-xl-9">
				<div class="panel-body">
					<!-- 占位一个展示区 -->
					<router-view></router-view>
				</div>
			</div>
		</div>
	</div>
</template>

<style>

	/* .list-group-item {
		margin: 10px;
		background-color: greenyellow;
	} */
	.wraper .title {
		padding: 20px;
		text-align: center;
		min-width: 610px;
	}
	.wraper .small{
		font-size: 15px;
	}
	.wraper .list-group-item {
		min-width: 130px;
	}
</style>
