import java.security.GeneralSecurityException;
import java.util.ArrayList;


public class Knoten {

	
	
	private boolean open;
	private boolean closed;
	private boolean blocked;
	private boolean start;
	private boolean end;
	
	private ArrayList<Knoten> nachbarn;
	
	public Knoten(boolean blocked, boolean start, boolean end) {
		this.blocked = blocked;
		this.start = start;
		this.end = end;
		nachbarn = new ArrayList<>();
	}
	
	public void addNachbar(Knoten k){
		nachbarn.add(k);
	}
	
	public boolean isOpen() {
		return open;
	}
	
	
	public boolean isClosed(){
		return closed;
	}
	
	public boolean isBlocked(){
		return blocked;
	}
	
	public boolean isStart(){
		return start;
	}
	
	public boolean isEnd(){
		return end;
	}
	
	public ArrayList<Knoten> getNachbarn() {
		return nachbarn;
	}
	
}
