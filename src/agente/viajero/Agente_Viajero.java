/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agente.viajero;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Jordy
 */
public class Agente_Viajero extends JFrame implements Runnable{
    Container c;
	Mapa mapa;
	JList Caminos;
	Vector cam;
	JButton bt_comenzar,bt_camino;
	JList list;
	JScrollPane pane;
	JComboBox combo;
	int nodo_inicial,camino=-1;
	float mat[][];
	Tablero tab;
	Thread hilo;
	Agente_Viajero yo;
	
	
	Agente_Viajero()
	{
	  this.setTitle("AGENTE VIAJERO");
	  c = this.getContentPane();
	  c.setLayout(null);
	  yo=this;
	 
	  
	  Caminos = new JList();
	  cam= new Vector();	
	  tab = new Tablero();
	  
	  mapa = new Mapa();
	  mapa.setBounds(10,10,500,500);
	  c.add(mapa);
	  
	  bt_comenzar = new JButton("Aceptar");
	  bt_camino   = new JButton("Generar");
	  bt_comenzar.addActionListener(new ActionListener()
	  {
	  	public void actionPerformed (ActionEvent e)
	  	{
	  		String cad="";
	  	    cam.clear();
	  	    int tam=getCam();
	  	    String vec[]= new String[tam];
	  	      for (int i=0;i<tam;i++) 
	  	      {
	  	      	vec[i]=""+(i+1);
	  	      	combo.addItem(vec[i]);
	  	      	
	  	      	
			  }
			  
	  		
	  	
	  		list.updateUI();
		
	  		list.setListData(cam);
	  		pane.updateUI();
	  		bt_comenzar.setVisible(false);
	  		bt_camino.setVisible(true);
	  		Matriz(tam);
	  		tab.setMatriz(mat);
	  		
	  	}
	  });
	  list  = new JList();
	  pane  = new JScrollPane(list);
	  combo = new JComboBox();
	  combo.setBounds(511,10,100,20);
	  
	  bt_comenzar.setBounds(511,240,100,20);
	  bt_camino.setBounds(511,240,100,20);
	  bt_camino.setVisible(false);
	  pane.setBounds(511,35,100,200);
	  tab.setBounds(511,270,800-511,200);
	  c.add(tab);
	  c.add(combo);
	  c.add(bt_comenzar);
	  c.add(bt_camino);
	  c.add(pane);
	  
	  bt_camino.addActionListener(new ActionListener()
	  {
	  	public void actionPerformed (ActionEvent e)
	  	{
	  		int tam=getCam();
	  		String temp="";
	  		nodo_inicial=combo.getSelectedIndex()+1;
	  		cam.clear();
	  		double a =System.currentTimeMillis();
	  		Sig(combo.getSelectedIndex(),tam,0,temp);
	  		double c =System.currentTimeMillis()-a;
	  		//JOptionPane.showMessageDialog(yo,c,"",1);
	  		list.updateUI();
	  		hilo = new Thread(yo);
	  		hilo.start();
	  	
	  	}
	  });
	  
	}
	
    public int getCam()
	{
		return mapa.getCamino();
	}
	
	public void run()
	{
		
	int i=0;float menor=getValcad(""+cam.elementAt(0)),m=0;
	boolean sw=true;
	
		while (i<cam.size())
		{
			try 
			{
				String cad="";
				
				
				
						cad=""+cam.elementAt(i);
						m=getValcad(cad);
						System.out.println (cad.substring(0,1) +" "+(i+1)+" "+m);
						mostrar(cad);
						Thread.sleep(10);
					
							if (m<=menor)
							{
							camino=i;
							menor=m;
							System.out.println (" MENOR " +menor);
							}

				
					
					i++;
			
			
				  
		    }
		    catch (Exception ex) 
		    {
		    	
		    }
		}
		
		mostrar(""+cam.elementAt(camino));	
	 JOptionPane.showMessageDialog(tab,menor+" en el camino "+"\n"+cam.elementAt(camino),"Mejor Ruta",1);
	 
	
	}

public void Sig(int i, int n, int p, String acum)
{

    if (p<n && i< n)// si se acabo un para o un contador
    {
    	boolean no=false;
      	
    	for(int k=0;k<acum.length()&&!no;k++)//si ya esta el numero de lo vuelo
        {
            if(acum.substring(k,k+1).equals(""+(i+1))) no=true;
        }
        
        if(!no) Sig(0,n,p+1,acum+""+(i+1));
        Sig(i+1,n,p,acum);
        no=false;
        for(int k=0;k<acum.length() && !no; k++)
        {
            if(acum.substring(k,k+1) .equals(""+(i+1))) no=true;
        }
        if(!no && acum.length()==n-1)
        {
        	acum=acum+""+(i+1)+nodo_inicial;
        	if(acum.startsWith(""+nodo_inicial))
             cam.add(acum);
             else
              return ;
            
        }
        
       
    	
    }
}


public float getDistancia(String i, String j)
{
	
	int a,b,c,d;
	float r,r1;
	a =mapa.getx(Integer.parseInt(i));
	b =mapa.getx(Integer.parseInt(j));
	c =mapa.gety(Integer.parseInt(i));
	d =mapa.gety(Integer.parseInt(j));
    r=(float)Math.pow(b-a,2);r1=(float)Math.pow(d-c,2);
    
	return (float)Math.sqrt((r+r1));
}	

public void Matriz(int n)
{
	mat = new float[n][n];
	
    for (int i=0;i<n;i++)
    {  
    	for(int j=0;j<n;j++)
    	{
    	    mat[i][j]=getDistancia(""+i,""+j);
    	    
    	}
    	
    } 	
}

public float getValcad(String cadena)
{
	float num=0;
	int a=0,b=0;
   for (int i=0;i<cadena.length()-1;i++) 
   {
   	  a=Integer.parseInt(cadena.substring(i,i+1));
   	  b=Integer.parseInt(cadena.substring(i+1,i+2));
   	  num=num+mat[a-1][b-1];
   	 
   	  
   }
  
   return num;	
}

public  float getTotal()
{
	String cad="";
  float menor=getValcad(""+cam.elementAt(0)),m=0;

	 for (int i=0;i<cam.size();i++)
	 {  
	    cad=""+cam.elementAt(i);
	    m=getValcad(cad);
	    System.out.println (cad.substring(0,1) +" "+(i+1)+" "+m);
	 	if (cad.startsWith(""+nodo_inicial))
	 	{
			
			
	 		if (m<=menor)
	 		{
	 			camino=i;
	 			menor=m;
	 			System.out.println (" MENOR " +menor);
	 		}
	 	}else
	 	return menor;
	 	
	 
	 }
	 
	 return menor;
}

public void mostrar (String camino)
{
	mapa.setResultado(camino);
}
	
public static void main (String arg[])
	{
	   Agente_Viajero frame = new Agente_Viajero();
	   Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	   frame.setUndecorated(!true);
	   frame.setSize(size.width,size.height);
	   frame.setVisible(true);	
	}
}
