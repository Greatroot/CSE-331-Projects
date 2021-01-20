package setup;

import java.util.Comparator;

public class BallComp implements Comparator<Ball> {

    /**
     *
     * @param b1
     * @param b2
     * @return Difference between two volumes
     */
    @Override
    public int compare(Ball b1, Ball b2) {
        if(b1.getVolume() - b2.getVolume() > 0) {
            return 1;
        } else if(b1.getVolume() - b2.getVolume() == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
