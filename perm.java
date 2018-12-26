
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class perm
{

    static double seconds;
    static ArrayList<Integer>[] blocked;
    static double oo = Double.MAX_VALUE;

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int numCase = in.nextInt();
        DecimalFormat df = new DecimalFormat("#.000");
        for (int i = 0; i < numCase; i++)
        {
            int numRide = in.nextInt();
            int numBlocked = in.nextInt();
            Ride[] rides = new Ride[numRide];
            blocked = new ArrayList[numRide];
            seconds = oo;
            for (int j = 0; j < numRide; j++)
            {
                int x = in.nextInt();
                int y = in.nextInt();
                rides[j] = new Ride(x, y);
                blocked[j] = new ArrayList<>();
            }
            for (int j = 0; j < numBlocked; j++)
            {
                int r1 = in.nextInt() - 1;
                int r2 = in.nextInt() - 1;
                blocked[r1].add(r2);
                blocked[r2].add(r1);
            }
  //          System.out.println(Arrays.toString(blocked));
            
  //          System.out.println("init time: " + seconds);
            solve(rides, 0, new int[rides.length], new boolean[rides.length]);
            System.out.println("Vacation #" + (i + 1) + ":");
            if (seconds == oo)
            {
                System.out.println("Jimmy should plan this vacation a different day.\n");
            } else
            {
                seconds += 120 * numRide;
                System.out.println("Jimmy can finish all of the rides in " + df.format(seconds) + " seconds.\n");
            }
        }
    }

    static void solve(Ride[] rides, int k, int[] perm, boolean[] used)
    {
        if (k == rides.length)
        {
//            System.out.println(Arrays.toString(perm));
            seconds = Math.min(seconds, computeTime(rides, perm));
//            System.out.println("seconds: " + seconds);
        }
        for (int i = 0; i < rides.length; i++)
        {
            if (!used[i])
            {
                used[i] = true;
                perm[k] = i;
                if (isValid(perm, k))
                {
                    solve(rides, k + 1, perm, used);
                }
                used[i] = false;
            }
        }
    }

    static boolean isValid(int[] perm, int ind)
    {
        if (ind == 0)
        {
            return true;
        }
        for (int i = 0; i < blocked[perm[ind]].size(); i++)
        {
            if (perm[ind - 1] == blocked[perm[ind]].get(i))
            {
                //System.out.println("invalid, ind: " + ind + Arrays.toString(perm));
                return false;
            }
        }
        //System.out.println("valid, ind: " + ind +  Arrays.toString(perm));
        return true;
    }

    static double computeTime(Ride[] rides, int[] perm)
    {
        double res = 0;
        if(rides.length > 0)
        {
            double x = Math.pow(rides[perm[0]].x, 2);
            double y = Math.pow(rides[perm[0]].y, 2);
            res += Math.pow(x + y, 0.5);
        }
        for (int i = 1; i < perm.length; i++)
        {
            double x = Math.pow(rides[perm[i]].x - rides[perm[i - 1]].x, 2);
            double y = Math.pow(rides[perm[i]].y - rides[perm[i - 1]].y, 2);
            res += Math.pow(x + y, 0.5);
        }
        //System.out.println("time: " + res);
        return res;
    }

    public static class Ride
    {

        int x, y;

        public Ride(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public String toString()
        {
            return "(" + x + ", " + ")";
        }
    }

}
