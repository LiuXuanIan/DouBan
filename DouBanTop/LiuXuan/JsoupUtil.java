package DouBanTop.LiuXuan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Liu Xuan
 *
 */
public class JsoupUtil {

    public JsoupUtil() {
    
    }

    private static final JsoupUtil instance = new JsoupUtil();

    public static JsoupUtil getInstance() {
        return instance;
    }

    /**
     * We write the names of the movies into the file.
     * @param rank The rank of the movie.
     * @param name The name of the movie.
     */
    public void writeFile(int rank, String name) {
        File file = new File("DouBanTop100.txt");
        Writer writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(rank + "  " + name + "\r\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getDouBanTop() {
        try {
            System.out.println("DouBan movies top 100 shows below.");
            //There are only 5 pages.
            int rank = 1;
            for(int i = 0;i < 5; i++) {
                String url = "https://movie.douban.com/tag/top100?start=" + i * 20 + "&type=S";
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();
                Element article = document.select(".article").first();
                Elements movies = article.getElementsByClass("nbg");
                Iterator<Element> iter = movies.iterator();
                while (iter.hasNext()) {
                    Element currentMovie = iter.next();
                    String name = currentMovie.getElementsByTag("img").get(0).attr("alt");
                    //getElementsByAttributeValue("class", "nbg").get(1).text();
                    System.out.println(rank + "  " + name);
                    JsoupUtil.getInstance().writeFile(rank,name);
                    rank++;
                }
                //Elements names = article.select("[class=nbg]");
                //Iterator<Element> ulIter = names.iterator();
                //while(ulIter.hasNext()) {
                    //Element element = ulIter.next();
                    //Elements elementName = element.select("[class=nbg]");
                    //String name = element.text();
                    //Iterator<Element> liIter = eleLi.iterator();
                    //while (liIter.hasNext()) {
                      //  System.out.println("Been here");
                        //Element liElement = liIter.next();
                        //Elements eleSpan = liElement.select("span.pl*");
                        //Elements eleHref = eleSpan.select("a[href]");
                        //String name = eleHref.text().substring(eleHref.text().indexOf("《"));
                        //System.out.println(rank + "  " + name);
                        //JsoupUtil.getInstance().writeFile(rank,name);
                        //rank++;
                    //}
                //}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
