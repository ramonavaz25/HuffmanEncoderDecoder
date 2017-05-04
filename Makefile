all:
	javac encoder.java decoder.java Heap.java HuffmanTree.java Node.java daryHeap.java PairHeap.java PairNode.java PriorityQueue.java
clean:
	rm -rf *.class