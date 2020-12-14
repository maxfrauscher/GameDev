package com.max.game.tiles;

import com.max.game.graphics.Sprite;
import com.max.game.util.Camera;
import com.max.game.util.Syso;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
public class TileManager {

    public static ArrayList<TileMap> tm;
    public Camera cam;

    public static int tileWidth;
    public static int tileHeight;

    public TileManager() {
        tm = new ArrayList<TileMap>();

    }

    public TileManager(String path, Camera cam) {
        tm = new ArrayList<TileMap>();
        addTileMap(path, 64, 64, cam);
    }

    public TileManager(String path,int blockWidth, int blockHeight, Camera cam) {
        tm = new ArrayList<TileMap>();
        addTileMap(path, 64, 64, cam);
    }

    private void addTileMap(String path, int blockWidth, int blockHeight, Camera cam) {
        String imagePath;
        this.cam = cam;

        int width = 0;
        int height= 0;
        int tileCount;
        int tileColumns;
        int layers = 0;
        Sprite sprite;

        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
            tileColumns = Integer.parseInt(eElement.getAttribute("columns"));
            sprite = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i >= 1) {
                    tm.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                } else {
                    tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }

                cam.setLimit(width * blockWidth, height * blockHeight);

            }

        } catch (Exception e) {
            Syso.write("ERROR - Tilemanager: Cannot read tilemap: " + e);
        }
    }

    public void render(Graphics2D g) {
        if(cam == null) return;

        for (int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g, cam.getBounds());
        }
    }

}
