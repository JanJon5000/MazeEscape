import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Iterator;
public class labirynth {
    Random r;
    String[][] arrayLab;
    public static boolean containsShortArray(List<short[]> list, short[] target) {
            for (short[] arr : list) {
                if (Arrays.equals(arr, target)) return true;
            }
            return false;
        }
    public static boolean removeShortArray(List<short[]> list, short[] target) {
            Iterator<short[]> iterator = list.iterator();
            while (iterator.hasNext()) {
                short[] current = iterator.next();
                if (Arrays.equals(current, target)) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }
    public static short[][] neighbourHood(short[] indexes){
        short[][] ans = new short[][]{new short[]{(short)(indexes[0]+1), (short)(indexes[1])},
                                      new short[]{(short)(indexes[0]-1), (short)(indexes[1])},
                                      new short[]{(short)(indexes[0]), (short)(indexes[1]+1)},
                                      new short[]{(short)(indexes[0]), (short)(indexes[1]-1)}};
        // System.out.println(ans[0][0] + " " + ans[0][1]);
        // System.out.println(ans[1][0] + " " + ans[1][1]);
        // System.out.println(ans[2][0] + " " + ans[2][1]);
        // System.out.println(ans[3][0] + " " + ans[3][1]);
        return ans;
    }
    public labirynth(short width, short height){
        //setup of variables and "empty" maze consisting only of walls
        this.r = new Random();
        this.arrayLab = new String[width][height];
        ArrayList<short[]> notInMaze = new ArrayList<>();
        ArrayList<short[]> WALL = new ArrayList<>();
        ArrayList<short[]> PATH = new ArrayList<>();
        for(short i=0;i<width;i++){
            for(short j=0;j<height;j++){
                this.arrayLab[i][j] = "WALL";
                notInMaze.add(new short[] {i, j});
            }
        }
        //first random square as first element of maze
        short wStart = (short) this.r.nextInt(width);
        short hStart = (short) this.r.nextInt(height);
        this.arrayLab[wStart][hStart] = "PATH";
        removeShortArray(notInMaze, new short[]{wStart, hStart});
        //System.out.println(wStart + " " + hStart);
        PATH.add(new short[]{wStart, hStart});
        short[][] indexesToMove = neighbourHood(new short[]{wStart, hStart});
        for(byte i=0;i<indexesToMove.length;i++){
            if(indexesToMove[i][0]>=0 && indexesToMove[i][1]>=0 && indexesToMove[i][1]<height && indexesToMove[i][0]<width){
                WALL.add(indexesToMove[i]);
                removeShortArray(notInMaze, indexesToMove[i]);
            }
        }
        //rest of maze
        while(notInMaze.size() > 1){
            System.out.println(notInMaze.size());
            short randomWALL = (short) r.nextInt(WALL.size());
            short[][] n = neighbourHood(WALL.get(randomWALL));
            byte counter = 0;
            for(byte h=0;h<n.length;h++){ 
                if(n[h][0]>=0 && n[h][1]>=0 && n[h][1]<height && n[h][0]<width)
                    if(arrayLab[n[h][0]][n[h][1]] == "PATH"){
                        counter++;
                    }
            }
            if(counter == 1){
                for(byte i=0;i<n.length;i++){
                    if(containsShortArray(notInMaze, n[i])){
                        removeShortArray(notInMaze, n[i]);
                        WALL.add(n[i]);
                    }
                }
                arrayLab[WALL.get(randomWALL)[0]][WALL.get(randomWALL)[1]] = "PATH";
                PATH.add(WALL.get(randomWALL));
                removeShortArray(WALL, WALL.get(randomWALL));
            }
        }
        System.out.println("done");
    }
}
