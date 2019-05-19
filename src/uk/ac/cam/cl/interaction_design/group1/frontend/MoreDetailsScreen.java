
package uk.ac.cam. cl.interaction_design.group1.frontend;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import uk.ac.cam.cl.interaction_design.group1.backend.Location;
import uk.ac.cam.cl.interaction_design.group1.backend.LocationState;
import uk.ac.cam.cl.interaction_design.group1.backend.Weather;
import uk.ac.cam.cl.interaction_design.group1.backend.WeatherApi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoreDetailsScreen extends JFrame{
    private Location location;
    private int day;
    private MainScreen caller;

    public MoreDetailsScreen(int day1, Location location1, MainScreen caller1) {
        super("More Details");

        this.location = location1; //set location, day and which screen this was called from as passed in
        this.day = day1;
        this.caller = caller1;

        setSize(350, 600);    //set size of screen
        setResizable(false);                // prevent resizing
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Background contentPane = new Background();  //create the pane with the desired background
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());  //create a layout to add each component to

        contentPane.add(makeBack(),BorderLayout.SOUTH); //add the back button component at the bottom
        contentPane.add(createCentralPanel(), BorderLayout.CENTER); // add the table in the centre
        contentPane.add(makeTitle(),BorderLayout.NORTH); // add the day and location at the top
    }


    private JPanel createCentralPanel(){

        JPanel centralPanel = new JPanel();
        centralPanel.setOpaque(false);
        centralPanel.setLayout(null);

        LocationState.setLocation(this.location);
        Weather weather = WeatherApi.getWeatherForDay(day);
        String[][] data = {
                {"Temperature", weather.temperature + "°C"},
                {"Wind Speed" , weather.windspeed + "km / h"},
                {"Humidity", weather.humidity + "%"},
                {"Sunrise", weather.sunrise},
                {"Sunset", weather.sunset}
        };



        String[] colNames = {"Field", "Value"};   // column names, not displayed but code breaks without!!

        //allow the text in the table to be set as centre aligned
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );

        JTable t = new JTable(data,colNames); // create a new table with relevant data

        centralPanel.add(t); // add the table to the centre panel

        t.setRowHeight(40); // set the height of rows in the table
        t.setBounds(this.getWidth()/30, this.getHeight()/10, 27*this.getWidth()/30, 200); //set the table size and position

        // prevents the table from being edited once running
        t.setModel(new DefaultTableModel(data, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // set each cell as centre aligned
        for(int x=0;x<2;x++){
            t.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
       // addBorder(centralPanel,"central");
        return(centralPanel);
    }

    public JPanel makeBack() {
        JPanel bottom = new JPanel();
        JButton back = new JButton("Back"); // create button with text "Back"
        MoreDetailsScreen m = this;
        back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				caller.setLocation(m.getLocation());
				m.setVisible(false);
				caller.setVisible(true);


			}
		});
        back.setPreferredSize(new Dimension(this.getWidth() / 3, 40)); // set the size of the button

        //set the gridbag layout stuff
        bottom.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.LINE_START;

        bottom.add(back, c); // add the button to the screen using the correct layout
        bottom.setOpaque(false); // make the bottom panel transparent


        back.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				m.setVisible(false);
				caller.setVisible(true);
				caller.setLocation(m.getLocation());

			}
        });


        return bottom;

    }


    public JPanel makeTitle(){
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BorderLayout());

        // get the date from the format provided to the constructor
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM");
        String dateString = s.format(c.getTime());

        // create labels containing the text for the titles, centre aligned
        JLabel dateLabel = new JLabel(dateString, JLabel.CENTER);
        JLabel locationLabel = new JLabel(this.location.name + ", " + this.location.countryCode, JLabel.CENTER);

        // set the size of the two title boxes
        dateLabel.setPreferredSize(new Dimension(350, 40));
        dateLabel.setFont(new Font("Courier New", Font.PLAIN, 25));
        dateLabel.setForeground(Color.white);

        locationLabel.setPreferredSize(new Dimension(350, 40));
        locationLabel.setFont(new Font("Courier New", Font.PLAIN, 25));
        locationLabel.setForeground(Color.white);

        // add titles to screen, date above location
        top.add(dateLabel,BorderLayout.CENTER);
        top.add(locationLabel, BorderLayout.SOUTH);

        return top;
    }



}
