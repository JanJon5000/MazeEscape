import java.util.LinkedList;
import java.util.Random;
public class labirynth {
    private final byte map[][];
    private final short width;
    private final short height;
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
                if ( x >= 2 && map[x-2][y] == constClass.WALL )
                    frontiers.add( new int[]{x-1,y,x-2,y} );
                if ( y >= 2 && map[x][y-2] == constClass.WALL )
                    frontiers.add( new int[]{x,y-1,x,y-2} );
                if ( x < width-2 && map[x+2][y] == constClass.WALL )
                    frontiers.add( new int[]{x+1,y,x+2,y} );
                if ( y < height-2 && map[x][y+2] == constClass.WALL )
                    frontiers.add( new int[]{x,y+1,x,y+2} );
            }
        }
    }
    public byte[][] arrayAccess(){
        return this.map;
    }
}
