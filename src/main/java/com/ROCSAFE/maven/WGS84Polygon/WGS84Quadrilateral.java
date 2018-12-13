/**
 * 
 */
package com.ROCSAFE.maven.WGS84Polygon;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * @author 13383861
 *
 */
public class WGS84Quadrilateral extends QuadrilateralBase<WGS84Coordinate> {

	public WGS84Quadrilateral(LinkedHashSet<WGS84Coordinate> boundary) throws Exception {
		super(boundary);
		// TODO Auto-generated constructor stub
	}
	public WGS84Quadrilateral(List<WGS84Coordinate> boundary) throws Exception {
		super(boundary);
		if(!verifyQuadrilateral()){
			throw new Exception("Please provide four coordinates to create a quadrilateral");
		}
	}

	public WGS84Quadrilateral(WGS84Coordinate coordinateBase, WGS84Coordinate coordinateBase2, WGS84Coordinate coordinateBase3, WGS84Coordinate coordinateBase4) throws Exception {
		this(Arrays.asList(coordinateBase, coordinateBase2, coordinateBase3, coordinateBase4));
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public QuadrilateralBase getBoundingQuadrilateral() {
		// TODO Auto-generated method stub
		return this;
	}

}
