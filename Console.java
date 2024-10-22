package P0_MVC;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Console implements Observer {
    
    public static final String Opcode = null;
    private Line line;
    
    public Console(Line linea) {
        line = linea;
    }

    @Override
    public void update(Observable obs, Object obj) {
        int x = (int) obj;
        switch(x) {
            case Keys.LEFT:
                System.out.print("\033[1D");
                break;
                
            case Keys.RIGHT:
                System.out.print("\033[1C");
                break;
                
            case Keys.HOME:
                System.out.print("\033[1G");
                break;
                
            case Keys.END:
                System.out.print("\033[" + (line.getPos() + 1) + "G");
                break;
                
            case Keys.DEL:
                System.out.print("\033[P");
                break;
                
            case Keys.BACK:
                System.out.print("\033[1D\033[P");
                break;
            case Keys.CHAR:
            System.out.print(line.getLine().charAt(line.getLength() - 1));
        }
    }
    
}