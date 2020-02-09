package gr.gousiosg.javacg.json;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodeList = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();

    public List<Node> getNodeList() {
        return nodeList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void addNode(Node node){
        if (nodeList.contains(node)){
            return ;
        }else{
            nodeList.add(node);
        }
    }
    public void addEdge(Edge edge){
        if (edgeList.contains(edge)){
            return ;
        }else{
            edgeList.add(edge);
        }
    }
//    public static
//    Set<Node> set =  new LinkedHashSet<>()
}
