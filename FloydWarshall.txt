
import java.util.Scanner;

public class FloydWarshall
{
    // static final int oo = 1234567890;
    
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int numLoc = in.nextInt();
        int[][] dist = new int[numLoc][numLoc];
        for (int i = 0; i < numLoc; i++)
        {
            for (int j = 0; j < numLoc; j++)
            {
                dist[i][j] = in.nextInt();
            }
        }
        // if not given the entire graph as above
        // for(int i = 0; i < numLoc; i++)
        // {
        //      for (int j = 0; j < numLoc; j++)
        //      {
        //          if(i == j) dist[i][j] = 0;
        //          else dist[i][j] = oo;
        //      }
        //  }
        // then add the distance 
        
        findDistance(dist);
    }

    static void findDistance(int[][] dist)
    {
        for (int pivot = 0; pivot < dist.length; pivot++)
        {
            for (int source = 0; source < dist.length; source++)
            {
                for (int dest = 0; dest < dist.length; dest++)
                {
                    dist[source][dest] = Math.min(dist[source][dest],
                            dist[source][pivot] + dist[pivot][dest]);
                }
            }
        }
    }

    static void print(long[][] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 0; j < arr[i].length; j++)
            {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
