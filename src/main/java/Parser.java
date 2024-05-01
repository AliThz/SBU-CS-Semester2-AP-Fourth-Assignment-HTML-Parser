import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {

    static List<Country> countries = new ArrayList<>();

    public Parser() {
    }

    public List<Country> sortByName(){
        List<Country> sortedByName = new ArrayList<>(countries);
        // Sort countries alphabetically (least)
        //TODO
        sortedByName.sort(Comparator.comparing(Country::getName));
        return  sortedByName;
    }

    public List<Country> sortByPopulation(){
        List<Country> sortedByPopulation = new ArrayList<>(countries);
        // Sort countries by population (most)
        //TODO
        sortedByPopulation.sort(Comparator.comparing(Country::getPopulation));
        Collections.reverse(sortedByPopulation);
        return sortedByPopulation;
    }

    public List<Country> sortByArea(){
        List<Country> sortedByArea = new ArrayList<>(countries);
        // Sort countries by area (most)
        //TODO
        sortedByArea.sort(Comparator.comparing(Country::getArea));
        Collections.reverse(sortedByArea);
        return sortedByArea;
    }

    public static void setUp() throws IOException {

        //Parse the HTML file using Jsoup
        File file = new File("src/Resources/country-list.html");
        Document document = Jsoup.parse(file, "UTF-8","https://countriesnow.space/api/v0.1/countries/flag/unicode");

        // Extract data from the HTML
        Elements countryDivs = document.select("div.country");

        // Iterate through each country div to extract country data
        for (var cd : countryDivs) {
            String name = cd.select(".country-name").first().ownText();
            String capital = cd.select(".country-info").first().select("span.country-capital").first().ownText();
            int population = Integer.parseInt(cd.select(".country-info").first().select("span.country-population").first().ownText());
            double area = Double.parseDouble(cd.select(".country-info").first().select("span.country-area").first().ownText());

            countries.add(new Country(name, capital, population, area));
        }
    }

    public static void main(String[] args) throws IOException {
        //you can test your code here before you run the unit tests ;)
        setUp();
        System.out.println(countries.get(107).toString());
    }
}
