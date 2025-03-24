/**
 * A partial 'Node' class. Modify as necessary.
 *
 * @author Vanessa Rivera
 */
public class BigNumberNode {
    private int data;
    private BigNumberNode next;

    public BigNumberNode(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() { return this.data; }
    public void setNext(BigNumberNode next) { this.next = next; }
    public BigNumberNode getNext() {
        return this.next;
    }
}