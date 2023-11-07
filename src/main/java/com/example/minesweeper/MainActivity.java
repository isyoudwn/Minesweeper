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
    public static class BlockButtons extends AppCompatButton {
        int x;
        int y;
        boolean mine;
        boolean flag;
        int neighborMines;
        static int flags = 10;
        static int blocks;

        // 생성자
        public BlockButtons(Context context, int x, int y) {

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

            if (flag == false) {
                setText("+");
                flags = flags - 1;

                return;
            }
            setText("");
            flags = flags + 1;
        }

        // 블록 열기 메소드
        public boolean breakBlock(View view) {

            setClickable(false);
            blocks = blocks - 1;

            if (mine == true) {
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        BlockButtons[][] buttons = new BlockButtons[9][9];
        
        // 버튼 81개 생성
        for (int i = 0; i < 9; i++) {

            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            for (int j = 0; j < 9; j++) {

                buttons[i][j] = new BlockButtons(this, i, j);
                tableRow.addView(buttons[i][j]);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        ((BlockButtons)view).toggleFlag();
                    }
                });
            }

        }

        // 10개의 버튼 임의로 선택 하여 지뢰 넣기
        Random random = new Random();
        Set<BlockButtons> selectedElements = new HashSet<>();
        int numberOfElementsToSelect = 10;

        while (selectedElements.size() < numberOfElementsToSelect) {
            int randomRow = random.nextInt(9);
            int randomCol = random.nextInt(9);
            BlockButtons selectedElement = buttons[randomRow][randomCol];

            if (!selectedElements.contains(selectedElement)) {
                selectedElements.add(selectedElement);
                selectedElement.setText("●");
                selectedElement.mine = true;
                selectedElement.setTextColor(Color.RED);
            }
        }


        // 주변 지뢰 수 계산
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                int count = buttons[i][j].neighborMines;

                if (selectedElements.contains(buttons[i][j])) continue;

                // 테두리 먼저 계산
                if (i == 0) {
                    if (j == 0) {
                        if (buttons[i][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        if (buttons[i + 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        if (buttons[i + 1][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    } else if (j == 8) {
                        if (buttons[i][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        if (buttons[i + 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        if (buttons[i + 1][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    } else {
                        // 왼
                        if (buttons[i][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 오
                        if (buttons[i][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 왼쪽 아래
                        if (buttons[i + 1][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 아래
                        if (buttons[i + 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 아래
                        if (buttons[i + 1][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    }
                } else if (i == 8) {
                    if (j == 0) {
                        // 위
                        if (buttons[i - 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 위
                        if (buttons[i - 1][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 오른쪽
                        if (buttons[i][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    } else if (j == 8) {
                        // 위
                        if (buttons[i - 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 왼쪽 위
                        if (buttons[i - 1][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 왼쪽
                        if (buttons[i][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    } else {
                        // 왼
                        if (buttons[i][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 오
                        if (buttons[i][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 왼쪽 위
                        if (buttons[i - 1][j - 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 위
                        if (buttons[i - 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 위
                        if (buttons[i - 1][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    }
                }

                // 테두리 제외
                else {
                    if (j == 0) {
                        // 오
                        if (buttons[i][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 위
                        if (buttons[i - 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 위
                        if (buttons[i - 1][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 아래
                        if (buttons[i + 1][j].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 아래
                        if (buttons[i + 1][j + 1].mine == true) {
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    }

                    else if (j == 8) {
                            // 위
                            if (buttons[i - 1][j].mine == true) {
                                count = count + 1;
                                buttons[i][j].setText(count + "");
                            }
                            // 대각선 왼쪽 위
                            if (buttons[i - 1][j - 1].mine == true) {
                                count = count + 1;
                                buttons[i][j].setText(count + "");
                            }
                            // 왼
                            if (buttons[i][j - 1].mine == true) {
                                count = count + 1;
                                buttons[i][j].setText(count + "");
                            }
                            // 대각선 왼쪽 아래
                            if (buttons[i + 1][j - 1].mine == true) {
                                count = count + 1;
                                buttons[i][j].setText(count + "");
                            }
                            // 아래
                            if (buttons[i + 1][j].mine == true) {
                                count = count + 1;
                                buttons[i][j].setText(count + "");
                            }
                    }

                    else {
                        // 왼
                        if (buttons[i][j-1].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 오
                        if (buttons[i][j+1].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 위
                        if (buttons[i-1][j].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 아래
                        if (buttons[i+1][j].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 왼쪽 위
                        if (buttons[i-1][j-1].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 위
                        if (buttons[i-1][j+1].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 왼쪽 아래
                        if (buttons[i+1][j-1].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                        // 대각선 오른쪽 아래
                        if (buttons[i+1][j+1].mine == true){
                            count = count + 1;
                            buttons[i][j].setText(count + "");
                        }
                    }
                }
            }
        }
    }
}