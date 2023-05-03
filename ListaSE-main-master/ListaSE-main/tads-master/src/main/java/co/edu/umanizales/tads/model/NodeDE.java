package co.edu.umanizales.tads.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NodeDE {
    public String name;
    private Pet data;
    private NodeDE next;
    private NodeDE previous;
    //-----------------------------CODIGO QUE SE NECESITA PARA EL RESTO-----------------------------
    public NodeDE(Pet data){
        this.data=data;
    }
    public NodeDE getPrevious() {return previous;}

    public void setPrevious(NodeDE previous) {this.previous = previous;}
    public NodeDE getNext() {
        return next;
    }

    public void setNext(NodeDE next) {
        this.next = next;
    }
    public Pet getData() {
        return data;
    }

    public void setData(Pet data) {
        this.data = data;
    }



}
