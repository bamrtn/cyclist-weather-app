package uk.ac.cam.cl.interaction_design.group1.frontend;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;


import uk.ac.cam.cl.interaction_design.group1.backend.Location;
import uk.ac.cam.cl.interaction_design.group1.backend.LocationState;
import uk.ac.cam.cl.interaction_design.group1.backend.Weather;
import uk.ac.cam.cl.interaction_design.group1.backend.Weather.Alert;
import uk.ac.cam.cl.interaction_design.group1.backend.Weather.GraphData;
import uk.ac.cam.cl.interaction_design.group1.backend.Weather.RainEnum;
import uk.ac.cam.cl.interaction_design.group1.backend.WeatherApi;
import uk.ac.cam.cl.interaction_design.group1.testing.backendTest;

public class MainScreen extends JFrame {

	//State
	private Calendar dateToDisplay;
	private int daysAheadOfToday;
	private Location location;

	//Images
	private Image lightShowersImage = getScaledImage("images/LIGHT_SHOWERS.png", 250, 150);
	private Image heavyShowersImage = getScaledImage("images/HEAVY_SHOWERS.png", 250, 150);
	private Image thunderImage = getScaledImage("images/THUNDER.png", 250, 150);
	private Image unlikelyImage = getScaledImage("images/UNLIKELY.png", 180, 150);
	
	private Image alertImage = getScaledImage("images/alert.png", 20, 20);
	private Image noAlertImage = getScaledImage("images/no_alert.png", 20,20);
	
	

	//Components that are dynamically updated
	private JLabel lblWarning = new JLabel("Showers in the afternoon!", new ImageIcon(alertImage), JLabel.LEFT);
	private JLabel lblTemperature = new JLabel();
	private JLabel lblDateAndWind = new JLabel();
	private JLabel lblContainerLabel = new JLabel(new ImageIcon(lightShowersImage));
	private ChartPanel chartPanel = new ChartPanel(null);

	public MainScreen(Location location) throws IOException{
		super("Home Screen");
		this.location = location;
		this.daysAheadOfToday = 0;

		setSize(350, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, daysAheadOfToday);
		this.dateToDisplay = c;

		Background contentPane = new Background();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		//Panel that contains saved location and search location navigation buttons
		add(createNorthPanel(), BorderLayout.NORTH);
		
		//THe main panel
		add(createCentralPanel(), BorderLayout.CENTER);
		
		//Panel that contains next day and previous day buttons
		add(createSouthPanel(), BorderLayout.SOUTH);

	}

	private Image getScaledImage(String filepath, int width, int height) throws IOException {
		return ImageIO.read(new File(filepath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch, title);
		component.setBorder(tb);
	}

	private JPanel createNorthPanel() {
		int componentWidth = this.getWidth() / 4;
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setOpaque(false);

		GridBagConstraints c = new GridBagConstraints();

		//Adds saved locations button to top left
		JButton btnSavedLocations = new JButton("<html>Saved<br>Locations</html>");
		btnSavedLocations.setPreferredSize(new Dimension(componentWidth, 40));
		MainScreen m = this;
		btnSavedLocations.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SavedLocations s = new SavedLocations();
				s.setCaller(m);
				s.setLocation(m.getLocation());
				m.setVisible(false);
				s.setVisible(true);

			}
		});

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		northPanel.add(btnSavedLocations, c);
		
		//Label that displays location stored in this object
		JLabel lblLocation = new JLabel("<html>" + this.location.name + "," + this.location.countryCode + "</html>");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setFont(new Font("Courier New", Font.PLAIN, 20));
		lblLocation.setPreferredSize(new Dimension(componentWidth + 70, 50));
		lblLocation.setOpaque(false);
		lblLocation.setForeground(Color.WHITE);
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		northPanel.add(lblLocation, c);

		//Button for searching locations
		JButton btnSearch = new JButton("<html>Search<br>Locations</html>");
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				m.setVisible(false);
				SearchLocations searchLocations = new SearchLocations(m);
				searchLocations.setLocation(m.getLocation());
				searchLocations.setVisible(true);

			}
		});
		btnSearch.setPreferredSize(new Dimension(componentWidth, 40));

		c.gridx = 2;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END;
		northPanel.add(btnSearch, c);

		return northPanel;
	}

	
	private JPanel createCentralPanel() throws IOException {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);


		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM");
		String dateString = s.format(dateToDisplay.getTime());


		//Container label containing background
		lblContainerLabel.setBounds((getWidth() - 250) / 2, 20, 280, 150);
		lblContainerLabel.setLayout(null);
		panel.add(lblContainerLabel);
		
		
		lblTemperature.setFont(new Font("Serif", Font.PLAIN, 50));
        lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBounds((getWidth() - 200) / 2, (lblContainerLabel.getHeight() - 100) / 2, 130, 70);
		lblContainerLabel.add(lblTemperature);

		lblDateAndWind.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateAndWind.setBounds(lblTemperature.getX(), lblTemperature.getY() + lblTemperature.getHeight() - 20, 200, 70);
		lblDateAndWind.setForeground(Color.WHITE);
		lblContainerLabel.add(lblDateAndWind, BorderLayout.CENTER);
		


		chartPanel.setOpaque(false);
		chartPanel.setBounds((getWidth() - 330) / 2 - 10, lblContainerLabel.getY() + lblContainerLabel.getHeight() + 20, 330, 200);

		panel.add(chartPanel);


		lblWarning.setForeground(Color.white);
		lblWarning.setOpaque(true);
		lblWarning.setBounds((getWidth() - 250) / 2, chartPanel.getY() + chartPanel.getHeight(), 250, 30);
		lblWarning.setBackground(Color.DARK_GRAY);
		panel.add(lblWarning);
		
		//Sets the contents of the labels to show the weather for the current day
		update();

		JButton btnMoreDetails = new JButton("More details >");
		MainScreen m = this;
		btnMoreDetails.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MoreDetailsScreen moreDetailsScreen = new MoreDetailsScreen(m.daysAheadOfToday, m.location, m);
				moreDetailsScreen.setLocation(m.getLocation());
				m.setVisible(false);
				moreDetailsScreen.setVisible(true);

			}
		});
		btnMoreDetails.setBounds(lblWarning.getX() + lblWarning.getWidth() - 150, lblWarning.getY() + lblWarning.getHeight(), 150, 20);

		panel.add(btnMoreDetails);


		return panel;
	}
	
	private void setUpChart(JFreeChart chart) {
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.getRenderer().setSeriesPaint(0, Color.RED);
		plot.getRenderer().setSeriesPaint(1, Color.blue);
		
		//Puts the legend in the bottom left
		LegendTitle lt = new LegendTitle(plot);
		lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		lt.setBackgroundPaint(new Color(200, 200, 255, 200));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.BOTTOM);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0.02, lt,RectangleAnchor.BOTTOM_RIGHT);

		DateAxis domainAxis = (DateAxis) plot.getDomainAxis(); //x axis
		domainAxis.setTickLabelFont(new Font("Arial" , Font.BOLD, 12));
        DateFormat formatter = new SimpleDateFormat("h a"); //Displays the values on the x-axis in the form n am/pm
        domainAxis.setDateFormatOverride(formatter);

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Arial" , Font.BOLD, 12));
        rangeAxis.setLowerBound(0);

		ta.setMaxWidth(0.4);
		plot.addAnnotation(ta);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);
		chart.setBackgroundPaint(null);
		chart.getPlot().setBackgroundPaint(null);
	}
	
	//Gets the graph data
	private XYDataset createDataset() {
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		TimeSeries temperature = new TimeSeries("Temperature (°C)");
		TimeSeries windSpeed = new TimeSeries("Wind speed (km / h)");

		GraphData graphData = WeatherApi.getGraphData(daysAheadOfToday);


		Hour h = new Hour(0, new Day(dateToDisplay.getTime()));

		for (int i = 0; i < 24; i++) {
			temperature.add(h, graphData.temperature.get(i));
			windSpeed.add(h, graphData.windspeed.get(i));
			h = (Hour) h.next();

		}

		dataset.addSeries(temperature);
		dataset.addSeries(windSpeed);
		return dataset;
	}

	private JPanel createSouthPanel() {

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		int buttonWidth = this.getWidth() / 3;
    	panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		JButton btnNextDay = new JButton("Next day");
		JButton btnPreviousDay = new JButton("Previous day");

		btnPreviousDay.setOpaque(false);
		btnPreviousDay.setEnabled(false);
		btnPreviousDay.setPreferredSize(new Dimension(buttonWidth, 20));
		btnPreviousDay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dateToDisplay.add(Calendar.DAY_OF_MONTH, -1);

				daysAheadOfToday --;

				if (daysAheadOfToday == 0)
					btnPreviousDay.setEnabled(false);

				update();
				btnNextDay.setEnabled(true);
			}
		});

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.3;
    	c.anchor = GridBagConstraints.LINE_START;
    	panel.add(btnPreviousDay, c);

		btnNextDay.setBounds(getWidth() - buttonWidth - 5, 0, buttonWidth, 20);
		btnNextDay.setOpaque(false);
		btnNextDay.setPreferredSize(new Dimension(buttonWidth, 20));

		btnNextDay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dateToDisplay.add(Calendar.DAY_OF_MONTH, 1);
				btnPreviousDay.setEnabled(true);
				daysAheadOfToday++;

				update();

				if (daysAheadOfToday == 4)
					btnNextDay.setEnabled(false);

			}
		});

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.3;
		c.anchor = GridBagConstraints.LINE_END;
		panel.add(btnNextDay, c);


		return panel;
	}

	private void update() {
		LocationState.setLocation(location);
		Weather requieredWeatherForDay = WeatherApi.getWeatherForDay(daysAheadOfToday);
		
		lblTemperature.setText(requieredWeatherForDay.temperature + "°C");

		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM");
		String dateString = s.format(dateToDisplay.getTime());

		int windSpeed = requieredWeatherForDay.windspeed;
		String windIntensity = "";
		if (windSpeed < 10) {
			windIntensity = "Very little wind";
		} else if (windSpeed >= 10 && windSpeed <= 20) {
			windIntensity = "Moderate wind";
		} else {
			windIntensity = "Strong wind";
		}

		Weather.RainEnum rainEnum = requieredWeatherForDay.rainLikelihood;
		String rainMesage = "";
		
		//Updates icon depending on rain level
		if (rainEnum == RainEnum.HEAVY_SHOWERS) {
			rainMesage = "Heavy showers likely today";
			lblContainerLabel.setIcon(new ImageIcon(heavyShowersImage));
		} else if (rainEnum == RainEnum.LIGHT_SHOWERS) {
			rainMesage = "Light showers likely today";
			lblContainerLabel.setIcon(new ImageIcon(lightShowersImage));
		} else if (rainEnum == RainEnum.THUNDER) {
			rainMesage = "THUNDER likely today";
			lblContainerLabel.setIcon(new ImageIcon(thunderImage));
		} else {
			rainMesage = "Rain unlikely today";
			lblContainerLabel.setIcon(new ImageIcon(unlikelyImage));
		}

		lblDateAndWind.setText("<html>" + dateString + "<br>" + windIntensity + " (" + windSpeed + " km/h) <br>" + rainMesage + "</html>");

		List<Alert> alerts = requieredWeatherForDay.alerts;
		if (alerts.size() == 0) {
			lblWarning.setIcon(new ImageIcon(noAlertImage));
			lblWarning.setText("No alerts for today");
		} else if (alerts.size() == 1)  {
			lblWarning.setIcon(new ImageIcon(alertImage));
			lblWarning.setText(alerts.get(0).name + " " + alerts.get(0).detail);

		} else {
			lblWarning.setIcon(new ImageIcon(alertImage));
			lblWarning.setText("<html>" + alerts.get(0).name + " " + alerts.get(0).detail + "<br>" + alerts.get(1).name + " " + alerts.get(1).detail);
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null , createDataset(),  false, true, false );
		setUpChart(chart);
		chartPanel.setChart(chart);
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Starting...");
		MainScreen m = new MainScreen(LocationState.getCurrentLocation());
		m.setVisible(true);
	}

}
