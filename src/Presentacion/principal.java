

package Presentacion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class principal extends JFrame implements KeyListener{
    
    private pong pong;
    
    public principal(){
        setSize(600,600);
        addKeyListener(this);
        pong=new pong(600,600);
        pong.inicar();
        add(pong);
        
    }
    
    @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
//            if ((e.getKeyChar() == 'w') || (e.getKeyChar() == 'W') && pong.yBarraIzq >= 5) 
//                pong.yBarraIzq-=5;
//            else if (((e.getKeyChar() == 's') || (e.getKeyChar() == 'S')) && (pong.yBarraIzq + pong.barraIzq.getIconHeight()) <= pong.finalY) 
//               pong.yBarraIzq+=5;
            if ((e.getKeyCode()== KeyEvent.VK_UP)&& pong.yBarraDer >= 5) 
                pong.yBarraDer-=5;
             else if ((e.getKeyCode() == KeyEvent.VK_DOWN ) &&(pong.yBarraDer + pong.barraDer.getIconHeight()) <= pong.finalY) 
               pong.yBarraDer+=5;
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                pong.setEntrar(true);
            }
            if(e.getKeyChar()=='r'){
                 pong.reestablecer();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
    
    public static void main(String[] args) {
        principal p=new principal();
        p.setVisible(true);
        p.setDefaultCloseOperation(3);
        p.setResizable(false);
    }
    
    private class pong extends JPanel implements KeyListener{
        
        private javax.swing.Timer t;
        private int finalX; 
        private int finalY;
        private int xPelota=10+50;
        private int yBarraIzq=200;
        private int yPelota=yBarraIzq;
        private int yBarraDer=200;
        private ImageIcon pelota;
        private boolean normalX=true;
        private boolean normalY=true;
        private ImageIcon barraIzq;
        private ImageIcon barraDer;
        private KeyListener accionTeclado;
        private boolean entrar=false;
        private int velocidad=6;
        private int tiempo=0;
        
        
        public pong(int fx,int fy){
            addKeyListener(this);
            finalX=fx;
            finalY=fy;
            pelota=new ImageIcon("imagenes/pelota.png");
            barraIzq=new ImageIcon("imagenes/barra.png");
            barraDer=new ImageIcon("imagenes/barra.png");
            t=new javax.swing.Timer(10,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(tiempo==1000){
                        velocidad+=1;
                        tiempo=0;
                    }
                    tiempo+=10;
                    if (entrar) {
                        int mitad=pelota.getIconHeight()/2;
                        if (normalX) {
                            xPelota+=velocidad;
                            if ((((xPelota + pelota.getIconWidth()) >= (finalX - (barraDer.getIconWidth())))
                                    &&((xPelota + pelota.getIconWidth())<=(finalX)))
                                    && (mitad + yPelota >= yBarraDer && mitad + yPelota <= yBarraDer + barraDer.getIconHeight())) {
                                xPelota-=velocidad;
                                normalX = false;
                            }
                        } else {
                            xPelota-=velocidad;
                            if (((xPelota <= barraIzq.getIconWidth())&&(xPelota>=0)) && (mitad + yPelota >= yBarraIzq && mitad + yPelota <= yBarraIzq + barraIzq.getIconHeight())) {
                                xPelota+=velocidad;
                                normalX = true;
                            }
                        }
                        if (normalY) {
                            yPelota+=velocidad;
                            if (yPelota + pelota.getIconHeight() >= finalY) {
                                yPelota-=velocidad;
                                normalY = false;
                            }
                        } else {
                            yPelota-=velocidad;
                            if (yPelota <= -20) {
                                yPelota+=velocidad;
                                normalY = true;
                            }
                        }
                        if(yPelota+barraIzq.getIconHeight()<=finalY){
                           yBarraIzq=yPelota; 
                           yBarraDer=yPelota;
                        }else{
                            yBarraIzq=finalY-barraIzq.getIconHeight();
                            yBarraDer=finalY-barraDer.getIconHeight();
                        }
                    }else{
                        int mitad=barraIzq.getIconHeight()/2;
                        yPelota=yBarraIzq+mitad;
                    }
                    update();
                }
            } );
            
            
        }
        
        public void reestablecer() {
            this.stop();
            xPelota = 10 + 50;
            yBarraIzq = 200;
            yPelota = yBarraIzq;
            yBarraDer = 200;
            normalX = true;
            normalY = true;
            entrar = false;
            this.inicar();
        }
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;
            pintarFondo(g);
            pintarPelota(g);
            pintarBarras(g);
           
        }
            
        public void pintarFondo(Graphics g){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, finalX, finalY);
        }
        
        public void pintarPelota(Graphics g){
            g.drawImage(pelota.getImage(), xPelota, yPelota,pelota.getIconWidth(),pelota.getIconHeight(), this);
        }
        
         
        public void pintarBarras(Graphics g){
            g.drawImage(barraIzq.getImage(),0, yBarraIzq,barraIzq.getIconWidth(),barraIzq.getIconHeight(), this);
            g.drawImage(barraDer.getImage(),finalX-(barraDer.getIconWidth()), yBarraDer,barraDer.getIconWidth(),barraDer.getIconHeight(), this);
        }
        
         public void inicar(){
            t.start();
        }
        public void update(){
            repaint();
        }
        public void stop(){
            t.stop();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("a");
            if ((e.getKeyChar() == 'w') || (e.getKeyChar() == 'W') && yBarraIzq < 0) 
                yBarraIzq--;
             else if ((e.getKeyChar() == 's') || (e.getKeyChar() == 'S') && (yBarraIzq + barraIzq.getIconHeight()) > finalY) 
                yBarraIzq++;
            
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }

        public boolean isEntrar() {
            return entrar;
        }

        public void setEntrar(boolean entrar) {
            this.entrar = entrar;
        }

        
        
        public int getyBarraIzq() {
            return yBarraIzq;
        }

        public void setyBarraIzq(int yBarraIzq) {
            this.yBarraIzq = yBarraIzq;
        }

        public int getyBarraDer() {
            return yBarraDer;
        }

        public void setyBarraDer(int yBarraDer) {
            this.yBarraDer = yBarraDer;
        }

        public int getFinalX() {
            return finalX;
        }

        public void setFinalX(int finalX) {
            this.finalX = finalX;
        }

        public int getFinalY() {
            return finalY;
        }

        public void setFinalY(int finalY) {
            this.finalY = finalY;
        }

        public ImageIcon getBarraIzq() {
            return barraIzq;
        }

        public void setBarraIzq(ImageIcon barraIzq) {
            this.barraIzq = barraIzq;
        }

        public ImageIcon getBarraDer() {
            return barraDer;
        }

        public void setBarraDer(ImageIcon barraDer) {
            this.barraDer = barraDer;
        }
        
        
    }
    
}
