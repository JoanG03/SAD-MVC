package P0_MVC;

import java.lang.StringBuilder;
import java.util.Observable;


@SuppressWarnings("deprecation")
public class Line  extends Observable{

	private int cursor;
	protected StringBuilder text;
	private boolean insercio;

	public Line() {
		cursor = 0;
		text = new StringBuilder();
		insercio = false;
	}

	public int getPos() {
		return this.cursor;
	}

	  public void write(int c) {
        if ((insercio && cursor >= text.length()) || !insercio) {
            text.insert(cursor, (char) c);
            cursor++;
        } else {
            text.setCharAt(cursor, (char) c);
            cursor++;
        }
        this.setChanged();
        this.notifyObservers(Keys.CHAR);
    }

	public int getLength(){
		return text.length();
	}

	public void setPosition(int position){
		cursor = position;
	}

	public void backspace() {
		if (cursor > 0) {
			cursor--;
			text.deleteCharAt(cursor);
		}
	}

	public void supr() {
		if (cursor >= 0 && cursor < text.length()) {
			if(insercio){
				text.deleteCharAt(cursor);
			}
			else{
				text.deleteCharAt(cursor + 1);
			}
		}
	}


	public void modeInsert() {
		insercio = !insercio;
	}

	public void moveRight() {
		if (cursor < text.length()) {
			//System.out.print("\033[C"); 
			cursor++;
		}
		this.setChanged();
		this.notifyObservers(Keys.RIGHT);
	}

	public void moveLeft() {
		if (cursor > 0) {
			//System.out.print("\033[D");
			cursor--;
			this.setChanged();
			this.notifyObservers(Keys.LEFT);
		}
	}

	@Override
	public String toString() {
    	return text.toString();
	}
	
	public void home() {
		cursor = 0;
		this.setChanged();
		this.notifyObservers(Keys.HOME);
		//System.out.print("\033[G");
	}

	public void fin() {
		//System.out.print("\033["+(text.length()+1)+"G");
		if (cursor != text.length()) {
            cursor = text.length();
        }
        this.setChanged();
        this.notifyObservers(Keys.END);
	}

	public String getLine(){
		return text.toString();
	}

}