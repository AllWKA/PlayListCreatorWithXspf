package Models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author AllWLKa
 */
public class Manager {

    private int totalEpi;
    private List<Integer> lastModified;
    private List<Show> shows;
    private File xspf, root;

    public Manager(File root) {

        try {
            this.root = root;
            this.totalEpi = 0;
            this.lastModified = new ArrayList<Integer>();
            this.shows = new ArrayList<Show>();

            this.xspf = new File(root.getAbsoluteFile() + "\\Lista.xspf");
            xspf.createNewFile();

            completeCreation();
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Manager() {

    }

    public Manager(File file, List<Integer> posEpisodes) {
        try {
            this.root = file;
            this.totalEpi = 0;
            this.lastModified = posEpisodes;
            this.shows = new ArrayList<Show>();

            this.xspf = new File(root.getAbsoluteFile() + "\\Lista.xspf");
            xspf.createNewFile();

            completeCreation();
            
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void completeCreation() {

        if (lastModified.isEmpty()) {
            for (int i = 0; i < root.listFiles().length; i++) {

                if (root.listFiles()[i].isDirectory()) {

                    shows.add(new Show(root.listFiles()[i]));

                    lastModified.add(0);

                    totalEpi += (new Show(root.listFiles()[i])).getEpisodes().size();

                }
            }
        } else {
            for (int i = 0; i < root.listFiles().length; i++) {

                if (root.listFiles()[i].isDirectory()) {

                    shows.add(new Show(root.listFiles()[i]));

                    totalEpi += (new Show(root.listFiles()[i])).getEpisodes().size();

                }
            }
        }

    }

    public void mixIt() {
        DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = bf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element playList = doc.createElement("playlist");
            playList.setAttribute("version", "1");
            doc.appendChild(playList);

            Element trackList = doc.createElement("trackList");
            playList.appendChild(trackList);

            int aux = 0;
            int a;
            

            for (int i = 0; i < totalEpi; i++) {
                
                a = (int) (shows.size() * Math.random());
                
                while (a == aux) {
                    a = (int) (shows.size() * Math.random());
                    System.out.println("nonono: "+a);
                }
                System.out.println("fin a:"+a);

                aux = a;

                if (lastModified.get(a) < shows.get(a).getEpisodes().size()) {

                    Element track = doc.createElement("track");
                    trackList.appendChild(track);
                    
                    Element location = doc.createElement("location");
                    track.appendChild(location);

                    location.setTextContent(this.shows.get(a).getEpisodes().get(lastModified.get(a)).getPath());

                    lastModified.set(a, lastModified.get(a) + 1);

                }

            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer ts = tf.newTransformer();
            DOMSource ds = new DOMSource(doc);

            StreamResult str = new StreamResult(xspf);

            ts.transform(ds, str);

            JOptionPane.showMessageDialog(null, "Terminado.");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void tryToOpen(File file) {
        file.canRead();
    }

    public File getRoot() {
        return root;
    }

    public List<Show> getShows() {
        return shows;
    }

    public List<File> getShow(int pos) {

        return shows.get(pos).getEpisodes();
    }

}
