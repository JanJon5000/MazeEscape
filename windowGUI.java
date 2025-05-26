import javax.swing.JFrame;

public class windowGUI extends JFrame{
    private short width;
    private short height;
    private String title;
    public windowGUI(String title, short w, short h){
        super(title);
        this.title = title;
        this.width = w;
        this.height = h;
        setSize(this.width, this.height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public short[] getDimentions(){
        short[] ans = {this.width, this.height};
        return ans;
    }
}
