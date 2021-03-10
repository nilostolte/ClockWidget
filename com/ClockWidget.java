
/*
Author: Nilo Stolte

License: this code is provided as is and can be copied, modified, or distributed only when accompanied with this comment. All modifications must be commented with the proper credit under the "Modified by" section below. Following the credit, a brief description of the modification must be stated to differentiate from original code. Package information, comments, changing variables names and other changes that do not modify or do not add any functionality are not considered modifications and don't need to be commented.

Modified by:
*/

package com;


import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.RenderingHints;

import java.util.Calendar;
import java.lang.InterruptedException;


@SuppressWarnings("serial")
public class ClockWidget extends JFrame {
   private Clock clock;
   public ClockWidget() {
      super("Clock Widget");
      final int size = 112;
      setSize(size,size);
      calculateWindowsBounds();
      //  System.out.println("Windows Bounds (" + 
      //		  virtualBounds.x + ", " +
      //		  virtualBounds.y + ", " +
      //		  virtualBounds.width + ", " +
      //		  virtualBounds.height + ")"
      //  );
      //  System.out.println((virtualBounds.width - size -2));
      //setLocation(1252,2);
      setLocation(virtualBounds.width - size -2, 2);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //setLocationRelativeTo(null);
      setResizable(false);
      setUndecorated(true);
      setBackground(new Color(0f, 0f, 0f, 0f));
      clock = new Clock();
      clock.start();
      getContentPane().add(clock); 
   }
   
   private Rectangle virtualBounds = new Rectangle();
   private void calculateWindowsBounds() {
	   GraphicsEnvironment ge = GraphicsEnvironment.
	           getLocalGraphicsEnvironment();
	   GraphicsDevice[] gs =
	           ge.getScreenDevices();
	   for (int j = 0; j < gs.length; j++) {
	       GraphicsDevice gd = gs[j];
	       GraphicsConfiguration[] gc =
	           gd.getConfigurations();
	       for (int i=0; i < gc.length; i++) {
	           virtualBounds =
	               virtualBounds.union(gc[i].getBounds());
	       }
	   }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new ClockWidget().setVisible(true);
         }
      });
   }
}


@SuppressWarnings("serial")
class Clock extends JPanel implements Runnable{
   public Clock() {
      //setBackground(new Color(0.2f, 0.3f, 0.4f ));
      setBackground(new Color(0.2f, 0.3f, 0.4f, 0f));
   }
   Thread thread = null;
   static private Color extrimColor = new Color(127, 127, 127);
   static private Color rimColor = new Color(186, 186, 186);
   static private Color dialColor1 = new Color(226, 226, 226);
   static private Color dialColor2 = new Color(216, 216, 216);
   static private Color marksColor = new Color(25, 25, 25);
   static private Color midWhiteColor = new Color(229, 229, 229);
   static private Color red = new Color(194, 0, 0);
   static private Ellipse2D.Float extrim = new Ellipse2D.Float(0f, 0f, 440f, 440f);
   static private Ellipse2D.Float rim = new Ellipse2D.Float(2f, 2f, 436f, 436f);
   static private Ellipse2D.Float dial = new Ellipse2D.Float(25f, 25f, 390f, 390f);
   static private Ellipse2D.Float center1 = new Ellipse2D.Float(205f, 205f, 30f, 30f);
   static private Ellipse2D.Float center2 = new Ellipse2D.Float(210f, 210f, 20f, 20f);
   static private Ellipse2D.Float center3 = new Ellipse2D.Float(215f, 215f, 10f, 10f);
   static private Path2D.Float hourmark = hour_mark();
   static private Path2D.Float minutemark = minute_mark();
   static private Path2D.Float hourneedle = h_needle();
   static private Path2D.Float minuteneedle = m_needle();
   static private Path2D.Float secondneedle = s_needle();
   static private Area downshadow = downShadowArea();
   static private Area dialrim = dialRimArea();
   static private Area dialarea = dialArea();
   static private Area downShadowArea( ) {
      Area a = new Area(new Ellipse2D.Float(2f, 2f, 436f, 436f));
      Area c1 = new Area(new Ellipse2D.Float(20f, 20f, 400f, 400f));
      Area c2 = new Area(new Ellipse2D.Float(-60f, -245f, 560f, 560f));
      a.subtract(c1);
      a.subtract(c2);
      return a;
   }
   static private Area dialRimArea( ) {
      Area a = new Area(new Ellipse2D.Float(20f, 20f, 400f, 400f));
      Area c1 = new Area(new Ellipse2D.Float(25f, 25f, 390f, 390f));
      a.subtract(c1);
      return a;
   }
   static private Area dialArea( ) {
      Area a = new Area(new Ellipse2D.Float(25f, 25f, 390f, 390f));
      Area c1 = new Area(new Ellipse2D.Float(-60f, 125f, 560f, 560f));
      a.subtract(c1);
      return a;
   }
   private void drawHourMark( Graphics2D g ) {
      int i ;
      g.translate(220f, 220f);
      g.setColor(marksColor);
      for (i = 0; i < 4; i++) {
         g.fill(hourmark);
         g.rotate(1.57079632679489662);
      }
   }
   private void drawMinuteMark( Graphics2D g, double r ) {
      int i ;
      g.translate(220f, 220f);
      g.rotate(r);
      g.setColor(marksColor);
      for (i = 0; i < 4; i++) {
         g.fill(minutemark);
         g.rotate(1.57079632679489662);
      }
   }
   private void drawNeedle( Graphics2D g, double r, Path2D.Float p ) {
      g.translate(220f, 220f);
      g.rotate(r);
      g.setColor(marksColor);
      g.fill(p);
   }
   private void drawSecondNeedle( Graphics2D g, double r ) {
      g.translate(220f, 220f);
      g.rotate(r);
      g.setColor(red);
      g.fill(secondneedle);
   }
   public void start() {
      if (thread == null ) {
        thread = new Thread(this);
        thread.start();
      }
   }
   public void stop() {
      thread = null;
   }
   public void run() {
      while (thread != null) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {}
        repaint();
      }
   }
   static private GradientPaint downshadow_grad = new GradientPaint(220f,200f,new Color(196,196,196),220f,438f,new Color(75,75,75));
   static private GradientPaint dialrim_grad = new GradientPaint(220f,20f,new Color(75,75,75),220f,420f,new Color(196,196,196));
   static private Path2D.Float hour_mark() {
      Path2D.Float p = new Path2D.Float();
      p.moveTo(-2.5f,-150f);
      p.lineTo(2.5f,-150f);
      p.lineTo(2.5f,-180f);
      p.lineTo(-2.5f,-180f);
      p.closePath();
      return p;
   }
   static private Path2D.Float minute_mark() {
      Path2D.Float p = new Path2D.Float();
      p.moveTo(-2.5f,-158f);
      p.lineTo(2.5f,-158f);
      p.lineTo(2.5f,-175f);
      p.lineTo(-2.5f,-175f);
      p.closePath();
      return p;
   }
   static private Path2D.Float h_needle() {
      Path2D.Float p = new Path2D.Float();
      p.moveTo(-6f,43f);
      p.lineTo(6f,43f);
      p.lineTo(2f,-119f);
      p.lineTo(-2f,-119f);
      p.closePath();
      return p;
   }
   static private Path2D.Float m_needle() {
      Path2D.Float p = new Path2D.Float();
      p.moveTo(-6f,43f);
      p.lineTo(6f,43f);
      p.lineTo(1f,-158f);
      p.lineTo(-1f,-158f);
      p.closePath();
      return p;
   }
   static private Path2D.Float s_needle() {
      Path2D.Float p = new Path2D.Float();
      p.moveTo(-3f,35f);
      p.lineTo(3f,35f);
      p.lineTo(0f,-158f);
      p.closePath();
      return p;
   }
   public void paintComponent(Graphics g1) {
      super.paintComponent(g1);
      Graphics2D g = (Graphics2D) g1;
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
      AffineTransform matrix;
      Calendar currentDate = Calendar.getInstance();
      int hour =  currentDate.get(Calendar.HOUR);
      int minute = currentDate.get(Calendar.MINUTE);
      int second = currentDate.get(Calendar.SECOND);
      float s = (second/60f);
      float m = (s/60f) +  (minute/60f);
      //if (hour < 6) hour = hour + 6;
      //else hour = hour - 6;
      float h = (m/12f) + (hour/12f);
      g.scale(0.25f,0.25f);
      g.translate(3f, 3f);
      matrix = g.getTransform();
      g.setColor(extrimColor);
      g.fill(extrim);
      g.setColor(rimColor);
      g.fill(rim);
      g.setColor(dialColor2);
      g.fill(dial);
      g.setPaint(downshadow_grad);
      g.fill(downshadow);
      g.setPaint(dialrim_grad);
      g.fill(dialrim);
      g.setColor(dialColor1);
      g.fill(dialarea);
      drawHourMark(g);
      g.setTransform(matrix);
      drawMinuteMark(g, 0.5235987755982988731);
      g.setTransform(matrix);
      drawMinuteMark(g, 1.04719755119659775);
      g.setTransform(matrix);
      drawNeedle(g, 6.283185307179586477 * h, hourneedle);
      g.setTransform(matrix);
      drawNeedle(g, 6.283185307179586477 * m, minuteneedle);
      g.setTransform(matrix);
      drawSecondNeedle(g, 6.283185307179586477 * s);
      g.setTransform(matrix);
      g.setColor(marksColor);
      g.fill(center1);
      g.setColor(midWhiteColor);
      g.fill(center2);
      g.setColor(red);
      g.fill(center3);
   }
}

