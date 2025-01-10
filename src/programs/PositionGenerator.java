package programs;

import java.util.*;

public class PositionGenerator 
{
    private List<String> availablePositions;
    private Set<String> occupiedPositions;

    public PositionGenerator() 
    {
        availablePositions = new ArrayList<>();
        occupiedPositions = new HashSet<>();
        initializePositions();
    }

    private void initializePositions() 
    {
        for (int x = 0; x < 3; x++) 
        {
            for (int y = 0; y < 21; y++) 
            {
                availablePositions.add(x + "," + y);
            }
        }
        Collections.shuffle(availablePositions);
    }

    public XY generateUniquePosition() 
    {
        for (String position : availablePositions) 
        {
            if (!occupiedPositions.contains(position)) 
            {
                occupiedPositions.add(position);
                String[] parts = position.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                return new XY(x, y);
            }
        }
        return null; // Если все позиции заняты
    }
}