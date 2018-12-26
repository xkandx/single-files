
import java.util.Arrays;
import java.util.Scanner;

public class Assign5EpicFightRecursive
{

    static final int MOD = 10007;
    static int[][] xm;  // X's moves  [0][i] spent of ith move
    static int[][] rm;  // R's moves   [1][i] reduction of oppenet at ith move
    static long[][][] memo; // time xs rs
    static int time; // the time the fight ends
    static final int VOID = -1;
    static int maxxs; // X's max stamina
    static int maxrs; // R's max stamina

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        time = in.nextInt();

        int numXM = in.nextInt();
        maxxs = in.nextInt();
        xm = new int[2][numXM];
        for (int i = 0; i < xm[0].length; i++)
        {
            xm[0][i] = in.nextInt();
            xm[1][i] = in.nextInt();
        }

        int numRM = in.nextInt();
        maxrs = in.nextInt();
        rm = new int[2][numRM];

        for (int i = 0; i < rm[0].length; i++)
        {
            rm[0][i] = in.nextInt();
            rm[1][i] = in.nextInt();
        }

        memo = new long[time + 1][maxxs + 1][maxrs + 1];
        for (long[][] arr : memo)
        {
            for (long[] a : arr)
            {
                Arrays.fill(a, VOID);
            }
        }

        // need to fill the memo for the first move
        // can always finishe
        System.out.println(solve(maxxs, maxrs, -1));

    }

    static long solve(int xs, int rs, int t)
    {
//        System.out.println("X stamina: " + xs + "\tR stamina: " + rs + "\ttime: " + t);
        // the fight finishes
        if (t == time)
        {
            if (xs > 0 && rs <= 0)
            {
//                System.out.println("yay");
                return 1;
            }
            return 0;
        }

        if (t > time) // may never happened
        {
//            System.err.println("err in time: " + t);
            return 0;
        }
        
        // if stamina of either went below or reached 0, invalid
        if (xs <= 0 || rs <= 0)
        {
            return 0;
        }

        if (t >= 0 && memo[t][xs][rs] != VOID)
        {
            return memo[t][xs][rs];
        }

        long ans = 0;
        for (int x = 0; x < xm[0].length; x++)
        {
            for (int r = 0; r < rm[0].length; r++)
            {
                // cost part
                int cxs = Math.min(xs - xm[0][x], maxxs);
                int crs = Math.min(rs - rm[0][r], maxrs);

                // if ends at even time, it is a half of the move
                if (time == t + 1)
                {
//                    System.out.println("yey even time rs: ");
                    if(crs <= 0 && cxs > 0)
                    {
                        ans += 1;
//                        System.out.println("yay even time");
                        ans %= MOD;
                    }
                }
                
                // reduction
                else if (cxs > 0 && crs > 0)
                {
                    int rxs = Math.min(cxs - rm[1][r], maxxs);
                    int rrs = Math.min(crs - xm[1][x], maxrs);
//                    System.out.println("rec");
                    ans += solve(rxs, rrs, t + 2);
                    ans %= MOD;
                }
            }
        }

        if (t == -1)
        {
            return ans;
        }
        return memo[t][xs][rs] = ans;
    }
}


