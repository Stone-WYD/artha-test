package com.wyd.dijkstra.atest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xh
 * @date 2024-11-20
 * @Description: A星测试
 */
public class ATest {


    public static void main(String[] args) {
        // testPrintRoute();
        // 添加阻碍坐标 测试用参数，斜着封口，但留下一个出口
        List<Node> noMoveList = new ArrayList<>();
        noMoveList.add(new Node(0,9));
        noMoveList.add(new Node(1,8));
        noMoveList.add(new Node(2,7));
        noMoveList.add(new Node(3,6));
//        noMoveList.add(new Node(4,5));
        noMoveList.add(new Node(5,5));
        noMoveList.add(new Node(5,4));
        noMoveList.add(new Node(6,3));
        noMoveList.add(new Node(7,2));
        noMoveList.add(new Node(8,1));
        noMoveList.add(new Node(9,0));


//        noMoveList.add(new Node(1,1));
//        // noMoveList.add(new Node(0,2));
//        noMoveList.add(new Node(2,0));

        Node way = getWay(noMoveList);
        if (way == null) {
            System.out.println("无法到达目的地！");
        } else {
            System.out.println(way.printRouteWithBlocks(noMoveList));
        }

    }


    static Node getWay(List<Node> noMoveList) {
        // 假设图的起点为 0，0  终点为 10，10，传入参数为不可移动到的坐标，返回结果为移动路线
        // 1.将阻碍坐标转换为 map，便于查询
        // 2.设置 open、close列表
        Node begin = new Node(0, 0);
        Node endNode = new Node(9,9);
        Set<String> noMoveSet = new HashSet<>();
        for (Node node : noMoveList) {
            noMoveSet.add(node.x + "," + node.y);
        }
        AGetOneWay aGetOneWay = new AGetOneWay(begin, endNode, noMoveSet);
        return aGetOneWay.getFastResultNode();
        // return aGetOneWay.getResultNode();
    }

    static class Node {
        Integer x;
        Integer y;
        Integer totalCount;
        Integer lastCount;
        Integer pastCount;
        Node preNode;

        public Node(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public Node(Integer x, Integer y, Integer lastCount, Integer pastCount) {
            this.x = x;
            this.y = y;
            this.lastCount = lastCount;
            this.pastCount = pastCount;
            this.totalCount = lastCount + pastCount;
        }

        public String printRoute() {
            Node preNode = this;
            int[][] map = new int[10][10];

            while (preNode != null) {
                map[preNode.y][preNode.x] = 1;
                preNode = preNode.preNode;
            }
            StringBuilder sb = new StringBuilder();
            for (int[] integers : map) {
                for (Integer integer : integers) {
                    if (integer == 0) {
                        sb.append(" ");
                    } else sb.append("X");
                } sb.append("\n");
            }
            return sb.toString();
        }

        public String printRouteWithBlocks(List<Node> noMoveList) {
            Node preNode = this;
            int[][] map = new int[10][10];

            while (preNode != null) {
                map[preNode.y][preNode.x] = 1;
                preNode = preNode.preNode;
            }
            // 障碍物
            for (Node node : noMoveList) {
               map[node.y][node.x] = -1;
            }
            StringBuilder sb = new StringBuilder();
            for (int[] integers : map) {
                for (Integer integer : integers) {
                    if (integer == 0) {
                        sb.append(" ");
                    } else if(integer == 1) {
                        sb.append("O");
                    } else if (integer == -1) {
                        sb.append("X");
                    }
                } sb.append("\n");
            }
            return sb.toString();
        }

        public Node copy() {
            return new Node(x, y);
        }
    }

    static void testPrintRoute() {
        Node node0 = new Node(0, 0);
        Node node1 = new Node(0, 1);
        Node node2 = new Node(1, 1);
        Node node3 = new Node(2, 1);
        node3.preNode = node2;
        node2.preNode = node1;
        node1.preNode = node0;
        System.out.println(node3.printRoute());
    }

    static Node revert(Node node) {
        // 调转 way 的方向
        Node back = node;
        Node front = node.preNode;
        // 初始化
        back.preNode = null;
        while (back != null && front != null) {
            Node temp = front.preNode;
            front.preNode = back;
            back = front;
            front = temp;
        }
        return front == null ? back : front;
    }

    private static void testRevert() {
        Node node = new Node(1, 1);
        Node node1 = new Node(2, 2);
        Node node2 = new Node(3, 3);
        node.preNode = node1;
        node1.preNode = node2;

        Node cur = node;
        while(cur!=null) {
            System.out.print("(" + cur.x + "," + cur.y + ")");
            cur = cur.preNode;
        }
        Node reverNode = revert(node);
        System.out.println();
        cur = reverNode;
        while(cur!=null) {
            System.out.print("(" + cur.x + "," + cur.y + ")");
            cur = cur.preNode;
        }
    }
}
