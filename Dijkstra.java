
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra
{

    static final int oo = 1000000;

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int numCases = in.nextInt();

        for (int i = 0; i < numCases; i++)
        {
            int numIntersections = in.nextInt();
            int numRoads = in.nextInt();
            int numHousing = in.nextInt();
            ArrayList<State>[] adj = new ArrayList[numIntersections];
            for (int j = 0; j < adj.length; j++)
            {
                adj[j] = new ArrayList<>();
            }

            for (int j = 0; j < numRoads; j++)
            {
                int p1 = in.nextInt() - 1;
                int p2 = in.nextInt() - 1;
                int time = in.nextInt();
                adj[p1].add(new State(p2, time));
                adj[p2].add(new State(p1, time));
            }

            int[] housing = new int[numHousing];
            for (int j = 0; j < numHousing; j++)
            {
                housing[j] = in.nextInt() - 1;
            }
            solve(adj, housing);

        }
    }

    static void solve(ArrayList<State>[] adj, int[] housing)
    {
        long[] dist = findDistance(adj);
        for (int i = 0; i < housing.length; i++)
        {
            System.out.println(dist[housing[i]]);
        }
    }

    static long[] findDistance(ArrayList<State>[] adj)
    {
        PriorityQueue<State> pq = new PriorityQueue<>();
        long[] dist = new long[adj.length];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        pq.offer(new State(0, 0));
        while (!pq.isEmpty())
        {
            State at = pq.poll();

            if (dist[at.node] < at.weight)
            {
                continue;
            }

            for (State lol : adj[at.node])
            {

                if (lol.weight + at.weight < dist[lol.node])
                {
                    dist[lol.node] = lol.weight + at.weight;
                    pq.add(new State(lol.node, dist[lol.node]));
                }
            }
        }
        return dist;
    }

    static class State implements Comparable<State>
    {
        int node;
        long weight;

        State(int node, long weight)
        {
            this.node = node;
            this.weight = weight;
        }

        public String toString()
        {
            return "\n\t" + (node + 1) + " weight: " + weight;
        }

        @Override
        public int compareTo(State t)
        {
            return (int) (this.weight - t.weight);
        }
    }
}
