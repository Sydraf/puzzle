package a04;

import edu.princeton.cs.algs4.Queue;

/**
 * 
 * @author Mathew Merkley, Mike Kennedy 
 * will comment later.....
 */
public final class Board {
	private int indexOf0;
	private final int size;
	private final int[] blocks;

	public Board(int[][] blocks) {

		this.size = blocks.length;
		this.blocks = new int[size * size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.blocks[i * size + j] = blocks[i][j];
			}
		}
		this.indexOf0 = indexOf0();
	}

	private Board(int[] board) {
		size = (int) Math.sqrt(board.length);
		this.blocks = new int[board.length];
		for (int i = 0; i < board.length; i++) {
			this.blocks[i] = board[i];
		}
	}

	private int indexOf0() {
		int index = 0;
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] == 0) {
				index = i;
			}
		}
		return index;
	}

	public int size() {
		return size;

	}

	public int hamming() {
		int distance = 0;
		for (int i = 0; i < blocks.length; i++) {
			if (i != (blocks[i] - 1)) {
				distance++;

			}
		}
		return distance - 1;

	}

	public int manhattan() {
		int manhattan = 0;
        for (int i = 0; i < size * size; i++)
            if (blocks[i] != i + 1 && blocks[i] != 0)
                manhattan += manhattan(blocks[i], i);
        return manhattan;
	}
	
	private int manhattan(int goal, int current) {
        int row;
        int col;
        row = Math.abs((goal - 1) / size - current / size);
        col = Math.abs((goal - 1) % size - current % size);
        return row + col;
    }

	public boolean isGoal() {
		for (int i = 0; i < size * size - 1; i++)
			if (blocks[i] != i + 1)
				return false;
		return true;

	}

	public boolean isSolvable() {
		int inversions = 0;

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] == 0) {
				continue;
			}

			for (int j = i; j < blocks.length ; j++) {
				if (blocks[j] < blocks[i] && blocks[j] != 0) {
					inversions++;
				}
			}
		}

		boolean even = (size % 2) == 0;
		if (even) {
			inversions += indexOf0 / size;
		}
		boolean evenInversions = (inversions % 2) == 0;

		return even != evenInversions;
	}

	public boolean equals(Object y) {
		if (y == this) {
			return true;
		}
		if (y == null) {
			return false;
		}
		if (y.getClass() != this.getClass()) {
			return false;
		}

		return this.toString().equals(y.toString());

	}

	private Board move(Board board, int one, int two) {
		int temp = board.blocks[one];
		board.blocks[two] = board.blocks[one];
		board.blocks[one] = temp;
		return board;
	}

	public Iterable<Board> neighbors() {
		int index = 0;
		boolean found = false;
		Board neighbor;
		Queue<Board> q = new Queue<Board>();

		for (int i = 0; i < blocks.length; i++)
			if (blocks[i] == 0) {
				index = i;
				found = true;
				break;
			}
		if (!found)
			return null;

		if (index / size != 0) {
			neighbor = new Board(blocks);
			move(neighbor, index, index - size);
			q.enqueue(neighbor);
		}

		if (index / size != (size - 1)) {
			neighbor = new Board(blocks);
			move(neighbor, index, index + size);
			q.enqueue(neighbor);
		}

		if ((index % size) != 0) {
			neighbor = new Board(blocks);
			move(neighbor, index, index - 1);
			q.enqueue(neighbor);
		}

		if ((index % size) != size - 1) {
			neighbor = new Board(blocks);
			move(neighbor, index, index + 1);
			q.enqueue(neighbor);
		}

		return q;

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append(size + "\n");
        for (int i = 0; i < size - 1; i++) {
            sb.append(String.format("%2d ", blocks[i]));
            if (i % size == 0)
                sb.append("\n");
        }
        return sb.toString();

	}

	public Board copy() {
		Board copy;
		if (size == 1) {
			return null;
		}
		copy = new Board(blocks);

		if (blocks[0] != 0 && blocks[1] != 0)
			move(copy, 0, 1);
		else
			copy.move(copy, size, size + 1);
		return copy;
	}

	public static void main(String[] args) {

	}

}
