import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("BLACKJACK");
        window.setLayout(new GridLayout(2,1));

        JPanel blackjackGUI = new JPanel();
        blackjackGUI.setLayout(new FlowLayout());

        JPanel dealerGUI = new JPanel();
        dealerGUI.setLayout(new FlowLayout());


        BlackJack game = new BlackJack();
        BlackJack.loadDeck();

        game.makeBlackJackGUI(blackjackGUI, window);
        game.makeDealerPortionOFBlackJackGUI(dealerGUI);


        window.add(dealerGUI);
        window.add(blackjackGUI);
        window.setBounds(500,500,750,750);
        window.setVisible(true);
    }
}