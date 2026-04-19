import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
public class labirynth {    
    private byte map[][];
    private int width;
    private int height;
    private LinkedList<byte[]> paths = new LinkedList<byte[]>();

    public labirynth(final int height, final int width){
        this.width = width;
        this.height = height;
        this.map = new byte[width][height];

        final LinkedList<int[]> frontiers = new LinkedList<>();
        final Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        frontiers.add(new int[]{x,y,x,y});

        while ( !frontiers.isEmpty() ){
            final int[] f = frontiers.remove( random.nextInt( frontiers.size() ) );
            x = f[2];
            y = f[3]; 
            if ( map[x][y] == constClass.WALL )
            {   
                map[f[0]][f[1]] = map[x][y] = constClass.PATH;
                this.paths.add(new byte[]{(byte)f[0], (byte)f[1]});
                this.paths.add(new byte[]{(byte)x, (byte)y});
                if ( x >= 2 && map[x-2][y] == constClass.WALL)
                    frontiers.add( new int[]{x-1,y,x-2,y} );
                if ( y >= 2 && map[x][y-2] == constClass.WALL )
                    frontiers.add( new int[]{x,y-1,x,y-2} );
                if ( x < width-2 && map[x+2][y] == constClass.WALL )
                    frontiers.add( new int[]{x+1,y,x+2,y} );
                if ( y < height-2 && map[x][y+2] == constClass.WALL )
                    frontiers.add( new int[]{x,y+1,x,y+2} );
            }
        }
        //code fixing two walls missing
        byte[][] placeholder = this.map;
        this.map = new byte[width+2][height+2];
        
        for(int i=0;i<width+2;i++){
            for(int j=0;j<height+2;j++){
                if(i == 0 || j == 0 || i == width + 1 || j == height + 1){
                    this.map[i][j] = constClass.WALL; 
                }else{
                    this.map[i][j] = placeholder[i-1][j-1]; 
                }
            }
        }
        this.width = width + 1;
        this.height = height + 1;
        //System.out.println(Arrays.deepToString(this.map));
    }
    public byte[][] arrayAccess(){
        return this.map;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }

    public LinkedList<byte[]> getPaths(){
        return this.paths;
    }

}
