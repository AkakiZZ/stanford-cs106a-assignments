/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entries.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
			entries.add(entry);
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		drawStartingLines();
		drawDecadesLabels();
		drawGraphs();
	}
	
	//draws the graphs
	private void drawGraphs() {
		int counter = 0;
		for (int i = 0; i < entries.size(); i++) {
			if (counter == 4) {
				counter = 0;
			}
			if (counter == 0) {
				drawGraph(entries.get(i), Color.black);
			} else if (counter == 1) {
				drawGraph(entries.get(i), Color.red);
			} else if (counter == 2) {
				drawGraph(entries.get(i), Color.blue);
			} else if (counter == 3) {
				drawGraph(entries.get(i), Color.yellow);
			}
			counter++;
		}
	}
	
	//draws the graph of the entry
	private void drawGraph(NameSurferEntry entry, Color color) {
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		String name = entry.getName();
		int rank = 0;
		int nextRank = 0;
		for (int i = 0; i < NDECADES - 1; i++) {
			rank = entry.getRank(i);
			nextRank = entry.getRank(i + 1);
			x1 = getWidth() / NDECADES * i;
			x2 = getWidth() / NDECADES * (i + 1);
			y1 = GRAPH_MARGIN_SIZE + (getHeight() - 2 * GRAPH_MARGIN_SIZE ) / (double) MAX_RANK * rank;
			y2 = GRAPH_MARGIN_SIZE + (getHeight() - 2 * GRAPH_MARGIN_SIZE ) / (double) MAX_RANK * nextRank;
			if (y1 == GRAPH_MARGIN_SIZE)
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
			if (y2 == GRAPH_MARGIN_SIZE)
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			GLine graphLine = new GLine(x1, y1, x2, y2);
			graphLine.setColor(color);
			add(graphLine);
			drawDataLabel(name, rank, color, x1, y1);
		}
		drawDataLabel(name, nextRank, color, x2, y2);
	}

	//draws name and rank labels
	private void drawDataLabel(String name, int rank, Color color, double x, double y) {
		String txt = "";
		if (rank != 0) {
			txt = name + " " + rank;
		} else {
			txt = name + " *";
		}
		GLabel dataLabel = new GLabel(txt);
		dataLabel.setLocation(x, y);
		dataLabel.setColor(color);
		add(dataLabel);
	}

	//draws the labels
	private void drawDecadesLabels() {
		for (int i = 0; i < NDECADES; i++) {
			GLabel label = new GLabel((START_DECADE + i * 10) + "");
			label.setLocation(getWidth() / NDECADES * i, getHeight() - (GRAPH_MARGIN_SIZE - label.getAscent()) / 2);
			add(label);
		}
	}

	//draws starting lines 
	private void drawStartingLines() {
		for (int i = 0; i < NDECADES; i++) {
			GLine line = new GLine(getWidth() / NDECADES * i, 0, getWidth() / NDECADES * i, getHeight());
			add(line);
		}
		GLine line1 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(line1);
		GLine line2 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(line2);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	 
	private ArrayList<NameSurferEntry> entries = new ArrayList<>();
}
