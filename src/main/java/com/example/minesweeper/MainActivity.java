package com.example.minesweeper;

import static com.example.minesweeper.MainActivity.BlockButton.flags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final int MINES = 10;
    BlockButton[][] button;
    TableLayout tableLayout;

    // BlockButton 클래스
    public static class BlockButton extends AppCompatButton {
        int x;
        int y;
        boolean mine;
        boolean flag;
        int neighborMines;
        static int flags = 10;
        static int blocks;

        // 생성자
        public BlockButton(Context context, int x, int y) {

            super(context);

            TableRow.LayoutParams layoutParams =
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1.0f);

            setLayoutParams(layoutParams);

            this.x = x;

            this.y = y;

            blocks = blocks + 1;

            mine = false;

            flag = false;

            neighborMines = 0;
        }

        // 깃발 꽂기 or 해제 메소드
        public void toggleFlag() {

            if (!flag) {
                setText("+");
                flags = flags - 1;

                return;
            }
            setText("");
            flags = flags + 1;
        }

        // 지뢰인지 검사
        public boolean isMine() {

            setClickable(false);

            blocks = blocks - 1;

            if (mine) {

                setText("●");

                return true;
            }

            setText(neighborMines + "");
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        button = new BlockButton[9][9];
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        Set<BlockButton> mines = new HashSet<>();

        // 버튼 81개 세팅
        settingButtons(tableLayout);
        // 지뢰 10개 세팅
        settingMines(mines);
        // 주변 지뢰 수 계산
        countNeighborMine(mines);
    }

    /**
     *
     * TODO : 깃발 컨트롤 (Toggle Button)
     * toggle 열린 상태면 깃발 -> 깃발 수 및 전체 mine 수 -1
     * toggle 닫혀 있으면 버튼 누르기
     *
     */


    // 버튼 생성
    public void settingButtons(TableLayout tableLayout) {
        // 버튼 81개 생성
        for (int i = 0; i < 9; i++) {

            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            for (int j = 0; j < 9; j++) {
                button[i][j] = new BlockButton(this, i, j);
                tableRow.addView(button[i][j]);

                button[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        boolean status = ((BlockButton)view).isMine();
                            if (status) {
                                Toast.makeText(MainActivity.this, "게임오버", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                openBlock(((BlockButton) view).x, ((BlockButton) view).y);
                            }
                    }
                });
            }
        }
    }

    
    
    // 지뢰 10개 세팅
    public void settingMines(Set<BlockButton> mines) {
        Random random = new Random();

        while (mines.size() < MINES) {
            int randomRow = random.nextInt(9);
            int randomCol = random.nextInt(9);
            BlockButton selectedElement = button[randomRow][randomCol];

            if (!mines.contains(selectedElement)) {
                mines.add(selectedElement);
                selectedElement.mine = true;
            }
        }
    }


    public void countNeighborMine(Set<BlockButton> mines) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (mines.contains(button[i][j])) continue;

                if (i == 0 && j == 0) {
                    if (button[i][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    if (button[i + 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (i == 0 && j == 8) {
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    if (button[i + 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (i == 8 && j == 8) {
                    // 위
                    if (button[i - 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 위
                    if (button[i - 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 왼쪽
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (i == 8 && j == 0) {
                    // 위
                    if (button[i - 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 위
                    if (button[i - 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 오른쪽
                    if (button[i][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (i == 0 && j != 8 || i == 0 && j != 0) {
                    // 왼
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 오
                    if (button[i][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 아래
                    if (button[i + 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 아래
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 아래
                    if (button[i + 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (i == 8 && j != 0 || i == 8 && j != 8) {
                    // 왼
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 오
                    if (button[i][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 위
                    if (button[i - 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 위
                    if (button[i - 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 위
                    if (button[i - 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (j == 0 && i != 0 || j == 0 && i != 8) {
                    // 오
                    if (button[i][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 위
                    if (button[i - 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 위
                    if (button[i - 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 아래
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 아래
                    if (button[i + 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else if (j == 8 && i != 0 || j == 8 && i != 8) {
                    // 위
                    if (button[i - 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 위
                    if (button[i - 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 왼
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 아래
                    if (button[i + 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 아래
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                } else {
                    // 왼
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 오
                    if (button[i][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 위
                    if (button[i - 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 아래
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 위
                    if (button[i - 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 위
                    if (button[i - 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 아래
                    if (button[i + 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 아래
                    if (button[i + 1][j + 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                }
            }
        }
    }

    // 재귀로 블록 열기
    public void openBlock (int x, int y) {

        BlockButton clickedButton = (BlockButton) button[x][y];

        int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
        int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

        clickedButton.setText(clickedButton.neighborMines + "");

        if (clickedButton.neighborMines == 0) {
            for (int i = 0; i < dx.length; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= 9 || ny < 0 || ny >= 9 || !button[nx][ny].getText().equals("") || button[nx][ny].isMine()) {
                    continue;
                }
                openBlock(nx, ny);
            }
        }
    }
}