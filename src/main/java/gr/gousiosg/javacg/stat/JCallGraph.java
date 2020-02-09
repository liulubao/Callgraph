/*
 * Copyright (c) 2011 - Georgios Gousios <gousiosg@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gr.gousiosg.javacg.stat;

import gr.gousiosg.javacg.json.Edge;
import gr.gousiosg.javacg.json.Graph;
import gr.gousiosg.javacg.json.Node;
import net.sf.json.JSONObject;
import org.apache.bcel.classfile.ClassParser;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Constructs a callgraph out of a JAR archive. Can combine multiple archives
 * into a single call graph.
 *
 * @author Georgios Gousios <gousiosg@gmail.com>
 */
public class JCallGraph {

    public static void main(String[] args) {
        File file = new File("D:\\workspace\\java-callgraph\\target\\javacg-0.1-SNAPSHOT-dycg-agent.jar");
        File[] test = new File[1];
        test[0] = file;
        System.out.println(analysisTest(test));

    }

    public static String analysisTest(File[] args){
        Set<String> classSet = new HashSet<>();
        List<String> classCalls = new ArrayList<>();
        Function<ClassParser, Set<String>> getClassSet =
                (ClassParser cp) -> {

                    try {
                        classSet.add(cp.parse().getClassName());
                        return new HashSet<>();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                };
        for (File f : args){
            try (JarFile jar = new JarFile(f)) {
                Stream<JarEntry> entries = enumerationAsStream(jar.entries());

                entries.
                        flatMap(e -> {
                            if (e.isDirectory() || !e.getName().endsWith(".class"))
                                return (new ArrayList<String>()).stream();
                            ClassParser cp = new ClassParser(f.getPath(), e.getName());
                            return getClassSet.apply(cp).stream();
                        }).
                        map(s -> s + "\n").
                        reduce(new StringBuilder(),
                                StringBuilder::append,
                                StringBuilder::append).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder result = new StringBuilder();

        Graph graph = new Graph();
        Graph classGraph = new Graph();
        Function<ClassParser, ClassVisitor> getClassVisitor =
                (ClassParser cp) -> {
                    try {
                        return new ClassVisitor(cp.parse(),classSet);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                };

        try {

            System.out.println("args : " + args.length);
            for (File f  : args) {
                System.out.println(f.getName());
                System.out.println("��ʼ����0");
                if (!f.exists()) {
                    System.err.println("Jar file " + f.getPath() + " does not exist");
                }

                try (JarFile jar = new JarFile(f)) {
                    Stream<JarEntry> entries = enumerationAsStream(jar.entries());
                    System.out.println("��ʼ����1");
                    String methodCalls = entries.
                            flatMap(e -> {
                                if (e.isDirectory() || !e.getName().endsWith(".class"))
                                    return (new ArrayList<String>()).stream();

                                ClassParser cp = new ClassParser(f.getPath(), e.getName());
                                ClassVisitor cv0 = getClassVisitor.apply(cp).start();
                                List<String> list =cv0.classCalls();

                                classCalls.addAll(list);
                                return cv0 .methodCalls().stream();
//                                return getClassVisitor.apply(cp).start().methodCalls().stream();
                            }).
                            map(s -> {

                                JSONObject jsonObject = JSONObject.fromObject(s);

                                JSONObject sourceJsonObject = JSONObject.fromObject(jsonObject.get("source"));
                                JSONObject targetJsonObject = JSONObject.fromObject(jsonObject.get("target"));
                                Node source = (Node) JSONObject.toBean(sourceJsonObject,Node.class);
                                Node target = (Node) JSONObject.toBean(targetJsonObject,Node.class);
                                if(graph.getNodeList().contains(source)||graph.getNodeList().contains(target)){
                                }else{
                                }
                                graph.addNode(source);
                                graph.addNode(target);
                                Edge edge = new Edge();
                                edge.setTarget(source);
                                edge.setSource(target);
                                graph.addEdge(edge);
                                return s+"\n";
                            }).
                            reduce(new StringBuilder(),
                                    StringBuilder::append,
                                    StringBuilder::append).toString();

                    List<Node> nodeList = graph.getNodeList();
                    List<Edge> edgeList = graph.getEdgeList();
                    for(Node node:nodeList){
                        int indegree = 0;
                        int outdegree = 0;
                        for(Edge edge:edgeList){
                            if(edge.getSource().equals(node)){
                                outdegree++;
                                edge.setSource(node);
                            }
                            if(edge.getTarget().equals(node)){
                                indegree++;
                                edge.setTarget(node);
                            }
                        }
                        node.setIndegree(indegree);
                        node.setOutdegree(outdegree);

                    }
                    for(Edge edge:edgeList){
                        double label = 0.0;

                        Node source = edge.getSource();
                        Node target = edge.getTarget();
                        int a = source.getOutdegree();
                        int b = target.getIndegree();
                        label = (double)2/
                                (a+ b);
                        edge.setLabel(label);
                    }

                    Set<String> set = new HashSet<>();
                    for(String str : classCalls)
                    {
//                        result.append("{\"data\":"+str+"},\n");
                        JSONObject jsonObject = JSONObject.fromObject(str);
                        set.add(jsonObject.getString("source"));
                        set.add(jsonObject.getString("target"));
                        JSONObject sourceJsonObject = JSONObject.fromObject(jsonObject.get("source"));
                        JSONObject targetJsonObject = JSONObject.fromObject(jsonObject.get("target"));
                        Node source = (Node) JSONObject.toBean(sourceJsonObject,Node.class);
                        Node target = (Node) JSONObject.toBean(targetJsonObject,Node.class);
                        if(classGraph.getNodeList().contains(source)){
                            source=classGraph.getNodeList().get(classGraph.getNodeList().indexOf(source));
                        }
                        if(classGraph.getNodeList().contains(target)){
                            target=classGraph.getNodeList().get(classGraph.getNodeList().indexOf(target));
                        }
                        classGraph.addNode(target);
                        classGraph.addNode(source);
                        Edge edge = new Edge();
                        edge.setSource(source);
                        edge.setTarget(target);
                        classGraph.addEdge(edge);
                    }

                    List<Node> classNodeList = classGraph.getNodeList();
                    List<Edge> classEdgeList = classGraph.getEdgeList();
                    Iterator<Edge> iterator = classEdgeList.iterator();
                    while(iterator.hasNext()){
                        Edge edge = iterator.next();
                        int label = 0;
                        for(Edge edge1:edgeList){
                            if(edge.getSource().getClassName().equals(edge1.getSource().getClassName())&&edge.getTarget().getClassName().equals(edge1.getTarget().getClassName())){
                                label++;
                            }
                        }
                        if(label==0){
                            iterator.remove();
                        }else{
                            edge.setLabel(label);
                        }
                    }
                    Iterator<Node> nodeIterator = classNodeList.iterator();
                    while (nodeIterator.hasNext()){
                        Node node = nodeIterator.next();
                        int indegree = 0;
                        int outdegree = 0;
                        for(Edge edge:classEdgeList){
                            if(edge.getSource().equals(node)){
                                outdegree=outdegree+(int)edge.getLabel();
                            }
                            if(edge.getTarget().equals(node)){
                                indegree=indegree+(int)edge.getLabel();
                            }
                        }
                        if(indegree==0&&outdegree==0){
                            nodeIterator.remove();
                        }
                        node.setIndegree(indegree);
                        node.setOutdegree(outdegree);
                    }
                    for(Edge edge : classEdgeList){
                        Double label = 0.0;
                        label = (2*edge.getLabel())/(edge.getSource().getOutdegree()+edge.getTarget().getIndegree());
                        edge.setLabel(label);
                    }

                    result.append("{\"classCall\":[");
                    for(Node node:classNodeList){
                        result.append(node.classToString()+","+"\n");
                    }
                    for(Edge node:classEdgeList){
                        result.append(node.classToString()+","+"\n");
                    }
                    result.append("],\n");
                    result.append("\"methodCall\":");
                    result.append("[");
                    for(Node node:nodeList){
                        result.append(node.toString()+","+"\n");
                    }
                    for(Edge node:edgeList){
                        result.append(node.toString()+","+"\n");
                    }
                    result.append("]");
                    result.append("}\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error while processing jar: " + e.getMessage());
            e.printStackTrace();
        }
        return result.toString();
    }

    public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        new Iterator<T>() {
                            public T next() {
                                return e.nextElement();
                            }

                            public boolean hasNext() {
                                return e.hasMoreElements();
                            }
                        },
                        Spliterator.ORDERED), false);
    }

    public static String analysis(String[] args){
        System.out.println("JCallGraph is starting");
        StringBuilder result = new StringBuilder();
        Graph graph = new Graph();
        Graph classGraph = new Graph();
        List<String> classCalls = new ArrayList<>();
        Function<ClassParser, ClassVisitor> getClassVisitor =
                (ClassParser cp) -> {
                    try {
                        return new ClassVisitor(cp.parse(),null);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                };
        try {
            for (String arg  : args) {
                System.out.println("JCallGraph is starting 1");
                File f = new File(arg);
                if (!f.exists()) {
                    System.err.println("Jar file " + arg + " does not exist");
                }
                try (JarFile jar = new JarFile(f)) {
                    System.out.println("JCallGraph is starting11");
                    Stream<JarEntry> entries = enumerationAsStream(jar.entries());
                    String methodCalls = entries.
                            flatMap(e -> {
                                if (e.isDirectory() || !e.getName().endsWith(".class"))
                                    return (new ArrayList<String>()).stream();

                                ClassParser cp = new ClassParser(arg, e.getName());
                                ClassVisitor cv0 = getClassVisitor.apply(cp).start();
                                List<String> list =cv0.classCalls();

                                classCalls.addAll(list);
                                return cv0 .methodCalls().stream();
                            }).
                            map(s -> {
                                JSONObject jsonObject = JSONObject.fromObject(s);
                                JSONObject sourceJsonObject = JSONObject.fromObject(jsonObject.get("source"));
                                JSONObject targetJsonObject = JSONObject.fromObject(jsonObject.get("target"));
                                Node source = (Node) JSONObject.toBean(sourceJsonObject,Node.class);
                                Node target = (Node) JSONObject.toBean(targetJsonObject,Node.class);
                                if(graph.getNodeList().contains(source)){
                                    source=graph.getNodeList().get(graph.getNodeList().indexOf(source));
                                }
                                if(graph.getNodeList().contains(target)){
                                    target=graph.getNodeList().get(graph.getNodeList().indexOf(target));
                                }
                                graph.addNode(source);
                                graph.addNode(target);
                                Edge edge = new Edge();
                                edge.setTarget(source);
                                edge.setSource(target);
                                graph.addEdge(edge);
                                return s+"\n";
                            }).
                            reduce(new StringBuilder(),
                                    StringBuilder::append,
                                    StringBuilder::append).toString();

                    List<Node> nodeList = graph.getNodeList();
                    List<Edge> edgeList = graph.getEdgeList();

                    for(Node node:nodeList){
                        int indegree = 0;
                        int outdegree = 0;
                        for(Edge edge:edgeList){
                            if(edge.getSource().equals(node)){
                                outdegree=outdegree+1;
                            }
                            if(edge.getTarget().equals(node)){
                                indegree=indegree+1;
                            }
                        }
                        node.setIndegree(indegree);
                        node.setOutdegree(outdegree);
                    }
                    for(Edge edge:edgeList){
                        double label = 0.0;
                        label = (double)2/
                                (edge.getSource().getOutdegree()+
                                        edge.getTarget().getIndegree());
                        edge.setLabel(label);
                    }

                    Set<String> set = new HashSet<>();
                    int i =0;
                    for(String str : classCalls)
                    {
                        result.append("{\"data\":"+str+"},\n");
                        JSONObject jsonObject = JSONObject.fromObject(str);
                        set.add(jsonObject.getString("source"));
                        set.add(jsonObject.getString("target"));
                        JSONObject sourceJsonObject = JSONObject.fromObject(jsonObject.get("source"));
                        JSONObject targetJsonObject = JSONObject.fromObject(jsonObject.get("target"));
                        Node source = (Node) JSONObject.toBean(sourceJsonObject,Node.class);
                        Node target = (Node) JSONObject.toBean(targetJsonObject,Node.class);
                        if(classGraph.getNodeList().contains(source)){
                            source=classGraph.getNodeList().get(classGraph.getNodeList().indexOf(source));
                        }
                        if(classGraph.getNodeList().contains(target)){
                            target=classGraph.getNodeList().get(classGraph.getNodeList().indexOf(target));
                        }
                        classGraph.addNode(target);
                        classGraph.addNode(source);
                        Edge edge = new Edge();
                        edge.setSource(source);
                        edge.setTarget(target);
                        classGraph.addEdge(edge);
                    }

                    List<Node> classNodeList = classGraph.getNodeList();
                    List<Edge> classEdgeList = classGraph.getEdgeList();
                    Iterator<Edge> iterator = classEdgeList.iterator();
                    while(iterator.hasNext()){
                        Edge edge = iterator.next();
                        int label = 0;
                        for(Edge edge1:edgeList){
                            if(edge.getSource().getClassName().equals(edge1.getSource().getClassName())&&edge.getTarget().getClassName().equals(edge1.getTarget().getClassName())){
                                label++;
                            }
                        }
                        if(label==0){
                            iterator.remove();
                        }else{
                            edge.setLabel(label);
                        }
                    }
                    Iterator<Node> nodeIterator = classNodeList.iterator();
                    while (nodeIterator.hasNext()){
                        Node node = nodeIterator.next();
                        int indegree = 0;
                        int outdegree = 0;
                        for(Edge edge:classEdgeList){
                            if(edge.getSource().equals(node)){
                                outdegree=outdegree+(int)edge.getLabel();
                            }
                            if(edge.getTarget().equals(node)){
                                indegree=indegree+(int)edge.getLabel();
                            }
                        }
                        if(indegree==0&&outdegree==0){
                            nodeIterator.remove();
                        }
                        node.setIndegree(indegree);
                        node.setOutdegree(outdegree);
                    }

                    for(Edge edge : classEdgeList){
                        Double label = 0.0;
                        label = (2*edge.getLabel())/(edge.getSource().getOutdegree()+edge.getTarget().getIndegree());
                        edge.setLabel(label);
                    }

                    result.append("{\"className\":[");
                    for(Node node:classNodeList){
                        result.append(node.classToString()+","+"\n");
                    }
                    for(Edge node:classEdgeList){
                        result.append(node.classToString()+","+"\n");
                    }
                    result.append("],\n");
                    result.append("\"method\":[");
                    for(Node node:nodeList){
                        result.append(node.toString()+","+"\n");
                    }
                    for(Edge node:edgeList){
                        result.append(node.toString()+","+"\n");
                    }
                    result.append("],\n");
                    result.append("\"dataName\":");
                    for(Node node:nodeList){
                        result.append(node.toString()+","+"\n");
                    }
                    for(Edge node:edgeList){
                        result.append(node.toString()+","+"\n");
                    }
                    result.append("}");
                    result.append("]");

                }
                System.out.println("JCallGraph is starting12");
            }
            System.out.println("JCallGraph is starting2");
        } catch (IOException e) {
            System.err.println("Error while processing jar: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("analysis end");
        return result.toString();
    }
}
