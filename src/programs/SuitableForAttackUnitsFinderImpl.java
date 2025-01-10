package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder 
{
    private static final Logger game_logger = Logger.getLogger(GeneratePresetImpl.class.getName());
    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) 
    {
        List<Unit> suitableUnits = new ArrayList<>();
        Set<String> aliveUnitPositions = new HashSet<>();
        for (List<Unit> l_row : unitsByRow) 
        {
            for (Unit unit : l_row) 
            {
                if (unit != null && unit.isAlive()) 
                {
                    aliveUnitPositions.add(getPositionKey(unit.getxCoordinate(), unit.getyCoordinate()));
                }
            }
        }
        
        for (List<Unit> l_row : unitsByRow) 
        {
            for (Unit currentUnit : l_row) 
            {
                if (currentUnit == null || !currentUnit.isAlive()) continue;

                int x = currentUnit.getxCoordinate();
                int y = currentUnit.getyCoordinate();

                if (isLeftArmyTarget) 
                {
                    if (y == 0 || getValidPosition(aliveUnitPositions, x, y - 1)) 
                    {
                        suitableUnits.add(currentUnit);
                    }
                } 
                else 
               {
                    if (y == l_row.size() - 1 || getValidPosition(aliveUnitPositions, x, y + 1)) 
                        suitableUnits.add(currentUnit);
                }
            }
        }

        if (suitableUnits.isEmpty()) {
            game_logger.log(Level.INFO, "Не найден подходящий юнит");
        }
        return suitableUnits;
    }

    private boolean getValidPosition(Set<String> aliveUnitPositions, int x, int y) {
        if (y < 0 || y >= 21) {
            return false;
        }
        return !aliveUnitPositions.contains(getPositionKey(x, y));
    }

    private String getPositionKey(int x, int y) {
        return x + "," + y;
    }
}