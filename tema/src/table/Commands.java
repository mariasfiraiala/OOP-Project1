package table;

import cards.Minion;
import fileio.Coordinates;

import java.util.ArrayList;

public class Commands {
    public static class RegularCommands {
        public void applyPlaceCard(int handIndex, Player player, ArrayList<Minion>[] table) {
            if (player.getHand().get(handIndex).getType().compareTo("Environment") == 0) {
                System.out.println("Cannot place environment card on table.");
            } else if (player.getHand().get(handIndex).getMana() > player.getMana()) {
                System.out.println("Not enough mana to place card on table.");
            }else if (table[player.getIndexFrontRow() + ((Minion)player.getHand().get(handIndex)).getPosition()].size() > 5) {
                System.out.println("Cannot place card on table since row is full.");
            } else {
                if (player.getIndexFrontRow() == 2) {
                    table[player.getIndexBackRow() - ((Minion)player.getHand().get(handIndex)).getPosition()].add(((Minion)player.getHand().get(handIndex)));
                } else {
                    table[player.getIndexBackRow() + ((Minion)player.getHand().get(handIndex)).getPosition()].add(((Minion)player.getHand().get(handIndex)));
                }
                player.getHand().remove(handIndex);
            }
        }

        public void applyCardUsesAttack(Coordinates cardAttacker, Coordinates cardAttacked, Player attacker, Player attacked, ArrayList<Minion>[] table) {
            boolean hasTank = false;
            ArrayList<Minion> tanks = new ArrayList<Minion>();

            for (Minion minion : table[attacked.getIndexFrontRow()]) {
                if (minion.getName().compareTo("Goliath") == 0 || minion.getName().compareTo("Warden") == 0) {
                    hasTank = true;
                    tanks.add(minion);
                }
            }
            for (Minion minion : table[attacked.getIndexBackRow()]) {
                if (minion.getName().compareTo("Goliath") == 0 || minion.getName().compareTo("Warden") == 0) {
                    hasTank = true;
                    tanks.add(minion);
                }
            }

            if (cardAttacked.getX() == attacker.getIndexFrontRow() || cardAttacked.getX() == attacker.getIndexBackRow()) {
                System.out.println("Attacked card does not belong to the enemy.");
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getHasAttacked() == true) {
                System.out.println("Attacker card has already attacked this turn.");
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getFrozen() == true) {
                System.out.println("Attacker card is frozen.");
            } else if (hasTank == true && tanks.contains(table[cardAttacked.getX()].get(cardAttacked.getY())) == false) {
                System.out.println("Attacked card is not of type 'Tank’");
            } else {
                table[cardAttacker.getX()].get(cardAttacker.getY()).setHasAttacked(true);
                table[cardAttacked.getX()].get(cardAttacked.getY()).setHealth(table[cardAttacked.getX()].get(cardAttacked.getY()).getHealth() - table[cardAttacker.getX()].get(cardAttacker.getY()).getAttackDamage());
                if (table[cardAttacked.getX()].get(cardAttacked.getY()).getHealth() <= 0) {
                    table[cardAttacked.getX()].remove(cardAttacked.getY());
                }
            }
        }
    }

    public static class DebugCommands {

    }
}
