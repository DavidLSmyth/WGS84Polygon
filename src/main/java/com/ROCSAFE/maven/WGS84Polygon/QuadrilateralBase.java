package com.ROCSAFE.maven.WGS84Polygon;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import com.ROCSAFE.maven.gpsutilities.CoordinateBase;

public abstract class QuadrilateralBase<T extends CoordinateBase> extends PolygonBase<T> {
	
	public QuadrilateralBase(List<T> boundary) throws Exception {
		super(boundary);
		if(!verifyQuadrilateral()){
			throw new Exception("Please provide four coordinates to create a quadrilateral");
		}
	}

	public QuadrilateralBase(T coordinateBase, T coordinateBase2, T coordinateBase3, T coordinateBase4) throws Exception {
		this(Arrays.asList(coordinateBase, coordinateBase2, coordinateBase3, coordinateBase4));
		
		// TODO Auto-generated constructor stub
	}
	
	public QuadrilateralBase(LinkedHashSet<T> boundary) throws Exception {
		super(boundary);
		if(!verifyQuadrilateral()){
			throw new Exception("Please provide four coordinates to create a quadrilateral");
		}
	}
	
	protected boolean verifyQuadrilateral() {
		return boundary.size() == 4;
	}

	
	
	

}
