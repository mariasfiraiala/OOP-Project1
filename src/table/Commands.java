package table;

import cards.Card;
import cards.Disciple;
import cards.EmpressThorina;
import cards.Environment;
import cards.Firestorm;
import cards.GeneralKocioraw;
import cards.HeartHound;
import cards.Hero;
import cards.KingMudface;
import cards.LordRoyce;
import cards.Minion;
import cards.Miraj;
import cards.TheCursedOne;
import cards.TheRipper;
import cards.Winterfell;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

import java.util.ArrayList;

public final class Commands {
    public static final int LAST_POSITION = 5;
    public static final int MAX_ROWS = 4;

    private Commands() { }
    public static class RegularCommands {
        /**
         * placeCard action implementation
         * @param handIdx
         * @param player
         * @param table
         * @param output
         */
        public static void placeCard(final int handIdx, final Player player,
                                     final ArrayList<Minion>[] table, final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "placeCard");

            if (player.getHand().get(handIdx).getType().compareTo("Environment") == 0) {
                node.put("error", "Cannot place environment card on table.");
                node.put("handIdx", handIdx);
                output.addPOJO(node);
            } else if (player.getHand().get(handIdx).getMana() > player.getMana()) {
                node.put("error", "Not enough mana to place card on table.");
                node.put("handIdx", handIdx);
                output.addPOJO(node);
            } else {
                if (player.getIndexFrontRow() == 2) {
                    if (table[player.getIndexBackRow() - ((Minion) player.getHand().get(handIdx)).
                            getPosition()].size() >= LAST_POSITION) {
                        node.put("error", "Cannot place card on table since row is full.");
                        node.put("handIdx", handIdx);
                        output.addPOJO(node);
                    } else {
                        table[player.getIndexBackRow() - ((Minion) player.getHand().get(handIdx)).
                                getPosition()].add(((Minion) player.getHand().get(handIdx)));
                        player.setMana(player.getMana() - player.getHand().get(handIdx).getMana());
                        player.getHand().remove(handIdx);
                    }
                } else {
                    if (table[player.getIndexBackRow() + ((Minion) player.getHand().get(handIdx)).
                            getPosition()].size() >= LAST_POSITION) {
                        node.put("error", "Cannot place card on table since row is full.");
                        node.put("handIdx", handIdx);
                        output.addPOJO(node);
                    } else {
                        table[player.getIndexBackRow() + ((Minion) player.getHand().get(handIdx)).
                                getPosition()].add(((Minion) player.getHand().get(handIdx)));
                        player.setMana(player.getMana() - player.getHand().get(handIdx).getMana());
                        player.getHand().remove(handIdx);
                    }
                }
            }
        }

        /**
         * cardUsesAttack implementation
         * @param cardAttacker
         * @param cardAttacked
         * @param attacker
         * @param attacked
         * @param table
         * @param output
         */
        public static void cardUsesAttack(final Coordinates cardAttacker,
                                          final Coordinates cardAttacked,
                                          final Player attacker, final Player attacked,
                                          final ArrayList<Minion>[] table,
                                          final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "cardUsesAttack");
            node.putPOJO("cardAttacker", cardAttacker);
            node.putPOJO("cardAttacked", cardAttacked);

            boolean hasTank = false;
            ArrayList<Minion> tanks = new ArrayList<Minion>();

            for (Minion minion : table[attacked.getIndexFrontRow()]) {
                if (minion.getName().compareTo("Goliath") == 0 || minion.getName().
                        compareTo("Warden") == 0) {
                    hasTank = true;
                    tanks.add(minion);
                }
            }
            for (Minion minion : table[attacked.getIndexBackRow()]) {
                if (minion.getName().compareTo("Goliath") == 0 || minion.getName().
                        compareTo("Warden") == 0) {
                    hasTank = true;
                    tanks.add(minion);
                }
            }

            if (cardAttacked.getX() == attacker.getIndexFrontRow() || cardAttacked.getX()
                    == attacker.getIndexBackRow()) {
                node.put("error", "Attacked card does not belong to the enemy.");
                output.addPOJO(node);
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getHasAttacked()) {
                node.put("error", "Attacker card has already attacked this turn.");
                output.addPOJO(node);
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getIsFrozen()) {
                node.put("error", "Attacker card is frozen.");
                output.addPOJO(node);
            } else if (hasTank && !tanks.contains(table[cardAttacked.getX()].get(cardAttacked.
                    getY()))) {
                node.put("error", "Attacked card is not of type 'Tank'.");
                output.addPOJO(node);
            } else {
                table[cardAttacker.getX()].get(cardAttacker.getY()).setHasAttacked(true);
                table[cardAttacked.getX()].get(cardAttacked.getY()).setHealth(table[cardAttacked.
                        getX()].get(cardAttacked.getY()).getHealth() - table[cardAttacker.getX()].
                        get(cardAttacker.getY()).getAttackDamage());
                if (table[cardAttacked.getX()].get(cardAttacked.getY()).getHealth() <= 0) {
                    table[cardAttacked.getX()].remove(cardAttacked.getY());
                }
            }
        }

        /**
         * useEnvironmentCard action implementation
         * @param handIdx
         * @param affectedRow
         * @param attacker
         * @param attacked
         * @param table
         * @param output
         */

        public static void useEnvironmentCard(final int handIdx, final int affectedRow,
                                              final Player attacker, final Player attacked,
                                              final ArrayList<Minion>[] table,
                                              final ArrayNode output) {
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
            } else if (affectedRow == attacker.getIndexFrontRow() || affectedRow == attacker.
                    getIndexBackRow()) {
                node.put("error", "Chosen row does not belong to the enemy.");
                output.addPOJO(node);
            } else if (attacker.getHand().get(handIdx).getName().compareTo("Heart Hound") == 0
                    && (affectedRow == attacked.getIndexFrontRow()
                    && table[attacker.getIndexFrontRow()].size() >= LAST_POSITION) || (affectedRow
                    == attacked.getIndexBackRow() && table[attacker.getIndexBackRow()].
                    size() >= LAST_POSITION)) {
                node.put("error", "Cannot steal enemy card since the player's row is full.");
                output.addPOJO(node);
            } else {
                int attackerRow;
                if (affectedRow == attacked.getIndexFrontRow()) {
                    attackerRow = attacker.getIndexFrontRow();
                } else {
                    attackerRow = attacker.getIndexBackRow();
                }

                ((Environment) attacker.getHand().get(handIdx)).
                        environmentAction(table[affectedRow], table[attackerRow]);
                attacker.setMana(attacker.getMana() - attacker.getHand().get(handIdx).getMana());
                attacker.getHand().remove(handIdx);
            }
        }

        /**
         * cardUsesAbility action implementation
         * @param cardAttacker
         * @param cardAttacked
         * @param attacker
         * @param attacked
         * @param table
         * @param output
         */

        public static void cardUsesAbility(final Coordinates cardAttacker,
                                           final Coordinates cardAttacked,
                                           final Player attacker, final Player attacked,
                                           final ArrayList<Minion>[] table,
                                           final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "cardUsesAbility");
            node.putPOJO("cardAttacker", cardAttacker);
            node.putPOJO("cardAttacked", cardAttacked);

            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getIsFrozen()) {
                node.put("error", "Attacker card is frozen.");
                output.addPOJO(node);
                return;
            }
            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getHasAttacked()) {
                node.put("error", "Attacker card has already attacked this turn.");
                output.addPOJO(node);
                return;
            }
            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getName().compareTo("Disciple")
                    == 0 && (cardAttacked.getX() != attacker.getIndexFrontRow() && cardAttacked.
                    getX() != attacker.getIndexBackRow())) {
                node.put("error", "Attacked card does not belong to the current player.");
                output.addPOJO(node);
                return;
            }
            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getName().compareTo("Disciple")
                    != 0 && (cardAttacked.getX() == attacker.getIndexFrontRow() || cardAttacked.
                    getX() == attacker.getIndexBackRow())) {
                node.put("error", "Attacked card does not belong to the enemy.");
                output.addPOJO(node);
                return;
            }
            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getName().compareTo("Disciple")
                    != 0) {
                boolean hasTank = false;
                ArrayList<Minion> tanks = new ArrayList<Minion>();

                for (Minion minion : table[attacked.getIndexFrontRow()]) {
                    if (minion.getName().compareTo("Goliath") == 0 || minion.getName().
                            compareTo("Warden") == 0) {
                        hasTank = true;
                        tanks.add(minion);
                    }
                }
                for (Minion minion : table[attacked.getIndexBackRow()]) {
                    if (minion.getName().compareTo("Goliath") == 0 || minion.getName().
                            compareTo("Warden") == 0) {
                        hasTank = true;
                        tanks.add(minion);
                    }
                }

                if (hasTank && !tanks.contains(table[cardAttacked.getX()].get(cardAttacked.
                        getY()))) {
                    node.put("error", "Attacked card is not of type 'Tank'.");
                    output.addPOJO(node);
                    return;
                }
            }
            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getName().
                    compareTo("The Ripper") == 0) {
                ((TheRipper) table[cardAttacker.getX()].get(cardAttacker.getY())).
                        weakKnees(table[cardAttacked.getX()].get(cardAttacked.getY()));
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getName().
                    compareTo("Miraj") == 0) {
                ((Miraj) table[cardAttacker.getX()].get(cardAttacker.getY())).
                        skyJack(table[cardAttacked.getX()].get(cardAttacked.getY()));
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getName().
                    compareTo("The Cursed One") == 0) {
                ((TheCursedOne) table[cardAttacker.getX()].get(cardAttacker.getY())).
                        shapeshift(table[cardAttacked.getX()].get(cardAttacked.getY()));
                if (table[cardAttacked.getX()].get(cardAttacked.getY()).getHealth() == 0) {
                    table[cardAttacked.getX()].remove(cardAttacked.getY());
                }
            } else {
                ((Disciple) table[cardAttacker.getX()].get(cardAttacker.getY())).
                        godsPlan(table[cardAttacked.getX()].get(cardAttacked.getY()));
            }
            table[cardAttacker.getX()].get(cardAttacker.getY()).setHasAttacked(true);
        }

        /**
         * useAttackHero action implementation
         * @param cardAttacker
         * @param attacker
         * @param attacked
         * @param table
         * @param output
         * @return
         */
        public static int useAttackHero(final Coordinates cardAttacker, final Player attacker,
                                        final Player attacked,
                                        final ArrayList<Minion>[] table,
                                        final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();

            boolean hasTank = false;

            for (Minion minion : table[attacked.getIndexFrontRow()]) {
                if (minion.getName().compareTo("Goliath") == 0 || minion.getName().
                        compareTo("Warden") == 0) {
                    hasTank = true;
                }
            }
            for (Minion minion : table[attacked.getIndexBackRow()]) {
                if (minion.getName().compareTo("Goliath") == 0 || minion.getName().
                        compareTo("Warden") == 0) {
                    hasTank = true;
                }
            }
            int ret = 0;

            if (table[cardAttacker.getX()].get(cardAttacker.getY()).getIsFrozen()) {
                node.put("command", "useAttackHero");
                node.putPOJO("cardAttacker", cardAttacker);
                node.put("error", "Attacker card is frozen.");
                output.addPOJO(node);
            } else if (table[cardAttacker.getX()].get(cardAttacker.getY()).getHasAttacked()) {
                node.put("command", "useAttackHero");
                node.putPOJO("cardAttacker", cardAttacker);
                node.put("error", "Attacker card has already attacked this turn.");
                output.addPOJO(node);
            } else if (hasTank) {
                node.put("command", "useAttackHero");
                node.putPOJO("cardAttacker", cardAttacker);
                node.put("error", "Attacked card is not of type 'Tank'.");
                output.addPOJO(node);
            } else {
                attacked.getHero().setHealth(attacked.getHero().getHealth() - table[cardAttacker.
                        getX()].get(cardAttacker.getY()).getAttackDamage());
                table[cardAttacker.getX()].get(cardAttacker.getY()).setHasAttacked(true);

                if (attacked.getHero().getHealth() <= 0) {
                    if (attacker.getIndexFrontRow() == 2) {
                        node.put("gameEnded", "Player one killed the enemy hero.");
                        ret = 1;
                    } else {
                        node.put("gameEnded", "Player two killed the enemy hero.");
                        ret = 2;
                    }
                    output.addPOJO(node);
                }
            }
            return ret;
        }

        /**
         * useHeroAbility action implementation
         * @param affectedRow
         * @param attacker
         * @param attacked
         * @param table
         * @param output
         */
        public static void useHeroAbility(final int affectedRow, final Player attacker,
                                          final Player attacked,
                                          final ArrayList<Minion>[] table,
                                          final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();

            if (attacker.getMana() < attacker.getHero().getMana()) {
                node.put("command", "useHeroAbility");
                node.put("affectedRow", affectedRow);
                node.put("error", "Not enough mana to use hero's ability.");
                output.addPOJO(node);
            } else if (attacker.getHero().getHasAttacked()) {
                node.put("command", "useHeroAbility");
                node.put("affectedRow", affectedRow);
                node.put("error", "Hero has already attacked this turn.");
                output.addPOJO(node);
            } else if ((attacker.getHero().getName().compareTo("Lord Royce") == 0 || attacker.
                    getHero().getName().compareTo("Empress Thorina") == 0) && (affectedRow
                    != attacked.getIndexFrontRow() && affectedRow != attacked.getIndexBackRow())) {
                node.put("command", "useHeroAbility");
                node.put("affectedRow", affectedRow);
                node.put("error", "Selected row does not belong to the enemy.");
                output.addPOJO(node);
            } else if ((attacker.getHero().getName().compareTo("General Kocioraw") == 0
                    || attacker.getHero().getName().compareTo("King Mudface") == 0)
                    && (affectedRow != attacker.getIndexFrontRow() && affectedRow != attacker.
                            getIndexBackRow())) {
                node.put("command", "useHeroAbility");
                node.put("affectedRow", affectedRow);
                node.put("error", "Selected row does not belong to the current player.");
                output.addPOJO(node);
            } else {
                ((Hero) attacker.getHero()).heroAction(table[affectedRow]);
                attacker.setMana(attacker.getMana() - attacker.getHero().getMana());
                attacker.getHero().setHasAttacked(true);
            }
        }
    }

    public static class DebugCommands {
        /**
         * getPlayerDeck action implementation
         * @param playerIdx
         * @param player
         * @param output
         */
        public static void getPlayerDeck(final int playerIdx, final Player player,
                                         final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerDeck");
            node.put("playerIdx", playerIdx);

            ArrayList<Card> totalCards = new ArrayList<Card>();

            for (Card card : player.getCurrentDeck().getTotalCards()) {
                String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper,"
                        + "Miraj, The Cursed One, Disciple");
                String isSpecialMinion = new String("Miraj, The Ripper, Disciple, The Cursed One");

                if (isMinion.indexOf(card.getName()) != -1) {
                    if (isSpecialMinion.indexOf(card.getName()) == -1) {
                        Minion minion = new Minion((Minion) card);
                        totalCards.add(minion);
                    } else if (card.getName().compareTo("Miraj") == 0) {
                        Miraj miraj = new Miraj((Miraj) card);
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

        /**
         * getPlayerHero action implementation
         * @param playerIdx
         * @param player
         * @param output
         */
        public static void getPlayerHero(final int playerIdx, final Player player,
                                         final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerHero");
            node.put("playerIdx", playerIdx);

            if (player.getHero().getName().compareTo("Lord Royce") == 0) {
                LordRoyce lordRoyce = new LordRoyce((LordRoyce) player.getHero());
                node.putPOJO("output", lordRoyce);
            } else if (player.getHero().getName().compareTo("Empress Thorina") == 0) {
                EmpressThorina empressThorina = new EmpressThorina((EmpressThorina) player.
                        getHero());
                node.putPOJO("output", empressThorina);
            } else if (player.getHero().getName().compareTo("King Mudface") == 0) {
                KingMudface kingMudface = new KingMudface((KingMudface) player.getHero());
                node.putPOJO("output", kingMudface);
            } else {
                GeneralKocioraw generalKocioraw = new GeneralKocioraw((GeneralKocioraw) player.
                        getHero());
                node.putPOJO("output", generalKocioraw);
            }

            output.addPOJO(node);
        }

        /**
         * getPlayerTurn action implementation
         * @param player
         * @param output
         */
        public static void getPlayerTurn(final Player player, final ArrayNode output) {
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

        /**
         * getCardsInHand action implementation
         * @param playerIdx
         * @param player
         * @param output
         */
        public static void getCardsInHand(final int playerIdx, final Player player,
                                          final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getCardsInHand");
            node.put("playerIdx", playerIdx);

            ArrayList<Card> cardsInHand = new ArrayList<Card>();
            for (Card card : player.getHand()) {
                String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper,"
                        + "Miraj, The Cursed One, Disciple");
                String isSpecialMinion = new String("Miraj, The Ripper, Disciple, The Cursed One");

                if (isMinion.indexOf(card.getName()) != -1) {
                    if (isSpecialMinion.indexOf(card.getName()) == -1) {
                        Minion minion = new Minion((Minion) card);
                        cardsInHand.add(minion);
                    } else if (card.getName().compareTo("Miraj") == 0) {
                        Miraj miraj = new Miraj((Miraj) card);
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

        /**
         * getPlayerMana action implementation
         * @param playerIdx
         * @param player
         * @param output
         */
        public static void getPlayerMana(final int playerIdx, final Player player,
                                         final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerMana");
            node.put("playerIdx", playerIdx);

            int mana = player.getMana();
            node.putPOJO("output", mana);
            output.addPOJO(node);
        }

        /**
         * getCardsOnTable action implementation
         * @param table
         * @param output
         */
        public static void getCardsOnTable(final ArrayList<Minion>[] table,
                                           final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getCardsOnTable");

            ArrayList<ArrayList<Minion>> cardsOnTable = new ArrayList<ArrayList<Minion>>(MAX_ROWS);

            for (int i = 0; i < MAX_ROWS; ++i) {
                cardsOnTable.add(new ArrayList<>());
                for (Minion minion : table[i]) {
                    Minion newMinion = new Minion(minion);
                    cardsOnTable.get(i).add(newMinion);
                }
            }
            node.putPOJO("output", cardsOnTable);
            output.addPOJO(node);
        }

        /**
         * getEnvironmentCardsInHand action implementation
         * @param playerIdx
         * @param hand
         * @param output
         */
        public static void getEnvironmentCardsInHand(final int playerIdx,
                                                     final ArrayList<Card> hand,
                                                     final ArrayNode output) {
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
                    } else if (card.getName().compareTo("Winterfell") == 0) {
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

        /**
         * getCardAtPosition action implementation
         * @param x
         * @param y
         * @param table
         * @param output
         */
        public static void getCardAtPosition(final int x, final int y,
                                             final ArrayList<Minion>[] table,
                                             final ArrayNode output) {
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

        /**
         * getFrozenCardsOnTable action implementation
         * @param table
         * @param output
         */
        public static void getFrozenCardsOnTable(final ArrayList<Minion>[] table,
                                                 final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getFrozenCardsOnTable");

            ArrayList<ArrayList<Minion>> frozenCardsOnTable = new ArrayList<ArrayList<Minion>>();
            boolean ok;

            for (int i = 0; i < MAX_ROWS; ++i) {
                ok = false;
                for (Minion minion : table[i]) {
                    if (minion.getIsFrozen()) {
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

        /**
         * getPlayerOneWins statistical action implementation
         * @param playerOneWins
         * @param output
         */
        public static void getPlayerOneWins(final int playerOneWins, final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerOneWins");
            node.put("output", playerOneWins);
            output.addPOJO(node);
        }

        /**
         * getPlayerTwoWins statistical action implementation
         * @param playerTwoWins
         * @param output
         */
        public static void getPlayerTwoWins(final int playerTwoWins, final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getPlayerTwoWins");
            node.put("output", playerTwoWins);
            output.addPOJO(node);
        }

        /**
         * getTotalGamesPlayed statistical action implementation
         * @param totalGamesPlayed
         * @param output
         */
        public static void getTotalGamesPlayed(final int totalGamesPlayed,
                                               final ArrayNode output) {
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("command", "getTotalGamesPlayed");
            node.put("output", totalGamesPlayed);
            output.addPOJO(node);
        }
    }
}
