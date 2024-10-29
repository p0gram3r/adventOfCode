package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day15 implements Puzzle {

    private static final Logger log = LoggerFactory.getLogger(Day15.class);

    @Override
    public String solutionA(List<String> input) {
        var map = parseCavernMap(input);

        var graph = createGraph(map);
        calculateShortestPathFromSource(graph, graph.getNode(0, 0));

        int maxX = map[0].length - 1;
        int maxY = map.length - 1;
        return graph.getNode(maxX, maxY).getDistance().toString();
    }

    @Override
    public String solutionB(List<String> input) {
        var map = parseCavernMap(input);
        map = scaleToFullCavernMap(map);

        var graph = createGraph(map);
        calculateShortestPathFromSource(graph, graph.getNode(0, 0));

        int maxX = map[0].length - 1;
        int maxY = map.length - 1;
        return graph.getNode(maxX, maxY).getDistance().toString(); // "2876";
    }

    @Value
    static class Graph {
        Collection<Node> nodes = new ArrayList<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        public Node getNode(int x, int y) {
            return nodes.stream()
                    .filter(n -> n.x == x)
                    .filter(n -> n.y == y)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Node not found"));
        }
    }

    @Data
    static class Node {
        final int x, y, risk;
        @EqualsAndHashCode.Exclude
        List<Node> shortestPath = new LinkedList<>();
        @EqualsAndHashCode.Exclude
        Integer distance = Integer.MAX_VALUE;
        @EqualsAndHashCode.Exclude
        Map<Node, Integer> adjacentNodes = new HashMap<>();

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public Node(int x, int y, int risk) {
            this.x = x;
            this.y = y;
            this.risk = risk;
        }

        public String toString() {
            return x + " / " + y + " (" + risk + ")";
        }
    }

    static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    static Graph createGraph(int[][] map) {
        Graph graph = new Graph();

        int xLength = map[0].length;
        int yLength = map.length;


        Map<Integer, Map<Integer, Node>> adjacentNodes = new TreeMap<>();
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                Node node = new Node(x, y, map[y][x]);
                graph.addNode(node);
                adjacentNodes.computeIfAbsent(y, k -> new TreeMap<>()).put(x, node);
            }
        }

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                Node node = adjacentNodes.get(y).get(x);

                if (x > 0) {
                    node.addDestination(adjacentNodes.get(y).get(x - 1), node.risk);
                }
                if (x < xLength - 1) {
                    node.addDestination(adjacentNodes.get(y).get(x + 1), node.risk);
                }
                if (y > 0) {
                    node.addDestination(adjacentNodes.get(y - 1).get(x), node.risk);
                }
                if (y < yLength - 1) {
                    node.addDestination(adjacentNodes.get(y + 1).get(x), node.risk);
                }
            }
        }


        return graph;
    }


    static int[][] parseCavernMap(List<String> input) {
        int[][] map = new int[input.size()][];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            map[y] = new int[line.length()];
            for (int x = 0; x < line.length(); x++) {
                int riskLevel = Integer.parseInt(line.substring(x, x + 1));
                map[y][x] = riskLevel;
            }
        }

        return map;
    }

    static int[][] scaleToFullCavernMap(int[][] map) {
        int xLength = map[0].length;
        int yLength = map.length;

        int[][] newMap = new int[5 * yLength][5 * xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                newMap[y][x] = map[y][x];
                newMap[y][x + xLength] = wrapRiskLevel(map[y][x], 1);
                newMap[y][x + 2 * xLength] = wrapRiskLevel(map[y][x], 2);
                newMap[y][x + 3 * xLength] = wrapRiskLevel(map[y][x], 3);
                newMap[y][x + 4 * xLength] = wrapRiskLevel(map[y][x], 4);

                newMap[y + yLength][x] = wrapRiskLevel(map[y][x], 1);
                newMap[y + yLength][x + xLength] = wrapRiskLevel(map[y][x], 2);
                newMap[y + yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 3);
                newMap[y + yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 4);
                newMap[y + yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 5);

                newMap[y + 2 * yLength][x] = wrapRiskLevel(map[y][x], 2);
                newMap[y + 2 * yLength][x + xLength] = wrapRiskLevel(map[y][x], 3);
                newMap[y + 2 * yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 4);
                newMap[y + 2 * yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 5);
                newMap[y + 2 * yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 6);

                newMap[y + 3 * yLength][x] = wrapRiskLevel(map[y][x], 3);
                newMap[y + 3 * yLength][x + xLength] = wrapRiskLevel(map[y][x], 4);
                newMap[y + 3 * yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 5);
                newMap[y + 3 * yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 6);
                newMap[y + 3 * yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 7);

                newMap[y + 4 * yLength][x] = wrapRiskLevel(map[y][x], 4);
                newMap[y + 4 * yLength][x + xLength] = wrapRiskLevel(map[y][x], 5);
                newMap[y + 4 * yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 6);
                newMap[y + 4 * yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 7);
                newMap[y + 4 * yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 8);
            }
        }
        return newMap;
    }

    static int wrapRiskLevel(int riskLevel, int increment) {
        int newRiskLevel = riskLevel + increment;
        if (newRiskLevel > 9) {
            newRiskLevel = newRiskLevel % 10 + 1;
        }

        return newRiskLevel;
    }
}
