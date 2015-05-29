package articleparser;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.HTMLDocument;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSReader {

    private static RSSReader instance = null;
    private URL rssURL;
    private String resource_id;

    private RSSReader() {
    }

    public static RSSReader getInstance() {
        if (instance == null) {
            instance = new RSSReader();
        }
        return instance;
    }

    public void setURL(URL url, String r_id) {
        rssURL = url;
        resource_id = r_id;
    }

    public void writeFeed() throws Exception {
        MySQLAccess dao = new MySQLAccess();
        String lastguid = dao.getLastNews(resource_id);
        boolean saved = false;
        String title, link, desc, text, category, language, imageLink, pubdate;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"); 
            rssURL.openConnection().setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            Document doc = builder.parse(rssURL.openStream());

            NodeList items = doc.getElementsByTagName("item");
            
            //System.out.println(items.getLength());

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                title = getValue(item, "title");
                title = title.replace("`", "");
                title = title.replace("'", "");
                desc = getValue(item, "description");
                desc = desc.replace("`", "");
                desc = desc.replace("'", "");
                link = getValue(item, "link");
                pubdate = getValue(item, "pubDate");
                DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
                if(rssURL.toString().contains("sports.kz")||rssURL.toString().contains("akorda.kz")||rssURL.toString().contains("today.kz")||rssURL.toString().contains("kapital.kz"))
                    formatter=new SimpleDateFormat("\nEEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
                Date date=formatter.parse(pubdate);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                pubdate=df.format(date);
                System.out.println(pubdate);
               imageLink = "no";
               if(item.getElementsByTagName("image").getLength()>0)
                    imageLink=getValue(item, "image");
                if(item.getElementsByTagName("enclosure").getLength()>0){
                    imageLink=item.getElementsByTagName("enclosure").item(0).getAttributes().getNamedItem("url").getNodeValue();
                            }
                
                
                if (link.equals(lastguid)) {
                    break;
                }
                if (!saved) {
                    dao.changeLastNews(resource_id, link);
                    saved = true;
                }
                text = ArticleExtractor.INSTANCE.getText(new URL(link));
                text = text.replace("`", "");
                text = text.replace("'", "");
                language = "2";
                
                System.out.println(link + "\n" + desc + "\n" + title + "\n" + text + "\n" + resource_id + "\n" + language + "\n" + imageLink);
                System.out.println("\n\n\n\n");
                dao.writeDataBase(link, desc, title, text, resource_id, language, imageLink, pubdate);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(Element parent, String nodeName) {
        return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
    }
    
}
