/**
 * The program prompts the user to enter a starting URL (lines 7 and 8) and
 * invokes the crawler(url method to traverse the Web (line 9).  The crawler(url)
 * method adds the starting url to listOfPendingURLs(line 16) and repeatedly
 * processes each URL in listOfPendingURLs in a while loop (lines 17-29).
 * It removes the first URL in the list (line 19) and processes the URL if it
 * has not been processed (lines 20-28).  To process each URL, the program first
 * adds the URL to listOfTracersedURLs (line 21).  This list stores all the URLs
 * that have been processed.  The getSubURLs(url) method returns a list of URLs
 * in the webpage from the specified URL (line 24).  The program uses a foreach
 * loop to add each URL in the page into listOfPendingURLs if it is not in
 * listOfTraversedURLs (lines 24-27).
 *
 * The getSubURLs(url) method reads each line from the webpage and
 * searches for the URLs in the line.
 *
 * 
 * / ** Russian....
 * 
 * Программа предлагает пользователю ввести начальный URL (строки 7 и 8) и
 * вызывает сканер (метод url для обхода Интернета (строка 9). сканер (url)
 * метод добавляет начальный URL в listOfPendingURLs (строка 16) и многократно
 * обрабатывает каждый URL в listOfPendingURLs в цикле while (строки 17-29).
 * Он удаляет первый URL в списке (строка 19) и обрабатывает URL, если он
 * не был обработан (строки 20-28). Чтобы обработать каждый URL, программа сначала
 * добавляет URL в listOfTracersedURLs (строка 21). В этом списке хранятся все URL
 * которые были обработаны. Метод getSubURLs (url) возвращает список URL
 * на веб-странице с указанного URL (строка 24). Программа использует foreach
 * цикл, чтобы добавить каждый URL на странице в listOfPendingURLs, если он не находится в
 * listOfTraversedURLs (строки 24-27).
 *
 * Метод getSubURLs (url) читает каждую строку с веб-страницы и
 * ищет URL-адреса в строке.
 *
 * Programma predlagayet pol'zovatelyu vvesti nachal'nyy URL (stroki 7 i 8) i
 * vyzyvayet skaner (metod url dlya obkhoda Interneta (stroka 9). skaner (url)
 * metod dobavlyayet nachal'nyy URL v listOfPendingURLs (stroka 16) i mnogokratno
 * obrabatyvayet kazhdyy URL v listOfPendingURLs v tsikle while (stroki 17-29).
 * On udalyayet pervyy URL v spiske (stroka 19) i obrabatyvayet URL, yesli on
 * ne byl obrabotan (stroki 20-28). Chtoby obrabotat' kazhdyy URL, programma snachala
 * dobavlyayet URL v listOfTracersedURLs (stroka 21). V etom spiske khranyatsya vse URL
 * kotoryye byli obrabotany. Metod getSubURLs (url) vozvrashchayet spisok URL
 * na veb-stranitse s ukazannogo URL (stroka 24). Programma ispol'zuyet foreach
 * tsikl, chtoby dobavit' kazhdyy URL na stranitse v listOfPendingURLs, yesli on ne nakhoditsya v
 * listOfTraversedURLs (stroki 24-27).
 *
 * Metod getSubURLs (url) chitayet kazhduyu stroku s veb-stranitsy i
 * ishchet URL-adresa v stroke.
 *
 * 
 */
package webcrawler;

/**
 *
 * @author Sean O'Brien
 * 5/19/2019
 * 
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class WebCrawler {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a URL, i.e., https://www... : ");
        String url = input.nextLine();
        crawler(url); //Traverse the Web from the starting url
    }

    public static void crawler(String startingURL) {
        ArrayList<String> listOfPendingURLs = new ArrayList<>();
        ArrayList<String> listOfTraversedURLs = new ArrayList<>();
        listOfPendingURLs.add(startingURL);
        while (!listOfPendingURLs.isEmpty()
                && listOfTraversedURLs.size() <= 1000) { // up to 1000 urls
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfTraversedURLs.contains(urlString)) {
                listOfTraversedURLs.add(urlString);
                System.out.println("Crawl " + urlString);
                getSubURLs(urlString).stream().filter((s)
                        -> (!listOfTraversedURLs.contains(s))).forEachOrdered((s)
                        -> {
                    listOfPendingURLs.add(s);
                });
            }
        }
    }

    public static ArrayList<String> getSubURLs(String urlString) {
        ArrayList<String> list = new ArrayList<>();

        try {
            java.net.URL url = new java.net.URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()) {
                String line = input.nextLine();
                current = line.indexOf("http:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) { // Ensure that a correct URL is found
                        list.add(line.substring(current, endIndex));
                        current = line.indexOf("http:", endIndex);
                    } else {
                        current = -1;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error:  " + ex.getMessage());
        }
        return list;
    }
}
