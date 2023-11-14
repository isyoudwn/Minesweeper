package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

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

        // 블록 열기 메소드
        public boolean breakBlock(View view) {
            view.setClickable(false);

            blocks = blocks - 1;

            if (mine) {

                setText("●");
                setTextColor(Color.RED);

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

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        BlockButton[][] button = new BlockButton[9][9];
        
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
                        BlockButton blockButton = (BlockButton) view;
                        boolean status = blockButton.breakBlock(view);

                        if (!status && blockButton.getText().equals("0")) {

                            if (blockButton.x == 0) {
                                if (blockButton.y == 0 || blockButton.y == 8) {
                                }

                            }

                        }
                    }
                });

            }

        }

        // 10개의 버튼 임의로 선택 하여 지뢰 넣기
        Random random = new Random();
        Set<BlockButton> selectedElements = new HashSet<>();
        int numberOfElementsToSelect = 10;

        while (selectedElements.size() < numberOfElementsToSelect) {
            int randomRow = random.nextInt(9);
            int randomCol = random.nextInt(9);
            BlockButton selectedElement = button[randomRow][randomCol];

            if (!selectedElements.contains(selectedElement)) {
                selectedElements.add(selectedElement);
                selectedElement.mine = true;
            }
        }


        // 주변 지뢰 수 계산
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (selectedElements.contains(button[i][j])) continue;

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
                }

                else if (i == 0 && j == 8) {
                    if (button[i][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    if (button[i + 1][j].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    if (button[i + 1][j - 1].mine) {
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                }

                else if (i == 8 && j == 8) {
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
                }

                else if (i == 8 && j == 0) {
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
                }

                else if (i == 0 && j != 8 || i == 0 && j != 0) {
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
                }

                else if (i == 8 && j != 0 || i == 8 && j != 8) {
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
                }

                else if (j == 0 && i != 0 || j == 0 && i != 8) {
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
                }

                else if (j == 8 && i != 0 || j == 8 && i !=8) {
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
                }

                else {
                    // 왼
                    if (button[i][j-1].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 오
                    if (button[i][j+1].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 위
                    if (button[i-1][j].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 아래
                    if (button[i+1][j].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 위
                    if (button[i-1][j-1].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 위
                    if (button[i-1][j+1].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 왼쪽 아래
                    if (button[i+1][j-1].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                    // 대각선 오른쪽 아래
                    if (button[i+1][j+1].mine){
                        button[i][j].neighborMines = button[i][j].neighborMines + 1;
                    }
                }
            }
        }
    }
}