package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.ArrayList;
import java.util.Collection;
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

/*
 * This solution uses a slightly modified version of the Dijkstra algorithm.
 *
 * - https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * - https://www.baeldung.com/java-dijkstra
 */
public class Day15 implements Puzzle {

    private static final Logger log = LoggerFactory.getLogger(Day15.class);

    @Override
    public String solutionA(List<String> input) {
        var map = parseCavernMap(input);
        return solvePuzzle(map);
    }

    @Override
    public String solutionB(List<String> input) {
        var map = parseCavernMap(input);
        map = scaleToFullCavernMap(map);
        // solution for B: "2876";
        return solvePuzzle(map);
    }

    private String solvePuzzle(int[][] map) {
        var graph = createGraph(map);
        var startNode = graph.nodeAtPosition(0, 0);

        calculatePathsWithLowestRiskFrom(startNode);

        int maxX = map[0].length - 1;
        int maxY = map.length - 1;
        var targetNode = graph.nodeAtPosition(maxX, maxY);

        return Integer.toString(targetNode.getLowestRiskLevel());
    }

    @Value
    static class Graph {
        Collection<Node> nodes = new ArrayList<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        public Node nodeAtPosition(int x, int y) {
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
        List<Node> pathWithLowestRisk = new LinkedList<>();
        @EqualsAndHashCode.Exclude
        int lowestRiskLevel = Integer.MAX_VALUE;
        @EqualsAndHashCode.Exclude
        Collection<Node> adjacentNodes = new ArrayList<>();

        public void addDestination(Node destination) {
            adjacentNodes.add(destination);
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

    static void calculatePathsWithLowestRiskFrom(Node source) {
        source.setLowestRiskLevel(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Node adjacentNode : currentNode.getAdjacentNodes()) {
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumRisk(adjacentNode, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getLowestRiskLevel();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    // TODO
    // TODO
    // TODO
    private static void calculateMinimumRisk(Node evaluationNode, Node sourceNode) {
        int sourceRiskLevel = sourceNode.getLowestRiskLevel();

        int pathRiskLevel = sourceRiskLevel + evaluationNode.risk;
        if (pathRiskLevel < evaluationNode.getLowestRiskLevel()) {
            evaluationNode.setLowestRiskLevel(pathRiskLevel);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getPathWithLowestRisk());
            shortestPath.add(sourceNode);
            evaluationNode.setPathWithLowestRisk(shortestPath);
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
                    node.addDestination(adjacentNodes.get(y).get(x - 1));
                }
                if (x < xLength - 1) {
                    node.addDestination(adjacentNodes.get(y).get(x + 1));
                }
                if (y > 0) {
                    node.addDestination(adjacentNodes.get(y - 1).get(x));
                }
                if (y < yLength - 1) {
                    node.addDestination(adjacentNodes.get(y + 1).get(x));
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
