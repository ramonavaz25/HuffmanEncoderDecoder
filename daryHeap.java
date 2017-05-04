import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author vazra
 * Ref:http://www.sanfoundry.com/java-program-implement-d-ary-heap/
 */
public class daryHeap implements PriorityQueue {
	private Node[] DaryHeap;

	private int size = 0;

	public daryHeap(int size) {
		DaryHeap = new Node[size + 3];
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == DaryHeap.length;
	}

	private int parent(int i) {
		return (i / 4) + 2;
	}

	private int kthChild(int i, int k) {
		return (4 * i) + k - 9;
	}

	public void add(Node element) {
		if (!isFull()) {
			DaryHeap[size + 3] = element;
			size++;
			minHeapifyUp(size + 2);

		}
	}

	private void minHeapifyUp(int childInd) {
		Node tmp = DaryHeap[childInd];
		while (childInd > 3 && tmp.getFreq() < DaryHeap[parent(childInd)].getFreq()) {
			DaryHeap[childInd] = DaryHeap[parent(childInd)];
			childInd = parent(childInd);
		}
		DaryHeap[childInd] = tmp;
	}

	public Node min() {
		if (isEmpty())
			throw new NoSuchElementException("Underflow Exception");
		return DaryHeap[3];
	}

	public Node removeMin() {
		if (!isEmpty()) {
			Node removed = DaryHeap[3];
			DaryHeap[3] = DaryHeap[size + 2];
			size--;
			minHeapifyDown(3);

			return removed;
		}
		return null;
	}

	private void minHeapifyDown(int ind) {
		int child;
		Node tmp = DaryHeap[ind];
		while (kthChild(ind, 1) < size + 3) {
			child = minChild(ind);
			if (DaryHeap[child].getFreq() < tmp.getFreq())
				DaryHeap[ind] = DaryHeap[child];
			else
				break;
			ind = child;
		}
		DaryHeap[ind] = tmp;
	}

	private int minChild(int ind) {
		int bestChild = kthChild(ind, 1);
		int k = 2;
		int pos = kthChild(ind, k);
		while ((k <= 4) && (pos < size + 3)) {
			if (DaryHeap[pos].getFreq() < DaryHeap[bestChild].getFreq())
				bestChild = pos;
			pos = kthChild(ind, ++k);
		}
		return bestChild;
	}

	public void print(Node x, String codeStr) {

		System.out.println("Parent: " + x.getFreq() + " LChild: " + x.getLeft().getFreq() + " RChild: "
				+ x.getRight().getFreq() + "Code till now: " + codeStr);
		if (x.isLeaf())
			System.out.println("Code: " + codeStr);
		if (!x.getLeft().isLeaf()) {

			print(x.getLeft(), codeStr + "0");
		} else {
			System.out.println("Code: " + codeStr + "0");
		}
		if (!x.getRight().isLeaf()) {
			print(x.getRight(), codeStr + "1");
		} else {
			System.out.println("Code: " + codeStr + "1");
		}
	}

	@Override
	public void insert(Node x) {
		add(x);

	}

	@Override
	public Node findMin() {
		return min();
	}

	@Override
	public Node deleteMin() {
		return removeMin();
	}

	@Override
	public int getHeapSize() {
		return getSize();
	}
}
