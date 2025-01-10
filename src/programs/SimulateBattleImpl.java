package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.logging.Logger;
import java.util.logging.Level;

public class SimulateBattleImpl implements SimulateBattle
{
    private PrintBattleLog printBattleLog;
    
    private static final Logger logger = Logger.getLogger(GeneratePresetImpl.class.getName());

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException 
    {
        List<Unit> gamerUnits = playerArmy.getUnits();
        List<Unit> pcUnits = computerArmy.getUnits();

        List<Unit> boxedUnits = new ArrayList<>();
        boxedUnits.addAll(gamerUnits);
        boxedUnits.addAll(pcUnits);

        boxedUnits.sort(Comparator.comparingInt(Unit::getBaseAttack).reversed());

        while (hasAliveUnits(gamerUnits) && hasAliveUnits(pcUnits)) 
        {
            for (Unit unit : boxedUnits) {
                if (!unit.isAlive()) continue;
                logger.log(Level.INFO, "{0} атака", unit.getName());
                Unit target = unit.getProgram().attack();
                printBattleLog.printBattleLog(unit, target);
            }
        }
        
        logger.log(Level.INFO, "Бой завершен!");
    }

    private boolean hasAliveUnits(List<Unit> units) 
    {
        return units.stream().anyMatch(Unit::isAlive);
    }
}
