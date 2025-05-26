import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
public class labirynth {
    private byte map[][];
    private short width;
    private short height;
    public labirynth(final short height, final short width){
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
        this.map = new byte[width+1][height+1];
        boolean isTop = false;
        boolean isLeft = false;
        Set<Byte> arePaths = new TreeSet<>();
        for(short i=0;i<height;i++){
            arePaths.add((byte)placeholder[0][i]);
        }
        if(arePaths.size() == 1) isTop = true;
        //clear the set
        arePaths.removeAll(arePaths);
        for(short i=0;i<width;i++){
            arePaths.add((byte)placeholder[i][0]);
        }
        if(arePaths.size() == 1) isLeft = true;
        //determining walls needed fixing - how will upcoming loop work
        short wallRow = 0;
        short wallCol = 0;
        short[] offset = {0, 0};
        if(isLeft == false && isTop == false){
            wallRow = 0;
            wallCol = 0;
            offset[0] = 1;
            offset[1] = 1;
        }else if(isLeft == true && isTop == false){
            wallRow = 0;
            wallCol = height;
            offset[0] = 1;
            offset[1] = 0;
        }else if(isLeft == false && isTop == true){
            wallRow = width;
            wallCol = 0;
            offset[0] = 0;
            offset[1] = 1;
        }else if(isLeft == true && isTop == true){
            wallRow = width;
            wallCol = height; 
            offset[0] = 0;
            offset[1] = 0;
        }
        for(short i=0;i<=width;i++){
            for(short j=0;j<=height;j++){
                if(i == wallRow || j == wallCol){
                    map[i][j] = constClass.WALL;
                }else{
                    map[i][j] = placeholder[i-offset[0]][j-offset[1]];
                }
            }
        }
    }
    public byte[][] arrayAccess(){
        return this.map;
    }
    public short getWidth(){
        return this.width;
    }
    public short getHeight(){
        return this.height;
    }
}
