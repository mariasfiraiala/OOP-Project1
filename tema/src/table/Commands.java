package table;

import cards.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

import java.util.ArrayList;

public class Commands {
    public static class RegularCommands {
        public static void applyPlaceCard(int handIndex, Player player, ArrayList<Minion>[] table) {
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

        public static void applyCardUsesAttack(Coordinates cardAttacker, Coordinates cardAttacked, Player attacker, Player attacked, ArrayList<Minion>[] table) {
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
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getIsFrozen() == true) {
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
        public static void getPlayerDeck(int playerIdx, Player player, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerDeck");
            node.put("playerIdx", playerIdx);

            ArrayList<Card> totalCards = new ArrayList<Card>();

            for (Card card : player.getCurrentDeck().getTotalCards()) {
                String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper, Miraj, The Cursed One, Disciple");
                String isSpecialMinion = new String("Miraj, The Ripper, Disciple, The Cursed One");

                if (isMinion.indexOf(card.getName()) != -1) {
                    if (isSpecialMinion.indexOf(card.getName()) == -1) {
                        Minion minion = new Minion((Minion) card);
                        totalCards.add(minion);
                    } else if (card.getName().compareTo("Miraj") == 0) {
                        Miraj miraj = new Miraj((Miraj)card);
                        totalCards.add(miraj);
                    } else if (card.getName().compareTo("The Ripper") == 0) {
                        TheRipper theRipper = new TheRipper((TheRipper) card);
                        totalCards.add(theRipper);
                    } else if (card.getName().compareTo("Disciple") == 0) {
                        Disciple disciple = new Disciple((Disciple) card);
                        totalCards.add(disciple);
                    } else {
                        TheCursedOne theCursedOne = new TheCursedOne((TheCursedOne) card);
                        totalCards.add(theCursedOne);
                    }
                } else {
                    if (card.getName().compareTo("Firestorm") == 0) {
                        Firestorm firestorm = new Firestorm((Firestorm) card);
                        totalCards.add(firestorm);
                    } else if (card.getName().compareTo("Winterfell") == 0) {
                        Winterfell winterfell = new Winterfell((Winterfell) card);
                        totalCards.add(winterfell);
                    } else {
                        HeartHound heartHound = new HeartHound((HeartHound) card);
                        totalCards.add(heartHound);
                    }
                }
            }

            node.putPOJO("output", totalCards);
            output.addPOJO(node);
        }
    }
}
