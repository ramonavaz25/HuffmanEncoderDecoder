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
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author vazra
 * Ref:http://stackoverflow.com/questions/11585944/reconstuct-huffman-tree-for-decoding
 */
public class decoder {
	public Node root;
	HuffmanTree mydecodeTree = new HuffmanTree();

	public static void main(String[] args) throws IOException {
		new decoder(args);
	}

	public decoder(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Give proper parameters to run code:encoded file name followed by code table file name");
			System.exit(0);
		} else {
			decode(args[0], args[1]);
		}

	}

	private void decode(String CODE_TABLEPATH, String ENCODEDPATH) throws IOException {
		reconstructedTree(CODE_TABLEPATH);
		getDecodedMessage(ENCODEDPATH);
	}

	private void reconstructedTree(String CODE_TABLEPATH) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(CODE_TABLEPATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		try {
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				String sequence = parts[1];
				int key = Integer.parseInt(parts[0]);
				mydecodeTree.add(key, sequence);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getDecodedMessage(String ENCODEDPATH) throws IOException {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(new File(ENCODEDPATH).toPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuffer output = new StringBuffer();

		String bitOut = "";
		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				int bit = (b << (7 - i)) & 0x80;
				if (bit == 0x80) {
					bitOut = "1";
					output.append(bitOut);
				} else {

					bitOut = "0";
					output.append(bitOut);
				}

			}

		}
		mydecodeTree.getDecodedMessage(output.toString());

	}

}
