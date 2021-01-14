package setup;

import java.util.Random;

/** RanodmHello selects and prints a random greeting. */
public class RandomHello {

    /**
     * Prints a random greeting to the console.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /** @return a greeting, randomly chosen from five possibilities. */
    public String getGreeting()
    {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(5);
        switch(randomInt)
        {
            case 0:
                return "Hello World";
            case 1:
                return "Hello Mundo";
            case 2:
                return "Bonjour Monde";
            case 3:
                return "Hallo Welt";
            case 4:
                return "Ciao Mondo";
            default:
                return "Something went wront";
        }
    }
}
