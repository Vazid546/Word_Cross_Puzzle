import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordCrossPuzzle {

    public static void main(String[] args) {
        char[][] puzzle = fill();                 /*calling fill method which is used to read the file and reads character by character ,r*/
        printPuzzle(puzzle);
        List<String> wordFromFile = new ArrayList<String>();
        try {

            System.out.println("Enter the word puzzle file name");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            String f = reader.readLine();
            File fp = new File(f);
            Scanner input = new Scanner(fp);
            while (input.hasNext()) {
                wordFromFile.add(input.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String word : wordFromFile) {
            System.out.println("\n\nStarted Searching for the word: \""+word+"\"\n");
            play(word, puzzle);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static char[][] fill() {
        FileReader fr = null;
        char[][] words = new char[0][];
        printPuzzle(words);

        try {
            System.out.println("Enter the  puzzle file name");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            String f = reader.readLine();
            File fp = new File(f);
            fr = new FileReader(fp);

            List<Character> size = new ArrayList<Character>();
            int c, i = 0, j = 0;
            int count = 0;

            while ((c = fr.read()) != -1) {
                if (count > 0) {
                    if (c != ' ' && c != '\n' && c != '\r') {
                        words[i][j] = (char) c;
                        if (j < words[0].length - 1) {
                            j++;
                        } else if (j == words[0].length - 1 && i <= words.length - 1) {
                            i++;
                            j = 0;
                        }
                    }
                } else {
                    char s = (char) c;
                    if (s != '\r' && s != '\n') {
                        size.add(s);
                    }
                }
                if (c == '\n' && count == 0) {
                    count++;
                    StringBuilder sb = new StringBuilder();
                    for (Character ch : size) {
                        sb.append(ch);
                    }
                    String temp = sb.toString().replaceAll("\\s+", " ");
                    System.out.println(temp);
                    words = new char[Integer.parseInt(temp.split(" ")[0])][Integer.parseInt(temp.split(" ")[1])];
                    i = 0;
                    j = 0;
                }
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void printPuzzle(char[][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                System.out.print(puzzle[i][j]);
                if (j != puzzle[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void play(String word, char[][] puzzle) {
        char[] charsToSearch = word.toCharArray();
        List<Integer> rowList = new ArrayList<>();
        List<Integer> colList = new ArrayList<>();
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == charsToSearch[0]) {
                    rowList.add(i);
                    colList.add(j);
                }
            }
        }
        if (rowList.size() == 0) {
            System.out.println("\tThe word \'" + word + "\' was not in the puzzle");
        } else {
            int size = rowList.size();
            int searchCount = 0;
            for (int s = 0; s < size; s++) {
                int rowNumber = rowList.get(s);
                int colNumber = colList.get(s);
                OUTER_LOOP:

                if (rowNumber == puzzle.length && colNumber == puzzle[0].length) {
                    System.out.println("\tThe word \'" + word + "\' was not in the puzzle");
                } else {
                    boolean upstatus = checkUp(puzzle, word, rowNumber, colNumber);
                    if (upstatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkUp\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean downStatus = checkDown(puzzle, word, rowNumber, colNumber);
                    if (downStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkDown\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean leftStatus = checkLeft(puzzle, word, rowNumber, colNumber);
                    if (leftStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkLeft\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean rightStatus = checkRight(puzzle, word, rowNumber, colNumber);
                    if (rightStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkRight\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean topLeftDiagonalStatus = checkTopLeftDiagonal(puzzle, word, rowNumber, colNumber);
                    if (topLeftDiagonalStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkTopLeftDiagonal\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean topRightDiagonalStatus = checkTopRightDiagonal(puzzle, word, rowNumber, colNumber);
                    if (topRightDiagonalStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkTopRightDiagonal\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean bottomLeftDiagonalStatus = checkBottomLeftDiagonal(puzzle, word, rowNumber, colNumber);
                    if (bottomLeftDiagonalStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkBottomLeftDiagonal\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    boolean bottomRightDiagonalStatus = checkBottomRightDiagonal(puzzle, word, rowNumber, colNumber);
                    if (bottomRightDiagonalStatus) {
                        System.out.println("\tThe word \'" + word + "\' was found by the method \'checkBottomRightDiagonal\' beginning in cell[" + rowNumber + "][" + colNumber + "]");
                        break OUTER_LOOP;
                    }
                    searchCount++;
                }
            }
            if (searchCount == size) {
                System.out.println("\tThe word \'" + word + "\' was not in the puzzle");
            }

        }
    }

    public static boolean checkUp(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = row, j = 0; i >= 0 && j < charsToSearch.length; i--, j++) {
                if (puzzle[i][col] == charsToSearch[j]) {
                    count++;
                }

                if (row - i != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDown(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = row, j = 0; i < puzzle.length && j < charsToSearch.length; i++, j++) {
                if (puzzle[i][col] == charsToSearch[j]) {
                    count++;
                }

                if (i - row != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkLeft(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = col, j = 0; i >= 0 && i < puzzle[0].length && j < charsToSearch.length; i--, j++) {
                if (puzzle[row][i] == charsToSearch[j]) {
                    count++;
                }

                if (col - i != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkRight(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = col, j = 0; i >= 0 && i < puzzle[0].length && j < charsToSearch.length; i++, j++) {
                if (puzzle[row][i] == charsToSearch[j]) {
                    count++;
                }

                if (i - col != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkTopLeftDiagonal(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = row, j = col, k = 0; i >= 0 && j >= 0 && j < puzzle[0].length && k < charsToSearch.length; i--, j--, k++) {
                if (puzzle[i][j] == charsToSearch[k]) {
                    count++;
                }

                if (row - i != count - 1 || col - j != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkTopRightDiagonal(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = row, j = col, k = 0; i >= 0 && j >= 0 && j < puzzle[0].length && k < charsToSearch.length; i--, j++, k++) {
                if (puzzle[i][j] == charsToSearch[k]) {
                    count++;
                }

                if (row - i != count - 1 || j - col != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBottomLeftDiagonal(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = row, j = col, k = 0; i >= 0 && i < puzzle.length && j >= 0 && j < puzzle[0].length && k < charsToSearch.length; i++, j--, k++) {
                if (puzzle[i][j] == charsToSearch[k]) {
                    count++;
                }

                if (i - row != count - 1 || col - j != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBottomRightDiagonal(char[][] puzzle, String word, int row, int col) {
        char[] charsToSearch = word.toCharArray();
        int count = 0;
        if (puzzle[row][col] == charsToSearch[0]) {
            for (int i = row, j = col, k = 0; i >= 0 && i < puzzle.length && j >= 0 && j < puzzle[0].length && k < charsToSearch.length; i++, j++, k++) {
                if (puzzle[i][j] == charsToSearch[k]) {
                    count++;
                }

                if (i - row != count - 1 || j - col != count - 1) {
                    return false;
                }
            }
            if (count == charsToSearch.length) {
                return true;
            }
        }
        return false;
    }
}
