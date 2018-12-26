
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MSTDisjointSet
{

    static final int MAX_NUM_WAGONS = 10;
    static int numCity;
    static int numRoad;
    static int budget;
    static int wagonCost;
    static int totalWeight;
    static int numNeededRoad;
    static int numBuiltRoad;
    static ArrayList<Road> roads;
    static ArrayList<Integer> validConnections;
    static int[] cities;

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        numCity = in.nextInt();
        numRoad = in.nextInt();

        numNeededRoad = numCity - 1;
        roads = new ArrayList<>();
        validConnections = new ArrayList<>();

        for (int i = 0; i < numRoad; i++)
        {
            int c1 = in.nextInt();
            int c2 = in.nextInt();
            int cost = in.nextInt();
            int capacity = in.nextInt();
            Road road = new Road(c1, c2, cost, capacity);
            roads.add(road);
        }
        budget = in.nextInt();
        wagonCost = in.nextInt();
        totalWeight = in.nextInt();

        Collections.sort(roads);
        //printInfo();

        solve();
        print(validConnections);
    }

    static void solve()
    {
        // try from 1 wagon to 10 wagons 
        for (int numWagon = 1; numWagon <= MAX_NUM_WAGONS; numWagon++)
        {
            cities = DisjointSet.makeSet(cities, numCity);
            numBuiltRoad = 0;
            int leftBudget = budget - numWagon * wagonCost;
            findPossibleConnections(numWagon, leftBudget);
        }
    }

    // find if the number of given wagon will satifies any road combination
    // go through posibly roads, add then if the cost, capacity allowed 
    // while creating no cycles
    static void findPossibleConnections(int numWagon, int budget)
    {
        for (int i = 0; i < roads.size(); i++)
        {
            Road road = roads.get(i);

            if (isValid(road, numWagon, budget))
            {
                budget -= road.getCost();
                DisjointSet.union(cities, road.getCity1() - 1, road.getCity2() - 1);
                numBuiltRoad++;
            }
            if (numBuiltRoad == numNeededRoad)
            {
                validConnections.add(numWagon);
                break;
            }
        }
    }

    // check if the road will able to handle the weight
    // check if adding the road will overbudget
    // check if adding the road will create cycle
    static boolean isValid(Road road, int numWagon, int budget)
    {
        int c1 = road.getCity1() - 1;
        int c2 = road.getCity2() - 1;

        // check if the road capacity is big enough
        if (totalWeight > road.getCapacity() * numWagon)
        {
            return false;
        }
        if (budget < road.getCost())
        {
            return false;
        }
        if (DisjointSet.isInSameSet(cities, c1, c2))
        {
            return false;
        }
        return true;
    }

    // print methods
    static void print(ArrayList<Integer> al)
    {
        int size = al.size();
        System.out.println(size);
        for (int i = 0; i < size; i++)
        {
            System.out.print(al.get(i) + " ");
        }
        System.out.println();
    }

    static void print(int[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void printInfo()
    {
        System.out.println("printInfo >>> Roads");
        for (int i = 0; i < roads.size(); i++)
        {
            System.out.println(roads.get(i) + " ");
        }

        System.out.println("Budget: " + budget);
        System.out.println("Shipment weight: " + totalWeight);
        System.out.println("Cost per wagon: " + wagonCost);
        System.out.println("number of city, road: " + numCity + ", " + numRoad);
        System.out.println();
    }

}

class DisjointSet
{

    // tracking the depth of the tree
    private static int depth;

    static int[] makeSet(int[] set, int size)
    {
        set = new int[size];
        for (int i = 0; i < size; i++)
        {
            set[i] = i;
        }
        return set;
    }

    // track the depth of tree for a more optimal runtime when union
    static int findRoot(int set[], int pos)
    {
        depth = 0;
        return findRootHelper(set, pos);
    }

    private static int findRootHelper(int[] set, int pos)
    {
        if (set[pos] == pos)
        {
            return pos;
        }
        depth++;
        return findRootHelper(set, set[pos]);
    }

    static boolean isInSameSet(int[] set, int pos1, int pos2)
    {
        return findRoot(set, pos1) == findRoot(set, pos2);
    }

    static boolean union(int[] set, int pos1, int pos2)
    {
        int c1 = findRoot(set, pos1);
        int c1Depth = depth;
        int c2 = findRoot(set, pos2);
        int c2Depth = depth;
        if (c1 == c2)
        {
            return false;
        }

        // join the "tree" with the smaller depth to the larger depth
        if (c1Depth < c2Depth)
        {
            set[c1] = c2;
        } else
        {
            set[c2] = c1;
        }

        return true;
    }
}
 
class Road implements Comparable<Road>
{

    private final int city1;
    private final int city2;
    private final int cost;
    private final int capacity;

    public Road(int city1, int city2, int cost, int capacity)
    {
        this.city1 = city1;
        this.city2 = city2;
        this.cost = cost;
        this.capacity = capacity;
    }

    @Override
    public int compareTo(Road o)
    {
        if (this.cost == o.cost)
        {
            return this.capacity - o.capacity;
        }
        return this.cost - o.cost;
    }

    @Override
    public String toString()
    {
        return "" + city1 + " - " + city2 + "\tcost " + cost + "\tcapacity " + capacity;
    }

    public int getCity1()
    {
        return city1;
    }

    public int getCity2()
    {
        return city2;
    }

    public int getCost()
    {
        return cost;
    }

    public int getCapacity()
    {
        return capacity;
    }
}
