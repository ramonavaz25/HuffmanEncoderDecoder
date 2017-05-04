import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author vazra
 * Ref:http://stackoverflow.com/questions/11585944/reconstuct-huffman-tree-for-decoding
 */
public class HuffmanTree {
	public Node root;

	public HuffmanTree() {
		this.root = new Node();
	}

	public void add(int data, String sequence) {

		Node temp = this.root;
		int i = 0;
		for (i = 0; i < sequence.length() - 1; i++) {

			if (sequence.charAt(i) == '0') {
				if (temp.getLeft() == null) {
					temp.setLeft(new Node());
					temp = temp.getLeft();
				} else {
					temp = (Node) temp.getLeft();
				}
			} else if (sequence.charAt(i) == '1') {
				if (temp.getRight() == null) {
					temp.setRight(new Node());
					temp = temp.getRight();
				} else {
					temp = (Node) temp.getRight();
				}
			}
		}

		if (sequence.charAt(i) == '0') {

			if (temp.getLeft() == null) {
				temp.setLeft(new Node());
			}
			temp.getLeft().setKey(data);
			temp.getLeft().setToLeaf();
		} else {
			if (temp.getRight() == null) {
				temp.setRight(new Node());
			}
			temp.getRight().setKey(data);
			temp.getRight().setToLeaf();

		}
	}

	public void getDecodedMessage(String encoding) throws IOException {
		FileWriter out = new FileWriter(new File("decoded.txt"));

		Integer output = 0;
		Node temp = this.root;
		for (int i = 0; i < encoding.length(); i++) {

			if (encoding.charAt(i) == '0') {
				temp = temp.getLeft();

				if (temp.getLeft() == null && temp.getRight() == null) {
					output = temp.getKey();
					out.write(output.toString().trim());
					out.write(System.lineSeparator());
					temp = this.root;
				}
			} else {
				temp = temp.getRight();
				if (temp.getLeft() == null && temp.getRight() == null) {
					output = temp.getKey();
					out.write(output.toString().trim());
					out.write(System.lineSeparator());
					temp = this.root;
				}

			}
		}
		out.close();
	}

	// Traversal of reconstructed huffman tree for debugging.
	public void traversal(Node node) {

		if (node == null)
			return;
		System.out.println(node);
		traversal(node.getLeft());
		traversal(node.getRight());

	}

}
