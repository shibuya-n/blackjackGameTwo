import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class BlackJack extends JFrame {
    //Total score

    JLabel userScoreText = new JLabel("Total: ", SwingConstants.CENTER);
    JLabel dealerScoreText = new JLabel("Dealer's total: ", SwingConstants.CENTER);
    int userScoreValue;
    int dealerScoreValue;

    //returns random card from file array
    public static String getRandomCard(){
        File folder = new  File("src/PNG-cards-1.3");
        File[] listOfFiles = folder.listFiles();
        int roll = (int) (Math.random() * 52) + 1;
        return listOfFiles[roll].getName();
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
            myPicture = ImageIO.read(new File("PNG-cards-1.3/" + randomCardName));

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
    public JPanel makeBlackJackGUI(JPanel window){
        userScoreText.setFont(new Font("Courier New", Font.PLAIN, 28));
        JPanel coolerSubPanel = new JPanel();
        coolerSubPanel.setLayout(new GridLayout(3, 1));

        //***********
        //Cards that are dealt

        JLabel panelToHoldCards = new JLabel();
        panelToHoldCards.setLayout(new FlowLayout());
        panelToHoldCards.add(rollImage("player"));


        //Hit Button
        JButton hitButton = new JButton("Deal card");
        hitButton.setFont(new Font("Courier New", Font.PLAIN, 16));
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelToHoldCards.add(rollImage("player"));
                window.revalidate();
                window.repaint();
            }
        });

        //Stay Button
        JButton stayButton = new JButton("Stay");
        stayButton.setFont(new Font("Courier New", Font.PLAIN, 16));

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

        //Adding items to subpanel on desired order
        coolerSubPanel.add(userScoreText);
        coolerSubPanel.add()
    }



}
