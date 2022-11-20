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

            if (player.getHand().get(handIdx).getType().compareTo("Environment") == 0) {
                String error = new String("Cannot place environment card on table.");
                node.put("error", error);
                node.put("handIdx", handIdx);
                output.addPOJO(node);
            } else if (player.getHand().get(handIdx).getMana() > player.getMana()) {
                String error = new String("Not enough mana to place card on table.");
                node.put("error", error);
                node.put("handIdx", handIdx);
                output.addPOJO(node);
            } else {
                if (player.getIndexFrontRow() == 2) {
                    if (table[player.getIndexBackRow() - ((Minion) player.getHand().get(handIdx)).getPosition()].size() >= 5) {
                        String error = new String("Cannot place card on table since row is full.");
                        node.put("error", error);
                        node.put("handIdx", handIdx);
                        output.addPOJO(node);
                    } else {
                        table[player.getIndexBackRow() - ((Minion) player.getHand().get(handIdx)).getPosition()].add(((Minion) player.getHand().get(handIdx)));
                        player.setMana(player.getMana() - player.getHand().get(handIdx).getMana());
                        player.getHand().remove(handIdx);
                    }
                } else {
                    if (table[player.getIndexBackRow() + ((Minion) player.getHand().get(handIdx)).getPosition()].size() >= 5) {
                        String error = new String("Cannot place card on table since row is full.");
                        node.put("error", error);
                        node.put("handIdx", handIdx);
                        output.addPOJO(node);
                    } else {
                        table[player.getIndexBackRow() + ((Minion) player.getHand().get(handIdx)).getPosition()].add(((Minion) player.getHand().get(handIdx)));
                        player.setMana(player.getMana() - player.getHand().get(handIdx).getMana());
                        player.getHand().remove(handIdx);
                    }
                }
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

        public static void useEnvironmentCard(int handIdx, int affectedRow, Player attacker, Player attacked, ArrayList<Minion>[] table, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "useEnvironmentCard");
            node.put("handIdx", handIdx);
            node.put("affectedRow", affectedRow);

            if (attacker.getHand().get(handIdx).getType().compareTo("Environment") != 0) {
                node.put("error", "Chosen card is not of type environment.");
                output.addPOJO(node);
            } else if (attacker.getMana() < attacker.getHand().get(handIdx).getMana()) {
                node.put("error", "Not enough mana to use environment card.");
                output.addPOJO(node);
            } else if (affectedRow == attacker.getIndexFrontRow() || affectedRow == attacker.getIndexBackRow()) {
                node.put("error", "Chosen row does not belong to the enemy.");
                output.addPOJO(node);
            } else if (attacker.getHand().get(handIdx).getName().compareTo("Heart Hound") == 0 && (affectedRow == attacked.getIndexFrontRow() && table[attacker.getIndexFrontRow()].size() >= 5) || (affectedRow ==  attacked.getIndexBackRow() && table[attacker.getIndexBackRow()].size() >= 5)) {
                node.put("error", "Cannot steal enemy card since the player's row is full.");
                output.addPOJO(node);
            } else {
                int attackerRow;
                if (affectedRow == attacked.getIndexFrontRow()) {
                    attackerRow = attacker.getIndexFrontRow();
                } else {
                    attackerRow = attacker.getIndexBackRow();
                }

                ((Environment) attacker.getHand().get(handIdx)).environmentAction(table[affectedRow], table[attackerRow]);
                attacker.setMana(attacker.getMana() - attacker.getHand().get(handIdx).getMana());
                attacker.getHand().remove(handIdx);
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

            ArrayList<ArrayList<Minion>> cardsOnTable = new ArrayList<ArrayList<Minion>>(4);

            for (int i = 0; i < 4; ++i) {
                cardsOnTable.add(new ArrayList<>());
                for (Minion minion : table[i]) {
                    Minion newMinion = new Minion(minion);
                    cardsOnTable.get(i).add(newMinion);
                }
            }
            node.putPOJO("output", cardsOnTable);
            output.addPOJO(node);
        }

        public static void getEnvironmentCardsInHand(int playerIdx, ArrayList<Card> hand, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getEnvironmentCardsInHand");
            node.put("playerIdx", playerIdx);

            ArrayList<Environment> environmentCardsInHand = new ArrayList<Environment>();
            for (Card card : hand) {
                String isEnvironment = new String("Firestorm, Winterfell, Heart Hound");
                if (isEnvironment.indexOf(card.getName()) != -1) {
                    if (card.getName().compareTo("Firestorm") == 0) {
                        Firestorm firestorm = new Firestorm((Firestorm) card);
                        environmentCardsInHand.add(firestorm);
                    } else if (card.getName().compareTo("Winterfell") == 0){
                        Winterfell winterfell = new Winterfell((Winterfell) card);
                        environmentCardsInHand.add(winterfell);
                    } else {
                        HeartHound heartHound = new HeartHound((HeartHound) card);
                        environmentCardsInHand.add(heartHound);
                    }
                }
            }

            node.putPOJO("output", environmentCardsInHand);
            output.addPOJO(node);
        }

        public static void getCardAtPosition(int x, int y, ArrayList<Minion>[] table, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getCardAtPosition");
            node.put("x", x);
            node.put("y", y);

            if (y > table[x].size() - 1) {
                node.put("output", "No card available at that position.");
            } else {
                Minion minion = new Minion(table[x].get(y));
                node.putPOJO("output", minion);
            }
            output.addPOJO(node);
        }

        public static void getFrozenCardsOnTable(ArrayList<Minion>[] table, ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getFrozenCardsOnTable");

            ArrayList<ArrayList<Minion>> frozenCardsOnTable = new ArrayList<ArrayList<Minion>>();
            boolean ok;

            for (int i = 0; i < 4; ++i) {
                ok = false;
                for (Minion minion : table[i]) {
                    if (minion.getIsFrozen() == true) {
                        frozenCardsOnTable.add(new ArrayList<>());
                        Minion newMinion = new Minion(minion);
                        frozenCardsOnTable.get(i).add(newMinion);
                        ok = true;
                    }
                }
            }
            node.putPOJO("output", frozenCardsOnTable);
            output.addPOJO(node);
        }
    }
}