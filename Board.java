package test;

public class Board {
    private static Board board = null;
    private Tile[][] tiles;

    private Board() {
        tiles = new Tile[15][15];
    }

    public static Board getBoard() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public boolean boardLegal(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.getVertical();

        if (row < 0 || col < 0 || row >= 15 || col >= 15) {
            return false;
        }

        if (vertical) {
            if (row + word.tiles.length > 15) {
                return false;
            }
            for (int r = row; r < row + word.tiles.length; r++) {
                Tile tile = word.tiles[r - row];
                if (tile == null) {
                    continue;
                }
                if (tiles[r][col] != null && tiles[r][col] != tile) {
                    return false;
                }
            }
        } else {
            if (col + word.tiles.length > 15) {
                return false;
            }
            for (int c = col; c < col + word.tiles.length; c++) {
                Tile tile = word.tiles[c - col];
                if (tile == null) {
                    continue;
                }
                if (tiles[row][c] != null && tiles[row][c] != tile) {
                    return false;
                }
            }
        }

        return true;
    }

    public int tryPlaceWord(Word word) {
        if (!boardLegal(word)) {
            return -1;
        }

        int score = 0;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.getVertical();

        if (vertical) {
            for (int r = row; r < row + word.tiles.length; r++) {
                Tile tile = word.tiles[r - row];
                if (tile != null) {
                    if (tiles[r][col] == null) {
                        tiles[r][col] = tile;
                        score += tile.score;
                    } else if (tiles[r][col].letter == tile.letter) {
                        score += tile.score;
                    }
                }
            }
        } else {
            for (int c = col; c < col + word.tiles.length; c++) {
                Tile tile = word.tiles[c - col];
                if (tile != null) {
                    if (tiles[row][c] == null) {
                        tiles[row][c] = tile;
                        score += tile.score;
                    } else if (tiles[row][c].letter == tile.letter) {
                        score += tile.score;
                    }
                }
            }
        }

        return score;
    }
}