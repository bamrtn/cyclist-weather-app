package uk.ac.cam. cl.interaction_design.group1.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search extends JFrame implements ActionListener
{
    JFrame saved;
    JButton back;

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==back)
            saved.setVisible(true);
            this.setVisible(false);
    }

    Search(JFrame ob)
    {


        saved=ob;

        setSize(350, 600);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel background=new Background();
        setContentPane(background);
        background.setLayout(new BorderLayout());
        add(makeBack(),BorderLayout.SOUTH);
        add(makeSearch(),BorderLayout.NORTH);

    }
    public JPanel makeSearch()
    {

        JPanel top=new JPanel();
        top.setLayout(new BorderLayout());

        JLabel text = new JLabel("Enter Location", SwingConstants.CENTER);

        text.setPreferredSize(new Dimension(350, 40));
        text.setFont(new Font("Courier New", Font.PLAIN, 25));
        text.setForeground(Color.white);
        top.add(text,BorderLayout.NORTH);
        top.setOpaque(false);
        JTextField searchBox=new JTextField();
        searchBox.setPreferredSize(new Dimension(150,50));
        top.add(searchBox,BorderLayout.CENTER);
        top.add(searchBox);
        return top;
    }
    public JPanel makeBack() {
        JPanel bottom = new JPanel();
        back = new JButton("Back");
        back.addActionListener(this);
        back.setPreferredSize(new Dimension(this.getWidth() / 3, 40));
        bottom.setLayout(new GridBagLayout());

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
}
