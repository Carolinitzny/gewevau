import java.security.GeneralSecurityException;
import java.util.ArrayList;


public class Knoten {

	
	

	private boolean closed;
	private boolean blocked;
	private boolean start;
	private boolean end;
	
	private int x, y;
	
	private ArrayList<Knoten> nachbarn;
	
	private Knoten vorgaenger;
	
	public Knoten(boolean blocked, boolean start, boolean end, int x, int y) {
		this.blocked = blocked;
		this.start = start;
		this.end = end;
		this.x = x;
		this.y = y;
		nachbarn = new ArrayList<>();
	}
	
	public void addNachbar(Knoten k){
		nachbarn.add(k);
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
	
	public void setClosed(){
		closed = true;
	}
	
	public void setOpen(){
		closed = false;
	}
	public void setVorgaenger(Knoten k){
		vorgaenger=k;
	}
	
	public Knoten getVorgaenger(){
		return vorgaenger;
	}
	
	public int printPath(){
		if(vorgaenger != null){
			int tiefe = vorgaenger.printPath();
			System.out.println("Länge: "+ tiefe+" x: "+x + "   y: "+y);
			return tiefe +1;
		}
		System.out.println("Länge: "+ 0+"|   x: "+x + " y: "+y);
		return 0;
		
	}
}
