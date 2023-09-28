package fifteenpuzzle;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class minHeapPriorityQ<E extends Comparable<E>> {
    private ArrayList<AStarNode> heap;

    public minHeapPriorityQ() {
        this.heap = new ArrayList<AStarNode>();
    }

    public void addElement(AStarNode element) {
        heap.add(element);
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).getF() >= heap.get(parentIndex).getF()) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    public AStarNode getMin() {
        if (heap.size() == 0) {
            throw new NoSuchElementException();
        }
        return heap.get(0);
    }

    public AStarNode removeMin() {
        if (heap.size() == 0) {
            throw new NoSuchElementException();
        }
        AStarNode min = heap.get(0);
        AStarNode last = heap.remove(heap.size() - 1);
        if (heap.size() > 0) {
            heap.set(0, last);
            int index = 0;
            while (index < heap.size()) {
                int leftChildIndex = 2 * index + 1;
                int rightChildIndex = 2 * index + 2;
                if (leftChildIndex >= heap.size()) {
                    break;
                }
                int minChildIndex = leftChildIndex;
                if (rightChildIndex < heap.size()
                        && heap.get(rightChildIndex).getF()< heap.get(leftChildIndex).getF()) {
                    minChildIndex = rightChildIndex;
                }
                if (heap.get(minChildIndex).getF() >= (last.getF())) {
                    break;
                }
                heap.set(index, heap.get(minChildIndex));
                index = minChildIndex;
            }
            heap.set(index, last);
        }
        return min;
    }

    public int getSize() {
        return heap.size();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }
    private void swap(int i, int j) {
        AStarNode temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

