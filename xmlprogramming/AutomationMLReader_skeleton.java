import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class AutomationMLReader_skeleton {
    public static void main(String[] args) {
        try {
            File file = new File("testing_station.aml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("InternalElement");
            System.out.println(nList.getLength());

            for (int i = 0; i < nList.getLength(); i++) {
                Node n = nList.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    String name = ((Element) n).getAttribute("Name");
                    System.out.println(name);
                }
            }
            
            System.out.println("\n----------\n");

            NodeList systemUnitList = doc.getElementsByTagName("SystemUnitClassLib").item(0).getChildNodes();
            for (int i = 0; i < systemUnitList.getLength(); i++) {
                Node n = systemUnitList.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    String name = ((Element) n).getAttribute("Name");
                    if (name.equals("")) continue;
                    System.out.println("Internal elements with system unit class SystemUnitClassLib/" + name + ":");

                    for (int j = 0; j < nList.getLength(); j++) {
                        Node nn = nList.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            String nname = ((Element) nn).getAttribute("Name");
                            String sysUnitPath = ((Element) nn).getAttribute("RefBaseSystemUnitPath");
                            if (sysUnitPath.equals("SystemUnitClassLib/"+name)) System.out.println(nname);
                        }
                    }
                    System.out.print("\n");
                }
            }

            ArrayList<Cell> cells = readCoords();
            cells.stream().forEach(System.out::println);


        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
    public static ArrayList<Cell> readCoords() {
        ArrayList<Cell> cellList = new ArrayList<Cell>();
        try {
            File file = new File("amlt5.aml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nl = doc.getElementsByTagName("InternalElement");

            for (int i = 0; i < nl.getLength(); i++){
                if (nl.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
                if (((Element) nl.item(i)).getAttribute("RefBaseSystemUnitPath").equals("SystemUnitClassLib/Cell")) {

                    Cell cell = new Cell();
                    NodeList cellparts = ((Element) nl.item(i)).getChildNodes();

                    for (int j = 0; j < cellparts.getLength(); j++) {
                        if (!(cellparts.item(j) instanceof Element)) continue;
                        
                        Element e = (Element) cellparts.item(j);
                        String refBase = e.getAttribute("RefBaseSystemUnitPath"); 

                        if (refBase.equals("SystemUnitClassLib/LegoBuffer")
                        || refBase.equals("SystemUnitClassLib/AssemblyStation")) {

                            NodeList values = e.getElementsByTagName("Value");
                            float[] coords = {0, 0, 0};
                            for (int k = 0; k < values.getLength(); k++) {
                                coords[k == 1 ? 2 : 0] = Float.valueOf(values.item(k).getTextContent());
                            }

                            if (refBase.equals("SystemUnitClassLib/LegoBuffer")) cell.setBufferCoords(coords);
                            else cell.setStationCoords(coords);
                        }
                    }
                    cellList.add(cell);
                } 
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return cellList;          
    }
}