package marvel;

import com.opencsv.bean.CsvBindByName;

/**
 * A HeroModel represents a JavaBean for a TSV/CSV file that contains the columns "hero" and "book."
 */
public class HeroModel {

    @CsvBindByName
    private String hero;

    @CsvBindByName
    private String book;

    /**
     * Gets hero's name.
     * @return hero name.
     */
    public String getHero() { return this.hero; }

    /**
     * Sets hero's name.
     *
     * @param hero the new name we want to give to this hero.
     */
    public void setHero(String hero)
    {
        this.hero = hero;
    }

    /**
     * Gets the book this hero shows up in.
     *
     * @return the name of the book this hero shows up in.
     */
    public String getBook() {
        return this.book;
    }

    /**
     * Changes the name of the book the hero shows up in.
     *
     * @param book the new name of the book this hero shows up in.
     */
    public void setBook(String book) {
        this.book = book;
    }
}
