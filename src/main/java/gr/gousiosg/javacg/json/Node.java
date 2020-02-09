package gr.gousiosg.javacg.json;

public class Node {
    private String className;
    private String methodName;
    private String arguments;
    private int indegree;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    private int outdegree;

    public int getIndegree() {
        return indegree;
    }

    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }

    public int getOutdegree() {
        return outdegree;
    }

    public void setOutdegree(int outdegree) {
        this.outdegree = outdegree;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return className.equals(node.getClassName())&&methodName.equals(node.getMethodName())&&arguments.equals(node.getArguments());
        }
        return false;
    }
    @Override
    public String toString(){
        return "{\"data\":{\"id\":\""+className+":"+methodName+"("+arguments+")"+"\",\"className\":\""+className+"\",\"methodName\":\""+methodName+"\",\"arguments\":\""+arguments+"\"}}";
    }
    public String classToString(){
        return "{\"data\":{\"id\":\""+className+"\",\"className\":\""+className+"\",\"methodName\":\""+className+"\",\"arguments\":\""+className+"\"}}";

    }
}
