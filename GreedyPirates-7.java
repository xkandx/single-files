
import java.util.PriorityQueue;
import java.util.Scanner;

public class GreedyPirates
{
    // the question is similar to the number of chair needed for all the 
    // friends that come and go at different time example in the class
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int xOne = in.nextInt();
        int yOne = in.nextInt();
        int xTwo = in.nextInt();
        int yTwo = in.nextInt(); // <-- do not really need the second y 
                                 //     since the gap is guranteed to be 
                                 //     paralled to x-axis
        
        // find left and right curtain to help to find the start and end 
        // point of the interval                         
        Point leftCur = new Point(Math.min(xOne, xTwo), yOne);
        Point rightCur = new Point(Math.max(xOne, xTwo), yOne);
        int numPirates = in.nextInt();
        
        // x-axis
        CramerLine xAxis = new CramerLine(2, 0, 4, 0);
        
        PriorityQueue<Event> pq = new PriorityQueue<>();

        // converting the pirates into intervals as reading in
        for (int i = 0; i < numPirates; i++)
        {
            // reading in the postion of pirates
            int x = in.nextInt();
            int y = in.nextInt();
            
            // line for the start of the interval with left curtain point
            CramerLine cll = new CramerLine(x, y, leftCur.x, leftCur.y);
            // line for the end of the interval with right curtain point
            CramerLine clr = new CramerLine(x, y, rightCur.x, rightCur.y);

            // find when the line ^ cross x-axis
            Point p1 = findIntersectionPoint(cll, xAxis);
            Point p2 = findIntersectionPoint(clr, xAxis);

            //System.out.println(new Interval(p1.x, p2.x));
            
            // add to priority to process later
            pq.offer(new Event(p1.x, 1));
            pq.offer(new Event(p2.x, -1));
        }

        // count for the max number of line the vertical line crossed
        int minNumShows = 0;
        // count for the current number of line the vertical line crossed
        int curNumShows = 0;
        while(!pq.isEmpty())
        {
            Event e = pq.poll();
            curNumShows += e.type;
            //System.out.println(e);
            if (minNumShows < curNumShows)
            {
                minNumShows = curNumShows;
            }
        }

        System.out.println(minNumShows);

    }

    /**
     *
     * @param l1 : contains a, b, c as in ax + by = c
     * @param l2 : contains a, b, c as in ax + by = c
     * @return point two points intersect
     *
     * Kramer's Rule (for line intersection) ax + by = c 
     * dx + ey = f 
     * x = (ce - bf) / (ae - bd) y = (af - cd) / (ae - bd)
     */
    static Point findIntersectionPoint(CramerLine l1, CramerLine l2)
    {
        double demoninator = l1.x * l2.y - l2.x * l1.y;
        double pX = (l1.c * l2.y - l1.y * l2.c) / demoninator;
        double pY = (l1.x * l2.c - l1.c * l2.x) / demoninator;
        Point p = new Point(pX, pY);
        return p;
    }
}

class Event implements Comparable<Event>
{

    double time;
    int type;

    Event(double time, int type)
    {
        this.time = time;
        this.type = type;
    }

    @Override
    public int compareTo(Event o)
    {
        if(Math.abs(this.time - o.time) < 1e-9)
        {
            if(this.type < o.type)
            {
                return -1;
            }
            return 1;
        }
        if(this.time > o.time)
            return 1;
        // exclusive at the end point

        return -1;
    }
    
    public String toString()
    {
        return "Event - time: " + time + "\ttype: " + type;
    }
}


class CramerLine
{

    double x;
    double y;
    double c;

    // ax + by = c
    // y - y1 = m * (x - x2)
    // y - y1 = (y1 - y2)/(x1 - x2) * (x - x2)
    // (x1 - x2) * (y - y1) = (y1 - y2) * (x - x1)
    // (x1 - x2) * y + (y2 - y1) * x = x1 * (y2 - y1) + y1 * (x1 - x2)
    // (x1 - x2) * y + (y2 - y1) * x = x1 * y1 - x1 * y1 + y1 * x1 - y1 * x2
    // (x1 - x2) * y + (y2-y1) * x = x1y2 - y1x2
    CramerLine(double x1, double y1, double x2, double y2)
    {
        this.x = y2 - y1;
        this.y = x1 - x2;
        this.c = x1 * y2 - y1 * x2;
    }

    @Override
    public String toString()
    {
        return "x: " + x + " y: " + y + " c: " + c;
    }

}

class Point
{

    double x;
    double y;

    Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "x: " + x + " y: " + y;
    }
}


//class Interval implements Comparable<Interval>
//{
//
//    double st;
//    double en;
//
//    Interval(double st, double en)
//    {
//        this.st = st;
//        this.en = en;
//    }
//
//    @Override
//    public int compareTo(Interval t)
//    {
//        return (int) (this.st - t.st);
//    }
//
//    @Override
//    public String toString()
//    {
//        return "interval: [" + st + ", " + en + "]";
//    }
//
//}
