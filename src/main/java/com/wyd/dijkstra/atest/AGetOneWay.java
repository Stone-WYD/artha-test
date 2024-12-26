package com.wyd.dijkstra.atest;

import java.util.*;

/**
 * @author xh
 * @date 2024-11-20
 * @Description:
 */
public class AGetOneWay {

    private Stack<OnePossibleNodes> openQueue = new Stack<>();
    private Set<String> passNodeSet = new HashSet<>();

    private Set<String> noMoveSet;
    private ATest.Node beginNode;
    private ATest.Node endNode;

    public AGetOneWay(ATest.Node beginNode, ATest.Node endNode, Set<String> noMoveSet) {
        this.beginNode = beginNode;
        this.noMoveSet = noMoveSet;
        this.endNode = endNode;
    }

    public ATest.Node getResultNode() {
        beginNode.pastCount = 0;
        beginNode.lastCount = dis(beginNode.x, beginNode.y, endNode);
        // 起始位置初始化
        ATest.Node resultNode = beginNode;
        passNodeSet.add(resultNode.x + "," + resultNode.y);
        OnePossibleNodes initPossibleNodes = new OnePossibleNodes(resultNode, endNode, passNodeSet, noMoveSet);
        openQueue.push(initPossibleNodes);

        while (resultNode.x != endNode.x || resultNode.y != endNode.y) {
            // 判断是否第一次到当前位置
            if (passNodeSet.contains(resultNode.x + "," + resultNode.y)) {
                // 回溯之后回到该位置
                if (openQueue.peek()!=null && openQueue.peek().hasNext()) {
                    // 当前位置还有方向能走，继续向下走
                    OnePossibleNodes possibleNodes = openQueue.peek();
                    ATest.Node next = possibleNodes.next();
                    next.preNode = resultNode;
                    resultNode = next;
                } else {
                    // 当前位置所有可能方向都是死路，如果当前是起始位置，则返回空
                    if (resultNode == beginNode) {
                        return null;
                    }

                    openQueue.pop();
                    resultNode = resultNode.preNode;
                }
            } else {
                // 第一次来到，加入已走过位置 set，计算周围位置
                passNodeSet.add(resultNode.x + "," + resultNode.y);
                OnePossibleNodes possibleNodes = new OnePossibleNodes(resultNode, endNode, passNodeSet, noMoveSet);
                ATest.Node next = possibleNodes.next();
                if (next != null) {
                    // 周围有移动位置，则将周围点加入
                    openQueue.push(possibleNodes);
                    // 选择离终点最近的点移动一步
                    next.preNode = resultNode;
                    resultNode = next;
                } else {
                    // 周围没有移动位置，说明当前位置是死路，则回溯到上一步
                    resultNode = resultNode.preNode;
                }
            }
        }

        return resultNode;
        // return getFastWay(resultNode);
    }

    public ATest.Node getFastResultNode() {
        ATest.Node resultNode = getResultNode();
        ATest.Node fastWay = getFastWay(resultNode);
        return ATest.revert(fastWay);
    }

    private ATest.Node getFastWay(ATest.Node way) {
        // 返回的结果是从起点开始到终点的
        // 调转 way 的方向
        ATest.Node revertWay = ATest.revert(way);
        if (revertWay.preNode == null || revertWay.preNode.preNode == null) {
            return revertWay;
        }
        ATest.Node cur = revertWay.preNode.preNode;
        while (cur.x != endNode.x || cur.y != endNode.y) {
            // 以当前坐标为终点
            ATest.Node end = new AGetOneWay(beginNode.copy(), cur.copy(), noMoveSet).getResultNode();
            if (end.pastCount < cur.pastCount) {
                ATest.Node temp = end;
                revertWay = ATest.revert(end);
                temp.preNode = cur.preNode;
                cur.preNode = null;
                cur = temp.preNode;
                // 调整后续点的 pastCount、totalCount
                int lastCount = temp.pastCount;
                while(temp.preNode != null) {
                    temp = temp.preNode;
                    lastCount++;
                    temp.pastCount = lastCount;
                    temp.totalCount = temp.pastCount + temp.lastCount;
                }
            } else {
                cur = cur.preNode;
            }
        }
        return revertWay;
    }

    private int dis(int x, int y, ATest.Node endNode) {
        int disX = endNode.x - x;
        int disY = endNode.y - y;
        disX = disX > 0 ? disX : -disX;
        disY = disY > 0 ? disY : -disY;
        return disX + disY;
    }

    class OnePossibleNodes {
        // 这个类中包含了一次移动后，下次可移动位置的集合（每次回溯之后，可移动的方向会减一）。
        private Set<String> noMoveSet;
        private ATest.Node endNode;
        // 一次移动后可能的坐标集合
        private List<PossibleNode> possibleNodes;

        public OnePossibleNodes(ATest.Node node, ATest.Node endNode, Set<String> passNodeSet, Set<String> noMoveSet) {
            if (node == null) return;
            this.endNode = endNode;
            this.noMoveSet = noMoveSet;
            this.possibleNodes = getPossibleLocation(node, node.x, node.y, passNodeSet);
        }

        private List<PossibleNode> getPossibleLocation(ATest.Node nowNode, Integer x, Integer y, Set<String> passNodeSet) {
            List<PossibleNode> result = new ArrayList<>();
            possible(nowNode, x+1, y, passNodeSet, result);
            possible(nowNode, x-1, y, passNodeSet, result);
            possible(nowNode, x , y+1, passNodeSet, result);
            possible(nowNode, x, y-1, passNodeSet, result);
            return result;
        }

        private void possible(ATest.Node nowNode, int x, int y, Set<String> passNodeSet, List<PossibleNode> result) {
            // todo 当前设计暂定边界为固定边框内，后根据实际情况调整
            if (!passNodeSet.contains(x + "," + y) && !noMoveSet.contains(x + "," + y) && 0 <=x && x < 10 && 0 <= y && y < 10) {
                result.add(new PossibleNode(x, y, dis(x,y,endNode), nowNode.pastCount + 1));
            }
        }

        public boolean hasNext() {
            for (PossibleNode possibleNode : possibleNodes) {
                if (!possibleNode.haveMoved) {
                    return true;
                }
            }
            return false;
        }

        public ATest.Node next() {
            PossibleNode result = null;
            for (PossibleNode possibleNode : possibleNodes) {
                if (!possibleNode.haveMoved) {
                    if (result == null) {
                        result = possibleNode;
                    } else {
                        result = result.totalCount > possibleNode.totalCount ? possibleNode : result;
                    }
                }
            }
            if (result != null) {
                result.haveMoved = true;
            }
            return result;
        }
    }

    class PossibleNode extends ATest.Node {
        boolean haveMoved;

        public PossibleNode(Integer x, Integer y) {
            super(x, y);
        }

        public PossibleNode(Integer x, Integer y, Integer lastCount, Integer pastCount) {
            super(x, y, lastCount, pastCount);
        }
    }
}
