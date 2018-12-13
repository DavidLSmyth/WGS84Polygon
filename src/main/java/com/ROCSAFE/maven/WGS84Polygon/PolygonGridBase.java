package com.ROCSAFE.maven.WGS84Polygon;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.ROCSAFE.maven.gpsutilities.CoordinateBase;
import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;
import com.ROCSAFE.maven.gpsutilities.WGS84CoordinateUtils;

/**
 * 
 * @author David Smyth
 * A class which creates a grid inside a Polygon
 *
 */
public class PolygonGridBase<T extends CoordinateBase> {

	PolygonBase<T> polygon;
	double latSpacing;
	double lngSpacing;
	//Does it make sense for the grid to have an altitude?
	double altitude;
	
	private int noGridPointsInBoundingRect;
	private int noGridPointsInPolygon;
	protected boolean canReturnCachedGridPoints;
	//a cache to store calculated grid points in Rectangle
	protected List<T> cachedRectangleGridPoints;
	protected List<T> cachedPolygonGridPoints;
	//The quadrilateral that circumscribes the polygon
	private QuadrilateralBase<T> boundingQuadrilateral;
	
	/**
	 * @param polygon - The bounding polygon (consisting of GPS coordinates) in which the grid will be created
	 * @param latSpacing - latitude spacing in metres
	 * @param lngSpacing - longitude spacing in metres
	 * @throws Exception 
	 */
	public PolygonGridBase(PolygonBase<T> polygon, double latSpacing, double lngSpacing) throws Exception {
		setPolygon(polygon);
		setLatSpacing(latSpacing);
		setLngSpacing(lngSpacing);
		canReturnCachedGridPoints = false;
		boundingQuadrilateral = polygon.getBoundingQuadrilateral();		
	}
	
	public PolygonGridBase(PolygonBase<T> polygon, double latSpacing, double lngSpacing, double altitude) throws Exception {
		//ToDo: generate grid points at given altitude
		this(polygon, latSpacing, lngSpacing);
	}

	/*
	public PolygonGridBase(List<WGS84Coordinate> boundary, double latSpacing, double lngSpacing) throws Exception {
		//probably should create a factory for this?
		this(new PolygonBase<T>(boundary), latSpacing, lngSpacing);
	}
	public PolygonGridBase(LinkedHashSet<WGS84Coordinate> boundary, double latSpacing, double lngSpacing) throws Exception {
		this(new PolygonBase<T>(boundary), latSpacing, lngSpacing);
	}
	*/
	
	
	
	public QuadrilateralBase<T> getBoundingQuad() {
		return boundingQuadrilateral;
	}
	
	
	
	
	
	/**
	 * Gives the ratio of how many grid points had to be generated in the bounding rectangle to the polygon
	 * @return
	 */
	public double getRatioOfPolyToRectGridPoints() {
		if (getNoGridPointsInBoundingRect() != 0) { 
			return ((double) getNoGridPointsInPolygon()) / ((double)getNoGridPointsInBoundingRect());
		}
		else {
//			System.out.println("Warning: no points found in bounding rect - perhaps change the grid spacing.");
			return -1;
		}
	}
	
	public int getNoGridPointsInBoundingRect() {
		return noGridPointsInBoundingRect;
	}

	protected void setNoGridPointsInBoundingRect(int noGridPointsInBoundingRect) {
		this.noGridPointsInBoundingRect = noGridPointsInBoundingRect;
	}

	public int getNoGridPointsInPolygon() {
		return noGridPointsInPolygon;
	}

	protected void setNoGridPointsInPolygon(int noGridPointsInPolygon) {
		canReturnCachedGridPoints = false;
		this.noGridPointsInPolygon = noGridPointsInPolygon;
	}
	
	public PolygonBase<T> getPolygon() {
		return polygon;
	}


	public void setPolygon(PolygonBase<T> polygon) {
		canReturnCachedGridPoints = false;
		this.polygon = polygon;
	}


	public double getLatSpacing() {
		return latSpacing;
	}

	public void setLatSpacing(double latSpacing) {
		canReturnCachedGridPoints = false;
		this.latSpacing = latSpacing;
	}


	public double getLngSpacing() {
		return lngSpacing;
	}


	public void setLngSpacing(double lngSpacing) {
		canReturnCachedGridPoints = false;
		this.lngSpacing = lngSpacing;
	}
}
