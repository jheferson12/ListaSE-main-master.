package co.edu.umanizales.tads.model;
import lombok.Data;



@Data
public class Node {

    private Kid data;
    private Node next;
    public Kid kid;
    public String name;


    public void setData(Kid data) {
        this.data = data;
    }

    public Node(Kid data) {
        this.data = data;
        this.next=null;
        this.name=getName();
    }


    public Kid getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }



    }



