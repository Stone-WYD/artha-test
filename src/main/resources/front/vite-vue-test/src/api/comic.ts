import request from '@/utils/http.ts';

import type { Router } from '@/api/model/ComicModel.ts'
import type { ContentQuery } from './model/query/ComicQuery';

// 获取路由
export function getComicRouters(): Result<Router[]> {
    return request({
      url: '/comic/router',
      method: 'GET'
    });
}

// 查询动漫相关内容
export function getComicContent(data: ContentQuery): Result<String[]> {
  return request({
    url: '/comic/content',
    method: 'POST',
    data
  })
}
