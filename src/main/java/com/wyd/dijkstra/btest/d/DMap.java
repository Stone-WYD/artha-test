package com.wyd.dijkstra.btest.d;

import java.util.Map;
import java.util.Properties;

/**
 * @author xh
 * @date 2024-11-27
 * @Description:
 */
public interface DMap {

    Integer getNodeCount();

    Integer getDistance(Integer a, Integer b);

    Map<Integer, Integer> getArrivalNodes(Integer index);

    void init(Properties properties);
}
