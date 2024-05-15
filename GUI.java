import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
   int stepDelay = 20;
    ArrayList<Integer> coords = new ArrayList<Integer>();

    public GUI(int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY, String fileType) {
        
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
/*
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
*/
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
                    if (fileType.equals("txt")) {
                        BinFileWriter.WriteLab("nowy_lab.bin", Lab, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                    }else if (fileType.equals("bin")) {
                        BinFileWriter.WriteLab("nowy_lab.bin", Lab, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                    }
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

        Lab2 = Lab;

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
        flooder.add(label, BorderLayout.CENTER);

        JLabel stan = new JLabel("Stan: w trakcie zalewania");
        flooder.add(stan, BorderLayout.NORTH);


        JButton autoZalew = new JButton("Zalej automatycznie");
        JButton oneStep = new JButton("Jeden krok");
        JButton allSteps = new JButton("Do końca");
        JButton floodOne = new JButton("Zalej raz");
        JButton floodAll = new JButton("Zalej do końca");
        JButton autoStep = new JButton("Automatyczny krok");
        JButton wallfollower = new JButton("Wallfollower");
        JButton rightPath = new JButton("Pokaż dobrą ścieżkę");

        wallfollower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                coords = Follower.Follow(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                oneStep.setVisible(true);
                allSteps.setVisible(true);
                autoStep.setVisible(true);
                autoZalew.setVisible(false);
                floodAll.setVisible(false);
                floodOne.setVisible(false);
                wallfollower.setVisible(false);
                flooder.add(rightPath, BorderLayout.SOUTH);
                rightPath.setVisible(false);
            }
        });

        rightPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Lab2= Zalewacz.Zalanie(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY, 4, 5);
                GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                refreshImage(label, Lab2, Columns, Rows, screenHeight, screenWidth);

                rightPath.setVisible(false);
                stan.setText("Stan: Pokazanie Dobrej Ścieżki");
            }
        });

                

        oneStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Lab2[coords.get(0)][coords.get(1)] = 4;
                GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                refreshImage(label, Lab2, Columns, Rows, screenHeight, screenWidth);
                coords.remove(0);
                coords.remove(0);

                if (coords.size() == 0){
                    stan.setText("Stan: Zakończono wyznaczanie ścieżki");
                    oneStep.setVisible(false);
                    allSteps.setVisible(false);
                    autoStep.setVisible(false);
                    wallfollower.setVisible(false);
                    rightPath.setVisible(true);
                }
            }
        });

        autoStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    while (coords.size() > 0) {
                        Lab2[coords.get(0)][coords.get(1)] = 4;
                        GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                        SwingUtilities.invokeLater(() -> refreshImage(label, Lab2, Columns, Rows, screenHeight, screenWidth));
                        coords.remove(0);
                        coords.remove(0);
                        try {
                            Thread.sleep(stepDelay);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    stan.setText("Stan: Zakończono wyznaczanie ścieżki");
                    oneStep.setVisible(false);
                    allSteps.setVisible(false);
                    autoStep.setVisible(false);
                    wallfollower.setVisible(false);
                    rightPath.setVisible(true);
                });
                thread.start();
            }
        });

        autoZalew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    while(Zalewacz.czyZalewalne(Lab2, Columns, Rows)) {
                        Lab2 = Zalewacz.ZalanieRaz(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                        GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                        SwingUtilities.invokeLater(() -> refreshImage(label, Lab2, Columns, Rows, screenHeight, screenWidth));
                        try {
                            Thread.sleep(stepDelay);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    stan.setText("Stan: Zalany labirynt - można przejść do wyznaczanie ścieżki");
                    coords = Follower.Follow(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                    SwingUtilities.invokeLater(() -> {
                        oneStep.setVisible(true);
                        allSteps.setVisible(true);
                        autoStep.setVisible(true);
                        autoZalew.setVisible(false);
                         
                         
                        floodAll.setVisible(false);
                        floodOne.setVisible(false);
                    });
                });
                thread.start();
            }
        });



        
        allSteps.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oneStep.setVisible(false);
                allSteps.setVisible(false);
                autoStep.setVisible(false);
                wallfollower.setVisible(false);
                rightPath.setVisible(true);
                for (int i  = 0; i < coords.size() - 2; i+=2){
                    Lab2[coords.get(i)][coords.get(i+1)] = 4;    
                }
                GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                refreshImage(label, Lab2, Columns, Rows, screenHeight, screenWidth);
                stan.setText("Stan: Zakończono wyznaczanie ścieżki");
            }
        });

        flooder.add(oneStep, BorderLayout.SOUTH);
        flooder.add(allSteps, BorderLayout.SOUTH);
        flooder.add(autoStep, BorderLayout.SOUTH);
        flooder.add(autoZalew, BorderLayout.SOUTH);
        flooder.add(wallfollower, BorderLayout.SOUTH);

        oneStep.setVisible(false);
        allSteps.setVisible(false);
        autoStep.setVisible(false);
        wallfollower.setVisible(true);
        rightPath.setVisible(false);


        
        floodOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Zalewacz.ZalanieRaz(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY) == null){
                    stan.setText("Stan: Zalany labirynt - można przejść do wyznaczanie ścieżki");
                    floodAll.setVisible(false);
                    floodOne.setVisible(false);
                    coords = Follower.Follow(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                    oneStep.setVisible(true);
                    allSteps.setVisible(true);
                    autoStep.setVisible(true);
                     
                     
                    autoZalew.setVisible(false);
                }
                GenerujObraz.GenerujObraz(Lab, Columns, Rows);
                refreshImage(label, Lab, Columns, Rows, screenHeight, screenWidth);
            }
        });

        flooder.add(floodOne, BorderLayout.SOUTH);    

        
        floodAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                floodAll.setVisible(false);
                floodOne.setVisible(false);
                 
                 
                autoZalew.setVisible(false);
                Lab2 = Zalewacz.Zalanie(Lab, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY, 0, 1);
                GenerujObraz.GenerujObraz(Lab2, Columns, Rows);
                refreshImage(label, Lab2, Columns, Rows, screenHeight, screenWidth);
                stan.setText("Stan: Zalany labirynt - można przejść do wyznaczanie ścieżki");
                coords = Follower.Follow(Lab2, Columns, Rows, localEnterX, localEnterY, localExitX, localExitY);
                oneStep.setVisible(true);
                allSteps.setVisible(true);
                autoStep.setVisible(true);
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

    public void refreshImage(JLabel label, int[][] Lab, int Columns, int Rows, int screenHeight, int screenWidth) {
        boolean flag = screenHeight < Rows || screenWidth < Columns;

        ImageIcon icon = new ImageIcon(flag ? "ResError.png" : "Labirynt.png");
        Image image = icon.getImage();
        Image newimg = flag ? 
            image.getScaledInstance(1365, 170, Image.SCALE_SMOOTH) :
            image.getScaledInstance(Columns * buttonSize, Rows * buttonSize, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        label.setIcon(icon);
        
    }
}

