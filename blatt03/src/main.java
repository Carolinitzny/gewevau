import java.util.LinkedList;
import java.util.Stack;



public class main {

	
	public static void main(String[] args) {
		Knoten[][] knoten = new ASCIIReader().readFile("blatt3_environment.txt");
		Graph g = new Graph(knoten);
		breitensuche(g);
		g.reset();
		tiefensuche(g);
	}
	
	
	public static void breitensuche(Graph g){
		
		System.out.println("-----------------------");
		System.out.println("Breitensuche:");
		Knoten start = g.getStart();
		
		LinkedList<Knoten> openList = new LinkedList<>();
		openList.add(start);
		boolean targetFound = false;
		
		while(!openList.isEmpty() && !targetFound){
			Knoten active = openList.remove();
			if(active.isEnd()){
				targetFound = true;
				System.out.println("Pfad zum Ziel:");
				active.printPath();
				System.out.println("Neue Karte: (C = closed)");
				g.print();
			}
			for(Knoten nachbar : active.getNachbarn()){
				if(!nachbar.isBlocked() && !nachbar.isClosed()){
					nachbar.setVorgaenger(active);
					openList.add(nachbar);
				}
			}
			active.setClosed();
		}
		
	}
	
	
public static void tiefensuche(Graph g){
		
		System.out.println("-----------------------");
		System.out.println("Tiefensuche:");
		Knoten start = g.getStart();
		
		Stack<Knoten> openList = new Stack<>();
		openList.push(start);
		boolean targetFound = false;
		
		while(!openList.isEmpty() && !targetFound){
			Knoten active = openList.pop();
			if(active.isEnd()){
				targetFound = true;
				System.out.println("Pfad zum Ziel:");
				active.printPath();
				System.out.println("Neue Karte: (C = closed)");
				g.print();
			}
			for(Knoten nachbar : active.getNachbarn()){
				if(!nachbar.isBlocked() && !nachbar.isClosed()){
					nachbar.setVorgaenger(active);
					openList.push(nachbar);
				}
			}
			active.setClosed();
		}
		
	}
	

}
