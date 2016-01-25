package v1.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GameTile extends JPanel{

	
	private int nr, pos;
	private GameStateVisualizer master;
	
	public GameTile(int nr, int pos, GameStateVisualizer master) {
		super();
		this.nr = nr;
		this.pos = pos;
		this.master = master;
		this.add(new JLabel(""+nr));
		this.initMouseListener();
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	
	public int getPos() {
		return pos;
	}
	
	
	public int getNr() {
		return nr;
	}
	
	
	private void initMouseListener(){
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				master.tileClicked(getNr(), getPos());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
