/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Humanoide
 */
public class GraphFrame extends javax.swing.JFrame {

    /**
     * Creates new form GraphFrame
     */
    public GraphFrame() {
        initComponents();
    }
    BufferedImage bufImg;
    String imageName;
    int cont = 0;
    Timer t;

    public GraphFrame(String imageName) {
        this();
        this.imageName = imageName;
        this.setTitle(imageName);
        t = new javax.swing.Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                //do an icon change
                try {
                   bufImg = ImageIO.read(new File(imageName + ".png"));
                   setSize(bufImg.getWidth()+50, bufImg.getHeight() + 50);
                } catch (IOException ex) {
                }
                cont++;
                if (cont <= 2) {
                    t.stop();
                }
                if(cont>0){
                    setVisible(true);
                }
            }
        });

        t.start();

        
    }

    public void resize() {
        this.setSize((int) (bufImg.getWidth() * 1.1), (int) (bufImg.getHeight() * 1.1));
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, bufImg.getWidth()*2, bufImg.getHeight()*2);
        g.drawImage(bufImg, 5, 30, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
