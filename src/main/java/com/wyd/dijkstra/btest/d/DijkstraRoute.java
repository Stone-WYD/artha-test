package com.wyd.dijkstra.btest.d;

import com.wyd.magicapitest.btest.d.impl.HashDMap;

import java.util.*;

/**
 * @author xh
 * @date 2024-11-27
 * @Description:
 */
public class DijkstraRoute {

    private Integer beginIndex;

    private Integer endIndex;

    private final DMap map;

    private int[] lengthArrays;

    public DijkstraRoute(Integer beginIndex, Integer endIndex, DMap map) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.map = map;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
        this.lengthArrays = getLengthArrays();
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public List<Integer> getRouter() {
        if (beginIndex==null || endIndex==null) {
            throw new RuntimeException("没有设置起点或终点！");
        }
        // 1. 获取起点到各个点的最短距离
        if (lengthArrays == null) {
            lengthArrays = getLengthArrays();
        }

        // 2. 通过 lengthArrays 获取起点到终点最短路径上各点 id
        return getResultWithLengthArrays();
    }

    private List<Integer> getResultWithLengthArrays() {
        int totalLength = lengthArrays[endIndex];
        if (totalLength == Integer.MAX_VALUE) {
            return null;
        }
        Stack<Integer> stack = new Stack<>();
        // 压入终点节点

        int curIndex = endIndex;
        int lastIndex;
        stack.push(endIndex);
        for (int i = 0; i < lengthArrays.length; i++) {
            if (map.getArrivalNodes(i).containsKey(endIndex)) {
                // i 能到达终点
                if (lengthArrays[endIndex] - map.getArrivalNodes(i).get(endIndex) == lengthArrays[i]) {
                    curIndex = i;
                    break;
                }
            }
        }

        while (true) {
            stack.push(curIndex);
            if (beginIndex.equals(curIndex)) {
                break;
            }
            lastIndex = curIndex;
            for (int i = 0; i < lengthArrays.length; i++) {
                if (map.getArrivalNodes(i).containsKey(lastIndex)) {
                    // i 能到达下个点
                    if (lengthArrays[lastIndex] - map.getArrivalNodes(i).get(lastIndex) == lengthArrays[i]) {
                        curIndex = i;
                        break;
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private int[] getLengthArrays() {
        if (beginIndex==null) {
            throw new RuntimeException("没有设置起点！");
        }
        if (map.getArrivalNodes(beginIndex) == null ||
                map.getArrivalNodes(beginIndex).isEmpty()) {
            return null;
        }
        Integer nodeCount = map.getNodeCount();
        boolean[] bool =new boolean[nodeCount];//判断是否已经确定
        int[] len =new int[nodeCount];//长度
        Arrays.fill(len, Integer.MAX_VALUE);
        Queue<DNode> q1= new PriorityQueue<>(Comparator.comparingInt(o -> o.totalLength));
        len[beginIndex]= 0; // 开始点的据离为 0
        q1.add(new DNode(beginIndex, 0));
        while (!q1.isEmpty()) {
            DNode t1=q1.poll();
            int index=t1.id;//节点编号
            int length=t1.totalLength;//节点当前点距离
            bool[index]=true;//抛出的点确定

            Map<Integer, Integer> arrivalNodes = map.getArrivalNodes(index);
            if (arrivalNodes.isEmpty()) {
                continue;
            }
            for (Map.Entry<Integer, Integer> arrivalNode : arrivalNodes.entrySet()) {
                Integer desIndex = arrivalNode.getKey();
                Integer dis = arrivalNode.getValue();
                if(dis>0&&!bool[desIndex])
                {
                    DNode node= new DNode(desIndex, length + dis);
                    if(len[desIndex]>node.totalLength)//需要更新节点的时候更新节点并加入队列
                    {
                        len[desIndex]=node.totalLength;
                        q1.add(node);
                    }
                }
            }
        }
        return len;
    }

    private static class DNode {
        int id; //节点编号
        int totalLength;//到出发点的据离
        public DNode(int id, int totalLength) {
            this.id = id;
            this.totalLength = totalLength;
        }
    }

    public static void main(String[] args) {
        DijkstraRoute route = new DijkstraRoute(4, 5, HashDMap.getInstance(null));
        List<Integer> router = route.getRouter();
        StringBuilder sb = new StringBuilder();
        for (Integer id : router) {
            sb.append(id + "->");
        }
        System.out.println(sb.substring(0, sb.length()-2));
    }
}
