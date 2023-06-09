package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum [][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[2];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/maps/map01.txt");

    }

    public  void getTileImage(){

        try{
            File file = new File("res/tiles/grass.png");
            FileInputStream fis = new FileInputStream(file);
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(fis);

            file = new File("res/tiles/wall.png");
            fis = new FileInputStream(file);
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(fis);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filepath){
        try{
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col =0;
            int row = 0;

            while (col<gp.maxScreenCol && row < gp.maxScreenRow){   //workout the map data given in the .txt

                String line = br.readLine();

                while (col<gp.maxScreenCol){

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col< gp. maxScreenCol && row < gp.maxScreenRow){

            int tileNum = mapTileNum[col][row]; //extract a tile number which is stored in mapTIleNum [0][0]

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+= gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                x= 0;
                row ++;
                y += gp.tileSize;
            }
        }


    }
}
