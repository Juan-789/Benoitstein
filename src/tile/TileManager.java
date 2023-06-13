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
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
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

            while (col<gp.maxWorldCol && row < gp.maxWorldRow){   //workout the map data given in the .txt

                String line = br.readLine();

                while (col<gp.maxWorldCol){

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
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

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol< gp. maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow]; //extract a tile number which is stored in mapTIleNum [0][0]

            int worldx = worldCol*gp.tileSize;
            int worldy = worldRow*gp.tileSize;
            int screenx = worldx - gp.player.worldx+gp.player.screenx;
            int screeny = worldy - gp.player.worldy+gp.player.screeny;

            if (worldx+gp.tileSize>gp.player.worldx - gp.player.screenx &&
                    worldx-gp.tileSize<gp.player.worldx+gp.player.screenx &&
                    worldy +gp.tileSize> gp.player.worldy - gp.player.screeny &&
                    worldy -gp.tileSize< gp.player.worldy+gp.player.screeny) {

                g2.drawImage(tile[tileNum].image, screenx, screeny, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow ++;

            }
        }


    }
}
