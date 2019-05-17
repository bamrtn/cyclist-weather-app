package uk.ac.cam.cl.interaction_design.group1;

//Todo change all instances of testLocation to backend.Location

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SearchLocations extends JFrame implements ActionListener {

    private JTextField searchField;
    
    private JPanel buttonPanel;
    private List<JButton> locationButtons = new ArrayList<>();
    private Set<backend.Location> locations;
    private Map<String, backend.Location> locationMap;

    //Handles button presses and other events in this frame
    @Override
    public void actionPerformed(ActionEvent e) {
        //Update the buttons to show relevant locations
        if (e.getActionCommand().equals("Search_Button_Pressed")){
            String searchString = searchField.getText();
            List<backend.Location> predictedLocations = this.locationsLikeText(searchString);
            locationMap = new HashMap<>();

            if (predictedLocations.size() > 0){
                buttonPanel.setVisible(true);
            }
            else{
                buttonPanel.setVisible(false);
            }

            if (predictedLocations.size() > 15){
                predictedLocations = predictedLocations.subList(0,15);
            }

            for (int i = 0; i < predictedLocations.size(); i++){
                backend.Location location = predictedLocations.get(i);
                String buttonLabel = location.getName() + ", " + location.getContryCode();
                locationMap.put(buttonLabel, location);
                locationButtons.get(i).setText(buttonLabel);
                locationButtons.get(i).setVisible(true);
            }

            for (int i = predictedLocations.size(); i < 15; i++){
                locationButtons.get(i).setText("<blank>");
                locationButtons.get(i).setVisible(false);
            }
        }

        //Take the user back
        else if (e.getActionCommand().equals("Back_Button_Pressed")){
            //Todo: Take user back to appropriate screen
            System.out.println("Back");
        }

        //Take the user to the new location
        else{
            //Todo: Update location in appropriate place and take user to appropriate screen
            String buttonLabel = e.getActionCommand();
            backend.Location location = locationMap.get(buttonLabel);
            System.out.println(location);
            System.out.println(buttonLabel);
            System.out.println();
        }
    }

    //Return some subset of our location set, that match s, in the form of a list.
    private List<backend.Location> locationsLikeText(String s){
        List<backend.Location> likelyLocations = new ArrayList<>();
        s = s.toLowerCase();

        for(backend.Location l: locations){
            if (l.getName().toLowerCase().contains(s)){
                likelyLocations.add(l);
            }
        }
        return likelyLocations;
    }

    //Make the panel that houses the text input and button for searching
    private JPanel makeSearch(){
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.setOpaque(false);

        JLabel text = new JLabel("Enter Location", SwingConstants.CENTER);
        text.setPreferredSize(new Dimension(350, 40));
        text.setFont(new Font("Courier New", Font.PLAIN, 25));
        text.setForeground(Color.white);

        this.searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150,50));

        JButton searchButton = new JButton();
        Image searchIcon = Toolkit.getDefaultToolkit().createImage("data/searchIcon.png");
        searchButton.setIcon(new ImageIcon(searchIcon));
        searchButton.addActionListener(this);
        searchButton.setActionCommand("Search_Button_Pressed");

        top.add(text,BorderLayout.NORTH);
        top.add(searchField,BorderLayout.CENTER);
        top.add(searchButton, BorderLayout.EAST);
        return top;
    }

    //Makes the panel that houses the back button
    private JPanel makeBack() {
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setPreferredSize(new Dimension(this.getWidth() / 3, 40));
        backButton.setActionCommand("Back_Button_Pressed");
        bottom.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.LINE_START;
        bottom.add(backButton, c);

        return bottom;
    }

    //Makes the panel that houses the location buttons
    private JPanel makeButtonPanel(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(layout);

        JLabel text = new JLabel("Did you mean?: ", SwingConstants.CENTER);
        text.setFont(new Font("Courier New", Font.PLAIN, 12));
        text.setForeground(Color.white);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        panel.add(text, constraints);

        //Create a vertical stack of 15 buttons and hide them until we need them
        for (int i = 0; i < 15; i++){
            JButton newButton = new JButton("<blank>");
            newButton.setBackground(new Color(255,255,255));
            newButton.addActionListener(this);
            newButton.setVisible(false);

            locationButtons.add(newButton);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = GridBagConstraints.RELATIVE;
            panel.add(newButton, constraints);
        }

        return panel;
    }

    //Primary constructor. Takes name of the frame and the set of locations for searching.
    //Calls the panels and assembles them into the frame
    public SearchLocations(String name, Set<backend.Location> locations){
        super(name);
        this.locations = locations;
        this.setSize(350,600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel background = new Background();
        this.setContentPane(background);
        background.setLayout(new BorderLayout());

        JPanel backButtonPanel = makeBack();
        JPanel searchFieldPanel = makeSearch();
        this.buttonPanel = makeButtonPanel();
        buttonPanel.setVisible(false);

        add(backButtonPanel,BorderLayout.SOUTH);
        add(searchFieldPanel,BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    //Todo: Delete this when code properly merged
    //Generate some fake location data to test with
    private static Set<backend.Location> generateFakeLocations(){
        Set<backend.Location> fakeLocations = new HashSet<>();
        fakeLocations.add(new backend.Location("London", "UK", 1));
        fakeLocations.add(new backend.Location("Cambridge", "UK", 2));
        fakeLocations.add(new backend.Location("Weymouth", "UK", 3));
        fakeLocations.add(new backend.Location("Cambridge", "US", 4));
        fakeLocations.add(new backend.Location("Frankfurt", "DE", 5));
        fakeLocations.add(new backend.Location("Paris", "FR", 6));
        fakeLocations.add(new backend.Location("Los Angeles", "US", 7));
        fakeLocations.add(new backend.Location("Oslo", "NO", 8));
        fakeLocations.add(new backend.Location("Stockholm", "SE", 9));
        fakeLocations.add(new backend.Location("Mexico City", "MX", 10));
        fakeLocations.add(new backend.Location("Nairobi", "KE", 11));
        fakeLocations.add(new backend.Location("Yeovil", "UK", 12));
        fakeLocations.add(new backend.Location("Liverpool", "UK", 13));
        fakeLocations.add(new backend.Location("Manchester", "UK", 14));
        fakeLocations.add(new backend.Location("Bristol", "UK", 15));
        fakeLocations.add(new backend.Location("Dorchester", "UK", 16));

        return fakeLocations;
    }

    //Main method for testing
    public static void main(String[] args){
        Set<backend.Location> ls = generateFakeLocations();
        SearchLocations frame = new SearchLocations("Weather App Search", ls);
        frame.setVisible(true);
    }

}
