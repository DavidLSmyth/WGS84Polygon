package com.ROCSAFE.maven.WGS84Polygon;

import java.util.Arrays;
import java.util.List;
import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

public class GPSQuadrilateral extends GPSPolygon {
	
	public GPSQuadrilateral(List<WGS84Coordinate> boundary) throws Exception {
		super(boundary);
		if(!verifyQuadrilateral()){
			throw new Exception("Please provide four coordinates to create a quadrilateral");
		}
	}

	public GPSQuadrilateral(WGS84Coordinate p1, WGS84Coordinate p2, WGS84Coordinate p3, WGS84Coordinate p4) throws Exception {
		this(Arrays.asList(p1, p2, p3, p4));
		
		// TODO Auto-generated constructor stub
	}
	
	private boolean verifyQuadrilateral() {
		return boundary.size() == 4;
	}
	

}
