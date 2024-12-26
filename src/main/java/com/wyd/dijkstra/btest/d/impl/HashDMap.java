package com.wyd.dijkstra.btest.d.impl;


import com.wyd.dijkstra.btest.d.DMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @author xh
 * @date 2024-11-27
 * @Description:
 */
public class HashDMap implements DMap {

    private final Map<Integer, HashMap<Integer, Integer>> map = new HashMap<>();

    @Override
    public Integer getNodeCount() {
        return map.keySet().size();
    }

    @Override
    public Integer getDistance(Integer a, Integer b) {
        // a 到 b 的距离（注意：b 不一定能到 a，因为可能是单向的）
        HashMap<Integer, Integer> abDis = map.get(a);
        if (abDis != null) {
            Integer i = abDis.get(b);
            if (i != null) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Map<Integer, Integer> getArrivalNodes(Integer index) {
        return map.get(index);
    }

    @Override
    public void init(Properties properties) {
        // todo 有参数的情况下根据参数初始化 map(考虑数据库获取点位信息)，目前没有，则直接写死
        HashMap<Integer, Integer> firstMap = new HashMap<>();
        HashMap<Integer, Integer> secondMap = new HashMap<>();
        HashMap<Integer, Integer> thirdMap = new HashMap<>();
        HashMap<Integer, Integer> fourthMap = new HashMap<>();
        HashMap<Integer, Integer> fifthMap = new HashMap<>();
        HashMap<Integer, Integer> sixthMap = new HashMap<>();
        map.put(0, firstMap);
        map.put(1, secondMap);
        map.put(2, thirdMap);
        map.put(3, fourthMap);
        map.put(4, fifthMap);
        map.put(5, sixthMap);

        firstMap.put(1, 2);firstMap.put(2,3);firstMap.put(3,6);
        secondMap.put(0,2);secondMap.put(4,4);secondMap.put(5,6);
        thirdMap.put(0,3);thirdMap.put(3,2);
        fourthMap.put(0,6);fourthMap.put(2,2);fourthMap.put(4,1);fourthMap.put(5,3);
        fifthMap.put(1,4);fifthMap.put(3,1);
        sixthMap.put(1,6);sixthMap.put(3,3);
    }

    public static HashDMap getInstance(String mapId) {
        HashDMap instance = new HashDMap();//记录权值，顺便记录链接情况，可以考虑附加邻接表
        Properties prop = new Properties();
        prop.setProperty("mapId", String.valueOf(mapId));
        instance.init(prop);
        return instance;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(21);
        System.out.println(i);
    }
}
