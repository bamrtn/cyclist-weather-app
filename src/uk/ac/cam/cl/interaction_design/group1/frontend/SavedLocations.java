package uk.ac.cam.cl.interaction_design.group1.frontend;

import javax.swing.*;
import javax.swing.border.Border;


import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import uk.ac.cam.cl.interaction_design.group1.backend.*;


public class SavedLocations extends JFrame implements ActionListener {
    private JButton addBtn;
    MainScreen caller;
    int del=0;
    JLabel popUp;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            Search search = new Search();
            search.setLocation(this.getLocation());
            search.setVisible(true);
            this.setVisible(false);


        }
    }
    public void setCaller(MainScreen m)
    {
        caller=m;
    }

    public SavedLocations() {
        super("saved locations");


        setSize(350, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel background = new Background();
        setContentPane(background);
        background.setLayout(new BorderLayout());

        add(makeTop(), BorderLayout.NORTH);
        add(makeCenter(), BorderLayout.CENTER);
        add(makeBack(), BorderLayout.SOUTH);
        setVisible(true);


    }

    public JPanel makeTop() {
        JPanel top = new JPanel();


        JLabel text = new JLabel("Choose Location", SwingConstants.CENTER);
        text.setOpaque(false);
        text.setPreferredSize(new Dimension(350, 40));
        text.setFont(new Font("Courier New", Font.PLAIN, 25));
        text.setForeground(Color.white);
        top.add(text);
        top.setOpaque(false);
        //addBorder(top, "fg");
        return top;

    }

    public JPanel makeBack() {
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridBagLayout());

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(this.getWidth() / 3, 40));
        SavedLocations s = this;
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                s.setVisible(false);

                caller.setLocation(s.getLocation());
                caller.setVisible(true);
            }
        });


        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.LINE_START;
        bottom.add(back, c);
        bottom.setOpaque(false);
        //addBorder(bottom, "fg");

        return bottom;
    }

    public JPanel makeCenter() {
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BorderLayout());

        JPanel southB = new JPanel();
        southB.setOpaque(false);
        addBtn = new CircleButton("+");
        addBtn.addActionListener(this::actionPerformed);
        CircleDelete delete=new CircleDelete("-") ;
        SavedLocations s=this;
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                del=1;
                JOptionPane.showMessageDialog(s,
                        "Click on location to delete!","Help",JOptionPane.PLAIN_MESSAGE);



            }
        });
        southB.add(delete);
        southB.add(addBtn);
        center.add(southB, BorderLayout.SOUTH);

        JPanel padding = new JPanel();
        //padding.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/8));
        padding.setOpaque(false);
        center.add(padding, BorderLayout.NORTH);

        JPanel locations = new JPanel();
        locations.setLayout(new GridLayout(5, 2, 25, 10));
        locations.setOpaque(false);
        List<Location> savedLocations = LocationState.getSavedLocations();
        for (Location l : savedLocations) {
            SavedLocations current = this;
            JButton jb = new JButton(l.name);
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(del==0) {
                            MainScreen mainScreen = new MainScreen(l);
                            mainScreen.setLocation(current.getLocation());
                            current.setVisible(false);
                            mainScreen.setVisible(true);
                        }
                        else {
                            del=0;
                            LocationState.removeSavedLocation(l);
                            locations.remove(jb);
                            locations.repaint();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });
            locations.add(jb);
        }


            center.add(locations, BorderLayout.CENTER);



        return center;
    }
}

