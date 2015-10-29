
public class Graph {

	
	Knoten[][] knoten;
	Knoten start;
	Knoten end;
	
	public Graph(Knoten[][] knoten) {
		this.knoten = knoten;
		for(int row = 0; row < knoten.length; row++){
			for(int collumn = 0; collumn < knoten[0].length; collumn++){
				Knoten k = knoten[row][collumn];
				if(k.isStart()){
					start = k;
				}
				if(k.isEnd()){
					end = k;
				}
			}
		}
	}
	
	
	public void reset(){
		for(int row = 0; row < knoten.length; row++){
			for(int collumn = 0; collumn < knoten[0].length; collumn++){
				 knoten[row][collumn].setOpen();
			}
		}
	}
	
	public Knoten getEnd() {
		return end;
	}
	
	
	public Knoten getStart() {
		return start;
	}
	
	
	public void print(){
		for(int row = 0; row < knoten.length; row++){
			for(int collumn = 0; collumn < knoten[0].length; collumn++){
				Knoten k = knoten[row][collumn];
				if (k.isStart()) {
					System.out.print('S');
				} else if (k.isEnd()) {
					System.out.print('E');
				} else if (k.isBlocked()) {
					System.out.print('X');
				}else if (k.isClosed()) {
					System.out.print('C');
				} else {
					System.out.print(' ');
				}
			}
			System.out.println();
		}
	}
}
