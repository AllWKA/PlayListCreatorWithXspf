package Models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AllWLKa
 */
public class Show {
    private File show;
    private String name, xmlPath;
    private List <File> episodes;

    public Show(File show) {
        
        this.show = show;
        
        this.name = show.getName();
        
        this.xmlPath = show.getAbsolutePath()+"/"+name+"List.xml";
        
        this.episodes = new ArrayList<File>();
        
        fillEpisodes(this.show);
    }
    
    private void fillEpisodes(File a) {

        for (int i = 0; i < a.listFiles().length; i++) {

            if (a.listFiles()[i].isDirectory()) {

                fillEpisodes(a.listFiles()[i]);

            } else if (!a.listFiles()[i].toString().contains(".xml")) {

                episodes.add(a.listFiles()[i]);

            }

        }

    }

    public File getShow() {
        return show;
    }

    public void setShow(File show) {
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public List<File> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<File> episodes) {
        this.episodes = episodes;
    }
    
    
    
}
