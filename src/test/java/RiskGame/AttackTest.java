package RiskGame;

import org.junit.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;

import static org.junit.Assert.*;

public class AttackTest {

    private Attack atk = null;
    private Defend def = null;

    private TelegramBot getBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot = new TelegramBot(true);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return telegramBot;
    }

    private Attack getAttacker() {
        if (atk == null) {
            int numbOfAttackerSpareArmy = 1;
            int numbOfDefenderSpareArmy = 0;
            int numbOfAttackerPenaltyArmy = 1;
            int numbOfDefenderPenaltyArmy = 1;

            Player attacker = new Player("Ton", 3);
            Player defender = new Player("AI", 3);
            Territory attackerTerritory = new Territory("Alaska", 0);
            attackerTerritory.setNumbOfArmy(3);
            attackerTerritory.setOccupiedBy(attacker);
            Territory defenderTerritory = new Territory("Northwest", 1);
            defenderTerritory.setNumbOfArmy(3);
            defenderTerritory.setOccupiedBy(defender);
            atk = new Attack(attacker, attackerTerritory, defenderTerritory, attackerTerritory.getNumbOfArmy(),
                    numbOfAttackerSpareArmy, numbOfAttackerPenaltyArmy, numbOfDefenderPenaltyArmy);
        }
        return atk;
    }

    private Defend getDefender() {
        if (def == null) {
            int numbOfDefenderSpareArmy = 0;

            Player attacker = new Player("Ton", 3);
            Player defender = new Player("AI", 3);
            Territory attackerTerritory = new Territory("Northwest", 1);
            attackerTerritory.setNumbOfArmy(3);
            attackerTerritory.setOccupiedBy(attacker);
            Territory defenderTerritory = new Territory("Alaska", 0);
            defenderTerritory.setNumbOfArmy(3);
            defenderTerritory.setOccupiedBy(defender);
            def = new Defend(defender, defenderTerritory, attackerTerritory, defenderTerritory.getNumbOfArmy(),
                    numbOfDefenderSpareArmy);
        }
        return def;
    }

    @Test
    public void getArmyPenalty() {
        assertEquals(getAttacker().getArmyPenalty(), 1);
    }

    @Test
    public void setArmyPenalty() {
        getAttacker().setArmyPenalty(4);
        assertEquals(getAttacker().getArmyPenalty(), 4);
    }

    @Test
    public void getArmyPenaltyToDefender() {
        assertEquals(getAttacker().getArmyPenaltyToDefender(), 1);
    }

    @Test
    public void setArmyPenaltyToDefender() {
        getAttacker().setArmyPenaltyToDefender(2);
        assertEquals(getAttacker().getArmyPenaltyToDefender(), 2);
    }

    @Test
    public void getNumbOfMaxDie() {
        assertEquals(getAttacker().getNumbOfMaxDie(), 2);
        assertEquals(getDefender().getNumbOfMaxDie(), 3);
    }

    @Test
    public void getBattleResult() {
        atk = getAttacker();
        def = getDefender();

        atk.thisPlayer.getDice().forEach(die -> die.reset());
        if (atk.numbOfMaxDie > 0 && atk.numbOfMaxDie <= atk.thisPlayer.getDice().size()) {
            for (int i = 0; i < atk.numbOfMaxDie; i++) atk.thisPlayer.getDice().get(i).roll();
        }

        def.thisPlayer.getDice().forEach(die -> die.reset());
        if (def.numbOfMaxDie > 0 && def.numbOfMaxDie <= def.thisPlayer.getDice().size()) {
            for (int i = 0; i < def.numbOfMaxDie; i++) def.thisPlayer.getDice().get(i).roll();
        }

        int result = Battle.getBattleResult(atk, def);

        Die attackerMaxFaceValueDie = atk.thisPlayer.getDice().stream()
                .max(Comparator.comparing(die -> die.getCurrentValue())).get();
        Die defenderMaxFaceValueDie = def.thisPlayer.getDice().stream()
                .max(Comparator.comparing(die -> die.getCurrentValue())).get();
        if (attackerMaxFaceValueDie.getCurrentValue() > defenderMaxFaceValueDie.getCurrentValue())
            assertEquals(result, 1);
        else if (attackerMaxFaceValueDie.getCurrentValue() < defenderMaxFaceValueDie.getCurrentValue())
            assertEquals(result, -1);
        else assertEquals(result, 0);
    }

    @Test
    public void startBattle() {
        assertTrue(getAttacker().startBattle(getAttacker().getNumbOfMaxDie()));
        assertTrue(getDefender().startBattle(getDefender().getNumbOfMaxDie()));
        assertFalse(getAttacker().startBattle(0));
        assertFalse(getDefender().startBattle(0));
        assertFalse(getAttacker().startBattle(4));
        assertFalse(getDefender().startBattle(4));
    }

    @Test
    public void afterBattle() {
        TelegramBot bot = getBot();

        Defend def = getDefender();
        Attack atk = getAttacker();

        atk.afterBattle(-1, bot);
        atk.afterBattle(-2, bot);
        atk.afterBattle(0, bot);
        def.thisTerritory.setNumbOfArmy(1);
        atk.afterBattle(1, bot);

        atk = getAttacker();
        atk.thisTerritory.setNumbOfArmy(0);
        atk.afterBattle(-1, bot);
    }

    @Test
    public void afterBattle1() {
        TelegramBot bot = getBot();

        Defend def = getDefender();
        Attack atk;

        def.afterBattle(-1, bot);
        def.afterBattle(-2, bot);
        def.afterBattle(0, bot);
        def.thisPlayer.getOwnedTerritories().clear();
        def.afterBattle(1, bot);

        atk = getAttacker();
        atk.otherTerritory.setNumbOfArmy(1);
        atk.afterBattle(1, bot);
    }

    @Test
    public void afterBattle2() {
        Defend def = getDefender();
        Attack atk = getAttacker();

        atk.afterBattle(-1, null);
        atk.afterBattle(-2, null);
        atk.afterBattle(0, null);
        def.thisTerritory.setNumbOfArmy(1);
        atk.afterBattle(1, null);

        atk = getAttacker();
        atk.otherTerritory.setNumbOfArmy(1);
        atk.afterBattle(1, null);
    }
}