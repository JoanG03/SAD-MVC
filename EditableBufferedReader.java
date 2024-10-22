package P0_MVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.*;

@SuppressWarnings("unused")
public class EditableBufferedReader extends BufferedReader {
    public final Line line;
    private Console console;

    @SuppressWarnings("deprecation")
    public EditableBufferedReader(InputStreamReader text) {
        super(text);
        line = new Line();
        console = new Console(line);
        line.addObserver(console);
    }

    public void setRaw() throws IOException {
        try {
            ProcessBuilder process = new ProcessBuilder("sh", "-c", "stty -echo raw < /dev/tty");
            process.inheritIO().start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void unsetRaw() throws IOException {
        try {
            ProcessBuilder process = new ProcessBuilder("sh", "-c", "stty echo cooked < /dev/tty");
            process.inheritIO().start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int read() throws IOException {
        int ch, ch1;
        if ((ch = super.read()) != Keys.ESC) {
            return ch;
        }

        switch (ch = super.read()) {
            case '[':
                switch (ch = super.read()) {
                    case 'C': return Keys.RIGHT;
                    case 'D': return Keys.LEFT;
                    case 'H': return Keys.HOME;
                    case 'F': return Keys.END;
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                        if((ch1 = super.read()) != '~')
                            return ch1;
                        return Keys.HOME + ch - '1';
                    default:
                        return ch;
                }
        }
        return ch;
    }


    public String readline() throws IOException {
    setRaw();
    int ch = this.read();
    while (ch != Keys.ENTER) {
        switch (ch) {
            case Keys.RIGHT:
                line.moveRight();
                break;

            case Keys.LEFT:
                line.moveLeft();
                break;

            case Keys.HOME:
                line.home();
                break;

            case Keys.END:
                line.fin();
                break;

            case Keys.INS:
                line.modeInsert();
                break;

            case Keys.DEL:
                line.supr();
                break;

            case Keys.BACK:
                line.backspace();
                break;

            default:
                line.write(ch);
            }
            ch = this.read();
        }
        unsetRaw();
        return line.getLine();
    }

}