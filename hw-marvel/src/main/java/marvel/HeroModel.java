package marvel;

import com.opencsv.bean.CsvBindByName;

public class HeroModel {

    @CsvBindByName
    private String hero;

    @CsvBindByName
    private String book;

    public String getHero() {
        return this.hero;
    }

    public void setHero(String hero)
    {
        this.hero = hero;
    }

    public String getBook() {
        return this.book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
