import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class BlackJack extends JFrame {
    //Total score

    JLabel userScoreText = new JLabel("Total: ", SwingConstants.CENTER);
    JLabel dealerScoreText = new JLabel("Dealer's total: ", SwingConstants.CENTER);

    static ArrayList<File> cardDeck = new ArrayList<>();
    int userScoreValue;
    int dealerScoreValue;

    JPanel dealerScoreAndCards = new JPanel();

    // Create panel to hold the card
    JPanel panelToEncapsulateCard = new JPanel();

    // load deck

    public static void loadDeck(){
        cardDeck.clear();

        File folder = new  File("src/PNG-cards-1.3");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++){
            cardDeck.add(listOfFiles[i]);
        }

    }

    //returns random card from file array
    public static String getRandomCard(){

        int roll = (int) (Math.random() * cardDeck.size());
        return cardDeck.remove(roll).getName();
    }

    //returns integer value for string card. for aces, 1 is chosen if sum is over 21

    public int getValue(String someCard){
        if(someCard.contains("jack") || someCard.contains("queen") || someCard.contains("king")){
            return 10;
        } else if (someCard.contains("ace")){
            return (userScoreValue + 11) > 21 ? 1 : 11;
        } else {
            return parseStringForValue(someCard);
        }
    }

    //returns number found in string of format "x_of_suit"
    public int parseStringForValue(String someCard){
        String getNumber = "";
        for (int i = 0; i < someCard.length(); i++){
            if (someCard.substring(i, i+1).equals("_")){
                 getNumber = (someCard.substring(0, i));
                 break;


            }
        }
        return Integer.parseInt(getNumber);
    }
    public JPanel rollImage(String user){
        //get a card image

        BufferedImage myPicture = null;

        try {
            String randomCardName = getRandomCard();
            int valueOfRandomCard = getValue(randomCardName);
            String pathName = "src/PNG-cards-1.3/" + randomCardName;

            myPicture = ImageIO.read(new File(pathName));

            if(user.equals("player")){
                userScoreValue += valueOfRandomCard;
                userScoreText.setText("Total: " + userScoreValue);
            } else if (user.equals("dealer")){
                dealerScoreValue += valueOfRandomCard;
                dealerScoreText.setText("Total: " + dealerScoreValue);
            }
        } catch (java.io.IOException ioe){
            ioe.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(myPicture);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(60,60, Image.SCALE_SMOOTH);

        //Create panel to hold the card
        JPanel panelToEncapsulateCard = new JPanel();
        panelToEncapsulateCard.add(new JLabel(new ImageIcon(newimg)));

        //return panel
        return panelToEncapsulateCard;
    }
    public JPanel makeBlackJackGUI(JPanel window, JFrame outer){
        userScoreText.setFont(new Font("Courier New", Font.PLAIN, 28));
        JPanel coolerSubPanel = new JPanel();
        coolerSubPanel.setLayout(new GridLayout(3, 1));

        //***********
        //Cards that are dealt

        JPanel panelToHoldCards = new JPanel();
        panelToHoldCards.setLayout(new FlowLayout());




        //Hit Button
        JButton hitButton = new JButton("Deal card");
        hitButton.setFont(new Font("Courier New", Font.PLAIN, 16));
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelToHoldCards.add(rollImage("player"));

                if (userScoreValue <= 21 ){

                }
                else {
                    boolean win = false;
                    windowPopup(win, outer);


                }

                window.revalidate();
                window.repaint();
            }
        });

        //Stay Button
        JButton stayButton = new JButton("Stay");
        stayButton.setFont(new Font("Courier New", Font.PLAIN, 16));
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean win = false;
                dealerScoreAndCards.remove(panelToEncapsulateCard);

                dealerScoreAndCards.add(rollImage("dealer"));

                while (dealerScoreValue < 17){
                    dealerScoreAndCards.add(rollImage("dealer"));
                }
                if (dealerScoreValue > userScoreValue){
                    win = false;
                    windowPopup(win, outer);
                }
                else {
                    win = true;
                    windowPopup(win, outer);
                }
            }
        });

        //Reset Button
        JButton resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Courier New", Font.PLAIN, 16));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelToHoldCards.removeAll();
                userScoreValue = 0;
                userScoreText.setText("Total: " + userScoreValue);
                window.revalidate();
            }
        });

        //Panel to hold the Hit button so that it can be centered
        JPanel panelToHoldButton = new JPanel(); //For the cell the Hit will be in
        panelToHoldButton.add(hitButton);
        panelToHoldButton.add(stayButton);
        panelToHoldButton.add(resetButton);
        //*********

        // Adding items to subpanel in desired order
        coolerSubPanel.add(userScoreText);
        coolerSubPanel.add(panelToHoldButton);
        coolerSubPanel.add(panelToHoldCards);
        //Adding subpanel to BlackJack window
        window.add(coolerSubPanel);
        return window;


    }
    public JPanel makeDealerPortionOFBlackJackGUI(JPanel window){
        dealerScoreText.setFont(new Font("Courier New", Font.PLAIN, 14));
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(2,1));

        //******
        JLabel gameTitle = new JLabel("BLACKJACK", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Times New Roman", Font.BOLD, 28));


        dealerScoreAndCards.setLayout(new GridLayout(1,2));

        //JLabel to hold all dealers Cards that are dealt
        JLabel dealersCards = new JLabel();
        dealersCards.setLayout(new FlowLayout());
        BufferedImage myPicture = null;
        try {
            myPicture  = ImageIO.read(new File("src/cardback.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(myPicture);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);



        // add dealer's total to subpanel
        dealerScoreAndCards.add(dealerScoreText);
        dealerScoreAndCards.add(dealersCards);
        //******


        // add cards to subpanel
        panelToEncapsulateCard.add(new JLabel(new ImageIcon(newimg)));
        dealerScoreAndCards.add(panelToEncapsulateCard);
        dealerScoreAndCards.add(rollImage("dealer"));



        subPanel.add(gameTitle);
        subPanel.add(dealerScoreAndCards);
        window.add(subPanel);
        return window;
    }

    public void windowPopup(boolean win, JFrame window){
        JFrame winLose = new JFrame();
        winLose.setLayout(new GridLayout(3,1));

        JPanel messageGUI = new JPanel();
        messageGUI.setLayout(new FlowLayout());
        JLabel message = new JLabel();
        message.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JPanel playCase = new JPanel();
        playCase.setLayout(new FlowLayout());

        JLabel playAgain = new JLabel();
        playAgain.setFont(new Font("Times New Roman", Font.BOLD, 20));
        playAgain.setText(" PLAY AGAIN? ");
        playCase.add(playAgain);

        if (win){
            message.setText(" YOU WIN ");

        }
        else {
            message.setText(" YOU LOST ");
        }
        messageGUI.add(message);


        JPanel playGUI = new JPanel();
        playGUI.setLayout(new GridLayout(1, 2));

        JPanel buttonCase1 = new JPanel();
        buttonCase1.setLayout(new FlowLayout());
        JButton yesButton = new JButton("YES");
        yesButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                winLose.setVisible(false);
                winLose.dispose();

                window.setVisible(false);
                window.dispose();

                JFrame window = new JFrame("BLACKJACK");
                window.setLayout(new GridLayout(2,1));

                JPanel blackjackGUI = new JPanel();
                blackjackGUI.setLayout(new FlowLayout());

                JPanel dealerGUI = new JPanel();
                dealerGUI.setLayout(new FlowLayout());


                BlackJack game = new BlackJack();

                game.makeBlackJackGUI(blackjackGUI, window);
                game.makeDealerPortionOFBlackJackGUI(dealerGUI);


                window.add(dealerGUI);
                window.add(blackjackGUI);
                window.setBounds(500,500,750,750);
                window.setVisible(true);

            }
        });

        buttonCase1.add(yesButton);

        JPanel buttonCase2 = new JPanel();
        buttonCase2.setLayout(new FlowLayout());
        JButton noButton = new JButton("NO");
        noButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                winLose.setVisible(false);
                winLose.dispose();

                window.setVisible(false);
                window.dispose();


            }
        });

        buttonCase2.add(noButton);

        playGUI.add(buttonCase1);
        playGUI.add(buttonCase2);


        winLose.add(messageGUI);
        winLose.add(playCase);
        winLose.add(playGUI);
        winLose.setVisible(true);
        winLose.setBounds(0,0, 500, 500);
    }


}
