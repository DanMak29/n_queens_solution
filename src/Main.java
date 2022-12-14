import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class Main {

    private static List<Board> solutions = new ArrayList<>();
    private static Board board = new Board(8);

    public static void main(String[] args) {

        findAllSolutions(board.getSize());

        out.println("Найдено " + solutions.size() + " возможных решения.\n");

        board.printChessBoard();
        int[] pos = readPositionFromConsole();

        board.addQueen(pos, true);

        int length = solutions.stream().filter(b -> b.isAMatch(pos)).toArray().length;
        solutions.stream().filter(b -> b.isAMatch(pos)).forEach(Board::printChessBoard);
        out.println("Найдено "
                + length
                + " возможных решений, с ферзем на позиции "
                + board.getPositions().get(pos[0] - 1).toUpperCase()
                + pos[1]
                + "."
        );

    }

    private static void findAllSolutions(int boardSize) {
        backtrack(new int[boardSize], 0);
    }

    private static boolean isAccurate(int[] q, int pos) {
        return IntStream.range(0, pos).boxed().noneMatch(i ->
                q[i] == q[pos]
                        || (q[i] - q[pos]) == (pos - i)
                        || (q[pos] - q[i]) == (pos - i)
        );
    }


    private static void saveBoard(int[] q) {
        Board b = new Board(q.length);

        IntStream.range(0, q.length).boxed().forEach(i ->
                IntStream.range(0, q.length).boxed().forEach(j -> {
                    if (q[i] == j) {
                        b.addQueen(new int[]{i + 1, j + 1}, false);
                    }
                })
        );
        solutions.add(b);
    }


    private static void backtrack(int[] q, int pos) {
        if (pos == q.length) {
            saveBoard(q);
        } else {
            IntStream.range(0, q.length).boxed().forEach(i -> {
                q[pos] = i;
                if (isAccurate(q, pos)) {
                    backtrack(q, pos + 1);
                }
            });
        }
    }


    private static int[] readPositionFromConsole() {
        Scanner sc = new Scanner(System.in);
        out.println("Введите позицию ферзя.  (Например: А1)");
        String pos = sc.nextLine();

        return validatePosition(pos);
    }

    private static int[] validatePosition(String position) {
        char[] pos = position.toCharArray();

        if (pos.length == 2) {
            int x = board.getPositions().indexOf(Character.toString(pos[0]).toLowerCase()) + 1;
            int y = Integer.parseInt(Character.toString(pos[1]));

            if (x > 0 && x <= 8 && y <= 8) {
                return new int[]{x, y};
            }
        }

        return new int[]{0, 0};
    }
}