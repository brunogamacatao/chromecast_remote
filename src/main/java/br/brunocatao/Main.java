package br.brunocatao;

import su.litvak.chromecast.api.v2.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Main {
  private static void createIcon(String iconPath, Runnable listener) throws Exception {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    SystemTray tray = SystemTray.getSystemTray();
    TrayIcon icon = new TrayIcon(toolkit.getImage(Main.class.getResource(iconPath)));
    icon.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          listener.run();
        }
      }
    });

    tray.add(icon);
  }

  private static void createGearIcon() throws Exception {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    SystemTray tray = SystemTray.getSystemTray();
    TrayIcon icon = new TrayIcon(toolkit.getImage(Main.class.getResource("/icons/gear.png")));

    PopupMenu menu = new PopupMenu();

    MenuItem sobre = new MenuItem("Sobre");
    MenuItem sair  = new MenuItem("Sair");

    sobre.addActionListener((e) -> {
      JOptionPane.showMessageDialog(null, "Controle Remoto Chromecast", "Controles simples e rápidos para o Chromecast", JOptionPane.INFORMATION_MESSAGE);
    });

    sair.addActionListener((e) -> {
      System.exit(0);
    });

    menu.add(sobre);
    menu.addSeparator();
    menu.add(sair);

    icon.setPopupMenu(menu);

    tray.add(icon);
  }


  public static void main(String[] args) throws Exception {
    ChromeCasts.registerListener(new ChromeCastsListener() {
      @Override
      public void newChromeCastDiscovered(ChromeCast chromeCast) {
        System.out.println("Found - " + chromeCast.getName());

        new Thread(() -> {
          try {
            chromeCast.connect();

            createGearIcon();
            createIcon("/icons/stop.png", () -> {
              System.out.println("\tSending stop command");
              try {
                chromeCast.stopApp();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
            createIcon("/icons/pause.png", () -> {
              System.out.println("\tSending pause command");
              try {
                chromeCast.pause();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
            createIcon("/icons/play.png", () -> {
              System.out.println("\tSending play command");
              try {
                chromeCast.play();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

/*            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
              System.out.println("Digite seu comando: (1) play - (2) pause - (3) stop - (4) back 30 sec - (5) forward 30 sec - (else) sair");
              String comando = in.readLine();
              if (comando.equals("1")) {
                System.out.println("\tSending play command");
                chromeCast.play();
              } else if (comando.equals("2")) {
                System.out.println("\tSending pause command");
                chromeCast.pause();
              } else if (comando.equals("3")) {
                System.out.printf("\tSending stop command");
                chromeCast.stopApp();
              } else if (comando.equals("4")) {
                System.out.printf("\tSending back 30 sec command");
                chromeCast.seek(chromeCast.getMediaStatus().currentTime - 30.0);
              } else if (comando.equals("5")) {
                System.out.printf("\tSending forward 30 sec command");
                chromeCast.seek(chromeCast.getMediaStatus().currentTime + 30.0);
              } else {
                chromeCast.disconnect();
                System.out.println("\tExiting");
                ChromeCasts.stopDiscovery();
                in.close();
                System.exit(0);
                break;
              }
            }*/
          } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Deu erro: " + ex);
          }
        }).start();
      }

      @Override
      public void chromeCastRemoved(ChromeCast chromecast) {

      }
    });

    System.out.println("Searching Chromecasts ...");
    ChromeCasts.startDiscovery();
  }
}