package a04;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private final Stack<Board> board;
    private boolean isSolvable;


    public Solver(Board initial) {
        if (initial == null) {
        	throw new NullPointerException();
        }
        isSolvable = false;
        board = new Stack<>();
        MinPQ<SearchNode> searchNodes = new MinPQ<>();

        searchNodes.insert(new SearchNode(initial, null));
        searchNodes.insert(new SearchNode(initial.copy(), null));

        while (!searchNodes.min().board.isGoal()) {
            SearchNode searchNode = searchNodes.delMin();
            for (Board board : searchNode.board.neighbors())
                if (searchNode.prev == null || searchNode.prev != null && !searchNode.prev.board.equals(board)) {
                    searchNodes.insert(new SearchNode(board, searchNode));
                }
        }

        SearchNode current = searchNodes.min();
        while (current.prev != null) {
            board.push(current.board);
            current = current.prev;
        }
        board.push(current.board);

        if (current.board.equals(initial)) {
        	isSolvable = true;
        }

    }
    
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private int moves;
        private int manhattan;

        public SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            this.manhattan = board.manhattan();
            if (prev != null) {
            	moves = prev.moves + 1;
            }
            else moves = 0;
        }

        @Override
        public int compareTo(SearchNode that) {
            int priorityDiff = (this.manhattan + this.moves) - (that.manhattan + that.moves);
            return  priorityDiff == 0 ? this.manhattan - that.manhattan : priorityDiff;
        }
    }

    public int moves() {
        if (!isSolvable()) {
        	return -1;
        }else {
        	return board.size() - 1;
        }
        
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
        	return board;
        }else {
        	return null;
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public static void main(String[] args) {

    }
   
}
