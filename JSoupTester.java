/**
 *
 *  This is a web scraper for the weather in my hometown Lawrenceville, GA. It uses
 *  JSoup to scrape the weather from www.weather.com.
 *
 *  @author Brian Smithers
 *  @version 1.1
 *  @since October 19, 2020
 *
 *
 */

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupTester {

    public static void main(String[] args) throws IOException {

        // Formats current date and time.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm a");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);

        // Website to scrape data from. **Lawrenceville, GA www.Weather.com**
        String url =
                "https://weather.com/weather/today/l/a24d2c05e9bf890af18b4d888cce64f6c1f3ed412e7c3075beb9bbf7003bf44b";

        // Opens connection with site.
        Document doc = Jsoup.connect(url).get();

        // Elements 'text' data to be scraped from website by 'class.'
        // Location, temperature, weather conditions, feels like, sunrise, sunset, and wind speed.
        String location = doc.getElementsByClass("CurrentConditions--location--1Ayv3").get(0).text();
        String temperature = doc.getElementsByClass("CurrentConditions--tempValue--3KcTQ").get(0).text();
        String conditions = doc.getElementsByClass("CurrentConditions--phraseValue--2xXsr").get(0).text();
        String feelsLike = doc.getElementsByClass("TodayDetailsCard--feelsLikeTempValue--2aogo").get(0).text();
        String sunrise = doc.getElementsByClass("SunriseSunset--datevalue--2nwgx").get(0).text();
        String sunset = doc.getElementsByClass("SunriseSunset--datevalue--2nwgx").get(1).text();
        String wind = doc.getElementsByClass("Wind--windWrapper--1VA1P undefined").get(0).text();
        
        // These StringBuilders are used to manipulate the time, location and sunriseSunset strings.
        StringBuilder stringBuilderDateAndTime = new StringBuilder(dateAndTime);
        StringBuilder stringBuilderLocation = new StringBuilder(location);
        //StringBuilder stringBuilderSunriseSunset = new StringBuilder(sunriseSunset);

        // Removes 'AM' or 'PM' from string.
        String dateAndTimeFormattedNoDayOrNight = stringBuilderDateAndTime.substring(0,22);

        // Captures 'AM' or 'PM' for modification in 'if else.'
        String dateAndTimeFormattedDayOrNight = stringBuilderDateAndTime.substring(22,24);

        // Changes capital 'AM' or capital 'PM' to lowercase.
        if (dateAndTimeFormattedDayOrNight.equals("AM")) {
            dateAndTimeFormattedDayOrNight = "am";
        }
        else {
            dateAndTimeFormattedDayOrNight = "pm";
        }

        // Removes 'Weather' from string.
        String locationFormatted = stringBuilderLocation.substring(0,18);

        // Prints, date and time, location, weather conditions, feels like, sunrise, sunset and wind speed.
        System.out.printf("%s%s", dateAndTimeFormattedNoDayOrNight, dateAndTimeFormattedDayOrNight);
        System.out.printf("%n%s%n%n", locationFormatted);
        System.out.printf("Weather - %s %sF%n", conditions, temperature);
        System.out.printf("Feels Like - %sF%n", feelsLike);
        System.out.printf("Sunrise - %s%n", sunrise);
        System.out.printf("Sunset - %s%n", sunset);
        System.out.printf("Wind - %s", wind);
    }
}
