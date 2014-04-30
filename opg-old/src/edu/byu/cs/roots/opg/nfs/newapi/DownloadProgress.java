package edu.byu.cs.roots.opg.nfs.newapi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class DownloadProgress extends JDialog{

	private static final long serialVersionUID = 1L;
	/** Width of the dialog box */
	private static final int DIALOG_WIDTH = 600;
	/** Height of the dialog box */
	private static final int DIALOG_HEIGHT = 200;
	/** Title of the dialog box */
	private static final String DIALOG_TITLE = "Download Progress";

	private JProgressBar progressbar;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JButton cancelButton;
	private ActionListener buttonListener;
	private JLabel timeElapsedLabel, timePerPersonLabel, timeRemainingLabel;
	private int finishedPeople;
	private int totalPeople;
	
	private long timeStarted;
	
	private Timer timer;
	
	private boolean cancelled = false;
	
	public DownloadProgress(int jobMax){
	
		
		initialize(jobMax);
	}
	
	private void initialize(int jobMax)
	{
		progressbar = new JProgressBar(0, jobMax);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);
		progressbar.setString("0//0");
		progressbar.setSize(new Dimension(DIALOG_WIDTH, 20));
		
		textArea = new JTextArea();
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setAutoscrolls(true);
		
		this.setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
		
        cancelButton = new JButton("Cancel");

        timeElapsedLabel = new JLabel("Time Elapsed: ");
        timePerPersonLabel = new JLabel("Average Time Per Person: ");
        timeRemainingLabel = new JLabel("Time Remaining: ");
        
	    Box box = Box.createVerticalBox();
	    
	    Box row = Box.createHorizontalBox();
        
        box.add(progressbar);
        box.add(scrollPane);
        row.add(cancelButton);
        row.add(timeElapsedLabel);
        row.add(timePerPersonLabel);
        row.add(timeRemainingLabel);
        box.add(row);
        this.add(box, BorderLayout.CENTER);
	}
	
	public JProgressBar getProgressBar(){
		return progressbar;
	}
	
	public JTextArea getTextArea(){
		return textArea;
	}
	
	public void updateProgress(int finished, int total, String messages){
		progressbar.setValue(finished);
		progressbar.setMaximum(total);
		
		refreshProgressString();
		appendText(messages);
	}
	
	private void appendText(String txt){
		textArea.append(txt);
	}
	
	private void refreshProgressString(){
		progressbar.setString(progressbar.getValue() + "/" + progressbar.getMaximum());
		finishedPeople = progressbar.getValue();
		totalPeople = progressbar.getMaximum();
	}
	
	
	
	public void setThread(NFSDownloadManager thread){
		cancelButton.removeActionListener(buttonListener);
		final DownloadProgress parent = this;
		buttonListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelled = true;
				parent.dispose();
			}
			
		};
		cancelButton.addActionListener(buttonListener);
	}
	
	public void startTimer(){
		Action updateCursorAction = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	long timeNow = System.nanoTime()/1000000l;
		    	long timeElapsed = timeNow - timeStarted;
		    	
		    	Date d = new Date(timeElapsed);
		    	DateFormat formatter = new SimpleDateFormat("mm:ss");
		    	String formatted = formatter.format(d);
		    	timeElapsedLabel.setText("Time Elapsed: "+formatted);
		    	
		    	long timePerPerson = 0l;
		    	if (finishedPeople > 0){
			    	timePerPerson = timeElapsed / finishedPeople;
			    	d = new Date(timePerPerson);
			    	DateFormat personFormatter = new SimpleDateFormat("ss:SS");
			    	formatted = personFormatter.format(d);
			    	timePerPersonLabel.setText("Average Time Per Person: "+formatted + " seconds");
		    	}
		    	
		    	long timeRemaining = timePerPerson * (totalPeople-finishedPeople);
		    	d = new Date(timeRemaining);
		    	formatted = formatter.format(d);
		    	timeRemainingLabel.setText("Time Remaining: "+formatted);
		    }
		};
		
		
		timeStarted = System.nanoTime()/1000000l;
		timer = new Timer(1000, updateCursorAction);
		timer.start();
	}
	
	public void finish(){
		timer.stop();
		
	}
	
	public synchronized boolean getCancelled(){
		return cancelled;
	}
	
}
