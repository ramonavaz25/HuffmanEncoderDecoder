import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * @author vazra Ref:https://github.com/Juusoh/Huffman-Coding
 */
public class encoder {

	public static void main(String[] args) throws IOException {
		new encoder(args);
	}

	int count;
	int[] freq = new int[256];

	HashMap<Integer, Integer> freqTable = new HashMap<>();

	PriorityQueue feederHeap;

	HashMap<Integer, String> codeTable = new HashMap<>();

	public encoder(String[] args) {
		if (args.length < 1) {
			System.out.println("Give proper parameters to run code: filename should follow java encode");
			System.exit(0);
		} else {
			code(args[0]);
		}

	}

	private void code(String PATH) {
		getFrequencies(PATH);
		Node min = generateTree();
		generateCode(min, "");
		writeFiles(PATH);
	}

	private void getFrequencies(String PATH) {
		try {
			readFreqsFromFile(PATH);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
			System.exit(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void readFreqsFromFile(String PATH) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(PATH));
		String line;
		int frequency = 0;
		while ((line = br.readLine()) != null) {
			int index = Integer.parseInt(line);
			if (freqTable.containsKey(index)) {
				frequency = freqTable.get(index) + 1;
				freqTable.put(index, frequency);
			} else {
				freqTable.put(index, 1);
			}
			count++;
		}
		br.close();

	}

	private Node generateTree() {

		return createTree(createHeap());
	}

	private PriorityQueue createHeap() {
		feederHeap = new daryHeap(freqTable.size());
		Iterator frequencyTableIt = freqTable.keySet().iterator();
		while (frequencyTableIt.hasNext()) {
			Integer key = (Integer) frequencyTableIt.next();
			Node mynode = new Node(freqTable.get(key), key);
			mynode.setToLeaf();
			feederHeap.insert(mynode);
		}
		return feederHeap;
	}

	private Node createTree(PriorityQueue priorityQueue) {
		int n = priorityQueue.getHeapSize();
		for (int i = 0; i < (n - 1); ++i) {
			Node z = new Node();
			z.setLeft(priorityQueue.deleteMin());
			z.setRight(priorityQueue.deleteMin());
			z.setFreq(z.getLeft().getFreq() + z.getRight().getFreq());
			priorityQueue.insert(z);

		}
		return priorityQueue.findMin();
	}

	private void generateCode(Node node, String code) {
		if (node != null) {
			if (node.isLeaf()) {
				codeTable.put(node.getKey(), code);
			} else {
				generateCode(node.getLeft(), code + "0");
				generateCode(node.getRight(), code + "1");

			}
		}
	}

	private void writeFiles(String PATH) {
		try {
			FileWriter out = new FileWriter(new File("code_table.txt"));
			Iterator codeTableIt = freqTable.keySet().iterator();
			while (codeTableIt.hasNext()) {
				Integer key = (Integer) codeTableIt.next();
				out.write(key + " " + codeTable.get(key));
				out.write(System.lineSeparator());
			}
			BufferedReader br = new BufferedReader(new FileReader(PATH));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("encoded.bin"));
			BitSet buffer = new BitSet(Integer.MAX_VALUE);
			int bitIndex = -1;
			String huff_code;
			String bit = "";
			try {
				while ((bit = br.readLine()) != null) {
					if (!bit.trim().equals("")) {
						huff_code = codeTable.get(Integer.parseInt(bit.trim())).trim();
						for (char c : huff_code.toCharArray()) {
							if (c == '1') {
								++bitIndex;
								buffer.set(bitIndex, true);
							} else if (c == '0') {
								++bitIndex;
								buffer.set(bitIndex, false);
							} else {
								continue;
							}
						}

					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				bos.write(buffer.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
			out.close();
			br.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

}
