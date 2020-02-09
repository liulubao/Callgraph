package gr.gousiosg.javacg.json;

public class Edge {
    private Node source;
    private Node target;
    private double label;

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public double getLabel() {
        return label;
    }

    public void setLabel(double label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge edge = (Edge) obj;
            return source.equals(edge.getSource())&&target.equals(edge.getTarget());
        }
        return false;
    }
    @Override
    public String toString(){
        return "{\"data\":{\"label\":\""+label+"\",\"source\":\""+source.getClassName()+":"+source.getMethodName()+"("+source.getArguments()+")"+"\",\"target\":\""+target.getClassName()+":"+target.getMethodName()+"("+target.getArguments()
                +")\"}}";
    }
    public String classToString(){
        return "{\"data\":{\"label\":\""+label+"\",\"source\":\""+source.getClassName()+"\",\"target\":\""+target.getClassName()
                +"\"}}";
    }
}
