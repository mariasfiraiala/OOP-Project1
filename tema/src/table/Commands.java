package table;

import cards.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

import java.util.ArrayList;

public class Commands {
    public static class RegularCommands {
        public static void placeCard(int handIdx, Player player, ArrayList<Minion>[] table, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "placeCard");
            node.put("handIdx", handIdx);

            if (player.getHand().get(handIdx).getType().compareTo("Environment") == 0) {
                String error = new String("Cannot place environment card on table.");
                node.put("error", error);
            } else if (player.getHand().get(handIdx).getMana() > player.getMana()) {
                String error = new String("Not enough mana to place card on table.");
                node.put("error", error);
            }else if (table[player.getIndexFrontRow() + ((Minion)player.getHand().get(handIdx)).getPosition()].size() > 5) {
                String error = new String("Cannot place card on table since row is full.");
                node.put("error", error);
            } else {
                if (player.getIndexFrontRow() == 2) {
                    table[player.getIndexBackRow() - ((Minion)player.getHand().get(handIdx)).getPosition()].add(((Minion)player.getHand().get(handIdx)));
                } else {
                    table[player.getIndexBackRow() + ((Minion)player.getHand().get(handIdx)).getPosition()].add(((Minion)player.getHand().get(handIdx)));
                }
                player.getHand().remove(handIdx);
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

        public static void getPlayerHero(int playerIdx, Player player, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerHero");
            node.put("playerIdx", playerIdx);

            if (player.getHero().getName().compareTo("Lord Royce") == 0) {
                LordRoyce lordRoyce = new LordRoyce((LordRoyce) player.getHero());
                node.putPOJO("output", lordRoyce);
            } else if (player.getHero().getName().compareTo("Empress Thorina") == 0) {
                EmpressThorina empressThorina = new EmpressThorina((EmpressThorina) player.getHero());
                node.putPOJO("output", empressThorina);
            } else if(player.getHero().getName().compareTo("King Mudface") == 0) {
                KingMudface kingMudface = new KingMudface((KingMudface) player.getHero());
                node.putPOJO("output", kingMudface);
            } else {
                GeneralKocioraw generalKocioraw = new GeneralKocioraw((GeneralKocioraw) player.getHero());
                node.putPOJO("output", generalKocioraw);
            }

            output.addPOJO(node);
        }

        public static void getPlayerTurn(Player player, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerTurn");
            int idx;

            if (player.getIndexFrontRow() == 2) {
                idx = 1;
            } else {
                idx = 2;
            }
            node.putPOJO("output", idx);
            output.addPOJO(node);
        }

        public static void getCardsInHand(int playerIdx, Player player, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getCardsInHand");
            node.put("playerIdx", playerIdx);

            ArrayList<Card> cardsInHand = new ArrayList<Card>();
            for (Card card : player.getHand()) {
                String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper, Miraj, The Cursed One, Disciple");
                String isSpecialMinion = new String("Miraj, The Ripper, Disciple, The Cursed One");

                if (isMinion.indexOf(card.getName()) != -1) {
                    if (isSpecialMinion.indexOf(card.getName()) == -1) {
                        Minion minion = new Minion((Minion) card);
                        cardsInHand.add(minion);
                    } else if (card.getName().compareTo("Miraj") == 0) {
                        Miraj miraj = new Miraj((Miraj)card);
                        cardsInHand.add(miraj);
                    } else if (card.getName().compareTo("The Ripper") == 0) {
                        TheRipper theRipper = new TheRipper((TheRipper) card);
                        cardsInHand.add(theRipper);
                    } else if (card.getName().compareTo("Disciple") == 0) {
                        Disciple disciple = new Disciple((Disciple) card);
                        cardsInHand.add(disciple);
                    } else {
                        TheCursedOne theCursedOne = new TheCursedOne((TheCursedOne) card);
                        cardsInHand.add(theCursedOne);
                    }
                } else {
                    if (card.getName().compareTo("Firestorm") == 0) {
                        Firestorm firestorm = new Firestorm((Firestorm) card);
                        cardsInHand.add(firestorm);
                    } else if (card.getName().compareTo("Winterfell") == 0) {
                        Winterfell winterfell = new Winterfell((Winterfell) card);
                        cardsInHand.add(winterfell);
                    } else {
                        HeartHound heartHound = new HeartHound((HeartHound) card);
                        cardsInHand.add(heartHound);
                    }
                }
            }

            node.putPOJO("output", cardsInHand);
            output.addPOJO(node);
        }

        public static void getPlayerMana(int playerIdx, Player player, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerMana");
            node.put("playerIdx", playerIdx);

            int mana = player.getMana();
            node.putPOJO("output", mana);
            output.addPOJO(node);
        }

        public static void getCardsOnTable(ArrayList<Minion>[] table, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getCardsOnTable");

            ArrayList<Minion> cardsOnTable = new ArrayList<Minion>();
            for (int i = 0; i < 4; ++i)
                for (Minion minion : table[i]) {
                    Minion newMinion = new Minion(minion);
                    cardsOnTable.add(newMinion);
                }
            node.putPOJO("output", cardsOnTable);
            output.addPOJO(node);
        }
    }
}