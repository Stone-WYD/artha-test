// 查找动漫内容的查询条件
export interface ContentQuery {
    comicName: string;
    type: number,
    pageSize: number;
    pageNum: number;
}
