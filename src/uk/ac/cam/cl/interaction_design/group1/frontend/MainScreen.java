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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

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

import uk.ac.cam.cl.interaction_design.group1.testing.backendTest;


public class MainScreen extends JFrame {
	
	private Calendar dateToDisplay = Calendar.getInstance();
	private int daysAheadOfToday = 0;
	private JLabel lblDateAndWind;
	private ChartPanel chartPanel;
	
	public MainScreen() throws IOException {
		super("Home Screen");
		setSize(350, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dateToDisplay.setTime(new Date());
		
		Image backgroundImage = ImageIO.read(new File("images/AppBackground.png"));
		ImagePanel contentPane = new ImagePanel(backgroundImage);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		
		add(createNorthPanel(), BorderLayout.NORTH);
		add(createCentralPanel(), BorderLayout.CENTER);
		add(createSouthPanel(), BorderLayout.SOUTH);
		
	}
	
	
	private JPanel createNorthPanel() {
		int componentWidth = this.getWidth() / 4;
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		
		
		JButton btnSavedLocations = new JButton("<html>Saved<br>Locations</html>");
		btnSavedLocations.setPreferredSize(new Dimension(componentWidth, 30));
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		northPanel.add(btnSavedLocations, c);
		
		
		JLabel lblLocation = new JLabel("Cambridge");
		lblLocation.setPreferredSize(new Dimension(componentWidth, 30));
		lblLocation.setForeground(Color.WHITE);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		northPanel.add(lblLocation, c);
		
		
		JButton btnSearch = new JButton("<html>Search<br>Locations</html>");
		btnSearch.setPreferredSize(new Dimension(componentWidth, 30));
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		c.anchor = GridBagConstraints.LINE_END;
		northPanel.add(btnSearch, c);
		
		return northPanel;
	}
	
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createLineBorder(Color.white);
		Border tb = BorderFactory.createTitledBorder(etch, title);

		component.setBorder(tb);
	}
	
	
	
	//width = 350
	//height = 600
	private JPanel createCentralPanel() throws IOException {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);
		
		
		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM");
		String dateString = s.format(dateToDisplay.getTime());
		
		//Image of sun
		BufferedImage cloudImage = ImageIO.read(new File("images/cloud.png"));
		Image scaledSunImage = cloudImage.getScaledInstance(250, 150, Image.SCALE_SMOOTH);
		
		//Container label containing sun background
		JLabel lblContainerLabel = new JLabel(new ImageIcon(scaledSunImage));
		lblContainerLabel.setBounds((getWidth() - 250) / 2, 20, 250, 150);
		lblContainerLabel.setLayout(null);
		panel.add(lblContainerLabel);
		
		JLabel lblTemperature = new JLabel("26 °C");
		lblTemperature.setFont(new Font("Serif", Font.PLAIN, 50));
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBounds((getWidth() - 200) / 2, (lblContainerLabel.getHeight() - 100) / 2, 130, 70);
		lblContainerLabel.add(lblTemperature);
		
		lblDateAndWind = new JLabel("<html>" + dateString + "<br>Moderate wind (32 km/h) <br>Rain Unlikely</html>");
		lblDateAndWind.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateAndWind.setBounds(lblTemperature.getX() + 25, lblTemperature.getY() + lblTemperature.getHeight() - 20, 200, 70);
		lblDateAndWind.setForeground(Color.WHITE);
		lblContainerLabel.add(lblDateAndWind, BorderLayout.CENTER);
		
		
		//JFreeChart chart = ChartFactory.createXYLineChart(null, "time", null , createDataset(), PlotOrientation.VERTICAL, false, true, false );
		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, "time", null , createDataset(),  false, true, false );
		setUpChart(chart);
        
		chartPanel = new ChartPanel(chart);
		chartPanel.setOpaque(false);
		chartPanel.setBounds((getWidth() - 330) / 2 - 10, lblContainerLabel.getY() + lblContainerLabel.getHeight() + 20, 330, 200);
		
		panel.add(chartPanel);
		
		
		//Warning image for the label
		BufferedImage warningImg = ImageIO.read(new File("images/warning.png"));
		Image scaledWarningImage = warningImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		
		
		JLabel lblWarning = new JLabel("Showers in the afternoon!", new ImageIcon(scaledWarningImage), JLabel.LEFT);
		lblWarning.setText("<html>Showers in the afternoon<br>Bring a coat!");
		lblWarning.setForeground(Color.white);
		lblWarning.setOpaque(true);
		lblWarning.setBounds((getWidth() - 250) / 2, chartPanel.getY() + chartPanel.getHeight(), 250, 30);
		lblWarning.setBackground(Color.DARK_GRAY);
		panel.add(lblWarning);
		
		
		JButton btnMoreDetails = new JButton("More details >");
		btnMoreDetails.setBounds(lblWarning.getX() + lblWarning.getWidth() - 150, lblWarning.getY() + lblWarning.getHeight(), 150, 20);
	
		panel.add(btnMoreDetails);
		
		
		return panel;
	}
	
	private void setUpChart(JFreeChart chart) {
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.getRenderer().setSeriesPaint(0, Color.RED);
		plot.getRenderer().setSeriesPaint(1, Color.blue);
		LegendTitle lt = new LegendTitle(plot);
		lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		lt.setBackgroundPaint(new Color(200, 200, 255, 100));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.BOTTOM);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0.02, lt,RectangleAnchor.BOTTOM_RIGHT);
		
		DateAxis domainAxis = (DateAxis) plot.getDomainAxis(); //x axis
        DateFormat formatter = new SimpleDateFormat("h a");
        domainAxis.setDateFormatOverride(formatter);
        
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLowerBound(0);
		
		ta.setMaxWidth(0.4);
		plot.addAnnotation(ta);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);
		chart.setBackgroundPaint(null);
		chart.getPlot().setBackgroundPaint(null);
	}
	
	private XYDataset createDataset() {
		//TODO: Change this method to get actual weather statistics
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		
		TimeSeries temperature = new TimeSeries("Temperature (°C)");
		TimeSeries windSpeed = new TimeSeries("Wind speed (km / h)");
		
		Hour h = new Hour(0, new Day(dateToDisplay.getTime()));
		
		Random r = new Random();
		
		for (int i = 0; i < 24; i++) {
			temperature.add(h, r.nextInt(10) + 20);
			windSpeed.add(h, r.nextInt(5) + 10);
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
				SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM");
				String dateString = s.format(dateToDisplay.getTime());
				lblDateAndWind.setText("<html>" + dateString + "<br>Moderate wind (32 km/h) <br>Rain Unlikely</html>");
				
				daysAheadOfToday --;
				
				JFreeChart chart = ChartFactory.createTimeSeriesChart(null, "time", null , createDataset(),  false, true, false );
				setUpChart(chart);
				chartPanel.setChart(chart);
				
				if (daysAheadOfToday == 0)
					btnPreviousDay.setEnabled(false);
						

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
				SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM");
				String dateString = s.format(dateToDisplay.getTime());
				
				JFreeChart chart = ChartFactory.createTimeSeriesChart(null, "time", null , createDataset(),  false, true, false );
				setUpChart(chart);
				chartPanel.setChart(chart);
				
				lblDateAndWind.setText("<html>" + dateString + "<br>Moderate wind (32 km/h) <br>Rain Unlikely</html>");
				btnPreviousDay.setEnabled(true);
				daysAheadOfToday++;
				if (daysAheadOfToday == 5)
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
	
	public static void main(String[] args) throws IOException {
		System.out.println("hello again 3rd time");
		MainScreen m = new MainScreen();
		m.setVisible(true);
		
		
	}

}

class ImagePanel extends JPanel {
	private Image image;

	public ImagePanel(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
}

