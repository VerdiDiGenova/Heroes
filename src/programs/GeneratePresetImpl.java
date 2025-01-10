package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {


    private static final Logger game_logger = Logger.getLogger(GeneratePresetImpl.class.getName());
    private final PositionGenerator positionGenerator;
    
    public GeneratePresetImpl() 
    {
        this.positionGenerator = new PositionGenerator();
    }
    
    @Override
    public Army generate(List<Unit> unitList, int maxPoints) 
    {
        game_logger.log(Level.INFO, "Генерация пресетов");
        game_logger.log(Level.INFO, "Список юнитов: {0}", unitList);
        game_logger.log(Level.INFO, "Макс число очков: {0}", maxPoints);

        List<Unit> l_units = new ArrayList<>();

        Army gen_ArmyPC = new Army();
        int remainingPoints = maxPoints;

        unitList.sort((u1, u2) -> 
        {
            double efficiency1 = (u1.getBaseAttack() / (double) u1.getCost()) + (u1.getHealth() / (double) u1.getCost());
            double efficiency2 = (u2.getBaseAttack() / (double) u2.getCost()) + (u2.getHealth() / (double) u2.getCost());
            return Double.compare(efficiency2, efficiency1);
        });

        for (Unit unit : unitList) 
        {
            int count = Math.min(11, remainingPoints / unit.getCost());

            for (int i = 0; i < count; i++) 
            {
               String s_label = " " + Integer.toString(i);
               
               XY position = positionGenerator.generateUniquePosition();
               if (position == null) 
               {
                    game_logger.log(Level.INFO, "Генерация уникальных позиций завершилась неудачей.");
                    break;
               }
               l_units.add(new Unit(unit.getName() + s_label, unit.getUnitType(), unit.getHealth(), unit.getBaseAttack(), unit.getCost(), unit.getAttackType(), unit.getAttackBonuses(), unit.getDefenceBonuses(), position.x, position.y));
            }

            remainingPoints -= count * unit.getCost();
            
            if (remainingPoints <= 0) break;
        }
        gen_ArmyPC.setUnits(l_units);
        gen_ArmyPC.setPoints(maxPoints - remainingPoints);
        game_logger.log(Level.INFO, "Размер армии ПК: {0}", gen_ArmyPC.getUnits().size());
        game_logger.log(Level.INFO, "Число позиций армии ПК: {0}", gen_ArmyPC.getPoints());
        return gen_ArmyPC;
    }
}