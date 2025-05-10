import java.util.*;

public class HuffmanCoding implements HuffmanInterface{
    private class HuffNode implements Comparable<HuffNode> {
        int freq;
        char value;
        HuffNode left;
        HuffNode right;
        public HuffNode(int freq, char value) {
            this.freq = freq;
            this.value =  value;
            left = right = null;
        }
        public HuffNode(HuffNode left, HuffNode right){
            this.left =  left;
            this.right = right;
            this.freq = left.freq + right.freq;
            this.value = 0;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(HuffNode o) {
            return this.freq - o.freq;
        }
    }

    class HuffCode {
        String code;
        char value;
        HuffCode( char value, String code) {
            this.code = code;
            this.value = value;
        }
    }


    PriorityQueue<HuffNode> heap = new PriorityQueue<>();
    HuffNode root;
    List<HuffCode> codes = new ArrayList<>();


    @Override
    public String decode(String codedMessage) {
        if (codedMessage == null || codedMessage.length() == 0 || root == null){
            return "";
        }

        String decodedMessage = "";
        HuffNode node = root;
        for (char c : codedMessage.toCharArray()){
            if(c == '0'){
                node = node.left;
            }
            if(c == '1'){
                node = node.right;
            }
            if(node.isLeaf()){
                decodedMessage += node.value;
                node =root;
            }
        }

        return decodedMessage;
    }

    @Override
    public String encode(String message) {
        int[] counts = new int[256];

        for (char c : message.toCharArray()) { counts[c]++; }

        for (char i= 0; i < counts.length; i++) {
            if (counts[i] > 0){
                HuffNode huffNode = new HuffNode(counts[i], i);
                heap.add(huffNode);
            }
        }

        while (heap.size() > 1) {
            HuffNode left = heap.poll();
            HuffNode right = heap.poll();
            HuffNode composite = new HuffNode(left, right);
            heap.add(composite);


        }
        root = heap.poll();

        genrateCodes(root, "");

        String encodedMessage = "";
        for (char c : message.toCharArray()){
            encodedMessage += findCodes(c);
        }

        return encodedMessage;
    }

    String findCodes(char c) {
        for (HuffCode code : codes){
            if (code.value == c){
                return code.code;
            }
        }
        return "";
    }


    void genrateCodes(HuffNode node, String code) {
        if (node == null) { return; }
        if (node.isLeaf()){
            //leaf
            codes.add(new HuffCode(node.value, code));
        }
        genrateCodes(node.left, code + "0");
        genrateCodes(node.right, code + "1");

    }
}
