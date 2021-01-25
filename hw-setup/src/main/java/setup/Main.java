package setup;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        BallContainer ballContainer = new BallContainer();
        ballContainer.add(new Ball(20));
        ballContainer.add(new Ball(20));

        Ball ball3 = new Ball(40);
        ballContainer.add(ball3);
        ballContainer.add(ball3);

        System.out.println("{");
        for(Ball ball : ballContainer)
        {
            System.out.println("\t" + ball.getVolume() + ", ");
        }
        System.out.println("}");

        Box box = new Box(1000);
        box.add(new Ball(20));
        box.add(new Ball(20));

        box.add(ball3);
        box.add(ball3);
        box.add(new Ball(2));
        box.add(new Ball(1));
        box.add(new Ball(329));
        box.add(new Ball(200));

        Iterator<Ball> iterator = box.iterator();
        System.out.println("{");
        while(iterator.hasNext())
        {
            System.out.println("\t" + iterator.next().getVolume() + ",");
        }
        System.out.println("}");

        Iterator<Ball> sortedIterator = box.getBallsFromSmallest();
        System.out.println("{");
        while(sortedIterator.hasNext())
        {
            System.out.println("\t" + sortedIterator.next().getVolume() + ",");
        }
        System.out.println("}");

    }
}
