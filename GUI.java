import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    int aktualnyTryb = 0;  //0 = nic, 1 = budowa, 2 = start, 3 = meta
    int gridColumns = 0;
    int gridRows = 0;
    int gridLimit = 30;
    int buttonSize = 25;
    int xOffset = 0;
    int yOffset = 0;
    int localEnterX = 0;
    int localEnterY = 0;
    int localExitX = 0;
    int localExitY = 0;

    public GUI(int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY) {
        
        localEnterX = EnterX;
        localEnterY = EnterY;
        localExitX = ExitX;
        localExitY = ExitY;
        
        JFrame frame = new JFrame("Labirynt GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        CardLayout cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        JPanel edytor = new JPanel();
        JPanel solver = new JPanel();

        panel.add(edytor, "Edytor");
        panel.add(solver, "Solver");

        frame.add(panel);

        if (Columns > gridLimit) {
            gridColumns = gridLimit;
        } else {
            gridColumns = Columns;
        }
        if (Rows > gridLimit) {
            gridRows = gridLimit;
        } else {
            gridRows = Rows;
        }

        JPanel grid = new JPanel(new GridLayout(gridRows, gridColumns));

        JButton[][] buttons = new JButton[gridColumns][gridRows];

        for (int j = 0; j < gridRows; j++) {
            for (int i = 0; i < gridColumns; i++) {
            buttons[i][j] = new JButton();
            buttons[i][j].setPreferredSize(new Dimension(buttonSize, buttonSize));
            if (Lab[i + xOffset][j + yOffset] == 0) {
                buttons[i][j].setBackground(Color.WHITE);
            } else if (Lab[i + xOffset][j + yOffset] == 1) {
                buttons[i][j].setBackground(Color.BLACK);
            } else if (Lab[i + xOffset][j + yOffset] == 2) {
                buttons[i][j].setBackground(Color.GREEN);
            } else if (Lab[i + xOffset][j + yOffset] == 3) {
                buttons[i][j].setBackground(Color.RED);
            }
            grid.add(buttons[i][j]);
            }
        }

        edytor.add(grid, BorderLayout.LINE_START);
        
        
        JButton up = new JButton("Up");
        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (yOffset > 0) {
                    yOffset--;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(up, BorderLayout.NORTH);

        JButton down = new JButton("Down");
        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (yOffset + gridRows < Rows) {
                    yOffset++;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(down, BorderLayout.SOUTH);

        JButton left = new JButton("Left");

        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (xOffset > 0) {
                    xOffset--;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(left, BorderLayout.WEST);

        JButton right = new JButton("Right");
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (xOffset + gridColumns < Columns) {
                    xOffset++;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(right, BorderLayout.EAST);

        JButton start = new JButton("Ustaw start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (aktualnyTryb == 0) {
                    aktualnyTryb = 2;
                    start.setText("Zakończ ustawianie startu");
                } else if (aktualnyTryb == 2) {
                    aktualnyTryb = 0;
                    start.setText("Ustaw start");
                }
            }
        });

        edytor.add(start, BorderLayout.EAST);

        JButton koniec = new JButton("Ustaw koniec");
        koniec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (aktualnyTryb == 0) {
                    aktualnyTryb = 3;
                    koniec.setText("Zakończ ustawianie końca");
                } else if (aktualnyTryb == 3) {
                    aktualnyTryb = 0;
                    koniec.setText("Ustaw koniec");
                }
            }
        });

        edytor.add(koniec, BorderLayout.WEST);

        JButton budowa = new JButton("Budowa/burzenie ścian");
        budowa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (aktualnyTryb == 0) {
                    aktualnyTryb = 1;
                    budowa.setText("Zakończ budowę/burzenie ścian");
                } else if (aktualnyTryb == 1) {
                    aktualnyTryb = 0;
                    budowa.setText("Budowa/burzenie ścian");
                }
            }
        });

        edytor.add(budowa, BorderLayout.NORTH);

        JButton print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("====================================");
                for (int i = 0; i < Rows; i++) {
                    for (int j = 0; j < Columns; j++) {
                        System.out.print(Lab[j][i]);
                    }
                    System.out.println();
                }
            }
        });

        edytor.add(print, BorderLayout.SOUTH);

        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                final int x = i;
                final int y = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                         if (aktualnyTryb == 1) {  
                            if (Lab[x + xOffset][y + yOffset] == 0) {
                                Lab[x + xOffset][y + yOffset] = 1;
                                buttons[x][y].setBackground(Color.BLACK);
                            } else if (Lab[x + xOffset][y + yOffset] == 1) {
                                Lab[x + xOffset][y + yOffset] = 0;
                                buttons[x][y].setBackground(Color.WHITE);
                            }
                        } else if (aktualnyTryb == 2) {
                            if (Lab[x + xOffset][y + yOffset] == 0 || Lab[x + xOffset][y + yOffset] == 1) {
                                Lab[x + xOffset][y + yOffset] = 2;
                                if (localEnterX >= xOffset && localEnterY >= yOffset && localEnterX <= gridColumns + xOffset && localEnterY <= gridRows + yOffset){
                                    if (Lab[localEnterX][localEnterY] == 2){
                                        buttons[localEnterX][localEnterY].setBackground(Color.WHITE);
                                    }
                                }
                                Lab[localEnterX][localEnterY] = 0;
                                buttons[x][y].setBackground(Color.GREEN);
                                localEnterX = x + xOffset;
                                localEnterY = y + yOffset;
                            }
                        } else if (aktualnyTryb == 3) {
                            if (Lab[x + xOffset][y + yOffset] == 0 || Lab[x + xOffset][y + yOffset] == 1) {
                                Lab[x + xOffset][y + yOffset] = 3;
                                if(localExitX >= xOffset && localExitY >= yOffset && localExitX <= gridColumns + xOffset && localExitY <= gridRows + yOffset){
                                    if(Lab[localExitX][localExitY] == 3){
                                        buttons[localExitX][localExitY].setBackground(Color.WHITE); 
                                    }
                                }
                                Lab[localExitX][localExitY] = 0;
                                buttons[x][y].setBackground(Color.RED);
                                localExitX = x + xOffset;
                                localExitY = y + yOffset;
                            }

                        }
                    }
                });
            }
        }
        

        cardLayout.show(panel, "Edytor");
        JButton solver_button = new JButton("Solver");
        solver_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "Solver");
            }
        });
        edytor.add(solver_button, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    public void refreshButtons(JButton[][] buttons, int[][] Lab) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (Lab[i + xOffset][j + yOffset] == 0) {
                    buttons[i][j].setBackground(Color.WHITE);
                } else if (Lab[i + xOffset][j + yOffset] == 1) {
                    buttons[i][j].setBackground(Color.BLACK);
                } else if (Lab[i + xOffset][j + yOffset] == 2) {
                    buttons[i][j].setBackground(Color.GREEN);
                } else if (Lab[i + xOffset][j + yOffset] == 3) {
                    buttons[i][j].setBackground(Color.RED);
                }
            }
        }
    }

}

