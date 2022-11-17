package table;

import cards.Card;
import cards.Minion;

import java.util.ArrayList;

public class Commands {
    public static class RegularCommands {
        public void applyPlaceCard(int handIndex, Player player, ArrayList<Minion>[] table) {
            if (player.getHand().get(handIndex).getType().compareTo("Environment") == 0) {
                System.out.println("Cannot place environment card on table.");
            } else if (player.getHand().get(handIndex).getMana() > player.getMana()) {
                System.out.println("Not enough mana to place card on table.");
            }else if (table[player.getIndexRow1() + ((Minion)player.getHand().get(handIndex)).getPosition()].size() > 5) {
                System.out.println("Cannot place card on table since row is full.");
            } else {
                table[player.getIndexRow1() + ((Minion)player.getHand().get(handIndex)).getPosition()].add(((Minion)player.getHand().get(handIndex)));
            }
        }
    }

    public static class DebugCommands {

    }
}
