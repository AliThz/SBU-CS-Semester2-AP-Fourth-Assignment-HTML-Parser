import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Country {

    private String name;
    private String capital;
    private int population;
    private double area;


    public Country(String name, String capital, int population, double area) {
        //TODO
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.area = area;
    }



    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }



    @Override
    public String toString() {
        //TODO
        return String.format("%s %s\n\uD83C\uDFD9 Capital: %s\n\uD83E\uDDD1\u200D\uD83E\uDD1D\u200D\uD83E\uDDD1 Population: %s\n▩ Area: %s", getUnicodeFlag(), name, capital, population, area);
//        return String.format("%s %s\n\uD83C\uDFD9 Capital: %s\n\uD83E\uDDD1\u200D\uD83E\uDD1D\u200D\uD83E\uDDD1 Population: %d\n▩ Area: %d", getEmojiByUnicode(Integer.parseInt(getUnicodeFlag())), name, capital, population, area);

    }

    @Override
    public boolean equals(Object o) {
        Country country = (Country) o;
        return population == country.population &&
                Double.compare(country.area, area) == 0 &&
                Objects.equals(name, country.name) &&
                Objects.equals(capital, country.capital);
    }

    private String getDataViaAPI() {
        try {
            URL url = new URL("https://countriesnow.space/api/v0.1/countries/flag/unicode");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getUnicodeFlag() {
        String response = getDataViaAPI();
        if (response != null) {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray countries = jsonObject.getJSONArray("data");
            for (int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);
                if (Objects.equals(country.getString("name"), name))
                    return country.getString("unicodeFlag");
            }
        }
        return null;
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

}
