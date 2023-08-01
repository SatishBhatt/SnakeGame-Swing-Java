import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GamePanel extends JPanel implements ActionListener {

    static final int screen_width = 400;
    static final int screen_height = 400;
    static final int unit_size = 15;
    static final int game_units = (screen_width * screen_height)/unit_size;
    static final int delay = 65;
    
    final int x[] = new int[game_units];
    final int y[] = new int[game_units];
    int bodyPart = 6;
    int foodEaten;
    int foodX;
    int foodY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    
    GamePanel(){
        
        random = new Random();
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.yellow);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame( );
        
        
    }
    
    public void startGame(){
        newFood();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
        
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
    	
    	if(running) {

//        	for(int i=0; i <screen_height/unit_size ;i++) {
//        		g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
//        		g.drawLine( 0, i*unit_size, screen_width,i*unit_size);
//        	}
        	
            
        	g.setColor(Color.red);
        	g.fillRect(foodX, foodY, unit_size, unit_size);
        	
        	for (int i=0; i < bodyPart ; i++) {
        		
        		if(i == 0) {
        			g.setColor(Color.cyan);
        			g.fillRect(x[i], y[i], unit_size, unit_size);
        		}
        		else {
        			g.setColor(Color.blue);
        			g.fillRect(x[i], y[i], unit_size, unit_size);
        		}
        	}
        	
        	g.setColor(Color.blue);
        	g.setFont(new Font("Bradley Hand ITC", Font.BOLD , 15));
        	g.drawString("Score : " + foodEaten, 150, 12);
    	
    	}
    	
    	
    	
    	
    	else {
    		gameover(g);
    	}
    }
    
    public void newFood(){
        foodX = random.nextInt((int)(screen_width/unit_size) )* unit_size;
        foodY = random.nextInt((int)(screen_height/unit_size) )* unit_size;
    }
    
    public void move(){
    	
    	for(int i = bodyPart ; i > 0 ; i--) {
    		x[i] = x[i-1];
    		y[i] = y[i-1];
    	}
    	
    	switch(direction) {
    	
    	case 'U':
    		y[0] = y[0] - unit_size;
    		break;
    		
    	case 'D':
    		y[0] = y[0] + unit_size;
    		break;
    	
    	case 'R':
    		x[0] = x[0] + unit_size;
    		break;
    		
    	case 'L':
    		x[0] = x[0] - unit_size;
    		break;
    		
    	
    	}
        
    }
    
    public void checkFood(){
        if((x[0] == foodX) && (y[0] == foodY)) {
        	bodyPart++;
        	foodEaten++;
        	newFood();
        }
    }
    
    public void checkCollisions(){
    	//collision with body
    	for(int i = bodyPart ; i>0 ; i--) {
    		
    		if((x[0] == x[i])  && (y[0] == y[i] )) {
    			running = false;
    		}
    	}
    	
    	//collision with left border
    	if(x[0] < 0) {
    		running = false;
    		
    	}
    	//collision with right border
    	if(x[0] > screen_width) {
    		running = false;
    		
    	}
    	//collision with top border
    	if(y[0] < 0) {
    		running = false;
    		
    	}
    	//collision with bottom border
    	if(y[0] > screen_height) {
    		running = false;
    		
    	}
    	
    	if(!running) {
    		timer.stop();
    	}
    	
    	
        
    }
    
    public void gameover (Graphics g){
        //gameover text
    	g.setColor(Color.red);
    	g.setFont(new Font("Bradley Hand ITC", Font.BOLD , 60));
    	
    	g.drawString("GAME OVER", 15, 175);
    	
    	//score
    	g.setColor(Color.blue);
    	g.setFont(new Font("Monospaced", Font.BOLD , 40));
    	g.drawString("Score : " + foodEaten, 100, 230);
    	
    }
    
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    	if(running) {
    		move();
    		checkFood();
    		checkCollisions();
    	}
    	repaint();
    }
    
    
    
    
    public class MyKeyAdapter extends KeyAdapter{
        @Override
         public void keyPressed(KeyEvent e){
        	 
            switch(e.getKeyCode()) {
            
            case KeyEvent.VK_LEFT:
            	if(direction !='R') {
            		direction = 'L';
            	}
            	break;
            	
            case KeyEvent.VK_RIGHT:
            	if(direction !='L') {
            		direction = 'R';
            	}
            	break;
            	
            case KeyEvent.VK_UP:
            	if(direction !='D') {
            		direction = 'U';
            	}
            	break;
            	
            case KeyEvent.VK_DOWN:
            	if(direction !='U') {
            		direction = 'D';
            	}
            	break;
            
            }
        }
    }
}
