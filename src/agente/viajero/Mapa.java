/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agente.viajero;

/**
 *
 * @author Jordy
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

class Mapa extends JPanel
{
	int X,Y,y;
	Image Buffer;
	Graphics2D  graphic;
	Point puntos[];
	int n_puntos=0;
	Vector pos,x,y1;
	boolean limite=false;
	boolean result=false;
	String camino;
	
	Mapa()
	{
		this.setBackground(Color.black);
		puntos = new Point[100];
		pos    = new Vector();
		y1     = new Vector();
		x      = new Vector();
		this.setBorder( BorderFactory.createBevelBorder(1));
		
	  this.addMouseMotionListener(new MouseMotionAdapter()
	   {
	   	public void mouseMoved(MouseEvent e)
	   	{
	   	
	   		X=e.getX()/20;
	   		y=e.getY();
	   		
	   		Y= ((y-getHeight())*-1);
	   	
	   		repaint();
	   		
	   	}
	   	
	   	
	   });
	   
	   this.addMouseListener(new MouseAdapter()
	   {
	   	 public void mouseClicked (MouseEvent e)
	   	 { 	
	   	  if(!limite)
	   	  {
	   	  	if (!((X==24)||(Y/20==24)))
	   	  	{
	   	  	
	   	  
	   	    puntos[n_puntos++]=e.getPoint();
	   	    pos.add("("+e.getX()/20+","+Y/20+")");
	   	    x.add(e.getX()/20);y1.add(Y/20);
	   	    repaint();
	   	    if(n_puntos==20)
	   	    {
	   	    	limite=true;
	   	    	JOptionPane.showMessageDialog(null,"LLegaste al limite de nodos","Info",JOptionPane.INFORMATION_MESSAGE);
	   	    }
	   	    
	   	    }
	   	  }    
	   	 }
	   	 
	   });	
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
		
	public void paintComponent(Graphics g)
	{
		Buffer = this.createImage(this.getWidth(),this.getWidth());
		graphic =(Graphics2D)Buffer.getGraphics(); 
		pintar(graphic);
		g.drawImage(Buffer,0,0,this.getWidth(),this.getWidth(),this);
	}
	
	public void pintar(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //para mejorar la calidad de la pintada	
		g.setColor(Color.white);
		g.fillRect(0,0,getWidth(),getHeight() );
		g.setColor(Color.LIGHT_GRAY);
	    for(int i=0;i<100;i+=4)
		{
		    g.drawLine(5*i,500,5*i,0);
		    g.drawLine(0,5*i,500,5*i);
		   
		}    	
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD,12));
		g.drawString("("+X+","+Y/20+")",X*20 +2,y);
		
		
		if (this.result)
		  this.resultado(g);
		  	
		for (int i=0;i<n_puntos;i++)
		{
			g.setColor(Color.BLUE);	
			g.fillOval(puntos[i].x-8,puntos[i].y-8,18,18);
			g.setColor(Color.white);
			g.drawString(""+(i+1),(puntos[i].x-10) +9/2,(puntos[i].y-9)+15);
			g.setColor(Color.black);
			g.drawString(""+pos.elementAt(i),puntos[i].x-9,puntos[i].y-18);
		}
		
		
		
			
		
	}
	public int getCamino()
	{
	 return this.n_puntos;
	}
	
	
	public int getx(int n)
	{
		
		return Integer.parseInt( ""+x.elementAt(n));
	}
	
	public int gety(int n)
	{
		return Integer.parseInt( ""+y1.elementAt(n));
	}
	
	public void setResultado(String camino)
	{
		this.result=true;
		this.camino=camino;
		repaint();
	
		
		
	}
	
	public void resultado(Graphics2D g )
	{
		int temp,temp2;
		g.setColor(Color.red);
		for(int i=0;i<camino.length()-1;i++)
		{
			temp=Integer.parseInt(""+camino.substring(i,i+1));
			temp2=Integer.parseInt(""+camino.substring(i+1,i+2));
			g.drawLine(puntos[temp-1].x,puntos[temp-1].y,puntos[temp2-1].x,puntos[temp2-1].y);
			g.drawString( ""+(i+1),(int)((((puntos[temp-1].x+puntos[temp2-1].x)/2)+puntos[temp-1].x)/2), (int)((((puntos[temp-1].y+puntos[temp2-1].y)/2)+puntos[temp-1].y)/2));
			
		}
	}


	
}

class Tablero extends JPanel
{
	
    JTextField m[][];
		
	Tablero ()
	{
		
	}
	
	public void setMatriz(float mat[][])
	{
		
		this.removeAll();
		m = new JTextField[mat.length][mat[0].length];
		this.setLayout(new GridLayout(mat.length,mat[0].length) );
		
		for (int i = 0; i<mat.length; i++)
		{  for (int j = 0; j<mat[0].length; j++) 
			{
			  m[i][j]= new JTextField();
			  m[i][j].setEditable(false);
			  m[i][j].setAutoscrolls(false);
			  m[i][j].setFont((new Font("Arial", Font.BOLD,10)));
			  m[i][j].setText(""+mat[i][j]);
			  m[i][j].setCaretPosition(0);
			  add(m[i][j]);	
		    }
		}
		
		this.updateUI();
		
	}

}
