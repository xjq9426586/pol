package test;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * 无限级节点模型
 */
public class Node {
    public static void main(String[] args) {
        Node n = new Node(1L, 2L, new Date());

        System.out.println(JSON.toJSON(n));
    }
    /**
     * 节点id
     */
    private Long id;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 父节点id
     */
    private Long parentId;

    private Date date;
    public Node() {
    }

    Node(Long id, Long parentId, Date date) {
        this.id = id;
        this.parentId = parentId;
        this.date = date;
    }

    Node(Long id, String nodeName, Long parentId) {
        this.id = id;
        this.nodeName = nodeName;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}