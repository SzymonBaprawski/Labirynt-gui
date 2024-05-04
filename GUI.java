import javax.swing.*;
import javax.swing.plaf.synth.SynthEditorPaneUI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

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
    int[][] Lab2;

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
        JPanel flooder = new JPanel();

        panel.add(edytor, "Edytor");
        panel.add(flooder, "flooder");

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

        JButton up = new JButton("↑");
        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (yOffset > 0) {
                    yOffset--;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(up, BorderLayout.NORTH);

        JButton down = new JButton("↓");
        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (yOffset + gridRows < Rows) {
                    yOffset++;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(down, BorderLayout.SOUTH);

        JButton left = new JButton("←");

        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (xOffset > 0) {
                    xOffset--;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(left, BorderLayout.WEST);

        JButton right = new JButton("→");
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (xOffset + gridColumns < Columns) {
                    xOffset++;
                    refreshButtons(buttons, Lab);
                }
            }
        });

        edytor.add(right, BorderLayout.EAST);

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

        JButton start = new JButton("Ustaw start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    aktualnyTryb = 2;
                    budowa.setText("Budowa/burzenie ścian");

            }
        });

        edytor.add(start, BorderLayout.EAST);

        JButton koniec = new JButton("Ustaw koniec");
        koniec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aktualnyTryb = 3;
                budowa.setText("Budowa/burzenie ścian");
            }
        });

        edytor.add(koniec, BorderLayout.WEST);

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

        JButton savetxt = new JButton("Zapisz do txt");

        savetxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    TxtFileWriter.WriteToFile("nowy_lab.txt", Lab, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                } catch (Exception ex) {
                    System.out.println("Błąd zapisu pliku");
                }
            }
        });

        edytor.add(savetxt, BorderLayout.SOUTH);

        JButton savebin = new JButton("Zapisz do bin");
        savebin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //do zaimplementowania
                } catch (Exception ex) {
                    System.out.println("Błąd zapisu pliku");
                }
            }
        });

        edytor.add(savebin, BorderLayout.SOUTH);

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
                                aktualnyTryb = 0;
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
                                aktualnyTryb = 0;
                            }

                        }
                    }
                });
            }
        }


        //TU SIĘ ZACZYNA FLOODER

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height - 70;

        System.out.println("Screen width: " + screenWidth);
        System.out.println("Screen height: " + screenHeight);

        if (screenWidth / Columns < screenHeight / Rows) {
            buttonSize = screenWidth / Columns;
        } else {
            buttonSize = screenHeight / Rows;
        }

        if (buttonSize == 0){
            buttonSize = 1;
        
        }

        ImageIcon icon = new ImageIcon("Labirynt.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        JLabel label = new JLabel(icon);
        flooder.add(label);

        JLabel stan = new JLabel("Stan: w trakcie zalewania");
        flooder.add(stan, BorderLayout.NORTH);

        JButton floodOne = new JButton("Zalej raz");
        floodOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Zalewacz.ZalanieRaz(Lab, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY) == null){
                    stan.setText("Stan: Zalany labirynt - można przejść do wyznaczanie ścieżki");
                }
                GenerujObraz.GenerujObraz(Lab, Columns, Rows);
                refreshImage(label, Lab, Columns, Rows);
            }
        });

        flooder.add(floodOne, BorderLayout.SOUTH);    

        JButton floodAll = new JButton("Zalej do końca");
        floodAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Lab2 = Zalewacz.Zalanie(Lab, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                refreshImage(label, Lab2, Columns, Rows);
                stan.setText("Stan: Zalany labirynt - można przejść do wyznaczanie ścieżki");
            }
        });

        flooder.add(floodAll, BorderLayout.SOUTH);

        cardLayout.show(panel, "Edytor");
        JButton flooder_button = new JButton("flooder");
        flooder_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "flooder");
                GenerujObraz.GenerujObraz(Lab, Columns, Rows);
                //refreshImage(null, Lab, Columns, Rows);
            }
        });
        edytor.add(flooder_button, BorderLayout.SOUTH);






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

    public void refreshImage(JLabel label, int[][] Lab, int Columns, int Rows) {
        GenerujObraz.GenerujObraz(Lab, Columns, Rows);
        ImageIcon icon = new ImageIcon("Labirynt.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(Columns * buttonSize, Rows * buttonSize, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        label.setIcon(icon);
    }
}

