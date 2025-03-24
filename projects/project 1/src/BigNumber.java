/**
 * A partial linked list class. Modify as necessary.
 *
 * @author Vanessa Rivera
 */
public class BigNumber {

    private BigNumberNode head;

    public BigNumber() {
        this.head = null;
    }

    public void insertAtFront(int data) {
        BigNumberNode new_node = new BigNumberNode(data);
        new_node.setNext(this.head);
        this.head = new_node;
    }
    public void insertAtEnd(int data) {
        BigNumberNode new_node = new BigNumberNode(data);
        if (this.head == null){
            this.head = new_node;
            return;
        }
        BigNumberNode last = this.head;
        while (last.getNext() != null) {
            last = last.getNext();
        }
        last.setNext(new_node);
    }

    /**
     * Instance method version of addition.
     *
     * @param addend The other number
     * @return A BigNumber representing the sum
     */
    public BigNumber add(BigNumber addend) {
        BigNumber sum = new BigNumber();
        BigNumberNode current1 = this.head;
        BigNumberNode current2 = addend.head;
        int carry = 0;

        while (current1 != null || current2 != null || carry == 1) {
            int data1 = current1 != null ? current1.getData() : 0;
            int data2 = current2 != null ? current2.getData() : 0;
            sum.insertAtEnd((data1 + data2 + carry) % 10);
            if (data1 + data2 + carry >= 10) {
                carry = 1;
            } else {
                carry = 0;
            }
            if (current1 != null) {
                current1 = current1.getNext();
            }
            if (current2 != null) {
                current2 = current2.getNext();
            }
        }
        return sum; //return new ll representing the sum
    }

    /**
     * Instance method version of multiplication.
     *
     * @param multiplicand The other number
     * @return A BigNumber representing the product
     */
    public BigNumber multiply(BigNumber multiplicand) {
        BigNumber product = new BigNumber();
        product.insertAtEnd(0);
        BigNumberNode current1 = this.head;
        BigNumberNode current2 = multiplicand.head;
        int carry = 0;
        int zeros = 0;

        while (current1 != null) {
            int data1 = current1.getData();
            BigNumber addend = new BigNumber();
            current2 = multiplicand.head;
            while (current2 != null || carry > 0) {
                int data2 = current2 != null ? current2.getData() : 0;
                addend.insertAtEnd(((data1 * data2) + carry) % 10);
                carry = (((data1 * data2) + carry) / 10);

                if (current2 != null) {
                    current2 = current2.getNext();
                }
            }
            current1 = current1.getNext();
            int count_zeros = 0;
            while (count_zeros < zeros) {
                addend.insertAtFront(0);
                count_zeros += 1;
            }

            product = product.add(addend);
            zeros += 1;
        }
        return product;
    }

    /**
     * Instance method version of exponentiation.
     * Note: this method accepts a regular integer as an exponent, not a BigNumber.
     *
     * @param exponent The exponent to raise to
     * @return A BigNumber representing the power
     */
    public BigNumber exponentiate(int exponent) {
        BigNumber squared = new BigNumber();
        squared = this.multiply(this);
        BigNumber result = new BigNumber();
        result = squared;

        if (exponent % 2 == 0) { // even power
            for (int n = 1; n < exponent/2; n++) {
                result = result.multiply(squared);
            }
        }
        else { // odd power
            result = result.multiply(this);
            for (int n = 1; n < (exponent - 1)/2; n++) {
                result = result.multiply(squared);
            }
        }

        return result;
    }

    public static BigNumber fromString(String string) {
        BigNumber new_number = new BigNumber();
        for (int i = 0; i < string.length(); i++) {
            new_number.insertAtFront(Integer.parseInt(String.valueOf(string.charAt(i))));
        }

        return new_number;
    }

    @Override
    public String toString() {
        String string = "";
        BigNumberNode current = this.head;
        while (current != null) {
            string = current.getData() + string; // appends the current node's data to the beginning of the string
            current = current.getNext(); // moves current to the next node in the ll
        }
        return string;
    }
}